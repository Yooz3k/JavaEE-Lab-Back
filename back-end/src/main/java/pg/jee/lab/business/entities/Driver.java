package pg.jee.lab.business.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Driver implements Serializable {

    private Integer id;

    private String firstName;

    private String secondName;

    private String nationality;

    private String team;

    private Long dateOfBirth;

    private Boolean wasWorldChampion;

    private List<Result> results = new ArrayList<>();
}



