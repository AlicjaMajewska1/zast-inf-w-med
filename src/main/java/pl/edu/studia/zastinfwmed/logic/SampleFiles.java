package pl.edu.studia.zastinfwmed.logic;

/**
 * Created by Alicja on 2017-06-10.
 */
public enum SampleFiles {
    SAMPLE1("samples.csv"), SAMPLE2("samples_2.csv"), SAMPLE3("samples_3.csv"), SAMPLE4("samples_4.csv"), SAMPLE5("samples_5.csv"),
    SAMPLE6("samples_1_min.csv"), SAMPLE7("samples_2_min.csv"), SAMPLE8("samples_3_min.csv"), SAMPLE9("samples_4_min.csv"),
    SAMPLE10("samples_5_min.csv"), SAMPLE11("samples_6.csv"), SAMPLE12("samples_7.csv"), SAMPLE13("samples_7.csv"),
    SAMPLE14("samples_8.csv"), SAMPLE15("samples_9.csv"), SAMPLE16("samples_10.csv");


    private final String fileName;

    /**
     * @param text
     */
    private SampleFiles(final String text) {
        this.fileName = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return fileName;
    }
}