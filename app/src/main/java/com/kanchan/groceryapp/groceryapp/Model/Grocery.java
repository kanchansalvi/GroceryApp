package com.kanchan.groceryapp.groceryapp.Model;

/**
 * Created by Ganesh-Salvi on 12/22/2017.
 */

public class Grocery {

    private String name;
    private  String quantity;
    private String dateItemAdded;
    private int id;

    public Grocery(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;
    }

    public Grocery(String name, String quantity, String dateItemAdded, int id) {
        this.name = name;
        this.quantity = quantity;
        this.dateItemAdded = dateItemAdded;
        this.id = id;

    }


}

