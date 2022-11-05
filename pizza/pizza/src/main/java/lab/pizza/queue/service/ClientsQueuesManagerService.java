package lab.pizza.queue.service;

import lab.pizza.queue.thread.ClientsQueuesServiceThread;

public class ClientsQueuesManagerService {
    private final ClientsQueuesServiceThread clientsQueuesServiceThread;

    public ClientsQueuesManagerService(final ClientsQueuesService clientsQueuesService,
                                       final int clientGenerationDelayInSeconds) {
        clientsQueuesServiceThread = new ClientsQueuesServiceThread(clientsQueuesService, clientGenerationDelayInSeconds);
    }
    public void startQueuesFilling(){
        clientsQueuesServiceThread.start();
    }
}
