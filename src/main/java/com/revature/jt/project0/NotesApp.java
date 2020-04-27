package com.revature.jt.project0;

import com.revature.jt.project0.db.NoteDataSource;
import com.revature.jt.project0.model.Note;
import com.revature.jt.project0.db.NoteSQL;
import com.revature.jt.project0.file.NoteJsonMap;
import com.revature.jt.project0.file.NoteCsvMap;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Arrays;

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
        logger.debug("Initiating logger...");

        // get db credentials from classpath
        try (InputStream input =
                NotesApp.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties prop = new Properties(System.getProperties());
            prop.load(input);
            System.setProperties(prop);
            logger.debug("Properties file read");
        } catch (IOException ex) {
            logger.error("Properties file error ", ex);
        }

        // initiate db connection
        NoteDataSource ds = NoteDataSource.getInstance();

        // command line options
        Option newOption = Option.builder("n").longOpt("new").hasArg().optionalArg(true).argName("sample entry")
                .desc("add a new entry").build();

        Option loadOption = Option.builder("l").longOpt("load").hasArg().optionalArg(true).argName("file")
                .desc("load CSV or JSON file containing note entries").build();

        Option readOption = new Option("r", "read", false, "display saved entries");
        Option nukeOption = new Option("nuke", "reset db");
        Option help = new Option("help", "print this message");

        Options options = new Options();
        options.addOption(newOption);
        options.addOption(loadOption);
        options.addOption(readOption);
        options.addOption(nukeOption);
        options.addOption(help);

        // command line parser
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("help") || line.getOptions().length == 0) {
                // help messages
                HelpFormatter helper = new HelpFormatter();
                helper.printHelp("NotesApp", "Interact with a CRUD note journal", options,
                        "=================================", true);
            }
            if (line.hasOption("n")) {
                String entryString = line.getOptionValue("n");
                if (entryString == null) {
                    Console c = System.console();
                    do {
                        entryString = c.readLine("New entry: ");
                    } while (entryString == null || entryString.isEmpty());
                }
                Note entryNote = new Note(entryString);
                NoteSQL noteDB = new NoteSQL(ds);
                noteDB.insertNote(entryNote);
            }
            if (line.hasOption("l")) {
                String fileName = line.getOptionValue("l");
                if (fileName == null) {
                    Console c = System.console();
                    do {
                        fileName = c.readLine("Please enter file name: ");
                    } while (fileName == null || fileName.isEmpty());
                }
                if (fileName.length() < 5) {
                    System.out.println("Please enter a valid file name!");
                    System.exit(1);
                }
                if (fileName.toLowerCase().endsWith(".csv")) {
                    NoteCsvMap mapper = new NoteCsvMap(fileName);
                    mapper.saveToDB(ds);
                }
                if (fileName.toLowerCase().endsWith(".json")) {
                    NoteJsonMap mapper = new NoteJsonMap(fileName);
                    mapper.saveToDB(ds);
                }
            }
            if (line.hasOption("r")) {
                NoteSQL noteDB = new NoteSQL(ds);
                System.out.println(noteDB.getAllNotes());
            }
            if (line.hasOption("nuke")) {
                Console c = System.console();
                String input = c.readLine("This will reset the database.  Are you sure? [Enter 'YES' to confirm']: ");
                if (input.equals("YES")) {
                    NoteSQL noteDB = new NoteSQL(ds);
                    noteDB.nuke();
                    System.out.println("Database reset");
                }
            }
            if (line.getArgs().length != 0) {
                System.out.println("Unrecognized options: " + Arrays.toString(line.getArgs()));
                logger.debug("Unrecognized options" + Arrays.toString(line.getArgs()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("invalid arguments passed; ", e);
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }

    }
}
