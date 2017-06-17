package pl.edu.studia.zastinfwmed.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Alicja on 2017-06-11.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataFile {

    private long id;
    private String name;
    private String absolutePath;
    private boolean resources;

    public DataFile(long id, String name) {
        this.id = id;
        this.name = name;
        this.resources = true;
    }
}
