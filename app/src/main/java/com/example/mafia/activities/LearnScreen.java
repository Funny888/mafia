package com.example.mafia.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mafia.R;

public class LearnScreen extends AppCompatActivity {

    ShowText showText;
    TextView mMotivate;
    String[] array;
    Button mBtn_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_screen);
        mMotivate = findViewById(R.id.motivate);
        mBtn_ok = findViewById(R.id.btn_ok);
        mBtn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View c) {
                LearnScreen.this.startActivity(new Intent(LearnScreen.this, MainActivity.class));
            }
        });
        array =  getResources().getStringArray(R.array.naputstvie);
        showText = new ShowText();
        showText.execute(array.length);
    }

    class ShowText extends AsyncTask<Integer,Integer,Void>{
        @Override
        protected void onProgressUpdate(Integer... values) {
         mMotivate.append(array[values[0]]);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int k = integers[0];
            int i = 0;
            while (i<k) {
                try {
                    publishProgress(i);
                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mBtn_ok.setVisibility(View.VISIBLE);
        }
    }
}
