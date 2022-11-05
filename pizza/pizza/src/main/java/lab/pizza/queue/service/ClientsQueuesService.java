package lab.pizza.queue.service;

import lab.pizza.client.generator.ClientsGenerationStrategy;
import lab.pizza.client.model.Client;
import lab.pizza.queue.model.ClientsQueue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientsQueuesService {
    private final List<ClientsQueue> clientsQueues;
    private final ClientsGenerationStrategy clientsGenerationStrategy;

    public ClientsQueuesService(List<ClientsQueue> clientsQueues, ClientsGenerationStrategy clientsGenerationStrategy) {
        this.clientsQueues = clientsQueues;
        this.clientsGenerationStrategy = clientsGenerationStrategy;
    }

    public void createClientsQueues(int queuesNumber) {
        clientsQueues.clear();
        for (int i = 0; i < queuesNumber; i++) {
            clientsQueues.add(new ClientsQueue());
        }
    }

    public synchronized void putClientInsideOfQueue(Client client, int queueIndex) {
        if (queueIndex < 0 || queueIndex >= clientsQueues.size()) {
            return;
        }
        System.out.println("Got client "+client+"\t put client in queue #"+queueIndex);
        client.setQueueNumber(queueIndex);
        clientsQueues.get(queueIndex).addClient(client);
    }
}
