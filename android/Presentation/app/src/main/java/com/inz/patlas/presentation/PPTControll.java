package com.inz.patlas.presentation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.inz.patlas.presentation.stream.ControllCommands;
import com.inz.patlas.presentation.stream.Messanger;

public class PPTControll extends AppCompatActivity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    public Switch effect_sw = null;
    public TextView effect_tv = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt_controll);

        effect_sw = (Switch) findViewById(R.id.effect_sw);
        effect_tv = (TextView) findViewById(R.id.effect_tv);

        MainWindow.messanger.sendCommand(ControllCommands.F_START);
        effect_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked)
                    effect_tv.setText(R.string.effect_mode_off);
                else
                    effect_tv.setText(R.string.effect_mode_on);

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
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        if(!effect_sw.isChecked())
                            MainWindow.messanger.sendCommand(ControllCommands.F_PPREVP);
                        else
                            MainWindow.messanger.sendCommand(ControllCommands.F_PPREVE);

                        //Toast.makeText(this, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                    }

                    // Right to left swipe action
                    else
                    {
                        if(!effect_sw.isChecked())
                            MainWindow.messanger.sendCommand(ControllCommands.F_PNEXTP);
                        else
                            MainWindow.messanger.sendCommand(ControllCommands.F_PNEXTE);

                        //Toast.makeText(this, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                    }

                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
