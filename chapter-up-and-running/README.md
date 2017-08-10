cd chapter-up-and-running
sbt clean compile
sbt test
sbt run

sbt assembly
java -jar target/scala-2.11/goticks.jar

For testing
- install command-line http-client: httpie.
- sudo apt-get install httpie

http localhost:5000/events/RHCP tickets:=10

The parameter is transformed into a JSON body. Notice the parameter uses := instead of = . This means that the parameter is a non-string field. The format of our command is translated into { "tickets" : 10} . The whole following block is the complete HTTP response dumped by httpie to the console.

http POST localhost:5000/events/DjMadlib tickets:=15

http GET localhost:5000/events

http POST localhost:5000/events/RHCP/tickets tickets:=2

http GET localhost:5000/events

