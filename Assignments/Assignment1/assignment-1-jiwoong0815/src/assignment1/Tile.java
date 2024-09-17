package assignment1;

public class Tile {

    private int food;
    private boolean isHiveBuilt;
    private boolean isNestBuilt;
    private boolean isPathBuilt;
    private Tile nextTileToBee;
    private Tile nextTileToHornet;
    private HoneyBee bee;
    private SwarmOfHornets hornets;
    private boolean isOnFire = false;

    public Tile(){
        this.food = 0;
        this.isHiveBuilt = false;
        this.isNestBuilt = false;
        this.isPathBuilt = false;
        this.nextTileToBee = null;
        this.nextTileToHornet = null;
        this.bee = null;
        this.hornets = new SwarmOfHornets();
    }
    public Tile(int Food,boolean isHiveBuilt,boolean isNestBuilt,boolean isPathBuilt,Tile nextTileToBee,Tile nextTileToHornet,HoneyBee bee,SwarmOfHornets hornets){
        this.food = Food;
        this.isHiveBuilt = isHiveBuilt;
        this.isNestBuilt = isNestBuilt;
        this.isPathBuilt = isPathBuilt;
        this.nextTileToBee = nextTileToBee;
        this.nextTileToHornet = nextTileToHornet;
        this.bee = bee;
        this.hornets = hornets;
    }
    public boolean isHive(){
        return this.isHiveBuilt;
    }
    public boolean isNest(){
        return this.isNestBuilt;
    }
    public void buildHive(){
        this.isHiveBuilt = true;
    }
    public void buildNest(){
        this.isNestBuilt = true;
    }
    public boolean isOnThePath(){
        return this.isPathBuilt;
    }

    public Tile towardTheHive(){
        return (this.isPathBuilt == false || this.isHiveBuilt == true)? null : this.nextTileToBee;
    }

    public Tile towardTheNest(){
        return (this.isPathBuilt == false || this.isNestBuilt == true)? null : this.nextTileToHornet;
    }

    public void createPath(Tile tile1 , Tile tile2){
        if ((tile1 == null && !this.isHive()) || (tile2 == null && !this.isNest())){
            throw new IllegalArgumentException("the null can be only inputted when the next tile is the hive or nest");
        }

        this.isPathBuilt = true;
        if (tile1 != null) tile1.isPathBuilt = true;
        if (tile2 != null) tile2.isPathBuilt = true;
        this.nextTileToBee = tile1;
        this.nextTileToHornet = tile2;

    }

    public int collectFood(){
        int foodAmount = this.food;
        this.food = 0;
        return foodAmount;
    }

    public void storeFood(int foodReceived){
        this.food += foodReceived;
    }

    public int getNumOfHornets(){
        return this.hornets.getHornets().length;
    }

    public HoneyBee getBee(){
        return this.bee;
    }

    public Hornet getHornet() {
        return this.hornets.getFirstHornet();
    }

    public Hornet[] getHornets() {
        return hornets.getHornets();
    }

    public boolean addInsect(Insect addingInsect){
        boolean returnbool = false;

         if(addingInsect instanceof HoneyBee){
             if(!this.isNestBuilt && this.bee == null){
                 addingInsect.setPosition(this);
                 this.bee = (HoneyBee) addingInsect;
                 returnbool = true;

             }
         }
         else if(addingInsect instanceof Hornet) {
             if(this.isPathBuilt || (this.isNestBuilt && this.isPathBuilt) || this.isPathBuilt && this.isHiveBuilt){
                 addingInsect.setPosition(this);
                 this.hornets.addHornet((Hornet)addingInsect);
                 returnbool = true;
             }
         }
         return returnbool;


    }

    public boolean removeInsect(Insect removinginsect){
        boolean returnbool = false;

        if(removinginsect instanceof HoneyBee){
            if(this.bee != null) {
                removinginsect.setPosition(null);
                this.bee = null;
                returnbool = true;
            }
        }
        else if(removinginsect instanceof Hornet) {
            if(this.hornets.getHornets().length != 0){
                removinginsect.setPosition(null);
                this.hornets.removeHornet((Hornet) removinginsect);
                returnbool = true;
            }
        }
        return returnbool;

    }

    public void setOnFire(){
        this.isOnFire = true;
    }

    public boolean isOnFire(){
        return this.isOnFire;
    }
}
