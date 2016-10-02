package com.mallock.pointless.tabfragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mallock.pointless.MainActivity;
import com.mallock.pointless.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static com.mallock.pointless.datautils.Constants.FILE_EXTERNAL_PRIVATE;
import static com.mallock.pointless.datautils.Constants.FILE_EXTERNAL_PUBLIC;
import static com.mallock.pointless.datautils.Constants.FILE_INTERNAL;

/**
 * Created by Mallock on 02-10-2016.
 */

public class FileFragment extends Fragment {
    private static final String TAG = "FILEFRAGMENT";
    int internal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.internal_file_fragment, container, false);
        final Button saveButton = (Button) view.findViewById(R.id.save_button);
        final EditText et = (EditText) view.findViewById(R.id.editText);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(et.getText().toString());
            }
        });
        TextView advice = (TextView) view.findViewById(R.id.storage_advice_text);
        internal = getArguments().getInt("internal");
        switch (internal) {
            case FILE_INTERNAL:
                break;
            case FILE_EXTERNAL_PUBLIC:
                advice.setText(R.string.external_storage_public_advice);
                break;
            case FILE_EXTERNAL_PRIVATE:
                advice.setText(R.string.external_storage_private_advice);
                break;
            default:
                advice.setText("ERROR.");
        }
        return view;
    }

    /**
     * write data to file
     */
    public void saveData(String string) {
        String filename = ((MainActivity) getActivity()).currentUser.getUsername();
        Toast.makeText(getActivity(), "Data saved to " + (internal == FILE_INTERNAL ? "internal" : "external") + " storage", Toast.LENGTH_SHORT).show();
        if (FILE_INTERNAL == internal) {
            FileOutputStream outputStream;

            try {
                outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (isExternalStorageWritable()) {
                File file = (FILE_EXTERNAL_PRIVATE == internal) ? getPrivateFile(filename) : getPublicFile(filename);
                
            }
        }
    }

    public File getPublicFile(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                "/Pointless/"), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

    public File getPrivateFile(String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(getActivity().getExternalFilesDir("/Pointless/"), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;

    }

    /**
     * Read data from file
     */
    public void readData() {

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
