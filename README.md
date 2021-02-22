## Value at Risk calculation service
Simple web-application based on Micronaut Framework   
### Building and running
**Java 15 is the minimum required java version**  
The application can be built and run by gradle command  
`./gradlew run`  
### Usage
Curl can be used to retrieve the result  
For a single trade:  
`curl -H "Content-Type: application/json" --data @data/trade.json http://localhost:8080/v1/var/trade/99.0`  
For a portfolio:
`curl -H "Content-Type: application/json" --data @data/portfolio.json http://localhost:8080/v1/var/portfolio/95`  
### Comments
Historical data is provided as a json file, there are two example files in the "data" directory for demo convenience  
Trade information is represented in a very simple form. It can be extended if needed
