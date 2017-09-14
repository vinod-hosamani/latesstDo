package com.example.client1.vndtodo.homescreen.presenter;

import android.content.Context;

import com.example.client1.vndtodo.homescreen.interactor.ArchievedInteractor;
import com.example.client1.vndtodo.homescreen.interactor.ArchievedInteractorInterface;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.ui.fragment.ArchievedFragment;
import com.example.client1.vndtodo.homescreen.ui.fragment.ArchievedFragmentInterface;

import java.util.List;

/**
 * Created by client1 on 9/11/2017.
 */

public class ArchievedPresenter implements ArchievedPresenterInterface {

    Context context;
    ArchievedFragmentInterface viewInterface;
    ArchievedInteractorInterface interactor;

    public ArchievedPresenter(Context context,ArchievedFragmentInterface viewInterface)
    {
        this.context=context;
        this.viewInterface=viewInterface;
        interactor=new ArchievedInteractor(context,this);
    }

    @Override
    public void getNoteList(String userId) {

    }

    @Override
    public void getNoteListSuccess(List<ToDoItemModel> noteList) {

    }

    @Override
    public void getNoteListFailure(String message) {

    }

    @Override
    public void showProgressDialog(String message) {

    }

    @Override
    public void hideProgressDialog() {

    }
}
