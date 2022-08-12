package com.khoahung.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.khoahung.model.Account;
import com.khoahung.model.Movies;

@Controller
public class LoginController {

	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("welcomePage");
		return model;
	}

	@RequestMapping(value = { "/homePage"}, method = RequestMethod.GET)
	public ModelAndView homePage() {
		ModelAndView model = new ModelAndView();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username="";
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		model.addObject("movies", movieRender());
		model.addObject("username", username);
		model.setViewName("homePage");
		return model;
	}
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
	@RequestParam(value = "logout",	required = false) String logout) {
		
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid Credentials provided.");
			model.setViewName("register");
			return model;
		}

		if (logout != null) {
			model.addObject("message", "Logged out from Remitano successfully.");
		}
		model.addObject("movies", movieRender());
		model.setViewName("loginPage");
		return model;
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView loginPage(Account acc) {
		String username = acc.getUsername();
		String password = acc.getPassword();
		ModelAndView model = new ModelAndView();	
			try {
			      File myObj = new File("c:/data/account.txt");
			      myObj.getParentFile().mkdirs();
			      if (myObj.createNewFile()) {
			    	  FileWriter fw = new FileWriter("c:/data/account.txt",true); //the true will append the new data
			    	  fw.write(username+";"+password+"\n");//appends the string to the file
			    	  fw.close();
			      } else {
			    	  FileWriter fw = new FileWriter("c:/data/account.txt",true); //the true will append the new data
			    	  fw.write(username+";"+password+"\n");//appends the string to the file
			    	  fw.close();
			      }
			      model.addObject("message","User create successfully please login");
			} catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			}
		
		model.addObject("movies", movieRender());
		model.setViewName("loginPage");
		return model;
	}
	
	@RequestMapping(value = { "/sharePage"}, method = RequestMethod.GET)
	public ModelAndView sharePage() {
		ModelAndView model = new ModelAndView();
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username="";
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		model.addObject("username", username);
		model.addObject("movies", movieRender());
		model.setViewName("sharePage");
		return model;
	}
	@RequestMapping(value = { "/sharePage"}, method = RequestMethod.POST)
	public ModelAndView sharePagePost(Movies movies) {
		ModelAndView model = new ModelAndView();
		String[] dataMovie=null;
		if(movies.getData()!=null && !movies.getData().isEmpty()) {
			dataMovie = movies.getData().trim().split("=");
		}
		if(dataMovie[1]==null || dataMovie[1].trim().isEmpty()){
			model.addObject("movies", movieRender());
			model.setViewName("sharePage");
			return model;
		}
		String movieURL = "https://www.youtube.com/embed/"+dataMovie[1];
		try {
		      File myObj = new File("c:/data/data.txt");
		      myObj.getParentFile().mkdirs();
		      if (myObj.createNewFile()) {
		    	  FileWriter fw = new FileWriter("c:/data/data.txt",true); //the true will append the new data
		    	  fw.write(movieURL+"\n");//appends the string to the file
		    	  fw.close();
		      } else {
		    	  FileWriter fw = new FileWriter("c:/data/data.txt",true); //the true will append the new data
		    	  fw.write(movieURL+"\n");//appends the string to the file
		    	  fw.close();
		      }
		} catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		model.addObject("movies", movieRender());		
		model.setViewName("sharePage");
		return model;
	}
	public List<Movies> movieRender(){
		List<Movies> listMovies = new ArrayList<Movies>();
		try {
		      File myObj = new File("c:/data/data.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        String[] movie = data.split("\\r?\\n");
		       
		        for(String url:movie) {
		        	Movies m = new Movies();
		        	m.setData(url);
		        	listMovies.add(m);
		        }
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return listMovies;
	}
}
