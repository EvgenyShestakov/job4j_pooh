package ru.job4j;


import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ReqTest {
    @Test
    public void whenGetTopic() {
        var content = """
                GET /topic/weather/1 HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.75.0
                Accept: */*
                """;
        var req = Req.of(content);
        assertThat(req.method(), is("GET"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.nameQueue(), is("weather"));
        assertThat(req.param("userId"), is("1"));
    }

    @Test
    public void whenGetQueue() {
        var content = """ 
                      GET /queue/weather HTTP/1.1
                      Host: localhost:9000
                      User-Agent: curl/7.75.0
                      Accept: */*
                      """;
        var req = Req.of(content);
        assertThat(req.method(), is("GET"));
        assertThat(req.mode(), is("queue"));
        assertThat(req.nameQueue(), is("weather"));
    }

    @Test
    public void whenPostTopic() {
        var content = """
                POST /topic/weather HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.75.0
                Accept: */*
                Content-Length: 14
                Content-Type: application/x-www-form-urlencoded
                       
                temperature=23
                """;
        var req = Req.of(content);
        assertThat(req.method(), is("POST"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.nameQueue(), is("weather"));
        assertThat(req.getKey(), is("temperature"));
        assertThat(req.param("temperature"), is("23"));

    }

    @Test
    public void whenPostQueue() {
        var content = """
                POST /queue/weather HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.75.0
                Accept: */*
                Content-Length: 14
                Content-Type: application/x-www-form-urlencoded
                                
                temperature=23
                """;
        var req = Req.of(content);
        assertThat(req.method(), is("POST"));
        assertThat(req.mode(), is("queue"));
        assertThat(req.nameQueue(), is("weather"));
        assertThat(req.getKey(), is("temperature"));
        assertThat(req.param("temperature"), is("23"));
    }
}
