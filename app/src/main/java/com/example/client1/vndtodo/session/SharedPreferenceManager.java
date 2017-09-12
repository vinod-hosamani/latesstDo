package com.example.client1.vndtodo.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.registration.model.UserModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by client1 on 9/8/2017.
 */

public class SharedPreferenceManager
{
    static SharedPreferences userDataPref;
    static SharedPreferences.Editor userEditor;
    static Context context;

    public SharedPreferenceManager(Context context)
    {
        this.context = context;
        if (userDataPref == null && userEditor == null) {
            userDataPref = this.context.getSharedPreferences(Constant.user_pref_name, MODE_PRIVATE);
            userEditor = userDataPref.edit();
        }
    }
    public void put(UserModel userModel, String profilePic)
    {
        userEditor.putString(Constant.key_id, userModel.getId());
        userEditor.putString(Constant.key_fullname, userModel.getFullname());
        userEditor.putString(Constant.key_email, userModel.getEmail());
        userEditor.putString(Constant.key_password, userModel.getPassword());
        userEditor.putString(Constant.key_mobile, userModel.getMobile());

        userEditor.putBoolean(Constant.key_is_login, true);
        userEditor.putString(Constant.key_pro_pic, profilePic);
        userEditor.commit();
    }
    public UserModel getUserDetails()
    {
        UserModel user = new UserModel();
        user.setFullname(userDataPref.getString(Constant.key_fullname, Constant.empty_value));
        user.setEmail(userDataPref.getString(Constant.key_email, Constant.empty_value));
        user.setPassword(userDataPref.getString(Constant.key_password, Constant.empty_value));
        user.setMobile(userDataPref.getString(Constant.key_mobile, Constant.empty_value));
        user.setId(userDataPref.getString(Constant.key_id, Constant.empty_value));
        return user;
    }
    public void setView(boolean isList) {
        userEditor.putBoolean(Constant.key_view_type, isList);
        userEditor.commit();
    }
    public boolean isList() {
        return userDataPref.getBoolean(Constant.key_view_type, true);
    }
}
