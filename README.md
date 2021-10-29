# schedules-api

This is the API of the Schedules Comparator project.

This project is the assignment of the ADS lecture of ISCTE's MEI (project 4).

## Environment Variables
|Variable|Description|Default Value|
|:-|:-|:-|
|SPRING_DATASOURCE_URL|Database URL||
|SPRING_DATASOURCE_USERNAME|Database User's Username||
|SPRING_DATASOURCE_PASSWORD|Database User's Password||
|SCHEDULES_MAX_PARALLEL|Maximum number of schedules being imported simultaneously|3|

## Run application

We recommend you run this app either dockerized or using IntelliJ.

For local development, we recommend the IntelliJ alternative.

---

### Dockerized

Requirements:
- Docker
- Port 8080 Available (for app)
- Port 5432 Available (for postgres)

Steps:
1. Start the container
   ```Dockerfile
   docker compose up schedules-api
   ```

---
### Using IntelliJ

Requirements:
- Postgres database (either local instance or dockerized)

**Note**: postgres is described in the docker-compose file. You can start this instance by running the command
```Dockerfile
docker compose up postgres
```

Steps:
1. Set up the environment variables in your IntelliJ task (take into consideration the way you setup postgres)
2. Run the application from IntelliJ
