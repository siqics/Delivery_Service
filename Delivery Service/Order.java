package edu.gatech.cs6310;
import java.util.Map;
import java.util.TreeMap;

// I originally missed an attribute for the collection of lines, it's added now.
public class Order {
    private String order_id;
    private Store store_obj;
    private String drone_id_order;
    private int total_weight_order;
    private int total_price_order;
    public Customer customer_obj;
    private Map<String, Line> line_map = new TreeMap<>();

    public Order(Store a_store, String orderID, String droneID, Customer a_customer) {
        this.store_obj = a_store;
        this.order_id = orderID;
        this.drone_id_order = droneID;
        this.customer_obj = a_customer;
        this.total_weight_order = 0;
        this.total_price_order = 0;
    }

    public String get_order_id() {
        return this.order_id;
    }

    public void display_order() {
        System.out.println("orderID:" + order_id);
        if (this.line_map != null) {
            for (Map.Entry<String, Line> entry : this.line_map.entrySet()) {
                Line one_line = entry.getValue();
                String name = one_line.get_itemName_line();
                int total_quantity = one_line.get_line_quantity();
                int total_cost = one_line.get_line_cost();
                int total_weight = one_line.get_line_weight();
                System.out.println("item_name:" + name + ",total_quantity:" + total_quantity + ",total_cost:" + total_cost + ",total_weight:" + total_weight);
            }
        }
    }

    public boolean item_already_ordered(String an_item) {
        if (line_map.containsKey(an_item)) {
            System.out.println("ERROR:item_already_ordered");
            return true;
        } else {
            return false;
        }
    }

    public String get_drone_id_order() {
        return this.drone_id_order;
    }

    public boolean request_item(Store store_obj, String item_name, int some_quantity, int some_price, Drone some_drone) {
        Item an_item = store_obj.items_map.get(item_name);
        an_item.set_item_price(some_price);
        int line_weight = an_item.get_item_weight() * some_quantity;
        int line_cost = an_item.get_item_price() * some_quantity;
        if (some_drone.drone_has_enough_capacity(line_weight)) {
            this.total_weight_order += line_weight;
            this.total_price_order += line_cost;
            Line a_line = new Line(item_name, some_quantity, an_item.get_item_weight(), an_item.get_item_price());
            this.line_map.put(item_name, a_line);
            some_drone.cal_left_capacity(line_weight);
            return true;
        } else {
            return false;
        }
    }

    public int get_order_weight() {
        return this.total_weight_order;
    }

    public int get_order_price() {
        return this.total_price_order;
    }

    public void change_drone_id(String new_drone_id) {
        this.drone_id_order = new_drone_id;
    }
}
