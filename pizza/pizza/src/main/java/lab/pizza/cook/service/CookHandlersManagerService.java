package lab.pizza.cook.service;

import lab.pizza.config.ConfigService;
import lab.pizza.cook.thread.CookHandlingThread;
import lab.pizza.queue.service.ClientsQueuesService;
import lab.pizza.service.factory.CookWorkingStrategyFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CookHandlersManagerService {
    private final ConfigService configService;
    private final CookWorkingStrategyFactory cookWorkingStrategyFactory;
    private final ClientsQueuesService clientsQueuesService;

    public void startOrdersHandling() {
        var clientsQueues = clientsQueuesService.getClientsQueues();
        for (var clientsQueue : clientsQueues) {
            final CookHandlingThread cookHandlingThread =
                    new CookHandlingThread(cookWorkingStrategyFactory, clientsQueue,
                            clientsQueuesService, configService);
            cookHandlingThread.start();
        }
    }
}
