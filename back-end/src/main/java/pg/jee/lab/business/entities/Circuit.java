package pg.jee.lab.business.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Circuit implements Serializable {

    private Integer id;

    private String name;

    private String country;

    private Boolean hostsStreetRace;

    private Boolean hostsNightRace;
}
