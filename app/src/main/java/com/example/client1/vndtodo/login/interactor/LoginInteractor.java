package com.example.client1.vndtodo.login.interactor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.login.presenter.LoginPresenterInterface;
import com.example.client1.vndtodo.registration.model.UserModel;
import com.example.client1.vndtodo.util.Connectivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by client1 on 9/9/2017.
 */

public class LoginInteractor implements LoginInteractorInterface {

    Context context;
    LoginPresenterInterface presenterInterface;

    UserModel model;

    /*Firebase objects*/
    FirebaseAuth userAuth;
    FirebaseDatabase database;
    DatabaseReference userDataReference;
    DatabaseReference userProfileDataReference;
    private String userId;

    String profilePic;

    public LoginInteractor(Context context, LoginPresenterInterface presenterInterface) {
        this.context = context;
        this.presenterInterface = presenterInterface;
        userAuth = FirebaseAuth.getInstance();
        profilePic = "";
    }

    @Override
    public void getLoginResponsefromFirebase(String email, String password) {
        presenterInterface.showDialog(context.getString(R.string.login_progress_dialog));
        if (Connectivity.isNetworkConnected(context)) {
            userAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userId = task.getResult().getUser().getUid();
                                getUserData(userId);
                            }
                        }
                    });
        }

    }

    public void getUserData(final String userId) {
        database = FirebaseDatabase.getInstance();

        userProfileDataReference = database.getReference(Constant.key_firebase_userProfile);

        userProfileDataReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userId).exists()) {
                    profilePic = (String) dataSnapshot.child(userId).child("imageUrl").getValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Profile Load Error", "onCancelled: " + databaseError.getDetails());
            }
        });

        userDataReference = database.getReference(Constant.key_firebase_user);
        userDataReference.child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        model = dataSnapshot.getValue(UserModel.class);
                        presenterInterface.loginSuccess(model, profilePic);
                        presenterInterface.hideDialog();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        presenterInterface.loginFailure(error.getMessage());
                        presenterInterface.hideDialog();
                    }
                });
    }


}