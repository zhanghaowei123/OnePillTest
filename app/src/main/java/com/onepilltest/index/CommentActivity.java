package com.onepilltest.index;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.onepilltest.R;

public class CommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:

                break;
        }
    }
}
