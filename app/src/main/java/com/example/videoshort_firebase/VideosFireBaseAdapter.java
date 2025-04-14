package com.example.videoshort_firebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class VideosFireBaseAdapter extends FirebaseRecyclerAdapter<VideoModel, VideosFireBaseAdapter.MyHolder> {

    private boolean isFav = false;

    public VideosFireBaseAdapter(@NonNull FirebaseRecyclerOptions<VideoModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull VideoModel model) {
        holder.textVideoTitle.setText(model.getTitle());
        holder.textVideoDescription.setText(model.getDesc());

        holder.videoView.setVideoPath(model.getUrl());
        holder.videoView.setOnPreparedListener(mp -> {
            holder.videoProgressBar.setVisibility(View.GONE);
            mp.start();
        });

        holder.videoView.setOnCompletionListener(mp -> mp.start());

        holder.favorites.setOnClickListener(v -> {
            if (!isFav) {
                holder.favorites.setImageResource(R.drawable.ic_fill_favorite);
                isFav = true;
            } else {
                holder.favorites.setImageResource(R.drawable.ic_favorite);
                isFav = false;
            }
        });
        holder.dislike.setOnClickListener(v -> {
            if (!isFav) {
                holder.dislike.setImageResource(R.drawable.ic_fill_dislike);
                isFav = true;
            } else {
                holder.dislike.setImageResource(R.drawable.ic_dislike);
                isFav = false;
            }
        });
        holder.imPerson.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, ViewProfileActivity.class);

            // Optional: gửi email hoặc ID người đăng để xem thông tin người đó
            intent.putExtra("email", model.getUserEmail());

            context.startActivity(intent);
        });
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private VideoView videoView;
        private ProgressBar videoProgressBar;
        private TextView textVideoTitle, textVideoDescription;
        private ImageView imPerson, favorites, imShare, imMore, dislike;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            imPerson = itemView.findViewById(R.id.imPerson);
            favorites = itemView.findViewById(R.id.favorites);
            dislike = itemView.findViewById(R.id.disfavorites2);
            imShare = itemView.findViewById(R.id.imShare);
            imMore = itemView.findViewById(R.id.imMore);

        }
    }
}