package forgefuck.team.xenobyte.utils;

public class TickHelper {
     
    public static final int ONE_SEC = fromSeconds(1);
    public static final int TWO_SEC = fromSeconds(2);
    public static final int THREE_SEC = fromSeconds(3);
    public static final int FOUR_SEC = fromSeconds(4);
    public static final int FIVE_SEC = fromSeconds(5);
    public static final int SIX_SEC = fromSeconds(6);
    
    public static int fromSeconds(int secs) {
        return secs * ticksInSeconds();
    }
    
    public static int ticksInSeconds() {
        return 40;
    }

}
