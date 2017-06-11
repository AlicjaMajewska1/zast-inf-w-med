package pl.edu.studia.zastinfwmed.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alicja on 2017-06-10.
 */
public class JsonWritter {

    public static List<CharDataSample> writeDataToSamples(EcgData ecgData) {
        List<CharDataSample> result = new ArrayList<CharDataSample>(ecgData.getEcgDataSamples().size());
        for (EcgDataSample sample : ecgData.getEcgDataSamples()) {
            CharDataSample charDataSample = new CharDataSample();
            charDataSample.setLabel(sample.getDuration().toString());
            charDataSample.setValue(String.valueOf(sample.getValue()));
            if (EcgRole.LOCAL_MAX.equals(sample.getRole())) {
                charDataSample.setBullet("round");
            }
            result.add(charDataSample);
        }
        return result;
    }
}
