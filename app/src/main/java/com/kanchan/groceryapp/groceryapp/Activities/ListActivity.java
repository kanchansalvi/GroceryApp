package com.kanchan.groceryapp.groceryapp.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.os.Build;

import com.kanchan.groceryapp.groceryapp.Data.DataBaseHandler;
import com.kanchan.groceryapp.groceryapp.Model.Grocery;
import com.kanchan.groceryapp.groceryapp.R;
import com.kanchan.groceryapp.groceryapp.UI.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  RecyclerView.Adapter recyclerViewAdapter;
    private List<Grocery> groceryList;
    private List<Grocery> listItems;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Build b = new Build();
        Log.d("GANESH"+" Borad : ", b.BOARD);
        Log.d("GANESH" +" DEVICE", b.DEVICE);
        Log.d("GANESH" +" MODEL", b.MODEL);
        Log.d("GANESH"+" PRODUCT", b.PRODUCT);
        Log.d("GANESH"+" FINGERPRINT", b.FINGERPRINT);
        Log.d("GANESH"+" ID", b.ID);
        Log.d("GANESH"+" HOST", b.HOST);
        Log.d("GANESH"+" BRAND", b.BRAND);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        db= new DataBaseHandler(this);

        recyclerView = (RecyclerView)  findViewById(R.id.recyclerviewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = new ArrayList<>();
        listItems = new ArrayList<>();

        //Get Items from database;
       groceryList= db.getAllGroceries();

        for (Grocery c : groceryList){
            Grocery grocery = new Grocery();
            grocery.setName(c.getName());
            grocery.setQuantity("Quantity : " + c.getQuantity());
            grocery.setDateItemAdded("Added on : " + c.getDateItemAdded());

            //Log.d("name : " , c.getName());

            listItems.add(grocery);

        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

}
