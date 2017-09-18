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
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.presenter.ArchievedPresenter;
import com.example.client1.vndtodo.homescreen.presenter.ArchievedPresenterInterface;
import com.example.client1.vndtodo.homescreen.ui.activity.HomeScreenActivity;
import com.example.client1.vndtodo.session.SharedPreferenceManager;
import com.example.client1.vndtodo.util.Connectivity;
import com.example.client1.vndtodo.util.SwipeArchive;
import com.example.client1.vndtodo.util.SwipeNotes;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by client1 on 9/11/2017.
 */

public class ArchievedFragment extends Fragment implements ArchievedFragmentInterface, SearchView.OnQueryTextListener {
    RecyclerView archievedRecyclerView;
    TodoItemAdapter todoItemAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    HomeScreenActivity homeScreenActivity;
    public ArchievedPresenter presenter;
    SwipeArchive swipeArchieve;
    ItemTouchHelper itemTouchHelper;
    SharedPreferenceManager session;
    List<ToDoItemModel> allData;
    private boolean isList;


    public ArchievedFragment(HomeScreenActivity homeScreenActivity) {
        this.homeScreenActivity = homeScreenActivity;
        presenter = new ArchievedPresenter(homeScreenActivity, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_archieved_list, container, false);
        initView(view);
        setHasOptionsMenu(true);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        presenter.getNoteList(userId);
        return view;
    }

    public void initView(View view) {
        archievedRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_archieved_list);
        homeScreenActivity.setTitle("archieve");
        setHasOptionsMenu(true);
        session = new SharedPreferenceManager(homeScreenActivity);
        isList = session.isList();
        todoItemAdapter = new TodoItemAdapter(homeScreenActivity);
        archievedRecyclerView.setAdapter(todoItemAdapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager
                (1, StaggeredGridLayoutManager.VERTICAL);
        archievedRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        if (Connectivity.isNetworkConnected(homeScreenActivity))
        {
            swipeArchieve = new SwipeArchive(SwipeNotes.up | SwipeNotes.down | SwipeNotes.left | SwipeNotes.right,
                    SwipeNotes.left | SwipeNotes.right, todoItemAdapter, ArchievedFragment.this, getActivity());
            itemTouchHelper = new ItemTouchHelper(swipeArchieve);
            itemTouchHelper.attachToRecyclerView(archievedRecyclerView);
        }
    }

    @Override
    public void getNoteListSuccess(List<ToDoItemModel> noteList) {
        List<ToDoItemModel> archievedList = new ArrayList<>();

        for (ToDoItemModel model : noteList)
        {
            if (model.isArchieved() && !model.isDeleted())
                archievedList.add(model);
        }
        allData = archievedList;
        todoItemAdapter.setTodoList(archievedList);
    }

    @Override
    public void getNoteListFailure(String message) {
        Toast.makeText(homeScreenActivity, message, Toast.LENGTH_SHORT).show();
    }

    ProgressDialog progressDialog;

    @Override
    public void showProgressDialog(String message) {

        progressDialog = new ProgressDialog(homeScreenActivity);
        if (!homeScreenActivity.isFinishing()) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
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
    public void moveToNotesSuccess(String message) {
        Toast.makeText(homeScreenActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToNotesFailure(String message) {
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchNotes).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_toggle) {
            toggle(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle(MenuItem item) {

        if (isList) {
            staggeredGridLayoutManager.setSpanCount(2);
            item.setIcon(R.drawable.ic_action_list);
            item.setTitle("show as list");
            isList = false;
        } else {
            staggeredGridLayoutManager.setSpanCount(1);
            item.setIcon(R.drawable.ic_action_grid);
            item.setTitle("isGrid");
            isList = true;
        }
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
}
