package com.example.client1.vndtodo.util;

import android.app.Activity;
import android.media.session.MediaSessionManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.client1.vndtodo.adapter.TodoItemAdapter;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.ui.fragment.ArchievedFragment;

/**
 * Created by client1 on 9/16/2017.
 */

public class SwipeArchive extends ItemTouchHelper.SimpleCallback
{
    public static final int left=ItemTouchHelper.LEFT;
    public static final int right=ItemTouchHelper.RIGHT;
    public static final int up=ItemTouchHelper.UP;
    public static final int down=ItemTouchHelper.DOWN;

    TodoItemAdapter todoItemAdapter;
    ArchievedFragment activity;
    Activity context;


    public SwipeArchive(int dragDirs, int swipeDirs,TodoItemAdapter todoItemAdapter,ArchievedFragment activity,Activity context)
    {
        super(dragDirs, swipeDirs);
        this.todoItemAdapter=todoItemAdapter;
        this.activity=activity;
        this.context=context;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        todoItemAdapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
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
                moveToNotes(pos);
                break;
        }
    }

    private void moveToTrash(int pos)
    {
        final ToDoItemModel itemModel = todoItemAdapter.getItemModel(pos);
        activity.presenter.moveToTrash(itemModel);

        Snackbar snackbar = Snackbar.make(context.getCurrentFocus()
                , "note has been Trashed", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        activity.presenter.moveToArchive(itemModel, true);

                        Snackbar snackbar1 = Snackbar.make(context.getCurrentFocus()
                                , "Undo done", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
        snackbar.show();

    }

    private  void moveToNotes(int pos)
    {
        final ToDoItemModel itemModel = todoItemAdapter.getItemModel(pos);
        activity.presenter.moveToNotes(itemModel);

        Snackbar snackbar = Snackbar.make(context.getCurrentFocus()
                , "note has been moved", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        activity.presenter.moveToArchive(itemModel,false);
                        Snackbar snackbar1 = Snackbar.make(context.getCurrentFocus()
                                , "Undo done", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
        snackbar.show();

    }

}
