package com.example.kokihoon.myserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ServerThread thread = new ServerThread();
//                thread.start();

                Intent intent = new Intent(getApplicationContext(), ChatService.class);
                startService(intent);

            }
        });
    }

//    class ServerThread extends Thread {
//        public void run() {
//            int port = 5001;
//            try {
//                ServerSocket server = new ServerSocket(port);
//                Log.d("ServerThread", "서바가 실행됨.");
//
//                while(true) {
//                    Socket socket = server.accept();
//
//                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
//                    Object input = inputStream.readObject();
//                    Log.d("ServerThread", "input : " + input);
//
//                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//                    outputStream.writeObject(input + " from server.");
//                    outputStream.flush();
//                    Log.d("serverThread", "output  보냄.");
//
//                    socket.close();
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
 }
