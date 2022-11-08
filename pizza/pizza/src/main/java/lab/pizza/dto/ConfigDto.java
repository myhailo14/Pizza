package lab.pizza.dto;

import lombok.Data;

@Data
public class ConfigDto {
    private int payDesksNumber;
    private int cooksNumber;
    private int pizzasNumber;
    private int pizzaCreationMinTimeInSec;
    private String cookWorkingStrategy;
    private String clientsGenerationStrategy;
}
