package mov.movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    final private List<Movie> movies;
    private Movie currentMovie;
    final private List<Boolean> guessResults;

    public Game() throws IOException
    {
        movies = getFromCSV();
        guessResults = new ArrayList<>();
        chooseRandomMovie();
    }

    public static List<Movie> getFromCSV() throws IOException
    {
        List<Movie> movies_= new ArrayList<>();

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
                movies_.add(movie);
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        return movies_;
    }

    private void chooseRandomMovie() {
        Random random = new Random();
        int index = random.nextInt(movies.size());
        currentMovie = movies.get(index);
    }

    public void makeGuess(String guess) {
        boolean isCorrect = guess.equalsIgnoreCase(currentMovie.getTitle());
        guessResults.add(isCorrect);
    }

    public List<Boolean> getGuessResults() {
        return guessResults;
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public boolean isGameOver() {
        return guessResults.size() >= 5;
    }
}
