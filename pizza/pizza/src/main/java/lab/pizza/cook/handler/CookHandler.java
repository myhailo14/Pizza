package lab.pizza.cook.handler;

import lab.pizza.model.Pizza;

public interface CookHandler {
    void setPizza(Pizza pizza);
    boolean isWorking();
    boolean isStop();
    void setWorking(boolean isWorking);
    void setStop(boolean isStop);
    void handlePizzaPart();
    void setNext(CookHandler handler);
    void setPizzaCreationMinTimeInSec(int pizzaCreationMinTimeInSec);
    void setId(int id);
    int getId();
    String getCookType();
}
