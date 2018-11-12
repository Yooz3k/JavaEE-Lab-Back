package pg.jee.lab.business.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

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
}
