package com.inz.patlas.presentation;

import android.os.Parcel;
import android.os.Parcelable;

import com.inz.patlas.presentation.stream.Messanger;

import java.util.ArrayList;

/**
 * Created by PatLas on 2015-10-25.
 */
public class MyParcelable implements Parcelable {

    private Messanger messanger = null;

    private ArrayList<Messanger> myarray = new ArrayList<>();
    public MyParcelable(Messanger m){
        messanger = m;
        myarray.add(messanger);
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        //dest.writeTypedList(myarray);
    }

}
