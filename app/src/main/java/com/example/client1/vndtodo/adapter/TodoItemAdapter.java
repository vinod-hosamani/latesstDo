package com.example.client1.vndtodo.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.client1.vndtodo.R;
import com.example.client1.vndtodo.homescreen.model.ToDoItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by client1 on 9/12/2017.
 */

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.TaskViewHolder>
{

    private static List<ToDoItemModel> todoList;
    Context context;
    ToDoItemModel model;
    OnNoteClickListener noteClickListener;
    OnLongClickListener longClickListener;

    public TodoItemAdapter(OnNoteClickListener noteClickListener,OnLongClickListener longClickListener)
    {
        todoList=new ArrayList<>();
        this.noteClickListener=noteClickListener;
        this.context=context;
        this.longClickListener=longClickListener;
    }
    public TodoItemAdapter(Context context)
    {
        this.context=context;
    }
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_todo_item_list,parent,false);
        return new TaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position)
    {
        final ToDoItemModel todoItemModel=todoList.get(position);
        holder.linearLayoutTodo.setBackgroundColor(todoItemModel.getColor());
        holder.title.setText(todoItemModel.getTitle());
        holder.note.setText(todoItemModel.getNote());
        if(!todoItemModel.getReminderDate().equals(""))
        {
            holder.reminderDate.setText(todoItemModel.getReminderDate());
        }
        else
        {
            holder.reminderDate.setText("");
        }
        if(noteClickListener!=null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(noteClickListener!=null)
                    {
                        noteClickListener.onItemClick(position);
                    }
                }
            });
        }
    }
    @Override
    public int getItemCount()
    {
        return todoList.size();
    }
    public void setTodoList(List<ToDoItemModel> noteList)
    {
        todoList.clear();
        notifyDataSetChanged();
        todoList.addAll(noteList);
        notifyDataSetChanged();
    }
    public class TaskViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout linearLayoutTodo, linearLayoutBorder;
        public AppCompatTextView title, note, reminderDate;
        public CardView cardViewTodo;

        public TaskViewHolder(View view) {
            super(view);
            linearLayoutTodo = (LinearLayout) view.findViewById(R.id.linearLayout_todo_background);
            linearLayoutBorder = (LinearLayout) view.findViewById(R.id.linearLayout_for_border);

            title = (AppCompatTextView) view.findViewById(R.id.todo_title);
            note = (AppCompatTextView) view.findViewById(R.id.todo_note);
            reminderDate = (AppCompatTextView) view.findViewById(R.id.todo_reminder);

            cardViewTodo = (CardView) view.findViewById(R.id.cardView_todo_note);
        }
    }
    public ToDoItemModel getItemModel(int pos)
    {
        return todoList.get(pos);
    }


    public List<ToDoItemModel> getAllDataList()
    {
        return todoList;
    }

    public void setFilter(List<ToDoItemModel> noteList)
    {
        todoList = new ArrayList<>();
        todoList.addAll(noteList);
        notifyDataSetChanged();
    }
    public interface OnNoteClickListener
    {
       void onItemClick(int pos);
    }
    public interface OnLongClickListener
    {
        void onLongClick(ToDoItemModel itemModel);
    }
}
