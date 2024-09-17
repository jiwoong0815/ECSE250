package assignment1;

public class SwarmOfHornets {

    private Hornet[] storage;
    private int swarm_size;
    public static double QUEEN_BOOST;

    public SwarmOfHornets(){
        this.storage = new Hornet[swarm_size];
    }

    public int sizeOfSwarm(){
        int counter = 0;
        for (int i = 0 ; i < storage.length ; i++){
            if(storage[i] != null){
                counter++;
            }
        }
        return counter;
    }

    public Hornet[] getHornets(){
        Hornet[] queue = new Hornet[this.sizeOfSwarm()];
        int location = 0;
            for(int j = 0; j < storage.length; j++){
                if(storage[j] != null){
                    queue[location++] = storage[j];
                }
        }
        return queue;
    }

    public Hornet getFirstHornet(){
        if(this.sizeOfSwarm() == 0){
            return null;
        }
        return this.getHornets()[0];
    }

    public void addHornet(Hornet adding_hornet){
        if(this.storage.length == this.getHornets().length){
            Hornet[] original_queue = this.getHornets();
            this.storage = new Hornet[storage.length + 1];

            for( int j = 0; j < original_queue.length; j++){
                this.storage[j] = original_queue[j];
            }
        }

        storage[storage.length-1] = adding_hornet;
        if (adding_hornet.isTheQueen()){
            for (int i = 0; i < storage.length ; i++ ){
                storage[i].regenerateHealth(QUEEN_BOOST);
            }
        }
//        for(){
//            if(storage[storage.length-1] == null){
//                if(adding_hornet.isTheQueen()){
//                    storage[i].regenerateHealth(QUEEN_BOOST);
//                }
//                storage[i] = adding_hornet;
//                break;
//            }

    }

    public boolean removeHornet(Hornet removing_hornet){
        boolean status = false;

        for (int i = 0; i < storage.length; i++ ){
            if(storage[i] == removing_hornet){
                storage[i] = null;

                status = true;
                break;
            }
        }
        this.storage = this.getHornets();

        return status;
    }

}
