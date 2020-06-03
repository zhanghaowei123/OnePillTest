package com.onepilltest.index;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;
import com.onepilltest.entity.Dao.focusDao;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ArticleActivity extends BaseActivity {

    private Button back = null;
    private ImageView img = null;
    private TextView title = null;
    private ImageView focus = null;
    private TextView from = null;
    private TextView text = null;
    private String json = null;
    private boolean isFocus = false;
    private MyListener myListener = null;
    private focusDao dao = new focusDao();
    private Article article = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        EventBus.getDefault().register(this);
        //isFocus
        initBar(ArticleActivity.this);
        myListener = new MyListener();
        find();
        init();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_article;
    }

    private void initBar(Activity activity) {
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(activity);
        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }

    private void find(){
        back = findViewById(R.id.article_back);
        img = findViewById(R.id.article_img);
        title = findViewById(R.id.article_title);
        focus = findViewById(R.id.article_focus);
        from = findViewById(R.id.article_from);
        text = findViewById(R.id.article_text);
        back.setOnClickListener(myListener);
        focus.setOnClickListener(myListener);
    }

    private void init(){

        //获取文章数据
        Intent intent = getIntent();
        json = intent.getStringExtra("json");
        article = new Gson().fromJson(json,Article.class);
        //初始化关注图标
        if (UserBook.Code ==1){
            dao.isHave(UserBook.NowDoctor.getId(),1,4,article.getId());
        }else{
            dao.isHave(UserBook.NowUser.getId(),2,4,article.getId());
        }

        //拼接Tag图片地址
        String tag = "tag1.png";
        switch (article.getTag()){
            case "日常医学":
                tag = "tag1.png";
                break;
            case "急救知识":
                tag = "tag1.png";
                break;
            case "疾病科普":
                tag = "tag1.png";
                break;
            case "个人饮食":
                tag = "tag1.png";
                break;
        }
        article.setHeadImg(Connect.BASE_URL+"/image/"+tag);

        Glide.with(this)
                .load(article.getHeadImg())
                .into(img);
        title.setText(article.getTitle());
        text.setText(article.getContent());

        //使用SpannableString处理textview
        String msg = "本文作者："+article.getWriterName()+"丨";
        Spannable span = new SpannableString(msg);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.e("ArticleActivity","点击作者跳转");
                Intent intent1 = new Intent(ArticleActivity.this,DoctorDetailsActivity.class);
                intent1.putExtra("id",article.getUserId());
                startActivity(intent1);
            }
        };
        //点击范围
        span.setSpan(clickableSpan, 5, msg.lastIndexOf("丨"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置前景色
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#0AC3BC")),5, msg.lastIndexOf("丨") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        from.setText(span);
        from.setMovementMethod(LinkMovementMethod.getInstance());
    }


    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.article_back:
                    finish();
                    break;
                case R.id.article_focus:
//                    focus.setImageResource(isFocus ? R.drawable.notfocus:R.drawable.isfocus);
                    if (isFocus){
                        if (UserBook.Code ==1){
                            dao.del(UserBook.NowDoctor.getId(),UserBook.Code,4,article.getId());
                        }else{
                            dao.del(UserBook.NowUser.getId(),2,4,article.getId());
                        }
                    }else{
                        if (UserBook.Code ==1){
                            dao.add(UserBook.NowDoctor.getId(),1,4,article.getId());
                        }else{
                            dao.add(UserBook.NowUser.getId(),2,4,article.getId());
                        }
                    }
//                    isFocus = (isFocus ? false:true);
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUI(EventMessage msg){
        if (msg.getCode().equals("focusDao_isHave")){
            if (msg.getJson().equals("yes")){
                focus.setImageResource(R.drawable.isfocus);
                isFocus = true;
            }else {
                focus.setImageResource(R.drawable.notfocus);
                isFocus = false;
            }
        }else if (msg.getCode().equals("focusDao_del")){
            if (msg.getJson().equals("yes")){
                isFocus = false;
                Toast.makeText(getApplicationContext(),"已取消",Toast.LENGTH_SHORT).show();
                focus.setImageResource(R.drawable.notfocus);
            }else{
                isFocus = true;
                Toast.makeText(getApplicationContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();

            }
        }else if (msg.getCode().equals("focusDao_add")){
            if (msg.getJson().equals("yes")){
                isFocus = true;
                Toast.makeText(getApplicationContext(),"已关注",Toast.LENGTH_SHORT).show();
                focus.setImageResource(R.drawable.isfocus);
            }else{
                isFocus = false;
                Toast.makeText(getApplicationContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();

            }
        }
    }
}
