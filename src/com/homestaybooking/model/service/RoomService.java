package com.homestaybooking.model.service;

import com.homestaybooking.model.domain.Homestay;
import com.homestaybooking.model.domain.Room;
import com.homestaybooking.model.repository.HomestayRepository;
import com.homestaybooking.model.repository.RoomRepository;

import java.util.List;

public class RoomService {
    private final RoomRepository roomRepository;
    private final HomestayRepository homestayRepository;
    private final HomestayService homestayService;

    public RoomService(RoomRepository roomRepository, HomestayRepository homestayRepository, HomestayService homestayService) {
        this.roomRepository = roomRepository;
        this.homestayRepository = homestayRepository;
        this.homestayService = homestayService;
    }

    public List<Room> listRooms(String keyword) {
        return roomRepository.findAll(keyword);
    }

    public List<Room> listByHomestay(String homestayId) {
        return roomRepository.findByHomestay(homestayId);
    }

    public Room detail(String id) {
        return roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng"));
    }

    public Room create(String homestayId, String name, String type, int capacity, String description) {
        Homestay homestay = homestayRepository.findById(homestayId).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy homestay"));
        if (!homestayService.canManage(homestay)) {
            throw new IllegalStateException("Không có quyền tạo phòng cho homestay này");
        }
        validate(name, type, capacity);
        return roomRepository.save(new Room(null, homestayId, name.trim(), type.trim(), capacity, description, "", "AVAILABLE"));
    }

    public Room update(String id, String name, String type, int capacity, String description, String status) {
        Room room = detail(id);
        Homestay homestay = homestayRepository.findById(room.homestayId).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy homestay"));
        if (!homestayService.canManage(homestay)) {
            throw new IllegalStateException("Không có quyền cập nhật phòng này");
        }
        validate(name, type, capacity);
        room.name = name.trim();
        room.type = type.trim();
        room.capacity = capacity;
        room.description = description;
        room.status = status == null || status.isBlank() ? "AVAILABLE" : status;
        return roomRepository.save(room);
    }

    public void delete(String id) {
        Room room = detail(id);
        Homestay homestay = homestayRepository.findById(room.homestayId).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy homestay"));
        if (!homestayService.canManage(homestay)) {
            throw new IllegalStateException("Không có quyền xóa phòng này");
        }
        roomRepository.delete(id);
    }

    private void validate(String name, String type, int capacity) {
        if (name == null || name.isBlank() || type == null || type.isBlank()) {
            throw new IllegalArgumentException("Tên phòng và loại phòng là bắt buộc");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Sức chứa phải lớn hơn 0");
        }
    }
}
