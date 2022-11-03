package lab.pizza.service;

import lab.pizza.client.generator.ClientsGenerationStrategy;
import lab.pizza.cook.CookWorkingStrategy;
import lab.pizza.dto.ConfigDto;
import lab.pizza.service.factory.ClientsGenerationStrategyFactory;
import lab.pizza.service.factory.CookWorkingStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lab.pizza.config.*;

@Service
@RequiredArgsConstructor
public class PizzaService {
    private final ClientsGenerationStrategyFactory clientsGenerationStrategyFactory;
    private final CookWorkingStrategyFactory cookWorkingStrategyFactory;

    private ConfigService configService;

    public void initConfigService(ConfigDto configDto) {
        ClientsGenerationStrategy clientsGenerationStrategy = clientsGenerationStrategyFactory
                .getClientsGenerationStrategy(configDto.getClientsGenerationStrategy());
        CookWorkingStrategy cookWorkingStrategy = cookWorkingStrategyFactory
                .getCookWorkingStrategy(configDto.getCookWorkingStrategy());
        configService = createConfig(clientsGenerationStrategy, cookWorkingStrategy, configDto);
    }

    public void startService() {

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
