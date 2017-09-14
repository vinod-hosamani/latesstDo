package com.example.client1.vndtodo.homescreen.ui.fragment;

import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;

import java.util.List;

/**
 * Created by client1 on 9/11/2017.
 */

public interface ArchievedFragmentInterface
{
    void getNoteListSuccess(List<ToDoItemModel> noteList);
    void getNoteListFailure(String message);
    void showProgressDialog(String message);
    void hideProgressDialog();

}
