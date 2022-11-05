package lab.pizza.client.generator;

import lab.pizza.client.model.Client;
import lab.pizza.model.Pizza;
import lab.pizza.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
        return RANDOM_PIZZA_INDEX_GENERATOR.nextInt(MAX_PIZZA_AMOUNT + 1);
    }

    private HashMap<Pizza, Integer> generateClientOrder() {
        HashMap<Pizza, Integer> order = new HashMap<>();
        int pizzaTypes = getRandomPizzaTypesAmount();
        for (int i = 0; i < pizzaTypes; i++) {
            final Pizza pizza = getRandomPizza();
            final int pizzaAmount = getRandomPizzaAmount();
            order.put(pizza, pizzaAmount);
        }
        return order;
    }

    private int getRandomPizzaTypesAmount() {
        return RANDOM_PIZZA_INDEX_GENERATOR.nextInt(MAX_NUMBER_OF_PIZZA_TYPES + 1);
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
