package lab.pizza.client.generator;


import lab.pizza.client.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(scopeName = "prototype")
public class DefaultClientsGenerationStrategy implements ClientsGenerationStrategy{
    private final ClientsGenerator clientsGenerator;
    @Value("${default_clients_number}")
    private int clientsDefaultNumber;

    @Autowired
    public DefaultClientsGenerationStrategy(ClientsGenerator clientsGenerator) {
        this.clientsGenerator = clientsGenerator;
    }

    @Override
    public List<Client> generateClients() {
        return clientsGenerator.getClients(clientsDefaultNumber);
    }
}
