package com.example.kokihoon.myappointment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kokihoon.myappointment.data.MovieList;
import com.example.kokihoon.myappointment.data.SingerItem;
import com.example.kokihoon.myappointment.data.SingerItemView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    String search;
    String clientId = "4h8XPYoAqJR2jFWefxLU";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "0Hopez1sls";//애플리케이션 클라이언트 시크릿값";
    ListView listView;
    SingerAdapter adapter;
    MovieList movieList;
    ImageView imageView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        imageView = (ImageView)findViewById(R.id.imageView);
        listView = (ListView)findViewById(R.id.listView);



        adapter = new SingerAdapter();

        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = editText.getText().toString();
                System.out.println(search + "123123123");

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

    public void sendImageRequest(String url) {

        ImageLoadTask task = new ImageLoadTask(url, imageView);
        task.execute();
    }

    public void sendRequest() {
        try {
            String text = URLEncoder.encode(search, "UTF-8");
            System.out.println("123123123 " + text);
            String apiURL = "https://"+ AppHelper.host+ text+"&display=100"; // json 결과\

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    apiURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("응답  -> " + response);
                            processResponse(response, search);
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

    public void processResponse(String response, String text) {
        Gson gson = new Gson();

        MovieList movieList = gson.fromJson(response, MovieList.class);
        if(movieList.items.size() == 0) {
            Toast.makeText(getApplicationContext(), "'" + search + "' 에 대한 검색이 없습니다.", Toast.LENGTH_LONG).show();
        }
        for(int i = 0; i < movieList.items.size(); i++) {
            adapter.addItem(new SingerItem(movieList.items.get(i).getLink(), movieList.items.get(i).getImage(), movieList.items.get(i).getSubtitle(), movieList.items.get(i).getTitle(), movieList.items.get(i).getPuDate(), movieList.items.get(i).getDirector(), movieList.items.get(i).getActor(), movieList.items.get(i).getUserRating()));
        }
        listView.setAdapter(adapter);
    }


    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }


        public void addItem(SingerItem item) {
            items.add(item);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            SingerItemView view = null;

            if(convertView == null) {
                view = new SingerItemView(getApplicationContext());
            }else {
                view = (SingerItemView)convertView;
            }

//            sendImageRequest(items.get(position).getImage());
            view.setTitle(items.get(position).getTitle());
            view.setActor(items.get(position).getActor());
            view.setDirector(items.get(position).getDirector());
            view.setPuDate(items.get(position).getPuDate());



            return view;
        }
    }
}
