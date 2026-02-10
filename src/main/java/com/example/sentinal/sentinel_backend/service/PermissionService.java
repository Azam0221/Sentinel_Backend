package com.example.sentinal.sentinel_backend.service;


import com.example.sentinal.sentinel_backend.enum_.RequestStatus;
import com.example.sentinal.sentinel_backend.model.PermissionRequest;
import com.example.sentinal.sentinel_backend.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public ResponseEntity<PermissionRequest> createRequest(String agentId, String action){
        PermissionRequest request = new PermissionRequest();

        request.setId(UUID.randomUUID().toString());
        request.setAgentId(agentId);
        request.setAction(action);
        request.setStatus(RequestStatus.PENDING);
        request.setTimestamp(LocalDateTime.now());
        permissionRepository.save(request);

        return ResponseEntity.ok(request);
    }

    public ResponseEntity<List<PermissionRequest>> getPendingRequests() {
        List<PermissionRequest> allRequest =  permissionRepository.findByStatus(RequestStatus.PENDING);

        return ResponseEntity.ok(allRequest);
    }

    public ResponseEntity<PermissionRequest> updateStatus(String id, RequestStatus status) {
        Optional<PermissionRequest> request = permissionRepository.findById(id);
        PermissionRequest updatedPermission = new PermissionRequest();
        if (request.isPresent()) {
            PermissionRequest r = request.get();
            r.setStatus(status);
            updatedPermission =  permissionRepository.save(r);
        }

        return ResponseEntity.ok(updatedPermission);
    }

    public RequestStatus checkStatus(String id) {
        return permissionRepository.findById(id)
                .map(PermissionRequest::getStatus)
                .orElse(RequestStatus.DENIED);
    }

}
