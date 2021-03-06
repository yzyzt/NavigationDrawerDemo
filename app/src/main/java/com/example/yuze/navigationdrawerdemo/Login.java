package com.example.yuze.navigationdrawerdemo;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yuze.navigationdrawerdemo.dto.SignInRequest;
import com.example.yuze.navigationdrawerdemo.dto.SignInResponse;
import com.example.yuze.navigationdrawerdemo.dto.SignUpRequest;
import com.example.yuze.navigationdrawerdemo.dto.SignUpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Login extends AppCompatActivity {

    private Button loginbtn;
    private Button registebtn;
    private Button login_cancle_btn;
    private EditText usernameEtxt;
    private EditText passwdEtxt;
    private CheckBox rememberpw;

    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginbtn =  findViewById(R.id.login_btn_login);
        registebtn = findViewById(R.id.login_btn_register);
        login_cancle_btn = findViewById(R.id.login_btn_cancle);
        usernameEtxt = findViewById(R.id.login_edit_account);
        passwdEtxt = findViewById(R.id.login_edit_pwd);
        rememberpw = findViewById(R.id.login_remember);

        loginbtn.setOnClickListener(m_login_listener);
        registebtn.setOnClickListener(m_login_listener);
        login_cancle_btn.setOnClickListener(m_login_listener);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);
    }

    View.OnClickListener m_login_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login_btn_login:
                    signIn();
                    break;
                case R.id.login_btn_register:
                    Intent intent = new Intent(Login.this,Register.class);
                    startActivity(intent);
                    break;
                case R.id.login_btn_cancle:
                    break;
            }
        }
    };

    public void signIn() {
        final SignInRequest signInRequest = SignInRequest.builder()
                .userName(usernameEtxt.getText().toString())
                .password(passwdEtxt.getText().toString())
                .build();
        try {
            final String signInRequestJson = objectMapper.writeValueAsString(signInRequest);
            Log.i("signInRequestJson",signInRequestJson);
            final String signInResponseJson = HttpUtils.post(
                    Constants.HOST + Constants.SESSIONS,signInRequestJson);
            final SignInResponse signInResponse = objectMapper.readValue(signInResponseJson, SignInResponse.class);
            Log.i("signInResponse",signInResponse.toString());
            if (signInResponse.getSession() == null){
                Toast.makeText(this,R.string.login_fail,Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,R.string.login_success,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
