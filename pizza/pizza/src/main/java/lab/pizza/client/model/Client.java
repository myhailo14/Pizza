package lab.pizza.client.model;

import lab.pizza.model.Pizza;
import lombok.Data;

import java.util.HashMap;

@Data
public class Client {
    private HashMap<Pizza, Integer> order;
    private int queueNumber;
    private static final int NO_QUEUE_NUMBER = -1;

    public Client() {
        order = new HashMap<>();
        queueNumber = NO_QUEUE_NUMBER;
    }
}
