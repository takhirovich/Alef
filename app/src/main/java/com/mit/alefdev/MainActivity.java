package com.mit.alefdev;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.mit.alefdev.adapters.MainAdapter;
import com.mit.alefdev.intefaces.ClickInterface;
import com.mit.alefdev.network.NetworkServices;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements ClickInterface {
    private RecyclerView mRc;
    private MainAdapter mainAdapter;
    private SwipeRefreshLayout swipeToRefresh;
    private int rotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRc = findViewById(R.id.recyclerView);
        swipeToRefresh = findViewById(R.id.swiperefresh);
        swipeToRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshing();
                    }
                }
        );

        Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
         rotation = display.getRotation();
        Log.d("aaaaa",rotation+"");

    }

    private void refreshing() {
        sendRequest();
        swipeToRefresh.setRefreshing(false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sendRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendRequest();
    }

    private void initRecycler() {
        mainAdapter = new MainAdapter(this,this);
        if (isTablet(this) || rotation == 3 || rotation == 1){
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
            mRc.setLayoutManager(mLayoutManager);
        }else {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            mRc.setLayoutManager(mLayoutManager);
        }
        mRc.setItemAnimator(new DefaultItemAnimator());
        mRc.setAdapter(mainAdapter);
    }

    private void sendRequest() {
        showProgress(getString(R.string.please_wait));
        Call<ArrayList<String>> call = NetworkServices.getInstance().getRequestApi().getImage();
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<String>> call, @NonNull Response<ArrayList<String>> response) {
                dismissProgress();
                ArrayList<String> requestResult = response.body();
                Globals.setImg(requestResult);
                initRecycler();
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<String>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast toast = Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    @Override
    public void onImageClick(int position) {
        fullImageDialog(Globals.getImg().get(position));
    }
}
