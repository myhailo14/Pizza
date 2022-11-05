package lab.pizza.cook.handler;

public abstract class CookBaseHandler implements CookHandler{
    private CookHandler next;
    @Override
    public void handlePizzaPart() {
        if(next!=null){
            next.handlePizzaPart();
        }
    }

    @Override
    public void setNext(CookHandler handler) {
        next = handler;
    }
}
