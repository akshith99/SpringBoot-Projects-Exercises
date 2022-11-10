package com.udemy.spring.learnspringframework;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.udemy.spring.learnspringframework.enterprise.example.web.MyWebController;
import com.udemy.spring.learnspringframework.game.GameRunner;

@SpringBootApplication
//@ComponentScan("com.udemy.spring.learnspringframework")
public class LearnSpringFrameworkApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext context = SpringApplication.run(LearnSpringFrameworkApplication.class, args);
		
//		MarioGame game = new MarioGame();
//		SuperContraGame game = new SuperContraGame();
//		BatmanGame game = new BatmanGame();
//		GamingConsole game = new BatmanGame();
//		GameRunner runner = new GameRunner(game);
		
		GameRunner runner = context.getBean(GameRunner.class);
		runner.run();
		
		MyWebController controller = context.getBean(MyWebController.class);
		System.out.println(controller.returnValurFromBusinessService());
	}

}
