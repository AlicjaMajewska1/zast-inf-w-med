package pl.edu.studia.zastinfwmed.logic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alicja on 2017-06-10.
 */
public class JsWritter {
    public static String DATA_FILE_PATH = "src/main/resources/static/js/data.js";
    public static String DATA_FILE_PATH_AMChARTS = "src/main/resources/static/js/dataAmCharts.js";

    public static void writeDataToJsForAmCharts(EcgData ecgData) {


        Path path = Paths.get(DATA_FILE_PATH_AMChARTS);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("var chartData = [");
            writer.newLine();
            for (int i = 0; i < ecgData.getEcgDataSamples().size(); ++i) {
                EcgDataSample sample = ecgData.getEcgDataSamples().get(i);
                if (i != 0) {
                    writer.write(",");
                }
                writer.write("{");
                writer.write("label: ");
                writer.write("'");
                writer.write(String.valueOf(sample.getDuration().toString()));
                writer.write("',");

                writer.write("value: ");
                writer.write("'");
                writer.write(String.valueOf(String.valueOf(sample.getValue())));
                writer.write("'");
                if (EcgRole.ZALAMEK_R.equals(sample.getRole())) {
                    writer.write(",");
                    writer.write("bullet: ");
                    writer.write("'round'");
                }

                writer.write("}");
            }


            writer.write("]");
            writer.newLine();

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
