# 📝 PlanMate

Welcome to **PlanMate** — a simple and powerful task management application built using **Kotlin** for the **Command Line Interface (CLI)**.  
This project follows the **TDD** approach and applies **SOLID** principles for clean and maintainable code.

---

## 📁 Project Overview

PlanMate allows users to organize projects and tasks with dynamic states and role-based access.  
It supports both **Admin** and **Mate** user types, providing a flexible and secure task management system.

---
## 🧩 Features

## ✅ Functional Requirements

- Support two types of users: **Admin** and **Mate**.
- Each user has a username and a password. Passwords must be stored securely by hashing them with **MD5** (no plain text storage).
- States (e.g., TODO, In Progress, Done) must be **dynamic** and customizable.
- **Admins** can:
  - Create, edit, and delete states per project.
  - Create, edit, and delete projects.
  - Create users of type Mate.
- **Mates** can:
  - Create, edit, and delete tasks within a project.
- All users can:
  - View all tasks in a project using a **Swimlanes-style UI** in the console.
- An **Audit System** is implemented to track the change history of any project or task, displaying who made each change and when. Example:
  > `User abc changed task XYZ-001 from InProgress to InDevReview at 2025/05/24 8:00 PM`

---

## ⚙️ Technical Requirements

- Follow a **simple layered architecture**:
  - Separate the code into three packages: **logic**, **data**, and **ui**.
- **Unidirectional dependencies**:
  - `data → logic`
  - `ui → logic`
  - `logic` must not access `data` or `ui`.
- Data must be stored in **multiple CSV files** based on features (e.g., users.csv, projects.csv, tasks.csv, states.csv).
- Apply the **Dependency Inversion Principle** for data access to allow easy migration from CSV to other storage solutions in the future.
- Create **separate repositories** for each feature (e.g., `AuthenticationRepository`, `ProjectsRepository`, etc.).
- Achieve **100% unit test coverage**.
- Clearly define **entities** inside the logic package.
- Use the **Koin** library for **Dependency Injection**.

---

## 🏗️ Project Structure
```
📁 planmate/
├── 📁 data/
│   ├── 📁 datasources/     # All data sources (interfaces + implementations)
│   │   ├── 📁 auth/
│   │   │   ├── UserDataSource.kt         # Interface
│   │   │   ├── UserCsvDataSource.kt      # Reads from CSV
│   │   ├── 📁 project/
│   │   │   ├── ProjectDataSource.kt
│   │   │   ├── ProjectCsvDataSource.kt
│   │   ├── 📁 task/
│   │   │   ├── TaskDataSource.kt
│   │   │   ├── TaskCsvDataSource.kt
│   │   ├── 📁 state/
│   │   │   ├── StateDataSource.kt
│   │   │   ├── StateCsvDataSource.kt
│   │   └── 📁 change/
│   │   │   ├── ChangeDataSource.kt
│   │   │   ├── ChangeCsvDataSource.kt
│   ├── 📁 auth/
│   │   └── AuthRepository.kt
│   ├── 📁 project/
│   │   └── ProjectRepository.kt
│   └── 📁 task/
│       └── TaskRepository.kt
├── 📁 logic/
│   ├── 📁 entities/         # Models
│   │   ├── User.kt
│   │   ├── Project.kt
│   │   └── Task.kt
│   ├── 📁 repositories/     # Repository Interfaces
│   │   ├── IAuthRepository.kt
│   │   ├── IProjectRepository.kt
│   │   └── ITaskRepository.kt
│   └── 📁 usecases/         # Application Use Cases
│       ├── AuthUseCases.kt
│       ├── ProjectUseCases.kt
│       └── TaskUseCases.kt
├── 📁 ui/
│   ├── 📁 cli/
│   │   ├── Main.kt
│   │   └── Menu.kt
│   └── 📁 swimlane/
│       └── SwimlaneRenderer.kt
├── 📁 di/                   # Dependency Injection (Koin setup)
│   └── KoinModules.kt
├── 📁 test/                 # Tests (same structure as logic + data + usecases)
│   └── ...
├── build.gradle.kts
└── .gitignore
``` 
---
 