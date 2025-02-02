package KDT.Web_IDE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebIdeApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebIdeApplication.class, args);
  }
}
