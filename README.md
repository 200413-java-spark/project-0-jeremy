# Project 0

A command-line app that receives and displays note entries.

---

## build

$ mvn package  

A jar file will be produced in the `target` directory.

---

## usage

$ java -jar target/project0-0.0.1-SNAPSHOT.jar \[arguments\]

arguments:

- --add="note contents": inserts note contents as new entry which is persisted in h2 file database
- display: shows all notes currently stored in h2
- --load: opens a JSON file which contains note(s) to be loaded into the database

Since this app was intended mainly for console use, logging has been turned off by default.  Console logging can be enabled with the `--debug` flag.  By default, logging messages are written locally to `spring.log`.
