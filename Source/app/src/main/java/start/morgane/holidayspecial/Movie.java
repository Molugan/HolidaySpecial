package start.morgane.holidayspecial;
import java.util.List;
import java.util.Date;
/**
 * Created by Morgane on 26/11/16.
 */

public class Movie {

    public String name;
    public String synopsis;
    public String imgID;

    public Date releaseDate;
    public Date watchDate;

    public Movie(String title){
        name = title;
        synopsis = "Unknown synopsis";
        imgID = "";
    }
}
