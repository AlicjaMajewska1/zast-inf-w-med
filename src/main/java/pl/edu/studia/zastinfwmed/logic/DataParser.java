package pl.edu.studia.zastinfwmed.logic;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alicja on 2017-05-13.
 */
public class DataParser {

    public List<EcgDataSample> parseDataFromFile(File file) throws IOException {


        List<String> lines = Files.readAllLines(file.toPath());
        List<EcgDataSample> samples = new ArrayList<>();
        for (String line : lines) {
            line = line.replace("'", "");
            if (!StringUtils.isNumeric(line.substring(0, 1))) {
                continue;
            }
            String[] timeAndValue = line.split(",");
            EcgDataSample sample = new EcgDataSample();
            sample.setValue(Double.valueOf(Double.parseDouble(timeAndValue[1]) * 1000).intValue());
            sample.setDuration(parseDuration(timeAndValue[0]));
            samples.add(sample);
        }
        return samples;

    }

    private Duration parseDuration(String timeFromFile) {
        String[] minutes_sec_mili = timeFromFile.replace(",", "").split(":");
        //0:00.008
        int minutes = Integer.parseInt(minutes_sec_mili[0].replace(":", ""));
        String[] seconds_miliseconds = minutes_sec_mili[1].split("\\.");
        int seconds = Integer.parseInt(seconds_miliseconds[0].replace(".", ""));
        int milis = Integer.parseInt(seconds_miliseconds[1]);
        long numberOfMillis = 60 * 1000 * minutes + 1000 * seconds + milis;
        return Duration.ofMillis(numberOfMillis);
    }
}
