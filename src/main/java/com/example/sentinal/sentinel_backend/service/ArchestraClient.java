package com.example.sentinal.sentinel_backend.service;


import com.example.sentinal.sentinel_backend.enum_.AgentStatus;
import com.example.sentinal.sentinel_backend.model.Agent;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArchestraClient {

    public List<Agent> getAllAgents() {
        List<Agent> agents = new ArrayList<>();


        agents.add(new Agent(
                "agent-1",
                "DevOps Commander",
                AgentStatus.ACTIVE,
                "Gemini 2.5 Flash",
                "Monitoring System Health..."
        ));


        agents.add(new Agent(
                "agent-2",
                "Security Guardian",
                AgentStatus.IDLE,
                "Gemini 1.5 Pro",
                "Waiting for security logs"
        ));

        agents.add(new Agent(
                "agent-3",
                "Data Analyst",
                AgentStatus.ACTIVE,
                "Claude 3.5 Sonnet",
                "Processing 1.2GB of logs"
        ));

        agents.add(new Agent(
                "agent-4",
                "Deploy DB",
                AgentStatus.ACTIVE,
                "Claude 4.6 Opius",
                "Processing 1.2GB of data"
        ));

        return agents;
    }

    public boolean stopAgentOnPlatform(String agentId) {

        System.out.println("🚨 SENDING KILL SIGNAL TO ARCHESTRA FOR AGENT: " + agentId);
        return true;
    }
}