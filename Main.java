import java.util.Scanner; 
import mainEnum.*;
import journeyPlanner.*;
import java.io.*;
import dataStructures.List;
import dataStructures.SinglyLinkedList;


/**
 * Main class of the "Journey Planner" application.
 * Reads the user's commands, forwards them to the transportation network and
 * writes the results. When it starts, it restores the network from the
 * serialization file (or, if it does not exist, loads the line definition);
 * before it ends, it saves it again.
 *
 * Space complexity: O(S + J*k), where S is the number of stations, J the number
 * of journeys and k the number of stops of a journey (the space is taken up by
 * the JourneyPlanner that main creates).
 *
 * authors: Andre Carneiro and Luis Fidalgo
 * ID: 74372_75129
 */
public class Main {
	private static final File FILE = new File("JourneyPlanner.ser");

	/**
	 * Program entry point. Restores (or creates) the network, processes the
	 * commands until "quit" and, at the end, serializes the network.
	 *
	 * Time complexity (c = number of commands, k = stops of a journey,
	 * P = departures of a station, S = stations, J = journeys):
	 * best case O(1) (there is no network nor line definition);
	 * worst case O(S + J*k*P + c*k*P) (the network is restored and each of the
	 * c commands costs at most O(k*P)).
	 *
	 * @param args - arguments (the first one is the line definition file)
	 */
	public static void main(String[] args) {
		JourneyPlanner jp = Main.deserialize(args);
		if (jp == null) {
			return;
		}

		try (Scanner in = new Scanner(System.in)) {
			Main.evaluateCommand(in, jp);
		}

		Main.serialize(jp);
	}

	/**
	 * Reads the commands and calls the matching method, until "quit".
	 *
	 * Time complexity: best case O(1) (the first command is quit);
	 * worst case O(c*k*P) (c commands, each one costs at most O(k*P)).
	 *
	 * @param in - Scanner
	 * @param jp - journey planner
	 */
	private static void evaluateCommand(Scanner in, JourneyPlanner jp) {
		String command;
		do {
			command = in.nextLine().trim().toUpperCase();

			try {
				switch (Commands.valueOf(command)) {

				case HELP -> Main.help();
				case QUIT -> {}
				case ADDJOURNEY -> Main.addJourney(in, jp);
				case FINDJOURNEY -> Main.findJourney(in, jp);
				case FINDJOURNEYMINSTOPS -> Main.findJourneyMinStops(in, jp);
				case REMOVEJOURNEY -> Main.removeJourney(in, jp);

				default -> {
				}
				}

			} catch (IllegalArgumentException e) {
			}

		} while (!command.equals(Commands.QUIT.toString()));
	}

	/**
	 * Command "help": writes the list of available commands.
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 * (the number of commands is fixed).
	 */
	private static void help() {
		System.out.printf(Interactions.HELP.getString());
	}

	/**
	 * Command "addJourney": reads the identifier and the k stops of the journey
	 * and adds it, if no journey with that identifier exists.
	 *
	 * Time complexity: best case O(k) (the journey already exists: only the k stops
	 * are read); worst case O(k*P) (each of the k-1 departures is inserted in the
	 * soted map of its station, which may be degenerate; on average O(k log P)).
	 *
	 * @param in - Scanner
	 * @param jp - journey planner
	 */
	private static void addJourney(Scanner in, JourneyPlanner jp) {
		String ID = in.nextLine().trim();
		int n = Integer.parseInt(in.nextLine().trim());
		String firstStation = in.nextLine().trim();
		String firstDepTime = in.nextLine().trim();

		List<Stop> stops = new SinglyLinkedList<Stop>();
		stops.addLast(new Stop(firstStation, null, firstDepTime));

		for (int i = 1; i < n; i++) {
			if (i == n - 1) {
				stops.addLast(new Stop(in.nextLine().trim(), in.nextLine().trim(), null));
			}else
				stops.addLast(new Stop(in.nextLine().trim(), in.nextLine().trim(), in.nextLine().trim()));
		}
		if(jp.hasID(ID)){
			System.out.printf(Interactions.JOURNEY_EXISTS.getString());
			return;
		}
		jp.addJourney(ID, stops);
		System.out.printf(Interactions.JOURNEY_ADDED.getString());
	}

	/**
	 * Command "findJourney": reads the origin, the destination and the time,
	 * validates them (station, time, order) and writes the earliest journey,
	 * or reports that there is no direct journey.
	 *
	 * Time complexity: best case O(1) (invalid input, or the first departure of the
	 * origin is the answer); worst case O(P*k) (every departure of the origin is
	 * visited and, for each one, the destination is searched among the k stops).
	 *
	 * @param in - Scanner
	 * @param jp - journey Planner
	 */
	private static void findJourney(Scanner in, JourneyPlanner jp) {
		String origin = in.nextLine().trim();
		String destination = in.nextLine().trim();
		String time = in.nextLine().trim();
		if (!jp.hasStation(origin) || !jp.hasStation(destination))
			System.out.printf(Interactions.INVALID_STATION.getString());
		else if (!isValidTime(time)){
			System.out.printf(Interactions.INVALID_TIME.getString());
		}else if (!jp.inOrder(origin, destination))
			System.out.printf(Interactions.WRONG_ORDER.getString());
		else
			printTrip(jp.findJourney(origin, destination, time), false);
	}

