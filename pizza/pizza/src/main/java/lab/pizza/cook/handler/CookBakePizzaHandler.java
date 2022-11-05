package lab.pizza.cook.handler;

import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.model.PizzaState;

public class CookBakePizzaHandler extends CookBaseHandler {

    public CookBakePizzaHandler(CookHandlersService cookHandlersService) {
        super(cookHandlersService);
    }

    @Override
    public void handlePizzaPart() {
        handlePizzaState(pizza, PizzaState.BAKING);
        if (isStop()) {
            requestCookHandlerReplacement(this);
        } else {
            pizza.setPizzaState(PizzaState.READY);
        }
    }
}
