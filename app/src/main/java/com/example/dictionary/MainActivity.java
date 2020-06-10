package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private Button[] wordButton;
    private ArrayList<Word> allWord,iterationWordCollect;
    private SQLLiteHelper sqlLiteHelper;
    private Switch aSwitch;
    private TextView textView;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        sqlLiteHelper = new SQLLiteHelper(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        ArrayList<Word> arrayList = sqlLiteHelper.getAllWordOnDB();
        if ((((dictionary) this.getApplication()).isSecondLogin())&&(arrayList.isEmpty())) instruction();
        //Banner
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //Interstitial
        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        ImageButton ToAddWordActivityButton = findViewById(R.id.AddWordAC);
        ToAddWordActivityButton.setOnClickListener(new ToAddWordActivityOnClickListener());
        ImageButton ToLibraryActivityButton = findViewById(R.id.library);
        ToLibraryActivityButton.setOnClickListener(new ToLibraryActivityOnClickListener());
        wordButton = new Button[4];
        wordButton[0] = findViewById(R.id.Button1);
        wordButton[1] = findViewById(R.id.Button2);
        wordButton[2] = findViewById(R.id.Button3);
        wordButton[3] = findViewById(R.id.Button4);
        aSwitch = findViewById(R.id.EnglishRussianSwitch);
        aSwitch.setOnCheckedChangeListener(new SwitchListener());
        textView = findViewById(R.id.TextView);
        GenerationNewAnswer();
        timerTask = new ADSTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, 50000);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                timer.schedule(timerTask, 50000);
            }

        });
         ((dictionary) this.getApplication()).setSecondLogin(false);

    }

    private void instruction() {
        Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
        startActivity(intent);
    }

    private class ToAddWordActivityOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
            startActivity(intent);
        }
    }
    private class ToLibraryActivityOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, Library.class);
            startActivity(intent);
        }
    }
    private class SendOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(findViewById(R.id.ParentLayout), "backgroundColor",Color.GREEN,Color.argb(0,80,181,255)).setDuration(500);
            objectAnimator.setEvaluator(new ArgbEvaluator());
            objectAnimator.start();

            GenerationNewAnswer();
        }
    }
    private void GenerationNewAnswer() {
        allWord = sqlLiteHelper.getAllWordOnDB();
        if (!allWord.isEmpty()) {
            wordButton[0].setOnClickListener(new FalseButtonOnClickListner());
            wordButton[1].setOnClickListener(new FalseButtonOnClickListner());
            wordButton[2].setOnClickListener(new FalseButtonOnClickListner());
            wordButton[3].setOnClickListener(new FalseButtonOnClickListner());
            iterationWordCollect = new ArrayList<>();
            for (int i = 0; i <= 3 && !allWord.isEmpty(); i++) {
                int rand = (int) (Math.random() * allWord.size());
                iterationWordCollect.add(allWord.get(rand));
                allWord.remove(rand);
                if (aSwitch.isChecked())
                    wordButton[i].setText(iterationWordCollect.get(i).getEnglishWord());
                else wordButton[i].setText(iterationWordCollect.get(i).getRussianWord());
            }
            int rand = (int) (Math.random() * iterationWordCollect.size());
            wordButton[rand].setOnClickListener(new SendOnClickListener());
            if (aSwitch.isChecked())
                textView.setText(iterationWordCollect.get(rand).getRussianWord());
            else textView.setText(iterationWordCollect.get(rand).getEnglishWord());
        }
    }


    private class SwitchListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GenerationNewAnswer();
        }
    }

    private class ADSTimerTask extends TimerTask{

        @Override
        public void run() {
            wordButton[0].setOnClickListener(new ADSOnClickListener());
            wordButton[1].setOnClickListener(new ADSOnClickListener());
            wordButton[2].setOnClickListener(new ADSOnClickListener());
            wordButton[3].setOnClickListener(new ADSOnClickListener());
                }
    }

    private class ADSOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            GenerationNewAnswer();
        }
    }
    {}

    private class FalseButtonOnClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(findViewById(R.id.ParentLayout), "backgroundColor", Color.RED, Color.argb(0,80,181,255)).setDuration(500);
            objectAnimator.setEvaluator(new ArgbEvaluator());
            objectAnimator.start();
        }
    }
}
