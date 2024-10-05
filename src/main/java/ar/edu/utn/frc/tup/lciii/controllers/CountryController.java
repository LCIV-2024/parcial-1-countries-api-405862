package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import ar.edu.utn.frc.tup.lciii.service.Impl.ImplCoutryService;
import ar.edu.utn.frc.tup.lciii.service.SaveCountriesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CountryController {
    @Autowired
    ImplCoutryService implCoutryService;

    @GetMapping("/countries")
    public List<CountryDTO> getCountries(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name) {
        if (code == null && name == null) {
            return implCoutryService.getAllCharacters();
        } else {
            return implCoutryService.getCountry(code, name);
        }
    }

    @GetMapping("/countries/{continent}/continent")
    public List<CountryDTO> getCountriesByContinent(@PathVariable String continent) {
        return implCoutryService.getCountriesByContinent(continent);
    }

    @GetMapping("/countries/{language}/language")
    public List<CountryDTO> getCountriesByLanguage(@PathVariable String language) {
        return implCoutryService.getCountriesByLanguage(language);
    }

    @GetMapping("/countries/most-borders")
    public CountryDTO getCountryWithMostBorders() {
        return implCoutryService.getCountryWithMostBorders();
    }

    @PostMapping("/countries")
    public List<CountryDTO> saveRandomCountries(@RequestBody SaveCountriesRequest request) {
        return implCoutryService.saveRandomCountries(request.getAmountOfCountryToSave());
    }

}