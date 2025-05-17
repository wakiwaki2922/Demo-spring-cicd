# Demo CI/CD Spring Boot Application

[![CI Pipeline](https://github.com/yourusername/demodeploycicd/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/yourusername/demodeploycicd/actions/workflows/ci-cd.yml)
[![Docker Image](https://img.shields.io/docker/pulls/yourusername/demodeploycicd)](https://hub.docker.com/r/yourusername/demodeploycicd)
[![Docker Image Version](https://img.shields.io/docker/v/yourusername/demodeploycicd/latest)](https://hub.docker.com/r/yourusername/demodeploycicd/tags)

A demonstration project showcasing CI/CD implementation with Spring Boot, featuring a RESTful API for user management with automated testing and Docker deployment.

## Technologies

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database
- OpenAPI (Swagger)
- Docker
- GitHub Actions

## Features

- RESTful API for user management (CRUD operations)
- Input validation
- Exception handling
- API documentation with Swagger UI
- In-memory H2 database
- Automated testing
- CI/CD pipeline with GitHub Actions
- Docker containerization

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle
- Docker (optional)

### Local Development

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/demodeploycicd.git
   cd demodeploycicd
   ```

2. Build the project:
   ```bash
   ./gradlew build
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

The application will be available at `http://localhost:8080`

### Docker Deployment

1. Build the Docker image:
   ```bash
   docker build -t demodeploycicd .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 demodeploycicd
   ```

## API Documentation

Once the application is running, you can access:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI docs: `http://localhost:8080/v3/api-docs`

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create a new user |
| PUT | `/api/users/{id}` | Update an existing user |
| DELETE | `/api/users/{id}` | Delete a user |

## Database

The application uses H2 in-memory database. You can access the H2 console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Testing

Run the tests using:
```bash
./gradlew test
```

## CI/CD Pipeline

The project includes a GitHub Actions workflow that:
1. Builds the application
2. Runs tests
3. Creates a Docker image
4. Pushes the image to Docker Hub

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.