# AI-Powered Document & Multimedia Q&A Web Application

## Overview

This project is a full-stack web application that allows users to upload PDF documents, audio files, and video files, then interact with the uploaded content using an AI-powered chatbot.

Users can also generate summaries, search timestamps for specific topics in multimedia files, and play the relevant section directly.

---

## Features

- Upload PDF, Audio, and Video Files
- AI Chatbot Question Answering
- Generate Content Summary
- Timestamp Search in Audio/Video
- Play Relevant Multimedia Section
- MySQL Database Storage
- Full Frontend + Backend Integration

---

## Tech Stack

### Frontend
- React.js
- Axios
- CSS

### Backend
- Java
- Spring Boot
- REST API

### Database
- MySQL

### Tools
- Maven
- Docker
- GitHub Actions

---

## Project Structure


ai_doc_multimedia_webApp/
│── README.md
│── docker-compose.yml
│
├── backend/
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/
│
└── frontend/
    ├── package.json
    ├── Dockerfile
    └── src/   


## Setup Instructions

1. Clone Project
git clone https://github.com/yourusername/ai_doc_multimedia_webApp.git
cd ai_doc_multimedia_webApp

## 2. Backend Setup & Run
cd backend
mvn clean install
mvn spring-boot:run

## Backend runs on:

http://localhost:8080


## 3. Frontend Setup & Run
Open new terminal:
cd frontend
npm install
npm start

## Frontend runs on:
http://localhost:3000

(or available port)


4. ## Database Setup

Create MySQL database:

CREATE DATABASE qaapp;

## Update username/password in:
backend/src/main/resources/application.properties


## API Endpoints
Upload File
POST /api/files/upload
Ask Question
POST /api/ai/ask
Generate Summary
GET /api/ai/summary/{fileId}
Find Timestamp
GET /api/ai/timestamp/{fileId}?topic=keyword
Transcribe File
POST /api/ai/transcribe/{fileId}


## How to Use
Start backend server
Start frontend server
Open browser
Upload any PDF / Audio / Video file
Ask questions about uploaded content
Generate summary
Search topic timestamp
Click Play button for relevant media portion
Docker Run
docker-compose up --build
CI/CD
GitHub Actions workflow included for automated build and deployment process.

## Testing
JUnit backend test structure included.

## Deliverables
Source Code
README Documentation
Docker Support
CI/CD Workflow
Functional Frontend + Backend
Database Integration


## Future Enhancements
Real-time chat streaming
JWT Authentication
Vector Search (FAISS / Pinecone)
Cloud Deployment
Redis Cache


## Author

Nitin Kumar
