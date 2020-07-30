##Sherpa-Module 3: Data Cruncher
Objective: 
```
The JPMC merchant banking payments department is processing a payments file containing payments that have been identified as fraudulent.
The task is to write software which helps them to analyse the data. 
```
## Installation

Below are the required installations to run the project

```
IntelliJ
Gradle
```
## Usage
The project is built to run local.

Build inside intelliJ or IDE which supports Gradle.
Run the build and test under
``src/main/test/java/DataCruncherTest``
##Unit Test

To run for unit testing
start 
``DataCruncherTest``

Tests for:
````
Task 1 – Get all the unique merchant IDs in the file and return in a SET. Run the test provided to validate.
Task 2- Return the total number of transactions marked as fraudulent. Run the test provided to validate.
Task 3 – Expanding on Task 2 two, allow the caller of the method to pass in a flag (true/false) to get total number of transactions which either are fraudulent or not fraudulent.
Task 4 – Return all fraudulent transactions for Merchant XXX (pass in variable). Run test provided to validate.
Task 5 – Return all fraudulent transactions based on variables provided by method caller (merchant ID and fraudulent payment flag). Run test provided to validate.
Task 6 – Return a List of all transactions sorted by amount. Create a test to validate the list.
Task 7 – Return the % of fraudulent transactions carried out by men. Run test provided to validate.
Task 8 – Produce a Set of Strings of customer IDs who have equals or higher number of fraudulent transactions (based on variable passed into method). Create a test to validate
Task 9 – Using a map – return the Customer IDs with total number of fraudulent transaction. Create a test to validate
Task 10 – Using a map – return the Merchant IDs with total amount of the fraudulent transactions. Create a test to validate
Bonus task – write a small model which returns the probability (value between 0 and 1) that the passed in transaction might be a fraudulent one.

````
## Authors
Created by Jeremy S Bayangos,

Software Engineering Program intern 2020
