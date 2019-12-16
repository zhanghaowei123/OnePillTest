package com.onepilltest.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onepilltest.R;
import com.onepilltest.index.DoctorDetailsActivity;
import com.onepilltest.index.MedicineDao;

public class SettingForUsActivity extends AppCompatActivity {

    Button back = null;
    MyListener myListener = null;
    Button ceshi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting__forus);

        myListener = new MyListener();
        find();
    }

    private void find() {
        back = findViewById(R.id.setting_forUs_back);
        back.setOnClickListener(myListener);
        ceshi = findViewById(R.id.thisisceshi);
        ceshi.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting_forUs_back:
                    finish();
                    break;
                case R.id.thisisceshi:
                    Intent intent = new Intent(SettingForUsActivity.this, DoctorDetailsActivity.class);
                    intent.putExtra("id",18);
                    startActivity(intent);
                    /*MedicineDao dao = new MedicineDao();
                    dao.searchMedicineByName("布洛芬");*/
            }
        }
    }
}
