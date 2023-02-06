public class MakePizzaFactory {

    public Pizza makePizza(main.PizzaType type)
    {
        Pizza pizza=null;

        switch (type)
        {
            case PEPPERONI :
            {
                    pizza=new Pepperoni();
            }
            case PINEAPPLE_PIZZA:
            {
                pizza=new Pepperoni();
            }
        }


        return pizza;

    }



}

