package com.example.client1.vndtodo.homescreen.presenter;

import android.content.Context;

import com.example.client1.vndtodo.homescreen.interactor.ToDoNotesInteractor;
import com.example.client1.vndtodo.homescreen.interactor.ToDoNotesInteractorInterface;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.ui.fragment.ToDoNotesFragmentInterface;

import java.util.List;

/**
 * Created by client1 on 9/11/2017.
 */

public class ToDoNotesPresenter implements ToDoNotesPresenterInterface {

    ToDoNotesFragmentInterface viewInterface;
    ToDoNotesInteractorInterface interactor;

    public ToDoNotesPresenter(Context context, ToDoNotesFragmentInterface viewInterface) {
        this.viewInterface = viewInterface;
        interactor = new ToDoNotesInteractor(context, this);
    }

    @Override
    public void getTodoNoteFromServer(String userId) {
        interactor.getTodoNoteFromServer(userId);
    }

    @Override
    public void getNoteSuccess(List<ToDoItemModel> noteList) {
        viewInterface.getNoteSuccess(noteList);
    }

    @Override
    public void getNoteFailure(String message) {
        viewInterface.getNoteFailure(message);
    }

    @Override
    public void showDialog(String message) {
        viewInterface.showDialog(message);
    }

    @Override
    public void hideDialog() {
        viewInterface.hideDialog();
    }

    @Override
    public void moveToTrash(ToDoItemModel itemModel) {
        interactor.moveToTrash(itemModel);
    }

    @Override
    public void moveToArchieve(ToDoItemModel itemModel) {
        interactor.moveToArchieve(itemModel);
    }

    @Override
    public void moveToNotes(ToDoItemModel itemModel, boolean flagForDelete) {
        interactor.moveToNotes(itemModel, flagForDelete);
    }

    @Override
    public void moveToTrashSuccess(String message) {
        viewInterface.moveToTrashSuccess(message);
    }

    @Override
    public void moveToTrashFailure(String message) {
        viewInterface.moveToTrashFailure(message);
    }

    @Override
    public void moveToArchiveSuccess(String message) {
        viewInterface.moveToArchiveSuccess(message);
    }

    @Override
    public void moveToArchiveFailure(String message) {
        viewInterface.moveToArchiveFailure(message);
    }

    @Override
    public void moveToNotesSuccess(String message) {
        viewInterface.moveToNotesSuccess(message);
    }

    @Override
    public void moveToNotesFailure(String message) {
        viewInterface.moveToNotesFailure(message);
    }
}
