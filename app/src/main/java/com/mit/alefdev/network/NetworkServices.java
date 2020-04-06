package com.mit.alefdev.network;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkServices {
    private static NetworkServices mInstance;
    private Retrofit mRetrofit;

    private NetworkServices() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(interceptor);
        client.connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://dev-tasks.alef.im/task-m-001/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static NetworkServices getInstance() {
        if (mInstance == null) mInstance = new NetworkServices();
        return mInstance;
    }

    public RequestApiHolder getRequestApi() {
        return mRetrofit.create(RequestApiHolder.class);
    }
}
