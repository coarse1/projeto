package journeyPlanner;

import java.io.Serializable;

public record DepartureKey(String time, int order) implements Comparable<DepartureKey>, Serializable {

	@Override
	public int compareTo(DepartureKey other) {
		int cmp = time.compareTo(other.time());
		if (cmp != 0)
			return cmp;
		return Integer.compare(order, other.order());
	}
}
