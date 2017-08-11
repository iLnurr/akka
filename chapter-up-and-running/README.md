### Run app
`$ cd chapter-up-and-running`

`$ sbt clean compile`

`$ sbt test`

`$ sbt run`

`$ sbt assembly`

`$ java -jar target/scala-2.11/goticks.jar`

### For testing
Install command-line http-client: httpie.

`$ sudo apt-get install httpie`


`$ http POST localhost:5000/events/RHCP tickets:=10`

The parameter is transformed into a JSON body. Notice the parameter uses := instead of = . This means that the parameter is a non-string field. The format of our command is translated into { "tickets" : 10} . The whole following block is the complete HTTP response dumped by httpie to the console.

`$ http POST localhost:5000/events/DjMadlib tickets:=15`

`$ http GET localhost:5000/events`

`$ http POST localhost:5000/events/RHCP/tickets tickets:=2`

`$ http GET localhost:5000/events`

### Into the cloud

Install Heroku toolbelt from https://toolbelt.heroku.com/

`$ wget -qO- https://cli-assets.heroku.com/install-ubuntu.sh | sh`

Login to heroku account:
`$ heroku login`

```
   Enter your Heroku credentials:
   Email: serbaevilnur@gmail.com
   Password: ***********
   Logged in as serbaevilnur@gmail.com
```

Create app
`$ heroku create`

```
  Creating app... done, ⬢ thawing-oasis-40751
  https://thawing-oasis-40751.herokuapp.com/ | https://git.heroku.com/thawing-oasis-40751.git
```

Add to project/plugins.sbt:
```
   resolvers += Classpaths.typesafeReleases
   
   addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0") //assembly create one big jar file needed to deployment to Heroku
   
   addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0") //packager create startup scripts for running the app on Heroku
```
   
Also need to create Procfile:
`web: target/universal/stage/bin/goticks`
which tells Heroku that app should be run on a web dino

`$ cd chapter-up-and-running`

Build app: `$ sbt clean compile stage`

Grab app archive and startup locally: `$ heroku local`

### Deploy and run on Heroku

Push to Heroku to deploy:

#### If git not init:

```
$ cd chapter-up-and-running
$ git init
$ heroku git:remote -a thawing-oasis-40751
```

```
$ git add .
$ git commit -am "make it better"
$ git push heroku master

```

```
Подсчет объектов: 524, готово.
Delta compression using up to 4 threads.
Сжатие объектов: 100% (423/423), готово.
Запись объектов: 100% (524/524), 20.78 MiB | 210.00 KiB/s, готово.
Total 524 (delta 164), reused 0 (delta 0)
remote: Compressing source files... done.
remote: Building source:
remote: 
remote: -----> Scala app detected
remote: -----> Installing OpenJDK 1.8... done
remote: -----> Priming Ivy cache... done
remote: -----> Running: sbt compile stage
remote: Downloading sbt launcher for 0.13.7:
...
remote:        https://thawing-oasis-40751.herokuapp.com/ deployed to Heroku
remote: 
remote: Verifying deploy... done.
To https://git.heroku.com/thawing-oasis-40751.git
 * [new branch]      master -> master


```

#### For existing repositories, simply add the heroku remote 
`$ heroku git:remote -a thawing-oasis-40751`

#### Testing the remote heroku-app

`$ http POST thawing-oasis-40751.herokuapp.com/events/RHCP tickets:=250`

`$ http POST thawing-oasis-40751.herokuapp.com/events/RHCP/tickets tickets:=4`
