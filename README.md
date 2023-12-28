# E-Voting-Android-App
# Introduction:
The "E-Voting System" is an Android application designed to streamline the electoral process by providing users with a convenient and secure platform for voter registration, authentication, and result viewing. This report outlines the key features of the app, its database structure, and the overall scope of its functionality.

# Scope:
The E-Voting System covers a wide range of functionalities, including user registration, login, voter verification, casting votes, and viewing election results. The app ensures data security and accuracy through its integration with a SQLite database, and its intuitive interface caters to both first-time voters and experienced users.

# Features:
  ## 1.	Splash Screen:
    •	Serves as the launcher activity with a 3-second display duration.
    •	Redirects users to the login or home screen based on their login status.
  ## 2.	Signup Screen:
    •	Facilitates user registration with fields for name, email, password, and recovery email.
    •	Employs AsyncTask for signup tasks, SQLite for database interaction, and redirects to login upon successful signup.
  ## 3.	Login Screen:
    •	Features a clean layout with email and password input fields.
    •	Validates user input, checks credentials against the SQLite database, and navigates to the home screen on successful login.
    •	Provides options for signup and password recovery.
  ## 4.	Forget Password Screen:
    •	Allows users to reset passwords with email input, reset password button, and dynamic visibility buttons.
    •	Manages user input, checks email existence, and displays the password if the email is registered.
    •	Offers navigation to login or signup screens.
  ## 5.	Home Screen:
    •	Displays a toolbar with menu and profile icons.
    •	Enables user registration, voting, and result viewing.
    •	Manages click events for various actions, including logout.
  ## 6.	Voter Registration Screen:
    •	Allows users to register for voting with input fields for personal details and location selection.
    •	Validates input, manages a local database, and provides confirmation dialogs.
    •	Prompts users to vote or return to the main menu upon successful registration.
  ## 7.	Voter Registration Verification Screen:
    •	Enables users to verify registration using CNIC with input, submit, and register buttons.
    •	Validates CNIC format, checks registration status, and navigates users based on verification.
  ## 8.	Cast Vote Screen:
    •	Allows users to cast votes with an action bar, description, RecyclerView for party selection, and cast vote button.
    •	Initializes RecyclerView with party data, handles voting logic, and updates the database.
  ## 9.	Result Screen:
    •	Displays election results with an action bar, RecyclerView for results, and a "Back to Home" button.
    •	Initializes RecyclerView, fetches results from the database, and populates using a custom adapter.
    
# Database Structure:
The SQLite database in the E-Voting System is structured with three tables:
## 1.	UserCredential Table:
  •	Manages user authentication information with columns for Email (Primary Key), Name, Password, and Recovery_Email.
## 2.	Results Table:
  •	Stores election results, tracking the votes received by each political party.
  •	Columns: Party_Name (Primary Key), Votes.
## 3.	VoterDetails Table:
  •	Records voter details, including personal information and voting status.
  •	Columns: CNIC (Primary Key), First_Name, Last_Name, Father_Name, DOB, Country, Province, City, House_Address, Vote_Status.

The DatabaseHelper class defines the database schema, and the DatabaseOperations class facilitates essential operations like inserting, checking existence, retrieving data, and updating records.
