package pg.jee.lab.business.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pg.jee.lab.api.controllers.CircuitsController;

import java.io.Serializable;
import java.math.BigDecimal;

import static pg.jee.lab.api.UriUtils.uri;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    private Integer id;

    private Integer pos;

    private BigDecimal gapToLeader;

    private Integer pitStops;

    private Boolean didFinish;

    private Circuit circuit;

    private String uriCircuit;

    public String getUriCircuit() {
        if (circuit.getId() != null)
            return uri(CircuitsController.class, CircuitsController.GET_CIRCUIT_METHOD_NAME, circuit.getId()).toString();
        else
            return "";
    }
}
