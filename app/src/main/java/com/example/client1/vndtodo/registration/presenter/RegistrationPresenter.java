package com.example.client1.vndtodo.registration.presenter;

import android.content.Context;

import com.example.client1.vndtodo.registration.interactor.RegistrationInteractor;
import com.example.client1.vndtodo.registration.interactor.RegistrationInteractorInterface;
import com.example.client1.vndtodo.registration.model.UserModel;
import com.example.client1.vndtodo.registration.ui.RegistrationActivityInterface;

/**
 * Created by client1 on 9/8/2017.
 */

public class RegistrationPresenter implements RegistrationPresenterInterface {
    RegistrationActivityInterface viewInterface;
    RegistrationInteractorInterface interactor;

    public RegistrationPresenter(Context context, RegistrationActivityInterface viewInterface) {
        this.viewInterface = viewInterface;
        interactor = new RegistrationInteractor(context, this);
    }

    @Override
    public void getRegister(UserModel model) {
        interactor.getRegisterResponse(model);
    }

    @Override
    public void registerSuccess(String message) {
        viewInterface.registrationSuccess(message);
    }

    @Override
    public void registerFailure(String message) {
        viewInterface.registrationFailure(message);
    }

    @Override
    public void showProgressDialog(String message) {
        viewInterface.showProgressDialog(message);
    }

    @Override
    public void hideProgressDialog() {
        viewInterface.hideProgressDialog();
    }
}
