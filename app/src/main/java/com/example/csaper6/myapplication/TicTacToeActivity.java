package com.example.csaper6.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by g on 8/3/2016.
 */
public class TicTacToeActivity extends AppCompatActivity implements View.OnClickListener{
    private Button topLeftButton, topCenterButton, topRightButton;
    private Button centerLeftButton, centerCenterButton, centerRightButton;
    private Button bottomLeftButton, bottomCenterButton, bottomRightButton;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_tic_tac_toe);
        wireWidgets();
        setUpOnClickButtons();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.topLeftButton :
                Toast.makeText(TicTacToeActivity.this,"button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.topCenterButton :
                Toast.makeText(TicTacToeActivity.this,"button2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.topRightButton :
                Toast.makeText(TicTacToeActivity.this,"button3", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void wireWidgets(){
        topLeftButton = (Button) findViewById(R.id.topLeftButton);
        topCenterButton = (Button) findViewById(R.id.topCenterButton);
        topRightButton = (Button) findViewById(R.id.topRightButton);
        centerLeftButton = (Button) findViewById(R.id.centerLeftButton);
        centerCenterButton = (Button) findViewById(R.id.centerCenterButton);
        centerRightButton = (Button) findViewById(R.id.centerRightButton);
        bottomLeftButton = (Button) findViewById(R.id.bottomLeftButton);
        bottomCenterButton = (Button) findViewById(R.id.bottomCenterButton);
        bottomRightButton = (Button) findViewById(R.id.bottomRightButton);
    }

    public void setUpOnClickButtons(){
        topLeftButton.setOnClickListener(this);
        topCenterButton.setOnClickListener(this);
        topRightButton.setOnClickListener(this);
        centerLeftButton.setOnClickListener(this);
        centerCenterButton.setOnClickListener(this);
        centerRightButton.setOnClickListener(this);
        bottomLeftButton.setOnClickListener(this);
        bottomCenterButton.setOnClickListener(this);
        bottomRightButton.setOnClickListener(this);
    }


}
