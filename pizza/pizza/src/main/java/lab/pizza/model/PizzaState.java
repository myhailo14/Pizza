package lab.pizza.model;

public enum PizzaState {
    DOUGH(0),
    FILLING(1),
    BAKING(2),
    READY(3);
    private final int value;

    PizzaState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
