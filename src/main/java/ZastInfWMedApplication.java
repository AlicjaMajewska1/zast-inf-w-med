import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pl.edu.studia.zastinfwmed.web.StorageProperties;
import pl.edu.studia.zastinfwmed.web.StorageService;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"pl.edu.studia.zastinfwmed"})
@EnableConfigurationProperties(StorageProperties.class)
public class ZastInfWMedApplication {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(ZastInfWMedApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };


    }
}
