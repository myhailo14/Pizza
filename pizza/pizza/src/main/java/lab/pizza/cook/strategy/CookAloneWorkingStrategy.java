package lab.pizza.cook.strategy;

import lab.pizza.cook.handler.CookAloneHandler;
import lab.pizza.cook.handler.CookHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
public class CookAloneWorkingStrategy implements CookWorkingStrategy{

    @Override
    public CookHandler getCookHandler() {
        return new CookAloneHandler();
    }
}
