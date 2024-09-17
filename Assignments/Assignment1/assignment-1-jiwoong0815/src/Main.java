//import assignment1.SwarmOfHornets;
//import assignment1.Hornet;
//
//public class Main {
//    public static void main(String[] args) {
//
//        SwarmOfHornets swarm = new SwarmOfHornets();
//        a(swarm.sizeOfSwarm() == 0 , "size of swarm");
//        a(swarm.getHornets().length == 0, "initial array");
//        Hornet hornet1 = new Hornet(null,1,2);
//        Hornet hornet2 = new Hornet(null,2,1999);
//        swarm.addHornet(hornet1);
//        swarm.addHornet(hornet2);
//        a(swarm.sizeOfSwarm() == 2 , "size of swarm");
//        a(swarm.getHornets().length == 2, "initial array");
//        swarm.removeHornet(hornet2);
//        a(swarm.sizeOfSwarm() == 1, "size 1");
//        a(swarm.getHornets().length == 1, "gethornet 1");
//        a(swarm.getHornets()[0] == hornet1 , "hornet1");
//        a(swarm.getFirstHornet() == hornet1, "firsthonet");
//    }
//
//    private static void a(boolean condition, String message) {
//        if (!condition) throw new AssertionError("Assertion failed: " + message);
//    }
//}
