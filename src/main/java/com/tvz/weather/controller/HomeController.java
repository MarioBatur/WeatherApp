package com.tvz.weather.controller;

import java.net.URL;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvz.weather.api.WeatherBitApi;

@Controller
@RequestMapping("/main")
public class HomeController {

    @GetMapping("/home")
    public String weatherPage(Model theModel) {

        theModel.addAttribute("Weather", new WeatherBitApi());

        return "home-page";
    }

    @GetMapping(value = "/processForm")
    public String processForm(@RequestParam("name") String name, Model theModel) {

        // create object mapper
        ObjectMapper mapper = new ObjectMapper();

        try {
            WeatherBitApi currentWeather = mapper
                    .readValue(
                            new URL("http://api.openweathermap.org/data/2.5/weather?q=" + name
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

        return "home-page";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
