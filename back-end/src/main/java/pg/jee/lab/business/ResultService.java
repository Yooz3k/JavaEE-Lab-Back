package pg.jee.lab.business;

import pg.jee.lab.api.controllers.ResultsController;
import pg.jee.lab.business.entities.Circuit;
import pg.jee.lab.business.entities.Driver;
import pg.jee.lab.business.entities.Result;
import pg.jee.lab.business.entities.ResultsDTO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static pg.jee.lab.api.UriUtils.uriWithParams;

@ApplicationScoped
public class ResultService implements Serializable {

    private SortedMap<Integer, Result> results = new TreeMap<>();
    private SortedMap<Integer, Driver> drivers = new TreeMap<>();
    private SortedMap<Integer, Circuit> circuits = new TreeMap<>();

    @PostConstruct
    public void init() {
        Circuit circuit1 = new Circuit(1, "Monza", "Włochy", false, false);
        Circuit circuit2 = new Circuit(2, "Sakhir", "Bahrajn", false, true);

        Result res1 = new Result(1, 5, new BigDecimal("18.208"), 1, true, circuit1, null);
        Result res2 = new Result(2, 2, new BigDecimal("8.705"), 1, true, circuit2, null);
        Result res3 = new Result(3, 5, new BigDecimal("18.208"), 1, true, circuit1, null);
        Result res4 = new Result(4, 2, new BigDecimal("8.705"), 1, true, circuit2, null);
        Result res5 = new Result(5, 5, new BigDecimal("18.208"), 1, true, circuit1, null);
        Result res6 = new Result(6, 2, new BigDecimal("8.705"), 1, true, circuit2, null);
        Result res7 = new Result(7, 5, new BigDecimal("18.208"), 1, true, circuit1, null);
        Result res8 = new Result(8, 2, new BigDecimal("8.705"), 1, true, circuit2, null);
        Result res9 = new Result(9, 5, new BigDecimal("18.208"), 1, true, circuit1, null);
        Result res10 = new Result(10, 2, new BigDecimal("8.705"), 1, true, circuit2, null);
        Result res11 = new Result(11, 5, new BigDecimal("18.208"), 1, true, circuit1, null);
        Result res12 = new Result(12, 2, new BigDecimal("8.705"), 1, true, circuit2, null);
        Result res13 = new Result(13, 5, new BigDecimal("18.208"), 1, true, circuit1, null);
        Result res14 = new Result(14, 2, new BigDecimal("8.705"), 1, true, circuit2, null);

        Driver driver1 = new Driver(1, "Max", "Verstappen", "Holandia", "Red Bull",
                prepareTimestampFromDate(LocalDate.of(1997, 9, 30)), false, new ArrayList<>(Arrays.asList(res1)), null);
        Driver driver2 = new Driver(2, "Kimi", "Raikkonen", "Finlandia", "Ferrari",
                prepareTimestampFromDate(LocalDate.of(1979, 10, 17)), true, new ArrayList<>(Arrays.asList(res2)), null);

        results.put(res1.getId(), res1);
        results.put(res2.getId(), res2);
        results.put(res3.getId(), res3);
        results.put(res4.getId(), res4);
        results.put(res5.getId(), res5);
        results.put(res6.getId(), res6);
        results.put(res7.getId(), res7);
        results.put(res8.getId(), res8);
        results.put(res9.getId(), res9);
        results.put(res10.getId(), res10);
        results.put(res11.getId(), res11);
        results.put(res12.getId(), res12);
        results.put(res13.getId(), res13);
        results.put(res14.getId(), res14);

        drivers.put(driver1.getId(), driver1);
        drivers.put(driver2.getId(), driver2);

        circuits.put(circuit1.getId(), circuit1);
        circuits.put(circuit2.getId(), circuit2);
    }

    public Collection<Result> findAllResults() {
        System.out.println("All");
        return results.values();
    }

    public Collection<Driver> findAllDrivers() {
        return drivers.values();
    }

    public Collection<Circuit> findAllCircuits() {
        return circuits.values();
    }

