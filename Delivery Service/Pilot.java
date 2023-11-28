package edu.gatech.cs6310;

// I originally missed attributes/operations to associate the 1:1 relationship between pilots and drones,
// they're added now.
public class Pilot extends User {
    private String pilot_account;
    private String tax;
    private String license;
    private int experience;
    private Drone drone_obj;

    public Pilot(String account, String first, String last, String phone, String taxID, String licenseID, int experience_times) {
        this.pilot_account = account;
        this.first_name = first;
        this.last_name = last;
        this.phone_number = phone;
        this.tax = taxID;
        this.license = licenseID;
        this.experience = experience_times;
    }

    public void display_pilot() {
        System.out.println("name:" + this.first_name + "_" + this.last_name + ",phone:" + phone_number + ",taxID:" + tax + ",licenseID:" + license + ",experience:" + experience);
    }

    public void fly_drone(Drone new_drone) {
        this.drone_obj = new_drone;
    }

    public void deliver_order_pilot() {
        this.experience += 1;
    }
}
