package lab.pizza.cook.handler;

import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.model.PizzaState;

public class CookAloneHandler extends CookBaseHandler {
    public CookAloneHandler(CookHandlersService cookHandlersService) {
        super(cookHandlersService);
    }

    @Override
    public void handlePizzaPart() {
        isWorking = true;
        while (!isStop && pizza.getPizzaState() != PizzaState.READY) {
            for (PizzaState pizzaState : PizzaState.values()) {
                /*if (pizzaState != pizza.getPizzaState()) {
                    continue;
                }*/
                pizza.setPizzaState(pizzaState);
                if(pizzaState == PizzaState.READY){
                    break;
                }
                try {
                    Thread.sleep(pizzaCreationMinTimeInSec / PizzaState.values().length * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        isWorking = false;
        if (isStop) {
            requestCookHandlerReplacement(this);
        }
    }
}
