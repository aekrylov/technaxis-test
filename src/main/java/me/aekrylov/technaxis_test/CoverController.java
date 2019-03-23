package me.aekrylov.technaxis_test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 7:44 PM
 */
@RestController
@RequestMapping(path = "/covers")
public class CoverController {

    @PostMapping(path = "/")
    public ResponseEntity<String> upload(MultipartFile file) {
        //todo
        return null;
    }
}
