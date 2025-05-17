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
This documentation assumes basic understanding of HTTP and ports as well as basic Spring configurations such as ```server.port=1234``` .
To run the application, you need to have a running instance of the mvcweb, servicecore and dbcore projects. Additionally you will need a running MySQL Workbench instance
to be able to persist data. How to do all of the above, what the database schema is and more is explained below.

### Configuring mvcweb
To start - you can git clone or simply download the mvcweb project from the SingleServerMVCClient branch as a zip archive and unzip it in a folder of your choice on your machine. 
You will need to manually configure a set of urls in the application.properties file of the mvcweb project. Open the unzipped project folder as a project in IntelliJ Idea
and then open the ```application.properties``` file. You will see the following properties (key-value pairs): 
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
You need to replace ``` DESIRED_IP:DESIRED_PORT ``` with the ip and port of the machine you will run servicecore on.
For example if the ip of the machine that you will run servicecore on is 123.45.6 and you want to run it on port 8081, you should
replace ``` DESIRED_IP:DESIRED_PORT ``` with ```123.45.6:8081``` . Make sure the port is free for use.
```SECRET_KEY``` is the key that is used to sign the jwts. You can choose whatever string you wish, as long as :
1) the string is the same in the servicecore configuration
2) the string is at least 32 symbols long
It is fine to keep it as it is, for the sake of testing the features of the app, but in general you should use a strong key which would usually be generated using a tool.
```ISSUER``` and ```AUDIENCE``` need to have the values set as above as this is vital for the proper claim setting in the jwt in the program and if changed, servicecore will reject
the tokens as potentially malicious. After having configured the app, you can run it and it will be available at http://localhost:8080/homepage which will redirect you to
the login page. You can also just visit it directly at http://localhost:8080/login .
Notice: You need to have a running servicecore and dbcore instance as well as a db client to use the app.

### Configuring servicecore
Similarly as above, go to the ServiceLayer branch of the repo and download the project. After that, open the project folder as an IntelliJ project and open the ```application.properties``` file.
You should see the following:
```
spring.application.name=servicecore
server.port=8081
SECRET_KEY=a_temporary_secret_key_to_test_the_app
ISSUER=servicecore
AUDIENCE=mvcweb
ACCESS_TOKEN_DURATION_IN_MINUTES=15
REFRESH_TOKEN_DURATION_IN_MINUTES=120
URL_OF_FIND_USER_BY_USERNAME=http://DESIRED_IP:DESIRED_PORT/api/auth/user
URL_OF_AUTHENTICATE_USER=http://DESIRED_IP:DESIRED_PORT/api/auth/authenticateUser
URL_OF_REGISTER_USER=http://DESIRED_IP:DESIRED_PORT/api/auth/register
URL_OF_VALIDATE_AUTHORITY=http://DESIRED_IP:DESIRED_PORT/api/auth/validateAuthority
URL_OF_FIND_ALL_CATEGORIES=http://DESIRED_IP:DESIRED_PORT/api/categories/findAll
URL_OF_CREATE_CATEGORY=http://DESIRED_IP:DESIRED_PORT/api/categories/create
URL_OF_GET_CATEGORY_BY_ID=http://DESIRED_IP:DESIRED_PORT/api/categories/getById
URL_OF_UPDATE_CATEGORY=http://DESIRED_IP:DESIRED_PORT/api/categories/update
URL_OF_DELETE_CATEGORY=http://DESIRED_IP:DESIRED_PORT/api/categories/delete
URL_OF_IS_CATEGORY_DELETABLE=http://DESIRED_IP:DESIRED_PORT/api/expenses/isDeletable
URL_OF_IS_CATEGORY_NAME_DUPLICATE=http://DESIRED_IP:DESIRED_PORT/api/categories/isDuplicate
URL_OF_FIND_ALL_EXPENSES=http://DESIRED_IP:DESIRED_PORT/api/expenses/findAll
URL_OF_CREATE_EXPENSE=http://DESIRED_IP:DESIRED_PORT/api/expenses/create
URL_OF_GET_EXPENSE_BY_ID=http://DESIRED_IP:DESIRED_PORT/api/expenses/getById
URL_OF_UPDATE_EXPENSE=http://DESIRED_IP:DESIRED_PORT/api/expenses/update
URL_OF_DELETE_EXPENSE=http://DESIRED_IP:DESIRED_PORT/api/expenses/delete


```
It is configured to run on port 8081 since it assumes that you will most likely run all three instances on the same machine for the sake of testing, although the app is written so that all three can
run on 3 separate machines. The sercret key is set to be the same as the one in mvcweb. ```ISSUER``` and ```AUDIENCE``` should have the values as above for proper jwt functioning.
If you would like, you can adjust the lenght of validity of the access token and the refresh tokens. When an access token expires, if a refresh token is present, it is used to get a new access token.
More on how the jwt features work, below. And again you should replace ```DESIRED_IP:DESIRED_PORT``` with  ```ip:port``` where ip is the ip of the machine that dbcore will run on and the respective port.

