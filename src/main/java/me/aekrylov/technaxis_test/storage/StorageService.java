package me.aekrylov.technaxis_test.storage;

import java.io.InputStream;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 11:58 PM
 */
public interface StorageService {
    String upload(String filename, InputStream data, long size);
}
