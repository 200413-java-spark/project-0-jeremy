# Project 0

A command-line app that receives and displays note entries.

---

## build

>mvn clean package  

A jar file will be produced in the `target` directory.

---

## usage

>java -jar target/project0-0.0.4-SNAPSHOT-jar-with-dependencies.jar \[arguments\]

arguments:

- -n, --new "note contents": inserts note contents as new entry which is persisted in h2 file database
- -r, --read: shows all notes currently stored in h2
- -l, -load: opens a JSON file which contains note(s) to be loaded into the database
- -nuke: resets database
