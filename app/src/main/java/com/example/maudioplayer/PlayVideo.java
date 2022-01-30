package com.example.maudioplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayVideo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayVideo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PlayerView videoView;

    public PlayVideo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayVideo.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayVideo newInstance(String param1, String param2) {
        PlayVideo fragment = new PlayVideo();
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
        View rootView= inflater.inflate(R.layout.fragment_play_video, container, false);

        String PathOfVideo=null;
        Uri originalUri=null;
        if(getArguments()!=null)
        {
            PathOfVideo=getArguments().getString("PATHOFVIDEO");
            originalUri=Uri.parse(getArguments().getString("uri"));
        }

        Toast.makeText(getContext(),""+originalUri,Toast.LENGTH_SHORT).show();
        videoView=rootView.findViewById(R.id.exoPlayerView);
        Toast.makeText(getContext(), ""+PathOfVideo, Toast.LENGTH_SHORT).show();

        Player player= new SimpleExoPlayer.Builder(getContext()).build();
        videoView.setPlayer(player);
        MediaItem mediaItem=MediaItem.fromUri(Uri.parse(PathOfVideo));
        player.setMediaItem(mediaItem);
        player.prepare();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1);

        }else
        {
            player.play();

        }






        //specify the location of media file





        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else
        {

        }



        //Setting MediaController and URI, then starting the videoView




        return rootView;
    }
}