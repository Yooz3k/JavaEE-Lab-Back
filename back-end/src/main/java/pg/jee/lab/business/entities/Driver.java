package pg.jee.lab.business.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pg.jee.lab.api.controllers.ResultsController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static pg.jee.lab.api.UriUtils.uri;

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

    private List<String> uriResults = new ArrayList<>();

    public List<String> getUriResults() {
        List<String> resultsUris = new ArrayList<>();

        for (Result result : results) {
            resultsUris.add(createResultUri(result));
        }

        return resultsUris;
    }

    private String createResultUri(Result result) {
        return uri(ResultsController.class, ResultsController.GET_RESULT_METHOD_NAME, result.getId()).toString();
    }
}



