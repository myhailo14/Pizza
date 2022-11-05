package lab.pizza.cook.strategy;

import lab.pizza.cook.handler.CookBakePizzaHandler;
import lab.pizza.cook.handler.CookFillPizzaHandler;
import lab.pizza.cook.handler.CookHandler;
import lab.pizza.cook.handler.CookMakeDoughHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
public class CookPartWorkingStrategy implements CookWorkingStrategy {
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
