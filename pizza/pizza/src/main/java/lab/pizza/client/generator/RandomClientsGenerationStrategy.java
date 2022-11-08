package lab.pizza.client.generator;

import lab.pizza.client.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Scope(scopeName = "prototype")
public class RandomClientsGenerationStrategy implements ClientsGenerationStrategy {
    private final ClientsGenerator clientsGenerator;
    @Value("${max_number_of_clients_per_generation}")
    private int maxNumberOfClientsPerGeneration;
    private final Random randomClientsNumberGenerator;

    @Autowired
    public RandomClientsGenerationStrategy(ClientsGenerator clientsGenerator) {
        this.clientsGenerator = clientsGenerator;
        randomClientsNumberGenerator = new Random();
    }

    @Override
    public List<Client> generateClients() {
        int clientsAmount = randomClientsNumberGenerator.nextInt(maxNumberOfClientsPerGeneration + 1);
        return clientsGenerator.getClients(clientsAmount);
    }
}
