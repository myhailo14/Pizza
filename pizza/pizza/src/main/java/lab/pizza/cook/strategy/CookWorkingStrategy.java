package lab.pizza.cook.strategy;

import lab.pizza.cook.handler.CookHandler;
import lab.pizza.model.Pizza;

public interface CookWorkingStrategy {
    void setCooksNumber(int cooksNumber);
    CookHandler getCookHandler();
    void setPizza(Pizza pizza);
    void setPizzaCreationMinTimeInSec(final int pizzaCreationMinTimeInSec);
}
