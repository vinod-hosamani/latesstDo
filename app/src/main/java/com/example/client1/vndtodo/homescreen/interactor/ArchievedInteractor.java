package com.example.client1.vndtodo.homescreen.interactor;

import android.content.Context;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.presenter.ArchievedPresenterInterface;
import com.example.client1.vndtodo.util.Connectivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by client1 on 9/11/2017.
 */

public class ArchievedInteractor implements ArchievedInteractorInterface {

    Context context;
    ArchievedPresenterInterface presenter;
    private DatabaseReference todoDataReference;
    String userId;

    public ArchievedInteractor(Context context,ArchievedPresenterInterface presenter)
    {
        this.context=context;
        this.presenter=presenter;
        todoDataReference = FirebaseDatabase.getInstance().getReference(Constant.key_firebase_todo);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void getNoteList( final String userId)
    {
        presenter.showProgressDialog(context.getString(R.string.loading));
        if (Connectivity.isNetworkConnected(context)) {

            todoDataReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    final List<ToDoItemModel> noteList = new ArrayList<>();
                    GenericTypeIndicator<ArrayList<ToDoItemModel>> t = new GenericTypeIndicator<ArrayList<ToDoItemModel>>() {
                    };
                    for (DataSnapshot obj : dataSnapshot.child(userId).getChildren()) {
                        List<ToDoItemModel> li;
                        li = obj.getValue(t);
                        noteList.addAll(li);
                    }
                    presenter.getNoteListSuccess(noteList);
                    presenter.hideProgressDialog();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    presenter.getNoteListFailure(context.getString(R.string.some_error));
                    presenter.hideProgressDialog();
                }
            });
        } else {
            presenter.getNoteListFailure(context.getString(R.string.no_internet));
            presenter.hideProgressDialog();
        }
    }

    @Override
    public void moveToTrash(ToDoItemModel itemModel) {
        presenter.showProgressDialog(context.getString(R.string.moving_to_trash));

        if (Connectivity.isNetworkConnected(context)) {
            todoDataReference.child(userId).child(itemModel.getStartDate())
                    .child(String.valueOf(itemModel.getNoteId()))
                    .child("deleted").setValue(true);

            presenter.moveToTrashSuccess(context.getString(R.string.moving_to_trash_success));
            presenter.hideProgressDialog();

        } else {
            presenter.moveToTrashFailure(context.getString(R.string.no_internet) + "\n"
                    + context.getString(R.string.moving_to_trash_fail));
            presenter.hideProgressDialog();
        }
    }

    @Override
    public void moveToNotes(ToDoItemModel itemModel) {
        presenter.showProgressDialog("moving to notes");

        if (Connectivity.isNetworkConnected(context)) {
            todoDataReference.child(userId).child(itemModel.getStartDate())
                    .child(String.valueOf(itemModel.getNoteId()))
                    .child("archieved").setValue(false);

            presenter.moveToNotesSuccess("moving to notes success");
            presenter.hideProgressDialog();

        } else {
            presenter.moveToNotesFailure(context.getString(R.string.no_internet) + "\n"
                    + context.getString(R.string.moving_to_trash_fail));
            presenter.hideProgressDialog();
        }
    }

    @Override
    public void moveToArchive(ToDoItemModel itemModel,boolean flageForDelete)
    {
        presenter.showProgressDialog("moving_to_archive");

        if (Connectivity.isNetworkConnected(context)) {
            if (flageForDelete)
            {
                todoDataReference.child(userId).child(itemModel.getStartDate())
                        .child(String.valueOf(itemModel.getNoteId()))
                        .child("deleted").setValue(false);
            } else {
                todoDataReference.child(userId).child(itemModel.getStartDate())
                        .child(String.valueOf(itemModel.getNoteId()))
                        .child("archieved").setValue(true);

            }
            presenter.moveToArchiveSuccess("moving to archive success");
            presenter.hideProgressDialog();

        } else {
            presenter.moveToArchiveFailure(context.getString(R.string.no_internet) + "\n"
                    + context.getString(R.string.moving_to_note_fail));
            presenter.hideProgressDialog();
        }
    }


}
