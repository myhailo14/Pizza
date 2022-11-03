package lab.pizza.other;

public enum PizzasState {
    DOUGH(1),
    FILLING(2),
    BAKING(3),
    READY(4),
    WAITING(5);
    private int value;

    PizzasState(int value) {
        this.value = value;
    }
}
