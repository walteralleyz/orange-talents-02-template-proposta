package br.com.zup.Credicard.util;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class Mvc {
    private MockMvc mvc;
    private Integer status;
    private String content;

    public Mvc(MockMvc mvc) {
        this.mvc = mvc;
    }

    public String doPost(URI uri, String content, Integer status) throws Exception {
        this.content = content;
        this.status = status;

        return expects(mvc.perform(headers(post(uri))));
    }

    public String doGet(URI uri, Integer status) throws Exception {
        this.status = status;
        this.content = "";

        return expects(mvc.perform(headers(get(uri))));
    }

    private String expects(ResultActions actions) throws Exception {
        return actions
            .andExpect(MockMvcResultMatchers.status().is(status))
            .andReturn().getResponse().getContentAsString();
    }

    private MockHttpServletRequestBuilder headers(MockHttpServletRequestBuilder builder) {
        return builder
            .content(content)
            .header("Accept-language", "pt")
            .contentType(MediaType.APPLICATION_JSON);
    }
}
