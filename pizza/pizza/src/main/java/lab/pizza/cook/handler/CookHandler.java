package lab.pizza.cook.handler;

public interface CookHandler {
    void handlePizzaPart();
    void setNext(CookHandler handler);
}
