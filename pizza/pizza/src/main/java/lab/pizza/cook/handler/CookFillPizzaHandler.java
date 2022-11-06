package lab.pizza.cook.handler;

import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.model.PizzaState;

public class CookFillPizzaHandler extends CookBaseHandler {
    public CookFillPizzaHandler(CookHandlersService cookHandlersService) {
        super(cookHandlersService);
    }

    @Override
    public void handlePizzaPart() {
        handlePizzaState(pizza, PizzaState.FILLING);
        System.out.printf("Pizza %s is filled\n", pizza.getName());
        if (isStop()) {
            requestCookHandlerReplacement(this);
        } else {
            super.handlePizzaPart();
        }
    }
}
