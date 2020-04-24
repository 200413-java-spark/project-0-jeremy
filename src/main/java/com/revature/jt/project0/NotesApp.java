package com.revature.jt.project0;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotesApp {
    public static void main(String[] args) {
        // initiate logging
        Logger logger = LoggerFactory.getLogger(NotesApp.class);
        logger.debug("Initiating...");

        // get db credentials from classpath
        try (InputStream input = NotesApp.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties prop = new Properties(System.getProperties());
            prop.load(input);
            System.setProperties(prop);
            logger.debug("properties file read");
        } catch (IOException ex) {
            logger.error("Properties file error ", ex);
        }

        // command line options
        Option addEntry = Option.builder("a").longOpt("add").hasArg().argName("sample entry").desc("add a new entry")
                .build();

        Option loadFile = Option.builder("l").longOpt("load").hasArg().argName("file")
                .desc("load CSV or JSON file containing note entries").build();

        Option displayEntries = new Option("d", "display", false, "display saved entries");

        Options options = new Options();
        options.addOption(addEntry);
        options.addOption(loadFile);
        options.addOption(displayEntries);

        // help messages
        HelpFormatter helper = new HelpFormatter();
        helper.printHelp("NotesApp", "Interact with a CRUD note journal", options, "=================================", true);

        // command line parser
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("a")) {
                String entry = line.getOptionValue("a");
                logger.debug("error adding " + entry + "; adding entries needs to be implemented!");
            }
            if (line.hasOption("l")) {
                String file = line.getOptionValue("l");
                logger.debug("error reading file " + file + "; loading files needs to be implemented!");
            }
            if (line.hasOption("d")) {
                logger.debug("no db connected!");
            }
        } catch (ParseException e) {
            logger.error("invalid arguments passed; ", e);
        }

    }
}
