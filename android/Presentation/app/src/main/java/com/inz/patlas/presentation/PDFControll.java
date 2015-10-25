package com.inz.patlas.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.inz.patlas.presentation.stream.ControllCommands;

public class PDFControll extends AppCompatActivity {

    private float x1,x2,y1,y2;
    static final int MIN_X_DISTANCE = 100;
    static final int MIN_Y_DISTANCE = 100;
    public Switch effect_sw = null;
    public TextView effect_tv = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_controll);

        effect_sw = (Switch) findViewById(R.id.effect_sw);
        effect_tv = (TextView) findViewById(R.id.effect_tv);

        MainWindow.messanger.sendCommand(ControllCommands.F_START);
        effect_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked) {
                    effect_tv.setText(R.string.effect_mode_off);
                    MainWindow.messanger.sendCommand(ControllCommands.F_DFULL);
                }
                else {
                    effect_tv.setText(R.string.effect_mode_on);
                    MainWindow.messanger.sendCommand(ControllCommands.F_DFULL);
                }

            }
        });

        effect_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effect_sw.setChecked(!effect_sw.isChecked());
            }
        });


    }


    @Override
    protected void onStop(){
        super.onStop();
       // MainWindow.messanger.sendCommand(ControllCommands.F_DEXIT);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //MainWindow.messanger.sendCommand(ControllCommands.F_DEXIT);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float deltaX = x2 - x1;
                float deltaY = y2 - y1;

                if (Math.abs(deltaX) > MIN_X_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        MainWindow.messanger.sendCommand(ControllCommands.F_DPREV);

                        //Toast.makeText(this, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                    }

                    // Right to left swipe action
                    else
                    {
                        MainWindow.messanger.sendCommand(ControllCommands.F_DNEXT);

                        //Toast.makeText(this, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                    }

                }
                else if (Math.abs(deltaY) > MIN_Y_DISTANCE)
                {
                    if (y2>y1)
                    {
                        MainWindow.messanger.sendCommand(ControllCommands.F_DPDOWN);
                    }
                    else
                    {
                        MainWindow.messanger.sendCommand(ControllCommands.F_DPUP);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
