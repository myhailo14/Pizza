package lab.pizza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lab.pizza.config.*;

@Service
public class PizzaService {
    private final ConfigService configService;
    public PizzaService(){
        this.configService = new ConfigService();
    }

}
