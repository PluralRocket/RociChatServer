package broker;

import model.Message;
import model.Sendable;

public interface Broker {

    void brokerMessage(Message message);
}
