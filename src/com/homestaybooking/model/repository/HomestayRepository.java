package com.homestaybooking.model.repository;

import com.homestaybooking.connection.Database;
import com.homestaybooking.model.domain.Homestay;
import com.homestaybooking.model.enums.HomestayStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomestayRepository {
    private static final List<Homestay> HOMESTAYS = new ArrayList<>();

    static {
        HOMESTAYS.add(new Homestay("h-dalat", "u-owner", "Da Lat Mountain Stay", "12 Tran Hung Dao, Da Lat", "Mountain Villa", "Cozy homestay near pine forest.", "", HomestayStatus.ACTIVE));
        HOMESTAYS.add(new Homestay("h-hanoi", "u-owner", "Hanoi Old Quarter Loft", "25 Hang Bac, Ha Noi", "City Loft", "Compact loft near Hoan Kiem lake.", "", HomestayStatus.ACTIVE));
    }

    public List<Homestay> findAll(String keyword) {
        String key = safe(keyword).toLowerCase();
        return HOMESTAYS.stream()
                .filter(homestay -> homestay.status != HomestayStatus.DELETED)
                .filter(homestay -> key.isBlank()
                        || safe(homestay.name).toLowerCase().contains(key)
                        || safe(homestay.address).toLowerCase().contains(key)
                        || safe(homestay.type).toLowerCase().contains(key))
                .toList();
    }

    public List<Homestay> findByOwner(String ownerId) {
        return HOMESTAYS.stream()
                .filter(homestay -> homestay.status != HomestayStatus.DELETED)
                .filter(homestay -> homestay.ownerId.equals(ownerId))
                .toList();
    }

    public Optional<Homestay> findById(String id) {
        return HOMESTAYS.stream()
                .filter(homestay -> homestay.status != HomestayStatus.DELETED)
                .filter(homestay -> homestay.id.equals(id))
                .findFirst();
    }

    public Homestay save(Homestay homestay) {
        if (homestay.id == null || homestay.id.isBlank()) {
            homestay.id = Database.newId();
            HOMESTAYS.add(homestay);
            return homestay;
        }
        for (int i = 0; i < HOMESTAYS.size(); i++) {
            if (HOMESTAYS.get(i).id.equals(homestay.id)) {
                HOMESTAYS.set(i, homestay);
                return homestay;
            }
        }
        HOMESTAYS.add(homestay);
        return homestay;
    }

    public void delete(String id) {
        Homestay homestay = findById(id).orElseThrow(() -> new IllegalArgumentException("Khong tim thay homestay"));
        homestay.status = HomestayStatus.DELETED;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
