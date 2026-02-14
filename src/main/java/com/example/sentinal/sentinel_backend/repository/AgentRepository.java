package com.example.sentinal.sentinel_backend.repository;

import com.example.sentinal.sentinel_backend.model.AgentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


import com.example.sentinal.sentinel_backend.model.AgentInfo;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AgentRepository {


    private final ConcurrentHashMap<String, AgentInfo> db = new ConcurrentHashMap<>();

    public Optional<AgentInfo> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    public AgentInfo save(AgentInfo agent) {
        db.put(agent.getAgentId(), agent);
        return agent;
    }
}