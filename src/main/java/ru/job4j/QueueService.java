package ru.job4j;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp;
        if ("POST".equals(req.method())) {
            post(req);
            resp = new Resp("Message delivered", 200);
        } else {
            Optional<String> optional = get(req);
            resp = optional.map(s -> new Resp(s, 200)).
                    orElseGet(() -> new Resp("No content", 200));
        }
        return resp;
    }

    private void post(Req req) {
        queues.putIfAbsent(req.nameQueue(), new ConcurrentLinkedQueue<>());
        ConcurrentLinkedQueue<String> queue = queues.get(req.nameQueue());
        queue.offer(req.param(req.getKey()));
    }

    private Optional<String> get(Req req) {
        ConcurrentLinkedQueue<String> queue = queues.get(req.nameQueue());
        Optional<String> optional = Optional.empty();
        if (queue != null && !queue.isEmpty()) {
            optional = Optional.of(queue.poll());
        }
        return optional;
    }
}
