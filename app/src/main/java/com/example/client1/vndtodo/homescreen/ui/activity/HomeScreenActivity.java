package com.example.client1.vndtodo.homescreen.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.addNote.ui.AddToDoNoteActivity;
import com.example.client1.vndtodo.constants.Constant;
import com.example.client1.vndtodo.homescreen.presenter.HomeScreenPresenter;
import com.example.client1.vndtodo.homescreen.ui.fragment.ArchievedFragment;
import com.example.client1.vndtodo.homescreen.ui.fragment.ReminderFragment;
import com.example.client1.vndtodo.homescreen.ui.fragment.ToDoNotesFragment;
import com.example.client1.vndtodo.homescreen.ui.fragment.TrashFragment;
import com.example.client1.vndtodo.session.SharedPreferenceManager;

public class HomeScreenActivity extends AppCompatActivity
        implements HomeScreenActivityInterface {

    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;


    ArchievedFragment archievedFragment;
    TrashFragment trashFragment;
    ReminderFragment reminderFragment;
    ToDoNotesFragment toDoNotesFragment;

    SharedPreferenceManager session;
    public HomeScreenPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initView();
        setListener();

        toDoNotesFragment = new ToDoNotesFragment(this);
        setTitle(Constant.note_title);
        fab.setVisibility(View.VISIBLE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.todo_item_fragment, toDoNotesFragment, "todoNoteList")
                .addToBackStack(null)
                .commit();

    }

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        session = new SharedPreferenceManager(this);
        presenter = new HomeScreenPresenter(this, this);
    }

    public void setListener() {
        setSupportActionBar(toolbar);
        fab.setOnClickListener(this);
        fab.setVisibility(View.VISIBLE);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        archievedFragment = new ArchievedFragment(this);
        reminderFragment = new ReminderFragment(this);
        trashFragment = new TrashFragment(this);


        if (id == R.id.nav_notes) {

            setTitle(Constant.note_title);

            fab.setVisibility(View.VISIBLE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.todo_item_fragment, toDoNotesFragment, "todoNoteList")
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_archieved) {
            setTitle(Constant.archieve_title);

            fab.setVisibility(View.INVISIBLE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.todo_item_fragment, archievedFragment, "archievedList")
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_reminder) {
            setTitle(Constant.reminder_title);

            fab.setVisibility(View.INVISIBLE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.todo_item_fragment, reminderFragment, "reminderList")
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_trash) {
            setTitle(Constant.trash_title);

            fab.setVisibility(View.INVISIBLE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.todo_item_fragment, trashFragment, "TrashDataList")
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_logout) {

        } /*else if (id == R.id.nav_send) {

        }*/

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                AddToDoNoteActivity.add = true;
                Intent intent = new Intent(this, AddToDoNoteActivity.class);
                startActivity(intent);
                // TODO: 16/6/17 fragment commented
                /*addTodoTask();*/
                break;
        }
    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {

    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
