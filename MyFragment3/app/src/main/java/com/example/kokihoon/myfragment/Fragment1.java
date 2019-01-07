package com.example.kokihoon.myfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment1 extends Fragment {
    FragmentCallback callback;
    TextView textView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentCallback) {
            callback = (FragmentCallback) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        if(callback != null) {
            callback = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);
        textView = (TextView) rootView.findViewById(R.id.textView);
        Button button = (Button)rootView.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null) {
                    callback.onCommand("show", "프레그먼트1에서 전달함.");
                }
            }
        });
        return rootView;
    }

    public void onCommandFromActivity(String command, String data) {
        textView.setText(data);
    }
}
