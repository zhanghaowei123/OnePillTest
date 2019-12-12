package com.onepilltest.personal.oder;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.Button;

public class OrderTabSpec {
    private Fragment fragment;
    private Button btnChoose;
    public void setSelected(boolean b){
        if(b) {
            btnChoose.setBackgroundColor(Color.parseColor("#ff6633"));
        } else {
            btnChoose.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }
    public Button getBtnChoose() {
        return btnChoose;
    }

    public void setBtnChoose(Button btnChoose) {
        this.btnChoose = btnChoose;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
