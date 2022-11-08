package lab.pizza.service.factory;

import lab.pizza.cook.service.CookHandlersService;
import lab.pizza.cook.strategy.CookAloneWorkingStrategy;
import lab.pizza.cook.strategy.CookPartWorkingStrategy;
import lab.pizza.cook.strategy.CookWorkingStrategy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Getter
public class CookWorkingStrategyFactory {
    private final CookHandlersService cookHandlersService;

    @Autowired
    public CookWorkingStrategyFactory(final CookHandlersService cookHandlersService) {
        this.cookHandlersService = cookHandlersService;
    }

    public CookWorkingStrategy getCookWorkingStrategy(String strategyName, final int cooksNumber) {
        CookWorkingStrategy cookWorkingStrategy = null;
        if (Objects.equals(strategyName, "alone")) {
            cookWorkingStrategy = getCookAloneWorkingStrategy(cooksNumber);
        } else if (Objects.equals(strategyName, "part")) {
            cookWorkingStrategy = getCookPartWorkingStrategy(cooksNumber);
        }
        return cookWorkingStrategy;
    }

    private CookAloneWorkingStrategy getCookAloneWorkingStrategy(final int cooksNumber) {
        var strategy = new CookAloneWorkingStrategy(cookHandlersService);
        strategy.setCooksNumber(cooksNumber);
        return strategy;
    }

    private CookPartWorkingStrategy getCookPartWorkingStrategy(final int cooksNumber) {
        var strategy = new CookPartWorkingStrategy(cookHandlersService);
        strategy.setCooksNumber(cooksNumber);
        return strategy;
    }
}
