package lab.pizza.config;

import lab.pizza.client.generator.ClientsGenerationStrategy;
import lab.pizza.cook.strategy.CookWorkingStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigService {
    private int payDesksNumber;
    private int cooksNumber;
    private int pizzasNumber;
    private int pizzaCreationMinTimeInSec;
    private String cookWorkingStrategy;
    private ClientsGenerationStrategy clientsGenerationStrategy;
}
