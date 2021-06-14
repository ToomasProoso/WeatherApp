package ee.bcs.valiit.myproject.weather;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {WeatherHibernateService.class, RestTemplate.class})
@ExtendWith(SpringExtension.class)
public class WeatherHibernateServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private WeatherHibernateService weatherHibernateService;

    @MockBean
    private WeatherRepository weatherRepository;

    @Test
    public void testGetWeather() throws RestClientException {
        Weather weather = new Weather();
        weather.setWeatherId(123);
        weather.setTime(LocalDateTime.of(1, 1, 1, 1, 1));
        weather.setCityName("Oxford");
        weather.setWindSpeed(10.0);
        weather.setTemperature(10.0);
        weather.setWindDirection(10.0);
        ArrayList<Weather> weatherList = new ArrayList<Weather>();
        when(this.weatherRepository.findAll()).thenReturn(weatherList);
        when(this.weatherRepository.save((Weather) any())).thenReturn(weather);
        WeatherInfoResponse weatherInfoResponse = mock(WeatherInfoResponse.class);
        when(weatherInfoResponse.getWind()).thenReturn(new WeatherInfoResponse.Wind());
        when(weatherInfoResponse.getMain()).thenReturn(new WeatherInfoResponse.Main());
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(weatherInfoResponse, HttpStatus.CONTINUE);

        when(this.restTemplate.getForEntity(anyString(), (Class<Object>) any(), (Object[]) any()))
                .thenReturn(responseEntity);
        List<Weather> actualWeather = this.weatherHibernateService.getWeather("Oxford");
        assertSame(weatherList, actualWeather);
        assertTrue(actualWeather.isEmpty());
        verify(this.weatherRepository).findAll();
        verify(this.weatherRepository).save((Weather) any());
        verify(this.restTemplate).getForEntity(anyString(), (Class<Object>) any(), (Object[]) any());
        verify(weatherInfoResponse, times(2)).getMain();
        verify(weatherInfoResponse, times(4)).getWind();
        assertSame(actualWeather, this.weatherHibernateService.getAll());
    }

    @Test
    public void testGetAll() {
        ArrayList<Weather> weatherList = new ArrayList<Weather>();
        when(this.weatherRepository.findAll()).thenReturn(weatherList);
        List<Weather> actualAll = this.weatherHibernateService.getAll();
        assertSame(weatherList, actualAll);
        assertTrue(actualAll.isEmpty());
        verify(this.weatherRepository).findAll();
    }

    @Test
    public void testDelAll() {
        doNothing().when(this.weatherRepository).deleteAll();
        this.weatherHibernateService.delAll();
        verify(this.weatherRepository).deleteAll();
        assertTrue(this.weatherHibernateService.getAll().isEmpty());
    }
}

