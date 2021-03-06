package com.example.client1.vndtodo.login.ui;

import android.view.View;

import com.example.client1.vndtodo.registration.model.UserModel;

/**
 * Created by client1 on 9/9/2017.
 */

public interface LoginActivityInterface  extends  View.OnClickListener, View.OnFocusChangeListener
{
    void loginSuccess(UserModel model, String profilePic);
    void loginFailure(String message);
    void showDialog(String message);
    void hideDialog();
}
