package com.onepilltest.message;

import android.content.Intent;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.onepilltest.Ease.MyUserProvider;
import com.onepilltest.util.StatusBarUtil;

import java.util.List;

public class MessageFragment extends EaseConversationListFragment {



    @Override
    protected void initView() {
        super.initView();
//        initBar();
        //跳转到会话详情页
        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                //传递参数
                Log.e("嘿嘿", conversation.conversationId().toString());
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                if (conversation.getType() == EMConversation.EMConversationType.GroupChat) {
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                }
                startActivity(intent);
            }

        });
//        conversationList.clear();
        //监听会话消息
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
    }

    private void initBar() {
        //设置状态栏透明
//        StatusBarUtil.setTranslucentStatus(getActivity());
        //设置状态栏paddingTop
//        StatusBarUtil.setRootViewFitsSystemWindows(getActivity(),true);
        //设置状态栏颜色
        StatusBarUtil.setStatusBarColor(getActivity(),0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),false);

    }

    private EMMessageListener emMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //接收到对方的消息
            for (EMMessage message : list) {
                String imUserName = message.getStringAttribute("ImUserName", "");
                String imNickName = message.getStringAttribute("ImNickName", "");
                String imImageUrl = message.getStringAttribute("ImImageUrl", "");

                Log.e("对方用户昵称", imNickName);
                Log.e("对方用户名", imUserName);
                Log.e("对方用户头像", imImageUrl);
                //设置对方信息（如果没这段代码，我们手机将会不显示对方的头像和昵称）
                //通过MyUserProvider设置对方信息(每次和用户聊天必须传递给用户自己的信息，
                // 然后对方将会保存这些信息到MyUserProvider中，再保存到数据库中)
                MyUserProvider.getInstance().setUser(imUserName, imNickName, imImageUrl);
                //保存到内存
                //保存到数据库
            }
            //设置数据
            EaseUI.getInstance().getNotifier().notify(list);
            //刷新页面
            refresh();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };
}
