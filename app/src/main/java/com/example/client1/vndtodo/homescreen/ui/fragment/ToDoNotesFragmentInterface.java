package com.example.client1.vndtodo.homescreen.ui.fragment;

import android.support.v7.widget.SearchView;

import com.example.client1.vndtodo.adapter.TodoItemAdapter;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;

import java.util.List;

/**
 * Created by client1 on 9/11/2017.
 */

public interface ToDoNotesFragmentInterface extends TodoItemAdapter.OnNoteClickListener,SearchView.OnQueryTextListener
{
    void getNoteSuccess(List<ToDoItemModel> noteList);
    void getNoteFailure(String message);
    void showDialog(String message);
    void hideDialog();
}
