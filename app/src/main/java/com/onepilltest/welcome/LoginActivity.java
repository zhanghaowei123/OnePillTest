package com.onepilltest.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.onepilltest.R;
public class LoginActivity extends AppCompatActivity {
    private EditText editPhone;
    private EditText editPassword;
    private Button btnLogin;
    private ImageView imgEye;
    private TextView textRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );

        editPhone = findViewById ( R.id.edit_phone );
        editPassword = findViewById ( R.id.edit_password );
        imgEye = findViewById ( R.id.img_eye );
        btnLogin = findViewById ( R.id.btn_login );
        textRegister = findViewById ( R.id.text_register );

        textRegister.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (  );
                intent.setClass ( LoginActivity.this,RegisteredActivity.class );
                startActivity ( intent );
            }
        } );
    }
}
