package me.aekrylov.technaxis_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;

import static org.junit.Assert.assertArrayEquals;
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

    private MockMultipartFile file = new MockMultipartFile("file", "example.txt", "text/plain", "text".getBytes());

    @Test
    public void testUpload() throws Exception {
        MvcResult result = this.mvc.perform(multipart("/covers")
                .file(file))
                .andExpect(status().isOk())
                .andReturn();

        String url = result.getResponse().getContentAsString();

        byte[] remoteContent = new byte[file.getBytes().length];
        new URL(url).openStream().read(remoteContent);
        assertArrayEquals(file.getBytes(), remoteContent);

    }

}
