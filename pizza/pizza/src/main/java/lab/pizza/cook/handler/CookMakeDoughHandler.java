package lab.pizza.cook.handler;

import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.logger.PizzasLogger;
import lab.pizza.model.PizzaState;

public class CookMakeDoughHandler extends CookBaseHandler{

    private static final String DOUGH_COOK_TYPE = "dough";
    public CookMakeDoughHandler(CookHandlersService cookHandlersService, final int id) {
        super(cookHandlersService, id);
        cookType = DOUGH_COOK_TYPE;
    }

    @Override
    public void handlePizzaPart() {
        PizzasLogger.logPizzaStart(pizza);
        handlePizzaState(pizza, PizzaState.DOUGH);
        System.out.printf("Pizza %s %d is dough\n", pizza.getName(), pizza.getId());
        if (isStop()) {
            requestCookHandlerReplacement(this);
        } else {
            this.pizza = null;
            isWorking = false;
            super.handlePizzaPart();
        }
    }
}
