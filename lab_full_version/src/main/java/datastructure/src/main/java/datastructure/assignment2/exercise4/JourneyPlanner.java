package datastructure.assignment2.exercise4;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JourneyPlanner {

    private final Map<String, Set<BusTrip>> locationMap;

    public JourneyPlanner() {
        locationMap = new HashMap<>();
    }

    public boolean add(BusTrip trip) {
        if (!locationMap.containsKey(trip.getDepartLocation())) {
            locationMap.putIfAbsent(trip.getDepartLocation(), new HashSet<>());
        }

        return locationMap.get(trip.getDepartLocation()).add(trip);
    }

    public List<BusJourney> getPossibleJourneys(String startLocation,
                                                LocalTime startTime,
                                                String endLocation,
                                                LocalTime endTime) {

        if (!locationMap.containsKey(startLocation)) {
            return new ArrayList<>();
        }

        List<BusTrip> startBuses = locationMap.get(startLocation).stream()
                .filter(trip -> trip.getDepartTime().compareTo(startTime) >= 0)
                .collect(Collectors.toList());

        List<BusJourney> journeys = new ArrayList<>();

        for (BusTrip departure : startBuses) {
            findPaths(departure,
                    endLocation,
                    endTime,
                    new BusJourney(),
                    journeys);
        }

        return journeys;
    }

    public static class JourneyComparator implements Comparator<BusJourney> {

        @Override
        public int compare(final BusJourney o1, final BusJourney o2) {
            return o1.getDestinationTime().compareTo(o2.getDestinationTime());
        }
    }

    private void findPaths(BusTrip departure,
                           String endLocation,
                           LocalTime endTime,
                           BusJourney currentJourney,
                           List<BusJourney> journeys) {


        BusJourney tempJourney = currentJourney.cloneJourney();
        tempJourney.addBus(departure);

        // if departure will stop at the endLocation
        if (departure.getArrivalTime().compareTo(endTime) <= 0
                && departure.getArrivalLocation().equals(endLocation)) {
            journeys.add(tempJourney);
            return;
        }

        List<BusTrip> rightNextBuses = locationMap.get(departure.getArrivalLocation())
                .stream()
                .filter(bus -> bus.getDepartTime().compareTo(departure.getArrivalTime()) >= 0 && bus.getArrivalTime().compareTo(endTime) <= 0)
                // no cycle visit
                .filter(bus -> !tempJourney.containsLocation(bus.getArrivalLocation()))
                .collect(Collectors.toList());

        if (rightNextBuses.isEmpty()) {
            return;
        }

        for (BusTrip rightNextBus : rightNextBuses) {
            findPaths(rightNextBus, endLocation, endTime, tempJourney, journeys);
        }
    }

}
