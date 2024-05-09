
# Bank System Application

## Overview

The Bank System is a Spring Boot application designed to handle banking operations such as account management, transactions, and reporting. It supports both flat fee and percentage-based transaction fees.

### Features

- **Create Bank and Accounts**: Users can create a bank and then create accounts within that bank.
- **Perform Transactions**: Support for both flat fee and percent fee transactions between accounts.
- **Deposit and Withdraw Funds**: Users can deposit or withdraw money from accounts.
- **View Transactions**: Users can view a list of all transactions associated with a specific account.
- **Account Balance**: Users can check the current balance of any account.
- **Reporting**: Users can view total transaction fees and total transferred amounts for a bank.

### Technologies Used

- **Spring Boot**: Framework for creating stand-alone, production-grade Spring-based applications.
- **H2 Database**: In-memory database for persisting data.
- **Spring Data JPA**: Simplifies the implementation of data access layers.
- **Spring Web**: For creating RESTful web services.

## Setup and Installation

Follow these steps to get the application up and running on your local machine:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/lavdimPireva/Bank-System
   cd BankSystem
2. Build the application:
   ```bash
     mvn clean install
3. Run the application:
   ```bash
     mvn spring-boot:run

## API Endpoints

Here are some of the critical endpoints available in this application:

- **POST /api/bank/createBank**: Create a new bank.
- **POST /api/bank/createAccount**: Create a new account within a bank.
- **POST /api/bank/transfer**: Transfer funds from one account to another.
- **POST /api/bank/accounts/{accountId}/deposit**: Deposit money into an account. Use the query parameter `amount` to specify the sum, e.g., `/api/bank/accounts/1/deposit?amount=500.00`.
- **POST /api/bank/accounts/{accountId}/withdraw**: Withdraw money from an account. Use the query parameter `amount` to specify the sum, e.g., `/api/bank/accounts/1/withdraw?amount=200.00`.

### GET Requests:

- **GET /api/bank/accounts/{accountId}/transactions**: List transactions for an account.
- **GET /api/bank/accounts/{accountId}/balance**: Get the balance of an account.
- **GET /api/bank/banks/{bankId}/accounts**: List all accounts in a bank.
- **GET /api/bank/banks/{bankId}/total-transaction-fees**: Get the total transaction fees accumulated by a bank.
- **GET /api/bank/banks/{bankId}/total-transfers**: Get the total amount transferred within a bank.

  
## How the application works?
The application begins with the creation of a bank, for example, "TEB Banka," which is then stored with a unique identifier in the H2 Database. Following this, users can create multiple accounts within this bank, where each account can be initialized with funds or have money deposited post-creation. The application allows for the execution of financial transactions such as transfers between these accounts. All transactions are recorded and saved in the H2 database, enabling users to review and track the history of all account activities. Additional functionalities include the ability to withdraw funds, add new accounts at any time, and list all transactions for any account, ensuring comprehensive management of banking operations.





