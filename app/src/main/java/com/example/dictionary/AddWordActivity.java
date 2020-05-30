package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.sql.SQLException;

public  class AddWordActivity extends AppCompatActivity {
    TextView RussianWordTextView,EnglishWordTextView;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        getSupportActionBar().hide();
        Button ToMainActivityButton = findViewById(R.id.dictionary);
        ToMainActivityButton.setOnClickListener(new ToMainActivityOnClickListener());
        Button ToLibraryActivityButton = findViewById(R.id.library);
        ToLibraryActivityButton.setOnClickListener(new ToLibraryActivityOnClickListener());
        Button buttonOk = findViewById(R.id.AddWordButton);
        buttonOk.setOnClickListener(new ButtonAddListener());
        EnglishWordTextView = findViewById(R.id.EnglishWordText);
        RussianWordTextView = findViewById(R.id.RussianWordText);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    private class ToMainActivityOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AddWordActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    private class ToLibraryActivityOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AddWordActivity.this, Library.class);
            startActivity(intent);
        }
    }
    private class ButtonAddListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            SQLLiteHelper helper = new SQLLiteHelper(AddWordActivity.this);
            Word word = new Word();
            word.setRussianWord(String.valueOf( RussianWordTextView.getText()));
            word.setEnglishWord(String.valueOf( EnglishWordTextView.getText()));
            if (!((word.getRussianWord().equals(""))||( word.getEnglishWord().equals("")))) {
                try {
                    RussianWordTextView.setText("");
                    EnglishWordTextView.setText("");
                    helper.addWordToDB(word);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
