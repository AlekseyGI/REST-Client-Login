package com.example.restclientlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restclientlogin.login.ApiClient;
import com.example.restclientlogin.login.LoginRequest;
import com.example.restclientlogin.login.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public String authToken = null;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    EditText username;
    EditText password;
    Button btnLogin;
    TextView printText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.name_editText);
        password = findViewById(R.id.password_editText);
        btnLogin = findViewById(R.id.login_button);
        printText = findViewById(R.id.textView);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Username / Password Required", Toast.LENGTH_SHORT).show();
                }else{
                    //proceed login
                    login(getAuthToken());
                }
            }
        });
    }

    public void login(String authToken){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService(authToken).userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    setAuthToken(response.toString());
                    printText.setText(response.toString());
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    setAuthToken(response.toString());
                    printText.setText(response.toString());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                printText.setText(t.getLocalizedMessage());
            }
        });

    }

}