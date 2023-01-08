package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.time.LocalDate;
import javax.annotation.Generated;

public class PotentialKeyRaceHorse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private int position;
	private double beatenLengths;
	private String track;
	private LocalDate raceDate;
	private int raceNumber;

	@Generated("SparkTools")
	private PotentialKeyRaceHorse(Builder builder) {
		this.name = builder.name;
		this.position = builder.position;
		this.beatenLengths = builder.beatenLengths;
		this.track = builder.track;
		this.raceDate = builder.raceDate;
		this.raceNumber = builder.raceNumber;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public double getBeatenLengths() {
		return beatenLengths;
	}
	public void setBeatenLengths(float beatenLengths) {
		this.beatenLengths = beatenLengths;
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public LocalDate getRaceDate() {
		return raceDate;
	}
	public void setRaceDate(LocalDate raceDate) {
		this.raceDate = raceDate;
	}
	public int getRaceNumber() {
		return raceNumber;
	}
	public void setRaceNumber(int raceNumber) {
		this.raceNumber = raceNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(beatenLengths);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + position;
		result = prime * result + ((raceDate == null) ? 0 : raceDate.hashCode());
		result = prime * result + raceNumber;
		result = prime * result + ((track == null) ? 0 : track.hashCode());
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
		PotentialKeyRaceHorse other = (PotentialKeyRaceHorse) obj;
		if (Double.doubleToLongBits(beatenLengths) != Double.doubleToLongBits(other.beatenLengths))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (position != other.position)
			return false;
		if (raceDate == null) {
			if (other.raceDate != null)
				return false;
		} else if (!raceDate.equals(other.raceDate))
			return false;
		if (raceNumber != other.raceNumber)
			return false;
		if (track == null) {
			if (other.track != null)
				return false;
		} else if (!track.equals(other.track))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("PotentialKeyRaceHorse [name=").append(name).append(", position=").append(position)
				.append(", beatenLengths=").append(beatenLengths).append(", track=").append(track).append(", raceDate=")
				.append(raceDate).append(", raceNumber=").append(raceNumber).append("]");
		return builder2.toString();
	}

	public PotentialKeyRaceHorse(String name, int position, float beatenLengths, String track, LocalDate raceDate,
			int raceNumber) {
		super();
		this.name = name;
		this.position = position;
		this.beatenLengths = beatenLengths;
		this.track = track;
		this.raceDate = raceDate;
		this.raceNumber = raceNumber;
	}

	public PotentialKeyRaceHorse() {
		
	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private String name;
		private int position;
		private double beatenLengths;
		private String track;
		private LocalDate raceDate;
		private int raceNumber;

		private Builder() {
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withPosition(int position) {
			this.position = position;
			return this;
		}

		public Builder withBeatenLengths(double beatenLengths) {
			this.beatenLengths = beatenLengths;
			return this;
		}

		public Builder withTrack(String track) {
			this.track = track;
			return this;
		}

		public Builder withRaceDate(LocalDate raceDate) {
			this.raceDate = raceDate;
			return this;
		}

		public Builder withRaceNumber(int raceNumber) {
			this.raceNumber = raceNumber;
			return this;
		}

		public PotentialKeyRaceHorse build() {
			return new PotentialKeyRaceHorse(this);
		}
	}
	
}
