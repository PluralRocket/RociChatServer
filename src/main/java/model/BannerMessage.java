package model;

public class BannerMessage extends Sendable{

    private final String payload;

    public BannerMessage(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}
