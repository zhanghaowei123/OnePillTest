package com.onepilltest.Ease;

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
        System.out.println("error ： 没有 数据" + username);
        return null;
    }


}