	/**
	 * Command "findJourneyMinStops": reads the origin, the destination and the
	 * time interval, validates them (station, time, interval, order) and writes the
	 * journey with the fewest stops, or reports that there is no direct journey.
	 *
	 * Time complexity: best case O(1) (invalid input, or no departures from the
	 * origin); worst case O(P*k) (every departure inside the interval is visited and,
	 * for each one, the destination is searched among the k stops; the search stops
	 * after the end of the interval).
	 *
	 * @param in - Scanner
	 * @param jp - journey Planner
	 */
	private static void findJourneyMinStops(Scanner in, JourneyPlanner jp) {
		String origin = in.nextLine().trim();
		String destination = in.nextLine().trim();
		String[] times = in.nextLine().trim().split(" ");
		if (!jp.hasStation(origin) || !jp.hasStation(destination))
			System.out.printf(Interactions.INVALID_STATION.getString());
		else if (times.length != 2 || !isValidTime(times[0]) || !isValidTime(times[1])){
			System.out.printf(Interactions.INVALID_TIME.getString());
		}else if (times[0].compareTo(times[1]) >= 0)
			System.out.printf(Interactions.INVALID_INTERVAL.getString());
		else if (!jp.inOrder(origin, destination))
			System.out.printf(Interactions.WRONG_ORDER.getString());
		else
			printTrip(jp.findJourneyMinStops(origin, destination, times[0], times[1]), true);
	}

	/**
	 * Checks whether the time has the format HH:mm, with hours 00-23 and
	 * minutes 00-59
	 *
	 * Time complexity: best case O(1); worst case O(1) (the time has 5 characters).
	 *
	 * @param time - time read
	 * @return true if the time is Valid, false otherwise
	 */
	private static boolean isValidTime(String time){
		if(time.length() != 5 || time.charAt(2) != ':')
			return false;
		for (int i = 0; i < 5; i++){
			char c = time.charAt(i);
			if(i != 2 && (c < '0' || c>'9'))
				return false;
		}
		int hours = Integer.parseInt(time.substring(0, 2));
		int minutes = Integer.parseInt(time.substring(3, 5));
		return  hours <= 23 && minutes <= 59;
	}

	/**
	 * Writes the result of a search (journey, stations, times and, if asked, the
	 * number of stops), or "No direct journey is available." if there is none.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @param trip - result of the search (null if there is none)
	 * @param showStops - true to also write the number of stops
	 */
	private static void  printTrip(Trip trip, boolean showStops){
		if(trip == null){
			System.out.printf(Interactions.NO_DIRECT_JOURNEY.getString());
		}else{
			System.out.printf(Interactions.TRIP.getString(), trip.journeyID(), trip.origin(), trip.departure(),
					trip.destination(), trip.arrival());
			if (showStops)
				System.out.printf(Interactions.STOPS.getString(), trip.stops());
		}
	}

	/**
	 * Command "removeJourney": reads the identifier and removes the journey,
	 * or reports that it does not exist.
	 *
	 * Time complexity: best case O(1) (the journey does not exist: one search in the
	 * hash table); worst case O(k*P) (each of the k-1 departures is removed from the
	 * sorted map of its station; on average O(k log P)).
	 *
	 * @param in - Scanner
	 * @param jp - journey planner
	 */
	private static void removeJourney(Scanner in, JourneyPlanner jp) {
		String ID = in.nextLine().trim();

		if (!jp.hasID(ID)) {
			System.out.printf(Interactions.INVALID_JOURNEY.getString());
			return;
		}

		jp.removeJourney(ID);
		System.out.printf(Interactions.JOURNEY_REMOVED.getString());
	}

	/**
	 * Saves (serializes) the network to the file JourneyPlanner.ser.
	 *
	 * Time complexity: best case O(S + J*k); worst case O(S + J*k)
	 * (every station and every journey, with its stops, is written).
	 *
	 * @param jp - journey planner to save
	 */
	private static void serialize(JourneyPlanner jp) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {

			oos.writeObject(jp);
			System.out.printf(Interactions.NETWORK_SAVED.getString());

		} catch (IOException e) {
		}
	}

	/**
	 * Restores (deserializes) the network from the file. If the file does not
	 * exist, it loads the line definition given as argument.
	 *
	 * Time complexity: best case O(1) (there is no file nor argument);
	 * worst case O(S + J*k*P) (every journey is read and its departures are inserted
	 * again in the sorted maps of the stations; on average O(S + J*k log P)).
	 *
	 * @param mainArgs - program arguments
	 * @return restored network or null
	 */
	private static JourneyPlanner deserialize(String[] mainArgs) {
		if (FILE.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {

				JourneyPlanner jp = (JourneyPlanner) ois.readObject();
				System.out.printf(Interactions.NETWORK_RESTORED.getString());
				return jp;

			} catch (IOException | ClassNotFoundException e) {
				return null;
			}
		}

		if (mainArgs.length > 0) {
			return Main.deserializeFromLineDefinition(mainArgs[0]);
		}

		System.out.printf(Interactions.NO_LINE_DEF.getString());
		return null;
	}

	/**
	 * Reads the line definition file (number of stations followed by their
	 * names) and creates a network with those stations.
	 *
	 * Time complexity: best case O(S); worst case O(S)
	 * (each station is added in O(1) expected time)
	 *
	 * @param lineDefinition - name of the line definition file
	 * @return new network, or null if the file cannot be read
	 */
	private static JourneyPlanner deserializeFromLineDefinition(String lineDefinition) {
		try (Scanner file = new Scanner(new File(lineDefinition))) {

			JourneyPlanner jp = new JourneyPlannerClass();
			int numberOfStations = Integer.parseInt(file.nextLine().trim());

			System.out.printf(Interactions.NETWORK_NOT_FOUND.getString(), numberOfStations);

			for (int i = 0; i < numberOfStations; i++) {
				jp.addStation(file.nextLine().trim());
			}
			return jp;

		} catch (IOException e) {
			System.out.printf(Interactions.NO_LINE_DEF.getString());
			return null;
		}

	}
}
