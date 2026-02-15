# Sentinel Backend (The Command Center)

**Sentinel Backend** is the centralized "State Engine" and security policy server for the Sentinel platform. Built with **Spring Boot**, it serves as the single source of truth for Agent permissions, real-time logging, and the biometric "Kill Switch" status.

It acts as the mediator between the **Python Bridge** (The Enforcer) and the **Android App** (The Key), ensuring that no high-risk AI action proceeds without persistent verification.

## Key Features

* **Centralized State Management:** Persists the status of every AI agent (Active, Idle, or **TERMINATED**).
* **Permission Queuing Engine:** Receives "Ask" requests from the Python Bridge and holds them in a pending state until approved by the Android Admin.
* **Real-Time Log Aggregator:** Ingests live logs from the AI execution environment and streams them to the mobile dashboard.
* **The "Kill Switch" Logic:** Exposes a high-availability endpoint (`/should-kill`) that the Python Bridge checks before *every* tool execution. If an agent is flagged as killed here, its access is instantly revoked network-wide.

## Tech Stack

* **Java 17+**
* **Spring Boot 3.x** (Web, Validation)
* **Lombok** (Boilerplate reduction)
* **Maven** (Dependency Management)
* **Concurrent HashMaps 

## API Endpoints

### Agent Management (Used by Python & Android)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/agent/mobile/list` | Returns list of all agents and their status for the Android Dashboard. |
| `GET` | `/api/v1/agent/should-kill/{agentId}` | **CRITICAL:** Checked by Python Bridge. Returns `true` if the agent is terminated. |
| `POST` | `/api/v1/agent/mobile/kill/{agentId}` | **The Kill Switch.** Sets an agent's status to `KILLED` and blocks all future actions. |

### Real-Time Logs

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/agent/logs/{agentId}` | Ingests a new log line from the Python Bridge. |
| `GET` | `/api/v1/agent/logs/{agentId}` | Streams the log history to the Android "Terminal" screen. |

### Permission System

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/permission/agent/ask` | Creates a new "Pending" request (Called by Python). |
| `GET` | `/api/v1/permission/mobile/pending` | Lists all requests waiting for approval (Called by Android). |
| `POST` | `/api/v1/permission/mobile/decide/{id}` | Approves or Denies a request (Called by Android). |
| `GET` | `/api/v1/permission/agent/status/{id}` | Long-polling endpoint checked by Python to see if Admin replied. |

## Installation & Setup

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/Azam0221/Sentinel_Backend.git](https://github.com/Azam0221/Sentinel_Backend.git)
    cd Sentinel_Backend
    ```

2.  **Build the Project:**
    ```bash
    mvn clean install
    ```

3.  **Run the Server:**
    ```bash
    mvn spring-boot:run
    ```

    The server will start on: `http://localhost:8080`

## 🔗 Architecture Integration

* **Python Connection:** Ensure your Python Bridge is configured to point to `http://localhost:8080/api`.
* **Android Connection:** Ensure your `RetrofitClient` in the Android app points to your machine's local IP address (e.g., `http://192.168.1.X:8080/api/v1/`).

## 🛡️ Security Philosophy

Why Spring Boot? We chose Java for this layer to ensure **Atomic State Consistency**. When an admin hits "Kill," that state change must be instant, persistent, and thread-safe across all connected bridges. Python handles the traffic, but Spring Boot holds the *truth*.
