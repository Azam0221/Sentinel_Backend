package com.example.sentinal.sentinel_backend.repository;


import com.example.sentinal.sentinel_backend.enum_.RequestStatus;
import com.example.sentinal.sentinel_backend.model.PermissionRequest;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class PermissionRepository {


    private final ConcurrentHashMap<String, PermissionRequest> db = new ConcurrentHashMap<>();

    public PermissionRequest save(PermissionRequest request) {
        db.put(request.getId(), request);
        return request;
    }

    public Optional<PermissionRequest> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    public List<PermissionRequest> findByStatus(RequestStatus status) {
        return db.values().stream()
                .filter(r -> r.getStatus() == status)
                .collect(Collectors.toList());
    }
}