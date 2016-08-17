package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.rest.VersionController;
import com.epam.brest.course2015.rest.config.RestTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * VersionController Mock Test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RestTestConfig.class)
public class VersionControllerMockTest {

    @Resource
    private VersionController versionController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(versionController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    public void getVersionTest() throws Exception {
        mockMvc.perform(
                get("/version")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("\"1.0\""));
    }

}
