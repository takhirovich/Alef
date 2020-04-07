package com.mit.alefdev.network;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RequestApiHolder {

    @Headers({"Content-Type: application/json"})
    @GET("list.php")
    Call<ArrayList<String>> getImage();

}
