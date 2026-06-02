package com.homestaybooking.model.service;

import com.homestaybooking.model.domain.Homestay;
import com.homestaybooking.model.domain.User;
import com.homestaybooking.model.enums.HomestayStatus;
import com.homestaybooking.model.enums.UserRole;
import com.homestaybooking.model.repository.HomestayRepository;

import java.util.List;

public class HomestayService {
    private final HomestayRepository homestayRepository;
    private final UserService userService;

    public HomestayService(HomestayRepository homestayRepository, UserService userService) {
        this.homestayRepository = homestayRepository;
        this.userService = userService;
    }

    public List<Homestay> listHomestays(String keyword) {
        return homestayRepository.findAll(keyword);
    }

    public List<Homestay> myHomestays() {
        User user = userService.requireLogin();
        return homestayRepository.findByOwner(user.id);
    }

    public Homestay detail(String id) {
        return homestayRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Khong tim thay homestay"));
    }

    public Homestay create(String name, String address, String type, String description) {
        User user = userService.requireLogin();
        validate(name, address, type);
        userService.promoteToOwner(user.id);
        Homestay homestay = new Homestay(null, user.id, name.trim(), address.trim(), type.trim(), description, "", HomestayStatus.ACTIVE);
        return homestayRepository.save(homestay);
    }

    public Homestay update(String id, String name, String address, String type, String description, HomestayStatus status) {
        Homestay homestay = detail(id);
        requireManage(homestay);
        validate(name, address, type);
        homestay.name = name.trim();
        homestay.address = address.trim();
        homestay.type = type.trim();
        homestay.description = description;
        homestay.status = status == null ? HomestayStatus.ACTIVE : status;
        return homestayRepository.save(homestay);
    }

    public void delete(String id) {
        Homestay homestay = detail(id);
        requireManage(homestay);
        homestayRepository.delete(id);
    }

    public boolean canManage(Homestay homestay) {
        User user = userService.requireLogin();
        return user.role == UserRole.ADMIN || homestay.ownerId.equals(user.id);
    }

    private void requireManage(Homestay homestay) {
        if (!canManage(homestay)) {
            throw new IllegalStateException("Khong co quyen quan ly homestay nay");
        }
    }

    private void validate(String name, String address, String type) {
        if (name == null || name.isBlank() || address == null || address.isBlank() || type == null || type.isBlank()) {
            throw new IllegalArgumentException("Ten, dia chi va loai homestay la bat buoc");
        }
    }
}
