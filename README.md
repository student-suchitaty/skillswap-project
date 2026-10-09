# SkillSwap – Skill Exchange Platform

SkillSwap is a full-stack web application that allows users to share their skills, find users with matching skills, and send skill exchange requests.

## Features

- User Registration and Login
- User Dashboard
- Add and Manage Skills
- Find Skill Matches
- Send Skill Exchange Requests
- View Sent and Received Requests
- Track Request Status
- User Profile
- MongoDB Database Integration

## Technologies Used

### Frontend
- HTML
- CSS
- JavaScript

### Backend
- Java
- Spring Boot
- Spring Data MongoDB
- REST API

### Database
- MongoDB

### Tools
- Visual Studio Code
- IntelliJ IDEA
- MongoDB Compass
- Git & GitHub

## Project Structure

```text
skillswap-project/
│
├── backend/
│   ├── src/
│   └── pom.xml
│
├── frontend/
│   ├── css/
│   ├── js/
│   └── HTML files
│
├── .gitignore
└── README.md
## Latest SkillSwap Modules

- Request management: Pending, Accepted and Rejected statuses.
- Accepted connections: study material sharing is available after a request is accepted.
- Study Material Swap: send notes/PDFs/files, view received/sent materials and download files.
- Admin Dashboard: manage users, requests and study materials.

### Demo Admin Account
- Email: `admin@skillswap.com`
- Password: `admin123`

The admin account is created automatically when the Spring Boot backend starts if it does not already exist.
