package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Generated;
import java.util.Collections;

public class PotentialKeyRace implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LocalDate raceDate;
	private String track;
	private int raceNumber;
	
	private List<PotentialKeyRaceHorse> horses;

	@Generated("SparkTools")
	private PotentialKeyRace(Builder builder) {
		this.raceDate = builder.raceDate;
		this.track = builder.track;
		this.raceNumber = builder.raceNumber;
		this.horses = builder.horses;
	}

	public LocalDate getRaceDate() {
		return raceDate;
	}

	public void setRaceDate(LocalDate raceDate) {
		this.raceDate = raceDate;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public int getRaceNumber() {
		return raceNumber;
	}

	public void setRaceNumber(int raceNumber) {
		this.raceNumber = raceNumber;
	}

	public List<PotentialKeyRaceHorse> getHorses() {
		return horses;
	}

	public void setHorses(List<PotentialKeyRaceHorse> horses) {
		this.horses = horses;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PotentialKeyRace [raceDate=").append(raceDate).append(", track=").append(track)
				.append(", raceNumber=").append(raceNumber).append(", horses=").append(horses).append("]");
		return builder.toString();
	}

	public PotentialKeyRace(LocalDate raceDate, String track, int raceNumber, List<PotentialKeyRaceHorse> horses) {
		super();
		this.raceDate = raceDate;
		this.track = track;
		this.raceNumber = raceNumber;
		this.horses = horses;
	}
	

	public PotentialKeyRace() {

	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private LocalDate raceDate;
		private String track;
		private int raceNumber;
		private List<PotentialKeyRaceHorse> horses = Collections.emptyList();

		private Builder() {
		}

		public Builder withRaceDate(LocalDate raceDate) {
			this.raceDate = raceDate;
			return this;
		}

		public Builder withTrack(String track) {
			this.track = track;
			return this;
		}

		public Builder withRaceNumber(int raceNumber) {
			this.raceNumber = raceNumber;
			return this;
		}

		public Builder withHorses(List<PotentialKeyRaceHorse> horses) {
			this.horses = horses;
			return this;
		}

		public PotentialKeyRace build() {
			return new PotentialKeyRace(this);
		}
	}

}
