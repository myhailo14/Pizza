package lab.pizza.service.factory;

import lab.pizza.client.generator.ClientsGenerationStrategy;
import lab.pizza.client.generator.DefaultClientsGenerationStrategy;
import lab.pizza.client.generator.RandomClientsGenerationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ClientsGenerationStrategyFactory {
    private final HashMap<String, ClientsGenerationStrategy> clientsGenerationMap;

    @Autowired
    public ClientsGenerationStrategyFactory(final DefaultClientsGenerationStrategy defaultClientsGenerationStrategy,
                                            final RandomClientsGenerationStrategy randomClientsGenerationStrategy) {
        clientsGenerationMap = new HashMap<>();
        clientsGenerationMap.put("default", defaultClientsGenerationStrategy);
        clientsGenerationMap.put("random", randomClientsGenerationStrategy);
    }
    public ClientsGenerationStrategy getClientsGenerationStrategy(String strategyName){
        return clientsGenerationMap.get(strategyName);
    }
}
