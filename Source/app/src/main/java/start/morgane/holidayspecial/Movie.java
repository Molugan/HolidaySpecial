package start.morgane.holidayspecial;
import java.util.List;

/**
 * Created by Morgane on 26/11/16.
 */

public class Movie {

    public String name;
    public String synopsis;
    public String imgID;


    public Movie(String title){
        name = title;
        synopsis = "Unknown synopsis";
        imgID = "";
    }
}
