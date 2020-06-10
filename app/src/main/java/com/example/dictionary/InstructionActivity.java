package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class InstructionActivity extends AppCompatActivity {
ImageButton imageButton;
private int stage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        getSupportActionBar().hide();
        imageButton = findViewById(R.id.instruction_button);
        imageButton.setOnClickListener(new ImageButtonOnClickListner());
        stage=1;
    }

    private class ImageButtonOnClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (stage){
                case 1: {
                    imageButton.setImageResource(R.drawable.imagebutton_1_image);
                    stage++;
                }
                break;
                case 2:{
                    imageButton.setImageResource(R.drawable.imagebutton_2_image);
                    stage++;
                }
                break;
                case 3:{
                    ((dictionary)getApplication()).setSecondLogin(false);
                    Intent intent = new Intent(InstructionActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}
