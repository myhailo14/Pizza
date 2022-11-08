package lab.pizza.repository;

import lab.pizza.model.Pizza;
import lab.pizza.util.PizzaJsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PizzaRepository {
    private final List<Pizza> pizzas;
    private final PizzaJsonReader pizzaJsonReader;

    @Autowired
    public PizzaRepository(final PizzaJsonReader pizzaJsonReader) {
        pizzas = new ArrayList<>();
        this.pizzaJsonReader = pizzaJsonReader;
    }

    public void loadPizzas(final int pizzasNumber) {
        pizzas.addAll(pizzaJsonReader.getPizzasFromJson(pizzasNumber));
    }

    public int getPizzasAmount() {
        return pizzas.size();
    }

    public Pizza getPizza(int number) {
        return pizzas.get(number);
    }
}
