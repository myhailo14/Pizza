package lab.pizza.service.factory;

import lab.pizza.cook.CookAloneWorkingStrategy;
import lab.pizza.cook.CookPartWorkingStrategy;
import lab.pizza.cook.CookWorkingStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CookWorkingStrategyFactory {
    private final HashMap<String, CookWorkingStrategy> cookWorkingStrategyMap;
    public CookWorkingStrategyFactory(){
        cookWorkingStrategyMap = new HashMap<>();
        cookWorkingStrategyMap.put("alone", new CookAloneWorkingStrategy());
        cookWorkingStrategyMap.put("part", new CookPartWorkingStrategy());
    }
    public CookWorkingStrategy getCookWorkingStrategy(String strategyName){
        return cookWorkingStrategyMap.get(strategyName);
    }
}
