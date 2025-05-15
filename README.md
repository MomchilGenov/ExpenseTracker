# ExpenseTracker


## Overview
This is a simple expense tracker that allows users to register into the system with a username and password or login if they already have an account.
They can then view the categories their profile has, create new ones,update existing ones or delete them, respectively.
Then they can view all their expenses, modify them, delete them or add new ones. In addition, they can also use the system to generate expense reports,
which are filtered for a specific period of time and grouped by day, month, year of their category. The system supports roles, where ordinary users
get the role of user upon registration, but an admin role is also present in the codebase with plans for future features to allow an admin to manage users
in a special page for that, similar to how users manage expenses and categories. They will additionally be able to ban or unban users and perform other relevant actions. 


## Architecture
This is a 3-tier Expense Tracker app.
The architectural plan of the application is to separate front-end, business and persistence logic into 3 separate loosely coupled modules communicating over REST
with the idea of easily changing the underlying implementation of each component when needed. For example the 3 currently available implementations are 
mvcweb, servicecore and dbcore which are the front-end, back-end and persistence logic respectively. The front-end module which is mvcweb is written in 
Java 23 + Spring Boot 3 with Spring MVC + Thymeleaf and Spring Web. The idea for the loose coupling is to provide the opportunity to switch out
Spring MVC + Thymeleaf for example with a JavaScript based client instead. Likewise with the other 2 components.

![Image](https://github.com/user-attachments/assets/d051e387-e9ea-481f-85a0-e102cedf3548)

## Features

## Tech Stack

## Getting Started

## API Overview

## Security & Authentication

## Future Improvements

## UML Diagrams / Videos (Coming Soon)

## License

