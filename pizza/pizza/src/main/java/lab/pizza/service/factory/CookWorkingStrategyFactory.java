package lab.pizza.service.factory;

import lab.pizza.cook.strategy.CookAloneWorkingStrategy;
import lab.pizza.cook.strategy.CookPartWorkingStrategy;
import lab.pizza.cook.strategy.CookWorkingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CookWorkingStrategyFactory {
    private final HashMap<String, CookWorkingStrategy> cookWorkingStrategyMap;
    @Autowired
    public CookWorkingStrategyFactory(final CookAloneWorkingStrategy cookAloneWorkingStrategy,
                                      final CookPartWorkingStrategy cookPartWorkingStrategy){
        cookWorkingStrategyMap = new HashMap<>();
        cookWorkingStrategyMap.put("alone", cookAloneWorkingStrategy);
        cookWorkingStrategyMap.put("part", cookPartWorkingStrategy);
    }
    public CookWorkingStrategy getCookWorkingStrategy(String strategyName){
        return cookWorkingStrategyMap.get(strategyName);
    }
}
