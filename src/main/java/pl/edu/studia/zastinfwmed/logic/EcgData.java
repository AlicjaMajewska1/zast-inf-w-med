package pl.edu.studia.zastinfwmed.logic;

import lombok.Data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alicja on 2017-05-13.
 */
@Data
public class EcgData {

    private String averageHeartbeatRythm;
    private boolean heartbeatRythmCorrect;
    private List<Duration> heartbeatRythms = new ArrayList<>();
    private List<EcgDataSample> ecgDataSamples;
    private String averageHeartbeatRythmPerMinute;
    private String filename;

    public EcgData(List<EcgDataSample> ecgDataSamples, String filename) {
        this.filename = filename;
        this.ecgDataSamples = ecgDataSamples;
    }
}
