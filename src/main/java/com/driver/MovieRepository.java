package com.driver;


import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class MovieRepository {

    HashMap<String,Movie> movieHashMap;
    HashMap<String, Director> directorHashMap;
    HashMap<String, List<String>> movieDirectorHashMap;

    public MovieRepository() {
        this.movieHashMap = new HashMap<String, Movie>();
        this.directorHashMap = new HashMap<String, Director>();
        this.movieDirectorHashMap = new HashMap<String, List<String>>();
    }

    public void addMovie(Movie movie){
        String movieName = movie.getName();
        movieHashMap.put(movieName,movie);
    }

    public void addDirector(Director director){
        String directorName = director.getName();
        directorHashMap.put(directorName,director);
    }

    public void addMovieDirectorPair(String movieName, String directorName){
        List<String> movieList = new ArrayList<>();
        if(movieDirectorHashMap.containsKey(directorName)){
            movieList = movieDirectorHashMap.get(directorName);
            movieList.add(movieName);
            movieDirectorHashMap.put(directorName,movieList);
        }
        else{
            movieList.add(movieName);
            movieDirectorHashMap.put(directorName,movieList);
        }
    }

    public Movie getMoviesByName(String movieName){
        return movieHashMap.get(movieName);

    }

    public Director getDirectorByName(String directorName){
        return directorHashMap.get(directorName);
    }

    public List<String> getMoviesByDirectorName(String director){
        List<String> movieList = new ArrayList<>();
        if(movieDirectorHashMap.containsKey(director))
            movieList = movieDirectorHashMap.get(director);

        return movieList;

    }

    public List<String> findAllMovies(){
        return new ArrayList<>(movieHashMap.keySet());
    }

    public void deleteDirectorByName(String director){
        List<String> allMovies = new ArrayList<>();

        //find all movies and save allMovies list
        if(movieDirectorHashMap.containsKey(director))
            allMovies = movieDirectorHashMap.get(director);

        //delete all movies from movieHashMap
        for(String  movie : allMovies){
            movieHashMap.remove(movie);
        }

        //also deleting the director pair
        movieDirectorHashMap.remove(director);

        //delete the director from directorHashMap
        directorHashMap.remove(director);

    }

    public void deleteAllDirectors(){

        HashSet<String> movieSet = new HashSet<>();

        directorHashMap = new HashMap<>();

        for(String director : movieDirectorHashMap.keySet()){
            movieSet.addAll(movieDirectorHashMap.get(director));
        }

        for(String movie : movieSet){
            movieHashMap.remove(movie);
        }

        movieDirectorHashMap = new HashMap<>();
    }
}
