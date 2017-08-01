package com.example.sonic.audiorecordingexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

/**
* Use MediaCodec (encoder) and MediaMuxer (muxer) to to record .m4a file.
* */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MediaMuxerWrapper mMuxer; // muxer for audio recording

    private ImageButton mRecordButton; // recording button for start/stop recording

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecordButton = (ImageButton)findViewById(R.id.record_button);
        mRecordButton.setOnClickListener(mOnClickListener);

    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.record_button:
                    if (mMuxer == null){
                        startRecording();
                    } else {
                        stopRecording();
                    }
                    break;
            }
        }
    };

    /**
    * Start Recording
    **/
    private void startRecording() {

        Log.v(TAG, "startRecording:");

        try {
            mRecordButton.setColorFilter(0xffff0000); // turn red
            mMuxer = new MediaMuxerWrapper(".m4a");	// ".m4a" is also OK.

            if (true) {
                // for audio capturing
                new MediaAudioEncoder(mMuxer, mMediaEncoderListener);
            }

            mMuxer.prepare();
            mMuxer.startRecording();

        } catch (final IOException e) {
            mRecordButton.setColorFilter(0);
            Log.e(TAG, "startCapture:", e);
        }
    }

    /**
     * Stop Recording
     **/
    private void stopRecording() {

        Log.v(TAG, "stopRecording:mMuxer");

        mRecordButton.setColorFilter(0);	// return to default color
        if (mMuxer != null) {
            mMuxer.stopRecording();
            mMuxer = null;
            // you should not wait here
        }

        Toast.makeText(getApplicationContext(), "Recording ends...", Toast.LENGTH_SHORT).show();
    }

    /**
     * callback methods from encoder
     */
    private final MediaEncoder.MediaEncoderListener mMediaEncoderListener = new MediaEncoder.MediaEncoderListener() {
        @Override
        public void onPrepared(final MediaEncoder encoder) {

            Log.v(TAG, "onPrepared:encoder=" + encoder);

        }

        @Override
        public void onStopped(final MediaEncoder encoder) {

            Log.v(TAG, "onStopped:encoder=" + encoder);

        }
    };
}
