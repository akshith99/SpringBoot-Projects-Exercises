package com.in28minutes.springboot.firstrestapi.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private UserDetailsRepository repository;
	
	public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
		super();
		this.repository = repository;
	}



	@Override
	public void run(String... args) throws Exception {

//		logger.info(args.toString());
		
		//to see the complete details of toString:
//		logger.info(Arrays.toString(args));
		
		repository.save(new UserDetails("Akshith", "Admin"));
		repository.save(new UserDetails("Naresh", "Admin"));
		repository.save(new UserDetails("Karthik", "User"));
		
//		List<UserDetails> users = repository.findAll();
		
		List<UserDetails> users = repository.findByRole("Admin");
		users.forEach(user -> logger.info(user.toString()));
		
		
	}

}
