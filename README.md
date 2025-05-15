# ExpenseTracker
This is a 3-tier Expense Tracker app.
The architectural plan of the application is to separate front-end, business  and persistence logic into 3 separate loosely coupled modules communicating over REST
with the idea of easily changing the underlying implementation of each component when needed. For example the 3 currently available implementations are 
mvcweb, servicecore and dbcore which are the front-end, back-end and persistence logic respectively. The front-end module which is mvcweb is written in 
Java 23 + Spring Boot 3 with Spring MVC + Thymeleaf and Spring Web. The idea for the loose coupling is to provide the opportunity to switch out
Spring MVC + Thymeleaf for example with a JavaScript based client instead. Likewise with the other 2 components.


# Project Name

## Overview

## Architecture

## Features

## Tech Stack

## Getting Started

## API Overview

## Security & Authentication

## Future Improvements

## UML Diagrams / Videos (Coming Soon)

## License

