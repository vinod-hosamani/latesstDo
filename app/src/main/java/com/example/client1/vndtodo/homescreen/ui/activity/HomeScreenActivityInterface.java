package com.example.client1.vndtodo.homescreen.ui.activity;

import android.support.design.widget.NavigationView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

/**
 * Created by client1 on 9/11/2017.
 */

public interface HomeScreenActivityInterface extends NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,SearchView.OnQueryTextListener,ColorPickerDialogListener{

    void showDialog(String message);
    void hideDialog();
}
