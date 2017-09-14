package com.example.client1.vndtodo.homescreen.ui.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.adapter.TodoItemAdapter;
import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.presenter.ToDoNotesPresenter;
import com.example.client1.vndtodo.homescreen.presenter.ToDoNotesPresenterInterface;
import com.example.client1.vndtodo.homescreen.ui.activity.HomeScreenActivity;
import com.example.client1.vndtodo.session.SharedPreferenceManager;
import com.example.client1.vndtodo.util.Connectivity;
import com.example.client1.vndtodo.util.SwipeNotes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by client1 on 9/11/2017.
 */

public class ToDoNotesFragment extends Fragment implements ToDoNotesFragmentInterface, TodoItemAdapter.OnLongClickListener
{
    HomeScreenActivity homeScreenActivity;
    public ToDoNotesPresenter presenter;
    SharedPreferenceManager session;
    List<ToDoItemModel> allData;
    TodoItemAdapter todoItemAdapter;
    boolean isList;
    RecyclerView toDoItemRecycler;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    SwipeNotes swipeNotes;
    ItemTouchHelper itemTouchHelper;
    Menu menu;
    ProgressDialog progressDialog;

    boolean isSelection;
    List<ToDoItemModel> actionList;


    public ToDoNotesFragment(HomeScreenActivity homeScreenActivity) {
        this.homeScreenActivity = homeScreenActivity;
        actionList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.content_home_screen, container, false);
        initView(view);
        homeScreenActivity.setTitle(Constant.note_title);
        setHasOptionsMenu(true);
        presenter.getTodoNoteFromServer(session.getUserDetails().getId());
        allData = todoItemAdapter.getAllDataList();
        return view;
    }

    public void initView(View view) {
        toDoItemRecycler = (RecyclerView) view.findViewById(R.id.recycler_todo_item);
        todoItemAdapter = new TodoItemAdapter(this, this);

        session = new SharedPreferenceManager(homeScreenActivity);
        isList = session.isList();
        presenter = new ToDoNotesPresenter(homeScreenActivity, this);

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        toDoItemRecycler.setLayoutManager(mStaggeredGridLayoutManager);
        toDoItemRecycler.setAdapter(todoItemAdapter);

        if (Connectivity.isNetworkConnected(homeScreenActivity))
        {
            swipeNotes = new SwipeNotes(SwipeNotes.up | SwipeNotes.down | SwipeNotes.left | SwipeNotes.right,
                    SwipeNotes.left | SwipeNotes.right, todoItemAdapter, ToDoNotesFragment.this, getActivity());
            itemTouchHelper = new ItemTouchHelper(swipeNotes);
            itemTouchHelper.attachToRecyclerView(toDoItemRecycler);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_toolbar, menu);
        this.menu = menu;
        //toggle();

        SearchManager searchManager = (SearchManager) homeScreenActivity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchNotes).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(homeScreenActivity.getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_toggle) {
            toggle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        MenuItem item = menu.findItem(R.id.action_toggle);

        if (isList) {
            mStaggeredGridLayoutManager.setSpanCount(1);
            item.setIcon(R.drawable.ic_action_grid);
            item.setTitle("Show as grid");
            session.setView(isList);
        } else {
            mStaggeredGridLayoutManager.setSpanCount(2);
            item.setIcon(R.drawable.ic_action_list);
            item.setTitle("Show as list");
            session.setView(isList);
        }
        isList = !isList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        searchText = searchText.toLowerCase();

        List<ToDoItemModel> noteList = new ArrayList<>();

        for (ToDoItemModel model : allData) {
            if (model.getTitle().toLowerCase().contains(searchText)) {
                noteList.add(model);
            }
        }
        todoItemAdapter.setFilter(noteList);
        return true;
    }

    @Override
    public void onItemClick(int pos) {

    }

    @Override
    public void getNoteSuccess(List<ToDoItemModel> noteList) {
        List<ToDoItemModel> nonArchievedList = new ArrayList<>();

        for (ToDoItemModel model : noteList) {
            if (!model.isArchieved() && !model.isDeleted()) {
                nonArchievedList.add(model);
            }
        }
        todoItemAdapter.setTodoList(nonArchievedList);
    }

    @Override
    public void getNoteFailure(String message) {
        Toast.makeText(homeScreenActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(String message) {
        if (!homeScreenActivity.isFinishing()) {
            progressDialog = new ProgressDialog(homeScreenActivity);
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    @Override
    public void hideDialog() {
        if (!homeScreenActivity.isFinishing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void moveToTrashSuccess(String message) {
        Toast.makeText(homeScreenActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToTrashFailure(String message) {
        Toast.makeText(homeScreenActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToArchiveSuccess(String message) {
        Toast.makeText(homeScreenActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToArchiveFailure(String message) {
        Toast.makeText(homeScreenActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToNotesSuccess(String message) {
        Toast.makeText(homeScreenActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToNotesFailure(String message) {
        Toast.makeText(homeScreenActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(ToDoItemModel itemModel) {
    }
}
