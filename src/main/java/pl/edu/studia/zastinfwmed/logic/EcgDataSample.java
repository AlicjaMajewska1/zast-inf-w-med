package pl.edu.studia.zastinfwmed.logic;

import lombok.*;

import java.time.Duration;


/**
 * Created by Alicja on 2017-05-13.
 */


@Data
public class EcgDataSample {
    private int value;
    private Duration duration;
    private EcgRole role = EcgRole.NONE;
    private int differenceToPrevious;
}
