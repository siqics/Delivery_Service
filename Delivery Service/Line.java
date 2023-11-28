package edu.gatech.cs6310;

// I originally created Line class as a parent class of the Item class, but I don't think that was a good idea anymore.
public class Line {
    private String item_name_line;
    private int quantity;
    private int line_weight;
    private int line_cost;
    private Item item_obj_line;

    public Line(String item, int a_quantity, int weight, int cost) {
        this.item_obj_line = new Item();
        this.item_name_line = item;
        this.quantity = a_quantity;
        this.line_weight = a_quantity * weight;
        this.line_cost = a_quantity * cost;
        this.item_obj_line.set_item_name_weight_price(item, weight, cost);
    }

    public int get_line_quantity() {
        return this.quantity;
    }

    public String get_itemName_line() {
        return this.item_name_line;
    }

    public int get_line_cost() {
        return this.line_cost;
    }

    public int get_line_weight() {
        return this.line_weight;
    }
}





