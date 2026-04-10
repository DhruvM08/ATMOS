package com.atmos.weather_service.model.AccuObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuForecastDailyResponse {


    @JsonProperty("DailyForecasts")
    private List<DailyForecast> dailyForecastList;

    @JsonProperty("Headline")
    private Headline todayheadline;

    @Data
    public static class Headline {
        @JsonProperty("Text")
        private String text;
    }


    @Data
    public static class DailyForecast{


        @JsonProperty("Date")
        private String date;

        @JsonProperty("Sun")
        private SunRiseandSet sun;

        @JsonProperty("Moon")
        private MoonRiseandSet moon;

        @JsonProperty("Temperature")
        private MinandMaxTemp temperature;

        @JsonProperty("RealFeelTemperature")
        private MinandMaxTempFeel realFeelTempreture;

        @JsonProperty("HoursOfSun")
        private Double hoursOfSun;

        @JsonProperty("AirAndPollen")
        private List<AirPollution> airPollution;

        @JsonProperty("Day")
        private Day day;

        @JsonProperty("Night")
        private Night night;



    }




    @Data
    public static class SunRiseandSet{

        @JsonProperty("Rise")
        private String rise;

        @JsonProperty("Set")
        private String set;

    }

    @Data
    public static class MoonRiseandSet{

        @JsonProperty("Rise")
        private String rise;

        @JsonProperty("Set")
        private String set;

        @JsonProperty("Phase")
        private String moonPhase;

    }

    @Data
    public static class MinandMaxTemp{

        @JsonProperty("Minimum")
        private Minimum minimum;

        @JsonProperty("Maximum")
        private Maximum maximum;
    }

    @Data
    public static class MinandMaxTempFeel{

        @JsonProperty("Minimum")
        private Minimum minimum;

        @JsonProperty("Maximum")
        private Maximum maximum;
    }

    @Data
    public static class AirPollution{

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Category")
        private String category;

        @JsonProperty("Value")
        private Integer value;

    }

    @Data
    public static class Day{

        @JsonProperty("ShortPhrase")
        private String shortPhrase;
        @JsonProperty("LongPhrase")
        private String longPhrase;
    }


    @Data
    public static class Night{

        @JsonProperty("ShortPhrase")
        private String shortPhrase;
        @JsonProperty("LongPhrase")
        private String longPhrase;
    }

    @Data
    public static class Minimum{
        @JsonProperty("Value")
        private Double value;

        @JsonProperty("Unit")
        private String unit;
    }

    @Data
    public static class Maximum{

        @JsonProperty("Value")
        private Double value;

        @JsonProperty("Unit")
        private String unit;
    }

}
