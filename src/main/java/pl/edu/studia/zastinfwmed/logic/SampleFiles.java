package pl.edu.studia.zastinfwmed.logic;

/**
 * Created by Alicja on 2017-06-10.
 */
public enum SampleFiles {
    SAMPLE1("samples.csv"), SAMPLE2("samples_2.csv");


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