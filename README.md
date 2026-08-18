# Star Seeker

A full-stack app for discovering artists via the Spotify API and managing 
"hire" contracts for them.

## Features

- User registration and JWT-based authentication
- Search artists via Spotify (RapidAPI)
- Hire artists (create a contract)
- View and manage your hired artists
- Remove a contract

## Tech Stack

**Backend**
- Java, Spring Boot
- Spring Security with JWT authentication
- PostgreSQL (via Docker)

**Frontend**
- React, TypeScript
- TailwindCSS

**Tools**
- Git/GitHub for version control

## Getting Started

### Prerequisites
- Java 17+ (or your version)
- Node.js 18+
- Docker

### Backend
```bash
cd backend/starseeker
# copy application.properties.example -> application.properties
# and set your RapidAPI key, JWT keys, and DB credentials
./mvnw spring-boot:run
```

### Frontend
```bash
cd frontend
cp .env.example .env
npm install
npm run dev
```

### Environment Variables
| Variable | Description |
|---|---|
| `VITE_API_BASE_URL` | Backend base URL (e.g. `http://localhost:8080`) |
| `RAPIDAPI_KEY` | Your RapidAPI key for Spotify search |

## API Overview

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/auth/login` | Log in, returns JWT | No |
| POST | `/users` | Register a new user | No |
| GET | `/search?q=` | Search artists | No |
| POST | `/contracts` | Hire an artist | Yes |
| GET | `/contracts` | List your contracts | Yes |
| GET | `/contracts/{id}` | Get a contract by ID | Yes |
| DELETE | `/contracts/{id}` | Remove a contract | Yes |