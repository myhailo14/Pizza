package lab.pizza.util;

import lab.pizza.model.Pizza;
import lab.pizza.model.PizzaState;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class PizzaJsonReader {
    @Value("${pizzas_json_path}")
    private String pizzasJsonPath;

    public List<Pizza> getPizzasFromJson(final int pizzasNumber) {
        List<Pizza> pizzas = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(pizzasJsonPath));
            JSONArray jsonArray = (JSONArray) jsonObject.get("pizzas");
            int i = 0;
            for (Object o : jsonArray) {
                if (i >= pizzasNumber) {
                    break;
                }
                i++;
                String pizzaName = (String) o;
                pizzas.add(new Pizza(0, pizzaName, PizzaState.WAITING));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pizzas;
    }
}
