package com.mallock.pointless.tabfragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mallock.pointless.MainActivity;
import com.mallock.pointless.R;
import com.mallock.pointless.User;
import com.mallock.pointless.datautils.DBHelper;

/**
 * Created by Mallock on 01-10-2016.
 */

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        Button loginButton = (Button) view.findViewById(R.id.login_button);
        TextView signUp = (TextView) view.findViewById(R.id.tv_signup);
        final EditText username = (EditText) view.findViewById(R.id.et_username);
        final EditText password = (EditText) view.findViewById(R.id.et_password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyLogin(username.getText().toString(), password.getText().toString());
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.signup_fragment, null, false);
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                        .setView(view)
                        .setTitle("SIGN UP");
                final AlertDialog ad = dialog.show();
                Button saveButton = (Button) view.findViewById(R.id.save_button);
                final EditText username = (EditText) view.findViewById(R.id.et_username);
                final EditText name = (EditText) view.findViewById(R.id.et_name);
                final EditText phone = (EditText) view.findViewById(R.id.et_phone);
                final EditText hobby = (EditText) view.findViewById(R.id.et_hobby);
                final EditText password = (EditText) view.findViewById(R.id.et_password);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean success = saveUser(username.getText().toString(), name.getText().toString(),
                                hobby.getText().toString(), phone.getText().toString(), password.getText().toString());
                        if (success) {
                            Toast.makeText(getContext(), "SAVED", Toast.LENGTH_LONG).show();
                            ad.cancel();
                        } else {
                            Toast.makeText(getContext(), "USERNAME TAKEN", Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
        return view;
    }

    /**
     * save user to DB
     */
    public boolean saveUser(String username, String name, String hobby, String phone, String password) {
        SQLiteDatabase db = ((MainActivity) getActivity()).dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_USERNAME, username);
        cv.put(DBHelper.COLUMN_NAME, name);
        cv.put(DBHelper.COLUMN_PHONE, phone);
        cv.put(DBHelper.COLUMN_HOBBY, hobby);
        cv.put(DBHelper.COLUMN_PASSWORD, password);
        long result = db.insert(DBHelper.TABLE_NAME, null, cv);
        ((MainActivity) getActivity()).setCurrentUser(new User(username, name, hobby, phone));
        return result != -1;
    }

    /**
     * verify login from user
     *
     * @param user
     * @param pwd
     */
    public void verifyLogin(String user, String pwd) {
        SQLiteDatabase db = ((MainActivity) getActivity()).dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DBHelper.COLUMN_USERNAME + " , " + DBHelper.COLUMN_NAME + " , " + DBHelper.COLUMN_PHONE + " , " + DBHelper.COLUMN_HOBBY + " , " + DBHelper.COLUMN_PASSWORD
                + " FROM " + DBHelper.TABLE_NAME, null
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String username = cursor.getString(0);
            String password = cursor.getString(4);
            if (username.equals(user) && password.equals(pwd)) {
                ((MainActivity) getActivity()).setCurrentUser(new User(username, cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                Toast.makeText(getContext(), "Login successful", Toast.LENGTH_LONG).show();
                Fragment newFragment = new UserDetailFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.commit();
                cursor.close();
                return;
            }
            cursor.moveToNext();
        }
        Toast.makeText(getContext(), "Incorrect username/password", Toast.LENGTH_LONG).show();
        cursor.close();
    }
}
