package journeyPlanner;

import java.io.Serializable;

public record Stop(String name, String arrival, String departure) implements Serializable {
	private static final long serialVersionUID = 1L;
}
