package com.example.client1.vndtodo.login.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.homescreen.ui.activity.HomeScreenActivity;
import com.example.client1.vndtodo.login.presenter.LoginPresenter;
import com.example.client1.vndtodo.registration.model.UserModel;
import com.example.client1.vndtodo.registration.ui.RegistrationActivity;
import com.example.client1.vndtodo.session.SharedPreferenceManager;

/**
 * Created by client1 on 9/8/2017.
 */

public class LoginActivity extends AppCompatActivity implements LoginActivityInterface
{
    LoginPresenter presenter;
    AppCompatTextView txtCreateAcc;
    AppCompatEditText editTextEmailLogin;
    AppCompatEditText editTextPasswordLogin;
    AppCompatButton btnLogin;

    String email;
    String password;
    SharedPreferenceManager session;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
    }

    public void initView()
    {
        session = new SharedPreferenceManager(this);
        presenter = new LoginPresenter(this, this);

        txtCreateAcc = (AppCompatTextView) findViewById(R.id.txtCreateAccount);
        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);
        editTextEmailLogin = (AppCompatEditText) findViewById(R.id.editViewEmailLogin);
        editTextPasswordLogin = (AppCompatEditText) findViewById(R.id.editViewPassLogin);
    }
    public void setListener()
    {
        txtCreateAcc.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        editTextEmailLogin.setOnFocusChangeListener(this);
        editTextPasswordLogin.setOnFocusChangeListener(this);
    }
    @Override
    public void onClick(View view)
    {
       switch (view.getId())
       {
           case R.id.txtCreateAccount:
               Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
               startActivity(intent);
               break;

           case R.id.btnLogin:
               email = editTextEmailLogin.getText().toString();
               password = editTextPasswordLogin.getText().toString();
               if (validateLogin(email, password))
               {
                   presenter.getLoginResponseFromFirebase(email, password);
               }
               break;

       }
    }

    private boolean validateLogin(String email, String password)
    {
        boolean flag = true;
        String emailPattern = Constant.email_pattern;
        int passwordlen = password.length();
        if (email.length() == 0)
        {
            editTextEmailLogin.setError(getString(R.string.empty_field));
            flag = flag && false;

        }
        else if (password.length() == 0)
        {
            editTextPasswordLogin.setError(getString(R.string.empty_field));
            flag = flag && false;
        }
        else
        {

            if (email.matches(emailPattern))
            {
                flag = flag && true;
            }
            if (!email.matches(emailPattern))
            {
                editTextEmailLogin.requestFocus();
                editTextEmailLogin.setError(getString(R.string.invalid_email));
                flag = flag && false;
            }

            if (passwordlen < 8)
            {
                editTextPasswordLogin.requestFocus();
                editTextPasswordLogin.setError(getString(R.string.invalid_pass));
                flag = flag && false;
            }
        }
        return flag;
    }

    @Override
    public void onFocusChange(View v, boolean b)
    {
        String editTextStr;
        switch (v.getId()) {
            case R.id.editViewEmailLogin:
                editTextStr = editTextEmailLogin.getText().toString();
                if (editTextStr.length() == 0)
                {
                    editTextEmailLogin.setError(getString(R.string.empty_field));
                }
                else if (!editTextStr.matches(Constant.email_pattern))
                {
                    editTextEmailLogin.setError(getString(R.string.invalid_email));
                }
                break;

            case R.id.editViewPassLogin:
                editTextStr = editTextPasswordLogin.getText().toString();
                if (editTextStr.length() == 0) {
                    editTextPasswordLogin.setError(getString(R.string.empty_field));
                } else if (editTextStr.length() < 8) {
                    editTextPasswordLogin.setError(getString(R.string.invalid_pass));
                }
        }
    }
    public void loginToSharedPreference(UserModel model, String profilePic) {

        session.put(model,  profilePic);
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginSuccess(UserModel model, String profilePic) {
        loginToSharedPreference(model, profilePic);
    }

    @Override
    public void loginFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(String message) {
        if (!isFinishing()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    @Override
    public void hideDialog() {
        if (!isFinishing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
