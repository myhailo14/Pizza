package lab.pizza.client.generator;

import lab.pizza.client.model.Client;
import lab.pizza.model.Pizza;
import lab.pizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientsGenerator {
    private final PizzaRepository pizzaRepository;
    private static final Random RANDOM_PIZZA_INDEX_GENERATOR = new Random();
    @Value("${max_number_of_pizza_types_per_client}")
    private int MAX_NUMBER_OF_PIZZA_TYPES;
    private int currentClientId;
    private int currentPizzaId;

    @Autowired
    public ClientsGenerator(final PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
        currentClientId = 0;
        currentPizzaId = 0;
    }

    public List<Client> getClients(int clientsAmount) {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < clientsAmount; i++) {
            var order = generateClientOrder();
            var client = new Client(currentClientId);
            currentClientId++;
            client.setOrder(order);
            clients.add(client);
        }
        return clients;
    }

    private List<Pizza> generateClientOrder() {
        List<Pizza> order = new LinkedList<>();
        int pizzaTypes = getRandomPizzaTypesAmount();
        for (int i = 0; i < pizzaTypes; i++) {
            final Pizza pizza = getRandomPizza();
            order.add(pizza);
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
        var pizza = pizzaRepository.getPizza(pizzaIndex);
        pizza.setId(currentPizzaId++);
        return pizza;
    }
}
