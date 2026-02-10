package com.example.sentinal.sentinel_backend.controller;


import com.example.sentinal.sentinel_backend.enum_.RequestStatus;
import com.example.sentinal.sentinel_backend.model.PermissionRequest;
import com.example.sentinal.sentinel_backend.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/permission")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("/agent/ask")
    public ResponseEntity<PermissionRequest> askPermission(@RequestBody Map<String, String> payload) {

        return permissionService.createRequest(payload.get("agentId"), payload.get("action"));
    }


    @GetMapping("/agent/status/{requestId}")
    public Map<String, String> checkStatus(@PathVariable String requestId) {
        RequestStatus status = permissionService.checkStatus(requestId);
        return Map.of("status", status.toString());
    }

    @GetMapping("/mobile/pending")
    public ResponseEntity<List<PermissionRequest>> getPending() {
        return permissionService.getPendingRequests();
    }


    @PostMapping("/mobile/decide/{requestId}")
    public ResponseEntity<PermissionRequest> decideRequest(
            @PathVariable String requestId,
            @RequestParam RequestStatus status
    ) {
        return permissionService.updateStatus(requestId, status);
    }
}
