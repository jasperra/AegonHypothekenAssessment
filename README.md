# AegonHypothekenAssessment

This is the Aegon hypotheken assessment created by Jasper Rademaker.  
To run the backend, you will have to put your Mysql username and password in the application.properties.  
Then simply navigate to the *assessment-backend* folder and run: ```mvn clean install``` followed by ```mvn spring-boot:run```

To run the front-end head into the *assessment-frontend* folder and run ```npm install``` followed by ```npm start```.  
The front-end will be running at http://localhost:4200/

## test

The unit and integration test for the backend are automaticly executed when you run ```mvn install```.  
The front-end unit test can be run by running ```npm test```.  
To run the e2e test for the front-end runt ```ng e2e```  
