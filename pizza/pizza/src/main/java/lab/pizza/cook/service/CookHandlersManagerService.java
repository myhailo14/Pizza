package lab.pizza.cook.service;

import lab.pizza.config.ConfigService;
import lab.pizza.cook.thread.CookHandlingThread;
import lab.pizza.queue.service.ClientsQueuesService;
import lab.pizza.service.factory.CookWorkingStrategyFactory;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class CookHandlersManagerService {
    private final ConfigService configService;
    private final CookWorkingStrategyFactory cookWorkingStrategyFactory;
    private final ClientsQueuesService clientsQueuesService;

    private List<Thread> cookHandlingThreads;

    public void startOrdersHandling() {
        cookHandlingThreads = new LinkedList<>();
        var clientsQueues = clientsQueuesService.getClientsQueues();
        for (var clientsQueue : clientsQueues) {
            final CookHandlingThread cookHandlingThread =
                    new CookHandlingThread(cookWorkingStrategyFactory, clientsQueue,
                            clientsQueuesService, configService);
            cookHandlingThread.start();
            cookHandlingThreads.add(cookHandlingThread);
        }
    }

    public void stopOrdersHandling() {
        for (final Thread thread : cookHandlingThreads) {
            thread.stop();
        }
    }
}
