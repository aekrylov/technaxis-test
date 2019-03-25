package me.aekrylov.technaxis_test;

import me.aekrylov.technaxis_test.storage.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 11:03 PM
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CoverController.class)
@AutoConfigureRestDocs
public class CoverControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StorageService storageService;

    @Test
    public void testUpload() throws Exception {
        InputStream image = getClass().getResourceAsStream("/cover.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "cover.jpg", "image/jpeg", image);

        String url = "http://example.com/test-upload/cover.jpg";
        when(storageService.upload(any())).thenReturn(url);

        this.mvc.perform(multipart("/covers")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(url))
                .andDo(document("covers-post"));
    }

    @Test
    public void testUpload_wrongContentType() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "app.js", "text/javascript", "alert();".getBytes());

        this.mvc.perform(multipart("/covers")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Only images allowed"))
                .andDo(document("covers-post-400"));
    }

}
