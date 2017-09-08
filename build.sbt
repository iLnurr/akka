
name := "akka"

version := "1.0"

lazy val up          = project.in(file("chapter-up-and-running"))
lazy val fault       = project.in(file("chapter-fault-tolerance"))
lazy val futures     = project.in(file("chapter-futures"))
lazy val remoting    = project.in(file("chapter-remoting"))