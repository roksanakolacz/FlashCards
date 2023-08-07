# MyFoodShop
Welcome to my flashcards project! 


## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [External APIs](#external-apis)
* [Features](#features)
* [Screenshots](#screenshots)
* [Setup](#setup)
* [Usage](#usage)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)
* [Acknowledgements](#acknowledgements)
* [Contact](#contact)



## General Information
FlashCards App is an interactive vocabulary learning application that utilizes flashcards for efficient word memorization. The app allows users to train and practice new words by reviewing and translating flashcards.
 
## Technologies Used
The project utilizes the following technologies and frameworks:

- Programming Languages: Java, HTML, CSS.
- Backend Framework: Spring Boot, Hibernate.
- Database Management: MS SQL.
- Frontend Frameworks: Thymeleaf.
- REST API.
- Maven.
- Version Control: Git.
- Deployment: Docker.
- Testing: JUnit, Mockito.

## External APIs:
1.   OpenAI GPT-3 API
To provide the interactive word translation feature, the application utilizes the OpenAI GPT-3 API. The API is an advanced language model developed by OpenAI. It is used for generating text-based responses based on given inputs.

Configuring the OpenAI GPT-3 API:
- Register on the OpenAI website and obtain access to the GPT-3 API.
- Set up the OpenAI API key in the application.properties file as an environment variable, e.g., OPENAI_API_KEY=your_api_key.

## Features
* Registration and login system.
* Displaying every day new foreign word to learn with usage example (with OpenAI GPT-3 API)
* Creating new course by setting attributes and uploading list of words from Excel file
* Browsing existing courses for logged-in user
* Running the flashcard course


## Screenshots
![image](https://github.com/roksanakolacz/FlashCards/assets/89216102/ea74b9d9-83dd-45ea-8fe6-02e825608ef5)
![image](https://github.com/roksanakolacz/FlashCards/assets/89216102/b9e1ad58-8ff7-4f6f-82d9-cfcdeaffaa19)
![image](https://github.com/roksanakolacz/FlashCards/assets/89216102/285078e6-e5a4-4b51-ba48-f9b9b0c22bbe)


## Setup
To set up the project, follow these steps:

1. Clone the repository from GitHub.
2. Install the required dependencies and libraries listed in the project's documentation or configuration files.
3. Configure the OpenAI GPT-3 API key as instructed above.
4. Set up the database and configure the necessary credentials.
5. Build and run the project using the specified build and run commands
6. The application will be accessible at http://localhost:8080.

## Usage
To use the FlashCards application as a user, follow these steps:

* Open the application in a web browser.
* Register a new customer account and then log in with credentials used during registration.
* Try to add new course in tab "Create course"
* Upload Excel file to the app. Snip of how Excel file should look like below. On the left side - word to learn, on the right side - translation,
  ![image](https://github.com/roksanakolacz/FlashCards/assets/89216102/c65f76ca-66d2-4fad-8eaf-fe2f4e6c834a)
* Go to the tab Browse Courses and find your newly created course.
* Click on "Classic flashcards" and start to do your flashcards. When you learn your words, you can create another course. Have fun!
  
## Project Status
The project is currently in progress and being actively developed.

## Room for Improvement
Room for improvement:
- consider adding features like spaced repetition algorithms, pronunciation practice, or gamified learning elements to make the learning experience more engaging.

To do:
- writing unit and integration tests
- implementing loggers
- implementing a feature to track and display user progress and achievements in mastering vocabulary.
- implementing second type of course based on words assign to the course - there will be more type of question
- implementing random word quick course

## Acknowledgements

- This project was inspired by the existing app Memrise: https://app.memrise.com/dashboard
  

## Contact
Created by Roksana Kolacz: 
  * linkedin.com/in/roksana-kolacz.
  * github.com/roksanakolacz.
    
Feel free to contact me!



