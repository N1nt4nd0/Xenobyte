package forgefuck.team.xenobyte.utils;

public class Delimitter {
    
    private int value, iterate, min, max;
    
    public Delimitter(int min, int max) {
        this(min, min, max);
    }
    
    public Delimitter(int value, int min, int max) {
        this.iterate = value;
        this.value = value;
        this.max = max;
        this.min = min;
    }
    
    public int getMin() {
        return min;
    }
    
    public int getMax() {
        return max;
    }
    
    public void reset() {
        iterate = value;
    }
    
    public int calc(int step) {
        iterate += step;
        return iterate = iterate > max ? max : iterate < min ? min : iterate;
    }
    
}