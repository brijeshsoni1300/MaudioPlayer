package com.example.maudioplayer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoFolderAdapter extends RecyclerView.Adapter<VideoFolderAdapter.VideoFolderHolder> {

    private ArrayList<VideoFolderModel> videoFolderList;
    private Context context;
    final private ListItemClickListener mOnClickListner;

    public VideoFolderAdapter(ArrayList<VideoFolderModel> videoFolderList, Context context, ListItemClickListener mOnClickListner) {
        this.videoFolderList = videoFolderList;
        this.context = context;
        this.mOnClickListner = mOnClickListner;
    }

    @NonNull
    @Override
    public VideoFolderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videofolder_recyclerview_layout, parent, false);
        return new VideoFolderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoFolderHolder holder, int position) {

        VideoFolderModel videoFolderModel=videoFolderList.get(position);
        holder.folderName.setText(videoFolderModel.getFolderName());
        holder.Folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("PositionOfFolder",position);
                bundle.putSerializable("VideoFolderList",videoFolderList);
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.action_localFileFragment2_to_videosInsideFolder,bundle);

            }
        });

    }







    @Override
    public int getItemCount() {
        return videoFolderList.size();
    }


    public class VideoFolderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView folderName;
        private CardView Folder;
        public VideoFolderHolder(@NonNull View itemView) {
            super(itemView);

            Folder=itemView.findViewById(R.id.Folder);
            folderName=itemView.findViewById(R.id.foldername);
        }

        @Override
        public void onClick(View view) {

        }
    };

    interface ListItemClickListener {
        void onListItemClick(int position);
    }

}

