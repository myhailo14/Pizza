package lab.pizza.cook.generator;

import lab.pizza.cook.handler.*;
import lab.pizza.cook.service.CookHandlersService;

import java.util.ArrayList;
import java.util.List;

public class CookHandlersGenerator {
    private final CookHandlersService cookHandlersService;
    private static final int COOK_TYPES_NUMBER = 3;
    private int currentCookHandlerId;
    public CookHandlersGenerator(CookHandlersService cookHandlersService) {
        this.cookHandlersService = cookHandlersService;
        currentCookHandlerId = 0;
    }

    public List<CookHandler> generateAloneCookHandlers(final int cooksNumber) {
        final List<CookHandler> cookHandlers = new ArrayList<>();
        for (int i = 0; i < cooksNumber; i++) {
            cookHandlers.add(new CookAloneHandler(cookHandlersService, currentCookHandlerId));
            currentCookHandlerId++;
        }
        return cookHandlers;
    }

    public List<CookHandler> generatePartCookHandlers(final int cooksNumber) {
        final List<CookHandler> cookHandlers = new ArrayList<>();
        int cooksAmountDivisibleOnCookTypesNumber = cooksNumber;
        int cooksLeftToAdd = 0;
        while(cooksAmountDivisibleOnCookTypesNumber % COOK_TYPES_NUMBER !=0){
            cooksAmountDivisibleOnCookTypesNumber--;
            cooksLeftToAdd++;
        }
        for (int i = 0; i < cooksAmountDivisibleOnCookTypesNumber; i += COOK_TYPES_NUMBER) {
            cookHandlers.add(new CookMakeDoughHandler(cookHandlersService, currentCookHandlerId++));
            cookHandlers.add(new CookFillPizzaHandler(cookHandlersService, currentCookHandlerId++));
            cookHandlers.add(new CookBakePizzaHandler(cookHandlersService, currentCookHandlerId++));
        }
        while (cooksLeftToAdd > 0) {
            if (cooksLeftToAdd % 2 == 0) {
                cookHandlers.add(new CookFillPizzaHandler(cookHandlersService, currentCookHandlerId++));
            } else {
                cookHandlers.add(new CookMakeDoughHandler(cookHandlersService, currentCookHandlerId++));
            }
            cooksLeftToAdd--;
        }
        return cookHandlers;
    }
}
