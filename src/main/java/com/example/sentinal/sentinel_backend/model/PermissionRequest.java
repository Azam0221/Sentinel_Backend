package com.example.sentinal.sentinel_backend.model;


import com.example.sentinal.sentinel_backend.enum_.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequest {

    private String id;
    private String agentId;
    private String action;
    private RequestStatus status;
    private LocalDateTime timestamp;
}
