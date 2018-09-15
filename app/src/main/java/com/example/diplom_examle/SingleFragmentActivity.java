package com.example.diplom_examle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Григорий Кисляков on 11.02.2018.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment =fm.findFragmentById(R.id.frame_content);
        if(fragment==null){
            fragment= createFragment();
            fm.beginTransaction()
                    .add(R.id.frame_content,fragment)
                    .commit();
        }
    }
}