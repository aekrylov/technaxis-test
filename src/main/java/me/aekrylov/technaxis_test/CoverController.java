package me.aekrylov.technaxis_test;

import me.aekrylov.technaxis_test.storage.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 7:44 PM
 */
@RestController
@RequestMapping(path = "/covers")
public class CoverController {

    private final StorageService storageService;

    public CoverController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(path = "")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(
                storageService.upload(file.getOriginalFilename(), file.getInputStream(), file.getSize())
        );
    }
}
