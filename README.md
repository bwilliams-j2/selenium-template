# J2 Global Selenium Template
## Summary
This is a general template to be used for frontend automation for J2 Global products.

### Powered by:
Java (Maven)

TestNG

Selenium WebDriver 3

Docker

## How To Use
### Summary
Generally speaking, automated tests should be ran in a "headless" fashion. 
These tests will be running in a Docker container as part of the build pipeline in Jenkins,
and as such should be tested against a locally running Selenium Grid. 

### Running Selenium Grid Locally
#### Install Docker
Step 1: Install Docker for your system. 
You will need to create an account with Docker to do this. 

Step 2: Use docker-compose to spin up a Selenium Grid with one Chrome and Firefox (Geckodriver) node.

Running in Detached Mode: 
To run Selenium Grid as a background process, in a terminal navigate to your projects root directory and simply type:
`docker-compose up -d`

(Optional) Running in Interactive Mode:
If you'd like to see what the Selenium grid is doing, you can run the grid in interactive mode.

`docker-compose up`

You can see the grid + nodes running by navigating to the following URL  your browser: http://localhost:4444/grid/console


### Running Tests
Running the tests is simply a matter of running a maven build command:
`mvn clean verify`


#### Targeting test classes and specific tests:
You can target test classes (ie: `GoogleTestIT`) by targeting them as parameters in the mvn command using the TestNG framework:
Target specific test class: `mvn clean verify -Dtest=GoogleExampleIT`

Target specific test in test class: `mvn clean verify -Dtest=GoogleExampleIT#googleCheeseExample`

## Options
You can change the default in the Configuration Properties section of the pom.xml, or pass them as runtime parameters.
Here are some examples:

### Targeting a specific browser: 
You can also target a specific browser: 

`mvn clean verify -Dbrowser=firefox`

or 

`mvn clean verify -Dbrowser=chrome`

### Running against a local binary
`mvn clean verify -Dremote=false`

### Running against local binary with headless mode off
`mvn clean verify -Dremote=false -Dheadless=false`