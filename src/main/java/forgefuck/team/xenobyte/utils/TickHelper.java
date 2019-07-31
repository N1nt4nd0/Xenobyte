package forgefuck.team.xenobyte.utils;

public class TickHelper {
    
    public static int fromSeconds(int secs) {
        return secs * ticksInSeconds();
    }
    
    public static int ticksInSeconds() {
        return 40;
    }
    
    public static int oneSecond() {
        return fromSeconds(1);
    }
    
    public static int twoSeconds() {
        return fromSeconds(2);
    }
    
    public static int threeSeconds() {
        return fromSeconds(3);
    }
    
    public static int fourSeconds() {
        return fromSeconds(4);
    }
    
    public static int fiveSeconds() {
        return fromSeconds(5);
    }

}
