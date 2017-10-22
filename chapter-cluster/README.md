## Cluster action

`cd chapter-cluster`

`sbt clean compile`

`sbt assembly`

### Starting leader seed node in first terminal

`cd chapter-cluster`

`java -DPORT=2551 -Dconfig.resource=/seed.conf -jar target/words-node.jar`

### Starting second seed node in second terminal

`cd chapter-cluster`

`java -DPORT=2552 -Dconfig.resource=/seed.conf -jar target/words-node.jar`

### Starting third seed node in third terminal

`cd chapter-cluster`

`java -DPORT=2553 -Dconfig.resource=/seed.conf -jar target/words-node.jar`

### Starting master node in fourth terminal

`cd chapter-cluster`

`java -DPORT=2554 -Dconfig.resource=/master.conf -jar target/words-node.jar`




#### For testing - if want to start worker node (after starting seed nodes)

`cd chapter-cluster`

`java -DPORT=2555 -Dconfig.resource=/worker.conf -jar target/words-node.jar`