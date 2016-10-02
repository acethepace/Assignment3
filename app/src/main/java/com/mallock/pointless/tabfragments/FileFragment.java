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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        internal = getArguments().getInt("internal");
        final Button saveButton = (Button) view.findViewById(R.id.save_button);
        final EditText et = (EditText) view.findViewById(R.id.editText);
        String data = readData();
        if (data != null && data != "") {
            et.setText(data);
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(et.getText().toString());
            }
        });
        TextView advice = (TextView) view.findViewById(R.id.storage_advice_text);
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
        String filename = ((MainActivity) getActivity()).currentUser.getUsername() + ".txt";
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
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(string.getBytes());
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "not writable", Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(getActivity(), "Data saved to " + (internal == FILE_INTERNAL ? "internal" : "external") + " storage", Toast.LENGTH_SHORT).show();

    }

    public File getPublicFile(String albumName) {
        // Get the directory for the user's public pictures "directory.
        File dir1 = Environment.getExternalStorageDirectory();

        File dir = new File(dir1.getAbsolutePath()+"/Pointless");
        if (!dir.exists()) {
            dir.mkdirs();
            Log.e(TAG, "Directory not created");
        }
        File file = new File(dir, albumName);
        return file;
    }

    public File getPrivateFile(String filename) {
        // Get the directory for the app's private pictures directory.
        File dir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"/Pointless");
        if (!dir.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        File file = new File(dir, filename);
        return file;

    }

    /**
     * Read data from file
     */
    public String readData() {
        StringBuilder stringBuilder = new StringBuilder();
        String filename = ((MainActivity) getActivity()).currentUser.getUsername() + ".txt";
        Toast.makeText(getActivity(), "Data read from " + (internal == FILE_INTERNAL ? "internal" : "external") + " storage", Toast.LENGTH_SHORT).show();
        if (FILE_INTERNAL == internal) {
            try {
                FileInputStream fis = getActivity().openFileInput(filename);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (isExternalStorageReadable()) {
                File file = (FILE_EXTERNAL_PRIVATE == internal) ? getPrivateFile(filename) : getPublicFile(filename);
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line = br.readLine();

                    while (line != null) {
                        stringBuilder.append(line);
                        stringBuilder.append(System.lineSeparator());
                        line = br.readLine();
                    }

                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "not writable", Toast.LENGTH_SHORT).show();
            }
        }
        return stringBuilder.toString();
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
