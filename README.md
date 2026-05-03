# 🗄️ Java Mini DBMS (CLI)

A lightweight **Database Management System built in Java** that simulates core DBMS operations using **CSV files as storage**. This project demonstrates how database systems work internally without using any external libraries.

---

## 🚀 Features

- Create and delete tables
- Insert, update, and delete records
- Query data with conditions
- Sort results by columns
- Show all existing tables
- CLI-based SQL-like command system

---

## 🧠 Concepts Applied

- File Handling (Java I/O)
- Data Storage using CSV files
- 2D Array Data Processing
- Custom Query Parsing
- CRUD Operations (Create, Read, Update, Delete)
- Manual Sorting Algorithm
- Input Validation & Error Handling
- Safe Updates using Temporary Files

---

## 🛠️ Example Commands

```sql
CREATE TABLE student (name, regno, address)

INSERT IN TABLE student VALUES ("Zain", "FA21-BCS-001", "Islamabad")

SELECT FROM TABLE student HAVING address = "Islamabad" SORT BY name

UPDATE student SET address = "Lahore" WHERE name = "Zain"

DELETE FROM TABLE student HAVING name = "Zain"

SHOW ALL
```

---

## ▶️ Run

```bash
javac DBMSproject.java
java DBMSproject
```
### Usage:
Run the program, and you will be greeted with a prompt:>> Welcome to Database Management System. Enter "HELP" for the syntax details >>Enter commands in the format specified above. Type HELP to view detailed syntax and examples. Type EXIT to quit the program. 


## 📌 Highlights

- Built a **mini query engine** from scratch  
- Implemented **DBMS logic without using databases**  
- Demonstrates strong understanding of **data handling & system design**

---

## 👨‍💻 Author

**Zain Javed**