### Configuring dbcore
Repeat the same steps for dbcore as were the steps for mvcweb and servicecore up untill ```application.properties``` .
You should see the following :
```
spring.application.name=dbcore
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
server.port=8082

```
You should set the username and password for your database client connection. You can also change the port the machine runs on, as long as you update the servicecore properties accordingly.

To run the program, make sure you run mvcweb, servicecore and dbcore all at the same time. Keep in mind that if you are running them on 2 or 3 different machines, you might have to
configure your firewall to allow the communication. Mind you, the system as of March 2025 is configured to use HTTP and not HTTPS, as using HTTPS will be developed additionally in the future,
since it requires developing additional features. When all three are running at the same time, access http://localhost:8080/login . From there you can start using the application. How to
do that, will be demonstrated later on.

To create the database, execute the following queries.

<pre lang="markdown">
DROP DATABASE expense_tracker;
CREATE DATABASE expense_tracker;
USE expense_tracker;


CREATE TABLE users(
  id BIGINT AUTO_INCREMENT PRIMARY KEY ,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL, -- hashed password
  enabled BOOLEAN NOT NULL DEFAULT TRUE
);


CREATE TABLE roles(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL UNIQUE -- ROLE_USER,ROLES_ADMIN,...
);

DROP TABLE user_roles;
CREATE TABLE user_roles(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
role_id BIGINT NOT NULL,
FOREIGN KEY(user_id) REFERENCES users(id),
FOREIGN KEY(role_id) REFERENCES roles(id)
);

