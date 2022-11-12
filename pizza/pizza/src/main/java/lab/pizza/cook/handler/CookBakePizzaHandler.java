package lab.pizza.cook.handler;

import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.logger.PizzasLogger;
import lab.pizza.model.PizzaState;

public class CookBakePizzaHandler extends CookBaseHandler {

    private static final String BAKE_PIZZA_TYPE = "bake";
    public CookBakePizzaHandler(CookHandlersService cookHandlersService, final int id) {
        super(cookHandlersService, id);
        cookType = BAKE_PIZZA_TYPE;
    }

    @Override
    public void handlePizzaPart() {
        handlePizzaState(pizza, PizzaState.BAKING);
        System.out.printf("Pizza %s is baked\n", pizza.getName());
        if (isStop()) {
            requestCookHandlerReplacement(this);
        } else {
            PizzasLogger.logPizzaEnd(pizza);
            pizza.setPizzaState(PizzaState.READY);
        }
    }
}
