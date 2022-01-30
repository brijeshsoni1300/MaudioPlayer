package com.example.maudioplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalFileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalFileFragment extends Fragment implements VideoFolderAdapter.ListItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Video> videoList = new ArrayList<Video>();
    ArrayList<String>Dummy=new ArrayList<String>();
    ArrayList<VideoFolderModel> videoFolderList = new ArrayList<VideoFolderModel>();
    private int lastPosition;
    GridLayoutManager gridLayoutManager;
    private final static int STORAGE_PERMISION = 101;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocalFileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocalFileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocalFileFragment newInstance(String param1, String param2) {
        LocalFileFragment fragment = new LocalFileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedViewModel model= new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        lastPosition=model.getLastPosition();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_local_file, container, false);

        SharedViewModel model= new ViewModelProvider(requireActivity()).get(SharedViewModel.class);





        RecyclerView videofolderRecyclerView = rootView.findViewById(R.id.videoFolderRecyclerView);
        videofolderRecyclerView.setAdapter(new VideoFolderAdapter(videoFolderList, getContext(), this::onListItemClick));
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        videofolderRecyclerView.setLayoutManager(gridLayoutManager);


        videofolderRecyclerView.scrollToPosition(lastPosition);
        videofolderRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastPosition = gridLayoutManager.findFirstVisibleItemPosition();
                model.setLastPosition(lastPosition);
            }
        });

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }else
        {
            getVideos();
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
                String VideoId=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));

//                try {
//                    File file=new File(VideoPath);
//                    Uri VideoArtUri=Uri.fromFile(file);
//                    if(file.exists())
//                    {
//                        videoList.add(new Video(VideoPath, VideoTitle, VideoThumbnail,VideoSize,VideoDuration,VideoId,folderName,VideoArtUri));
//
//                    }
//                }catch (Exception e)
//                {
//
//                }
                if (!Dummy.contains(folderName)) {
                    Dummy.add(folderName);
                    videoFolderList.add(new VideoFolderModel(folderId,folderName));
                }
            } while (cursor.moveToNext());
        }
//        SharedViewModel model= new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        model.setVideoFolderList(videoFolderList);


    }







    @Override
    public void onListItemClick(int position) {

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("list", lastPosition);
    }


    @Override
    public void onDetach() {
        super.onDetach();
//        SharedPreferences getPrefs= PreferenceManager.getDefaultSharedPreferences(getContext());
//        SharedPreferences.Editor editor=getPrefs.edit();
//        editor.putInt("lastPos",lastPosition);
//        editor.apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Not Granted", Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }
        }
    }


}