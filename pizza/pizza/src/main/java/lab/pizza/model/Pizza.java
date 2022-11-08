package lab.pizza.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Pizza {
    private int id;
    private String name;
    private PizzaState pizzaState;
}
