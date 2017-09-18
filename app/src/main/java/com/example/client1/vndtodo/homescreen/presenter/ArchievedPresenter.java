package com.example.client1.vndtodo.homescreen.presenter;

import android.content.Context;

import com.example.client1.vndtodo.homescreen.interactor.ArchievedInteractor;
import com.example.client1.vndtodo.homescreen.interactor.ArchievedInteractorInterface;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.ui.fragment.ArchievedFragment;
import com.example.client1.vndtodo.homescreen.ui.fragment.ArchievedFragmentInterface;

import java.util.List;

/**
 * Created by client1 on 9/11/2017.
 */

public class ArchievedPresenter implements ArchievedPresenterInterface {

    Context context;
    ArchievedFragmentInterface viewInterface;
    ArchievedInteractorInterface interactor;

    public ArchievedPresenter(Context context, ArchievedFragmentInterface viewInterface) {
        this.context = context;
        this.viewInterface = viewInterface;
        interactor = new ArchievedInteractor(context, this);
    }

    @Override
    public void getNoteList(String userId) {
        interactor.getNoteList(userId);
    }

    @Override
    public void getNoteListSuccess(List<ToDoItemModel> noteList) {
        viewInterface.getNoteListSuccess(noteList);
    }

    @Override
    public void getNoteListFailure(String message) {
        viewInterface.getNoteListFailure(message);
    }

    @Override
    public void showProgressDialog(String message) {
        viewInterface.showProgressDialog(message);
    }

    @Override
    public void hideProgressDialog() {
        viewInterface.hideProgressDialog();
    }

    @Override
    public void moveToTrash(ToDoItemModel itemModel) {
        interactor.moveToTrash(itemModel);
    }

    @Override
    public void moveToNotes(ToDoItemModel itemModel) {
        interactor.moveToNotes(itemModel);
    }

    @Override
    public void moveToArchive(ToDoItemModel itemModel, boolean flagForDelete) {
        interactor.moveToArchive(itemModel, flagForDelete);
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
    public void moveToNotesSuccess(String message) {
        viewInterface.moveToNotesSuccess(message);
    }

    @Override
    public void moveToNotesFailure(String message) {
        viewInterface.moveToNotesFailure(message);
    }

    @Override
    public void moveToArchiveSuccess(String message) {
        viewInterface.moveToArchiveSuccess(message);
    }

    @Override
    public void moveToArchiveFailure(String message) {
        viewInterface.moveToArchiveFailure(message);
    }
}
