package com.example.client1.vndtodo.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.client1.vndtodo.adapter.TodoItemAdapter;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.ui.activity.HomeScreenActivity;
import com.example.client1.vndtodo.homescreen.ui.fragment.ToDoNotesFragment;

/**
 * Created by client1 on 9/12/2017.
 */

public class SwipeNotes extends ItemTouchHelper.SimpleCallback {

    public static final int left = ItemTouchHelper.LEFT;
    public static final int right = ItemTouchHelper.RIGHT;
    public static final int up = ItemTouchHelper.UP;
    public static final int down = ItemTouchHelper.DOWN;

    TodoItemAdapter todoAdapter;
    ToDoNotesFragment activity;
    Activity context;

    public SwipeNotes(int dragDirs, int swipeDirs,TodoItemAdapter todoAdapter,ToDoNotesFragment activity,Activity context)
    {
        super(dragDirs, swipeDirs);
        this.todoAdapter=todoAdapter;
        this.activity=activity;
        this.context=context;

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
      final int pos=viewHolder.getAdapterPosition();
        switch (direction)
        {
            case left:
                moveToTrash(pos);
                break;
            case right:
                moveToArchive(pos);
                break;
        }
    }
    private void moveToTrash(int pos)
    { final ToDoItemModel itemModel = todoAdapter.getItemModel(pos);
        activity.presenter.moveToTrash(itemModel);
        Snackbar snackbar = Snackbar.make(context.getCurrentFocus()
                , "note has been Trashed", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        activity.presenter.moveToNotes(itemModel, true);

                        Snackbar snackbar1 = Snackbar.make(context.getCurrentFocus()
                                , "Undo done", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
        snackbar.show();
    }

    private void moveToArchive(int pos)
    {
        final ToDoItemModel itemModel = todoAdapter.getItemModel(pos);
        activity.presenter.moveToArchieve(itemModel);

        Snackbar snackbar = Snackbar.make(context.getCurrentFocus()
                , "note has been Archieved", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        activity.presenter.moveToNotes(itemModel, false);

                        Snackbar snackbar1 = Snackbar.make(context.getCurrentFocus()
                                , "Undo done", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
        snackbar.show();
    }

}
