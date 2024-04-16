package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

public class RaceDate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date RaceDate;
	private Boolean HasChartFlag;
	private Boolean ReviewedFlag;
	private int SpeedVariantDirt;
	private int SpeedVariantTurf;
	private int SpeedVariantAllWeather;

	@Generated("SparkTools")
	private RaceDate(Builder builder) {
		this.RaceDate = builder.RaceDate;
		this.HasChartFlag = builder.HasChartFlag;
		this.ReviewedFlag = builder.ReviewedFlag;
		this.SpeedVariantDirt = builder.SpeedVariantDirt;
		this.SpeedVariantTurf = builder.SpeedVariantTurf;
		this.SpeedVariantAllWeather = builder.SpeedVariantAllWeather;
	}
	
	public Date getRaceDate() {
		return RaceDate;
	}
	public void setRaceDate(Date raceDate) {
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
	public int getSpeedVariantDirt() {
		return SpeedVariantDirt;
	}
	public void setSpeedVariantDirt(int speedVariantDirt) {
		SpeedVariantDirt = speedVariantDirt;
	}
	public int getSpeedVariantTurf() {
		return SpeedVariantTurf;
	}
	public void setSpeedVariantTurf(int speedVariantTurf) {
		SpeedVariantTurf = speedVariantTurf;
	}
	public int getSpeedVariantAllWeather() {
		return SpeedVariantAllWeather;
	}
	public void setSpeedVariantAllWeather(int speedVariantAllWeather) {
		SpeedVariantAllWeather = speedVariantAllWeather;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((HasChartFlag == null) ? 0 : HasChartFlag.hashCode());
		result = prime * result + ((RaceDate == null) ? 0 : RaceDate.hashCode());
		result = prime * result + ((ReviewedFlag == null) ? 0 : ReviewedFlag.hashCode());
		result = prime * result + SpeedVariantAllWeather;
		result = prime * result + SpeedVariantDirt;
		result = prime * result + SpeedVariantTurf;
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
		if (HasChartFlag == null) {
			if (other.HasChartFlag != null)
				return false;
		} else if (!HasChartFlag.equals(other.HasChartFlag))
			return false;
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
		if (SpeedVariantAllWeather != other.SpeedVariantAllWeather)
			return false;
		if (SpeedVariantDirt != other.SpeedVariantDirt)
			return false;
		if (SpeedVariantTurf != other.SpeedVariantTurf)
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("RaceDate [RaceDate=").append(RaceDate).append(", HasChartFlag=").append(HasChartFlag)
				.append(", ReviewedFlag=").append(ReviewedFlag).append(", SpeedVariantDirt=").append(SpeedVariantDirt)
				.append(", SpeedVariantTurf=").append(SpeedVariantTurf).append(", SpeedVariantAllWeather=")
				.append(SpeedVariantAllWeather).append("]");
		return builder2.toString();
	}

	
	
	public RaceDate(Date raceDate, Boolean hasChartFlag, Boolean reviewedFlag, int speedVariantDirt,
			int speedVariantTurf, int speedVariantAllWeather) {
		super();
		RaceDate = raceDate;
		HasChartFlag = hasChartFlag;
		ReviewedFlag = reviewedFlag;
		SpeedVariantDirt = speedVariantDirt;
		SpeedVariantTurf = speedVariantTurf;
		SpeedVariantAllWeather = speedVariantAllWeather;
	}

	public RaceDate() {
		
	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private Date RaceDate;
		private Boolean HasChartFlag;
		private Boolean ReviewedFlag;
		private int SpeedVariantDirt;
		private int SpeedVariantTurf;
		private int SpeedVariantAllWeather;

		private Builder() {
		}

		public Builder withRaceDate(Date RaceDate) {
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

		public Builder withSpeedVariantDirt(int SpeedVariantDirt) {
			this.SpeedVariantDirt = SpeedVariantDirt;
			return this;
		}

		public Builder withSpeedVariantTurf(int SpeedVariantTurf) {
			this.SpeedVariantTurf = SpeedVariantTurf;
			return this;
		}

		public Builder withSpeedVariantAllWeather(int SpeedVariantAllWeather) {
			this.SpeedVariantAllWeather = SpeedVariantAllWeather;
			return this;
		}

		public RaceDate build() {
			return new RaceDate(this);
		}
	}

}
