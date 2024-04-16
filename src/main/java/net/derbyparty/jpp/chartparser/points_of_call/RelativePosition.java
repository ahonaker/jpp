package net.derbyparty.jpp.chartparser.points_of_call;

public class RelativePosition {
	public Integer position;
    public LengthsAhead lengthsAhead;
    public TotalLengthsBehind totalLengthsBehind;

    public RelativePosition(Integer position, LengthsAhead lengthsAhead) {
        this.position = position;
        this.lengthsAhead = lengthsAhead;
    }

    public Integer getPosition() {
        return position;
    }

    public LengthsAhead getLengthsAhead() {
        return lengthsAhead;
    }

    public void setPosition(Integer position) {
		this.position = position;
	}

	public void setLengthsAhead(LengthsAhead lengthsAhead) {
		this.lengthsAhead = lengthsAhead;
	}

	public TotalLengthsBehind getTotalLengthsBehind() {
        return totalLengthsBehind;
    }

    public void setTotalLengthsBehind(TotalLengthsBehind totalLengthsBehind) {
        this.totalLengthsBehind = totalLengthsBehind;
    }

    @Override
    public String toString() {
        return "RelativePosition{" +
                "position=" + position +
                ", lengthsAhead=" + lengthsAhead +
                ", totalLengthsBehind=" + totalLengthsBehind +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelativePosition that = (RelativePosition) o;

        if (position != null ? !position.equals(that.position) : that.position != null)
            return false;
        if (lengthsAhead != null ? !lengthsAhead.equals(that.lengthsAhead) : that
                .lengthsAhead != null)
            return false;
        return totalLengthsBehind != null ? totalLengthsBehind.equals(that
                .totalLengthsBehind) : that.totalLengthsBehind == null;
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (lengthsAhead != null ? lengthsAhead.hashCode() : 0);
        result = 31 * result + (totalLengthsBehind != null ? totalLengthsBehind.hashCode
                () : 0);
        return result;
    }

    public RelativePosition() {
    	super();
		
		// TODO Auto-generated constructor stub
	}
}