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

    void moveToTrash(ToDoItemModel itemModel);
    void moveToNotes(ToDoItemModel itemModel);
    void moveToArchive(ToDoItemModel itemModel ,boolean flagForDelete);

    void moveToTrashSuccess(String message);
    void moveToTrashFailure(String message);

    void moveToNotesSuccess(String message);
    void moveToNotesFailure(String message);

    void moveToArchiveSuccess(String message);
    void moveToArchiveFailure(String message);


}
