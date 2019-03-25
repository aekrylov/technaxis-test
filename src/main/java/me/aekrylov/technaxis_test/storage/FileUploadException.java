package me.aekrylov.technaxis_test.storage;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/25/19 9:13 PM
 */
public class FileUploadException extends RuntimeException {

    public FileUploadException(Throwable cause) {
        super("Unable to upload file", cause);
    }
}
