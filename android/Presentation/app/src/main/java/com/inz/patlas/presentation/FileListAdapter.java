package com.inz.patlas.presentation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PatLas on 2015-10-24.
 */
public class FileListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList name;
    private final ArrayList imageId;

    public FileListAdapter(Activity context,ArrayList name, ArrayList imageId)
    {
        super(context, R.layout.image_list, name);
        this.context = context;
        this.name = name;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.image_list, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText((String)name.get(position));

        imageView.setImageResource((Integer)imageId.get(position));
        return rowView;

    }

}
