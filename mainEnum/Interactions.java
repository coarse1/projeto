package mainEnum;

public enum Interactions {
	HELP("addJourney: Inserts a new journey into the transportation network.%nfindJourney: Finds the earliest journey that allows a passenger to travel from one station to another.%nfindJourneyMinStops: Finds the direct journey with the minimum number of stops.%nremoveJourney: Removes the given journey from the transportation network.%nhelp: Displays the list of available commands.%nquit: Saves the transportation network and terminates the application.%n"),
	QUIT("Transportation network saved.%nGoodbye.%n"),
	NETWORK_RESTORED("Transportation network restored successfully.%n"),
	NETWORK_NOT_FOUND("Transportation network not found.%nLoading line definition. %d stations loaded.%n"),
	NO_LINE_DEF("No line definition.%n"), JOURNEY_EXISTS("Journey already exists.%n"),
	JOURNEY_ADDED("Journey successfully added.%n"), NETWORK_SAVED("Transportation network saved.%nGoodbye.%n"),
	JOURNEY_REMOVED("Journey successfully removed.%n"), INVALID_JOURNEY("Invalid journey.%n"),
	INVALID_STATION("Invalid station.%n"), INVALID_INTERVAL(""), INVALID_TIME("Invalid time.%n"),
	WRONG_ORDER("Stations are not in the correct order.%n"), NO_DIRECT_JOURNEY("No direct journey is available.%n"),
	TRIP("Journey: %s%nDeparture station: %s%nDeparture time: %s%nArrival station: %s%nArrival time: %s%n"),
	STOPS("Stops: %d%n");

	private final String string;

	Interactions(String string) {
		this.string = string;
	}

	public final String getString() {
		return string;
	}

}
