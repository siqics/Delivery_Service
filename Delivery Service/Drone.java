package edu.gatech.cs6310;
import java.util.Map;
import java.util.TreeMap;

// I originally missed attributes/operations to associate the 1:1 relationship between pilots and drones,
// attributes to hold the orders onboard, and missed operations for calculating and displaying the total
// weight for all the orders being delivered by each drone, they're added now.
public class Drone {
    private String store_name;
    private String drone_id;
    private int capacity;
    private int left_capacity;
    private int trips;
    public Pilot a_pilot;
    public int num_of_orders;
    public Map<String, Order> order_map_drone = new TreeMap<>();

    public Drone(String a_store, String dro_id, int capacity_num, int trips_num) {
        this.store_name = a_store;
        this.drone_id = dro_id;
        this.capacity = capacity_num;
        this.trips = trips_num;
        this.left_capacity = this.capacity;
        this.num_of_orders = this.order_map_drone.size();
    }

    public void display_one_drone() {
        System.out.print("droneID:" + this.drone_id + ",total_cap:" + this.capacity + ",num_orders:" + this.order_map_drone.size() + ",remaining_cap:" + this.left_capacity + ",trips_left:" + this.trips);
        if (this.a_pilot != null) {
            System.out.println(",flown_by:" + this.a_pilot.first_name + "_" + this.a_pilot.last_name);
        } else {
            System.out.print('\n');
        }
    }

    public void add_pilot(Pilot new_pilot) {
        this.a_pilot = new_pilot;
    }

    public boolean drone_has_enough_capacity(int some_weight) {
        if (this.left_capacity > some_weight) {
            return true;
        } else {
            return false;
        }
    }

    public void cal_left_capacity(int total_items_weight) {
        this.left_capacity -= total_items_weight;
    }

    public int get_trips() {
        return this.trips;
    }

    public void deliver_an_order(String order_id, int weight) {
        this.left_capacity += weight;
        this.trips -= 1;
        this.num_of_orders -= 1;
        this.order_map_drone.remove(order_id);
    }

    public void cancel_the_delivery(String order_id, int weight) {
        this.left_capacity += weight;
        this.order_map_drone.remove(order_id);
    }

    public int get_left_capacity() {
        return this.left_capacity;
    }

    public void take_away_an_order(String an_order_id, int weight) {
        this.order_map_drone.remove(an_order_id);
        this.left_capacity += weight;
        this.num_of_orders -= 1;
    }

    public void take_an_order(Order an_order_obj, int weight) {
        this.order_map_drone.put(an_order_obj.get_order_id(), an_order_obj);
        this.left_capacity -= weight;
        this.num_of_orders += 1;
    }
}





















