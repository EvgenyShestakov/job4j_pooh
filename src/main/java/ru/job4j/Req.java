package ru.job4j;

import java.util.HashMap;
import java.util.Map;

public class Req {
    private final String method;
    private final String mode;
    private final String nameQueue;
    private final String key;
    private final Map<String, String> params;

    public Req(String method, String mode, String nameQueue, String key, Map<String, String> params) {
        this.method = method;
        this.mode = mode;
        this.nameQueue = nameQueue;
        this.key = key;
        this.params = params;
    }

    public static Req of(String content) {
        String[] parts = content.split("[\\s/=]");
        Map<String, String> map = new HashMap<>();
        map.put(parts[parts.length - 2], parts[parts.length - 1]);
        if ("topic".equals(parts[2])) {
            map.put("userId", parts[4]);
        }
        return new Req(parts[0], parts[2],  parts[3], parts[parts.length - 2], map);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String nameQueue() {
        return nameQueue;
    }

    public String param(String key) {
        return params.get(key);
    }

    public String getKey() {
        return key;
    }
}
