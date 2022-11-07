package lab.pizza.cook.thread;

import lab.pizza.cook.handler.CookHandler;
import lab.pizza.cook.strategy.CookWorkingStrategy;
import lab.pizza.model.Pizza;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CookPizzaHandlerThread extends Thread {
    private final Pizza pizza;
    private final CookWorkingStrategy cookWorkingStrategy;
    private final int pizzaCreationMinTimeInSec;

    @Override
    public void run() {
        System.out.println("Got pizza to handle :" + pizza.getName() + "\tCurrent Thread :"
                + Thread.currentThread().getName());
        cookWorkingStrategy.setPizza(pizza);
        cookWorkingStrategy.setPizzaCreationMinTimeInSec(pizzaCreationMinTimeInSec);
        final CookHandler cookHandler = cookWorkingStrategy.getCookHandler();
        cookHandler.handlePizzaPart();
    }
}
