package lab.pizza.queue.model;

import lab.pizza.client.model.Client;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ClientsQueue {
    private final List<Client> clients;

    public ClientsQueue() {
        clients = new LinkedList<>();
    }

    public void addClient(Client client){
        clients.add(client);
    }
}
