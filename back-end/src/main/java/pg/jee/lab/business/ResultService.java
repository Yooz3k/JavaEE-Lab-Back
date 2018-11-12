package pg.jee.lab.business;

import pg.jee.lab.business.entities.Circuit;
import pg.jee.lab.business.entities.Driver;
import pg.jee.lab.business.entities.Result;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@ApplicationScoped
public class ResultService implements Serializable {

    private SortedMap<Integer, Result> results = new TreeMap<>();
    private SortedMap<Integer, Driver> drivers = new TreeMap<>();
    private SortedMap<Integer, Circuit> circuits = new TreeMap<>();

    @PostConstruct
    public void init() {
        Circuit circuit1 = new Circuit(1, "Monza", "Włochy", false, false);
        Circuit circuit2 = new Circuit(2, "Sakhir", "Bahrajn", false, true);

        Result res1 = new Result(1, 5, new BigDecimal("18.208"), 1, true, circuit1);
        Result res2 = new Result(2, 2, new BigDecimal("8.705"), 1, true, circuit2);

        Driver driver1 = new Driver(1, "Max", "Verstappen", "Holandia", "Red Bull",
                prepareTimestampFromDate(LocalDate.of(1997, 9, 30)), false, new ArrayList<>(Arrays.asList(res1)));
        Driver driver2 = new Driver(2, "Kimi", "Raikkonen", "Finlandia", "Ferrari",
                prepareTimestampFromDate(LocalDate.of(1979, 10, 17)), true, new ArrayList<>(Arrays.asList(res2)));

        results.put(res1.getId(), res1);
        results.put(res2.getId(), res2);

        drivers.put(driver1.getId(), driver1);
        drivers.put(driver2.getId(), driver2);

        circuits.put(circuit1.getId(), circuit1);
        circuits.put(circuit2.getId(), circuit2);
    }

    public Collection<Result> findAllResults() {
        return results.values();
    }

    public Collection<Driver> findAllDrivers() {
        return drivers.values();
    }

    public Collection<Circuit> findAllCircuits() {
        return circuits.values();
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

    private void removeResults(Driver driver) {
        Map<Result, Boolean> resultsToRemove = collectResultsToRemove(driver);

        removeUnusedResults(resultsToRemove.keySet());
    }

    private Map<Result, Boolean> collectResultsToRemove(Driver driver) {
        Map<Result, Boolean> resultsToRemove = new HashMap<>();

        for (Result result : driver.getResults()) {
            for (Driver driverEntry : drivers.values()) {
                if (driverHasResult(result, driverEntry)) {
                    markAsUsed(resultsToRemove, result);
                    break;
                }
            }

            markAsUnused(resultsToRemove, result);
        }

        return resultsToRemove;
    }

    private void markAsUnused(Map<Result, Boolean> resultsToRemove, Result result) {
        if (!resultsToRemove.containsKey(result)) {
            resultsToRemove.put(result, true);
        }
    }

    private void markAsUsed(Map<Result, Boolean> resultsToRemove, Result result) {
        resultsToRemove.put(result, false);
    }

    private void removeUnusedResults(Set<Result> results) {
        for (Result result : results) {
            removeResult(result);
        }
    }

    private boolean driverHasResult(Result result, Driver driver) {
        if (driver.getResults().contains(result)) {
            return true;
        }

        return false;
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
