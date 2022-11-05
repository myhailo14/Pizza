package lab.pizza.service;

import lab.pizza.client.generator.ClientsGenerationStrategy;
import lab.pizza.cook.strategy.CookWorkingStrategy;
import lab.pizza.dto.ConfigDto;
import lab.pizza.queue.model.ClientsQueue;
import lab.pizza.queue.service.ClientsQueuesManagerService;
import lab.pizza.queue.service.ClientsQueuesService;
import lab.pizza.service.factory.ClientsGenerationStrategyFactory;
import lab.pizza.service.factory.CookWorkingStrategyFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lab.pizza.config.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaService {
    private final ClientsGenerationStrategyFactory clientsGenerationStrategyFactory;
    private final CookWorkingStrategyFactory cookWorkingStrategyFactory;
    private ClientsQueuesService clientsQueuesService;
    private ClientsQueuesManagerService clientsQueuesManagerService;
    private ConfigService configService;
    private final List<ClientsQueue> clientsQueues;
    @Value("${client_generation_delay_in_seconds}")
    private int clientGenerationDelayInSeconds;

    public PizzaService(ClientsGenerationStrategyFactory clientsGenerationStrategyFactory,
                        CookWorkingStrategyFactory cookWorkingStrategyFactory) {
        this.clientsGenerationStrategyFactory = clientsGenerationStrategyFactory;
        this.cookWorkingStrategyFactory = cookWorkingStrategyFactory;
        clientsQueues = new ArrayList<>();
    }


    public void initConfigService(ConfigDto configDto) {
        ClientsGenerationStrategy clientsGenerationStrategy = clientsGenerationStrategyFactory
                .getClientsGenerationStrategy(configDto.getClientsGenerationStrategy());
        CookWorkingStrategy cookWorkingStrategy = cookWorkingStrategyFactory
                .getCookWorkingStrategy(configDto.getCookWorkingStrategy());
        configService = createConfig(clientsGenerationStrategy, cookWorkingStrategy, configDto);
    }

    public void startService() {
        createEmptyClientsQueues();
        startClientsQueuesFilling();
    }

    private void startClientsQueuesFilling() {
        clientsQueuesManagerService = new ClientsQueuesManagerService(clientsQueuesService, clientGenerationDelayInSeconds);
        clientsQueuesManagerService.startQueuesFilling();
    }

    private void createEmptyClientsQueues() {
        clientsQueuesService = new ClientsQueuesService(clientsQueues, configService.getClientsGenerationStrategy());
        clientsQueuesService.createClientsQueues(configService.getPayDesksNumber());
    }

    private ConfigService createConfig(ClientsGenerationStrategy clientsGenerationStrategy,
                                       CookWorkingStrategy cookWorkingStrategy,
                                       ConfigDto configDto) {
        return ConfigService.builder()
                .cookWorkingStrategy(cookWorkingStrategy)
                .clientsGenerationStrategy(clientsGenerationStrategy)
                .pizzasNumber(configDto.getPizzasNumber())
                .payDesksNumber(configDto.getPayDesksNumber())
                .cooksNumber(configDto.getCooksNumber())
                .pizzaCreationMinTimeInSec(configDto.getPizzaCreationMinTimeInSec())
                .build();
    }

}
