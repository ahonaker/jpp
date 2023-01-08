package net.derbyparty.jpp.object;

import java.io.Serializable;
import javax.annotation.Generated;

public class TrackBias implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int numberOfRaces;
	private String dates;
	private int speedBias;
	private int wirePercent;
	private float winnerABLFirstCall;
	private float winnerABLSecondCall;
	private int percentEWon;
	private int percentEPWon;
	private int percentPWon;
	private int percentSWon;
	private int percentRailWon;
	private int percent123Won;
	private int percent4567Won;
	private int percent8PlusWon;
	@Generated("SparkTools")
	private TrackBias(Builder builder) {
		this.numberOfRaces = builder.numberOfRaces;
		this.dates = builder.dates;
		this.speedBias = builder.speedBias;
		this.wirePercent = builder.wirePercent;
		this.winnerABLFirstCall = builder.winnerABLFirstCall;
		this.winnerABLSecondCall = builder.winnerABLSecondCall;
		this.percentEWon = builder.percentEWon;
		this.percentEPWon = builder.percentEPWon;
		this.percentPWon = builder.percentPWon;
		this.percentSWon = builder.percentSWon;
		this.percentRailWon = builder.percentRailWon;
		this.percent123Won = builder.percent123Won;
		this.percent4567Won = builder.percent4567Won;
		this.percent8PlusWon = builder.percent8PlusWon;
	}
	public int getNumberOfRaces() {
		return numberOfRaces;
	}
	public void setNumberOfRaces(int numberOfRaces) {
		this.numberOfRaces = numberOfRaces;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public int getSpeedBias() {
		return speedBias;
	}
	public void setSpeedBias(int speedBias) {
		this.speedBias = speedBias;
	}
	public int getWirePercent() {
		return wirePercent;
	}
	public void setWirePercent(int wirePercent) {
		this.wirePercent = wirePercent;
	}
	public float getWinnerABLFirstCall() {
		return winnerABLFirstCall;
	}
	public void setWinnerABLFirstCall(float winnerABLFirstCall) {
		this.winnerABLFirstCall = winnerABLFirstCall;
	}
	public float getWinnerABLSecondCall() {
		return winnerABLSecondCall;
	}
	public void setWinnerABLSecondCall(float winnerABLSecondCall) {
		this.winnerABLSecondCall = winnerABLSecondCall;
	}
	public int getPercentEWon() {
		return percentEWon;
	}
	public void setPercentEWon(int percentEWon) {
		this.percentEWon = percentEWon;
	}
	public int getPercentEPWon() {
		return percentEPWon;
	}
	public void setPercentEPWon(int percentEPWon) {
		this.percentEPWon = percentEPWon;
	}
	public int getPercentPWon() {
		return percentPWon;
	}
	public void setPercentPWon(int percentPWon) {
		this.percentPWon = percentPWon;
	}
	public int getPercentSWon() {
		return percentSWon;
	}
	public void setPercentSWon(int percentSWon) {
		this.percentSWon = percentSWon;
	}
	public int getPercentRailWon() {
		return percentRailWon;
	}
	public void setPercentRailWon(int percentRailWon) {
		this.percentRailWon = percentRailWon;
	}
	public int getPercent123Won() {
		return percent123Won;
	}
	public void setPercent123Won(int percent123Won) {
		this.percent123Won = percent123Won;
	}
	public int getPercent4567Won() {
		return percent4567Won;
	}
	public void setPercent4567Won(int percent4567Won) {
		this.percent4567Won = percent4567Won;
	}
	public int getPercent8PlusWon() {
		return percent8PlusWon;
	}
	public void setPercent8PlusWon(int percent8PlusWon) {
		this.percent8PlusWon = percent8PlusWon;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dates == null) ? 0 : dates.hashCode());
		result = prime * result + numberOfRaces;
		result = prime * result + percent123Won;
		result = prime * result + percent4567Won;
		result = prime * result + percent8PlusWon;
		result = prime * result + percentEPWon;
		result = prime * result + percentEWon;
		result = prime * result + percentPWon;
		result = prime * result + percentRailWon;
		result = prime * result + percentSWon;
		result = prime * result + speedBias;
		result = prime * result + Float.floatToIntBits(winnerABLFirstCall);
		result = prime * result + Float.floatToIntBits(winnerABLSecondCall);
		result = prime * result + wirePercent;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrackBias other = (TrackBias) obj;
		if (dates == null) {
			if (other.dates != null)
				return false;
		} else if (!dates.equals(other.dates))
			return false;
		if (numberOfRaces != other.numberOfRaces)
			return false;
		if (percent123Won != other.percent123Won)
			return false;
		if (percent4567Won != other.percent4567Won)
			return false;
		if (percent8PlusWon != other.percent8PlusWon)
			return false;
		if (percentEPWon != other.percentEPWon)
			return false;
		if (percentEWon != other.percentEWon)
			return false;
		if (percentPWon != other.percentPWon)
			return false;
		if (percentRailWon != other.percentRailWon)
			return false;
		if (percentSWon != other.percentSWon)
			return false;
		if (speedBias != other.speedBias)
			return false;
		if (Float.floatToIntBits(winnerABLFirstCall) != Float.floatToIntBits(other.winnerABLFirstCall))
			return false;
		if (Float.floatToIntBits(winnerABLSecondCall) != Float.floatToIntBits(other.winnerABLSecondCall))
			return false;
		if (wirePercent != other.wirePercent)
			return false;
		return true;
	}
	public TrackBias(int numberOfRaces, String dates, int speedBias, int wirePercent, float winnerABLFirstCall,
			float winnerABLSecondCall, int percentEWon, int percentEPWon, int percentPWon, int percentSWon,
			int percentRailWon, int percent123Won, int percent4567Won, int percent8PlusWon) {
		super();
		this.numberOfRaces = numberOfRaces;
		this.dates = dates;
		this.speedBias = speedBias;
		this.wirePercent = wirePercent;
		this.winnerABLFirstCall = winnerABLFirstCall;
		this.winnerABLSecondCall = winnerABLSecondCall;
		this.percentEWon = percentEWon;
		this.percentEPWon = percentEPWon;
		this.percentPWon = percentPWon;
		this.percentSWon = percentSWon;
		this.percentRailWon = percentRailWon;
		this.percent123Won = percent123Won;
		this.percent4567Won = percent4567Won;
		this.percent8PlusWon = percent8PlusWon;
	}
	public TrackBias() {

	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	@Generated("SparkTools")
	public static final class Builder {
		private int numberOfRaces;
		private String dates;
		private int speedBias;
		private int wirePercent;
		private float winnerABLFirstCall;
		private float winnerABLSecondCall;
		private int percentEWon;
		private int percentEPWon;
		private int percentPWon;
		private int percentSWon;
		private int percentRailWon;
		private int percent123Won;
		private int percent4567Won;
		private int percent8PlusWon;

		private Builder() {
		}

		public Builder withNumberOfRaces(int numberOfRaces) {
			this.numberOfRaces = numberOfRaces;
			return this;
		}

		public Builder withDates(String dates) {
			this.dates = dates;
			return this;
		}

		public Builder withSpeedBias(int speedBias) {
			this.speedBias = speedBias;
			return this;
		}

		public Builder withWirePercent(int wirePercent) {
			this.wirePercent = wirePercent;
			return this;
		}

		public Builder withWinnerABLFirstCall(float winnerABLFirstCall) {
			this.winnerABLFirstCall = winnerABLFirstCall;
			return this;
		}

		public Builder withWinnerABLSecondCall(float winnerABLSecondCall) {
			this.winnerABLSecondCall = winnerABLSecondCall;
			return this;
		}

		public Builder withPercentEWon(int percentEWon) {
			this.percentEWon = percentEWon;
			return this;
		}

		public Builder withPercentEPWon(int percentEPWon) {
			this.percentEPWon = percentEPWon;
			return this;
		}

		public Builder withPercentPWon(int percentPWon) {
			this.percentPWon = percentPWon;
			return this;
		}

		public Builder withPercentSWon(int percentSWon) {
			this.percentSWon = percentSWon;
			return this;
		}

		public Builder withPercentRailWon(int percentRailWon) {
			this.percentRailWon = percentRailWon;
			return this;
		}

		public Builder withPercent123Won(int percent123Won) {
			this.percent123Won = percent123Won;
			return this;
		}

		public Builder withPercent4567Won(int percent4567Won) {
			this.percent4567Won = percent4567Won;
			return this;
		}

		public Builder withPercent8PlusWon(int percent8PlusWon) {
			this.percent8PlusWon = percent8PlusWon;
			return this;
		}

		public TrackBias build() {
			return new TrackBias(this);
		}
	}
	
	
	

}
