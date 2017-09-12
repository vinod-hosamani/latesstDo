package com.example.client1.vndtodo.homescreen.interactor;

import android.content.Context;

import com.example.client1.vndtodo.homescreen.presenter.HomeScreenPresenter;
import com.example.client1.vndtodo.homescreen.presenter.HomeScreenPresenterInterface;

/**
 * Created by client1 on 9/11/2017.
 */

public class HomeScreenInteractor implements HomeScreenInteractorInterface
{
    Context context;
    HomeScreenPresenterInterface presenter;

    public HomeScreenInteractor(Context context, HomeScreenPresenter presenter)
    {
        this.context=context;
        this.presenter=presenter;
    }

}
