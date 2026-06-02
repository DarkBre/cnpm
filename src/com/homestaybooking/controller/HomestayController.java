package com.homestaybooking.controller;

import com.homestaybooking.model.domain.Homestay;
import com.homestaybooking.model.enums.HomestayStatus;
import com.homestaybooking.model.service.HomestayService;

import java.util.List;

public class HomestayController {
    private final HomestayService homestayService;

    public HomestayController(HomestayService homestayService) {
        this.homestayService = homestayService;
    }

    public List<Homestay> list(String keyword) {
        return homestayService.listHomestays(keyword);
    }

    public List<Homestay> mine() {
        return homestayService.myHomestays();
    }

    public Homestay detail(String id) {
        return homestayService.detail(id);
    }

    public Homestay create(String name, String address, String type, String description) {
        return homestayService.create(name, address, type, description);
    }

    public Homestay update(String id, String name, String address, String type, String description, HomestayStatus status) {
        return homestayService.update(id, name, address, type, description, status);
    }

    public void delete(String id) {
        homestayService.delete(id);
    }
}
