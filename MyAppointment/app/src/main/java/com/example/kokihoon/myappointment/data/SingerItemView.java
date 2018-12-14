package com.example.kokihoon.myappointment.data;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kokihoon.myappointment.R;

public class SingerItemView extends LinearLayout {

    ImageView imageView;
    TextView titleView, pubDateView, actorView, directorView;
    public SingerItemView(Context context) {
        super(context);

        init(context);
    }

    public SingerItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item, this, true);

        titleView = (TextView)findViewById(R.id.titleView);
        pubDateView = (TextView)findViewById(R.id.pubDateView);
        actorView = (TextView)findViewById(R.id.actorView);
        directorView = (TextView)findViewById(R.id.directorView);
//        imageView = (ImageView) findViewById(R.id.imageView);

    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setPuDate(String puDate) {
        pubDateView.setText(puDate);
    }
    public void setDirector(String director) {
        directorView.setText(director);
    }
    public void setActor(String actor) {
        actorView.setText(actor);
    }
//    public void setUserRating(double userRating) {
//        textView2.setText(userRating);
//    }

//    public void setImage(int resId) {
//        imageView.setImageResource(resId);
////        imageView.setImageBitmap();
//    }
}
