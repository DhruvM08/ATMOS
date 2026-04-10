package com.atmos.weather_service.model.AccuObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuCurrentResponse {


        @JsonProperty("WeatherText")
        private String weatherText;

        @JsonProperty("IsDayTime")
        private Boolean isDayTime;

        @JsonProperty("RelativeHumidity")
        private Double relativeHumidity;

        @JsonProperty("UVIndex")
        private Integer uvIndex;

        @JsonProperty("UVIndexText")
        private String uvIndexText;

        @JsonProperty("CloudCover")
        private Double cloudCover;

        @JsonProperty("Temperature")
        private TemperatureDTO temperature;

        @JsonProperty("RealFeelTemperature")
        private RealFeelTemperatureDTO realFeelTemperature;

        @JsonProperty("DewPoint")
        private DewPointDTO dewPoint;

        @JsonProperty("Wind")
        private WindDTO wind;

        @JsonProperty("WindGust")
        private WindGust windGust;

        @JsonProperty("Visibility")
        private VisibilityDTO visibility;

        @JsonProperty("Pressure")
        private PressureDTO pressure;

        @JsonProperty("PressureTendency")
        private PressureTendencyDTO pressureTendency;

        @JsonProperty("PrecipitationSummary")
        private PrecipitationSummaryDTO precipitationSummary;


        @Data
        public static class TemperatureDTO {

            @JsonProperty("Metric")
            private UnitDetails metric;

            @JsonProperty("Imperial")
            private UnitDetails imperial;
        }

        @Data
        public static class WindGust{
            @JsonProperty("Speed")
            private SpeedDTO speed;
        }

        @Data
        public static class RealFeelTemperatureDTO {


            @JsonProperty("Metric")
            private UnitDetails metric;

            @JsonProperty("Imperial")
            private UnitDetails imperial;

        }

        @Data
        public static class DewPointDTO {
            @JsonProperty("Metric")
            private UnitDetails metric;

            @JsonProperty("Imperial")
            private UnitDetails imperial;

        }

        @Data
        public static class WindDTO {

            @JsonProperty("Direction")
            private DirectionDTO direction;

            @JsonProperty("Speed")
            private SpeedDTO speed;


        }

        @Data
        public static class VisibilityDTO {

            @JsonProperty("Metric")
            private UnitDetails metric;

            @JsonProperty("Imperial")
            private UnitDetails imperial;
        }

        @Data
        public static class PressureDTO {

            @JsonProperty("Metric")
            private UnitDetails metric;

            @JsonProperty("Imperial")
            private UnitDetails imperial;
        }

        @Data
        public static class PressureTendencyDTO {

            @JsonProperty("LocalizedText")
            private String localizedText;

            @JsonProperty("Code")
            private String code;

        }

        @Data
        public static class PrecipitationSummaryDTO {

            @JsonProperty("Precipitation")
            private PrecipitationDTO precipitation;

        }

        @Data
        public static class PrecipitationDTO {

            @JsonProperty("Metric")
            private UnitDetails metric;

            @JsonProperty("Imperial")
            private UnitDetails imperial;

        }

        @Data
        public static class SpeedDTO {

            @JsonProperty("Metric")
            private UnitDetails metric;

            @JsonProperty("Imperial")
            private UnitDetails imperial;
        }

        @Data
        public static class DirectionDTO {

            @JsonProperty("Degrees")
            private Double degrees;

            @JsonProperty("Localized")
            private String localized;
        }

        @Data
        public static class UnitDetails {

            @JsonProperty("Value")
            private Double value;

            @JsonProperty("Unit")
            private String unit;
        }


}
