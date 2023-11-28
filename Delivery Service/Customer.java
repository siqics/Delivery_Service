package edu.gatech.cs6310;
import java.util.Map;
import java.util.TreeMap;

// I originally missed an attribute to hold the collection of orders and operations to create
// a new order, they're added now.
public class Customer extends User{
    private String customer_account;
    private int rating;
    private int credit;
    private Map<String, Order> order_map_cus = new TreeMap<>();

    public Customer(String acc, String first, String last, String phone, int a_rating, int a_credit) {
        this.customer_account = acc;
        this.first_name = first;
        this.last_name = last;
        this.phone_number = phone;
        this.rating = a_rating;
        this.credit = a_credit;
    }

    public void display_customer() {
        System.out.println("name:" + this.first_name + "_" + this.last_name + ",phone:" + this.phone_number + ",rating:" + this.rating + ",credit:" + this.credit);
    }

    public void start_order(Order an_order) {
        String id = an_order.get_order_id();
        this.order_map_cus.put(id, an_order);
    }

    public boolean enough_credit(int an_orderline_price) {
        if (this.credit > an_orderline_price) {
            return true;
        } else {
            return false;
        }
    }

    public void complete_an_order(String order_id, int a_price) {
        this.credit -= a_price;
        this.order_map_cus.remove(order_id);
    }

    public void cancel_order_customer(String order_id, int a_price) {
        this.order_map_cus.remove(order_id);
    }
}
