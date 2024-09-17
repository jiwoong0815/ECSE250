package assignment1;

public class Hornet extends Insect{

    private int attack_damage;
    public static int BASE_FIRE_DMG;
    private boolean isQueen = false;
    private static int queensTotal = 0;


    public Hornet(Tile position, int hp, int attack_damage){
        super(position,hp);
        this.attack_damage = attack_damage;

    }

    private Boolean queenFirstTurns = true;

    public boolean takeAction() {
        boolean returnbool = false;

        if (this.getPosition().isOnFire()) {
            this.takeDamage(BASE_FIRE_DMG);
        }
        if (this.getPosition() == null) {
            return false;
        }
        if (this.getPosition().getBee() != null) {
            this.getPosition().getBee().takeDamage(this.attack_damage);
            returnbool = true;

        }

        else if (this.getPosition().isHive() && this.getPosition().getBee() == null) {
            returnbool = false;

        }
        else if (this.getPosition().getBee() == null) {
            Tile tempLocation = this.getPosition().towardTheHive();
            this.getPosition().removeInsect(this);
            this.setPosition(tempLocation);
            tempLocation.addInsect(this);
            returnbool = true;

        }


        if(queenFirstTurns && this.isQueen){
            queenFirstTurns = false;
            takeAction();
            queenFirstTurns = true;
        }
            return returnbool;
    }
    @Override
    public boolean equals(Object object){
        if((object instanceof Hornet) &&(super.equals(object)) && (((Hornet)object).attack_damage == this.attack_damage)){
            return true;
        }
        return false;
    }

    public boolean isTheQueen(){
        return this.isQueen;
    }

    public void promote(){
        this.isQueen = true;
    }

}
