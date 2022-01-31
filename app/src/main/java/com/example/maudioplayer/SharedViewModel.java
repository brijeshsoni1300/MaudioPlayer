package com.example.maudioplayer;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {

    private int lastPosition;
    private ArrayList<VideoFolderModel>videoFolderModelArrayList=null;

    public void setLastPosition(int lastPosition)
    {
        this.lastPosition=lastPosition;
    }
    public int getLastPosition()
    {
        return this.lastPosition;
    }

    public ArrayList<VideoFolderModel> getVideoFolderModelArrayList() {
        return videoFolderModelArrayList;
    }

    public void setVideoFolderModelArrayList(ArrayList<VideoFolderModel> videoFolderModelArrayList) {
        this.videoFolderModelArrayList = videoFolderModelArrayList;
    }
}
