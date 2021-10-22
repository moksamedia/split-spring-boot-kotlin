package com.example.demo

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DemoApplication {

	// Initialize some sample data
	@Bean
	fun init(repository: PaperAirplaneRepository): ApplicationRunner {
		return ApplicationRunner { _: ApplicationArguments? ->
			var paperAirplane = PaperAirplane(null, "Bi-Fold", 2, false);
			repository.save(paperAirplane);
			paperAirplane = PaperAirplane(null, "Tri-Fold", 3, false);
			repository.save(paperAirplane);
			paperAirplane = PaperAirplane(null, "Big-ol-wad", 100, true);
			repository.save(paperAirplane);
		}
	}

}

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
