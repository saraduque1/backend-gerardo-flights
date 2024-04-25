package co.edu.udea.sitas.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Airport")
public class Airport implements Serializable {
    @Id
    @Column(name = "airport_code")
    private String airportCode;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "runways")
    private Integer runways;

    @JsonIgnore
    @OneToMany(mappedBy = "originAirport", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Scale> originScales;

    @JsonIgnore
    @OneToMany(mappedBy = "destinationAirport", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Scale> destinationScales;

    @Override
    public String toString() {
        return "Airport{" +
                "runways=" + runways +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", airportCode='" + airportCode + '\'' +
                '}';
    }
}
