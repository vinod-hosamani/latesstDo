package com.example.client1.vndtodo.homescreen.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.homescreen.presenter.TrashPresenter;
import com.example.client1.vndtodo.homescreen.presenter.TrashPresenterInterface;
import com.example.client1.vndtodo.homescreen.ui.activity.HomeScreenActivity;

/**
 * Created by client1 on 9/11/2017.
 */

public class TrashFragment extends Fragment implements TrashFragmentInterface
{
    TrashPresenterInterface presenter;
    RecyclerView trashRecyclerView;
    HomeScreenActivity homeScreenActivity;

    public TrashFragment( HomeScreenActivity homeScreenActivity){

        this.homeScreenActivity = homeScreenActivity;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_archieved_list, container, false);
        initView(view);
        return view;

    }
    public void initView(View view)
    {
        trashRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_archieved_list);
        homeScreenActivity.setTitle("trash");
    }
}
