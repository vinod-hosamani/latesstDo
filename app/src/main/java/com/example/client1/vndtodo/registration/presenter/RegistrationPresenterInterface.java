package com.example.client1.vndtodo.registration.presenter;

import com.example.client1.vndtodo.registration.model.UserModel;

/**
 * Created by client1 on 9/8/2017.
 */

public interface RegistrationPresenterInterface
{
    void getRegister(UserModel model);
    void registerSuccess(String message);
    void registerFailure(String message);
    void showProgressDialog(String message);
    void hideProgressDialog();
}
