package com.example.maudioplayer;

import android.graphics.Bitmap;
import android.net.Uri;

class Video {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    private String title,path,size,duration,Videoid,folderName;
    private Uri artUri;
    private Bitmap thumbnail;

    public String getVideoId() {
        return Videoid;
    }

    public void setVideoId(String id) {
        this.Videoid = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Uri getArtUri() {
        return artUri;
    }

    public void setArtUri(Uri artUri) {
        this.artUri = artUri;
    }

    public Video(String Path , String title, Bitmap thumbnail, String size, String duration, String VideoId, String folderName, Uri artUri) {

        this.path=Path;
        this.thumbnail=thumbnail;
        this.title = title;
        this.size=size;
        this.duration=duration;
        this.Videoid=VideoId;
        this.folderName=folderName;
        this.artUri=artUri;

    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
