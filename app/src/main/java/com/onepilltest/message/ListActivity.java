package com.onepilltest.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Inquiry;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity {

    private String Nickname;
    //List详情页
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle extras = getIntent().getExtras();
        Gson gson = new Gson();
        Inquiry inquiry = gson.fromJson(extras.getString("json"),Inquiry.class);

        TextView title = findViewById(R.id.tv_lv_title);
        title.setText(inquiry.getTitle());
        TextView content = findViewById(R.id.tv_lv_content);
        content.setText(inquiry.getContent());
        TextView time = findViewById(R.id.tv_lv_time);
        time.setText(inquiry.getTime());

        TextView name = findViewById(R.id.tv_lv_name);
        int userId = inquiry.getUserId();
        searchNickname(userId);
        name.setText(Nickname);
    }
    public void searchNickname(int userId){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request= new Request.Builder().url(Connect.BASE_URL+"GetNameServlet?userId="+userId).build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("null","null");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Nickname = response.body().toString();
            }
        });
    }
}
