package lab.pizza.repository;

import lab.pizza.model.Pizza;
import lab.pizza.model.PizzaState;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PizzaRepository {
    private final List<Pizza> pizzas;

    public PizzaRepository() {
        pizzas = new ArrayList<>();
        pizzas.add(new Pizza("margaryta", PizzaState.WAITING));
        pizzas.add(new Pizza("hawaii", PizzaState.WAITING));
        pizzas.add(new Pizza("meat", PizzaState.WAITING));
        pizzas.add(new Pizza("cheese", PizzaState.WAITING));
    }
    public int getPizzasAmount(){
        return pizzas.size();
    }
    public Pizza getPizza(int number) {
        return pizzas.get(number);
    }
}
