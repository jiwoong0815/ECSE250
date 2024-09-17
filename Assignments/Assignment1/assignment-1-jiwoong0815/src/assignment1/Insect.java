package assignment1;

public abstract class Insect {
    private Tile position ;
    private int hp ;

    public Insect(Tile position ,int hp ) {
        this.hp = hp;
        if (!position.addInsect(this)) {
            throw new IllegalArgumentException("the Insect can not be added to this tile");
        }
    }
    public final Tile getPosition() {
        return this.position;
    }
    public final int getHealth(){
        return this.hp;
    }

    public void setPosition(Tile position){
        this.position = position;
    }

    public void takeDamage(int damage){
        this.hp -= damage;
        if (this.hp <= 0){

            position.removeInsect(this);

        }
    }

    public abstract boolean takeAction();

    @Override
    public boolean equals(Object object){
        if((object instanceof Insect) &&( ((Insect)object).getPosition() == this.getPosition() )&& (((Insect)object).getHealth() == this.hp)){
            return true;
        }
        return false;
    }

    public void regenerateHealth(double healthRegen){
        this.hp = (int) (this.hp + (this.hp * healthRegen));
    }

}
