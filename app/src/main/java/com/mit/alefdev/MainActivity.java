package com.mit.alefdev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mit.alefdev.adapters.MainAdapter;
import com.mit.alefdev.beans.RequestResult;
import com.mit.alefdev.intefaces.ClickInterface;
import com.mit.alefdev.network.NetworkServices;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ClickInterface {
    private RecyclerView mRc;
    private MainAdapter mainAdapter;
    private SwipeRefreshLayout swipeToRefresh;

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

    }

    private void refreshing() {
        sendRequest();
        swipeToRefresh.setRefreshing(false);
    }

    private void initRecycler() {
        mainAdapter = new MainAdapter(this,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRc.setLayoutManager(mLayoutManager);
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


    @Override
    public void onImageClick(int position) {
        fullImageDialog(Globals.getImg().get(position));
    }
}
