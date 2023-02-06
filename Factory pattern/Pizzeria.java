public class Pizzeria {

    private final MakePizzaFactory makePizzaFactory;

    public Pizzeria(MakePizzaFactory makePizzaFactory) {
        this.makePizzaFactory = makePizzaFactory;
    }

    public Pizza  orderPizza(main.PizzaType type)
    {
        Pizza pizza =makePizzaFactory.makePizza(type);

        return pizza;

    }
}
