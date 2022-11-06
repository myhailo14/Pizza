package lab.pizza.cook.handler;

import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.logger.PizzasLogger;
import lab.pizza.model.PizzaState;

public class CookMakeDoughHandler extends CookBaseHandler{

    public CookMakeDoughHandler(CookHandlersService cookHandlersService, final int id) {
        super(cookHandlersService, id);
    }

    @Override
    public void handlePizzaPart() {
        PizzasLogger.logPizzaStart(pizza);
        handlePizzaState(pizza, PizzaState.DOUGH);
        System.out.printf("Pizza %s is dough\n", pizza.getName());
        if (isStop()) {
            requestCookHandlerReplacement(this);
        } else {
            super.handlePizzaPart();
        }
    }
}
