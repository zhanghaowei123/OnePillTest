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
                Log.e("去聊天页面", conversation.conversationId().toString());
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
        StatusBarUtil.setStatusBarColor(getActivity(), 0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);

    }

    private EMMessageListener emMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
//            //接收到对方的消息
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
