# CLGitHubServiceApp

This is a spring boot application with a RESTful API that takes a user's id (aka username) and returns a max of five followers with nested tree. The tree is three level deep.

INSTALLATION:

1. Clone or download the code.
2. Run 'mvn clean install' command.
3. Open the project in your favourite IDE.
4. Start the application.
5. Use the 'localhost:8080/api/{userName}/followers' endpoint to get the data.

     P.S. The service might take a bit longer than usual. Be patient.


Also, the endpoint is publicly available at an AWS hosted VPC.
Use this endpoint for testing purposes. 


EX: Retreiving data from user 'octocat'

http://ec2-18-217-187-4.us-east-2.compute.amazonaws.com:8080/api/octocat/followers

     
