package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    int[] pos = {R.drawable.seoul, R.drawable.day3, R.drawable.napol, R.drawable.single, R.drawable.monster, R.drawable.nct,
                R.drawable.howlive, R.drawable.hedwiq, R.drawable.dune, R.drawable.snowfox};

    private String key = "02a0ad2f5574022eee0e2ca27ddc1389";
    private String address = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    // 영화 정보를 담을 List 변수(movieList) 선언
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                movieList.clear();

                Date date = new Date();
                date.setTime(date.getTime() - (1000 * 60 * 60 * 24));   // 현재의 날짜에서 1일을 뺀 날짜

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String dateStr = sdf.format(date);  // 20210316

                String urlAddress = address + "?key=" + key + "&targetDt=" + dateStr;

                try {
                    URL url = new URL(urlAddress);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuilder buffer = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        buffer.append(line).append("\n");
                        line = reader.readLine();
                    }

                    String jsonData = buffer.toString();

                    JSONObject obj = new JSONObject(jsonData);
                    JSONObject boxOfficeResult = obj.getJSONObject("boxOfficeResult");
                    JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");

                    for (int i = 0; i < dailyBoxOfficeList.length(); i++) {
                        JSONObject temp = dailyBoxOfficeList.getJSONObject(i);
                        String movieNm = temp.getString("movieNm");
                        String rank = temp.getString("rank");
                        int movieImage = pos[i];

                        // 이미지 리소스 ID를 변경하면서 Movie 객체 생성
                        Movie movie = new Movie(movieNm, rank, movieImage);
                        movieList.add(movie);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
