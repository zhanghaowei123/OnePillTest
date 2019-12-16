package com.onepilltest.index;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.HeFeng;
import com.onepilltest.message.QuestionActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.LifestyleBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private List<Article> articles = new ArrayList<>();
    private RecyclerView recyclerView;
    private IndexAdapter indexAdapter;
    Button zxing = null;
    /*滚动字幕条*/
    com.onepilltest.others.CustomScrollBar bar = null;

    public static String text = "";

    ImageView Question;
    MyListener myListener;
    private SharedPreferences sharedPreferences;
    private OkHttpClient okHttpClient;
    private Article article;
    private ImageView imgFoundDoctor;
    private ImageView imgFoundMedicine;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_home, container, false);
        myListener = new MyListener();
        //将主线程注册成为订阅者
        EventBus.getDefault().register(this);
        okHttpClient = new OkHttpClient();
        find(view);
        initView(view);
        setArticles();
        initZxing();
        initHe();
        HeConfig.switchToFreeServerNode();
        HeConfig.init("HE1912110956121206", "828403b14fc24867bccb4494b3228294");
        HeWeather.getWeatherNow(getActivity(), new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("天气获取失败", "onError: ");
            }

            @Override
            public void onSuccess(Now date) {
                Log.i("天气获取成功\n", " Weather Now: " + new Gson().toJson(date));

                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK.getCode().equalsIgnoreCase(date.getStatus())) {
                    //此时返回数据
                    NowBase now = date.getNow();
                    setText(new HeFeng(now.getTmp(),now.getCond_txt()));


                } else {
                    //在此查看返回数据失败的原因
                    String status = date.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i("错误码", "failed code: " + code);
                }

            }
        });
        return view;
    }

    private void initZxing() {
          //6.0版本或以上需请求权限
        String[] permissions=new String[]{Manifest.permission.
                WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            requestPermissions(permissions,1);
        }
    }

    private void initHe() {
        //动态申请权限
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{android.Manifest.permission.INTERNET}, 1);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{android.Manifest.permission.READ_PHONE_STATE}, 1);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{android.Manifest.permission.ACCESS_WIFI_STATE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){

        }
    }

    private void find(View view) {
        zxing = view.findViewById(R.id.home_zxing);
        zxing.setOnClickListener(myListener);
        bar = view.findViewById(R.id.fragement_home_bar);
        Question = view.findViewById(R.id.iv_inquiry);
        Question.setOnClickListener(myListener);
        imgFoundDoctor = view.findViewById(R.id.iv_find_doctor);
        imgFoundDoctor.setOnClickListener(myListener);
        imgFoundMedicine = view.findViewById(R.id.iv_find_medicine);
        imgFoundMedicine.setOnClickListener(myListener);
    }

    private void setArticles() {
        Request request = new Request.Builder()
                .url(Connect.BASE_URL + "ArticleServlet")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String articleListStr = response.body().string();
                //定义他的派生类调用getType，真实对象
                Type type = new TypeToken<List<Article>>() {
                }.getType();
                articles.addAll((List<Article>) new Gson().fromJson(articleListStr, type));

                //在onResponse里面不能直接更新界面
                //接收到之后发送消息  通知给主线程
                EventBus.getDefault().post("文章");
            }
        });
    }

    //根据参数类型调用
    //消息的处理方法，形参类型同消息一致
    @Subscribe(threadMode = ThreadMode.MAIN)    //设置线程模式为主线程
    public void updateUI(String msg) {
        if (msg.equals("文章")) {
            //更新视图
            indexAdapter.notifyDataSetChanged();
        }
    }

    //设置每日提醒的内容

    public void setText(HeFeng heFeng) {
        String str = null;
        if(Integer.valueOf(heFeng.getTem())<0){
            str = "温度很低，请注意保暖";
        }else if(Integer.valueOf(heFeng.getTem())<10){
            str = "温度较低，请注意保暖";
        }else if (Integer.valueOf(heFeng.getTem())<20){
            str = "气温舒适，玩的开心";
        }else if (Integer.valueOf(heFeng.getTem())<50){
            str = "热死个人！！";
        }else{

        }
        text = "今天温度："+heFeng.getTem()+"----->"+str;
            bar.setText(text);
            Log.e("获取到天气数据",heFeng.getLife()+heFeng.getTem());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setBar(EventMessage msg){
        if (msg.getCode() == "bar"){
            if (msg.getJson() == "0"){
                bar.setVisibility(View.VISIBLE);//可见
            }else if (msg.getJson() == "1"){
                bar.setVisibility(View.INVISIBLE);//可见
            }
        }
    }
    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view);
        indexAdapter = new IndexAdapter(getContext(), articles, R.layout.recycle_home_item);
        //设置适配器
        recyclerView.setAdapter(indexAdapter);
        //必须调用，设置布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_inquiry:
                    Intent question_intent = new Intent(getContext(), QuestionActivity.class);
                    startActivity(question_intent);
                    break;
                case R.id.iv_commentImg:
                    Intent intent = new Intent(getContext(), CommentActivity.class);
                    startActivity(intent);
                    break;
                case R.id.iv_find_doctor:
                    Intent inent_findoctor = new Intent();
                    inent_findoctor.setClass(getContext(), FoundDoctorActivity.class);
                    startActivity(inent_findoctor);
                    break;
                case R.id.iv_find_medicine:
                    Intent intent_findpatient = new Intent();
                    intent_findpatient.setClass(getContext(), FoundPatientActivity.class);
                    startActivity(intent_findpatient);
                    break;
                case R.id.home_zxing:
                    //new IntentIntegrator(getActivity()).initiateScan();
                    new IntentIntegrator(getActivity()).setCaptureActivity(ZxingActivity.class).initiateScan();
                    break;


            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Home","Destroy");
        //bar.setVisibility(View.GONE);//不可见
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("扫码结果分析", "Cancelled");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e("扫码", "Cancelled");
                Toast.makeText(getActivity(), "扫描结果为空", Toast.LENGTH_LONG).show();
            } else {
                Log.e("扫码", "Scanned: " + result.getContents());
                Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
    }
}


