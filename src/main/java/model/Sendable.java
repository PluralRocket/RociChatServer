package model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TestMessage.class, name = "test_message"),
        @JsonSubTypes.Type(value = BannerMessage.class, name = "banner_message")
})
public abstract class Sendable {

    private Sender from;
    private Recipient to;
    private String timestamp;

    public Sender getFrom() {
        return from;
    }

    public Recipient getTo() {
        return to;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
