package assignment1;

public class SniperBee extends HoneyBee{
    private int attackDamage;
    private int piercingPower;
    public static int BASE_HEALTH;
    public static int BASE_COST;

    public SniperBee(Tile position, int attackDamage, int piercingPower){
        super(position,BASE_HEALTH,BASE_COST);
        this.attackDamage = attackDamage;
        this.piercingPower = piercingPower;

    }

    private boolean aimingstatus = false;

    @Override
    public boolean takeAction() {
        if (!this.getPosition().isOnThePath()) return false;
        boolean returnbool = false;
        if (!aimingstatus) {
            aimingstatus = true;
            return false;
        }
        Tile aim = this.getPosition();
        while(aim.isNest() == false && aim.getNumOfHornets() == 0 && aim.isOnThePath()){
            aim = aim.towardTheNest();

        }

        if(aim.getNumOfHornets() == 0){
            return false;
        }

        for (int i = (Math.min(piercingPower, aim.getHornets().length))-1 ; i >= 0 ; i--){

            aim.getHornets()[i].takeDamage(attackDamage);
            returnbool = true;
        }
        aimingstatus = false;
        return returnbool;
    }

}
