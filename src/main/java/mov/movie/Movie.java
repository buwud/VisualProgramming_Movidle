package mov.movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Movie
{
    //filmleri tutacak sınıf, entity,

    private int Id, year;
    private String title, genre, origin, director, star, url;

    public Movie(int Id, int year, String title,String genre, String origin,
                 String director, String star, String url)
    {
        this.Id=Id;
        this.year=year;
        this.title=title;
        this.genre=genre;
        this.origin=origin;
        this.director=director;
        this.star=star;
        this.url=url;
    }
    public String getTitle(){return title;}
    public int getYear(){return year;}
    public String getGenre(){return genre;}
    public String getOrigin(){return origin;}
    public String getDirector(){return director;}
    public String getStar(){return star;}
    public String getUrl(){return url;}


    //CSV den verileri bir diziye atacak, movie nesnelerini ve bunu return
    // eyleyecek
    public static List<Movie> getFromCSV() throws IOException
    {
        List<Movie> movies = new ArrayList<>();


        try(BufferedReader br= new BufferedReader(new FileReader("C:/Users/yeolb/Desktop/imdb_top_250.csv")))
        {
            String line;
            br.readLine();
            while((line=br.readLine() )!= null)
            {
                String[] items= line.split(";");
                int id = Integer.parseInt(items[0]);
                String title  = items[1];
                int year = Integer.parseInt(items[2]);
                String genre = items[3];
                String origin = items[4];
                String director = items[5];
                String star = items[6];
                String url = items[7];

                Movie movie = new Movie(id,year,title,genre,origin,director,
                                        star,url);
                movies.add(movie);
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        return movies;
    }
}