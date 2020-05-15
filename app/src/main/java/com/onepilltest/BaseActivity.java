package com.onepilltest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.onepilltest.util.KeyboardUtils;
import com.onepilltest.util.StatusBarUtil;

/**
 * 封装Activity用于管理所有Activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    /***是否显示标题栏*/
    private  boolean isshowtitle = true;
    /***是否显示标题栏*/
    private  boolean isshowstate = true;
    /***封装toast对象**/
    private static Toast toast;
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(intiLayout());
        StatusBarUtil.setTranslucentStatus(this);

//        //初始化状态栏
//        {
//            //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding,在BaseActivity设置没用，要等setContentView里的initLayout初始化完成之后才能生效
////            StatusBarUtil.setRootViewFitsSystemWindows(this,true);
//            //设置状态栏透明
////            StatusBarUtil.setTranslucentStatus(this);
//            //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
//            //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
//            if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
//                //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
//                //这样半透明+白=灰, 状态栏的文字能看得清
//                StatusBarUtil.setStatusBarColor(this,0x55000000);
//            }
//        }
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();


    public static String Help(){
        String str = "\n-->\n";
        str += "丨------------------------------\n";
        str += "丨设置屏幕横竖屏切换  :setScreenRoate(Boolean screenRoate) true  竖屏  false  横屏"+"\n";
        str += "丨显示长toast        :toastLong(String msg) String msg"+"\n";
        str += "丨显示短toast        :toastShort(String msg)"+"\n";
        str += "丨页面跳转           :startActivity(Class<?> clz)"+"\n";
        str += "丨携带数据的页面跳转  :startActivity(Class<?> clz, Bundle bundle)"+"\n";
        str += "丨* * *设置EditView和Activity的互动* * *"+"\n";
        str += "丨传入EditText的Id   :重写hideSoftByEditViewIds()"+"\n";
        str += "丨传入要过滤的View   :filterViewByIds()"+"\n";
        str += "丨* * * * * * * * * * * * * * * * * * *"+"\n";
        str += "丨-----------------------------\n";
        Log.e("BaseActivity_Help",str);
        return str;
    }


    /**
     * 设置屏幕横竖屏切换
     * @param screenRoate true  竖屏     false  横屏
     */
    private void setScreenRoate(Boolean screenRoate) {
        if (screenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏模式
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    /**
     * 显示长toast
     * @param msg
     */
    public void toastLong(String msg){
        if (null == toast) {
            toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }


    /**
     * 显示短toast
     * @param msg
     */
    public void toastShort(String msg){
        if (null == toast) {
            toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }


    /**
     * [页面跳转]
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }


    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }



    /**
     * 以下是关于软键盘的处理
     */

    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    public void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText et = (EditText) v;
            for (int id : ids) {
                if (et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) {
            return false;
        }
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev)) {
                return super.dispatchTouchEvent(ev);
            }
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0) {
                return super.dispatchTouchEvent(ev);
            }
            View v = getCurrentFocus();
            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                KeyboardUtils.hideInputForce(this);
                clearViewFocus(v, hideSoftByEditViewIds());
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }


    /*实现案例===============================================================================================*/

//    @Override
//    public int[] hideSoftByEditViewIds() {
//        int[] ids = {R.id.et_company_name, R.id.et_address};
//        return ids;
//    }
//
//    @Override
//    public View[] filterViewByIds() {
//        View[] views = {mEtCompanyName, mEtAddress};
//        return views;
//    }



}
