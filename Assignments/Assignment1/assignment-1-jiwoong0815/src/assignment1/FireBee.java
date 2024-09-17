package assignment1;

public class FireBee extends HoneyBee{
    private int attackRange;
    public static int BASE_HEALTH;
    public static int BASE_COST;

    public FireBee(Tile position, int attackRange){
        super(position,BASE_HEALTH,BASE_COST);
        this.attackRange = attackRange;

    }

    @Override
    public boolean takeAction() {
        if(!this.getPosition().isOnThePath() || this.getPosition().isNest()) return false;
        Tile prev = this.getPosition().towardTheNest();
        for (int i=0 ; i < attackRange ; i++){
            if(prev == null) return false;
            if(prev.getHornets().length != 0 && !prev.isOnFire() && !prev.isNest()){
                prev.setOnFire();
                return true;
            }
            prev = prev.towardTheNest();
        }
        return false;
    }


}
