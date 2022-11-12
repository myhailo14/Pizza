package lab.pizza.queue.thread;

import lab.pizza.client.generator.ClientsGenerationStrategy;
import lab.pizza.client.model.Client;
import lab.pizza.queue.service.ClientsQueuesService;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Random;

public class ClientsQueuesServiceThread extends Thread {
    private final ClientsQueuesService clientsQueuesService;
    private final ClientsGenerationStrategy clientsGenerationStrategy;
    private final int clientGenerationDelayInSeconds;
    private final Random randomQueueIndexGenerator;

    public ClientsQueuesServiceThread(final ClientsQueuesService clientsQueuesService,
    final int clientGenerationDelayInSeconds) {
        this.clientsQueuesService = clientsQueuesService;
        clientsGenerationStrategy = clientsQueuesService.getClientsGenerationStrategy();
        randomQueueIndexGenerator = new Random();
        this.clientGenerationDelayInSeconds = clientGenerationDelayInSeconds;
    }

    @Override
    public void run() {
        while (true) {
            final List<Client> clients = clientsGenerationStrategy.generateClients();
            putClientsInQueue(clients);
            try {
                Thread.sleep(clientGenerationDelayInSeconds * 1000L);
            } catch (InterruptedException e) {
                System.out.println("Interruption of pizza handling...");
            }
        }
    }

    public void putClientsInQueue(final List<Client> clients) {
        int queuesNumber = clientsQueuesService.getClientsQueues().size();
        for (final Client client : clients) {
            int queueIndex = randomQueueIndexGenerator.nextInt(queuesNumber);
            clientsQueuesService.putClientInsideOfQueue(client, queueIndex);
        }
    }
}
