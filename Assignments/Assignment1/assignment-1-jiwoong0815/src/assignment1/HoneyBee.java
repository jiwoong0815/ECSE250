package assignment1;

public abstract class HoneyBee extends Insect {

    private int cost;
    public static double HIVE_DMG_REDUCTION;

    public HoneyBee(Tile position, int hp, int cost){
        super(position,hp);
        this.cost = cost;
    }

    public int getCost(){
        return this.cost;
    }

    @Override
    public void takeDamage(int damage) {
        if (this.getPosition().isHive()){
            super.takeDamage((int)(damage-(damage*HIVE_DMG_REDUCTION)));
        }
        else {super.takeDamage(damage);}
    }
}
