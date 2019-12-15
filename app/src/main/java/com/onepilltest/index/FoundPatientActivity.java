package com.onepilltest.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.onepilltest.R;
import com.onepilltest.message.QuestionActivity;
import com.onepilltest.personal.ProductActivity;

public class FoundPatientActivity extends AppCompatActivity {
    FoundPatientActivity.MyListener myListener = null;
    private ImageView imgBack;
    private EditText editSelect;
    private ImageView imgSelect;
    private LinearLayout linKid;
    private LinearLayout linOld;
    private LinearLayout linWomen;
    private LinearLayout linOther;
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.found_patient_layout );
        imgBack = findViewById ( R.id.findpatient_left );
        imgSelect = findViewById ( R.id.img_findpa_select );
        editSelect = findViewById ( R.id.findpatient_select );

        myListener = new FoundPatientActivity.MyListener();
        find();

        imgBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
                intent.setClass ( FoundPatientActivity.this,HomeFragment.class);
                startActivity(intent);
            }
        } );
        imgSelect.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
                Log.e("搜索框内容",""+editSelect.getText().toString());
                intent.putExtra("product",editSelect.getText().toString());
                intent.setClass ( FoundPatientActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        } );
    }
    private void find(){
        btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(myListener);
        btn2 = findViewById(R.id.btn_2);
        btn2.setOnClickListener(myListener);
        btn3 = findViewById(R.id.btn_3);
        btn3.setOnClickListener(myListener);
        btn4 = findViewById(R.id.btn_4);
        btn4.setOnClickListener(myListener);
        btn5 = findViewById(R.id.btn_5);
        btn5.setOnClickListener(myListener);
        btn6 = findViewById(R.id.btn_6);
        btn6.setOnClickListener(myListener);
        btn7 = findViewById(R.id.btn_7);
        btn7.setOnClickListener(myListener);
        btn8 = findViewById(R.id.btn_8);
        btn8.setOnClickListener(myListener);
        btn9 = findViewById(R.id.btn_9);
        btn9.setOnClickListener(myListener);
        btn10 = findViewById(R.id.btn_10);
        btn10.setOnClickListener(myListener);
        btn11 = findViewById(R.id.btn_11);
        btn11.setOnClickListener(myListener);
        btn12 = findViewById(R.id.btn_12);
        btn12.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent (FoundPatientActivity.this,ProductActivity.class);
            switch (v.getId()) {
                case R.id.btn_1:
                    intent.putExtra("product","布洛芬");
                    startActivity(intent);
                    break;
                case R.id.btn_2:
                    intent.putExtra("product","百服宁");
                    startActivity(intent);
                    break;
                case R.id.btn_3:
                    intent.putExtra("product","泰诺林");
                    startActivity(intent);
                    break;
                case R.id.btn_4:
                    intent.putExtra("product","速效救心丸");
                    startActivity(intent);
                    break;
                case R.id.btn_5:
                    intent.putExtra("product","芬必得");
                    startActivity(intent);
                    break;
                case R.id.btn_6:
                    intent.putExtra("product","西瓜霜");
                    startActivity(intent);
                    break;
                case R.id.btn_7:
                    intent.putExtra("product","银翘片");
                    startActivity(intent);
                    break;
                case R.id.btn_8:
                    intent.putExtra("product","云南白药");
                    startActivity(intent);
                    break;
                case R.id.btn_9:
                    intent.putExtra("product","枇杷膏");
                    startActivity(intent);
                    break;
                case R.id.btn_10:
                    intent.putExtra("product","藿香正气胶囊");
                    startActivity(intent);
                    break;
                case R.id.btn_11:
                    intent.putExtra("product","夏桑菊");
                    startActivity(intent);
                    break;
                case R.id.btn_12:
                    intent.putExtra("product","健胃消食片");
                    startActivity(intent);
                    break;
            }
        }
    }
}
