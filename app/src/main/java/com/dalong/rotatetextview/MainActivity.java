package com.dalong.rotatetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<RotateEntity> mList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView rotateView=(ListView)findViewById(R.id.listview);
        adapter=new MyAdapter(getData(10),this);
        rotateView.setAdapter(adapter);

    }

    public List<RotateEntity> getData(int num){
        mList=new ArrayList<>();
        for (int i=0;i<num;i++){
            RotateEntity mRotateEntity=new RotateEntity(-50*(i+1)+"",45*i);
            mList.add(mRotateEntity);
        }
        return mList;
    }
}
