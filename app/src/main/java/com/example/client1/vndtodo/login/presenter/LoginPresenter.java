package com.example.client1.vndtodo.login.presenter;

import android.content.Context;

import com.example.client1.vndtodo.login.interactor.LoginInteractor;
import com.example.client1.vndtodo.login.interactor.LoginInteractorInterface;
import com.example.client1.vndtodo.login.ui.LoginActivityInterface;
import com.example.client1.vndtodo.registration.model.UserModel;

/**
 * Created by client1 on 9/9/2017.
 */

public class LoginPresenter implements LoginPresenterInterface {

    LoginActivityInterface viewAnInterface;

    LoginInteractorInterface interactor;

    public LoginPresenter(Context context, LoginActivityInterface viewAnInterface) {
        this.viewAnInterface = viewAnInterface;
        interactor = new LoginInteractor(context, this);
    }

    @Override
    public void getLoginResponseFromFirebase(String email, String password) {
        interactor.getLoginResponsefromFirebase(email, password);
    }

    @Override
    public void loginSuccess(UserModel model, String profilePic) {
        viewAnInterface.loginSuccess(model, profilePic);
    }

    @Override
    public void loginFailure(String message) {
        viewAnInterface.loginFailure(message);
    }

    @Override
    public void showDialog(String message) {
        viewAnInterface.showDialog(message);
    }

    @Override
    public void hideDialog() {
        viewAnInterface.hideDialog();
    }
}
