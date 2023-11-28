package edu.gatech.cs6310;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

public class DeliveryService {

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        Map<String, Store> store_map = new TreeMap<>();
        Map<String, Pilot> pilot_account_map = new TreeMap<>();
        Map<String, Pilot> pilot_license_map = new TreeMap<>();
        Map<String, Customer> customer_map = new TreeMap<>();


        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                // Add new store's information.
                if (tokens[0].equals("make_store")) {
                    int revenue = Integer.parseInt(tokens[2]);
                    if (store_map.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_already_exists");
                    } else {
                        Store a_store = new Store(tokens[1], revenue);
                        store_map.put(a_store.get_store_name(), a_store);
                        System.out.println("OK:change_completed");
                    }

                // Display information about all the stores that have been created.
                } else if (tokens[0].equals("display_stores")) {
                    for (Map.Entry<String, Store> entry: store_map.entrySet()) {
                        Store a_store = entry.getValue();
                        a_store.display_store();
                    }
                    System.out.println("OK:display_completed");

                //Add the item provided to the “catalog” of items available to be requested and purchased from the store.
                } else if (tokens[0].equals("sell_item")) {
                    if (store_map.containsKey(tokens[1])) {
                        Store a_store = store_map.get(tokens[1]);
                        int weight = Integer.parseInt(tokens[3]);
                        if (a_store.sell_item(tokens[2], weight)) {
                            System.out.println("OK:change_completed");
                        }
                        else {
                            System.out.println("ERROR:item_identifier_already_exists");
                        }
                    }
                    else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }

