package com.homestaybooking.model.repository;

import com.homestaybooking.connection.Database;
import com.homestaybooking.model.domain.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomRepository {
    private static final List<Room> ROOMS = new ArrayList<>();

    static {
        ROOMS.add(new Room("r-pine", "h-dalat", "Phòng Deluxe nhìn đồi thông", "Deluxe", 2, "Ban công riêng nhìn ra đồi thông, phù hợp cho hai khách.", "", "AVAILABLE"));
        ROOMS.add(new Room("r-family", "h-dalat", "Phòng áp mái gia đình", "Gia đình", 4, "Phòng áp mái ấm cúng dành cho gia đình.", "", "AVAILABLE"));
        ROOMS.add(new Room("r-studio", "h-hanoi", "Studio Phố Cổ", "Studio", 2, "Studio sáng sủa, có bếp nhỏ và gần trung tâm.", "", "AVAILABLE"));
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
        Room room = findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng"));
        room.status = "DELETED";
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
