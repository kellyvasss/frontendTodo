## Frontend Application for Todo API 
URL for API: http://todoapi-env.eba-rgxj6v26.eu-north-1.elasticbeanstalk.com/

## AWS
    This is hosted on AWS with CI/CD. You do not need any authentication to use the API.
    The API is configured with a workflow file that uses GitHub actions for building and testing the code
    

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
    

