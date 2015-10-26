package com.inz.patlas.presentation;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListFileActivity extends ListActivity {

    private String path;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int img_id=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_file);

        // Use the current directory as title
        path = "/";//"/storage";
        if (getIntent().hasExtra("path")) {
            path = getIntent().getStringExtra("path");
        }
        setTitle(path);

        // Read all files sorted into the values-array
        List values = new ArrayList();
        File dir = new File(path);
        if (!dir.canRead()) {
            setTitle(getTitle() + " (inaccessible)");
        }
        String[] list = dir.list();
        if (list != null) {
            for (String file : list) {
                if (!file.startsWith(".")) {

                    //TODO check if file is supported
                    if((img_id = SupportedFiles.checkFileSupport(file))>=0) {
                        values.add(file);
                        names.add(file);
                        int img = 0;
                        switch(img_id)
                        {
                            case 0:
                                img = R.mipmap.catalog_b_25;
                                break;
                            case 1:
                            case 2:
                                img = R.mipmap.ppt_24;
                                break;
                            case 3:
                                img = R.mipmap.pdf_24;
                                break;

                        }
                        images.add(img);

                    }
                }
            }
        }
        if(names.isEmpty()) {
            names.add("No supported files detected");
            images.add(R.mipmap.no_file_w_32);
        }

        Collections.sort(values);

        // Put the data into the list
//        ArrayAdapter adapter = new ArrayAdapter(this,
//                android.R.layout.simple_list_item_2, android.R.id.text1, values);

        FileListAdapter my_adapter = new FileListAdapter(this,names,images);
        setListAdapter(my_adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                Intent returnIntent = new Intent();
                returnIntent.putExtra("file_path", intent.getStringExtra("file_path"));
                returnIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                setResult(RESULT_OK, returnIntent);
                finish();

            }
        }
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String filename = (String) getListAdapter().getItem(position);
        if (path.endsWith(File.separator)) {
            filename = path + filename;
        } else {
            filename = path + File.separator + filename;
        }


        if (new File(filename).isDirectory()) {
            Intent intent = new Intent(this, ListFileActivity.class);
            intent.putExtra("path", filename);
            startActivityForResult(intent, 1);
        } else {
           // Toast.makeText(this, filename + " is not a directory", Toast.LENGTH_LONG).show();

            Intent returnIntent = new Intent();
            returnIntent.putExtra("file_path",filename);
            returnIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            setResult(RESULT_OK, returnIntent);
            finish();
            //tutaj mamy już lokalizacje pliku -> TODO sprawdzic czy plik ma dobre zozszerenie
        }
    }
}

