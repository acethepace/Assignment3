package com.mallock.pointless;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.mallock.pointless.datautils.DBHelper;
import com.mallock.pointless.datautils.SharedPrefsUtils;
import com.mallock.pointless.tabfragments.BrowseFragment;
import com.mallock.pointless.tabfragments.FileFragment;
import com.mallock.pointless.tabfragments.LoginFragment;
import com.mallock.pointless.tabfragments.NotLoggedInFragment;
import com.mallock.pointless.tabfragments.UserDetailFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import static com.mallock.pointless.datautils.Constants.FILE_EXTERNAL_PRIVATE;
import static com.mallock.pointless.datautils.Constants.FILE_EXTERNAL_PUBLIC;
import static com.mallock.pointless.datautils.Constants.FILE_INTERNAL;

public class MainActivity extends FragmentActivity {
    public User currentUser = null;
    public DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        String savedUsername = SharedPrefsUtils.getStringDataByKey(this, "username");
        if (savedUsername != null) {
            setCurrentUser(getUserFromUsername(savedUsername));
        }else{
            currentUser = null;
        }
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_login:
                        if (findViewById(R.id.fragment_container) != null) {
                            if (savedInstanceState != null) {
                                return;
                            }

                            Fragment newFragment;
                            if (currentUser == null) {
                                newFragment = new LoginFragment();
                            } else {
                                newFragment = new UserDetailFragment();
                            }

                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        break;
                    case R.id.tab_browse:
                        if (findViewById(R.id.fragment_container) != null) {
                            if (savedInstanceState != null) {
                                return;
                            }
                            Fragment newFragment;
                            if (currentUser == null) {
                                newFragment = new NotLoggedInFragment();
                            } else {
                                newFragment = new BrowseFragment();
                            }
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        break;
                    case R.id.tab_internal:
                        if (findViewById(R.id.fragment_container) != null) {
                            if (savedInstanceState != null) {
                                return;
                            }
                            Fragment newFragment;
                            if (currentUser == null) {
                                newFragment = new NotLoggedInFragment();
                            } else {
                                newFragment = new FileFragment();
                            }
                            Bundle args = new Bundle();
                            args.putInt("internal", FILE_INTERNAL);
                            newFragment.setArguments(args);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        break;
                    case R.id.tab_external:
                        if (findViewById(R.id.fragment_container) != null) {
                            if (savedInstanceState != null) {
                                return;
                            }
                            Fragment newFragment;
                            if (currentUser == null) {
                                newFragment = new NotLoggedInFragment();
                            } else {
                                newFragment = new FileFragment();
                            }
                            Bundle args = new Bundle();
                            args.putInt("internal", FILE_EXTERNAL_PRIVATE);
                            newFragment.setArguments(args);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        break;

                    case R.id.tab_external_2:
                        if (findViewById(R.id.fragment_container) != null) {
                            if (savedInstanceState != null) {
                                return;
                            }
                            Fragment newFragment;
                            if (currentUser == null) {
                                newFragment = new NotLoggedInFragment();
                            } else {
                                newFragment = new FileFragment();
                            }
                            Bundle args = new Bundle();
                            args.putInt("internal", FILE_EXTERNAL_PUBLIC);
                            newFragment.setArguments(args);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "unexpected error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public User getUserFromUsername(String u) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DBHelper.COLUMN_USERNAME + " , " + DBHelper.COLUMN_NAME + " , " + DBHelper.COLUMN_PHONE + " , " + DBHelper.COLUMN_HOBBY
                + " FROM " + DBHelper.TABLE_NAME, null
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String username = cursor.getString(0);
            if (username.equals(u)) {
                User ret =  new User(username, cursor.getString(1), cursor.getString(2), cursor.getString(3));
                cursor.close();
                return ret;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return null;
    }

    public void setCurrentUser(User user) {
        SharedPrefsUtils.putString(this, "username", user.getUsername());
        this.currentUser = user;
    }
}