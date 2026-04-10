package com.atmos.weather_service.service;

import com.atmos.weather_service.exception.ExternalServiceException;
import com.atmos.weather_service.exception.InvalidException;
import com.atmos.weather_service.exception.NotFoundException;
import com.atmos.weather_service.model.AccuObject.*;
import com.atmos.weather_service.service.ForecaService;
import com.atmos.weather_service.exception.*;
import com.atmos.weather_service.model.ForecaObject.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccuService {

    @Value("${weather.api2.key}")
    private String accuApiKey;


    //Inject Fields
    private final ForecaService forecaService;
    private final RestTemplate restTemplate;

    //Inject by Constructure
    public AccuService(ForecaService forecaService, RestTemplate restTemplate) {
        this.forecaService = forecaService;
        this.restTemplate = restTemplate;

    }

    // Slf4j Logger to log the info
    private static final Logger log = LoggerFactory.getLogger(AccuService.class);


    //All important URL for Data fetching
    private static final String ACCUWEATHER_LOCATION_KEY = "https://dataservice.accuweather.com/locations/v1/cities/search?q=%s&apikey=%s";
    private static final String ACCUWEATHER_FORECAST_HOURLY = "https://dataservice.accuweather.com/forecasts/v1/hourly/12hour/%s?metric=true&apikey=%s&details=true";
    private static final String ACCUWEATHER_FORECAST_5_DAY_DAILY = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/%s?apikey=%s&metric=true&details=true";
    private static final String ACCUWEATHER_TOP50_CITIES = "https://dataservice.accuweather.com/currentconditions/v1/topcities/50?apikey=%s";
    private static final String ACCUWEATHER_CURRENT_CONDITION = "https://dataservice.accuweather.com/currentconditions/v1/%s?apikey=%s&metric=true&details=true";
    private static final String ACCUWEATHER_HISTORIC_24_PAST = "https://dataservice.accuweather.com/currentconditions/v1/%s/historical/24?apikey=%s&details=true";


    //Validate a City
    public void validateCity(String city) {
        if (city == null || city.isBlank()) {
            log.warn("City not to be Valid");
            throw new InvalidException("City not be Valid");
        }
    }

    //get City by LocationKey
    public String getCityLocationKey(String city) {
        validateCity(city);
        return fetchLocationKeyResponse(city).getLocationKey();
    }


    //Get Location Information by City
    public AccuLocationKeyDTO getLocationInfoByCity(String city) {
        validateCity(city);
        AccuLocationKeyResponse locationKeyResponse = fetchLocationKeyResponse(city);
        AccuLocationKeyDTO accuLocationKeyDTO = new AccuLocationKeyDTO();

        accuLocationKeyDTO.setCity(locationKeyResponse.getCityName());
        accuLocationKeyDTO.setCountry(locationKeyResponse.getCountry().getCountryName());

        if (locationKeyResponse.getGeoPosition() != null) {
            accuLocationKeyDTO.setLatitude(locationKeyResponse.getGeoPosition().getLatitude());
            accuLocationKeyDTO.setLongitude(locationKeyResponse.getGeoPosition().getLongitude());
        }
        if (locationKeyResponse.getTimeZone() != null) {
            accuLocationKeyDTO.setTimeZone(locationKeyResponse.getTimeZone().getTimeZoneName());
        }
        return accuLocationKeyDTO;
    }


    //Get Latitude and Longitude info by City
    public Map<String, Double> getLatandLongByCity(String city) {
        Map<String, Double> map = new HashMap<>();
        validateCity(city);
        AccuLocationKeyResponse locationKeyResponse = fetchLocationKeyResponse(city);
        if (locationKeyResponse.getGeoPosition() == null) {
            log.warn("GeoPosition is null");
            throw new NotFoundException("GeoPosition is null");
        }
        map.put("latitude", locationKeyResponse.getGeoPosition().getLatitude());
        map.put("longitude", locationKeyResponse.getGeoPosition().getLongitude());
        return map;

    }


    //LocationKey Fetching Response a part of LocationKey Response

    public AccuLocationKeyResponse fetchLocationKeyResponse(String city) {
        validateCity(city);
        try {

            String locationkeyUrl = String.format(ACCUWEATHER_LOCATION_KEY, city, accuApiKey);
            AccuLocationKeyResponse[] locationkeyResponse = restTemplate.getForObject(locationkeyUrl, AccuLocationKeyResponse[].class);
            if (locationkeyResponse == null || locationkeyResponse.length == 0) {

                log.warn("No location key found");
                throw new NotFoundException("LocationKey is not Founded for" + city);
            }
            return locationkeyResponse[0];

        } catch (HttpMessageConversionException e) {
            log.error("AccuWeather response deserialization failed ", e);
            throw new ExternalServiceException("AccuWeather response Deserialization Failed", e);
        } catch (RestClientException e) {
            log.error("Failed to fetch the data from the AccuWeather API", e);
            throw new ExternalServiceException("Failed to fetch the data from the AccuWeather API", e);
        }

    }


    //Get Current Weather
    public AccuCurrentDTO getCurrentWeather(String city) {

        validateCity(city);
        String locationKey = getCityLocationKey(city);
        try {

            String currentURL = String.format(ACCUWEATHER_CURRENT_CONDITION, locationKey, accuApiKey);
            AccuCurrentResponse[] responseArray = restTemplate.getForObject(currentURL,
                    AccuCurrentResponse[].class);

            if (responseArray == null || responseArray.length == 0) {
                log.warn("No Current AccuWeather Data Found ");
                throw new NotFoundException("Current AccuWeather Data Not Found for city: " + city);
            }
            AccuCurrentResponse current = responseArray[0];
            return mapToCurrentWeather(current);

        } catch (HttpMessageConversionException e) {

            log.error("Deserialization of Data for AccuWeather Current Response failed", e);
            throw new ExternalServiceException("Deserialization for AccuWeather Current Response failed", e.getCause());

        } catch (RestClientException e) {
            log.error("API Data fetching Failed for the AccuWeather Current Endpoint", e);
            throw new ExternalServiceException("API Data fetching Failed for the AccuWeather Current Endpoint", e.getCause());
        }
    }


    //Fetch the Daily Forecast Response
    public AccuForecastDailyResponse fetchForecastDailyResponse(String city) {
        validateCity(city);
        String locationKey = getCityLocationKey(city);
        try {

            String finalUrl = String.format(ACCUWEATHER_FORECAST_5_DAY_DAILY, locationKey, accuApiKey);
            AccuForecastDailyResponse forecastDailyResponse = restTemplate.getForObject(finalUrl,
                    AccuForecastDailyResponse.class);
            if (forecastDailyResponse == null) {
                log.warn("No Forecast Daily Data Found ");
                throw new NotFoundException("Forecast Daily Data Not Found for city: " + city);
            }
            return forecastDailyResponse;
        } catch (HttpMessageConversionException e) {
            log.error("Deserialization of Data for AccuWeather ForecastDaily Response failed", e);
            throw new ExternalServiceException("Deserialization of Data for DailyForecast by AccuWeather is Failed", e.getCause());
        } catch (RestClientException e) {
            log.error("API is Failed to fetch the data of AccuWeather Daily Forecast Endpoint", e);
            throw new ExternalServiceException("API is Failed to fetch the data of AccuWeather Endpoint", e.getCause());
        }
    }

    // get a 5 Days Daily Forecast by fetch Daily Forecast Method
    public List<AccuForecastDailyDTO> getForecast5Daily(String city) {
        validateCity(city);
        AccuForecastDailyResponse forecastDailyResponse = fetchForecastDailyResponse(city);
        List<AccuForecastDailyResponse.DailyForecast> list1 = forecastDailyResponse.getDailyForecastList();
        List<AccuForecastDailyDTO> list = new ArrayList<>();

        for (int i = 0; i < list1.toArray().length; i++) {

            AccuForecastDailyResponse.DailyForecast firstElementofDailyForecast = forecastDailyResponse
                    .getDailyForecastList().get(i);

            AccuForecastDailyDTO accuForecastDailyDTO = new AccuForecastDailyDTO();
            accuForecastDailyDTO.setDate(firstElementofDailyForecast.getDate());

            if(!firstElementofDailyForecast.getAirPollution().isEmpty())
            {
                accuForecastDailyDTO.setAirQualityValue(
                        firstElementofDailyForecast.getAirPollution().get(i).getValue());
                accuForecastDailyDTO.setAirQualityCategory(firstElementofDailyForecast.getAirPollution().get(i)
                                .getCategory());
            }

            accuForecastDailyDTO
                    .setDayLongPhrase(forecastDailyResponse.getDailyForecastList().get(i).getDay()
                            .getLongPhrase());
            accuForecastDailyDTO
                    .setDayShortPhrase(forecastDailyResponse.getDailyForecastList().get(i).getDay()
                            .getShortPhrase());

            accuForecastDailyDTO.setNightShortPhrase(
                    forecastDailyResponse.getDailyForecastList().get(i).getNight()
                            .getShortPhrase());
            accuForecastDailyDTO
                    .setNightLongPhrase(forecastDailyResponse.getDailyForecastList().get(i)
                            .getNight().getLongPhrase());

            accuForecastDailyDTO
                    .setMaxTemp((int) Math.round(firstElementofDailyForecast.getTemperature()
                            .getMaximum().getValue()));
            accuForecastDailyDTO
                    .setMinTemp((int) Math.round(firstElementofDailyForecast.getTemperature()
                            .getMinimum().getValue()));

            accuForecastDailyDTO.setHoursOfSun(firstElementofDailyForecast.getHoursOfSun());

            accuForecastDailyDTO.setMoonRise(firstElementofDailyForecast.getMoon().getRise());
            accuForecastDailyDTO.setMoonSet(firstElementofDailyForecast.getMoon().getSet());
            accuForecastDailyDTO.setMoonPhase(firstElementofDailyForecast.getMoon().getMoonPhase());

            accuForecastDailyDTO.setMinFeelTemp(
                    (int) Math.round(firstElementofDailyForecast.getRealFeelTempreture()
                            .getMinimum().getValue()));
            accuForecastDailyDTO.setMaxFeelTemp(
                    (int) Math.round(firstElementofDailyForecast.getRealFeelTempreture()
                            .getMaximum().getValue()));

            accuForecastDailyDTO.setSunRise(firstElementofDailyForecast.getSun().getRise());
            accuForecastDailyDTO.setSunSet(firstElementofDailyForecast.getSun().getSet());

            list.add(accuForecastDailyDTO);

        }
        return list;

    }



    // Get Todays Headline
    public TodayHeadlineDTO getTodayHeadline(String city) {
        validateCity(city);
        AccuForecastDailyResponse response = fetchForecastDailyResponse(city);
        TodayHeadlineDTO todayHeadlineDTO = new TodayHeadlineDTO();
        todayHeadlineDTO.setTodayHeadlineText(response.getTodayheadline().getText());
        return todayHeadlineDTO;
    }

    // Get 12 Hourly Forecast
    public List<AccuForecast12HourlyDTO> getAccuForecast12Hourly(String city) {
        validateCity(city);
        String finalLocationKey = getCityLocationKey(city);
        try {
            String finalUrl = String.format(ACCUWEATHER_FORECAST_HOURLY, finalLocationKey, accuApiKey);
            AccuForecast12HourlyResponse[] response = restTemplate.getForObject(finalUrl,
                    AccuForecast12HourlyResponse[].class);

            if (response == null || response.length == 0) {
                log.warn("AccuWeather 12 Hourly Forecast is Not Found");
                throw new NotFoundException("AccuWeather 12 Hourly Forecast is Not Found");
            }
            List<AccuForecast12HourlyDTO> list = new ArrayList<>();

            ZonedDateTime CurrentDateTime = ZonedDateTime.now();

            for (AccuForecast12HourlyResponse accuForecast12HourlyResponse : response) {
                // Make the Format of the incoming DateTime
                ZonedDateTime incomingDateTime = ZonedDateTime.parse(accuForecast12HourlyResponse.getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
                // Check only Date not a Time if Date is match with the current data then it
                // allowed -- Current Hourly Forecasting by AccuWeather
                if (CurrentDateTime.toLocalDate().equals(incomingDateTime.toLocalDate())) {
                    list.add(mapToAccuForecast12Hourly(accuForecast12HourlyResponse));
                }
            }
        return list;
        }catch (HttpMessageConversionException e)
        {
            log.error("Deserialization of 12 Hourly Forecasting of AccuWeather is Failed", e);
            throw new ExternalServiceException("Deserialization of 12 Hourly Forecasting of AccuWeather is failed", e.getCause());

        }catch (RestClientException e)
        {
            log.error("API Data fetching Failed for the 12 Hourly Forecasting by AccuWeather", e);
            throw new ExternalServiceException("API Data fetching Failed for the 12 Hourly Forecasting by AccuWeather", e.getCause());
        }

    }

    // get Top 50 Cities Info
    public List<AccuTop50CitiesDTO> getTop50Cities() {
        String finalUrl = String.format(ACCUWEATHER_TOP50_CITIES, accuApiKey);
        try {

            AccuTop50CitiesResponse[] response = restTemplate.getForObject(finalUrl,
                    AccuTop50CitiesResponse[].class);
            if (response == null || response.length == 0) {
                log.warn("Response is Not Found");
                throw new NotFoundException("Top 50 Cities Response is not found");
            }
            List<AccuTop50CitiesDTO> list = new ArrayList<>();
            for (AccuTop50CitiesResponse citiesResponse : response) {
                list.add(mapToTop50Cities(citiesResponse));
            }
            return list;

        } catch (HttpMessageConversionException e) {
            log.error("Deserialization of Top 50 Cities is Failed", e);
            throw new ExternalServiceException("Deserialization of Top 50 cities is failed", e.getCause());
        } catch (RestClientException e) {
            log.error("API Data fetching Failed for the Top 50 Cities by AccuWeather", e);
            throw new ExternalServiceException("API Data fetching Failed for the Top 50 Cities by AccuWeather", e.getCause());
        }

    }


    //Get Current Weather Details
    public AccuCurrentWeatherDetailsDTO getAccuWeatherCurrentDetails(String city) {
        validateCity(city);
        String locationKey = getCityLocationKey(city);

        try {

            String currentURL = String.format(ACCUWEATHER_CURRENT_CONDITION, locationKey, accuApiKey);
            AccuCurrentResponse[] responseArray = restTemplate.getForObject(currentURL,
                    AccuCurrentResponse[].class);
            if (responseArray == null || responseArray.length == 0) {
                log.warn("Current Weather Details of AccuWeather is not Found");
                throw new NotFoundException("Current Weather Details Response of AccuWeather is not found");
            }
            AccuCurrentResponse current = responseArray[0];
            return mapToCurrentWeatherDetails(current);
        }catch (HttpMessageConversionException e) {
            log.error("Deserialization of Current Weather Details of AccuWeather is Failed", e);
            throw new ExternalServiceException("Deserialization of Current Weather Details of AccuWeather is failed", e.getCause());
        }catch (RestClientException e) {
            log.error("API Data fetching Failed for the Current Weather Details by AccuWeather", e);
            throw new ExternalServiceException("API Data fetching Failed for the Current Weather Details by AccuWeather", e.getCause());
        }
    }


    //Mapping 1 : Forecast12Hourly

    public AccuForecast12HourlyDTO mapToAccuForecast12Hourly(AccuForecast12HourlyResponse accuForecast12HourlyResponse)
    {
        AccuForecast12HourlyDTO accuForecast12HourlyDTO = new AccuForecast12HourlyDTO();
        accuForecast12HourlyDTO.setDateTime(accuForecast12HourlyResponse.getDateTime());
        accuForecast12HourlyDTO.setIconPhrase(accuForecast12HourlyResponse.getIconPhrase());
        accuForecast12HourlyDTO.setHasPrecipitation(
                accuForecast12HourlyResponse.getHasPrecipitation());
        accuForecast12HourlyDTO
                .setTemperature((int) Math.round(accuForecast12HourlyResponse
                        .getTemperature().getValue()));
        accuForecast12HourlyDTO.setIsDayLight(accuForecast12HourlyResponse.getIsDayLight());
        accuForecast12HourlyDTO.setCloudCover(accuForecast12HourlyResponse.getCloudCover());
        accuForecast12HourlyDTO.setUvIndex(accuForecast12HourlyResponse.getUvIndex());
        accuForecast12HourlyDTO.setFeelsLike(
                (int) Math.round(accuForecast12HourlyResponse.getRealFeelTemperature()
                        .getValue()));
        accuForecast12HourlyDTO
                .setDewPoint(accuForecast12HourlyResponse.getDewPoint().getValue());
        accuForecast12HourlyDTO
                .setVisibility(accuForecast12HourlyResponse.getVisibility().getValue());
        accuForecast12HourlyDTO.setHumidity(accuForecast12HourlyResponse.getRelativeHumidity());
        accuForecast12HourlyDTO.setFeelsLikeDescription(
                accuForecast12HourlyResponse.getRealFeelTemperature().getDescription());
        accuForecast12HourlyDTO
                .setWindDegree(accuForecast12HourlyResponse.getWind().getDirection()
                        .getDegrees());
        accuForecast12HourlyDTO
                .setWindDirection(accuForecast12HourlyResponse.getWind().getDirection()
                        .getDirection());
        accuForecast12HourlyDTO
                .setWindGustSpeed(accuForecast12HourlyResponse.getWindGusts().getSpeed()
                        .getValue());
        accuForecast12HourlyDTO.setWindSpeed(
                accuForecast12HourlyResponse.getWind().getSpeed().getValue());

        return accuForecast12HourlyDTO;

    }


    //Mapping 2 : CurrentWeather
    public AccuCurrentDTO mapToCurrentWeather(AccuCurrentResponse current)
    {
        AccuCurrentDTO accuCurrentDTO = new AccuCurrentDTO();

        accuCurrentDTO.setWeatherText(current.getWeatherText()); // Text
        accuCurrentDTO.setDirectionDegrees(current.getWind().getDirection().getDegrees()); // Degree
        accuCurrentDTO.setDirection(current.getWind().getDirection().getLocalized()); // Direction
        accuCurrentDTO.setPressure(current.getPressure().getMetric().getValue()); // mb
        accuCurrentDTO.setTemperature((int) Math.round(current.getTemperature().getMetric().getValue())); // C
        accuCurrentDTO.setDewPoint(current.getDewPoint().getMetric().getValue()); // Degree C
        accuCurrentDTO.setPrecipitation(
                current.getPrecipitationSummary().getPrecipitation().getMetric().getValue()); // Chance
        accuCurrentDTO.setWindSpeed(current.getWind().getSpeed().getMetric().getValue()); // Km/h
        accuCurrentDTO.setIsDayTime(current.getIsDayTime()); // Booelan
        accuCurrentDTO.setPressureTendency(current.getPressureTendency().getLocalizedText()); // Text
        accuCurrentDTO.setRelativeHumidity(current.getRelativeHumidity()); // % Chance
        accuCurrentDTO.setUvIndex(current.getUvIndex()); // Index
        accuCurrentDTO.setUvIndexText(current.getUvIndexText()); // Index Text
        accuCurrentDTO.setVisibility(current.getVisibility().getMetric().getValue()); // Km
        accuCurrentDTO.setFeelsLike((int) Math.round(current.getRealFeelTemperature().getMetric().getValue())); // C
        accuCurrentDTO.setWindGustSpeed(current.getWindGust().getSpeed().getMetric().getValue()); // Km/h
        accuCurrentDTO.setCloudCover(current.getCloudCover()); // %
        return accuCurrentDTO;

    }

    //Mapping 3 : Top50Cities
    public AccuTop50CitiesDTO mapToTop50Cities(AccuTop50CitiesResponse citiesResponse) {
        AccuTop50CitiesDTO citiesDTO = new AccuTop50CitiesDTO();

        citiesDTO.setCityName(citiesResponse.getCityName());
        citiesDTO.setLatitude(citiesResponse.getGeoPosition().getLatitude());
        citiesDTO.setLongitude(citiesResponse.getGeoPosition().getLongitude());
        citiesDTO.setTemperature(
                (int) Math.round(citiesResponse.getTemperature().getMetric().getValue()));
        citiesDTO.setTemperatureUnit(citiesResponse.getTemperature().getMetric().getUnit());
        citiesDTO.setCountryID(citiesResponse.getCountry().getId());
        citiesDTO.setCountryName(citiesResponse.getCountry().getCountryName());
        citiesDTO.setGmtOffSet(citiesResponse.getTimeZone().getGmtOffset());
        citiesDTO.setLocationKey(citiesResponse.getLocationKey());
        citiesDTO.setIsDayTime(citiesResponse.getIsDayTime());
        citiesDTO.setTimeZoneCode(citiesResponse.getTimeZone().getCode());
        citiesDTO.setTimeZoneName(citiesResponse.getTimeZone().getName());
        citiesDTO.setWeatherText(citiesResponse.getWeatherText());
        citiesDTO.setObservationDateTime(citiesResponse.getTime());
        return citiesDTO;
    }

    // Mapping 4 : Current Weather Details
    public AccuCurrentWeatherDetailsDTO mapToCurrentWeatherDetails(AccuCurrentResponse current) {
        AccuCurrentWeatherDetailsDTO currentDTO = new AccuCurrentWeatherDetailsDTO();

        currentDTO.setTemperature((int) Math.round(current.getTemperature().getMetric().getValue()));
        currentDTO.setFeelsLike((int) Math.round(current.getRealFeelTemperature().getMetric().getValue()));
        currentDTO.setPrecipitation(
                current.getPrecipitationSummary().getPrecipitation().getMetric().getValue());
        currentDTO.setPressure(current.getPressure().getMetric().getValue());
        currentDTO.setPressureTendency(current.getPressureTendency().getLocalizedText());
        currentDTO.setCloudCover(current.getCloudCover());
        currentDTO.setUvIndex(current.getUvIndex());
        currentDTO.setWindDirection(current.getWind().getDirection().getLocalized());
        currentDTO.setWindSpeed(current.getWind().getSpeed().getMetric().getValue());
        currentDTO.setWindDirectionDegrees(current.getWind().getDirection().getDegrees());
        currentDTO.setUvIndexDescription(current.getUvIndexText());
        currentDTO.setWindGustSpeed(current.getWindGust().getSpeed().getMetric().getValue());
        return currentDTO;
    }
}
