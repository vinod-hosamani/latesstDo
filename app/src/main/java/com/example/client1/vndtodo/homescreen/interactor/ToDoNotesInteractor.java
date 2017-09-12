package com.example.client1.vndtodo.homescreen.interactor;

import android.content.Context;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.presenter.ToDoNotesPresenterInterface;
import com.example.client1.vndtodo.util.Connectivity;
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

    public ToDoNotesInteractor(Context context, ToDoNotesPresenterInterface presenter)
    {
        this.context = context;
        this.presenter = presenter;
        firebaseDatabase = FirebaseDatabase.getInstance();
        todoDataReference = firebaseDatabase.getReference(Constant.key_firebase_todo);

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




}
