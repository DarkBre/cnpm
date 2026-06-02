package com.homestaybooking.model.domain;

public class Room {
    public String id;
    public String homestayId;
    public String name;
    public String type;
    public int capacity;
    public String description;
    public String imageUrl;
    public String status;

    public Room() {
    }

    public Room(String id, String homestayId, String name, String type, int capacity, String description, String imageUrl, String status) {
        this.id = id;
        this.homestayId = homestayId;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    @Override
    public String toString() {
        return name + " - " + type + " - " + capacity + " khach (" + status + ")";
    }
}
