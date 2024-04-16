package net.derbyparty.jpp.chartparser.points_of_call;

public class Lengths {
    public String text;
    public Double lengths;

    public Lengths(String text, Double lengths) {
        this.text = text;
        this.lengths = lengths;
    }

    public String getText() {
        return text;
    }

    public Double getLengths() {
        return lengths;
    }

    public void setText(String text) {
		this.text = text;
	}

	public void setLengths(Double lengths) {
		this.lengths = lengths;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lengths lengths1 = (Lengths) o;

        if (text != null ? !text.equals(lengths1.text) : lengths1.text != null)
            return false;
        return lengths != null ? lengths.equals(lengths1.lengths) : lengths1.lengths
                == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (lengths != null ? lengths.hashCode() : 0);
        return result;
    }

	public Lengths() {
		super();
		// TODO Auto-generated constructor stub
	}
}
