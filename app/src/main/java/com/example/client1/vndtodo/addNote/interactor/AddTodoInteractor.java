package com.example.client1.vndtodo.addNote.interactor;

import android.content.Context;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.addNote.presenter.AddTodoPresenter;
import com.example.client1.vndtodo.addNote.presenter.AddTodoPresenterInterface;
import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.util.Connectivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class AddTodoInteractor implements AddTodoInteractorInterface {
    Context context;
    AddTodoPresenterInterface presenter;
    ToDoItemModel itemModel;

    /*Firebase objects*/
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    public AddTodoInteractor(Context context, AddTodoPresenter presenter) {
        this.context = context;
        this.presenter = presenter;

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Constant.key_firebase_todo);

    }

    @Override
    public void getResponseForAddTodoToServer(final ToDoItemModel model, final String userId) {
        presenter.showDialog(context.getString(R.string.note_saving));
        if(Connectivity.isNetworkConnected(context)){
            itemModel = model;

            databaseReference.addValueEventListener(new ValueEventListener()
            {
                GenericTypeIndicator<ArrayList<ToDoItemModel>> t =
                        new GenericTypeIndicator<ArrayList<ToDoItemModel>>()
                        {
                        };

                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if(itemModel != null)
                    {
                        int index = (int) dataSnapshot.child(userId)
                                .child(model.getStartDate()).getChildrenCount();

                        List<ToDoItemModel> noteList = new ArrayList<>();
                        for (DataSnapshot obj : dataSnapshot.child(userId).getChildren())
                        {
                            List<ToDoItemModel> li;
                            li = obj.getValue(t);
                            noteList.addAll(li);
                        }
                       // itemModel.setSrNo(noteList.size());
                        getIndex(index, userId, itemModel);
                        itemModel = null;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    presenter.addTodoFailure(context.getString(R.string.addTodo_failure));
                }
            });

        }
        else
        {

            presenter.addTodoSuccess(context.getString(R.string.no_internet));
            presenter.hideDialog();
        }
    }
    private void getIndex(int index, String userId, ToDoItemModel model)
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null)
        {
            model.setNoteId(index);
            databaseReference.child(userId).child(model.getStartDate())
                    .child(String.valueOf(index)).setValue(model);

           // sqliteDatabase.addTodo(model, userId);
            presenter.addTodoSuccess(context.getString(R.string.addTodo_success));
            presenter.hideDialog();
        }
        else
        {
            presenter.addTodoFailure(context.getString(R.string.addTodo_failure));
            presenter.hideDialog();
        }

    }

    @Override
    public void getResponseForUpdateTodoToServer(ToDoItemModel model, String userId) {

        presenter.showDialog(context.getString(R.string.updating));

        if(Connectivity.isNetworkConnected(context))
        {
            databaseReference.child(userId).child(model.getStartDate())
                    .child(String.valueOf(model.getNoteId()))
                    .setValue(model);
            presenter.updateSuccess(context.getString(R.string.update_success));
        }
        else
        {
            presenter.updateFailure(context.getString(R.string.no_internet) + "\n"+
                    context.getString(R.string.update_failure));
        }
        presenter.hideDialog();
    }

}
