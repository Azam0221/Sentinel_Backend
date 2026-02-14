package com.example.sentinal.sentinel_backend.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class AgentInfo {

    @Id
    private String agentId;

    private String name;
    private String currentAction;
    private LocalDateTime lastHeartbeat;
    private boolean isKilled;
}
 