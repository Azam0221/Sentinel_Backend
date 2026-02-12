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
                "DevOps Assistant",
                AgentStatus.ACTIVE,
                "GPT-4",
                "Scanning logs..."
        ));

        agents.add(new Agent(
                "agent-2",
                "Security Auditor",
                AgentStatus.IDLE,
                "Claude-3",
                "Waiting for tasks"
        ));

        return agents;
    }


    public boolean stopAgentOnPlatform(String agentId) {
        // TODO: In the future, this will be:
        // restTemplate.postForEntity("http://archestra-api/agents/" + agentId + "/stop", null, String.class);

        System.out.println("SENDING KILL SIGNAL TO ARCHESTRA FOR AGENT: " + agentId);
        return true;
    }

}
