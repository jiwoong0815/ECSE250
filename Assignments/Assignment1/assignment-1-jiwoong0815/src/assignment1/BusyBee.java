package assignment1;

public class BusyBee extends HoneyBee{

    public static int BASE_HEALTH;
    public static int BASE_COST;
    public static int BASE_AMOUNT_COLLECTED;

    public BusyBee(Tile position){
        super(position,BASE_HEALTH,BASE_COST);

    }

    @Override
    public boolean takeAction() {
        this.getPosition().storeFood(BASE_AMOUNT_COLLECTED);
        return true;
    }
}
