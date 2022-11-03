package lab.pizza.cook;

public class CookPartWorkingStrategy implements CookWorkingStrategy{
    @Override
    public CookHandler getCookHandler() {
        CookHandler doughHandler = new CookMakeDoughHandler();
        CookHandler fillingHandler = new CookFillPizzaHandler();
        CookHandler bakeHandler = new CookBakePizzaHandler();
        doughHandler.setNext(fillingHandler);
        fillingHandler.setNext(bakeHandler);
        return doughHandler;
    }
}
