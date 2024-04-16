package net.derbyparty.jpp.chartparser.points_of_call;

public class TotalLengthsBehind {
    public String text;
    public Double lengths;
	

    public TotalLengthsBehind(String text, Double lengths) {
		super();
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
    public String toString() {
        return "TotalLengthsBehind{" +
                "text='" + text + '\'' +
                ", lengthsAhead=" + lengths +
                '}';
    }

	public TotalLengthsBehind() {
		super();
		// TODO Auto-generated constructor stub
	}
}

