package com.example.maudioplayer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListHolder> {

    private ArrayList<Video> videoList;
    private Context context;
    final private ListItemClickListener mOnClickListner;

    public VideoListAdapter(ArrayList<Video> videoList, Context context, ListItemClickListener mOnClickListner) {
        this.videoList = videoList;
        this.context = context;
        this.mOnClickListner = mOnClickListner;
    }

    @NonNull
    @Override
    public VideoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_layout, parent, false);
        return new VideoListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListHolder holder, int position) {

        Video video=videoList.get(position);
        holder.VideofolderName.setText(video.getTitle());
        if(video.getThumbnail()!=null)
        {
            Glide.with(context).asBitmap().load(video.getThumbnail()).apply(RequestOptions.placeholderOf(R.drawable.videothumbnailplaceholder)).into(holder.thumbnailOfVideo);

        }

//        Toast.makeText(context, ""+videoList.get(position).getPath(), Toast.LENGTH_SHORT).show();
        holder.Folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("PATHOFVIDEO",videoList.get(position).getPath());
                bundle.putString("uri",videoList.get(position).getArtUri().toString());
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.action_videosInsideFolder_to_playVideo,bundle);

            }
        });




    }









    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public class VideoListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView VideofolderName;
        private ImageView thumbnailOfVideo;
        private CardView Folder;
        public VideoListHolder(@NonNull View itemView) {
            super(itemView);

            VideofolderName=itemView.findViewById(R.id.videoName);
            thumbnailOfVideo=itemView.findViewById(R.id.thumbnail);
            Folder=itemView.findViewById(R.id.videoListFolder);
        }

        @Override
        public void onClick(View view) {

        }
    };

    interface ListItemClickListener {
        void onListItemClick(int position);
    }

}


