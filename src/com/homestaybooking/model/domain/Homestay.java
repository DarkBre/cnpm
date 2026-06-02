package com.homestaybooking.model.domain;

import com.homestaybooking.model.enums.HomestayStatus;

public class Homestay {
    public String id;
    public String ownerId;
    public String name;
    public String address;
    public String type;
    public String description;
    public String imageUrl;
    public HomestayStatus status;

    public Homestay() {
    }

    public Homestay(String id, String ownerId, String name, String address, String type, String description, String imageUrl, HomestayStatus status) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.type = type;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    @Override
    public String toString() {
        return name + " - " + address + " (" + status + ")";
    }
}
