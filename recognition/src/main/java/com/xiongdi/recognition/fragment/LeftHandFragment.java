package com.xiongdi.recognition.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xiongdi.recognition.R;
import com.xiongdi.recognition.activity.GatherActivity;
import com.xiongdi.recognition.audio.AudioPlay;

/**
 * Created by moubiao on 2016/3/22.
 * 采集左手指纹的fragment
 */
public class LeftHandFragment extends Fragment implements View.OnClickListener {
    private final static int LEFT_LITTER_FINGER = 1;//左手小拇指
    private final static int LEFT_RING_FINGER = 2;
    private final static int LEFT_MIDDLE_FINGER = 3;
    private final static int LEFT_INDEX_FINGER = 4;
    private final static int LEFT_THUMB_FINGER = 5;
    private final static int RIGHT_LITTER_FINGER = 6;
    private final static int RIGHT_RING_FINGER = 7;
    private final static int RIGHT_MIDDLE_FINGER = 8;
    private final static int RIGHT_INDEX_FINGER = 9;
    private final static int RIGHT_THUMB_FINGER = 10;

    private Button leftLittleBT, leftRingBT, leftMiddleBT, leftIndexBT, leftThumbBT,
            rightLittleBT, rightRingBT, rightMiddleBT, rightIndexBT, rightThumbBT;
    private GatherActivity gatherActivity;

    private AudioPlay mAudioPlay;
    private AssetManager mAssetManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gatherActivity = (GatherActivity) getActivity();
        mAudioPlay = new AudioPlay();
        mAssetManager = gatherActivity.getAssets();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_hand_layout, container, false);

        initView(view);
        setListener();

        return view;
    }

    private void initView(View view) {
        leftLittleBT = (Button) view.findViewById(R.id.left_little_finger_bt);
        leftRingBT = (Button) view.findViewById(R.id.left_ring_finger_bt);
        leftMiddleBT = (Button) view.findViewById(R.id.left_middle_finger_bt);
        leftIndexBT = (Button) view.findViewById(R.id.left_index_finger_bt);
        leftThumbBT = (Button) view.findViewById(R.id.left_thumb_bt);
        rightLittleBT = (Button) view.findViewById(R.id.right_little_finger_bt);
        rightRingBT = (Button) view.findViewById(R.id.right_ring_finger_bt);
        rightMiddleBT = (Button) view.findViewById(R.id.right_middle_finger_bt);
        rightIndexBT = (Button) view.findViewById(R.id.right_index_finger_bt);
        rightThumbBT = (Button) view.findViewById(R.id.right_thumb_bt);
    }

    private void setListener() {
        leftLittleBT.setOnClickListener(this);
        leftRingBT.setOnClickListener(this);
        leftMiddleBT.setOnClickListener(this);
        leftIndexBT.setOnClickListener(this);
        leftThumbBT.setOnClickListener(this);
        rightLittleBT.setOnClickListener(this);
        rightRingBT.setOnClickListener(this);
        rightMiddleBT.setOnClickListener(this);
        rightIndexBT.setOnClickListener(this);
        rightThumbBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_little_finger_bt:
                gatherActivity.setFingerNUM(LEFT_LITTER_FINGER);
                break;
            case R.id.left_ring_finger_bt:
                gatherActivity.setFingerNUM(LEFT_RING_FINGER);
                break;
            case R.id.left_middle_finger_bt:
                gatherActivity.setFingerNUM(LEFT_MIDDLE_FINGER);
                break;
            case R.id.left_index_finger_bt:
                gatherActivity.setFingerNUM(LEFT_INDEX_FINGER);
                break;
            case R.id.left_thumb_bt:
                gatherActivity.setFingerNUM(LEFT_THUMB_FINGER);
                break;
            case R.id.right_little_finger_bt:
                gatherActivity.setFingerNUM(RIGHT_LITTER_FINGER);
                break;
            case R.id.right_ring_finger_bt:
                gatherActivity.setFingerNUM(RIGHT_RING_FINGER);
                break;
            case R.id.right_middle_finger_bt:
                gatherActivity.setFingerNUM(RIGHT_MIDDLE_FINGER);
                break;
            case R.id.right_index_finger_bt:
                gatherActivity.setFingerNUM(RIGHT_INDEX_FINGER);
                break;
            case R.id.right_thumb_bt:
                gatherActivity.setFingerNUM(RIGHT_THUMB_FINGER);
                break;
            default:
                break;
        }

        mAudioPlay.resetMediaPlayer();
        mAudioPlay.playAsset(AudioPlay.PUT_FINGER, mAssetManager);
        gatherActivity.showGatherFingerDialog();
        gatherActivity.gatherFingerprint();
    }
}
