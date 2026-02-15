package com.example.sentinal.sentinel_backend.controller;


import com.example.sentinal.sentinel_backend.enum_.AgentStatus;
import com.example.sentinal.sentinel_backend.model.Agent;
import com.example.sentinal.sentinel_backend.service.AgentService;
import com.example.sentinal.sentinel_backend.service.ArchestraClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/agent")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;
    private final ArchestraClient archestraClient;


    @GetMapping("/mobile/list")
    public ResponseEntity<List<Agent>> getAllAgents() {

        List<Agent> agents = archestraClient.getAllAgents();

        for (Agent agent : agents) {

            boolean isKilled = agentService.shouldKill(agent.getId());

            if (isKilled) {
                agent.setStatus(AgentStatus.KILLED);
                agent.setCurrentActivity("Terminated by Sentinel");
            }
        }
        return ResponseEntity.ok(agents);
    }


    @GetMapping("/should-kill/{agentId}")
    public ResponseEntity<Map<String, Boolean>> checkKillSwitch(@PathVariable String agentId) {

        agentService.registerHeartbeat(agentId, "Unknown-Agent");

        boolean killStatus = agentService.shouldKill(agentId);

        return ResponseEntity.ok(Map.of("shouldKill", killStatus));
    } 


    @PostMapping("/mobile/kill/{agentId}")
    public ResponseEntity<String> killAgent(@PathVariable String agentId) {
        return agentService.killAgent(agentId);
    }

    @GetMapping("/logs/{agentId}")
    public List<String> getLogs(@PathVariable String agentId){
        return agentService.getLogs(agentId);
    }

    @PostMapping("/logs/{agentId}")
    public ResponseEntity<Void> receiveLog(@PathVariable String agentId, @RequestBody Map<String, String> payload) {
        agentService.addLog(agentId, payload.get("log"));
        return ResponseEntity.ok().build();
    }

}
