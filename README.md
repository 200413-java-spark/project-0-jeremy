# Project 0

A command-line app that receives and displays note entries.

---

## build

$ mvn package  

A jar file will be produced in the `target` directory.

---

## usage

$ java -jar target/project0_jt-0.0.2-SNAPSHOT.jar \[arguments\]

arguments:

- --add="note contents": inserts note contents as new entry which is persisted in h2 file database
- display: shows all notes currently stored in h2
- --load: opens a JSON file which contains note(s) to be loaded into the database
