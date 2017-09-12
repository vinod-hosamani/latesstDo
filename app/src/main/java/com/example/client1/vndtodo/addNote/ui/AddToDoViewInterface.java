package com.example.client1.vndtodo.addNote.ui;

import android.view.View;

/**
 * Created by client1 on 9/11/2017.
 */

public interface AddToDoViewInterface extends View.OnClickListener {

    void addTodoSuccess(String message);
    void addTodoFailure(String message);
    void showDialog(String message);
    void hideDialog();

    void updateSuccess(String message);

    void updateFailure(String message);
}
