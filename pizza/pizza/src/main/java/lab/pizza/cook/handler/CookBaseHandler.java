package lab.pizza.cook.handler;

import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.model.Pizza;
import lab.pizza.model.PizzaState;

public abstract class CookBaseHandler implements CookHandler {
    protected final CookHandlersService cookHandlersService;
    private CookHandler next;
    protected Pizza pizza;
    protected int pizzaCreationMinTimeInSec;
    protected boolean isWorking;
    protected boolean isStop;
    public CookBaseHandler(final CookHandlersService cookHandlersService) {
        this.cookHandlersService = cookHandlersService;
        isWorking = false;
        isStop = false;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public int getPizzaCreationMinTimeInSec() {
        return pizzaCreationMinTimeInSec;
    }

    public void setPizzaCreationMinTimeInSec(int pizzaCreationMinTimeInSec) {
        this.pizzaCreationMinTimeInSec = pizzaCreationMinTimeInSec;
    }

    @Override
    public boolean isWorking() {
        return isWorking;
    }

    @Override
    public void setWorking(boolean working) {
        isWorking = working;
    }

    @Override
    public boolean isStop() {
        return isStop;
    }

    @Override
    public void setStop(boolean stop) {
        isStop = stop;
    }

    @Override
    public void handlePizzaPart() {
        if (next != null) {
            next.handlePizzaPart();
        }
    }

    @Override
    public void setNext(CookHandler handler) {
        next = handler;
    }

    protected void handlePizzaState(Pizza pizza, PizzaState requiredState) {
        isWorking = true;
        while (!isStop && pizza.getPizzaState() != requiredState) {
            pizza.setPizzaState(requiredState);
            try {
                Thread.sleep(pizzaCreationMinTimeInSec / 3 * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        isWorking = false;
    }
    protected void requestCookHandlerReplacement(CookHandler handler) {
        CookHandler cookHandler = cookHandlersService.getCookHandlerReplacement(handler);
        while (cookHandler == null){
            cookHandler = cookHandlersService.getCookHandlerReplacement(handler);
        }
        cookHandler.setPizza(pizza);
        cookHandler.handlePizzaPart();
    }
}
