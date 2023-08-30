# Book My Bus
🚌🎫 Bus Reservation System Portal: Simplifying travel! Search, select, and reserve bus tickets seamlessly. Your journey, made easy. 🌐✨
Certainly! Here's a complete `README.md` file for your Bus Management System project:

The Book My Bus is a web application that allows users to manage buses, routes, and user feedback related to bus services. This project is built using Java, Spring Boot, and MySQL.

- Live Link : https://ankits-book-my-bus.netlify.app  [Don't forget to start the backend. 😉]

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Features

- Add, update, and delete bus details such as name, type, driver information, and more.
- Define routes with departure and arrival locations, distance, and associated buses.
- Manage user feedback for specific buses, including ratings and comments.
- View active, deleted, and user-specific feedbacks.
- Pagination support for viewing a specific number of feedbacks per page.

## Getting Started

To run the Bus Management System on your local machine, follow these steps:

### Prerequisites

- Java Development Kit (JDK) 8 or later
- Maven
- MySQL database

### Installation

1. Clone the repository:

```sh
git clone https://github.com/AnkitDeshpande/book-my-bus.git
cd bus-management-system
```

2. Configure the MySQL database:
   
   - Create a new MySQL database named `masaidb`.
   - Update the database configuration in `src/main/resources/application.properties` with your MySQL connection details.

3. Build the project:

```sh
mvn clean install
```

4. Run the application:

```sh
mvn spring-boot:run
```

The application should now be running at http://localhost:8088.

## Usage

- Access the web application by navigating to http://localhost:8088 in your web browser.
- Use the provided API endpoints to interact with the system programmatically (refer to API documentation below).

## API Documentation

API documentation provides detailed information about each API endpoint and how to use them. It can be accessed at `http://localhost:8088/swagger-ui.html` when the application is running.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix: `git checkout -b feature-name`.
3. Make your changes and commit them: `git commit -am 'Add some feature'`.
4. Push your changes to the branch: `git push origin feature-name`.
5. Submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
