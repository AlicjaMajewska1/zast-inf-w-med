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
        List<Duration> durations = ecgData.getEcgDataSamples().stream().filter(sample -> EcgRole.ZALAMEK_R.equals(sample.getRole())).map(EcgDataSample::getDuration).collect(Collectors.toList());
        Duration previousDuration = durations.isEmpty() ? null : durations.get(0);

        for (int i = 1; i < durations.size(); ++i) {
            ecgData.getHeartbeatRythms().add(durations.get(i).minus(previousDuration));
            previousDuration = durations.get(i);
        }
        ecgData.setAverageHeartbeatRythm(two_decimals.format(ecgData.getHeartbeatRythms().stream().mapToLong(Duration::toMillis).average().getAsDouble()) + " ms");
    }

    public void wyznaczZalamkiR() {

        double average = ecgData.getEcgDataSamples().stream().filter(ecgSample -> ecgSample.getDifferenceToPrevious() < 0).mapToInt(EcgDataSample::getDifferenceToPrevious).average().getAsDouble();



        int maxDifference = ecgData.getEcgDataSamples().stream().mapToInt(EcgDataSample::getDifferenceToPrevious).min().getAsInt();
        int aboveAverage = (int) ((maxDifference - average) / 2);

        int previousDifference = ecgData.getEcgDataSamples().isEmpty() ? 0 : ecgData.getEcgDataSamples().get(0).getDifferenceToPrevious();
        for (int i = 1; i < ecgData.getEcgDataSamples().size(); ++i) {
            EcgDataSample actualSample = ecgData.getEcgDataSamples().get(i);
            if (sampleGoDown(previousDifference) && sampleGoUp(actualSample.getDifferenceToPrevious()) &&
                    (actualSample.getDifferenceToPrevious() < average) && nextFiveSamplesAreGrowingFast(average, i)) {
                actualSample.setRole(EcgRole.ZALAMEK_R);
            }
            previousDifference = actualSample.getDifferenceToPrevious();
        }

//        ecgData.getEcgDataSamples().stream().filter(sample -> sample.getDifferenceToPrevious() < aboveAverage).forEach(dataSample -> dataSample.setRole(EcgRole.ZALAMEK_R));

/*        boolean previousWasR = false;
        boolean roleHasChanged = false;
        for (EcgDataSample currentSample : ecgData.getEcgDataSamples()) {

            if (previousWasR && EcgRole.ZALAMEK_R.equals(currentSample.getRole())) {
                roleHasChanged = true;
                currentSample.setRole(EcgRole.NONE);
            }
            previousWasR = roleHasChanged || EcgRole.ZALAMEK_R.equals(currentSample.getRole());
        }*/
    }

    private boolean sampleGoUp(int differenceToPrevious) {
        return differenceToPrevious < 0;
    }

    private boolean sampleGoDown(int differenceToPrevious) {
        return differenceToPrevious > 0;
    }

    private boolean nextFiveSamplesAreGrowingFast(double average, int index ){
        for (int i = index; i < index + 5 && i <  ecgData.getEcgDataSamples().size(); i++){
            int actualDifference = ecgData.getEcgDataSamples().get(i).getDifferenceToPrevious();
            if (!(sampleGoUp(actualDifference) && (actualDifference < average))){
                return false;
            }
        }
        return true;
    }

}
