package lab.pizza.queue.service;

import lab.pizza.client.generator.ClientsGenerationStrategy;
import lab.pizza.client.model.Client;
import lab.pizza.queue.model.ClientsQueue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.Semaphore;

@Getter
@Setter
public class ClientsQueuesService {
    private final List<ClientsQueue> clientsQueues;
    private final Semaphore clientQueuesSemaphore;
    private static final int SEMAPHORE_PERMITS = 1;
    private final ClientsGenerationStrategy clientsGenerationStrategy;

    public ClientsQueuesService(List<ClientsQueue> clientsQueues, ClientsGenerationStrategy clientsGenerationStrategy) {
        this.clientsQueues = clientsQueues;
        this.clientsGenerationStrategy = clientsGenerationStrategy;
        clientQueuesSemaphore = new Semaphore(SEMAPHORE_PERMITS);
    }

    public void createClientsQueues(int queuesNumber) {
        clientsQueues.clear();
        for (int i = 0; i < queuesNumber; i++) {
            clientsQueues.add(new ClientsQueue());
        }
    }

    public synchronized void putClientInsideOfQueue(Client client, int queueIndex) {
        try {
            clientQueuesSemaphore.acquire();
            client.setQueueNumber(queueIndex);
            System.out.println("Got client " + client + "\t put client in queue #" + queueIndex);
            clientsQueues.get(queueIndex).addClient(client);
            clientQueuesSemaphore.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void deleteClientFromQueue(Client client) {
        try {
            clientQueuesSemaphore.acquire();
            clientsQueues.get(client.getQueueNumber()).getClients().remove(client);
            System.out.println("Deleted client from queue "+client.getQueueNumber());
            clientQueuesSemaphore.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
