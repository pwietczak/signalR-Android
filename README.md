# Android SignalR MessageBoard
Real-time messaging system built with Android (Jetpack Compose) and ASP.NET Core SignalR.

## Overview
The project consists of:
- **Android client** – receives messages in real time and sends acknowledgments
- **SignalR server** – delivers messages via SignalR and exposes a REST API 

Supports one-to-one messaging, broadcasts, and IMPORTANT message acknowledgments.

## MessageBoard Client (Android)
### Features
- Real-time messaging via SignalR
- Message types: Default / Important
- Automatic acknowledgment for IMPORTANT messages
- Auto-reconnect with configurable delays
- Modern UI (Jetpack Compose + Material 3)

### Tech Stack
- Kotlin
- Jetpack Compose
- SignalR Java Client
- Koin
- Jetpack DataStore
- EventBus
- Timber

### Project Structure
The project follows a clean architecture-inspired package structure:

- `data`: Contains data sources, repositories, DTOs, and the SignalR service implementation.
- `domian` (domain): Core business models, event definitions, and business logic.
- `presentation`: ViewModels and screen-level composables.
- `di`: Dependency injection modules (Koin).
- `ui`: Reusable Compose components and themes.
- `navigation`: App navigation logic using Compose Navigation.

### Building
To build the project, run:
```bash
./gradlew assembleDebug
```

### Configuration
1. Launch the app.
2. Ensure the initial preferences are set correctly:
    - **Server Address**: The URL of your SignalR hub (e.g., `192.168.1.10:5000`).
    - **Username**: Your unique identifier used in the `UserId` header during connection.

## MessageBoard Server (ASP.NET Core)
### Features
- One-to-one messaging via user-based groups
- Broadcast messaging
- Client acknowledgment handling
- Detailed logging

### Tech Stack
- ASP.NET Core (.NET 10)
- C#
- SignalR

### Hub Configuration
The server is accessible with the following endpoints:
- SignalR Hub: `http://localhost:5000/messageHub`
- REST API: `http://localhost:5000/api/message`

### Endpoints
- SignalR Hub: /messageHub
- POST /api/message/{userId} – send message to user
- POST /api/message/broadcast – broadcast message

### Example Request
#### Send Message to User
```
POST /api/message/{userId}
Content-Type: application/json

{
  "type": 1,
  "text": "Hello User"
}
```

#### Broadcast Message to All
```
POST /api/message/broadcast
Content-Type: application/json

{
  "type": 1,
  "text": "Hello Everyone"
}
```
Messages send with type 1 are consider 'important' and will be automatically acknowledged by the receiving client via the GetAck hub method.

### Building and running
1. Restore dependencies:
```bash
dotnet restore
```
2. Run the server:
```bash
dotnet run
```
