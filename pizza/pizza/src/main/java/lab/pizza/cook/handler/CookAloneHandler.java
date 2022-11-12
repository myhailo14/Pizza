package lab.pizza.cook.handler;

import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.logger.PizzasLogger;
import lab.pizza.model.PizzaState;

public class CookAloneHandler extends CookBaseHandler {

    private static final String ALONE_COOK_TYPE = "alone";
    public CookAloneHandler(CookHandlersService cookHandlersService, final int id) {
        super(cookHandlersService, id);
        cookType = ALONE_COOK_TYPE;
    }

    @Override
    public void handlePizzaPart() {
        isWorking = true;
        //System.out.println("Starting pizza handling :" + pizza.getName());
        while (!isStop && pizza.getPizzaState() != PizzaState.READY) {
            PizzasLogger.logPizzaStart(pizza);
            for (PizzaState pizzaState : PizzaState.values()) {
                if (pizzaState.getValue() < pizza.getPizzaState().getValue()) {
                    continue;
                }
                pizza.setPizzaState(pizzaState);
                System.out.println(pizza.getName() + " " +pizza.getId()+" "+ pizza.getPizzaState());
                if (pizzaState == PizzaState.READY) {
                    break;
                }
                if (isStop) {
                    break;
                }
                try {
                    Thread.sleep(pizzaCreationMinTimeInSec / (PizzaState.values().length - 2) * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //System.out.println("Stopped pizza handling, pizza state:" + pizza.getPizzaState().name());
        isWorking = false;
        if (isStop) {
            requestCookHandlerReplacement(this);
        } else {
            PizzasLogger.logPizzaEnd(pizza);
        }
    }
}
