package com.onepilltest.personal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.entity.Article;
import com.onepilltest.entity.Dao.ArticleDao;
import com.onepilltest.entity.Dao.focusDao;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.index.ArticleActivity;
import com.onepilltest.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class focusArticle extends BaseActivity {

    Button back;
    RecyclerView list;
    List<Article>articleList = new ArrayList<>();
    ArticleDao articleDao = new ArticleDao();
    FocusArticleAdapter focusArticleAdapter = null;
    focusDao focusDao = new focusDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_article);
        EventBus.getDefault().register(this);

        find();
        init();
        initBar(this);

    }

    private void initBar(Activity activity) {
        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
        StatusBarUtil.setStatusBarColor(activity,0x0056ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_focus_article;
    }

    public void find(){
        back = findViewById(R.id.focus_article_back);
        list = findViewById(R.id.focus_article_list);
    }

    public void init(){
        //请求数据
        Log.e("focusArticle","请求数据"+(UserBook.Code == 1 ? UserBook.NowDoctor.getId():UserBook.NowUser.getId())+UserBook.Code);
        focusDao.searchArticle(UserBook.Code == 1 ? UserBook.NowDoctor.getId():UserBook.NowUser.getId(),UserBook.Code);


    }

    //接收到数据并绑定listview刷新。

    //EventBus监听器
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getArticles(EventMessage msg){
        Log.e("focusArticle","获取数据"+msg.getCode());
        if (msg.getCode().equals("focusDao_article")){
            Log.e("focusArticle","获取数据"+msg.getJson());
            Type type = new TypeToken<List<Article>>() {
            }.getType();
            articleList.addAll(new Gson().fromJson(msg.getJson(), type));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            list.setLayoutManager(layoutManager);
            focusArticleAdapter = new FocusArticleAdapter(articleList);
            list.setAdapter(focusArticleAdapter);
            focusArticleAdapter.setOnItemClickListener(new FocusArticleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, String json) {
                    Intent intent = new Intent(focusArticle.this, ArticleActivity.class);
                    intent.putExtra("json",json);
                    startActivity(intent);
                }
            });

        }
    }

    //按钮监听器


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
