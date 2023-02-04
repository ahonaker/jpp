package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.time.LocalDate;
import javax.annotation.Generated;

public class RaceNote implements Serializable, Comparable<RaceNote> {

	private static final long serialVersionUID = 1L;
	
	private String track;
	private LocalDate raceDate;
	private int raceNumber;
	private String type;
	private String raceClassification;
	private int purse;
	private int claimingPrice;
	private int distance;
	private Boolean exactDistance;
	private String surface;
	private Boolean offTurf;
	private String trackCondition;
	private int position;
	private float beatenLengths;
	private String comment;
	private String footnote;
	private String flag;

	@Generated("SparkTools")
	private RaceNote(Builder builder) {
		this.track = builder.track;
		this.raceDate = builder.raceDate;
		this.raceNumber = builder.raceNumber;
		this.type = builder.type;
		this.raceClassification = builder.raceClassification;
		this.purse = builder.purse;
		this.claimingPrice = builder.claimingPrice;
		this.distance = builder.distance;
		this.exactDistance = builder.exactDistance;
		this.surface = builder.surface;
		this.offTurf = builder.offTurf;
		this.trackCondition = builder.trackCondition;
		this.position = builder.position;
		this.beatenLengths = builder.beatenLengths;
		this.comment = builder.comment;
		this.footnote = builder.footnote;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRaceClassification() {
		return raceClassification;
	}
	public void setRaceClassification(String raceClassification) {
		this.raceClassification = raceClassification;
	}
	public int getPurse() {
		return purse;
	}
	public void setPurse(int purse) {
		this.purse = purse;
	}
	public int getClaimingPrice() {
		return claimingPrice;
	}
	public void setClaimingPrice(int claimingPrice) {
		this.claimingPrice = claimingPrice;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public Boolean getExactDistance() {
		return exactDistance;
	}

	public void setExactDistance(Boolean exactDistance) {
		this.exactDistance = exactDistance;
	}

	public String getSurface() {
		return surface;
	}
	public void setSurface(String surface) {
		this.surface = surface;
	}
	public Boolean getOffTurf() {
		return offTurf;
	}
	public void setOffTurf(Boolean offTurf) {
		this.offTurf = offTurf;
	}
	public String getTrackCondition() {
		return trackCondition;
	}
	public void setTrackCondition(String trackCondition) {
		this.trackCondition = trackCondition;
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
	public String getFootnote() {
		return footnote;
	}
	public void setFootnote(String footnote) {
		this.footnote = footnote;
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
		result = prime * result + claimingPrice;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + distance;
		result = prime * result + ((exactDistance == null) ? 0 : exactDistance.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((footnote == null) ? 0 : footnote.hashCode());
		result = prime * result + ((offTurf == null) ? 0 : offTurf.hashCode());
		result = prime * result + position;
		result = prime * result + purse;
		result = prime * result + ((raceClassification == null) ? 0 : raceClassification.hashCode());
		result = prime * result + ((raceDate == null) ? 0 : raceDate.hashCode());
		result = prime * result + raceNumber;
		result = prime * result + ((surface == null) ? 0 : surface.hashCode());
		result = prime * result + ((track == null) ? 0 : track.hashCode());
		result = prime * result + ((trackCondition == null) ? 0 : trackCondition.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (claimingPrice != other.claimingPrice)
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (distance != other.distance)
			return false;
		if (exactDistance == null) {
			if (other.exactDistance != null)
				return false;
		} else if (!exactDistance.equals(other.exactDistance))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (footnote == null) {
			if (other.footnote != null)
				return false;
		} else if (!footnote.equals(other.footnote))
			return false;
		if (offTurf == null) {
			if (other.offTurf != null)
				return false;
		} else if (!offTurf.equals(other.offTurf))
			return false;
		if (position != other.position)
			return false;
		if (purse != other.purse)
			return false;
		if (raceClassification == null) {
			if (other.raceClassification != null)
				return false;
		} else if (!raceClassification.equals(other.raceClassification))
			return false;
		if (raceDate == null) {
			if (other.raceDate != null)
				return false;
		} else if (!raceDate.equals(other.raceDate))
			return false;
		if (raceNumber != other.raceNumber)
			return false;
		if (surface == null) {
			if (other.surface != null)
				return false;
		} else if (!surface.equals(other.surface))
			return false;
		if (track == null) {
			if (other.track != null)
				return false;
		} else if (!track.equals(other.track))
			return false;
		if (trackCondition == null) {
			if (other.trackCondition != null)
				return false;
		} else if (!trackCondition.equals(other.trackCondition))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("RaceNote [track=").append(track).append(", raceDate=").append(raceDate).append(", raceNumber=")
				.append(raceNumber).append(", type=").append(type).append(", raceClassification=")
				.append(raceClassification).append(", purse=").append(purse).append(", claimingPrice=")
				.append(claimingPrice).append(", distance=").append(distance).append(", exactDistance=")
				.append(exactDistance).append(", surface=").append(surface).append(", offTurf=").append(offTurf)
				.append(", trackCondition=").append(trackCondition).append(", position=").append(position)
				.append(", beatenLengths=").append(beatenLengths).append(", comment=").append(comment)
				.append(", footnote=").append(footnote).append(", flag=").append(flag).append("]");
		return builder2.toString();
	}
	@Override
	public int compareTo(RaceNote o) {
		return raceDate.compareTo(o.getRaceDate());
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
		private String type;
		private String raceClassification;
		private int purse;
		private int claimingPrice;
		private int distance;
		private Boolean exactDistance;
		private String surface;
		private Boolean offTurf;
		private String trackCondition;
		private int position;
		private float beatenLengths;
		private String comment;
		private String footnote;
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

		public Builder withType(String type) {
			this.type = type;
			return this;
		}

		public Builder withRaceClassification(String raceClassification) {
			this.raceClassification = raceClassification;
			return this;
		}

		public Builder withPurse(int purse) {
			this.purse = purse;
			return this;
		}

		public Builder withClaimingPrice(int claimingPrice) {
			this.claimingPrice = claimingPrice;
			return this;
		}

		public Builder withDistance(int distance) {
			this.distance = distance;
			return this;
		}

		public Builder withExactDistance(Boolean exactDistance) {
			this.exactDistance = exactDistance;
			return this;
		}

		public Builder withSurface(String surface) {
			this.surface = surface;
			return this;
		}

		public Builder withOffTurf(Boolean offTurf) {
			this.offTurf = offTurf;
			return this;
		}

		public Builder withTrackCondition(String trackCondition) {
			this.trackCondition = trackCondition;
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

		public Builder withFootnote(String footnote) {
			this.footnote = footnote;
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
