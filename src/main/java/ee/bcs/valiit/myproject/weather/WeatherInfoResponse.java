package ee.bcs.valiit.myproject.weather;

public class WeatherInfoResponse {
    private Main main;
    private Wind wind;

    public static class Main {
        private Double temp;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }
    }

    public static class Wind {
        private Double speed;
        private Double deg;

        public Double getSpeed() {
            return speed;
        }

        public void setSpeed(Double speed) {
            this.speed = speed;
        }

        public Double getDeg() {
            return deg;
        }

        public void setDeg(Double deg) {
            this.deg = deg;
        }
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
