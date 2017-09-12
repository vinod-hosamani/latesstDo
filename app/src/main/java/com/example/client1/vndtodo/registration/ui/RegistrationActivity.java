package com.example.client1.vndtodo.registration.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.login.ui.LoginActivity;
import com.example.client1.vndtodo.registration.model.UserModel;
import com.example.client1.vndtodo.registration.presenter.RegistrationPresenter;

/**
 * Created by client1 on 9/8/2017.
 */

public class RegistrationActivity extends AppCompatActivity implements RegistrationActivityInterface {

    UserModel userModel;

    AppCompatTextView txtAlreadyAcc;
    AppCompatEditText editTextFullname;
    AppCompatEditText editTextEmail;
    AppCompatEditText editTextMobile;
    AppCompatEditText editTextPassword;
    AppCompatButton btnRegistration;

    RegistrationPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
        setListener();

    }

    public void initView()
    {
        presenter = new RegistrationPresenter(this, this);
        txtAlreadyAcc = (AppCompatTextView) findViewById(R.id.txtAlreadyAccount);

        editTextFullname = (AppCompatEditText) findViewById(R.id.editViewFullName);
        editTextEmail = (AppCompatEditText) findViewById(R.id.editViewEmail);
        editTextMobile = (AppCompatEditText) findViewById(R.id.editViewMobile);
        editTextPassword = (AppCompatEditText) findViewById(R.id.editViewPass);

        btnRegistration = (AppCompatButton) findViewById(R.id.btnRegistration);

        editTextFullname.setOnFocusChangeListener(this);
        editTextEmail.setOnFocusChangeListener(this);
        editTextMobile.setOnFocusChangeListener(this);
        editTextPassword.setOnFocusChangeListener(this);


    }

    public void setListener()
    {
        txtAlreadyAcc.setOnClickListener(this);
        btnRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);

        switch (view.getId())
        {
            case R.id.btnRegistration:
                userModel = new UserModel();
                userModel.setFullname(editTextFullname.getText().toString());
                userModel.setEmail(editTextEmail.getText().toString());
                userModel.setMobile(editTextMobile.getText().toString());
                userModel.setPassword(editTextPassword.getText().toString());

                if (validateReg(userModel))
                {
                    presenter.getRegister(userModel);
                    startActivity(intent);
                }
                break;
            case R.id.txtAlreadyAccount:
                startActivity(intent);
                break;
        }
    }

    private boolean validateReg(UserModel userModel)
    {
        boolean flag = true, toast = true;
        String mobilePattern = Constant.mobilepattern;
        String emailPattern = Constant.email_pattern;
        int passwordlen = userModel.getPassword().length();
        if (userModel.getFullname().length() == 0 || userModel.getMobile().length() == 0
                || userModel.getEmail().length() == 0 || userModel.getPassword().length() == 0) {
            editTextFullname.setError(getString(R.string.empty_field));
            editTextEmail.setError(getString(R.string.empty_field));
            editTextMobile.setError(getString(R.string.empty_field));
            editTextPassword.setError(getString(R.string.empty_field));
            flag = flag && false;

        } else {

            if (userModel.getFullname().length() > 25) {
                editTextFullname.setError(getString(R.string.invalid_name));
                flag = flag && false;
            }

            if (userModel.getEmail().matches(emailPattern)) {
                flag = flag && true;
            }
            if (toast) {
                if (!userModel.getEmail().matches(emailPattern)) {
                    editTextEmail.requestFocus();
                    editTextEmail.setError(getString(R.string.invalid_email));
                    toast = false;
                    flag = flag && false;
                }
            }

            if (userModel.getMobile().matches(mobilePattern)) {
                flag = flag && true;
            }

            if (toast) {
                if (!userModel.getMobile().matches(mobilePattern))
                {
                    editTextMobile.requestFocus();
                    editTextMobile.setError(getString(R.string.invalid_number));
                    toast = false;
                    flag = flag && false;
                }
            }

            if (toast) {
                if (passwordlen < 8) {
                    editTextPassword.requestFocus();
                    editTextPassword.setError(getString(R.string.invalid_pass));
                    flag = flag && false;
                }
            }
        }
        return flag;
    }

    @Override
    public void registrationSuccess(String message) {
        if (!isFinishing())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registrationFailure(String message) {
        if (!isFinishing())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    ProgressDialog mProgressDialog;

    @Override
    public void showProgressDialog(String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.hide();
    }


    @Override
    public void onFocusChange(View v, boolean b)
    {
        AppCompatEditText editTextValidate = new AppCompatEditText(this);

        switch (v.getId())
        {
            case R.id.editViewFullName:
                editTextValidate = editTextFullname;
                break;

            case R.id.editViewEmail:
                editTextValidate = editTextEmail;
                break;

            case R.id.editViewMobile:
                editTextValidate = editTextMobile;
                break;

            case R.id.editViewPass:
                editTextValidate = editTextPassword;
                break;
        }

        if (editTextValidate.hasFocus())
        {
            switch (editTextValidate.getId())
            {
                case R.id.editViewFullName:
                    if (editTextValidate.getText().toString().length() == 0)
                    {
                        editTextFullname.setError(getString(R.string.empty_field));
                    }
                    editTextValidate.addTextChangedListener(new TextWatcher()
                    {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after)
                        {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count)
                        {
                            if (s.length() == 0)
                            {
                                editTextFullname.setError(getString(R.string.empty_field));
                            }
                            else if (s.length() > 25 || s.length() < 6)
                            {
                                editTextFullname.setError(getString(R.string.invalid_name));
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s)
                        {

                        }
                    });
                    break;



                case R.id.editViewEmail:
                    if (editTextValidate.getText().toString().length() == 0){
                        editTextEmail.setError(getString(R.string.empty_field));
                    }
                    editTextValidate.addTextChangedListener(new TextWatcher()
                    {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after)
                        {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count)
                        {
                            if (s.length() == 0)
                            {
                                editTextEmail.setError(getString(R.string.empty_field));
                            }
                            else if (!s.toString().matches(Constant.email_pattern))
                            {
                                editTextEmail.setError(getString(R.string.invalid_email));
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    break;

                case R.id.editViewMobile:
                    if (editTextValidate.getText().toString().length() == 0){
                        editTextMobile.setError(getString(R.string.empty_field));
                    }
                    editTextValidate.addTextChangedListener(new TextWatcher()
                    {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after)
                        {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count)
                        {
                            if (s.length() == 0)
                            {
                                editTextMobile.setError(getString(R.string.empty_field));
                            }
                            else if (!s.toString().matches(Constant.mobilepattern))
                            {
                                editTextMobile.setError(getString(R.string.invalid_number));
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s)
                        {

                        }
                    });
                    break;

                case R.id.editViewPass:
                    if (editTextValidate.getText().toString().length() == 0)
                    {
                        editTextPassword.setError(getString(R.string.empty_field));
                    }
                    editTextValidate.addTextChangedListener(new TextWatcher()
                    {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after)
                        {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() == 0) {
                                editTextPassword.setError(getString(R.string.empty_field));
                            } else if (s.length() < 8) {
                                editTextPassword.setError(getString(R.string.invalid_pass));
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    break;
            }
        }

    }
}
