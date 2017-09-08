package com.example.client1.vndtodo.registration.interactor;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.registration.model.UserModel;
import com.example.client1.vndtodo.registration.presenter.RegistrationPresenter;
import com.example.client1.vndtodo.registration.presenter.RegistrationPresenterInterface;
import com.example.client1.vndtodo.util.Connectivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by client1 on 9/8/2017.
 */

public class RegistrationInteractor implements RegistrationInteractorInterface
{

    RegistrationPresenterInterface presenterInterface;
    Context context;

    private FirebaseAuth userAuth;
    private FirebaseDatabase userDatabase;
    private DatabaseReference userDatabaseRef;
    private String userId;

    public RegistrationInteractor(Context context, RegistrationPresenterInterface presenterInterface)
    { this.presenterInterface=presenterInterface;
        this.context=context;
    }


    @Override
    public void getRegisterResponse(final UserModel userModel)
    {
        presenterInterface.showProgressDialog(context.getString(R.string.register_progress_dialog));

        if(Connectivity.isNetworkConnected(context))
        {
            userAuth=FirebaseAuth.getInstance();
            userAuth.createUserWithEmailAndPassword(userModel.getEmail(),userModel.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                userId=task.getResult().getUser().getUid();
                                userModel.setId(userId);
                                userDatabase=FirebaseDatabase.getInstance();
                                userDatabaseRef=userDatabase. getReference(Constant.key_firebase_user);
                                userDatabaseRef.child(userId).setValue(userModel);

                                presenterInterface.registerSuccess(context.getString(R.string.register_success));
                                presenterInterface.hideProgressDialog();
                            }

                        }
                    });
        }
        else {
            presenterInterface.registerFailure(context.getString(R.string.no_internet));
        }
    }
}
