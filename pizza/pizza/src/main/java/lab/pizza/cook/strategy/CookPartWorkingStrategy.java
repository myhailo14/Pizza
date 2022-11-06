package lab.pizza.cook.strategy;

import lab.pizza.cook.handler.CookBakePizzaHandler;
import lab.pizza.cook.handler.CookFillPizzaHandler;
import lab.pizza.cook.handler.CookHandler;
import lab.pizza.cook.handler.CookMakeDoughHandler;
import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.model.Pizza;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Setter
public class CookPartWorkingStrategy implements CookWorkingStrategy {
    private final CookHandlersService cookHandlersService;
    private int cooksNumber;
    private Pizza pizza;

    @Override
    public synchronized CookHandler getCookHandler() {
        if (!cookHandlersService.areCookHandlersLoaded()) {
            cookHandlersService.loadCookHandlers(cooksNumber, this);
        }
        final CookHandler doughHandler = getCookHandlerForPizzaMakingStart(new CookMakeDoughHandler(cookHandlersService));
        final CookHandler fillingHandler = getCookHandlerForPizzaMakingStart(new CookFillPizzaHandler(cookHandlersService));
        final CookHandler bakeHandler = getCookHandlerForPizzaMakingStart(new CookBakePizzaHandler(cookHandlersService));
        setPizzaForCookHandlers(List.of(doughHandler, fillingHandler, bakeHandler), pizza);
        doughHandler.setNext(fillingHandler);
        fillingHandler.setNext(bakeHandler);
        return doughHandler;
    }

    private void setPizzaForCookHandlers(List<CookHandler> cookHandlers, Pizza pizza) {
        for (CookHandler cookHandler : cookHandlers) {
            cookHandler.setPizza(pizza);
        }
    }

    @Override
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    private CookHandler getCookHandlerForPizzaMakingStart(CookHandler cookHandler) {
        var handler = cookHandlersService.getCookHandlerReplacement(cookHandler);
        while (handler == null) {
            handler = cookHandlersService.getCookHandlerReplacement(cookHandler);
        }
        return handler;
    }
}
