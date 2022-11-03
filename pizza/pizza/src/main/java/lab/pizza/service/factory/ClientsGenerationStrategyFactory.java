package lab.pizza.service.factory;

import lab.pizza.client.generator.ClientsGenerationStrategy;
import lab.pizza.client.generator.DefaultClientsGenerationStrategy;
import lab.pizza.client.generator.RandomClientsGenerationStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ClientsGenerationStrategyFactory {
    private final HashMap<String, ClientsGenerationStrategy> clientsGenerationMap;

    public ClientsGenerationStrategyFactory() {
        clientsGenerationMap = new HashMap<>();
        clientsGenerationMap.put("default", new DefaultClientsGenerationStrategy());
        clientsGenerationMap.put("random", new RandomClientsGenerationStrategy());
    }
    public ClientsGenerationStrategy getClientsGenerationStrategy(String strategyName){
        return clientsGenerationMap.get(strategyName);
    }
}
