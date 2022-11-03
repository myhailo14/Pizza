package lab.pizza.cook;

public class CookAloneWorkingStrategy implements CookWorkingStrategy{

    @Override
    public CookHandler getCookHandler() {
        return new CookAloneHandler();
    }
}
