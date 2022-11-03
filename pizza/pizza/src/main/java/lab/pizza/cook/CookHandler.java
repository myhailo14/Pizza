package lab.pizza.cook;

public interface CookHandler {
    void handlePizzaPart();
    void setNext(CookHandler handler);
}
