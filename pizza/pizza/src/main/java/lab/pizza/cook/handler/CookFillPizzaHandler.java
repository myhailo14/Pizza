package lab.pizza.cook.handler;

import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.model.PizzaState;

public class CookFillPizzaHandler extends CookBaseHandler {
    private static final String FILL_COOK_TYPE = "fill";
    public CookFillPizzaHandler(CookHandlersService cookHandlersService, final int id) {
        super(cookHandlersService, id);
        cookType = FILL_COOK_TYPE;
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
