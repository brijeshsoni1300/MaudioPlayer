package com.example.maudioplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideosInsideFolder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideosInsideFolder extends Fragment implements VideoListAdapter.ListItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Video> videoList = new ArrayList<Video>();
    ArrayList<String>Dummy=new ArrayList<String>();
    ArrayList<String>Dummy1=new ArrayList<String>();

    ArrayList<VideoFolderModel> videoFolderList = new ArrayList<VideoFolderModel>();
    private int lastPosition;
    LinearLayoutManager linearLayoutManager;

    public VideosInsideFolder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideosInsideFolder.
     */
    // TODO: Rename and change types and number of parameters
    public static VideosInsideFolder newInstance(String param1, String param2) {
        VideosInsideFolder fragment = new VideosInsideFolder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_videos_inside_folder, container, false);
        int pos=0;
        if(getArguments()!=null)
        {
            pos=getArguments().getInt("PositionOfFolder");
            videoFolderList= (ArrayList<VideoFolderModel>) getArguments().getSerializable("VideoFolderList");

        }
        RecyclerView videofolderRecyclerView = rootView.findViewById(R.id.videoListRecyclerView);
        videofolderRecyclerView.setAdapter(new VideoListAdapter(videoList, getContext(), this::onListItemClick));
        linearLayoutManager= new LinearLayoutManager(getContext());
        videofolderRecyclerView.setLayoutManager(linearLayoutManager);

//        getVideos();
        String FolderID=videoFolderList.get(pos).getFolderKey();
        if(FolderID!=null)
        {
            getVideosList(FolderID);

        }


        return rootView;
    }

    private void getVideos() {

        ContentResolver contentResolver = getContext().getContentResolver();

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[]{MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE, MediaStore.Video.Media._ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DURATION, MediaStore.Video.Media.BUCKET_ID};

        Cursor cursor = contentResolver.query(uri, projection, null, null, MediaStore.Video.Media.DATE_ADDED + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String VideoTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String VideoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                Bitmap VideoThumbnail = ThumbnailUtils.createVideoThumbnail(VideoPath, MediaStore.Images.Thumbnails.MINI_KIND);
                String VideoSize = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                String VideoDuration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));

                String folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                String folderId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));

//                videoList.add(new Video(VideoPath, VideoTitle, VideoThumbnail,VideoSize,VideoDuration));
                if(!Dummy.contains(folderName))
                {
                    Dummy.add(VideoTitle);
                    videoFolderList.add(new VideoFolderModel(folderId,folderName));

                }

            } while (cursor.moveToNext());
        }
//        SharedViewModel model= new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        model.setVideoFolderList(videoFolderList);


    }


    private void getVideosList(String FolderID) {

        ContentResolver contentResolver = getContext().getContentResolver();

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String selection=MediaStore.Video.Media.BUCKET_ID+" like?";
        String[] selectionArgs  = {FolderID};

        String[] projection = new String[]{MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE, MediaStore.Video.Media._ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DURATION, MediaStore.Video.Media.BUCKET_ID};

        Cursor cursor = contentResolver.query(uri, projection, selection,selectionArgs , MediaStore.Video.Media.DATE_ADDED + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {




                String VideoTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String VideoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                String VideoSize = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                String VideoDuration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String VideoId=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                String folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
//                String folderId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));
//
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize = 1;
                Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail( contentResolver,Long.parseLong(VideoId), MediaStore.Video.Thumbnails.MICRO_KIND, options);

                try {
                    File file=new File(VideoPath);
                    Uri VideoArtUri=Uri.fromFile(file);
                    if(file.exists())
                    {
                        videoList.add(new Video(VideoPath, VideoTitle, curThumb,VideoSize,VideoDuration,VideoId,folderName,VideoArtUri));

                    }
                }catch (Exception e)
                {

                }




                if(!Dummy1.contains(VideoTitle))
                {
                    Dummy1.add(VideoTitle);


                }

//                if (!videoFolderList.contains(folderName)) {
//                    videoFolderList.add(new VideoFolderModel(folderId,folderName));
//                }
            } while (cursor.moveToNext());
        }
//        SharedViewModel model= new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        model.setVideoFolderList(videoFolderList);


    }


    @Override
    public void onListItemClick(int position) {

    }
}