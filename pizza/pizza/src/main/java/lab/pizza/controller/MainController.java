package lab.pizza.controller;

import lab.pizza.cook.handler.CookHandler;
import lab.pizza.dto.ConfigDto;
import lab.pizza.queue.model.ClientsQueue;
import lab.pizza.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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

    @GetMapping("/queues")
    public List<ClientsQueue> getQueues() {
        return pizzaService.getClientsQueues();
    }

    @GetMapping("/cooks")
    public List<CookHandler> getCookers() {
        return pizzaService.getCookHandlers();
    }

    @PutMapping("/stop-cook/{id}")
    public void stopCook(@PathVariable final int id) {
        pizzaService.stopCook(id);
    }

    @PutMapping("/resume-cook/{id}")
    public void resumeCook(@PathVariable final int id) {
        pizzaService.resumeCook(id);
    }

    @PostMapping("/stop")
    public void stopService() {
        pizzaService.stopService();
    }
}
