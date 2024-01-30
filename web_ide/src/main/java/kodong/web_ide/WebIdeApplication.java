package kodong.web_ide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class WebIdeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebIdeApplication.class, args);
	}

}
