package pg.jee.lab.business.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultsDTO {

    private List<Result> results;

    private String previousPage;

    private String nextPage;
}
