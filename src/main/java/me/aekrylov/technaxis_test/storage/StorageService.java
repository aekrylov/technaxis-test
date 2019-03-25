package me.aekrylov.technaxis_test.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 11:58 PM
 */
public interface StorageService {
    String upload(MultipartFile file) throws IOException, FileUploadException;
}
