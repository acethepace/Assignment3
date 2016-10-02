package com.mallock.pointless.tabfragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mallock.pointless.MainActivity;
import com.mallock.pointless.R;
import com.mallock.pointless.User;
import com.mallock.pointless.datautils.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mallock on 02-10-2016.
 */

public class BrowseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.browse_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new Adapter(getUserList()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    /**
     * fetch list of users from database
     *
     * @return all users in DB
     */
    private List<User> getUserList() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = ((MainActivity) getActivity()).dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DBHelper.COLUMN_USERNAME + " , " + DBHelper.COLUMN_NAME + " , " + DBHelper.COLUMN_PHONE + " , " + DBHelper.COLUMN_HOBBY
                + " FROM " + DBHelper.TABLE_NAME, null
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String username = cursor.getString(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            String hobby = cursor.getString(3);
            users.add(new User(username, name, hobby, phone));
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }
}
