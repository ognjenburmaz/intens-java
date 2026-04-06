# HR Candidate Management Portal

### Requirements:

Java 25 JDK, Maven, MySQL 8.x, Node.js LTS

### How to run:

1. Make a new mySQL database using MySQL Workbench for example, it must be named `intens-java`
or if you want to change that go into application-properties and change connection url,
also you will probably also need to change the username and password to connect.

2. Navigate to `back` folder and run `mvn clean install` and then in the same folder run `mvn spring-boot:run`

3. Navigate to `front` folder and run `npm install` and then in the same folder run `npm run dev`

Access application at [React UI](http://localhost:5173/)

Or access documentation at [Swagger](http://localhost:8080/swagger-ui/index.html)

You can also run tests by navigating to the `back` folder and running `mvn clean test`

## What did I find challenging during development?

To be honest the most challenging part about all this wasn't a particular feature implementation, 
optimization or something along those lines, but rather the "no AI use" policy. I came to realize 
that during these last year or two I've become overreliant on LLMs and that my manual coding skills
took a slight hit. Now that even a simple search on the browser spits out an AI summary it is kinda 
hard to dodge it completely. Besides that if I had to name something specific I'd say making the UI in
React since I only ever used Angular. That was kinda stitched together. I also used this opportunity to
give Java Records a try, since I usually just make a class and annotate it with Lombok @Data and call it a day.
That would be all :D