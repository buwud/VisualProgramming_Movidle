package mov.movie;

import java.io.Console;
import java.io.IOException;
import java.util.List;

public class Controller
{
    public static void main(String[] args) throws IOException
    {
        List<Movie> movies1 = Movie.getFromCSV();
        System.out.println(movies1.get(29).getTitle());
    }
}
