package ru.job4j;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TopicServiceTest {
    @Test
    public void whenPostThenGetTopic1() {
        TopicService topicService = new TopicService();
        Map<String, String> params = new HashMap<>();
        params.put("userId", "1");
        params.put("temperature", "15");
        topicService.process(
                new Req("GET", "topic", "weather", null, params)
        );
        topicService.process(
                new Req("POST", "topic", "weather", "temperature", params)
        );
        Resp result = topicService.process(
                new Req("GET", "topic", "weather", null, params)
        );
        assertThat(result.text(), is("15"));
    }

    @Test
    public void whenPostThenGetTopic2() {
        TopicService topicService = new TopicService();
        Map<String, String> params1 = new HashMap<>();
        Map<String, String> params2 = new HashMap<>();
        params1.put("userId", "1");
        params1.put("temperature", "25");
        params2.put("userId", "2");
        topicService.process(
                new Req("GET", "topic", "weather", null, params1)
        );
        topicService.process(
                new Req("GET", "topic", "weather", null, params2)
        );
        topicService.process(
                new Req("POST", "topic", "weather", "temperature", params1)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", null, params1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", null, params2)
        );
        assertThat(result1.text(), is("25"));
        assertThat(result2.text(), is("25"));
    }

    @Test
    public void whenPostThenGetTopic3() {
        TopicService topicService = new TopicService();
        Map<String, String> params1 = new HashMap<>();
        Map<String, String> params2 = new HashMap<>();
        params1.put("userId", "1");
        params1.put("feline", "tiger");
        params2.put("userId", "2");
        params2.put("electionWinner", "TomJefferson");
        topicService.process(
                new Req("GET", "topic", "animals", null, params1)
        );
        topicService.process(
                new Req("GET", "topic", "politics", null, params2)
        );
        topicService.process(
                new Req("POST", "topic", "animals", "feline", params1)
        );
        topicService.process(
                new Req("POST", "topic", "politics", "electionWinner", params2)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "animals", null, params1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "politics", null, params2)
        );
        assertThat(result1.text(), is("tiger"));
        assertThat(result2.text(), is("TomJefferson"));
    }

    @Test
    public void whenGetTopic1() {
        TopicService topicService = new TopicService();
        Map<String, String> params1 = new HashMap<>();
        Map<String, String> params2 = new HashMap<>();
        params1.put("userId", "1");
        params2.put("userId", "2");
        Resp result1 = topicService.process(
                new Req("GET", "topic", "animals", null, params1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "animals", null, params2)
        );
        assertThat(result1.text(), is("animals subscription for user 1 is subscribed"));
        assertThat(result2.text(), is("animals subscription for user 2 is subscribed"));
    }

    @Test
    public void whenGetTopic2() {
        TopicService topicService = new TopicService();
        Map<String, String> params1 = new HashMap<>();
        Map<String, String> params2 = new HashMap<>();
        params1.put("userId", "1");
        params2.put("userId", "2");
        topicService.process(
                new Req("GET", "topic", "animals", null, params1)
        );
        topicService.process(
                new Req("GET", "topic", "animals", null, params2)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "animals", null, params1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "animals", null, params2)
        );
        assertThat(result1.text(), is("No content"));
        assertThat(result2.text(), is("No content"));
    }
}
