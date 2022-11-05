package lab.pizza.controller;

import lab.pizza.dto.ConfigDto;
import lab.pizza.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final PizzaService pizzaService;

    @PostMapping("/config")
    public void createConfig(@RequestBody final ConfigDto configDto) {
        pizzaService.initConfigService(configDto);
    }

    @PostMapping("/start")
    public void startService() {
        pizzaService.startService();
    }
}
