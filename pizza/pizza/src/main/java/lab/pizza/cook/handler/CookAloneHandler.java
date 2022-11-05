package lab.pizza.cook.handler;

import lab.pizza.model.Pizza;
import lab.pizza.model.PizzaState;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CookAloneHandler extends CookBaseHandler {
    private final Pizza pizza;
    private final int pizzaCreationMinTimeInSec;
    @Override
    public void handlePizzaPart() {
        //cooking alone...
        for(PizzaState pizzaState : PizzaState.values()){
            pizza.setPizzaState(pizzaState);
            try {
                Thread.sleep(pizzaCreationMinTimeInSec*1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
