package ee.bcs.valiit.myproject.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherHibernateService {

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private RestTemplate restTemplate;

    public List<Weather> getWeather(String cityName) {
        ResponseEntity<WeatherInfoResponse> response = restTemplate.getForEntity
                ("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=b8847b1a56edb3fc15c8353c8550c05e" + "&units=metric", WeatherInfoResponse.class);
        WeatherInfoResponse body = response.getBody();
        Weather entity = new Weather();
        entity.setCityName(cityName);
        entity.setTemperature(body.getMain().getTemp());
        entity.setWindSpeed(body.getWind().getSpeed());
        entity.setWindDirection(body.getWind().getDeg());
        entity.setTime(LocalDateTime.now());
        weatherRepository.save(entity);
        System.out.println(body.getMain().getTemp());
        System.out.println(body.getWind().getDeg());
        System.out.println(body.getWind().getSpeed());
        return weatherRepository.findAll();
    }

    public List<Weather> getAll() {
        return weatherRepository.findAll();
    }

    public void delAll() {
        weatherRepository.deleteAll();
    }

}






