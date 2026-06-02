package com.homestaybooking.controller;

import com.homestaybooking.model.domain.Room;
import com.homestaybooking.model.service.RoomService;

import java.util.List;

public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    public List<Room> list(String keyword) {
        return roomService.listRooms(keyword);
    }

    public List<Room> byHomestay(String homestayId) {
        return roomService.listByHomestay(homestayId);
    }

    public Room detail(String id) {
        return roomService.detail(id);
    }

    public Room create(String homestayId, String name, String type, int capacity, String description) {
        return roomService.create(homestayId, name, type, capacity, description);
    }

    public Room update(String id, String name, String type, int capacity, String description, String status) {
        return roomService.update(id, name, type, capacity, description, status);
    }

    public void delete(String id) {
        roomService.delete(id);
    }
}
