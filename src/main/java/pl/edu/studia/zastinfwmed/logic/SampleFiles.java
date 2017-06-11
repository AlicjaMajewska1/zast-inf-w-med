package pl.edu.studia.zastinfwmed.logic;

/**
 * Created by Alicja on 2017-06-10.
 */
public enum SampleFiles {
    SAMPLE1("samples.csv"), SAMPLE2("samples_2.csv"), SAMPLE3("samples_3.csv"), SAMPLE4("samples_4.csv"), SAMPLE5("samples_5.csv");


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