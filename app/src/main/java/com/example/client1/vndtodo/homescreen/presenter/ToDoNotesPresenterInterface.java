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

    void moveToTrash(ToDoItemModel itemModel);
    void moveToArchieve(ToDoItemModel itemModel);
    void moveToNotes(ToDoItemModel itemModel,boolean flagForDelete);

    void moveToTrashSuccess(String message);
    void moveToTrashFailure(String message);

    void moveToArchiveSuccess(String message);
    void moveToArchiveFailure(String message);

    void moveToNotesSuccess(String message);
    void moveToNotesFailure(String message);

}
