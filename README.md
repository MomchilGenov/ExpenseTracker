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
MVCWEB:
Java 23
Spring Boot + Spring Web + Spring MVC + Thymeleaf + Spring Security + Java JWT + JFreeChart (Java lib for charts and diagrams) + Maven as a build tool.

SERVICECORE:
Java 23
Spring Boot + Spring Security + Spring Web + Java JWT + Maven as a build tool.

DBCORE:
Java 24
Spring Web + Spring Data JPA + SQL + MySQL Workbench 8.0 CE

For the specific dependencies, please refer to the respective pom.xml file of each component. Each component has its own git branch (SingleServerClientMVC,ServiceLayer,DbServer).
The projects have been developed on a Windows 10 Pro OS in IntelliJ Idea.

Spring Web was used to provide REST support for the 3 APIs to communicate. Spring MVC is used for server-side rendering of Thymeleaf templates that are then sent to the user's browser.
In combination with the JFreeChart library, the two technologies were used to develop the expense report feature. Spring Security provides the Security Filter Chain used to pre-process
every request and authenticate the user via a JWT access token carried in an HTTP-only cookie. Additionally it is leveraged for roles as well as for providing a BCrypt PasswordEncoder in 
Servicecore to hash the password of newly-registered users. Dbcore uses Spring Data Jpa(Hibernate) for data persistency in combination with SQL and MySQL Workbench as a DB Client.

## Getting Started
//todo - how to configure URLS(application.properties file), upload structurally important files like the tests one + pom.xml mvcweb,etc; 
To run the application, you need to have a running instance of the mvcweb, servicecore and dbcore projects. Additionally you will need a running MySQL Workbench instance
to be able to persist data. How to do all of the above, what the database schema is and more is explained below.

### Configuring mvcweb
To start - you can git clone or simply download the mvcweb project from the SingleServerMVCClient branch as a zip archive and unzip it in a folder of your choice on your machine. 
You will need to manually configure a set of urls in the application.properties file of the mvcweb project. Open the unzipped project folder as a project in IntelliJ Idea
and then open the "application.properties" file. You will see the following properties (key-value pairs): 
```
spring.application.name=mvcweb
logging.level.org.springframework.security=DEBUG
SECRET_KEY=a_temporary_secret_key_to_test_the_app
URL_OF_JWT_AUTHENTICATOR=http://DESIRED_IP:DESIRED_PORT/auth/login
URL_OF_JWT_ACCESS_TOKEN_VALIDATION=http://DESIRED_IP:DESIRED_PORT/auth/validateAccessToken
URL_OF_JWT_REFRESH_TOKEN_VALIDATION=http://DESIRED_IP:DESIRED_PORT/auth/validateRefreshToken
URL_OF_LOGOUT_SERVICE=http://DESIRED_IP:DESIRED_PORT/auth/logout
URL_OF_REGISTER_SERVICE=http://DESIRED_IP:DESIRED_PORT/auth/register
ISSUER=servicecore
AUDIENCE=mvcweb
spring.mvc.hiddenmethod.filter.enabled=true
URL_OF_FIND_ALL_CATEGORIES=http://DESIRED_IP:DESIRED_PORT/categories/findAll
URL_OF_CREATE_CATEGORY=http://DESIRED_IP:DESIRED_PORT/categories/create
URL_OF_GET_CATEGORY_BY_ID=http://DESIRED_IP:DESIRED_PORT/categories/getById
URL_OF_UPDATE_CATEGORY=http://DESIRED_IP:DESIRED_PORT/categories/update
URL_OF_DELETE_CATEGORY=http://DESIRED_IP:DESIRED_PORT/categories/delete
URL_OF_IS_CATEGORY_DELETABLE=http://DESIRED_IP:DESIRED_PORT/categories/isDeletable
URL_OF_IS_CATEGORY_NAME_DUPLICATE=http://DESIRED_IP:DESIRED_PORT/categories/isDuplicate
URL_OF_FIND_ALL_EXPENSES=http://DESIRED_IP:DESIRED_PORT/expenses/findAll
URL_OF_CREATE_EXPENSE=http://DESIRED_IP:DESIRED_PORT/expenses/create
URL_OF_GET_EXPENSE_BY_ID=http://DESIRED_IP:DESIRED_PORT/expenses/getById
URL_OF_UPDATE_EXPENSE=http://DESIRED_IP:DESIRED_PORT/expenses/update
URL_OF_DELETE_EXPENSE=http://DESIRED_IP:DESIRED_PORT/expenses/delete

```


## API Overview
// todo - sequence diagrams for API use case flow
## Security & Authentication
//todo - explain jwt implementation in detail
## Demo
video showcasing all the features of the app 
## Future Improvements
what will be fixed and added as features
## License
This project is under the GNU GENERAL PUBLIC LICENSE.
