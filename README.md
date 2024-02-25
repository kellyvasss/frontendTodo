## Frontend Application for Todo API 
URL for API: http://todoapi-env.eba-rgxj6v26.eu-north-1.elasticbeanstalk.com/

## Simple explaniation of the API's workflow
    This is hosted on AWS with CI/CD. You do not need any authentication to use the API.
    The API is configured with a workflow file that uses GitHub actions for testing the code.
    The building is done at AWS CodeBuild and the deployment is through AWS Elastic Beanstalk with a automized pipeline for a CI/CD.

## AWS Pipeline Process

1. Integration with GitHub Repository:

        AWS Pipeline is integrated with GitHub repository to monitor changes and updates.
        When you push to GitHub repository, AWS Pipeline is automatically triggered to initiate the CI/CD process.
   
2. Build Phase:

        Upon pipeline triggering, AWS Pipeline fetches the source code from the GitHub repository.
        A build environment is set up where all necessary dependencies and tools are downloaded and configured.
        The build process is executed, which may involve compiling the code, creating distribution packages, and running unit tests.
   
3. Testing Phase:

        After the build phase, tests are conducted to ensure the code functions as expected.
        This may include unit tests, integration tests, and other forms of automated tests.
        If any tests fail, the process is aborted, and a notification is sent to the email.

4. Deployment to Elastic Beanstalk:

        Once all tests are passed, AWS Pipeline sends the built and tested code to Elastic Beanstalk for deployment.
        Elastic Beanstalk handles the creation of environments, server configuration, and automatic scaling based on load.
        The application then runs on the Elastic Beanstalk environment and is accessible to users.
   

 ![CodePipeline](https://github.com/kellyvasss/frontendTodo/assets/124784916/23dae682-51e0-45b8-86ff-74624c5abc20)   
 
    
    

## Run the app

Download the project and run in IntelliJ or other editor. You need internet connection.
    Click on the runnable file and open up a browser with address "localhost:8080".


## Endpoints for the API:

### 1. Create new Todo
     * Method: POST
     * URL: base URL
     * Description: Create a new todo with given information
     * Request Body JSON:

      {
     "description": "string"
      }
   
       * Response code:
          201 Created

### 2. Get all Todos
       * Method: GET
       * URL: base URL + "?searchTerm={your_searchTerm}"
       * Description: Returns a list with all Todos or a filtered list based on your search
       * Parameters: *searchTerm (optional)
       * Response code:
          200 OK

### 3. Get a specific Todo
       * Method: GET
       * URL: base URL + "/{id}"
       * Description: Get a specific Todo based on the ID of the Todo
       * Parameters: id (mandatory)
       * Response codes:
          200 OK -> Todo found and returned
          400 Bad Request -> Todo with requested id does not exist

### 4. Update a Todo
       * Method: PUT
       * URL: base URL + "/update"
       * Description: Update an existing Todo from "done":false to "done":true
       * Request Body JSON:
       
          {
         "id": Long,
         "description": "string"
          }
       
       * Response codes:
          202 Accepted 
          400 Bad Request -> Didn't find matching Todo

### 5. Delete a Todo
       * Method: DELETE
       * URL: base URL + "/{id}"
       * Description: Delete a todo based on the ID of the Todo
       * Response codes:
          202 Accepted -> Todo deleted
          400 Bad Request -> Todo with given ID is not found
    

