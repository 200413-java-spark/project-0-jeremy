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

import java.util.List;

@SpringBootApplication(scanBasePackages = "com.revature.project0")
@EntityScan("com.revature.project0.persistence.model")
@EnableJpaRepositories("com.revature.project0.persistence.repo")
public class Notes {

	private static final Logger log = LoggerFactory.getLogger(Notes.class);

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Notes.class);
		springApplication.setAddCommandLineProperties(false);
		SpringApplication.run(Notes.class, args);
	}

	@Bean
	ApplicationRunner appRunner(NoteRepository repo) {
		return (args) -> {
			if (args.containsOption("add")) {
				List<String> entries = args.getOptionValues("add");
				for (String entry: entries) {
					repo.save(new Note(entry));
				}
			}

			if (args.getNonOptionArgs().contains("display")) {
				System.out.println("Current notes");
				System.out.println("-------------");
				for (Note note: repo.findAll()) {
					System.out.println(note);
				}
			}
		};
	}
}
