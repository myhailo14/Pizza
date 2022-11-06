package lab.pizza.client.generator;

import lab.pizza.client.model.Client;
import lab.pizza.model.Pizza;
import lab.pizza.model.PizzaState;
import lab.pizza.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientsGenerator {
    private final PizzaRepository pizzaRepository;
    private static final Random RANDOM_PIZZA_INDEX_GENERATOR = new Random();
    @Value("${max_number_of_pizza_types_per_client}")
    private int MAX_NUMBER_OF_PIZZA_TYPES;
    @Value("${max_pizza_amount_per_client}")
    private int MAX_PIZZA_AMOUNT;

    public List<Client> getClients(int clientsAmount) {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < clientsAmount; i++) {
            var order = generateClientOrder();
            var client = new Client();
            client.setOrder(order);
            clients.add(client);
        }
        return clients;
    }

    private int getRandomPizzaAmount() {
        int pizzaAmount = RANDOM_PIZZA_INDEX_GENERATOR.nextInt(MAX_PIZZA_AMOUNT + 1);
        while(pizzaAmount == 0){
            pizzaAmount = RANDOM_PIZZA_INDEX_GENERATOR.nextInt(MAX_PIZZA_AMOUNT + 1);
        }
        return pizzaAmount;
    }

    private List<Pizza> generateClientOrder() {
        List<Pizza> order = new LinkedList<>();
        int pizzaTypes = getRandomPizzaTypesAmount();
        for (int i = 0; i < pizzaTypes; i++) {
            final Pizza pizza = getRandomPizza();
            final int pizzaAmount = getRandomPizzaAmount();
            for (int k = 0; k < pizzaAmount; k++) {
                order.add(new Pizza(pizza.getName(), PizzaState.WAITING));
            }
        }
        return order;
    }

    private int getRandomPizzaTypesAmount() {
        int value = RANDOM_PIZZA_INDEX_GENERATOR.nextInt(MAX_NUMBER_OF_PIZZA_TYPES + 1);
        while (value == 0) {
            value = RANDOM_PIZZA_INDEX_GENERATOR.nextInt(MAX_NUMBER_OF_PIZZA_TYPES + 1);
        }
        return value;
    }

    private int getRandomPizzaIndex() {
        int pizzasAmount = pizzaRepository.getPizzasAmount();
        return RANDOM_PIZZA_INDEX_GENERATOR.nextInt(pizzasAmount);
    }

    private Pizza getRandomPizza() {
        int pizzaIndex = getRandomPizzaIndex();
        return pizzaRepository.getPizza(pizzaIndex);
    }
}
