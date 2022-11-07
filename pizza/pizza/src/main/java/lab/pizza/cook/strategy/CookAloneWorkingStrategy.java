package lab.pizza.cook.strategy;

import lab.pizza.cook.handler.CookAloneHandler;
import lab.pizza.cook.handler.CookHandler;
import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.model.Pizza;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
public class CookAloneWorkingStrategy implements CookWorkingStrategy {
    private final CookHandlersService cookHandlersService;
    private int cooksNumber;
    private Pizza pizza;
    private int pizzaCreationMinTimeInSec;

    @Override
    public CookHandler getCookHandler() {
        if (!cookHandlersService.areCookHandlersLoaded()) {
            cookHandlersService.loadCookHandlers(cooksNumber, this);
        }
        var cookHandler = getCookHandlerFromService();
        cookHandler.setPizza(pizza);
        cookHandler.setPizzaCreationMinTimeInSec(pizzaCreationMinTimeInSec);
        return cookHandler;
    }

    private CookHandler getCookHandlerFromService() {
        var cookHandler = cookHandlersService
                .getCookHandlerReplacement(new CookAloneHandler(cookHandlersService, 0));
        while(cookHandler == null){
            cookHandler = cookHandlersService
                    .getCookHandlerReplacement(new CookAloneHandler(cookHandlersService, 0));
        }
        return cookHandler;
    }

    @Override
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
