package com.example.client1.vndtodo.homescreen.interactor;

import android.content.Context;

import com.example.client1.vndtodo.homescreen.presenter.ArchievedPresenterInterface;

/**
 * Created by client1 on 9/11/2017.
 */

public class ArchievedInteractor implements ArchievedInteractorInterface {

    Context context;
    ArchievedPresenterInterface presenter;

    public ArchievedInteractor(Context context,ArchievedPresenterInterface presenter)
    {
        this.context=context;
        this.presenter=presenter;
    }

    @Override
    public void getNoteList(String userId) {

    }
}
