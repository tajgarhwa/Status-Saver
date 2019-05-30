package com.statussaver.chamiappslk.statussaver.activities;

import android.statussaver.com.statussaver.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.statussaver.chamiappslk.statussaver.DialogFragment.SelectDirectoryDialogFragment;

public class SettingsActivity extends AppCompatActivity {


    RelativeLayout idimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        idimg = findViewById(R.id.idimg);

        idimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDirectoryDialogFragment selectDirectoryDialogFragment =new SelectDirectoryDialogFragment();
                selectDirectoryDialogFragment.setContext(getApplicationContext());
                selectDirectoryDialogFragment.show(getSupportFragmentManager(), "SelectDirectoryDialogFragment");

            }
        });
    }
}
