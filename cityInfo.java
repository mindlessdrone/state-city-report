/**
 * Created by Shelly on 2017/3/10.
 */
public class cityInfo {
    //local constants
    //local variables
    int cityPop;
    String cityName;

    public cityInfo(String name, int pop){
        cityPop  = pop;
        cityName = name;
    }

    public int getPop() {
        return cityPop;
    }

    public String getName() {
        return cityName;
    }

    public String toString() {
        return cityName + " " + cityPop;
    }
}
