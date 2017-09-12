package com.example.client1.vndtodo.homescreen.interactor;

import android.content.Context;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.presenter.ToDoNotesPresenterInterface;
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

public class ToDoNotesInteractor implements ToDoNotesInteractorInterface {

    Context context;
    ToDoNotesPresenterInterface presenter;

    /*Firebase objects*/
    FirebaseDatabase firebaseDatabase;
    DatabaseReference todoDataReference;
    ToDoItemModel itemModel;
    String userId;

    public ToDoNotesInteractor(Context context, ToDoNotesPresenterInterface presenter)
    {
        this.context = context;
        this.presenter = presenter;
        firebaseDatabase = FirebaseDatabase.getInstance();
        todoDataReference = firebaseDatabase.getReference(Constant.key_firebase_todo);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

    }


    @Override
    public void getTodoNoteFromServer( final String userId) {
        presenter.showDialog("loading");

        if (Connectivity.isNetworkConnected(context)) {

            todoDataReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    final List<ToDoItemModel> noteList = new ArrayList<>();
                    GenericTypeIndicator<ArrayList<ToDoItemModel>> t =
                            new GenericTypeIndicator<ArrayList<ToDoItemModel>>() {
                            };
                    for (DataSnapshot obj : dataSnapshot.child(userId).getChildren()) {
                        List<ToDoItemModel> li;
                        li = obj.getValue(t);
                        noteList.addAll(li);
                    }



                    presenter.getNoteSuccess(noteList);
                    presenter.hideDialog();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    presenter.getNoteFailure("some_error");
                    presenter.hideDialog();
                }
            });
        } else {

            presenter.getNoteFailure(context.getString(R.string.no_internet));
            presenter.hideDialog();
        }
    }

    @Override
    public void moveToTrash(ToDoItemModel itemModel) {
        presenter.showDialog("moving_to_trash");

        if (Connectivity.isNetworkConnected(context)) {
            todoDataReference.child(userId).child(itemModel.getStartDate())
                    .child(String.valueOf(itemModel.getNoteId()))
                    .child("deleted").setValue(true);

            presenter.moveToTrashSuccess("moving_to_trash_success");
            presenter.hideDialog();

        } else {
            presenter.moveToTrashFailure(context.getString(R.string.no_internet) + "\n"
                    + context.getString(R.string.moving_to_trash_fail));
            presenter.hideDialog();
        }

    }

    @Override
    public void moveToArchieve(ToDoItemModel itemModel) {
        presenter.showDialog(context.getString(R.string.moving_to_archieve));

        if (Connectivity.isNetworkConnected(context)) {
            todoDataReference.child(userId).child(itemModel.getStartDate())
                    .child(String.valueOf(itemModel.getNoteId()))
                    .child("archieved").setValue(true);

            presenter.moveToArchiveSuccess(context.getString(R.string.moving_to_archieve_success));
            presenter.hideDialog();

        } else {
            presenter.moveToArchiveFailure(context.getString(R.string.no_internet) + "\n"
                    + context.getString(R.string.moving_to_archieve_fail));
            presenter.hideDialog();
        }

    }

    @Override
    public void moveToNotes(ToDoItemModel itemModel, boolean flagForDelete) {
        presenter.showDialog(context.getString(R.string.moving_to_note));

        if (Connectivity.isNetworkConnected(context)) {
            if (flagForDelete) {
                todoDataReference.child(userId).child(itemModel.getStartDate())
                        .child(String.valueOf(itemModel.getNoteId()))
                        .child("deleted").setValue(false);
            } else {
                todoDataReference.child(userId).child(itemModel.getStartDate())
                        .child(String.valueOf(itemModel.getNoteId()))
                        .child("archieved").setValue(false);

            }
            presenter.moveToNotesSuccess(context.getString(R.string.moving_to_note_success));
            presenter.hideDialog();

        } else {
            presenter.moveToNotesFailure(context.getString(R.string.no_internet) + "\n"
                    + context.getString(R.string.moving_to_note_fail));
            presenter.hideDialog();
        }
    }


}
