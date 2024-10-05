package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CountryServiceI {
    List<CountryDTO> getAllCharacters();
    List<CountryDTO> getCountry(String code, String name);
}
