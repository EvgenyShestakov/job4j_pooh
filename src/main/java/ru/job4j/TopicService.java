package ru.job4j;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
   private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> queues = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp;
        if ("POST".equals(req.method())) {
            int users = post(req);
            resp = new Resp(String.format("Message delivered %d users", users), 200);
        } else {
            Optional<String> optional = get(req);
            resp = optional.map(s -> new Resp(s, 200)).
                    orElseGet(() -> new Resp("No content", 200));
        }
        return resp;
    }

    private int post(Req req) {
        int count = 0;
        for (var value : queues.values()) {
            ConcurrentLinkedQueue<String> queue = value.get(req.nameQueue());
            if (queue != null) {
                queue.offer(req.param(req.getKey()));
                count++;
            }
        }
        return count;
    }

    private Optional<String> get(Req req) {
        String id = req.param("userId");
        queues.putIfAbsent(id, new ConcurrentHashMap<>());
        ConcurrentHashMap<String,
                ConcurrentLinkedQueue<String>> allSubscriptions = queues.get(id);
        String subscriptionName = req.nameQueue();
        ConcurrentLinkedQueue<String> queue = allSubscriptions.
                putIfAbsent(subscriptionName, new ConcurrentLinkedQueue<>());
        String message = allSubscriptions.get(subscriptionName).poll();
        Optional<String> optional = Optional.empty();
        if (queue == null) {
            optional = Optional.of(String.
                    format("%s subscription for user %s is subscribed", subscriptionName, id));
        }
        if (message != null) {
            optional = Optional.of(message);
        }
        return optional;
    }
}
