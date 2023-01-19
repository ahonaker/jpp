package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.time.LocalDate;
import javax.annotation.Generated;

public class RaceDate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LocalDate RaceDate;
	private Boolean HasChartFlag;
	private Boolean ReviewedFlag;
	@Generated("SparkTools")
	private RaceDate(Builder builder) {
		this.RaceDate = builder.RaceDate;
		this.HasChartFlag = builder.HasChartFlag;
		this.ReviewedFlag = builder.ReviewedFlag;
	}
	public LocalDate getRaceDate() {
		return RaceDate;
	}
	public void setRaceDate(LocalDate raceDate) {
		RaceDate = raceDate;
	}
	public Boolean getHasChartFlag() {
		return HasChartFlag;
	}
	public void setHasChartFlag(Boolean hashChartFlag) {
		HasChartFlag = hashChartFlag;
	}
	public Boolean getReviewedFlag() {
		return ReviewedFlag;
	}
	public void setReviewedFlag(Boolean reviewedFlag) {
		ReviewedFlag = reviewedFlag;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((RaceDate == null) ? 0 : RaceDate.hashCode());
		result = prime * result + ((ReviewedFlag == null) ? 0 : ReviewedFlag.hashCode());
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
		RaceDate other = (RaceDate) obj;
		if (RaceDate == null) {
			if (other.RaceDate != null)
				return false;
		} else if (!RaceDate.equals(other.RaceDate))
			return false;
		if (ReviewedFlag == null) {
			if (other.ReviewedFlag != null)
				return false;
		} else if (!ReviewedFlag.equals(other.ReviewedFlag))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RaceDate [RaceDate=").append(RaceDate).append(", ReviewedFlag=").append(ReviewedFlag)
				.append("]");
		return builder.toString();
	}
	public RaceDate(LocalDate raceDate, Boolean reviewedFlag) {
		super();
		RaceDate = raceDate;
		ReviewedFlag = reviewedFlag;
	}
	
	public RaceDate() {
		
	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private LocalDate RaceDate;
		private Boolean HasChartFlag;
		private Boolean ReviewedFlag;

		private Builder() {
		}

		public Builder withRaceDate(LocalDate RaceDate) {
			this.RaceDate = RaceDate;
			return this;
		}

		public Builder withHasChartFlag(Boolean HasChartFlag) {
			this.HasChartFlag = HasChartFlag;
			return this;
		}

		public Builder withReviewedFlag(Boolean ReviewedFlag) {
			this.ReviewedFlag = ReviewedFlag;
			return this;
		}

		public RaceDate build() {
			return new RaceDate(this);
		}
	}

}
