public class main {

    public static void main(String args[])
    {

        Pizzeria pizzeria=new Pizzeria(new MakePizzaFactory());
        Pizza pizza=pizzeria.orderPizza(PizzaType.PEPPERONI);
        pizza.makePizza();


    }
    public enum PizzaType
    {
        PEPPERONI,
        PINEAPPLE_PIZZA
    }
}
