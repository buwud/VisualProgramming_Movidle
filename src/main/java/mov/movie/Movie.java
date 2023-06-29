package mov.movie;

public class Movie
{
    private final int Id;
    private final int year;
    private final String title;
    private final String genre;
    private final String origin;
    private final String director;
    private final String star;
    private final String url;

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
}