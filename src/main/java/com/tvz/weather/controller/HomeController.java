package com.tvz.weather.controller;

import java.net.URL;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tvz.weather.api.WeatherBitApi;

@RestController
@RequestMapping("/main")
public class HomeController {
	

    @CrossOrigin()
    @RequestMapping(value = "/processForm", method=RequestMethod.GET)
    public String processForm(@RequestParam("name") String name, Model theModel) {

    	String name2 = name.replaceAll("\\s", "+");
        // create object mapper
        ObjectMapper mapper = new ObjectMapper();

        try {
            WeatherBitApi currentWeather = mapper
                    .readValue(
                            new URL("http://api.openweathermap.org/data/2.5/weather?q="+name2
                                    + "&units=metric&lang=hr&appid=c739e35da2a135760efb82c804b0ca6e"),
                            WeatherBitApi.class);

            java.util.Date sunriseTime = new java.util.Date((long) currentWeather.getSys().getSunrise() * 1000);
            java.util.Date sunsetTime = new java.util.Date((long) currentWeather.getSys().getSunset() * 1000);

            theModel.addAttribute("currentWeather", currentWeather);
            theModel.addAttribute("sunriseTime", sunriseTime);
            theModel.addAttribute("sunsetTime", sunsetTime);

        } catch (Exception e) {
            theModel.addAttribute("Error", "Nepoznata lokacija molimo Vas pružite točnu lokaciju");
        }

        
        theModel.addAttribute("Weather", new WeatherBitApi());

        Gson g = new Gson();
        String json = g.toJson(theModel);
        
        return json;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
