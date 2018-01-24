package com.kanchan.groceryapp.groceryapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.kanchan.groceryapp.groceryapp.Data.DataBaseHandler;
import com.kanchan.groceryapp.groceryapp.Model.Grocery;
import com.kanchan.groceryapp.groceryapp.R;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryItem;
    private EditText quantity;
    private Button savebutton;
    private DataBaseHandler db;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db= new DataBaseHandler(this);
        byPassActivity();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                createPopupDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    private void createPopupDialog(){
        
        dialogBuilder=new AlertDialog.Builder(this);
        View view =getLayoutInflater().inflate(R.layout.popup, null);
        groceryItem=(EditText) view.findViewById(R.id.groceryItem);
        quantity= (EditText) view.findViewById(R.id.groceryQty);
        savebutton=(Button) view.findViewById(R.id.saveButton);
        
        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();
        
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!groceryItem.getText().toString().isEmpty() && (!quantity.getText().toString().isEmpty()))
                {
                    saveGroceryToDB(v);
                }
            }
        });
        
    }

    private void saveGroceryToDB(View v) {
        Grocery grocery = new Grocery();

        String newGroceryItem = groceryItem.getText().toString();
        String newQuantity = quantity.getText().toString();

        grocery.setName(newGroceryItem);
        grocery.setQuantity(newQuantity);
        //grocery.setDateItemAdded("oyfgghf");

        db.addGrocery(grocery);

        Snackbar.make(v,"Item saved !", Snackbar.LENGTH_LONG).show();
       Log.d("Item added ! ", String.valueOf(db.getGroceriesCount()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

                //Start ew Activity
               startActivity(new Intent(MainActivity.this, ListActivity.class));

            }
        }, 1000);
    }

    public  void byPassActivity(){

        if (db.getGroceriesCount() > 0){
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }
}
