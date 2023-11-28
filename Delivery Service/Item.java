package edu.gatech.cs6310;

// I originally put the attribute: item_name in another class, but I'm putting it in this Item class now.
public class Item {
    private String item_name;
    private int item_weight;
    private int item_price;

    public Item() {
        this.item_name = null;
        this.item_weight = 0;
        this.item_price = 0;
    }

    public void set_item_price(int a_price) {
        this.item_price = a_price;
    }

    public int get_item_price() {
        return this.item_price;
    }

    public void set_item_name_weight_price(String some_name, int some_weight, int some_price) {
        this.item_name = some_name;
        this.item_weight = some_weight;
        this.item_price = some_price;
    }

    public void set_item_name_weight(String some_name, int some_weight) {
        this.item_name = some_name;
        this.item_weight = some_weight;
    }

    public int get_item_weight() {
        return this.item_weight;
    }
}
