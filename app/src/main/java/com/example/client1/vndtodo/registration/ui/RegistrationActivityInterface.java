package com.example.client1.vndtodo.registration.ui;

import android.view.View;

/**
 * Created by client1 on 9/8/2017.
 */

public interface RegistrationActivityInterface extends View.OnClickListener,View.OnFocusChangeListener
{
    void registrationSuccess(String message);
    void registrationFailure(String message);
    void showProgressDialog(String message);
    void hideProgressDialog();
}
