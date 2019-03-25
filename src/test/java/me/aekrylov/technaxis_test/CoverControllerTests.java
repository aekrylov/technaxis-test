package me.aekrylov.technaxis_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.assertArrayEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 11:03 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class CoverControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testUpload() throws Exception {
        InputStream image = getClass().getResourceAsStream("/cover.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "cover.jpg", "image/jpeg", image);

        this.mvc.perform(multipart("/covers")
                .file(file))
                .andExpect(status().isOk())
                .andDo(document("covers-post"));

    }

}
