package com.mallock.pointless.tabfragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mallock.pointless.MainActivity;
import com.mallock.pointless.R;
import com.mallock.pointless.User;
import com.mallock.pointless.datautils.DBHelper;

/**
 * Created by Mallock on 02-10-2016.
 */

public class UserDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext())
                .inflate(R.layout.user_detail_fragment, container, false);
        Button saveButton = (Button) view.findViewById(R.id.save_button);
        Button logoutButton = (Button) view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).currentUser = null;
                Fragment newFragment = new LoginFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.commit();
            }
        });
        User user = ((MainActivity) getActivity()).currentUser;
        final EditText username = (EditText) view.findViewById(R.id.et_username);
        final EditText name = (EditText) view.findViewById(R.id.et_name);
        final EditText phone = (EditText) view.findViewById(R.id.et_phone);
        final EditText hobby = (EditText) view.findViewById(R.id.et_hobby);
        username.setText(user.getUsername());
        name.setText(user.getName());
        hobby.setText(user.getHobby());
        phone.setText(user.getPhone());
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(username.getText().toString(), name.getText().toString(), hobby.getText().toString(), phone.getText().toString());
            }
        });
        return view;
    }

    /**
     * update on database
     */
    private void saveData(String username, String name, String hobby, String phone) {
        SQLiteDatabase db = ((MainActivity) getActivity()).dbHelper.getWritableDatabase();
        String oldUsername = ((MainActivity) getActivity()).currentUser.getUsername();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_USERNAME, username);
        cv.put(DBHelper.COLUMN_NAME, name);
        cv.put(DBHelper.COLUMN_PHONE, phone);
        cv.put(DBHelper.COLUMN_HOBBY, hobby);
        db.update(DBHelper.TABLE_NAME, cv, " " + DBHelper.COLUMN_USERNAME + " = ? ", new String[]{oldUsername});
        ((MainActivity) getActivity()).setCurrentUser(new User(username, name, hobby, phone));
        Toast.makeText(getActivity(), "Database updated.", Toast.LENGTH_LONG).show();
    }
}
