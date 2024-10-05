# Thyme Wizards

Sample project

## How to run the project?

### Development mode

1. Run the Spring Boot Project with active profile `local`.
2. Run the following command:
    ```shell
   npm run build && npm run watch 
    ```
### Release mode

Run the following command in order to create `jar` file:

```shell
./mvnw -P release package
```

Run the following command in order to create Docker image:

```shell
./mvnw -P release spring-boot:build-image
```
