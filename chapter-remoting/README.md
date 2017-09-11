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

### First terminal
```
cd chapter-remoting
sbt run
``` 

```
Multiple main classes detected, select one to run:

 [1] com.goticks.BackendMain
 [2] com.goticks.BackendRemoteDeployMain
 [3] com.goticks.FrontendMain
 [4] com.goticks.FrontendRemoteDeployMain
 [5] com.goticks.FrontendRemoteDeployWatchMain
 [6] com.goticks.SingleNodeMain

```

then choose 1

### Second terminal
```
cd chapter-remoting
sbt run
```  
then choose 3

### Testing in new terminal remote apps (backend and frontend) with httpie

```
http POST localhost:5000/events/RHCP tickets:=10
http GET localhost:5000/events
```

## Remote deploying

Add to frontend conf 

```
  actor {
    provider = "akka.remote.RemoteActorRefProvider"

    deployment {
      /boxOffice {
        remote = "akka.tcp://backend@0.0.0.0:2551"
      }

      /forwarder/boxOffice {
        remote = "akka.tcp://backend@0.0.0.0:2551"
      }

    }
  }
```

### First terminal
Run backend - this will be ready to create actor remotely from frontend actor system

```
cd chapter-remoting
sbt run
``` 

```
Multiple main classes detected, select one to run:

 [1] com.goticks.BackendMain
 [2] com.goticks.BackendRemoteDeployMain
 [3] com.goticks.FrontendMain
 [4] com.goticks.FrontendRemoteDeployMain
 [5] com.goticks.FrontendRemoteDeployWatchMain
 [6] com.goticks.SingleNodeMain

```

then choose 2

### Second terminal
Run frontend

________________________________________
**It’s important to note that remote deployment doesn’t require that Akka automatically deploy the actual class file(s) 
for the BoxOffice actor into the remote actor system in some way; the code for the BoxOffice needs to already be present 
on the remote actor system for this to work, and the remote actor system needs to be running. If the remote backend actor 
system crashes and restarts, the ActorRef won’t automatically point to the new remote actor instance. 
Since the actor is going to be deployed remotely, it can’t already be started by the backend actor system as we did in the BackendMain. 
Because of this a couple of changes have to be made. We start with new Main classes for starting the backend (BackendRemoteDeployMain) 
and the frontend (FrontendRemoteDeployMain).
________________________________________


```
cd chapter-remoting
sbt run
``` 

then choose 4

### Testing in new terminal remote apps (backend and frontend) with httpie

```
http POST localhost:5000/events/RHCP tickets:=10
http GET localhost:5000/events
```

## Because of **  - it is created actor (RemoteBoxOfficeForwarder) that will be watch and create remote actor (BoxOffice) automatically 

### First terminal
Run backend - this will be ready to create actor remotely from frontend actor system

```
cd chapter-remoting
sbt run
``` 

then choose 2

### Second terminal
```
cd chapter-remoting
sbt run
```  
then choose 5

### Testing in new terminal remote apps (backend and frontend) with httpie

```
http POST localhost:5000/events/RHCP tickets:=10
http GET localhost:5000/events
```