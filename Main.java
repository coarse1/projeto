import java.util.Scanner; 
import mainEnum.*;
import journeyPlanner.*;
import java.io.*;
import dataStructures.List;
import dataStructures.SinglyLinkedList;

public class Main {
	private static final File FILE = new File("JourneyPlanner.ser");

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

	private static void help() {
		System.out.printf(Interactions.HELP.getString());
	}

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

	private static void findJourneyMinStops(Scanner in, JourneyPlanner jp) {
		String origin = in.nextLine().trim();
		String destination = in.nextLine().trim();
		String[] times = in.nextLine().trim().split("\\s+");
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

	private static void removeJourney(Scanner in, JourneyPlanner jp) {
		String ID = in.nextLine().trim();

		if (!jp.hasID(ID)) {
			System.out.printf(Interactions.INVALID_JOURNEY.getString());
			return;
		}

		jp.removeJourney(ID);
		System.out.printf(Interactions.JOURNEY_REMOVED.getString());
	}

	private static void serialize(JourneyPlanner jp) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {

			oos.writeObject(jp);
			System.out.printf(Interactions.NETWORK_SAVED.getString());

		} catch (IOException e) {
		}
	}

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
