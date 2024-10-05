package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.Impl.ImplCoutryService;
import ar.edu.utn.frc.tup.lciii.service.SaveCountriesRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(CountryController.class)
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImplCoutryService implCoutryService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetCountries_NoParameters_ReturnsAllCountries() throws Exception {
        List<CountryDTO> countries = new ArrayList<>();
        when(implCoutryService.getAllCharacters()).thenReturn(countries);

        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetCountries_WithCode_ReturnsCountryByCode() throws Exception {
        String code = "ARG";
        List<CountryDTO> countries = new ArrayList<>();
        when(implCoutryService.getCountry(code, null)).thenReturn(countries);

        mockMvc.perform(get("/api/countries?code={code}", code))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetCountries_WithName_ReturnsCountryByName() throws Exception {
        String name = "Argentina";
        List<CountryDTO> countries = new ArrayList<>();
        when(implCoutryService.getCountry(null, name)).thenReturn(countries);

        mockMvc.perform(get("/api/countries?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetCountriesByContinent_ReturnsCountriesByContinent() throws Exception {
        String continent = "South America";
        List<CountryDTO> countries = new ArrayList<>();
        when(implCoutryService.getCountriesByContinent(continent)).thenReturn(countries);

        mockMvc.perform(get("/api/countries/{continent}/continent", continent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetCountriesByLanguage_ReturnsCountriesByLanguage() throws Exception {
        String language = "Spanish";
        List<CountryDTO> countries = new ArrayList<>();
        when(implCoutryService.getCountriesByLanguage(language)).thenReturn(countries);

        mockMvc.perform(get("/api/countries/{language}/language", language))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetCountryWithMostBorders_ReturnsCountryDTO() throws Exception {
        CountryDTO countryDTO = new CountryDTO();
        when(implCoutryService.getCountryWithMostBorders()).thenReturn(countryDTO);

        mockMvc.perform(get("/api/countries/most-borders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testSaveRandomCountries_ReturnsSavedCountries() throws Exception {
        SaveCountriesRequest request = new SaveCountriesRequest();
        request.setAmountOfCountryToSave(5);
        List<CountryDTO> countries = new ArrayList<>();
        when(implCoutryService.saveRandomCountries(request.getAmountOfCountryToSave())).thenReturn(countries);

        mockMvc.perform(post("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
}