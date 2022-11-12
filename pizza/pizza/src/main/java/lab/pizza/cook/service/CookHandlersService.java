package lab.pizza.cook.service;


import lab.pizza.cook.generator.CookHandlersGenerator;
import lab.pizza.cook.handler.CookHandler;
import lab.pizza.cook.strategy.CookAloneWorkingStrategy;
import lab.pizza.cook.strategy.CookPartWorkingStrategy;
import lab.pizza.cook.strategy.CookWorkingStrategy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Setter
@RequiredArgsConstructor
@Getter
public class CookHandlersService {
    private final CookHandlersGenerator cookHandlersGenerator;
    private final List<CookHandler> cookHandlers;
    private boolean areCookHandlersLoaded;

    @Autowired
    public CookHandlersService() {
        cookHandlers = new ArrayList<>();
        areCookHandlersLoaded = false;
        this.cookHandlersGenerator = new CookHandlersGenerator(this);
    }

    public synchronized void loadCookHandlers(final int cooksNumber, final CookWorkingStrategy cookWorkingStrategy) {
        if (cookWorkingStrategy != null && !areCookHandlersLoaded) {
            createCookHandlersDependingOnWorkingStrategy(cookWorkingStrategy, cooksNumber);
            areCookHandlersLoaded = true;
        }
    }

    public boolean areCookHandlersLoaded() {
        return areCookHandlersLoaded;
    }

    private void createCookHandlersDependingOnWorkingStrategy(final CookWorkingStrategy cookWorkingStrategy,
                                                              final int cooksNumber) {
        List<CookHandler> resultHandlers = new ArrayList<>();
        if (cookWorkingStrategy instanceof CookAloneWorkingStrategy) {
            resultHandlers = cookHandlersGenerator.generateAloneCookHandlers(cooksNumber);
        } else if (cookWorkingStrategy instanceof CookPartWorkingStrategy) {
            resultHandlers = cookHandlersGenerator.generatePartCookHandlers(cooksNumber);
        }
        cookHandlers.addAll(resultHandlers);
    }

    public synchronized CookHandler getCookHandlerReplacement(final CookHandler cookHandler) {
        for (final CookHandler handler : cookHandlers) {
            if (handler.getClass() == cookHandler.getClass() &&
                    (!handler.isWorking() && !handler.isStop())) {
                handler.setWorking(true);
                return handler;
            }
        }
        return null;
    }
}
