package pl.edu.studia.zastinfwmed.web;

/**
 * Created by Alicja on 2017-06-11.
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}