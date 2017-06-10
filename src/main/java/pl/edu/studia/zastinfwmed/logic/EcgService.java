package pl.edu.studia.zastinfwmed.logic;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Alicja on 2017-06-10.
 */
@Service
public class EcgService {

    public EcgData calculate(String filename) throws IOException {

        FileLoader fileLoader = new FileLoader();
        File resourceFile = fileLoader.loadFileFromResources(filename);

        DataParser dataParser = new DataParser();
        List<EcgDataSample> integers = dataParser.parseDataFromFile(resourceFile);

        final EcgData ecgData = new EcgData(integers);
        EcgDataCalculator calculator = new EcgDataCalculator(ecgData);
        calculator.wyznaczZalamkiR();
        calculator.countRythm();
        return ecgData;
    }

}
