package datastructure.assignment2.exercise4;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BusJourney {
    private final List<BusTrip> trips;

    public BusJourney() {
        trips = new ArrayList<>();
    }

    public BusJourney(List<BusTrip> trips) {
        this.trips = trips;
    }

    public boolean addBus(BusTrip bus) {
        if (bus == null) {
            return false;
        }

        if (trips.isEmpty()) {
            trips.add(bus);
            return true;
        }

        BusTrip lastStop = trips.get(trips.size() - 1);
        if (!lastStop.getArrivalLocation().equals(bus.getDepartLocation())
                || lastStop.getArrivalTime().compareTo(bus.getDepartTime()) > 0) {
            return false;
        }

        // no closed graph
        if (containsLocation(bus.getArrivalLocation())) {
            return false;
        }

        trips.add(bus);
        return true;
    }

    public boolean containsLocation(String location) {
        return trips.stream()
                .anyMatch(e ->
                        e.getArrivalLocation().equals(location) || e.getDepartLocation().equals(location));
    }

    public String getOrigin() {
        if (trips.isEmpty()) {
            return null;
        }

        return trips.get(0).getDepartLocation();
    }

    public LocalTime getOriginTime() {
        if (trips.isEmpty()) {
            return null;
        }

        return trips.get(0).getDepartTime();
    }

    public LocalTime getDestinationTime() {
        if (trips.isEmpty()) {
            return null;
        }
        return trips.get(trips.size() - 1).getArrivalTime();
    }

    public String getDestination() {
        if (trips.isEmpty()) {
            return null;
        }
        return trips.get(trips.size() - 1).getArrivalLocation();
    }

    public boolean removeLastTrip() {
        if (trips.isEmpty()) {
            return false;
        }

        trips.remove(trips.size() - 1);
        return true;
    }

    public BusJourney cloneJourney() {
        return new BusJourney(new ArrayList<>(trips));
    }

    public int getNumberOfBusTrips() {
        return trips.size();
    }

    public double getTotalCost() {
        return trips.stream().reduce(0f,
                (x, y) -> x + y.getCost(),
                Float::sum);
    }

    @Override
    public String toString() {
        return String.format("TOTAL COST: $%s!!!", getTotalCost())
                + "\n" + trips.stream().map(BusTrip::toString).collect(Collectors.joining("\n"));
    }
}
