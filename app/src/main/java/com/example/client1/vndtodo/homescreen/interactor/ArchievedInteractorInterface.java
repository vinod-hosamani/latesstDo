package com.example.client1.vndtodo.homescreen.interactor;

import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;

/**
 * Created by client1 on 9/11/2017.
 */

public interface ArchievedInteractorInterface
{
    void getNoteList(String userId);

    void moveToTrash(ToDoItemModel itemModel);
    void moveToNotes(ToDoItemModel itemModel);
    void moveToArchive(ToDoItemModel itemModel, boolean flagForDelete);
}
