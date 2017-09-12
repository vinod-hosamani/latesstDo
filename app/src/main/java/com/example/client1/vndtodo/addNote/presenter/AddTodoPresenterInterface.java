package com.example.client1.vndtodo.addNote.presenter;

import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;

/**
 * Created by client1 on 9/11/2017.
 */

public interface AddTodoPresenterInterface
{
    void getResponseForAddTodoToServer(ToDoItemModel model, String userId);

    void addTodoSuccess(String message);
    void addTodoFailure(String message);
    void showDialog(String message);
    void hideDialog();

    void getResponseForUpdateTodoToServer(ToDoItemModel model, String userId);

    void updateSuccess(String message);

    void updateFailure(String message);
}
