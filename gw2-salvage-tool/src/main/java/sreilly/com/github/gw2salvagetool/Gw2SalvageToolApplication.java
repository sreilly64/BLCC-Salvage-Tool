package sreilly.com.github.gw2salvagetool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class Gw2SalvageToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gw2SalvageToolApplication.class, args);
	}

}
