package lab.pizza.model;

public enum PizzaState {
    WAITING(0),
    DOUGH(1),
    FILLING(2),
    BAKING(3),
    READY(4);
    private final int value;

    PizzaState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
