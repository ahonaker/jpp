package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import java.util.Collections;

public class Trainer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String Name;
	
	private int CurrentMeetStarts;
	private int CurrentMeetWins;
	private int CurrentMeetPlaces;
	private int CurrentMeetShows;
	
	private int CurrentYearStarts;
	private int CurrentYearWins;
	private int CurrentYearPlaces;
	private int CurrentYearShows;
	private float CurrentYearROI;
	
	private int PreviousYearStarts;
	private int PreviousYearWins;
	private int PreviousYearPlaces;
	private int PreviousYearShows;
	private float PreviousYearROI;
	
	private List<Stat> TrainerStats;

	@Generated("SparkTools")
	private Trainer(Builder builder) {
		this.Name = builder.Name;
		this.CurrentMeetStarts = builder.CurrentMeetStarts;
		this.CurrentMeetWins = builder.CurrentMeetWins;
		this.CurrentMeetPlaces = builder.CurrentMeetPlaces;
		this.CurrentMeetShows = builder.CurrentMeetShows;
		this.CurrentYearStarts = builder.CurrentYearStarts;
		this.CurrentYearWins = builder.CurrentYearWins;
		this.CurrentYearPlaces = builder.CurrentYearPlaces;
		this.CurrentYearShows = builder.CurrentYearShows;
		this.CurrentYearROI = builder.CurrentYearROI;
		this.PreviousYearStarts = builder.PreviousYearStarts;
		this.PreviousYearWins = builder.PreviousYearWins;
		this.PreviousYearPlaces = builder.PreviousYearPlaces;
		this.PreviousYearShows = builder.PreviousYearShows;
		this.PreviousYearROI = builder.PreviousYearROI;
		this.TrainerStats = builder.TrainerStats;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getCurrentMeetStarts() {
		return CurrentMeetStarts;
	}

	public void setCurrentMeetStarts(int currentMeetStarts) {
		CurrentMeetStarts = currentMeetStarts;
	}

	public int getCurrentMeetWins() {
		return CurrentMeetWins;
	}

	public void setCurrentMeetWins(int currentMeetWins) {
		CurrentMeetWins = currentMeetWins;
	}

	public int getCurrentMeetPlaces() {
		return CurrentMeetPlaces;
	}

	public void setCurrentMeetPlaces(int currentMeetPlaces) {
		CurrentMeetPlaces = currentMeetPlaces;
	}

	public int getCurrentMeetShows() {
		return CurrentMeetShows;
	}

	public void setCurrentMeetShows(int currentMeetShows) {
		CurrentMeetShows = currentMeetShows;
	}

	public int getCurrentYearStarts() {
		return CurrentYearStarts;
	}

	public void setCurrentYearStarts(int currentYearStarts) {
		CurrentYearStarts = currentYearStarts;
	}

	public int getCurrentYearWins() {
		return CurrentYearWins;
	}

	public void setCurrentYearWins(int currentYearWins) {
		CurrentYearWins = currentYearWins;
	}

	public int getCurrentYearPlaces() {
		return CurrentYearPlaces;
	}

	public void setCurrentYearPlaces(int currentYearPlaces) {
		CurrentYearPlaces = currentYearPlaces;
	}

	public int getCurrentYearShows() {
		return CurrentYearShows;
	}

	public void setCurrentYearShows(int currentYearShows) {
		CurrentYearShows = currentYearShows;
	}

	public float getCurrentYearROI() {
		return CurrentYearROI;
	}

	public void setCurrentYearROI(float currentYearROI) {
		CurrentYearROI = currentYearROI;
	}

	public int getPreviousYearStarts() {
		return PreviousYearStarts;
	}

	public void setPreviousYearStarts(int previousYearStarts) {
		PreviousYearStarts = previousYearStarts;
	}

	public int getPreviousYearWins() {
		return PreviousYearWins;
	}

	public void setPreviousYearWins(int previousYearWins) {
		PreviousYearWins = previousYearWins;
	}

	public int getPreviousYearPlaces() {
		return PreviousYearPlaces;
	}

	public void setPreviousYearPlaces(int previousYearPlaces) {
		PreviousYearPlaces = previousYearPlaces;
	}

	public int getPreviousYearShows() {
		return PreviousYearShows;
	}

	public void setPreviousYearShows(int previousYearShows) {
		PreviousYearShows = previousYearShows;
	}

	public float getPreviousYearROI() {
		return PreviousYearROI;
	}

	public void setPreviousYearROI(float previousYearROI) {
		PreviousYearROI = previousYearROI;
	}

	public List<Stat> getTrainerStats() {
		return TrainerStats;
	}

	public void setTrainerStats(List<Stat> trainerStats) {
		TrainerStats = trainerStats;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
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
		Trainer other = (Trainer) obj;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Trainer [Name=").append(Name).append(", CurrentMeetStarts=").append(CurrentMeetStarts)
				.append(", CurrentMeetWins=").append(CurrentMeetWins).append(", CurrentMeetPlaces=")
				.append(CurrentMeetPlaces).append(", CurrentMeetShows=").append(CurrentMeetShows)
				.append(", CurrentYearStarts=").append(CurrentYearStarts).append(", CurrentYearWins=")
				.append(CurrentYearWins).append(", CurrentYearPlaces=").append(CurrentYearPlaces)
				.append(", CurrentYearShows=").append(CurrentYearShows).append(", CurrentYearROI=")
				.append(CurrentYearROI).append(", PreviousYearStarts=").append(PreviousYearStarts)
				.append(", PreviousYearWins=").append(PreviousYearWins).append(", PreviousYearPlaces=")
				.append(PreviousYearPlaces).append(", PreviousYearShows=").append(PreviousYearShows)
				.append(", PreviousYearROI=").append(PreviousYearROI).append(", TrainerStats=").append(TrainerStats)
				.append("]");
		return builder.toString();
	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private String Name;
		private int CurrentMeetStarts;
		private int CurrentMeetWins;
		private int CurrentMeetPlaces;
		private int CurrentMeetShows;
		private int CurrentYearStarts;
		private int CurrentYearWins;
		private int CurrentYearPlaces;
		private int CurrentYearShows;
		private float CurrentYearROI;
		private int PreviousYearStarts;
		private int PreviousYearWins;
		private int PreviousYearPlaces;
		private int PreviousYearShows;
		private float PreviousYearROI;
		private List<Stat> TrainerStats = Collections.emptyList();

		private Builder() {
		}

		public Builder withName(String Name) {
			this.Name = Name;
			return this;
		}

		public Builder withCurrentMeetStarts(int CurrentMeetStarts) {
			this.CurrentMeetStarts = CurrentMeetStarts;
			return this;
		}

		public Builder withCurrentMeetWins(int CurrentMeetWins) {
			this.CurrentMeetWins = CurrentMeetWins;
			return this;
		}

		public Builder withCurrentMeetPlaces(int CurrentMeetPlaces) {
			this.CurrentMeetPlaces = CurrentMeetPlaces;
			return this;
		}

		public Builder withCurrentMeetShows(int CurrentMeetShows) {
			this.CurrentMeetShows = CurrentMeetShows;
			return this;
		}

		public Builder withCurrentYearStarts(int CurrentYearStarts) {
			this.CurrentYearStarts = CurrentYearStarts;
			return this;
		}

		public Builder withCurrentYearWins(int CurrentYearWins) {
			this.CurrentYearWins = CurrentYearWins;
			return this;
		}

		public Builder withCurrentYearPlaces(int CurrentYearPlaces) {
			this.CurrentYearPlaces = CurrentYearPlaces;
			return this;
		}

		public Builder withCurrentYearShows(int CurrentYearShows) {
			this.CurrentYearShows = CurrentYearShows;
			return this;
		}

		public Builder withCurrentYearROI(float CurrentYearROI) {
			this.CurrentYearROI = CurrentYearROI;
			return this;
		}

		public Builder withPreviousYearStarts(int PreviousYearStarts) {
			this.PreviousYearStarts = PreviousYearStarts;
			return this;
		}

		public Builder withPreviousYearWins(int PreviousYearWins) {
			this.PreviousYearWins = PreviousYearWins;
			return this;
		}

		public Builder withPreviousYearPlaces(int PreviousYearPlaces) {
			this.PreviousYearPlaces = PreviousYearPlaces;
			return this;
		}

		public Builder withPreviousYearShows(int PreviousYearShows) {
			this.PreviousYearShows = PreviousYearShows;
			return this;
		}

		public Builder withPreviousYearROI(float PreviousYearROI) {
			this.PreviousYearROI = PreviousYearROI;
			return this;
		}

		public Builder withTrainerStats(List<Stat> TrainerStats) {
			this.TrainerStats = TrainerStats;
			return this;
		}

		public Trainer build() {
			return new Trainer(this);
		}
	}

	public Trainer() {
		super();
	}
	

}
