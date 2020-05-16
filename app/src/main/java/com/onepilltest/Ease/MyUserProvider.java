package com.onepilltest.Ease;

import android.util.Log;

import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.HashMap;
import java.util.Map;

public class MyUserProvider implements EaseUI.EaseUserProfileProvider {

    //单例类
    private static MyUserProvider myUserProvider;
    //用户列表(包含自己和聊天对象)
    private Map<String, EaseUser> userList = new HashMap<>();

    private String ImgUrl = "";//头像(需要从服务器获取)

    @Override
    public EaseUser getUser(String username) {
        if (userList.containsKey(username))
            //有就返归这个对象。。
            return userList.get(username);
//        Log.e("mprovider,ERROR", "没有数据" + username);
        return null;
    }

    public void setUser(String username, String nickname, String imageUrl) {
        if (!userList.containsKey(username)) {
            EaseUser easeUser = new EaseUser(username);
            userList.put(username, easeUser);
        }

        EaseUser easeUser = getUser(username);
        // 环信的easerUser的父类有一个setNickname 的方法可以用来设置昵称，直接调用就好。。
        easeUser.setNickname(nickname);
        // 同理，设置一个图像的url就好，因为他加载头像是使用glide加载的
        easeUser.setAvatar(imageUrl);
    }

    // 获取单例子
    public static MyUserProvider getInstance() {
        if (myUserProvider == null) {
            myUserProvider = new MyUserProvider();
        }
        return myUserProvider;
    }

}
