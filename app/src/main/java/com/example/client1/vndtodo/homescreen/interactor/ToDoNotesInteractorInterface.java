package com.example.client1.vndtodo.homescreen.interactor;

import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;

/**
 * Created by client1 on 9/11/2017.
 */

public interface ToDoNotesInteractorInterface
{
    void getTodoNoteFromServer(String userId);

    void moveToTrash(ToDoItemModel itemModel);
    void moveToArchieve(ToDoItemModel itemModel);
    void moveToNotes(ToDoItemModel itemModel,boolean flagForDelete);
}
