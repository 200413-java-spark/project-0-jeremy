package com.revature.project0;

import com.revature.project0.persistence.model.Note;
import com.revature.project0.persistence.repo.NoteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "com.revature.project0")
@EntityScan("com.revature.project0.persistence.model")
@EnableJpaRepositories("com.revature.project0.persistence.repo")
public class NotesApp {

	private static final Logger log = LoggerFactory.getLogger(NotesApp.class);

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(NotesApp.class);
		springApplication.setAddCommandLineProperties(false);
		SpringApplication.run(NotesApp.class, args);
	}

	@Bean
	ApplicationRunner appRunner(NoteRepository repo) {
		return (args) -> {
			if (args.containsOption("add")) {
				List<String> entries = args.getOptionValues("add");
				for (String entry : entries) {
					repo.save(new Note(entry));
				}
			}

			if (args.containsOption("load")) {
				List<String> files = args.getOptionValues("load");
				String file = files.get(0);

				ObjectMapper objectMapper = new ObjectMapper();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.setDateFormat(df);

				List<Note> notes = Arrays.asList(objectMapper.readerFor(Note[].class).readValue(new File(file)));
				// notes.forEach(System.out::println);
				notes.forEach((n) -> repo.save(n));
			}

			if (args.getNonOptionArgs().contains("display")) {
				System.out.println("Current notes");
				System.out.println("-------------");
				for (Note note : repo.findAll()) {
					System.out.println(note);
				}
			}
		};
	}
	
}
