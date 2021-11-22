package com.formacion.back.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacion.back.entities.Movie1;
import com.formacion.back.repository.MovieRepository;
@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	public Movie1 movie(String movieName) {
		return movieRepository.movie(movieName);
	}
	
	public String remove(int id) {
		if (movieRepository.findById(id).isEmpty())
			return "There is no movies with that id: " + id;
		Movie1 m = movieRepository.findById(id).get();
		movieRepository.deleteById(id);
		
		Date purchaseDate = new Date();
		DateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    outFormat.setTimeZone(TimeZone.getTimeZone("Europe/Lithuania"));
	    
		return m.getMovieName() + " Removed" + "\nDate:" + outFormat.format(purchaseDate);
	}
	
	public String modify(int id, String movieName, String directorName, int premiere_date) {
		if (movieRepository.findById(id).isEmpty())
			return "There is no movies with that id: " + id;
		
		Movie1 m = movieRepository.findById(id).get();
		String name = m.getMovieName();
		m.setMovieName(movieName);
	    m.setDirectorName(directorName);
		m.setPremiere_date(premiere_date);

		movieRepository.save(m);
		
		Date purchaseDate = new Date();
		DateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    outFormat.setTimeZone(TimeZone.getTimeZone("Europe/Lithuania"));
		return name + " Modified" + "\nDate: " + outFormat.format(purchaseDate);
	}
}