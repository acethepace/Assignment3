package com.mallock.pointless.tabfragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mallock.pointless.R;
import com.mallock.pointless.User;

import java.util.List;

/**
 * Created by Mallock on 02-10-2016.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.UserHolder> {

    List<User> userList;

    public Adapter(List<User> list) {
        this.userList = list;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_child, parent, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        User user = userList.get(position);
        holder.username.setText("@"+user.getUsername());
        holder.name.setText(user.getName());
        holder.hobby.setText(user.getHobby());
        holder.phone.setText(user.getPhone());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserHolder extends RecyclerView.ViewHolder {

        TextView username, name, hobby, phone;

        public UserHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.tv_username);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            hobby = (TextView) itemView.findViewById(R.id.tv_hobby);
            phone = (TextView) itemView.findViewById(R.id.tv_phone);
        }
    }
}
