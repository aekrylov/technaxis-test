package me.aekrylov.technaxis_test;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.aekrylov.technaxis_test.storage.FileUploadException;
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
    @ApiOperation(value = "Upload book cover", notes = "Only images allowed")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Upload successful, returns file URL"),
            @ApiResponse(code = 400, message = "File type not supported"),
            @ApiResponse(code = 500, message = "Internal error during file upload")
    })
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body("Only images allowed");
        }
        try {
            return ResponseEntity.ok(storageService.upload(file));
        } catch (FileUploadException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
