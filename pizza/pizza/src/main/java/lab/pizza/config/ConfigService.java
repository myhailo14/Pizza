package lab.pizza.config;

import lab.pizza.client.generator.ClientsGenerationStrategy;
import lab.pizza.cook.CookWorkingStrategy;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
public class ConfigService {
    private int payDesksNumber;
    private int cooksNumber;
    private int pizzasNumber;
    private int pizzaCreationMinTimeInSec;
    private CookWorkingStrategy cookWorkingStrategy;
    private ClientsGenerationStrategy clientsGenerationStrategy;
}
