package lab.pizza.service;

import lab.pizza.client.generator.ClientsGenerationStrategy;
import lab.pizza.cook.handler.CookHandler;
import lab.pizza.cook.service.CookHandlersManagerService;
import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.dto.ConfigDto;
import lab.pizza.queue.model.ClientsQueue;
import lab.pizza.queue.service.ClientsQueuesManagerService;
import lab.pizza.queue.service.ClientsQueuesService;
import lab.pizza.repository.PizzaRepository;
import lab.pizza.service.factory.ClientsGenerationStrategyFactory;
import lab.pizza.service.factory.CookWorkingStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lab.pizza.config.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaService {
    private final ClientsGenerationStrategyFactory clientsGenerationStrategyFactory;
    private final CookWorkingStrategyFactory cookWorkingStrategyFactory;
    private final PizzaRepository pizzaRepository;
    private final CookHandlersService cookHandlersService;
    private ClientsQueuesService clientsQueuesService;
    private ClientsQueuesManagerService clientsQueuesManagerService;
    private CookHandlersManagerService cookHandlersManagerService;
    private ConfigService configService;
    private final List<ClientsQueue> clientsQueues;
    @Value("${client_generation_delay_in_seconds}")
    private int clientGenerationDelayInSeconds;

    @Autowired
    public PizzaService(final ClientsGenerationStrategyFactory clientsGenerationStrategyFactory,
                        final CookWorkingStrategyFactory cookWorkingStrategyFactory,
                        final PizzaRepository pizzaRepository, CookHandlersService cookHandlersService) {
        this.clientsGenerationStrategyFactory = clientsGenerationStrategyFactory;
        this.cookWorkingStrategyFactory = cookWorkingStrategyFactory;
        this.cookHandlersService = cookHandlersService;
        clientsQueues = new ArrayList<>();
        this.pizzaRepository = pizzaRepository;
    }


    public void initConfigService(ConfigDto configDto) {
        ClientsGenerationStrategy clientsGenerationStrategy = clientsGenerationStrategyFactory
                .getClientsGenerationStrategy(configDto.getClientsGenerationStrategy());
        configService = createConfig(clientsGenerationStrategy, configDto);
    }

    private void loadPizzas() {
        pizzaRepository.loadPizzas(configService.getPizzasNumber());
    }

    public void startService() {
        loadPizzas();
        createEmptyClientsQueues();
        startClientsQueuesFilling();
        startClientOrdersHandling();
    }

    private void startClientOrdersHandling() {
        cookHandlersManagerService = new CookHandlersManagerService(configService, cookWorkingStrategyFactory, clientsQueuesService);
        cookHandlersManagerService.startOrdersHandling();
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
                                       ConfigDto configDto) {
        return ConfigService.builder()
                .cookWorkingStrategy(configDto.getCookWorkingStrategy())
                .clientsGenerationStrategy(clientsGenerationStrategy)
                .pizzasNumber(configDto.getPizzasNumber())
                .payDesksNumber(configDto.getPayDesksNumber())
                .cooksNumber(configDto.getCooksNumber())
                .pizzaCreationMinTimeInSec(configDto.getPizzaCreationMinTimeInSec())
                .build();
    }

    public List<ClientsQueue> getClientsQueues() {
        return clientsQueues;
    }

    public List<CookHandler> getCookHandlers() {
        return cookHandlersService.getCookHandlers();
    }

    public void stopCook(final int id) {
        setStopForCook(id, true);
    }

    private void setStopForCook(final int id, final boolean stop) {
        var cookHandlers = cookHandlersService.getCookHandlers();
        for (var cookHandler : cookHandlers) {
            if (cookHandler.getId() == id) {
                cookHandler.setStop(stop);
                break;
            }
        }
    }

    public void resumeCook(final int id) {
        setStopForCook(id, false);
    }
}
