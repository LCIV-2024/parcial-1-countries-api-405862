package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Entity
@Table(name = "countries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String name;
    private Long population;
    private Double area;
    private String region;

    @ElementCollection
    @CollectionTable(name = "country_languages", joinColumns = @JoinColumn(name = "country_id"))
    @MapKeyColumn(name = "language_code")
    @Column(name = "language_name")
    private Map<String, String> languages;

    @ElementCollection
    @CollectionTable(name = "country_borders", joinColumns = @JoinColumn(name = "country_id"))
    @Column(name = "border_code")
    private List<String> borders;

}
