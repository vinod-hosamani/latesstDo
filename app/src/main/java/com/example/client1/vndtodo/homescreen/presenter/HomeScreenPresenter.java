package com.example.client1.vndtodo.homescreen.presenter;

import android.content.Context;

import com.example.client1.vndtodo.homescreen.interactor.HomeScreenInteractor;
import com.example.client1.vndtodo.homescreen.ui.activity.HomeScreenActivityInterface;

/**
 * Created by client1 on 9/11/2017.
 */

public class HomeScreenPresenter implements HomeScreenPresenterInterface {

    HomeScreenActivityInterface viewInterface;
    HomeScreenInteractor interactor;

    public HomeScreenPresenter(Context context, HomeScreenActivityInterface viewInterface)
    {
        this.viewInterface=viewInterface;
        interactor=new HomeScreenInteractor(context,this);

    }

    @Override
    public void showProgressDailogue(String message) {

    }

    @Override
    public void hideProgressDailogue() {

    }
}
