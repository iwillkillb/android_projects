package com.example.jhc.music;

import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar pbMP3;
    TextView tv;
    Switch sw;
    MediaPlayer mPlayer;

    // I using THREAD. thread changes UI.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbMP3 = (SeekBar)findViewById(R.id.seekbar);
        tv = (TextView)findViewById(R.id.tv);
        sw = (Switch)findViewById(R.id.sw);


        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw.isChecked() == true){
                    mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.buzzmonologue);
                    mPlayer.start();
                    makeThread();
                } else {
                    mPlayer.stop();
                }
            }
        });



        pbMP3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void makeThread () {
        new Thread() {
          public void run() {
              //music continue...
              while(mPlayer.isPlaying()) {
                  pbMP3.setMax(mPlayer.getDuration());  //=All time(ms)
                  pbMP3.setProgress(mPlayer.getCurrentPosition());
                  //    Bar setting...
                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          tv.setText("남은시간 : " + (mPlayer.getDuration() - mPlayer.getCurrentPosition())/1000 + "초");
                      }
                  });
                  SystemClock.sleep(1000);   //Call Delay (1 Second = 1000)
              }
          }
        }.start();
    }
}
