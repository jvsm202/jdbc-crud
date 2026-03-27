# CRUD JDBC
A simple CRUD project using JDBC with DAO pattern to manage employees.
## Technologies
+ Java SE 21
+ JDBC
+ [MySQL](https://www.mysql.com/)
+ [Maven](https://maven.apache.org/)
## Features
+ Save employee.
+ Find employee by id.
+ Update employee.
+ Delete employee by id.
+ Save a list of employees.
+ Find all employees.
+ Delete all employees.
+ Count the number of employees.
## Prerequisites
### Database configuration
```sql
CREATE TABLE Employees(
	id INT AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR(100),
	last_name VARCHAR(100),
	email VARCHAR(100)
);
```
### `ConnectionFactory` configuration
```java
private static final String URL = "jdbc:mysql://localhost:3306/jdbc_crud";
private static final String USER = "root";
private static final String PASSWORD = "password";
```
### Cloning
```Shell
git clone https://github.com/jvsm202/jdbc-crud.git
```

