package com.homestaybooking.model.repository;

import com.homestaybooking.connection.Database;
import com.homestaybooking.model.domain.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomRepository {
    private static final List<Room> ROOMS = new ArrayList<>();

    static {
        ROOMS.add(new Room("r-pine", "h-dalat", "Pine View Deluxe", "Deluxe", 2, "Private balcony with pine hill view.", "", "AVAILABLE"));
        ROOMS.add(new Room("r-family", "h-dalat", "Family Attic Suite", "Family", 4, "Warm attic suite for families.", "", "AVAILABLE"));
        ROOMS.add(new Room("r-studio", "h-hanoi", "Old Quarter Studio", "Studio", 2, "Bright studio with kitchenette.", "", "AVAILABLE"));
    }

    public List<Room> findAll(String keyword) {
        String key = safe(keyword).toLowerCase();
        return ROOMS.stream()
                .filter(room -> !"DELETED".equals(room.status))
                .filter(room -> key.isBlank()
                        || safe(room.name).toLowerCase().contains(key)
                        || safe(room.type).toLowerCase().contains(key)
                        || safe(room.status).toLowerCase().contains(key))
                .toList();
    }

    public List<Room> findByHomestay(String homestayId) {
        return ROOMS.stream()
                .filter(room -> !"DELETED".equals(room.status))
                .filter(room -> room.homestayId.equals(homestayId))
                .toList();
    }

    public Optional<Room> findById(String id) {
        return ROOMS.stream()
                .filter(room -> !"DELETED".equals(room.status))
                .filter(room -> room.id.equals(id))
                .findFirst();
    }

    public Room save(Room room) {
        if (room.id == null || room.id.isBlank()) {
            room.id = Database.newId();
            ROOMS.add(room);
            return room;
        }
        for (int i = 0; i < ROOMS.size(); i++) {
            if (ROOMS.get(i).id.equals(room.id)) {
                ROOMS.set(i, room);
                return room;
            }
        }
        ROOMS.add(room);
        return room;
    }

    public void delete(String id) {
        Room room = findById(id).orElseThrow(() -> new IllegalArgumentException("Khong tim thay phong"));
        room.status = "DELETED";
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
