package lab.pizza.client.model;

import lab.pizza.model.Pizza;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Data
public class Client {
    private List<Pizza> order;
    private int queueNumber;
    private static final int NO_QUEUE_NUMBER = -1;

    public Client() {
        order = new LinkedList<>();
        queueNumber = NO_QUEUE_NUMBER;
    }
}
