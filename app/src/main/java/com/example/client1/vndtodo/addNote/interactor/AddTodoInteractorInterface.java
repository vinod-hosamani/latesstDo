package com.example.client1.vndtodo.addNote.interactor;

import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;

/**
 * Created by client1 on 9/11/2017.
 */

public interface AddTodoInteractorInterface
{
    void getResponseForAddTodoToServer(ToDoItemModel model, String userId);
    void getResponseForUpdateTodoToServer(ToDoItemModel model, String userId);
}
