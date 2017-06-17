package pl.edu.studia.zastinfwmed.logic;

import lombok.Getter;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alicja on 2017-05-13.
 */
public class EcgDataCalculator {
    @Getter
    private EcgData ecgData;

    private static DecimalFormat two_decimals = new DecimalFormat("0.00");

    public EcgDataCalculator(EcgData ecgData) {
        this.ecgData = ecgData;
        fillEcgSamlpleDatas();
    }

    private void fillEcgSamlpleDatas() {
        Integer previousValue = ecgData.getEcgDataSamples().isEmpty() ? 0 : ecgData.getEcgDataSamples().get(0).getValue();

        for (EcgDataSample actualSample : ecgData.getEcgDataSamples()) {
            actualSample.setDifferenceToPrevious(previousValue - actualSample.getValue());
            previousValue = actualSample.getValue();
        }
    }

    public void countRythm() {
        List<Duration> durations = ecgData.getEcgDataSamples().stream().filter(sample -> EcgRole.LOCAL_MAX.equals(sample.getRole())).map(EcgDataSample::getDuration).collect(Collectors.toList());
        Duration previousDuration = durations.isEmpty() ? null : durations.get(0);

        for (int i = 1; i < durations.size(); ++i) {
            ecgData.getHeartbeatRythms().add(durations.get(i).minus(previousDuration));
            previousDuration = durations.get(i);
        }

        double averageDuration = ecgData.getHeartbeatRythms().stream().mapToLong(Duration::toMillis).average().getAsDouble();
        ecgData.setAverageHeartbeatRythm(two_decimals.format(1 / averageDuration * 1000) + " Hz");
        final int averageHeartbeatRythmPerMinute = (int) (60 / averageDuration * 1000);
        ecgData.setAverageHeartbeatRythmPerMinute(String.valueOf(averageHeartbeatRythmPerMinute));
        ecgData.setHeartbeatRythmCorrect(averageHeartbeatRythmPerMinute > 60 && averageHeartbeatRythmPerMinute < 80);

    }

    public void wyznaczZalamkiR() {

        double average = ecgData.getEcgDataSamples().stream().mapToInt(EcgDataSample::getValue).average().getAsDouble();
        int maxValue = ecgData.getEcgDataSamples().stream().mapToInt(EcgDataSample::getValue).max().getAsInt();
        double aboveAverage = (maxValue + average * 2) / 3;

        int previousDifference = ecgData.getEcgDataSamples().isEmpty() ? 0 : ecgData.getEcgDataSamples().get(0).getDifferenceToPrevious();
        int size = ecgData.getEcgDataSamples().size();
        for (int i = 1; i < size; ++i) {
            EcgDataSample actualSample = ecgData.getEcgDataSamples().get(i);
            if (sampleGoUp(previousDifference) && sampleGoDown(actualSample.getDifferenceToPrevious()) &&
                    (actualSample.getValue() > aboveAverage)) {
                actualSample.setRole(EcgRole.LOCAL_MAX);
            }
            previousDifference = actualSample.getDifferenceToPrevious();
        }
    }

    private boolean sampleGoUp(int differenceToPrevious) {
        return differenceToPrevious < 0;
    }

    private boolean sampleGoDown(int differenceToPrevious) {
        return differenceToPrevious >= 0;
    }


}
