import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.edu.studia.zastinfwmed.logic.*;

import java.io.File;
import java.util.List;

//@EnableWebMvc
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"pl.edu.studia.zastinfwmed"})
public class ZastInfWMedApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ZastInfWMedApplication.class, args);


//        FileLoader fileLoader = new FileLoader();
//        File resourceFile = fileLoader.loadFileFromResources("samples.csv");
//
//        DataParser dataParser = new DataParser();
//        List<EcgDataSample> integers = dataParser.parseDataFromFile(resourceFile);
//        EcgData ecgData = new EcgData( integers);
//        EcgDataCalculator calculator = new EcgDataCalculator(ecgData);
//        calculator.wyznaczZalamkiR();
//        calculator.countRythm();
//
//        ChartPrinter chartPrinter = new ChartPrinter();
//        chartPrinter.drawChart(integers);

    }
}
