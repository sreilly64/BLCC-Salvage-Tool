package sreilly64.com.github.gw2salvagetool;

import org.json.simple.JSONArray;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class Gw2SalvageToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gw2SalvageToolApplication.class, args);
	}

}
