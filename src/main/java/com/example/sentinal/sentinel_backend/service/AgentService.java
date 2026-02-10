package com.example.sentinal.sentinel_backend.service;


import com.example.sentinal.sentinel_backend.model.AgentInfo;
import com.example.sentinal.sentinel_backend.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;
    private final ArchestraClient archestraClient;

    // AgentService.java
    public ResponseEntity<String> registerHeartbeat(String agentId, String name) {
        AgentInfo agent = agentRepository.findById(agentId)
                .orElseGet(() -> {

                    AgentInfo newAgent = new AgentInfo();
                    newAgent.setAgentId(agentId);
                    newAgent.setName(name);
                    return newAgent;
                });

        agent.setLastHeartbeat(LocalDateTime.now());
        agentRepository.save(agent);

        return ResponseEntity.ok("Agent Registered Successfully"); 
    }


    public boolean shouldKill(String agentId) {
        return agentRepository.findById(agentId)
                .map(AgentInfo::isKilled)
                .orElse(false);
    }

    public ResponseEntity<String> killAgent(String agentId) {


        agentRepository.findById(agentId).ifPresent(agent -> {
            agent.setKilled(true);
            agentRepository.save(agent);
        });

        boolean success = archestraClient.stopAgentOnPlatform(agentId);

        if (success) {
            return ResponseEntity.ok("Agent Terminated Successfully (DB + Platform)");
        } else {

            return ResponseEntity.status(500).body("Kill signal sent to DB, but Platform failed");
        }
    }
}
