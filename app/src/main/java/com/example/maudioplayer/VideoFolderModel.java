package com.example.maudioplayer;

public class VideoFolderModel {
    String folderKey;
    String folderName;

    public VideoFolderModel(String folderKey, String folderName) {
        this.folderKey = folderKey;
        this.folderName = folderName;
    }

    public String getFolderKey() {
        return folderKey;
    }

    public void setFolderKey(String folderKey) {
        this.folderKey = folderKey;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}

