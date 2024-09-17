package assignment1;

public class AngryBee extends HoneyBee{

    private int attack_damage;
    public static int BASE_HEALTH;
    public static int BASE_COST;

    public AngryBee(Tile position, int attack_damage){
        super(position,BASE_HEALTH,BASE_COST);
        this.attack_damage = attack_damage;
    }

    @Override
    public boolean takeAction() {
        if (this.getPosition().isOnThePath()){
            if(this.getPosition().getHornets().length > 0 && !this.getPosition().isNest()){
                this.getPosition().getHornet().takeDamage(attack_damage);
            }
            else if (this.getPosition().getHornets().length > 0 && !this.getPosition().towardTheNest().isNest()) {
                this.getPosition().getHornet().takeDamage(attack_damage);

            }
            return true;
        }
        else {
            return false;
        }
    }
}
