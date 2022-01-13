package model;

import java.util.Collections;
import java.util.Map;

public class TestMessage extends Sendable{

    public final Map<String, Integer> payload;

    public TestMessage(Map<String, Integer> payload) {
        this.payload = Collections.unmodifiableMap(payload);
    }

    public Map<String, Integer> getPayload() {
        return payload;
    }
}
