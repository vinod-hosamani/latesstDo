package com.example.client1.vndtodo.homescreen.presenter;

import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;

import java.util.List;

/**
 * Created by client1 on 9/11/2017.
 */

public interface ToDoNotesPresenterInterface
{
    void getTodoNoteFromServer(String userId);
    void getNoteSuccess(List<ToDoItemModel> noteList);
    void getNoteFailure(String message);
    void showDialog(String message);
    void hideDialog();

}
