package journeyPlanner;

import java.io.Serializable;

public record Stop(String name, String arrival, String departure) implements Serializable {
}