                // Display the information about all the items that are available for request/purchase at a specific store.
                } else if (tokens[0].equals("display_items")) {
                    if (store_map.containsKey(tokens[1])) {
                        Store a_store = store_map.get(tokens[1]);
                        a_store.display_items();
                        System.out.println("OK:display_completed");
                    }
                    else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }


                // Create a pilot who could fly a drone later to support grocery deliveries.
                } else if (tokens[0].equals("make_pilot")) {
                    int experience_number = Integer.parseInt(tokens[7]);
                    if (pilot_account_map.containsKey(tokens[1])) {
                        System.out.println("ERROR:pilot_identifier_already_exists");
                    } else if (pilot_license_map.containsKey(tokens[6])) {
                        System.out.println("ERROR:pilot_license_already_exists");
                    } else {
                        Pilot a_pilot = new Pilot(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], experience_number);
                        pilot_account_map.put(tokens[1], a_pilot);
                        pilot_license_map.put(tokens[6], a_pilot);
                        System.out.println("OK:change_completed");
                    }


                // Display the information about all the pilots who’ve been introduced in the system.
                } else if (tokens[0].equals("display_pilots")) {
                    for (Map.Entry<String, Pilot> entry : pilot_account_map.entrySet()) {
                        Pilot a_pilot = entry.getValue();
                        a_pilot.display_pilot();
                    }
                    System.out.println("OK:display_completed");

                // Create a drone that can be used to deliver groceries to the appropriate customer when an order has been purchased.
                } else if (tokens[0].equals("make_drone")) {
                    String a_store = tokens[1];
                    String the_drone_id = tokens[2];
                    int the_capacity = Integer.parseInt(tokens[3]);
                    int left_trips = Integer.parseInt(tokens[4]);
                    if (store_map.containsKey(a_store)) {
                        Store one_store = store_map.get(a_store);
                        if (one_store.drone_map.containsKey(the_drone_id)) {
                            System.out.println("ERROR:drone_identifier_already_exists");
                        } else {
                            Drone some_drone = new Drone(a_store, the_drone_id, the_capacity, left_trips);
                            one_store.add_drone(the_drone_id, some_drone);
                            System.out.println("OK:change_completed");
                        }
                    } else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }

                // Display the information about all the drones that can be used to deliver grocery orders for a specific store.
                } else if (tokens[0].equals("display_drones")) {
                    if (store_map.containsKey(tokens[1])) {
                        Store some_store = store_map.get(tokens[1]);
                        some_store.display_drones();
                        System.out.println("OK:display_completed");
                    } else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }

                // Assign a given pilot to take control of a given drone.
                } else if (tokens[0].equals("fly_drone")) {
                    String a_store = tokens[1];
                    String a_drone = tokens[2];
                    String a_pilot = tokens[3];
                    if (store_map.containsKey(a_store)) {
                        Store store_obj = store_map.get(a_store);
                        if (store_obj.drone_map.containsKey(a_drone)) {
                            if (pilot_account_map.containsKey(a_pilot)) {
                                Drone exist_drone = store_obj.drone_map.get(a_drone);
                                Pilot exist_pilot = pilot_account_map.get(a_pilot);
                                exist_drone.add_pilot(exist_pilot);
                                exist_pilot.fly_drone(exist_drone);
                                System.out.println("OK:change_completed");
                            } else {
                                System.out.println("ERROR:pilot_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:drone_identifier_does_not_exist");
                        }
                    } else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }

                // Create a customer who can start orders, request items and eventually purchase (or cancel) those orders.
                } else if (tokens[0].equals("make_customer")) {
                    int one_rating = Integer.parseInt(tokens[5]);
                    int one_credit = Integer.parseInt(tokens[6]);
                    if (customer_map.containsKey(tokens[1])) {
                        System.out.println("ERROR:customer_identifier_already_exists");
                    } else {
                        Customer customer_obj = new Customer(tokens[1], tokens[2], tokens[3], tokens[4], one_rating, one_credit);
                        customer_map.put(tokens[1], customer_obj);
                        System.out.println("OK:change_completed");
                    }

                // Displays all the customers who have been introduced in the system.
                } else if (tokens[0].equals("display_customers")) {
                    for (Map.Entry<String, Customer> entry : customer_map.entrySet()) {
                        Customer one_cus = entry.getValue();
                        one_cus.display_customer();
                    }
                    System.out.println("OK:display_completed");

                // Create the initial “stub” for an order at a given store for a given customer.
                } else if (tokens[0].equals("start_order")) {
                    if (store_map.containsKey(tokens[1])) {
                        Store store_obj = store_map.get(tokens[1]);
                        if (!store_obj.order_if_exist(tokens[2])) {
                            if (store_obj.drone_if_exist(tokens[3])) {
                                if (customer_map.containsKey(tokens[4])) {
                                    Customer customer_obj = customer_map.get(tokens[4]);
                                    Order order_obj = new Order(store_obj, tokens[2], tokens[3], customer_obj);
                                    store_obj.add_order(order_obj);
                                    customer_obj.start_order(order_obj);
                                    Drone the_drone = store_obj.drone_map.get(tokens[3]);
                                    the_drone.order_map_drone.put(tokens[2], order_obj);

                                    System.out.println("OK:change_completed");
                                } else {
                                    System.out.println("ERROR:customer_identifier_does_not_exist");
                                }
                            }
                        }
                    }else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }

                // Display information about all the orders at a given store.
                } else if (tokens[0].equals("display_orders")) {
                    if (store_map.containsKey(tokens[1])) {
                        Store store_obj = store_map.get(tokens[1]);
                        store_obj.display_orders();
                        System.out.println("OK:display_completed");
                    } else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }

                // Add an item to the designated order.
                } else if (tokens[0].equals("request_item")) {
                    if (store_map.containsKey(tokens[1])) {
                        Store a_store = store_map.get(tokens[1]);
                        if (a_store.order_map.containsKey(tokens[2])) {
                            if (a_store.items_map.containsKey(tokens[3])) {
                                Order an_order = a_store.order_map.get(tokens[2]);
                                if (!an_order.item_already_ordered(tokens[3])) {
                                    int the_quantity = Integer.parseInt(tokens[4]);
                                    int unit_price = Integer.parseInt(tokens[5]);
                                    int orderline_price = the_quantity * unit_price;
                                    if (an_order.customer_obj.enough_credit(orderline_price)) {
                                        Drone the_drone = a_store.drone_map.get(an_order.get_drone_id_order());
                                        String an_item_name = tokens[3];
                                        if (an_order.request_item(a_store, an_item_name, the_quantity, unit_price, the_drone)) {
                                            System.out.println("OK:change_completed");
                                        } else {
                                            System.out.println("ERROR:drone_cant_carry_new_item");
                                        }
                                    } else {
                                        System.out.println("ERROR:customer_cant_afford_new_item");
                                    }
                                }
                            } else {
                                System.out.println("ERROR:item_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        }
                    } else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }

                // Complete the purchase of the order and the delivery of the groceries to the appropriate customer.
                } else if (tokens[0].equals("purchase_order")) {
                    if (store_map.containsKey(tokens[1])) {
                        Store the_store = store_map.get(tokens[1]);
                        if (the_store.order_map.containsKey(tokens[2])) {
                            Order the_order = the_store.order_map.get(tokens[2]);
                            Customer the_customer = the_order.customer_obj;
                            int order_weight = the_order.get_order_weight();
                            int order_price = the_order.get_order_price();
                            String drone_id = the_order.get_drone_id_order();
                            Drone the_drone = the_store.drone_map.get(drone_id);
                            if (the_drone.a_pilot != null) {
                                if (the_drone.get_trips() > 0) {
                                    the_customer.complete_an_order(tokens[2], order_price);
                                    the_store.complete_an_order_inStore(tokens[2], order_price);
                                    the_drone.deliver_an_order(tokens[2], order_weight);
                                    int overload_orders = the_drone.order_map_drone.size();
                                    the_store.update_overloads(overload_orders);
                                    the_drone.a_pilot.deliver_order_pilot();
                                    the_store.add_purchase();
                                    System.out.println("OK:change_completed");
                                } else {
                                    System.out.println("ERROR:drone_needs_fuel");
                                }
                            } else {
                                System.out.println("ERROR:drone_needs_pilot");
                            }
                        } else {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        }
                    } else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }

                // Remove the order from the system without otherwise changing the system’s state.
                } else if (tokens[0].equals("cancel_order")) {
                    if (store_map.containsKey(tokens[1])) {
                        Store the_store = store_map.get(tokens[1]);
                        if (the_store.order_map.containsKey(tokens[2])) {
                            Order the_order = the_store.order_map.get(tokens[2]);
                            Customer the_customer = the_order.customer_obj;
                            String drone_id = the_order.get_drone_id_order();
                            Drone the_drone = the_store.drone_map.get(drone_id);
                            the_customer.cancel_order_customer(tokens[2], the_order.get_order_price());
                            the_store.cancel_order_store(tokens[2]);
                            the_drone.cancel_the_delivery(tokens[2], the_order.get_order_weight());
                            System.out.println("OK:change_completed");
                        } else {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        }
                    } else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }

                // This function would cause the order being referenced to be moved to a new drone controlled by the same store.
                } else if (tokens[0].equals("transfer_order")) {
                    if (store_map.containsKey(tokens[1])) {
                        Store the_store = store_map.get(tokens[1]);
                        if (the_store.order_map.containsKey(tokens[2])) {
                            if (the_store.drone_map.containsKey(tokens[3])) {
                                Order the_order = the_store.order_map.get(tokens[2]);
                                Drone the_new_drone = the_store.drone_map.get(tokens[3]);
                                int available_capacity = the_new_drone.get_left_capacity();
                                int order_weight = the_order.get_order_weight();
                                if (available_capacity > order_weight) {
                                    if (the_order.get_drone_id_order().equals(tokens[3])) {
                                        System.out.println("OK:new_drone_is_current_drone_no_change");
                                    } else {
                                        String the_old_drone = the_order.get_drone_id_order();
                                        int the_order_weight = the_order.get_order_weight();
                                        the_new_drone.take_an_order(the_order, the_order_weight);
                                        the_order.change_drone_id(tokens[3]);
                                        Drone the_old_drone_obj = the_store.drone_map.get(the_old_drone);
                                        the_old_drone_obj.take_away_an_order(tokens[2], the_order_weight);
                                        the_store.add_transfer_times();
                                        System.out.println("OK:change_completed");
                                    }
                                } else {
                                    System.out.println("ERROR:new_drone_does_not_have_enough_capacity");
                                }
                            } else {
                                System.out.println("ERROR:drone_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        }
                    } else {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                } else if (tokens[0].equals("display_efficiency")) {
                    for (Map.Entry<String, Store> entry: store_map.entrySet()) {
                        Store a_store = entry.getValue();
                        a_store.display_efficiency();
                    }
                    System.out.println("OK:display_completed");
                } else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;
                } else {
                    if (tokens[0].charAt(0) == '/') {
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }
        System.out.println("simulation terminated");
        commandLineInput.close();
    }
}
