# SRM-navigation[step-by-step-explaination.txt](https://github.com/user-attachments/files/23180073/step-by-step-explaination.txt)
=========================================
SRM CAMPUS NAVIGATOR - HOW TO RUN
=========================================

This project consists of a Java Spring Boot backend and an HTML/JS/CSS frontend.
The backend serves the frontend and provides a persistent database for login, reviews, and map locations.

---------------------
PROJECT STRUCTURE
---------------------

You need to arrange the files as follows:

srm-navigation-project/
├── pom.xml                   <-- (The main Maven build file)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── srm/
│   │   │           └── navigation/
│   │   │               ├── NavigationApplication.java (Main class)
│   │   │               ├── config/
│   │   │               │   └── DataInitializer.java   (Loads default user/locations)
│   │   │               ├── controller/
│   │   │               │   └── ApiController.java     (Handles all API requests)
│   │   │               ├── entity/
│   │   │               │   ├── Location.java          (Database table for locations)
│   │   │               │   ├── Review.java            (Database table for reviews)
│   │   │               │   └── User.java              (Database table for users)
│   │   │               └── repository/
│   │   │                   ├── LocationRepository.java
│   │   │                   ├── ReviewRepository.java
│   │   │                   └── UserRepository.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties    <-- (Database and server config)
│   │       └── static/                   <-- (ALL FRONTEND FILES GO HERE)
│   │           ├── index.html            (Main map/login page)
│   │           ├── chatbot.html
│   │           ├── reviews.html
│   │           └── style.css
│   
└── (After running, database files will appear here, e.g., 'srm-nav-db.mv.db')


---------------------
PREREQUISITES
---------------------

1.  **Java (JDK)**: Version 17 or newer.
2.  **Apache Maven**: A build tool to compile the Java code and run the server.

You can download both from the internet. Make sure they are installed and added to your system's PATH.
You can check by opening a terminal/command prompt and typing:
> java -version
> mvn -version

---------------------
HOW TO RUN
---------------------

1.  **Arrange Files**: Create the folder structure above and place all the provided files in their correct locations.

2.  **Open Terminal**: Open your command prompt or terminal.

3.  **Navigate to Project**: `cd` into the main project directory (e.g., `cd path/to/srm-navigation-project`).

4.  **Run with Maven**: Type the following command and press Enter:
    > mvn spring-boot:run

5.  **Wait**: Maven will download all the necessary dependencies (Spring Boot, H2, etc.) and then start the server.
    You will see a lot of text, ending with something like:
    "... Started NavigationApplication in X.XXX seconds"

6.  **Server is Live!**: Your application is now running on `http://localhost:8080`.

---------------------
HOW TO USE
---------------------

1.  **Main Application (Map)**:
    Open your browser and go to: `http://localhost:8080/index.html`
    (or just `http://localhost:8080`, as index.html is the default)

    You will see the login modal. Use the credentials that are pre-loaded into the database:
    * **Email**: admin@srm.com
    * **Password**: 12345

    You can also "Continue as Guest" to skip login.

2.  **Chatbot**:
    Go to: `http://localhost:8080/chatbot.html`

3.  **Reviews**:
    Go to: `http://localhost:8080/reviews.html`
    (Add some reviews! They will be saved in the database. If you stop and restart the server, the reviews will still be there.)

4.  **View the Database (Optional)**:
    Spring Boot provides a web console to see your H2 database.
    Go to: `http://localhost:8080/h2-console`

    On the login screen, make sure the "JDBC URL" matches the one in your `application.properties` file:
    * **JDBC URL**: `jdbc:h2:file:./srm-nav-db`

    Leave the username as `sa` and the password blank. Click "Connect".
    You can now see the `USERS`, `LOCATIONS`, and `REVIEWS` tables and run SQL queries.
