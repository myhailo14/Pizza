package lab.pizza.client.generator;

import lab.pizza.client.model.Client;

import java.util.List;

public interface ClientsGenerationStrategy {
    List<Client> generateClients();
}
