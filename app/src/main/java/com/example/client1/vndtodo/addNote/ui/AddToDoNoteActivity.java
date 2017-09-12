package com.example.client1.vndtodo.addNote.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.addNote.presenter.AddTodoPresenter;
import com.example.client1.vndtodo.addNote.presenter.AddTodoPresenterInterface;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;
import com.example.client1.vndtodo.homescreen.ui.activity.HomeScreenActivity;
import com.example.client1.vndtodo.registration.model.UserModel;
import com.example.client1.vndtodo.session.SharedPreferenceManager;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by client1 on 9/11/2017.
 */

public class AddToDoNoteActivity extends AppCompatActivity implements AddToDoViewInterface, ColorPickerDialogListener {

    AppCompatEditText editTextTitle;
    AppCompatEditText editTextNote;
    AppCompatTextView textTextReminder;
    AppCompatImageView imageViewBack;
    AppCompatImageView imageViewColor;
    AppCompatImageView imageViewReminder;
    AppCompatImageView imageViewSave;

    LinearLayout linearLayout;

    ToDoItemModel model;

    SharedPreferenceManager session;

    DatePickerDialog datePicker;
    TimePickerDialog timepicker;

    public static boolean add = true;
    public static int edit_pos;
    public int color = Color.parseColor("#FFFFFF");

    AddTodoPresenterInterface presenter;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_todo_item);
        initView();
    }

    public void initView() {
        editTextTitle = (AppCompatEditText) findViewById(R.id.editViewTodoTitle);
        editTextNote = (AppCompatEditText) findViewById(R.id.editViewTodoItem);
        textTextReminder = (AppCompatTextView) findViewById(R.id.textViewReminder);
        linearLayout = (LinearLayout) findViewById(R.id.add_todo_fragment_linearlayout);

        imageViewBack = (AppCompatImageView) findViewById(R.id.action_back);
        imageViewColor = (AppCompatImageView) findViewById(R.id.color_picker);
        imageViewReminder = (AppCompatImageView) findViewById(R.id.add_reminder);
        imageViewSave = (AppCompatImageView) findViewById(R.id.action_save);

        presenter = new AddTodoPresenter(this, this);
        session = new SharedPreferenceManager(this);

        imageViewBack.setOnClickListener(this);
        imageViewColor.setOnClickListener(this);
        imageViewSave.setOnClickListener(this);
        imageViewReminder.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_back:
                Intent intent = new Intent(this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.add_reminder:

                myCalendar = Calendar.getInstance();

                date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        timepicker = new TimePickerDialog(AddToDoNoteActivity.this, time,
                                myCalendar.get(Calendar.HOUR_OF_DAY),
                                myCalendar.get(Calendar.MINUTE), true);
                        timepicker.show();
                    }
                };

                time = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalendar.set(Calendar.MINUTE, minute);
                        myCalendar.set(Calendar.SECOND, 00);
                        updateLabel();
                    }
                };

                datePicker = new DatePickerDialog(this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                datePicker.show();
                break;

            case R.id.color_picker:
                ColorPickerDialog.newBuilder().setAllowPresets(true).setShowAlphaSlider(true).show(this);
                break;

            case R.id.action_save:
                if (add) {
                    saveDateToAdapter();
                } else {
                    //editTodoItem();
                }
                finish();
                break;
        }

    }

    private void updateLabel()
    {
        String myFormat = getString(R.string.date_format_month);
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        textTextReminder.setText(sdf.format(myCalendar.getTime()));
    }
    public void saveDateToAdapter()
    {
        UserModel userModel=session.getUserDetails();
        model=new ToDoItemModel();
        model.setNoteId(-1);
        model.setTitle(editTextTitle.getText().toString());
        model.setNote(editTextNote.getText().toString());
        model.setReminderDate(textTextReminder.getText().toString());
        model.setArchieved(false);
        model.setDeleted(false);

        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat(getString(R.string.date_format));
        String currentDate=format.format(date.getTime());
        model.setStartDate(currentDate);
        model.setColor(color);

        if (!(model.getTitle().equals("") || model.getNote().equals("")
                || model.getTitle().equals(" ") || model.getNote().equals(" "))) {
            presenter.getResponseForAddTodoToServer(model, userModel.getId());
        }

        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    @Override
    public void addTodoSuccess(String message) {
        Toast.makeText(AddToDoNoteActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addTodoFailure(String message) {
        Toast.makeText(AddToDoNoteActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    ProgressDialog progressDialog;
    @Override
    public void showDialog(String message)
    {
        if(!isFinishing())
        {
            progressDialog=new ProgressDialog(AddToDoNoteActivity.this);
            progressDialog.setMessage(message);
            progressDialog.show();
        }

    }

    @Override
    public void hideDialog() {
        if(!isFinishing() && progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void updateSuccess(String message) {
        Toast.makeText(AddToDoNoteActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateFailure(String message) {
        Toast.makeText(AddToDoNoteActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
      linearLayout.setBackgroundColor(color);
        this.color=color;
    }

    @Override
    public void onDialogDismissed(int dialogId) {
        Log.i("colorpicker closed", "onDialogDismissed: "+dialogId);
    }
}
