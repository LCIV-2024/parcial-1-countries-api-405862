package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        @Autowired
        private final CountryRepository countryRepository;

        @Autowired
        RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                String cca3 = (String) countryData.get("cca3");
                List<String> borders = (List<String>) countryData.get("borders");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .code(cca3)
                        .borders(borders != null ? borders : new ArrayList<>())
                        .build();
        }


        public CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        public CountryEntity mapToEntity(Country country) {
                CountryEntity entity = new CountryEntity();
                entity.setCode(country.getCode());
                entity.setName(country.getName());
                entity.setPopulation(country.getPopulation());
                entity.setArea(country.getArea());
                entity.setRegion(country.getRegion());
                entity.setLanguages(country.getLanguages());
                entity.setBorders(country.getBorders());
                return entity;
        }

        public Country mapToCountry(CountryEntity entity) {
                return Country.builder()
                        .code(entity.getCode())
                        .name(entity.getName())
                        .population(entity.getPopulation())
                        .area(entity.getArea())
                        .region(entity.getRegion())
                        .languages(entity.getLanguages())
                        .borders(entity.getBorders())
                        .build();
        }
}