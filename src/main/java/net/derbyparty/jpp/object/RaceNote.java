package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.time.LocalDate;
import javax.annotation.Generated;

public class RaceNote implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String track;
	private LocalDate raceDate;
	private int raceNumber;
	private int position;
	private float beatenLengths;
	private String comment;
	private String flag;

	@Generated("SparkTools")
	private RaceNote(Builder builder) {
		this.track = builder.track;
		this.raceDate = builder.raceDate;
		this.raceNumber = builder.raceNumber;
		this.position = builder.position;
		this.beatenLengths = builder.beatenLengths;
		this.comment = builder.comment;
		this.flag = builder.flag;
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
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public float getBeatenLengths() {
		return beatenLengths;
	}
	public void setBeatenLengths(float beatenLengths) {
		this.beatenLengths = beatenLengths;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(beatenLengths);
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
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
		RaceNote other = (RaceNote) obj;
		if (Float.floatToIntBits(beatenLengths) != Float.floatToIntBits(other.beatenLengths))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
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
		StringBuilder builder = new StringBuilder();
		builder.append("RaceNote [track=").append(track).append(", raceDate=").append(raceDate).append(", raceNumber=")
				.append(raceNumber).append(", position=").append(position).append(", beatenLengths=")
				.append(beatenLengths).append(", comment=").append(comment).append("]");
		return builder.toString();
	}
	public RaceNote(String track, LocalDate raceDate, int raceNumber, int position, int beatenLengths, String comment) {
		super();
		this.track = track;
		this.raceDate = raceDate;
		this.raceNumber = raceNumber;
		this.position = position;
		this.beatenLengths = beatenLengths;
		this.comment = comment;
	}
	
	public RaceNote() {
		
	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private String track;
		private LocalDate raceDate;
		private int raceNumber;
		private int position;
		private float beatenLengths;
		private String comment;
		private String flag;

		private Builder() {
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

		public Builder withPosition(int position) {
			this.position = position;
			return this;
		}

		public Builder withBeatenLengths(float beatenLengths) {
			this.beatenLengths = beatenLengths;
			return this;
		}

		public Builder withComment(String comment) {
			this.comment = comment;
			return this;
		}

		public Builder withFlag(String flag) {
			this.flag = flag;
			return this;
		}

		public RaceNote build() {
			return new RaceNote(this);
		}
	}
}