-- Saves categories for all users, duplicates are allowed, if a user needs a category removed,
-- the program will find the right ids below to be deleted via joins
DROP TABLE categories;
CREATE TABLE categories(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
user_id BIGINT,
FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE expenses(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
amount DOUBLE NOT NULL,
name VARCHAR(255) NOT NULL,
date DATE NOT NULL,
category_id BIGINT NOT NULL,
user_id BIGINT NOT NULL,
FOREIGN KEY(category_id) REFERENCES categories(id),
FOREIGN KEY(user_id) REFERENCES users(id)
);

</pre>

## API Overview
// todo - sequence diagrams for API use case flow
## Security & Authentication
The system uses JWT for authentication and authorization. Upon system start there is a form login page. Should an unauthenticated user try to access an API endpoint different from the form login
or the registration page, the system will redirect the request to the form login. Upon entering a username and password and submitting them, mvcweb sends them to servicecore for authentication.

```
package com.momchilgenov.springboot.mvcweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtAuthFilter filter;
    private final JwtAuthProvider authProvider;

    @Autowired
    public SecurityConfig(JwtAuthFilter authFilter, JwtAuthProvider authProvider) {
        this.filter = authFilter;
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Disable session creation
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("register").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authProvider)
                .exceptionHandling(e ->
                        e.authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/login"); // Redirect to login page
                        })).logout(AbstractHttpConfigurer::disable);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return authProvider;
    }

    //configures GLOBALLY, not just for http, the auth provider to be used to be your custom one,FORCES it on
    //the auth manager
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

}
```
The above class is the configuration for how the system works with regards to security and in this case in mvcweb. The main idea is that Spring Security has a SecurityFilterChain which is a chain of filters
through which every http request passes through and some type of logic is performed on it. After that an ```AuthenticationManager``` is used to try an authenticate the request by calling all of its available ```AuthenticationProvider```s and passing the request to their ```authenticate()``` method. Typically this is where we would perform a typical out-of-the-box implementation where we use ```DaoAuthenticationProvider``` and a ```UserDetailsService``` with a JDBC implementation of it and a ```PasswordEncoder``` bean, however our database is not on the same machine as the one running mvcweb or would just happen to be so. Because of that we need to write our own provider and send the credentials to servicecore which will then fetch data from dbcore, perform authentication logic and on success will return a ```JwtTokenPair```  to mvcweb .

``` 
package com.momchilgenov.springboot.mvcweb.token.dto;

public record JwtTokenPair(JwtAccessToken accessToken, JwtRefreshToken refreshToken) {
}

```
```
package com.momchilgenov.springboot.mvcweb.token.dto;

public record JwtAccessToken(String token) {
}

```
```
package com.momchilgenov.springboot.mvcweb.token.dto;

public  record JwtRefreshToken (String token){
}

```
For now assume that a ```JwtAccessToken``` and ```JwtRefreshToken``` are tokens use for access and respectively refreshing.
In the configuration above we saw that we can set the duration of an access token and a refresh token. The basic idea is that when a user successfully authenticates into the system,
they receive one access token and one refresh token, formally strings, that are saved as http-only cookies in their browser which prevents javascript pieces of code to access them and
thus preventing malicious access to them, while always being sent with every http request. The access token is used by mvcweb to authenticate the user on every request. Should it expire,
mvcweb looks for a refresh token and should it find a valid such token, sends it to servicecore and receives a new pair or tokens, whereby the previous two tokens are revoked and no longer considered valid.
There are multiple ways to revoke tokens, but given the desire to stay true to REST and be lightweight, the mechanism chosen for this project is using a ```TokenService``` :
```
package com.momchilgenov.springboot.servicecore.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    /**
     * revokedTokens - username,iat timestamp
     * any token for a user with an iat claim prior to the Date value in the map is considered revoked
     */
    private final ConcurrentHashMap<String, Date> revokedTokens;

    @Autowired
    public TokenService() {
        this.revokedTokens = new ConcurrentHashMap<>();
    }

    /**
     * revokes all tokens for a given user issued before the present moment
     *
     * @param username - the user for whom all tokens issued up to current moment should be revoked
     */
    public void revokeAll(String username) {
        //to handle exact timestamp match problem
        revokedTokens.put(username, Date.from(Instant.now().minusMillis(1000)));
        // revokedTokens.put(username, Date.from(Instant.now()));
    }

    /**
     * @param username - the username of the user
     * @param iatClaim - the token claim to verify against to see if the token from
     *                 which the claim was extracted is revoked
     * @return - true, if the token is revoked, false otherwise
     */
    public boolean isRevoked(String username, Date iatClaim) {
        if (revokedTokens.containsKey(username)) {
            return this.revokedTokens.get(username).after(iatClaim);
        }
        return false;
    }

}

```
where we use  ```ConcurrentHashMap<String, Date> revokedTokens``` due to having a ```@Controller``` calling the services which might result in a concurrency problem such as a race condition and an incomplete
revocation or other undefined behavior. The key and value is a string and date respectively to keep track of users by username and a timestamp. An entry of ```<"John Doe",timestamp1>``` for example means that all tokens, be they access or refresh tokens, issued prior  to ```timestamp1 ``` are considered revoked. To revoke a token, we just update the timestamp. When issuing tokens, we also need to revoke the previously issued ones to avoid long-lived tokens leaking to malicious hackers. There is a problem with the timestamp precision, since when issuing a token pair right after revoking it, the probability of the difference between the revocation moment and token generation being small enough to be ignored by the comparison of the two ```Date``` objects is high, so we manually alter the revocation timestamp tp be 1000ms earlier to avoid the new tokens to be considered revoked when they really are not.

//todo - explain jwt implementation in detail
## Demo
video showcasing all the features of the app 
## Future Improvements
what will be fixed and added as features
## License
This project is under the GNU GENERAL PUBLIC LICENSE.
