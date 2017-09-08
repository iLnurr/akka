## Remote repl action

## Example
### Backend - first terminal
```
cd chapter-remoting

sbt clean compile

sbt console

:paste

val conf = """
             |akka {
             |  actor {
             |    provider = "akka.remote.RemoteActorRefProvider"
             |  }
             |
             |  remote {
             |    enabled-transports = ["akka.remote.netty.tcp"]
             |    netty.tcp {
             |      hostname = "0.0.0.0"
             |      port = 2551
             |    }
             |  }
             |}
"""

//CTRL-D

import com.typesafe.config._

import akka.actor._

val config = ConfigFactory.parseString(conf)

val backend = ActorSystem("backend", config)

:paste

class Simple extends Actor {
  def receive = {
    case m => println(s"received $m!")
  }
}

//CTRL-D

backend.actorOf(Props[Simple], "simple")

```

### Frontend -second terminal

```
cd chapter-remoting

sbt console

:paste

val conf = """
             |akka {
             |  actor {
             |    provider = "akka.remote.RemoteActorRefProvider"
             |  }
             |
             |  remote {
             |    enabled-transports = ["akka.remote.netty.tcp"]
             |    netty.tcp {
             |      hostname = "0.0.0.0"
             |      port = 2552
             |    }
             |  }
             | }
"""

// CTRL-D

import com.typesafe.config._

import akka.actor._

val config = ConfigFactory.parseString(conf)

val frontend= ActorSystem("frontend", config)

:paste

val path = "akka.tcp://backend@0.0.0.0:2551/user/simple"
val simple = frontend.actorSelection(path)

//CTRL-D

simple ! "Hello REMOTE Backend"
```

## Run app

####First terminal
```
cd chapter-remoting
sbt run
``` 
then choose 1

####Second terminal
```
cd chapter-remoting
sbt run
```  
then choose 3

#### Testing in new terminal remote apps (backend and frontend) with httpie

`http POST localhost:5000/events/RHCP tickets:=10`
`http GET localhost:5000/events`