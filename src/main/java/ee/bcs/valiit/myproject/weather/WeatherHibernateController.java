package ee.bcs.valiit.myproject.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class WeatherHibernateController {

    @Autowired
    private WeatherHibernateService weatherService;

    //http://localhost:9090/weather/selectCity?cityName=Tallinn
    @CrossOrigin
    @GetMapping("weather/selectCity")
    public List<Weather> selectCity(@RequestParam("cityName") String cityName) {
        return weatherService.getWeather(cityName);

    }

    //http://localhost:9090/weather/all
    @CrossOrigin
    @GetMapping("weather/all")
    public List<Weather> allWeather() {
        return weatherService.getAll();
    }

    @CrossOrigin
    @DeleteMapping("weather/delete")
    public void delWeather() {
        weatherService.delAll();
    }
}
//TODO 1 REST API kaudu saab sisestada linna nime mille hetke ilmaennustust soovitakse näha (temperatuur, tuulekiirus ja tuulesuund)

//TODO 2 Backend teeb open weather apile päringu ja kuvab tulemuse üle REST API välja ning salvestab ka andmebaasi

//TODO 3 Eraldi REST API endpoint mille kaudu näeb kõiki päringu tulemusi mis on varasemalt küsitud

//TODO 4 Eraldi REST API endpoint mille kaudu saab kustutada andmebaasi kogunenud info

//TODO 5 Kui sisestatakse mõni linnanimi mida ei ole olemas siis REST API peab seda ka ütlema




