package com.revature.project0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.Arrays;

@SpringBootApplication
@EntityScan("com.revature.project0.persistence.model")
@EnableJpaRepositories("com.revature.project0.persistence.repo")
public class Notes implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(Notes.class);

	public static void main(String[] args) {
		SpringApplication.run(Notes.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String[] sourceArgs = args.getSourceArgs();
		System.out.println("TO BE COMPLETED.  HERE ARE THE ARGS " +
		    Arrays.toString(sourceArgs));
	}
}
