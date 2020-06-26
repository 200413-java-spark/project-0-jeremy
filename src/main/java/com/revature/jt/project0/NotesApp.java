package com.revature.jt.project0;

import com.revature.jt.project0.db.NoteDataSource;
import com.revature.jt.project0.db.NoteSQL;
import com.revature.jt.project0.file.CsvLoader;
import com.revature.jt.project0.file.FileLoader;
import com.revature.jt.project0.file.JsonLoader;
import com.revature.jt.project0.model.Note;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class NotesApp {
    public static void main(String[] args) {
        // initiate logging
        Logger logger = LoggerFactory.getLogger(NotesApp.class);
        logger.debug("Initiating logger...");

        // get db credentials from classpath
        try (InputStream input = NotesApp.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties prop = new Properties(System.getProperties());
            prop.load(input);
            System.setProperties(prop);
            logger.debug("Properties file read");
        } catch (IOException ex) {
            logger.error("Properties file error ", ex);
        }

        // create db service
        NoteSQL db = new NoteSQL(NoteDataSource.getInstance());

        // command line options
        Option newOption = Option.builder("n")
                .longOpt("new")
                .hasArg()
                .optionalArg(true)
                .argName("sample entry")
                .desc("Add a new entry.")
                .build();

        Option loadOption = Option.builder("l")
                .longOpt("load")
                .hasArg()
                .optionalArg(true)
                .argName("file")
                .desc("Load CSV or JSON file containing note entries.")
                .build();

        Option readOption = Option.builder("r")
                .longOpt("read")
                .hasArg()
                .hasArgs()
                .optionalArg(true)
                .argName("read options")
                .desc("Read journal entries. Possible optional (and mutually exclusive) argument values: all, latest "
                        + "(# of messages), category (category name).")
                .build();
        Option nukeOption = new Option("nuke", "Reset db.");
        Option webOption = new Option("w", "web", false, "Start the web app.");
        Option help = new Option("help", "Print this message.");

        Options options = new Options();
        options.addOption(newOption);
        options.addOption(loadOption);
        options.addOption(readOption);
        options.addOption(nukeOption);
        options.addOption(webOption);
        options.addOption(help);

        // command line parser
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("help") || line.getOptions().length == 0) {
                // help messages
                HelpFormatter helper = new HelpFormatter();
                helper.printHelp("NotesApp", "Interact with a simple note journal", options,
                        "==========================================================================", true);
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
                db.insertNote(entryNote);
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
                String fileExt = fileName.substring(fileName.indexOf('.') + 1).toLowerCase();
                FileLoader loader = new FileLoader();
                switch (fileExt) {
                    case "csv":
                        loader = new CsvLoader(fileName);
                        break;
                    case "json":
                        loader = new JsonLoader(fileName);
                        break;
                    default:
                        System.out.println("Only CVS and JSON files supported for import at this time");
                        System.exit(1);
                }
                loader.saveToDB(db);
            }

            if (line.hasOption("r")) {
                String readArg = line.getOptionValue("r");
                if (readArg == null) {
                    db.getAllNotes().forEach(System.out::print);
                    System.exit(1);
                }
                List<String> readArgs = Arrays.asList(line.getOptionValues("r"));
                if (readArgs == null || readArgs.contains("all")) {
                    db.getAllNotes().forEach(System.out::print);
                }
                if (readArgs.contains("latest")) {
                    if (readArgs.size() == 1) {
                        db.getLatest(5).forEach(System.out::print);
                    } else {
                        try {
                            Integer numArg = Integer.valueOf(readArgs.get(readArgs.indexOf("latest") + 1));
                            db.getLatest(numArg).forEach(System.out::print);
                        } catch (NumberFormatException n) {
                            n.printStackTrace();
                            System.out.println("Error parsing number requested, assuming 5 wanted...");
                            db.getLatest(5).forEach(System.out::print);
                        }
                    }
                }
                if (readArgs.contains("category")) {
                    if (readArgs.size() == 1) {
                        System.out.println("No category specified.");
                        System.exit(1);
                    }
                    String categoryArg = readArgs.get(readArgs.indexOf("category") + 1);
                    db.getNotesByCategory(categoryArg).forEach(System.out::print);
                }
            }

            if (line.hasOption("nuke")) {
                Console c = System.console();
                String input = c.readLine("This will reset the database.  Are you sure? [Enter 'YES' to confirm']: ");
                if (input.equals("YES")) {
                    db.nuke();
                    System.out.println("Database reset");
                    System.exit(1);
                }
            }
            if (line.hasOption("w")) {
                Tomcat tomcat = new Tomcat();
                tomcat.setPort(8081);
                tomcat.getConnector();
                tomcat.enableNaming();

                String baseDirLocation = "target/tomcat/";
                tomcat.setBaseDir(new File(baseDirLocation).getAbsolutePath());

                String webappDirLocation = "src/main/webapp/";
                tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

                // JNDI
                ContextResource dataResource = new ContextResource();
                dataResource.setProperty("factory", "org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory");
                dataResource.setName(System.getProperty("db.name"));
                dataResource.setType(System.getProperty("db.type"));
                dataResource.setAuth("Container");
                dataResource.setProperty("driverClassName", System.getProperty("db.driver"));
                dataResource.setProperty("url", System.getProperty("db.url"));
                dataResource.setProperty("username", System.getProperty("db.user"));
                dataResource.setProperty("password", System.getProperty("db.password"));
                tomcat.getServer().getGlobalNamingResources().addResource(dataResource);

                tomcat.start();
                tomcat.getServer().await();
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
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("EXCEPTION", e);
        }

    }
}
