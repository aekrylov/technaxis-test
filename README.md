This is a test project for TECHNAXIS

# Using the API

[Check out Swagger docs](https://technaxis-test.herokuapp.com/swagger-ui.html)

# Buliding and running

## Prerequisites

1. The app uses S3 API for file storage, so you'll need to create a bucket on any S3-compatible storage provider 
([AWS](https://aws.amazon.com), [Wasabi](https://wasabi.com), [Yandex.Cloud](https://cloud.yandex.ru/) etc)

2. Pass credentials as environment variables (or use [any other method available in Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)):

    Parameter       | Description
    -------------   |----------
    `s3.endpoint`   | Endpoint for API calls (defaults to Yandex.Cloud endpoint)
    `s3.region`     | S3 region (default is `us-east-1`, shouldn't be changed for Wasabi or Yandex.Cloud)
    `s3.access-key` | Your access key
    `s3.secret-key` | Your secret key    
    `s3.bucket-name`| Bucket name. Bucket must be public
    
3. Note that all these properties must be available for tests to pass, 
which can be done by putting them into `application.properties` file under test resources root

3. For DBMS other that Postgres or H2, add corresponding runtime dependency
    
## Locally

Run `./mvnw spring-boot:run`, package using `mvn package` 

## Run in Docker

Simply run `docker build .`, this will build the app inside Docker. HTTP port is 8080 unless overriden

## Run on Heroku

1. Setup git remote: 
    ```bash
    heroku git:remote -a <app_name>
    ```
2. Add heroku postgres add-on
3. Setup S3 variables in app settings 
4. Push to heroku remote

# Check list

[x] REST API
[x] AWS
[x] Deploy instructions
[x] Docker
[x] Heroku
[x] API docs
[ ] FTS
[ ] Frontend