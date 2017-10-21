
name := "akka"

version := "1.0"

lazy val up          = project.in(file("chapter-up-and-running"))
lazy val fault       = project.in(file("chapter-fault-tolerance"))
lazy val futures     = project.in(file("chapter-futures"))
lazy val remoting    = project.in(file("chapter-remoting"))
lazy val routing     = project.in(file("chapter-routing"))
lazy val channels    = project.in(file("chapter-channels"))
lazy val state    = project.in(file("chapter-state"))
lazy val integration    = project.in(file("chapter-integration"))
lazy val stream    = project.in(file("chapter-stream"))