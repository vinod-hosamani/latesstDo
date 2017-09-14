package com.example.client1.vndtodo.homescreen.presenter;

import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;

import java.util.List;

/**
 * Created by client1 on 9/11/2017.
 */

public interface ArchievedPresenterInterface
{

    void getNoteList(String userId);

    void getNoteListSuccess(List<ToDoItemModel> noteList);
    void getNoteListFailure(String message);
    void showProgressDialog(String message);
    void hideProgressDialog();
}
