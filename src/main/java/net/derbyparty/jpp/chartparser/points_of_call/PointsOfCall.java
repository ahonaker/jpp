package net.derbyparty.jpp.chartparser.points_of_call;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

/**
 * Stores the {@link PointOfCall} instances for a particular race distance
 */
public class PointsOfCall {

	public String distance;
	public int floor;
	public List<PointOfCall> calls;

    @JsonCreator
    public PointsOfCall(
            @JsonProperty("distance") String distance,
            @JsonProperty("floor") int floor,
            @JsonProperty("calls") List<PointOfCall> calls) {
        this.distance = distance;
        this.floor = floor;
        this.calls = calls;
    }

    public String getDistance() {
        return distance;
    }

    public int getFloor() {
        return floor;
    }

    public List<PointOfCall> getCalls() {
        return calls;
    }

    public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public void setCalls(List<PointOfCall> calls) {
		this.calls = calls;
	}

	public Optional<PointOfCall> getFinishPointOfCall() {
        for (PointOfCall pointOfCall : getCalls()) {
            if (pointOfCall.getPoint() == 6) {
                return Optional.of(pointOfCall);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "PointsOfCall{" +
                "distance='" + distance + '\'' +
                ", floor=" + floor +
                ", calls=" + calls +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointsOfCall that = (PointsOfCall) o;

        if (floor != that.floor) return false;
        if (distance != null ? !distance.equals(that.distance) : that.distance != null)
            return false;
        return calls != null ? calls.equals(that.calls) : that.calls == null;

    }

    @Override
    public int hashCode() {
        int result = distance != null ? distance.hashCode() : 0;
        result = 31 * result + floor;
        result = 31 * result + (calls != null ? calls.hashCode() : 0);
        return result;
    }

	public PointsOfCall() {
		super();
		// TODO Auto-generated constructor stub
	}
}
