package com.onepilltest.index;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.util.SharedPreferencesUtil;
import com.onepilltest.util.StatusBarUtil;

import java.util.List;

public class ZxingActivity extends BaseActivity {
    private CaptureManager capture;
    private DecoratedBarcodeView bv_barcode;
    private TextView textView = null;
    SharedPreferences.Editor editor = null;
    private MyListener myListener = null;
    private String results = "扫描结果为空";
    //回调
    private BarcodeCallback barcodeCallback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            bv_barcode.resume();
            if (result != null){
                Log.e(getClass().getName(), "获取到的扫描结果是：" + result.getText());
                String myresult = result.getText();
                if (!results.equals(myresult)){
                    editor = SharedPreferencesUtil.getSharedEdit(getApplicationContext(),"zxing");
                    editor.putString("result",myresult);
                    editor.commit();
                    results = myresult;
//                    Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
                    textView.setText("扫描成功");
                    replace(new ZxingUser());
                }
                bv_barcode.resume();
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    private void replace(Fragment fragment) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.zxing_fragment,fragment);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xffffffff);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.zxing);
        find();
        init();
        capture = new CaptureManager(this, bv_barcode);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
//        capture.decode();
//        bv_barcode.decodeSingle(this.barcodeCallback);//单次扫描
        bv_barcode.decodeContinuous(barcodeCallback);//连续扫描

        initBar(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.zxing;
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

    public void find(){
        bv_barcode = (DecoratedBarcodeView) findViewById(R.id.bv_barcode);
        textView = findViewById(R.id.zxing_text);
    }

    public void init(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return bv_barcode.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    }
}
