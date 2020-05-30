package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.sql.SQLException;
import java.util.ArrayList;

public class Library extends AppCompatActivity {
    private AdView mAdView;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        getSupportActionBar().hide();
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Button ToAddWordActivityButton = findViewById(R.id.AddWordAC);
        ToAddWordActivityButton.setOnClickListener(new ToAddWordActivityOnClickListener());
        Button ToMainActivityButton = findViewById(R.id.dictionary);
        ToMainActivityButton.setOnClickListener(new ToMainActivityOnClickListener());
        Graphics();
    }
    private class ToAddWordActivityOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Library.this, AddWordActivity.class);
            startActivity(intent);
        }
    }
    private class ToMainActivityOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Library.this, MainActivity.class);
            startActivity(intent);
        }
    }
    private class DeleteButtonOnClickListner implements OnClickListener {
        private int id;
        private LinearLayout linearLayout;
        public DeleteButtonOnClickListner(int Id,LinearLayout linearLayout1){
            super();
            id=Id;
            linearLayout=linearLayout1;
        }
        @Override
        public void onClick(View v) {
            try {
                SQLLiteHelper sqlLiteHelper = new SQLLiteHelper(Library.this);
                sqlLiteHelper.deleteWordOnDB(id);
                Graphics();
            } catch (SQLException e) {
                e.printStackTrace();
            }
         //   Intent intent = new Intent(Library.this, Library.class);
         //   startActivity(intent);
        }
    }
    public void Graphics(){
        SQLLiteHelper sqlLiteHelper = new SQLLiteHelper(this);
        ArrayList<Word> AllWord = sqlLiteHelper.getAllWordOnDB();
        scrollView = findViewById(R.id.scroll);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,2);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,3);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (Word word : AllWord){
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            TextView EnglishView = new TextView(this);
            EnglishView.setText(word.getEnglishWord());
            EnglishView.setLayoutParams(params2);
            TextView RussianView = new TextView(this);
            RussianView.setText(word.getRussianWord());
            RussianView.setLayoutParams(params2);
            Button button = new Button(this);
            button.setText("X");
            button.setLayoutParams(params3);
            button.setOnClickListener(new DeleteButtonOnClickListner(word.getId(),layout));
            layout.addView(EnglishView);
            layout.addView(RussianView);
            layout.addView(button);
            linearLayout.addView(layout);
        }
        scrollView.addView(linearLayout);

    }

}
