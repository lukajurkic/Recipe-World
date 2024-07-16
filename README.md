# Final thesis: Recipe World

## Faculty of Electrical Engineering Computer Science and Information Technology Osijek
## Mentor: prof. dr. sc. Krešimir Nenadić
## Co-mentor: Kristijan Šimunović

# Project description

Recipe World web application consists of backend and frontend part.
This web application allows every user to create and manage recipes. Recipes are created entering new recipe name, and then entering all necessary ingredients.
User also can create ingredients if it is not already in the database. Application has option to upload up to 5 images for each recipe and maximum of one image for ingredient.
Recipe world app uses another API for importing recipes. User enters a recipe name, and application pulls different recipes with that name from external API.
Every user can comment on every recipe, user can edit or delete comments they made. Application implements option for recipes to be exported to .pdf format.

Recipe World web application has two types of users:

USER 
- can create and manage recipes he created
- can create ingredients and upload images
- can comment and export recipes
- 
ADMIN
- can do all the things USER can
- can update, delete and manage images on ingredients.
- can promote USER to ADMIN account
- can demote himself from ADMIN account

# Technologies used

## Backend
- Java
- Spring Boot
- REST APIs
- Flyway for database migations
- iTextPdf for .pdf generating
- Docker
- Maven
- Git
- PostgreSQL
- Spring Mail
- Spring Web
- Hibernate
- H2
- Junit 5
- AssertJ

## Frontend
- HTML
- CSS
- Thymeleaf
- Bootstrap 5

