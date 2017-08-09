cd chapter-up-and-running
sbt clean compile
sbt test
sbt run

sbt assembly
java -jar target/scala-2.11/goticks.jar

For testing
- install command-line http-client: httpie
sudo apt-get install httpie

http localhost:5000/events/RHCP tickets:=10
