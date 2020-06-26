#!/bin/sh

java -cp h2-*.jar org.h2.tools.Shell -url "jdbc:h2:./notes" -user sa -password sa
