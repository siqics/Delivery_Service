package edu.gatech.cs6310;
import java.util.Map;
import java.util.TreeMap;

// I originally missed the attributes to hold the collections of items, drones and orders, they're added now.
public class Store {
    private String store_name;
    private int revenue;
    private int purchases;
    private int overloads;
    private int transfers;
    public Map<String, Item> items_map = new TreeMap<>();
    public Map<String, Drone> drone_map = new TreeMap<>();
    public Map<String, Order> order_map = new TreeMap<>();

    public Store(String store_name, int revenue) {
        this.store_name = store_name;
        this.revenue = revenue;
        this.purchases = 0;
        this.overloads = 0;
        this.transfers = 0;
    }

    public String get_store_name() {
        return store_name;
    }

    public void display_store() {
        System.out.println("name:" + this.store_name + ",revenue:" + this.revenue);
    }

    // This function is to add an item to the catalog.
    public boolean sell_item(String one_item_name, int one_weight) {
        if (items_map.containsKey(one_item_name)) {
            return false;
        } else {
            Item an_item = new Item();
            an_item.set_item_name_weight(one_item_name, one_weight);
            this.items_map.put(one_item_name, an_item);
            return true;
        }
    }

    public void display_items() {
        if (!(this.items_map.isEmpty())) {
            for (Map.Entry<String, Item> entry : this.items_map.entrySet()) {
                String an_item_name = entry.getKey();
                Item one_item = entry.getValue();
                int a_weight = one_item.get_item_weight();
                System.out.println(an_item_name + "," + a_weight);
            }
        }
    }

    public void add_drone(String a_drone_id, Drone a_drone) {
        this.drone_map.put(a_drone_id, a_drone);
    }

    public void display_drones() {
        if (!(this.drone_map.isEmpty())) {
            for (Map.Entry<String, Drone> entry : this.drone_map.entrySet()) {
                Drone one_drone = entry.getValue();
                one_drone.display_one_drone();
            }
        }
    }

    public boolean order_if_exist(String an_order_acc) {
        if (order_map.containsKey(an_order_acc)) {
            System.out.println("ERROR:order_identifier_already_exists");
            return true;
        } else {
            return false;
        }
    }

    public boolean drone_if_exist(String drone_idnumber) {
        if (drone_map.containsKey(drone_idnumber)) {
            return true;
        } else {
            System.out.println("ERROR:drone_identifier_does_not_exist");
            return false;
        }
    }

    public void add_order(Order an_order) {
        String id = an_order.get_order_id();
        this.order_map.put(id, an_order);
    }

    public void display_orders() {
        for(Map.Entry<String, Order> entry : this.order_map.entrySet()) {
            Order order_obj = entry.getValue();
            order_obj.display_order();
        }
    }

    public void complete_an_order_inStore(String order_id, int a_price) {
        this.revenue += a_price;
        this.order_map.remove(order_id);
    }

    public void cancel_order_store(String order_id) {
        this.order_map.remove(order_id);
    }

    public void add_purchase() {
        this.purchases += 1;
    }

    public void add_transfer_times() {
        this.transfers += 1;
    }

    public void update_overloads(int overload_orders) {
        this.overloads += overload_orders;
    }

    public void display_efficiency() {
        System.out.println("name:" + this.store_name + ",purchases:" + this.purchases + ",overloads:" + this.overloads + ",transfers:" + this.transfers);
    }
}
