package com.example.taskmasterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewAdapter  extends RecyclerView.Adapter <ViewAdapter.ViewHolder>{
    private final List<Tasks> tasksList;

    public ViewAdapter(List<Tasks> tasksList) {
        this.tasksList = tasksList;
    }

    @NonNull
    @Override
    public ViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_items,parent,false);
return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter.ViewHolder holder, int position) {
    String title=tasksList.get(position).getTaskTitle();
    String body=tasksList.get(position).getTaskBody();
    String status = tasksList.get(position).getTaskStatus();
holder.setData(title,body,status);
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView taskTitle;
        private final TextView taskbody;
        private final TextView taskstatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle=itemView.findViewById(R.id.taskTitle);
            taskbody=itemView.findViewById(R.id.taskDes);
            taskstatus=itemView.findViewById(R.id.taskStatus);
        }



        public void setData(String title, String body, String status) {
        taskTitle.setText(title);
        taskbody.setText(body);
        taskstatus.setText(status);
        }
    }
}
