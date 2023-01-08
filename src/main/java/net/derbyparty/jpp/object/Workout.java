package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.annotation.Generated;

public class Workout implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private LocalDate DateOfWorkout;
	private Float TimeOfWorkout;
	private String TrackOfWorkout;
	private int DistanceOfWorkout;
	private String TrackCondition;
	private String Description;
	private WorkoutTrackType WorkoutTrackIndicator;
	private int NumberOfWorks;
	private int Rank;
	@SuppressWarnings("unused")
	private String dateOfWorkoutString;
	@SuppressWarnings("unused")
	private float furlongs;

	@Generated("SparkTools")
	private Workout(Builder builder) {
		this.DateOfWorkout = builder.DateOfWorkout;
		this.TimeOfWorkout = builder.TimeOfWorkout;
		this.TrackOfWorkout = builder.TrackOfWorkout;
		this.DistanceOfWorkout = builder.DistanceOfWorkout;
		this.TrackCondition = builder.TrackCondition;
		this.Description = builder.Description;
		this.WorkoutTrackIndicator = builder.WorkoutTrackIndicator;
		this.NumberOfWorks = builder.NumberOfWorks;
		this.Rank = builder.Rank;
	}

	public LocalDate getDateOfWorkout() {
		return DateOfWorkout;
	}
	public void setDateOfWorkout(LocalDate dateOfWorkout) {
		DateOfWorkout = dateOfWorkout;
	}
	public String getDateOfWorkoutString() {
		return DateOfWorkout.getDayOfMonth() +
				DateOfWorkout.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) +
				(DateOfWorkout.getYear()-2000);
	}
	public Float getTimeOfWorkout() {
		return TimeOfWorkout;
	}
	public void setTimeOfWorkout(Float timeOfWorkout) {
		TimeOfWorkout = timeOfWorkout;
	}
	public String getTrackOfWorkout() {
		return TrackOfWorkout;
	}
	public void setTrackOfWorkout(String trackOfWorkout) {
		TrackOfWorkout = trackOfWorkout;
	}
	public int getDistanceOfWorkout() {
		return DistanceOfWorkout;
	}
	public void setDistanceOfWorkout(int distanceOfWorkout) {
		DistanceOfWorkout = distanceOfWorkout;
	}	
	public String getTrackCondition() {
		return TrackCondition;
	}
	public void setTrackCondition(String trackCondition) {
		TrackCondition = trackCondition;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public WorkoutTrackType getTrackIndicator() {
		return WorkoutTrackIndicator;
	}
	public void setTrackIndicator(WorkoutTrackType workoutTrackIndicator) {
		WorkoutTrackIndicator = workoutTrackIndicator;
	}
	public int getNumberOfWorks() {
		return NumberOfWorks;
	}
	public void setNumberOfWorks(int numberOfWorks) {
		NumberOfWorks = numberOfWorks;
	}
	public int getRank() {
		return Rank;
	}
	public void setRank(int rank) {
		Rank = rank;
	}
	public float getFurlongs () {
		return ((float) DistanceOfWorkout / 220);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DateOfWorkout == null) ? 0 : DateOfWorkout.hashCode());
		result = prime * result + ((Description == null) ? 0 : Description.hashCode());
		result = prime * result + DistanceOfWorkout;
		result = prime * result + NumberOfWorks;
		result = prime * result + Rank;
		result = prime * result + ((TimeOfWorkout == null) ? 0 : TimeOfWorkout.hashCode());
		result = prime * result + ((TrackCondition == null) ? 0 : TrackCondition.hashCode());
		result = prime * result + ((TrackOfWorkout == null) ? 0 : TrackOfWorkout.hashCode());
		result = prime * result + ((WorkoutTrackIndicator == null) ? 0 : WorkoutTrackIndicator.hashCode());
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
		Workout other = (Workout) obj;
		if (DateOfWorkout == null) {
			if (other.DateOfWorkout != null)
				return false;
		} else if (!DateOfWorkout.equals(other.DateOfWorkout))
			return false;
		if (Description == null) {
			if (other.Description != null)
				return false;
		} else if (!Description.equals(other.Description))
			return false;
		if (DistanceOfWorkout != other.DistanceOfWorkout)
			return false;
		if (NumberOfWorks != other.NumberOfWorks)
			return false;
		if (Rank != other.Rank)
			return false;
		if (TimeOfWorkout == null) {
			if (other.TimeOfWorkout != null)
				return false;
		} else if (!TimeOfWorkout.equals(other.TimeOfWorkout))
			return false;
		if (TrackCondition == null) {
			if (other.TrackCondition != null)
				return false;
		} else if (!TrackCondition.equals(other.TrackCondition))
			return false;
		if (TrackOfWorkout == null) {
			if (other.TrackOfWorkout != null)
				return false;
		} else if (!TrackOfWorkout.equals(other.TrackOfWorkout))
			return false;
		if (WorkoutTrackIndicator != other.WorkoutTrackIndicator)
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Workout [DateOfWorkout=").append(DateOfWorkout).append(", TimeOfWorkout=")
				.append(TimeOfWorkout).append(", TrackOfWorkout=").append(TrackOfWorkout).append(", DistanceOfWorkout=")
				.append(DistanceOfWorkout).append(", TrackCondition=").append(TrackCondition).append(", Description=")
				.append(Description).append(", WorkoutTrack=").append(WorkoutTrackIndicator).append(", NumberOfWorks=")
				.append(NumberOfWorks).append(", Rank=").append(Rank).append("]");
		return builder2.toString();
	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	@Generated("SparkTools")
	public static final class Builder {
		private LocalDate DateOfWorkout;
		private Float TimeOfWorkout;
		private String TrackOfWorkout;
		private int DistanceOfWorkout;
		private String TrackCondition;
		private String Description;
		private WorkoutTrackType WorkoutTrackIndicator;
		private int NumberOfWorks;
		private int Rank;

		private Builder() {
		}

		public Builder withDateOfWorkout(LocalDate DateOfWorkout) {
			this.DateOfWorkout = DateOfWorkout;
			return this;
		}

		public Builder withTimeOfWorkout(Float TimeOfWorkout) {
			this.TimeOfWorkout = TimeOfWorkout;
			return this;
		}

		public Builder withTrackOfWorkout(String TrackOfWorkout) {
			this.TrackOfWorkout = TrackOfWorkout;
			return this;
		}

		public Builder withDistanceOfWorkout(int DistanceOfWorkout) {
			this.DistanceOfWorkout = DistanceOfWorkout;
			return this;
		}

		public Builder withTrackCondition(String TrackCondition) {
			this.TrackCondition = TrackCondition;
			return this;
		}

		public Builder withDescription(String Description) {
			this.Description = Description;
			return this;
		}

		public Builder withWorkoutTrackIndicator(WorkoutTrackType WorkoutTrackIndicator) {
			this.WorkoutTrackIndicator = WorkoutTrackIndicator;
			return this;
		}

		public Builder withNumberOfWorks(int NumberOfWorks) {
			this.NumberOfWorks = NumberOfWorks;
			return this;
		}

		public Builder withRank(int Rank) {
			this.Rank = Rank;
			return this;
		}

		public Workout build() {
			return new Workout(this);
		}
	}
	
	public Workout() {
		super();
	}

	
}
