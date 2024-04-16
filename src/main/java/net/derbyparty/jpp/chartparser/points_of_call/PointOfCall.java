package net.derbyparty.jpp.chartparser.points_of_call;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PointOfCall {
	public int point;
	public String text;
	public Integer feet;
	public RelativePosition relativePosition;

    @JsonCreator
    public PointOfCall(
            @JsonProperty("point") int point,
            @JsonProperty("text") String text,
            @JsonProperty("feet") Integer feet) {
        this.point = point;
        this.text = text;
        this.feet = feet;
    }

    public boolean hasKnownDistance() {
        return (feet != null);
    }

    public boolean hasRelativePosition() {
        return getRelativePosition() != null;
    }

    public boolean hasLengths() {
        return getRelativePosition() != null && getRelativePosition().getLengthsAhead() != null;
    }

    public int getPoint() {
        return point;
    }

    public String getText() {
        return text;
    }

    public Integer getFeet() {
        return feet;
    }

    public void setFeet(Integer feet) {
        this.feet = feet;
    }

    public RelativePosition getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(RelativePosition relativePosition) {
        this.relativePosition = relativePosition;
    }

    @Override
    public String toString() {
        return "PointOfCall{" +
                "point=" + point +
                ", text='" + text + '\'' +
                ", feet=" + feet +
                ", relativePosition=" + relativePosition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfCall that = (PointOfCall) o;

        if (point != that.point) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (feet != null ? !feet.equals(that.feet) : that.feet != null) return false;
        return relativePosition != null ? relativePosition.equals(that.relativePosition) : that
                .relativePosition == null;
    }

    @Override
    public int hashCode() {
        int result = point;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (feet != null ? feet.hashCode() : 0);
        result = 31 * result + (relativePosition != null ? relativePosition.hashCode() : 0);
        return result;
    }

    public PointOfCall() {
    	super();
		
		// TODO Auto-generated constructor stub
	}
}
