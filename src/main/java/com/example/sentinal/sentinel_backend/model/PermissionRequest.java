package com.example.sentinal.sentinel_backend.model;


import com.example.sentinal.sentinel_backend.enum_.RequestStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "permission_requested")
@Data
public class PermissionRequest {

    @Id
    private String id;

    private String agentId;
    private String action;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDateTime timestamp;
}
