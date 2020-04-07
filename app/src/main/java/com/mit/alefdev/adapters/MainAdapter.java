package com.mit.alefdev.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.mit.alefdev.Globals;
import com.mit.alefdev.R;
import com.mit.alefdev.intefaces.ClickInterface;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private ClickInterface listener;
    private Activity mActivity;
    private ArrayList<String> images = Globals.getImg();


    public MainAdapter(Activity mActivity, ClickInterface listener) {
        this.mActivity = mActivity;
        this.listener = listener;

    }
    @NonNull
    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_image_view, viewGroup, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainViewHolder holder, final int position) {
        Glide.with(mActivity)
                .load(images.get(position))
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onImageClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }


}
