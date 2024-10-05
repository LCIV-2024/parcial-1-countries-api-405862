package ar.edu.utn.frc.tup.lciii.service.Impl;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ImplCoutryServiceTest {
    @InjectMocks
    private ImplCoutryService implCountryService;

    @Mock
    private CountryService countryService;

    @Mock
    private CountryRepository countryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCharacters() {
        Country country1 = new Country("United States", 331002651, 9833517, "USA", "Americas", new ArrayList<>(), Collections.singletonMap("en", "English"));
        Country country2 = new Country("Argentina", 45195777, 2780400, "ARG", "Americas", new ArrayList<>(), Collections.singletonMap("es", "Spanish"));

        when(countryService.getAllCountries()).thenReturn(Arrays.asList(country1, country2));
        when(countryService.mapToDTO(any(Country.class))).thenAnswer(invocation -> {
            Country c = invocation.getArgument(0);
            return new CountryDTO(c.getCode(), c.getName());
        });

        List<CountryDTO> result = implCountryService.getAllCharacters();

        assertEquals(2, result.size());
        assertEquals("USA", result.get(0).getCode());
        assertEquals("ARG", result.get(1).getCode());
    }

    @Test
    public void testGetCountry() {
        Country country = new Country("United States", 331002651, 9833517, "USA", "Americas", new ArrayList<>(), Collections.singletonMap("en", "English"));

        when(countryService.getAllCountries()).thenReturn(Collections.singletonList(country));
        when(countryService.mapToDTO(any(Country.class))).thenReturn(new CountryDTO("USA", "United States"));

        List<CountryDTO> result = implCountryService.getCountry("USA", null);

        assertEquals(1, result.size());
        assertEquals("USA", result.get(0).getCode());
    }

    @Test
    public void testGetCountriesByContinent() {
        Country country1 = new Country("United States", 331002651, 9833517, "USA", "Americas", new ArrayList<>(), Collections.singletonMap("en", "English"));
        Country country2 = new Country("Argentina", 45195777, 2780400, "ARG", "Americas", new ArrayList<>(), Collections.singletonMap("es", "Spanish"));

        when(countryService.getAllCountries()).thenReturn(Arrays.asList(country1, country2));
        when(countryService.mapToDTO(any(Country.class))).thenAnswer(invocation -> {
            Country c = invocation.getArgument(0);
            return new CountryDTO(c.getCode(), c.getName());
        });

        List<CountryDTO> result = implCountryService.getCountriesByContinent("Americas");

        assertEquals(2, result.size());
        assertEquals("USA", result.get(0).getCode());
        assertEquals("ARG", result.get(1).getCode());
    }

    @Test
    public void testGetCountriesByLanguage() {
        Country country1 = new Country("United States", 331002651, 9833517, "USA", "Americas", new ArrayList<>(), Collections.singletonMap("en", "English"));
        Country country2 = new Country("Argentina", 45195777, 2780400, "ARG", "Americas", new ArrayList<>(), Collections.singletonMap("es", "Spanish"));

        when(countryService.getAllCountries()).thenReturn(Arrays.asList(country1, country2));
        when(countryService.mapToDTO(any(Country.class))).thenAnswer(invocation -> {
            Country c = invocation.getArgument(0);
            return new CountryDTO(c.getCode(), c.getName());
        });

        List<CountryDTO> result = implCountryService.getCountriesByLanguage("Spanish");

        assertEquals(1, result.size());
        assertEquals("ARG", result.get(0).getCode());
    }

    @Test
    public void testGetCountryWithMostBorders() {
        Country country1 = new Country("United States", 331002651, 9833517, "USA", "Americas", Arrays.asList("CAN", "MEX"), Collections.singletonMap("en", "English"));
        Country country2 = new Country("Argentina", 45195777, 2780400, "ARG", "Americas", Arrays.asList("BRA", "PAR"), Collections.singletonMap("es", "Spanish"));

        when(countryService.getAllCountries()).thenReturn(Arrays.asList(country1, country2));
        when(countryService.mapToDTO(any(Country.class))).thenAnswer(invocation -> {
            Country c = invocation.getArgument(0);
            return new CountryDTO(c.getCode(), c.getName());
        });

        CountryDTO result = implCountryService.getCountryWithMostBorders();

        assertEquals("USA", result.getCode());
    }

    @Test
    public void testSaveRandomCountries() {
        Country country1 = new Country("United States", 331002651, 9833517, "USA", "Americas", new ArrayList<>(), Collections.singletonMap("en", "English"));
        CountryEntity countryEntity1 = new CountryEntity();
        countryEntity1.setCode("USA");
        countryEntity1.setName("United States");

        when(countryService.getAllCountries()).thenReturn(Collections.singletonList(country1));
        when(countryService.mapToEntity(any(Country.class))).thenReturn(countryEntity1);
        when(countryRepository.saveAll(any())).thenReturn(Collections.singletonList(countryEntity1));
        when(countryService.mapToCountry(any(CountryEntity.class))).thenReturn(country1);
        when(countryService.mapToDTO(any(Country.class))).thenReturn(new CountryDTO("USA", "United States"));

        List<CountryDTO> result = implCountryService.saveRandomCountries(1);

        assertEquals(1, result.size());
        assertEquals("USA", result.get(0).getCode());
    }
}