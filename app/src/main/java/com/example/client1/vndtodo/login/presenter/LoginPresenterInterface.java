package com.example.client1.vndtodo.login.presenter;

import com.example.client1.vndtodo.registration.model.UserModel;

/**
 * Created by client1 on 9/9/2017.
 */

public interface LoginPresenterInterface
{
    void getLoginResponseFromFirebase(String email, String password);

    void loginSuccess(UserModel model, String profilePic);
    void loginFailure(String message);
    void showDialog(String message);
    void hideDialog();
}
