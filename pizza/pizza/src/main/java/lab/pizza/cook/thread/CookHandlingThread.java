package lab.pizza.cook.thread;

import lab.pizza.client.model.Client;
import lab.pizza.config.ConfigService;
import lab.pizza.cook.strategy.CookWorkingStrategy;
import lab.pizza.queue.model.ClientsQueue;
import lab.pizza.queue.service.ClientsQueuesService;
import lab.pizza.service.factory.CookWorkingStrategyFactory;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;


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
        List<CookPizzaHandlerThread> cookPizzaHandlerThreads = new LinkedList<>();
        for (var pizza : order) {
            final CookPizzaHandlerThread cookPizzaHandlerThread =
                    new CookPizzaHandlerThread(pizza, cookWorkingStrategy, pizzaCreationMinTimeInSec);
            cookPizzaHandlerThread.start();
            cookPizzaHandlerThreads.add(cookPizzaHandlerThread);
        }
        for (var thread : cookPizzaHandlerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private boolean isClientsQueueFilled(final ClientsQueue clientsQueue) {
        return clientsQueue != null && clientsQueue.getClients().size() != 0;
    }
}
