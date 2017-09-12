package com.example.client1.vndtodo.addNote.presenter;

import android.content.Context;

import com.example.client1.vndtodo.addNote.interactor.AddTodoInteractor;
import com.example.client1.vndtodo.addNote.interactor.AddTodoInteractorInterface;
import com.example.client1.vndtodo.addNote.ui.AddToDoViewInterface;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;

/**
 * Created by client1 on 9/11/2017.
 */

public class AddTodoPresenter implements AddTodoPresenterInterface {
    Context context;
    AddToDoViewInterface viewInterface;
    AddTodoInteractorInterface interactor;

    public AddTodoPresenter(Context context, AddToDoViewInterface viewInterface) {
        this.context = context;
        this.viewInterface = viewInterface;

        interactor = new AddTodoInteractor(context, this);
    }

    @Override
    public void getResponseForAddTodoToServer(ToDoItemModel model, String userId) {
        interactor.getResponseForAddTodoToServer(model, userId);
    }

    @Override
    public void addTodoSuccess(String message) {
        viewInterface.addTodoSuccess(message);
    }

    @Override
    public void addTodoFailure(String message) {
        viewInterface.addTodoFailure(message);
    }

    @Override
    public void showDialog(String message) {
        viewInterface.showDialog(message);
    }

    @Override
    public void hideDialog() {
        viewInterface.hideDialog();
    }

    @Override
    public void getResponseForUpdateTodoToServer(ToDoItemModel model, String userId) {
        interactor.getResponseForUpdateTodoToServer(model, userId);
    }

    @Override
    public void updateSuccess(String message) {
        viewInterface.updateSuccess(message);
    }

    @Override
    public void updateFailure(String message) {
        viewInterface.updateFailure(message);
    }
}
