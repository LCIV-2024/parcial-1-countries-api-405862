package ar.edu.utn.frc.tup.lciii.service.Impl;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import ar.edu.utn.frc.tup.lciii.service.CountryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImplCoutryService implements CountryServiceI {

    @Autowired
    CountryService countryService;

    @Autowired
    CountryRepository countryRepository;

    public List<CountryDTO> getAllCharacters() {
        List<Country> countries = countryService.getAllCountries();
        List<CountryDTO> countryDTOS = new ArrayList<>();
        for (Country country : countries) {
            countryDTOS.add(countryService.mapToDTO(country));
        }
        return countryDTOS;
    }

    public List<CountryDTO> getCountry(String code, String name) {
        List<Country> allCountries = countryService.getAllCountries();
        return allCountries.stream()
                .filter(country -> (code == null || country.getCode().equalsIgnoreCase(code))
                        && (name == null || country.getName().toLowerCase().contains(name.toLowerCase())))
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CountryDTO> getCountriesByContinent(String continent) {
        List<Country> allCountries = countryService.getAllCountries();
        return allCountries.stream()
                .filter(country -> country.getRegion().equalsIgnoreCase(continent))
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CountryDTO> getCountriesByLanguage(String language) {
        List<Country> allCountries = countryService.getAllCountries();
        return allCountries.stream()
                .filter(country -> country.getLanguages() != null &&
                        country.getLanguages().values().stream()
                                .anyMatch(lang -> lang.equalsIgnoreCase(language)))
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());
    }

    public CountryDTO getCountryWithMostBorders() {
        List<Country> allCountries = countryService.getAllCountries();
        return allCountries.stream()
                .max(Comparator.comparingInt(country -> country.getBorders().size()))
                .map(countryService::mapToDTO)
                .orElse(null);
    }

    public List<CountryDTO> saveRandomCountries(int amount) {
        List<Country> allCountries = countryService.getAllCountries();
        Collections.shuffle(allCountries);
        List<CountryEntity> countriesToSave = allCountries.stream()
                .limit(Math.min(amount, 10))
                .map(countryService::mapToEntity)
                .collect(Collectors.toList());

        List<CountryEntity> savedCountries = countryRepository.saveAll(countriesToSave);

        return savedCountries.stream()
                .map(countryService::mapToCountry)
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());
    }


}
