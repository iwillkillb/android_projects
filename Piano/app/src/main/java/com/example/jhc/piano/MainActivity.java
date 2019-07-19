package com.example.jhc.piano;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    SoundPool soundPool;
    final int soundFile[] = {R.raw.piano1_1do, R.raw.piano1_2re, R.raw.piano1_3mi, R.raw.piano1_4fa, R.raw.piano1_5so, R.raw.piano1_6ra, R.raw.piano1_7si, R.raw.piano1_8do};
    int soundId[] = new int[8];

    Timer timer[] = new Timer[8];   //Timers using by Seekbars

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("전현찬 프로젝트");

        final ImageView iv[] = new ImageView[8];  //건반 이미지들
        Integer ivId[] = {R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv7, R.id.iv8};

        SeekBar sb[] = new SeekBar[8];      //건반 옆의 바들
        Integer sbId[] = {R.id.sb1, R.id.sb2, R.id.sb3, R.id.sb4, R.id.sb5, R.id.sb6, R.id.sb7, R.id.sb8};
        final int sbDelay[] = new int [8];//바 게이지 정보를 저장하는 배열

        InitSound();

        for(int i=0 ; i<ivId.length ; i++){
            final int index;
            index = i;
            iv[index] = (ImageView)findViewById(ivId[i]);
            sb[index] = (SeekBar)findViewById(sbId[i]);

            sb[index].setMax(10);

            iv[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {   //건반 이미지를 누르면 소리가 난다.
                    PlaySound(index);
                    KeyImageChange(iv[index], index);
                }
            });



            sb[index].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {    //각 Seekbar들에 대해서

                @Override   //바의 내용을 변경하면
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {    //progress : 0 ~ 10
                    if(fromUser){
                        sbDelay[index] = 11 - progress;    //Take Zero or 10 ~ 1
                        if(progress == 0)sbDelay[index] = 0;    //바를 가장 왼쪽으로 당기면, 기능 OFF
                    }
                }

                @Override   //바를 누르기 시작하면
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if(timer[index] != null){   //이전의 timer 기록은 제거
                        timer[index].cancel();
                    }
                }

                @Override   //바에서 손을 떼면
                public void onStopTrackingTouch(SeekBar seekBar) {

                    TimerTask timerTask = new TimerTask() { //Timer에서 스케줄을 등록했을 때 실행되는 기능
                        @Override
                        public void run() {
                            if(sbDelay[index] > 0){
                                PlaySound(index);
                            } else {    //바를 가장 왼쪽으로 당겨서 기능을 끈 경우, 소리도 바로 꺼진다.
                                StopSound(index);
                            }
                            KeyImageChange(iv[index], index);
                        }
                    };

                    timer[index] = new Timer(); //이전의 것을 지우고, 바에게 새로운 정보를 넣는다.

                    if(sbDelay[index] > 0){
                        if(timer[index] != null){   //타이머에 손을 댄 경우(ON), sbDelay[index] * 1초의 주기로 timerTask의 기능을 실행한다.
                            timer[index].schedule(timerTask, 0, sbDelay[index] * 1000); //Activate timerTask's code.
                        }
                        Toast.makeText(getApplicationContext(), sbDelay[index] + "sec delay of " + index, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No Repeat of " + index, Toast.LENGTH_SHORT).show();
                    }

                }
            });



        }
    }

    void InitSound(){   //soundPool과 소리 파일들을 연결한다.
        soundPool = new SoundPool(64, AudioManager.STREAM_MUSIC, 1);
        for(int i=0 ; i<8 ; i++){
            soundId[i] = soundPool.load(getBaseContext(), soundFile[i], 1);
        }
    }
    void PlaySound(int index){
        soundPool.play(soundId[index],1,1,0,0,1);
    }
    void StopSound(int index) {soundPool.stop(soundId[index]);}



    void KeyImageChange(final ImageView iv, final int index){
                TimerTask timerTask = new TimerTask() { //Timer에서 스케줄을 등록했을 때 실행되는 기능
            @Override
            public void run() {
                switch (index){
                    case 0: iv.setImageResource(R.drawable.keydo);  break;
                    case 1: iv.setImageResource(R.drawable.keyre);  break;
                    case 2: iv.setImageResource(R.drawable.keymi);  break;
                    case 3: iv.setImageResource(R.drawable.keyfa);  break;
                    case 4: iv.setImageResource(R.drawable.keyso);  break;
                    case 5: iv.setImageResource(R.drawable.keyra);  break;
                    case 6: iv.setImageResource(R.drawable.keysi);  break;
                    case 7: iv.setImageResource(R.drawable.keyddo);  break;

                    default: iv.setImageResource(R.drawable.keynull);  break;
                }
            }
        };

        iv.setImageResource(R.drawable.keynull);
        Timer timer = new Timer();
        timer.schedule(timerTask, 100);
    }




}
