package lab.pizza.cook.thread;

import lab.pizza.client.model.Client;
import lab.pizza.config.ConfigService;
import lab.pizza.cook.strategy.CookWorkingStrategy;
import lab.pizza.queue.model.ClientsQueue;
import lab.pizza.queue.service.ClientsQueuesService;
import lab.pizza.service.factory.CookWorkingStrategyFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CookHandlingThread extends Thread {
    private final CookWorkingStrategyFactory cookWorkingStrategyFactory;
    private final ClientsQueue clientsQueue;
    private final ClientsQueuesService clientsQueuesService;
    private final ConfigService configService;
    private static final int FIRST_CLIENT_INDEX = 0;

    @Override
    public void run() {
        final CookWorkingStrategy cookWorkingStrategy = cookWorkingStrategyFactory
                .getCookWorkingStrategy(configService.getCookWorkingStrategy(), configService.getCooksNumber());
        while (true) {
            if (isClientsQueueFilled(clientsQueue)) {
                var firstClientInQueue = clientsQueue.getClients().get(FIRST_CLIENT_INDEX);
                handleClientOrder(firstClientInQueue, cookWorkingStrategy);
                popClientFromQueue(firstClientInQueue);
            }
        }
    }

    private void popClientFromQueue(final Client client) {
        clientsQueuesService.deleteClientFromQueue(client);
    }

    private void handleClientOrder(final Client client, final CookWorkingStrategy cookWorkingStrategy) {
        var order = client.getOrder();
        final int pizzaCreationMinTimeInSec = configService.getPizzaCreationMinTimeInSec();
        for (var pizza : order) {
            cookWorkingStrategy.setPizza(pizza);
            var cookHandler = cookWorkingStrategy.getCookHandler();
            cookHandler.setPizzaCreationMinTimeInSec(pizzaCreationMinTimeInSec);
            cookHandler.handlePizzaPart();
        }
    }


    private boolean isClientsQueueFilled(final ClientsQueue clientsQueue) {
        return clientsQueue != null && clientsQueue.getClients().size() != 0;
    }
}
