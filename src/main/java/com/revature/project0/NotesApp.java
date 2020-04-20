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
import java.io.IOException;
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
				entries.forEach((entry) -> repo.save(new Note(entry)));
			}

			if (args.containsOption("load")) {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				
				List<String> files = args.getOptionValues("load");
				files.forEach((file) -> {
					try {
						List<Note> notes = Arrays.asList(objectMapper.readerFor(Note[].class).readValue(new File(file)));
						notes.forEach((n) -> repo.save(n));
					} catch (IOException e) {
						log.info("Exception occurred: " + e.getMessage());
					}
				});
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
