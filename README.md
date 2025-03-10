Boots Bootique
=================

Customized implementation of the "Boots Bootique" from the Codecademy course "Create REST APIs with Spring and Java Skill Path".

### Description:
One-page web application that simulates a rudimentary web store. The boots are stored in an H2 database and the frontend is created using Thymeleaf.

#### Project Link:
[Boots Bootique on Codecademy.com](https://www.codecademy.com/paths/create-rest-apis-with-spring-and-java/tracks/spring-apis-data-with-jpa/modules/spring-data-and-jpa/projects/spring-data-jpa-the-boots-bootique)

### Features:
The user sees a predefined quantity of boots. Each boot is defined by 'type', 'material', 'size' and 'quantity'.
Each existing boot can be deleted or the stock can be reduced or increased using a button next to the entry.
New boots can be added using a form.
The user can search for specific boots using any combination of the search parameters 'material', 'type' and 'size'.

### Technologies used:
+ Spring Initializr
+ Spring Data JPA
+ H2 Database
+ Project Lombok
+ Hibernate validator
+ Thymeleaf

### Potential Improvements:
+ Adding a function to sort the displayed boots (by type, material, size and quantity).
+ Adding different roles (e.g. customer and store owner).
+ Adding actions that only the store owner can perform, e.g. edit boot properties.
