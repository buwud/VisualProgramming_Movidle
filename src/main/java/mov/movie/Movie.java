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
    private Movie currMovie;
    public List<Movie> movies;

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
    public Movie getCurrMovie(){return currMovie;}

}