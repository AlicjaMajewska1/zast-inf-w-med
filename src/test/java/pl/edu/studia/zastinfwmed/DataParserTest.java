package pl.edu.studia.zastinfwmed;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import pl.edu.studia.zastinfwmed.logic.DataParser;
import pl.edu.studia.zastinfwmed.logic.EcgDataSample;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by Alicja on 2017-05-13.
 */
public class DataParserTest {

    private final List<String> linesToParse = new ArrayList<>();
    private File fileWithData;
    private DataParser dataParser = new DataParser();

    @Before
    public void setUp() throws IOException {

        linesToParse.add("'Elapsed time','V1'");
        linesToParse.add("'hh:mm:ss.mmm','mV'");
        linesToParse.add("'0:00.133',-5.805");

        final File sysTempDir = new File(System.getProperty("java.io.tmpdir"));
        final String dirName = this.getClass().getSimpleName() + UUID.randomUUID().toString() + "TEST";
        File newTempDir = new File(sysTempDir, dirName);
        newTempDir.mkdirs();
        newTempDir.deleteOnExit();
        fileWithData = new File(newTempDir, "parserTest");
        fileWithData.createNewFile();
        FileUtils.writeLines(fileWithData, linesToParse);


    }


    @Test
    public void parseDataFromFile() throws Exception {
        List<EcgDataSample> ecgSamples = dataParser.parseDataFromFile(fileWithData);
        assertTrue(ecgSamples.size() == 1);
        assertTrue(ecgSamples.get(0).getValue() == -5805);
        assertTrue(ecgSamples.get(0).getDuration().toMillis() == 133);
    }

}