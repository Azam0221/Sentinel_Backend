package com.example.sentinal.sentinel_backend.repository;


import com.example.sentinal.sentinel_backend.enum_.RequestStatus;
import com.example.sentinal.sentinel_backend.model.PermissionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionRequest, String> {

          List<PermissionRequest> findByStatus(RequestStatus status);
}
