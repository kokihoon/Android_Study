package com.example.kokihoon.myhttp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Handler handler = new Handler();
    String urlStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlStr = editText.getText().toString();
                RequestThread thread = new RequestThread();
                thread.start();
            }
        });

    }

    class RequestThread extends Thread {
        public void run() {
            try {
                URL url = new URL(urlStr);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if(conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    int resCode = conn.getResponseCode();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;

                    while(true) {
                        line = reader.readLine();
                        if(line == null) {
                            break;
                        }

                        println(line);
                    }

                    reader.close();
                    conn.disconnect();
//                    if(resCode == HttpURLConnection.HTTP_OK) {
//
//                    }
                }
            } catch (Exception e) {

            }
        }
    }


    public void println(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");

            }
        });
    }
}
