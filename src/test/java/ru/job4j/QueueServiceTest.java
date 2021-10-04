package ru.job4j;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class QueueServiceTest {
    @Test
    public void whenPostThenGetQueue1() {
        QueueService queueService = new QueueService();
        Map<String, String> params = new HashMap<>();
        params.put("temperature", "11");
        queueService.process(
                new Req("POST", "queue", "weather", "temperature", params)
        );
        var result = queueService.process(
                new Req("GET", "queue", "weather", null, null)
        );
        assertThat(result.text(), is("11"));
    }

    @Test
    public void whenPostThenGetQueue2() {
        QueueService queueService = new QueueService();
        Map<String, String> params = new HashMap<>();
        params.put("temperature", "10");
        params.put("dollar", "73");
        params.put("event", "savedTheCat");
        queueService.process(
                new Req("POST", "queue", "weather", "temperature", params)
        );
        queueService.process(
                new Req("POST", "queue", "dollarRate", "dollar", params)
        );
        queueService.process(
                new Req("POST", "queue", "incidents", "event", params)
        );
        Resp result1 = queueService.process(
                new Req("GET", "queue", "weather", null, null)
        );
        Resp result2 = queueService.process(
                new Req("GET", "queue", "dollarRate", null, null)
        );
        Resp result3 = queueService.process(
                new Req("GET", "queue", "incidents", null, null)
        );
        assertThat(result1.text(), is("10"));
        assertThat(result2.text(), is("73"));
        assertThat(result3.text(), is("savedTheCat"));
    }

    @Test
    public void whenGetQueue() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null, null)
        );
        assertThat(result.text(), is("No content"));
    }
}
