package com.example.sentinal.sentinel_backend.model;



import com.example.sentinal.sentinel_backend.enum_.AgentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agent {
    private String id;
    private String name;
    private AgentStatus status;
    private String model;
    private String currentActivity;
}