    public ResultsDTO findResults(int offset, int limit) {
        System.out.println("Stream");
        List<Result> resultsFound = results.values().stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());

        int previousPageOffset = offset - limit >= 0 ? offset - limit : 0;
        int nextPageOffset = offset + limit > results.size() ? offset : offset + limit;

        String previousPage = uriWithParams(ResultsController.class, "getResults", String.valueOf(limit), String.valueOf(previousPageOffset)).toString();
        String nextPage = uriWithParams(ResultsController.class, "getResults", String.valueOf(limit), String.valueOf(nextPageOffset)).toString();

        ResultsDTO resultsToReturn = new ResultsDTO(resultsFound, previousPage, nextPage);

        return resultsToReturn;
    }

    public Result findResult(Integer id) {
        if (id == null)
            return null;

        return results.get(id);
    }

    public Driver findDriver(Integer id) {
        if (id == null)
            return null;

        return drivers.get(id);
    }

    public Circuit findCircuit(Integer id) {
        if (id == null)
            return null;

        return circuits.get(id);
    }

    public void removeResult(Result result) {
        if (isResultNotNull(result)) {
            removeResultsFromDrivers(result);
            results.remove(result.getId());
        }
    }

    public void removeDriver(Driver driver) {
        if (isDriverNotNull(driver)) {
            //removeResults(driver);
            drivers.remove(driver.getId());
        }
    }

    public void removeCircuit(Circuit circuit) {
        if (isCircuitNotNull(circuit)) {
            removeCircuitsFromResults(circuit);
            circuits.remove(circuit.getId());
        }
    }

    private void removeResultsFromDrivers(Result result) {
        for (Driver driver : drivers.values()) {
            if (driver.getResults().contains(result)) {
                driver.getResults().remove(result);
            }
        }
    }

    private void removeCircuitsFromResults(Circuit circuit) {
        for (Result result : results.values()) {
            if (result.getCircuit().equals(circuit)) {
                result.setCircuit(new Circuit());
            }
        }
    }

    public void saveResult(Result result) {
        if (result != null) {
            if (result.getId() == null) {
                result.setId(generateResultId());
            }

            results.put(result.getId(), result);
            updateDrivers(result);
        }
    }

    public void saveDriver(Driver driver) {
        if (driver != null) {
            if (driver.getId() == null) {
                driver.setId(generateDriverId());
            }

            drivers.put(driver.getId(), driver);
        }
    }

    public void saveCircuit(Circuit circuit) {
        if (circuit != null) {
            if (circuit.getId() == null) {
                circuit.setId(generateCircuitId());
            }

            circuits.put(circuit.getId(), circuit);
            updateResults(circuit);
        }
    }

    private void updateDrivers(Result result) {
        for (Driver driver : drivers.values()) {
            if (driver.getResults().contains(result)) {
                driver.getResults().remove(result);
                driver.getResults().add(result);
            }
        }
    }

    private void updateResults(Circuit circuit) {
        for (Result result : results.values()) {
            if (result.getCircuit().equals(circuit)) {
                result.setCircuit(circuit);
                updateDrivers(result);
            }
        }
    }

    private boolean isResultNotNull(Result result) {
        return result != null && result.getId() != null;
    }

    private boolean isDriverNotNull(Driver driver) {
        return driver != null && driver.getId() != null;
    }

    private boolean isCircuitNotNull(Circuit circuit) {
        return circuit != null && circuit.getId() != null;
    }

    private int generateDriverId() {
        if (!drivers.isEmpty()) {
            return drivers.lastKey() + 1;
        } else {
            return 1;
        }
    }

    private int generateResultId() {
        if (!results.isEmpty()) {
            return results.lastKey() + 1;
        } else {
            return 1;
        }
    }

    private int generateCircuitId() {
        if (!circuits.isEmpty()) {
            return circuits.lastKey() + 1;
        } else {
            return 1;
        }
    }

    private Long prepareTimestampFromDate(LocalDate date) {
        return Timestamp.valueOf(date.atStartOfDay()).getTime();
    }
}
