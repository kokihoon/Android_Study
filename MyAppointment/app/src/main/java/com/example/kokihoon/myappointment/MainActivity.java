package com.example.kokihoon.myappointment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kokihoon.myappointment.data.MovieList;
import com.example.kokihoon.myappointment.data.SingerItem;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    String search;
    String clientId = "4h8XPYoAqJR2jFWefxLU";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "0Hopez1sls";//애플리케이션 클라이언트 시크릿값";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Button button = (Button)findViewById(R.id.button);

        gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = editText.getText().toString();
                if(search.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "검색어를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    sendRequest();
                }
            }
        });

        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());

        }
    }

    public void sendRequest() {
        try {
            String text = URLEncoder.encode(search, "UTF-8");
            String apiURL = "https://"+ AppHelper.host+ text+"&display=100"; // json 결과

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    apiURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("응답  -> " + response);
                            processResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("에러 -> " + error.getMessage());
                        }
                    }
            ) {
              @Override
              public Map<String, String> getHeaders() throws AuthFailureError {
                  Map<String, String> params = new HashMap<>();
                  params.put("X-Naver-Client-Id", clientId);
                  params.put("X-Naver-Client-Secret", clientSecret);
                  Log.d("getHeader =>", "" + params);

                  return params;
              }
            };

            request.setShouldCache(false);
            AppHelper.requestQueue.add(request);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processResponse(String response) {
        Gson gson = new Gson();

        ArrayList<SingerItem> movieLists = new ArrayList<SingerItem>();

        MovieList movieList = gson.fromJson(response, MovieList.class);
        if(movieList.items.size() == 0) {
            Toast.makeText(getApplicationContext(), "'" + search + "' 에 대한 검색이 없습니다.", Toast.LENGTH_LONG).show();
        }
        for(int i = 0; i < movieList.items.size(); i++) {
            SingerItem singerItem = new SingerItem(movieList.items.get(i).link, movieList.items.get(i).image, movieList.items.get(i).subtitle, movieList.items.get(i).title, movieList.items.get(i).pubDate, movieList.items.get(i).director, movieList.items.get(i).actor, movieList.items.get(i).userRating);
            movieLists.add(singerItem);
        }
        SingerAdapter singerAdapter = new SingerAdapter(movieLists);
        singerAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(singerAdapter);
    }


    class SingerAdapter extends RecyclerView.Adapter<ViewHolder> {

        private ArrayList<SingerItem> movies;

        SingerAdapter(ArrayList<SingerItem> items1) {
            this.movies = items1;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View itemView = layoutInflater.inflate(R.layout.singer_item, parent, false);


            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final SingerItem singerItem = movies.get(position);

            ImageView imageView = holder.imageView;
            sendImageRequest(singerItem.image, imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(singerItem.link)));
                }
            });

            RatingBar ratingBar = holder.ratingBar;
            ratingBar.setIsIndicator(true);
            ratingBar.setRating(Float.parseFloat(singerItem.userRating)/2);

            TextView titleView = holder.titleView;
            titleView.setText(singerItem.title);

            TextView pubDateView = holder.pubDateView;
            pubDateView.setText(singerItem.pubDate);

            TextView actorView = holder.actorView;
            actorView.setText(singerItem.actor);

            TextView directorView = holder.directorView;
            directorView.setText(singerItem.director);
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

        public void sendImageRequest(String str, ImageView imageView) {
            ImageLoadTask task = new ImageLoadTask(str, imageView);
            task.execute();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleView;
        public TextView pubDateView;
        public TextView actorView;
        public TextView directorView;
        public ImageView imageView;
        public RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            titleView = (TextView)itemView.findViewById(R.id.titleView);
            pubDateView = (TextView)itemView.findViewById(R.id.pubDateView1);
            actorView = (TextView)itemView.findViewById(R.id.actorView1);
            directorView = (TextView)itemView.findViewById(R.id.directorView1);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);

        }

    }
}
