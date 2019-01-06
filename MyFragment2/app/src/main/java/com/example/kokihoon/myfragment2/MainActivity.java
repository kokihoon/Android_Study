package com.example.kokihoon.myfragment2;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ListFragment fragment1;
    Viewerfragment fragment2;

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        manager = getSupportFragmentManager();

        fragment1 = (ListFragment) manager.findFragmentById(R.id.listFragment);
        fragment2 = (Viewerfragment)manager.findFragmentById(R.id.viewerFragment);

    }

    public void onImageChange(int index) {
        fragment2.setImage(index);

    }
}
