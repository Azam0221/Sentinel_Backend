package com.example.sentinal.sentinel_backend.repository;

import com.example.sentinal.sentinel_backend.model.AgentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<AgentInfo,String> {

}
