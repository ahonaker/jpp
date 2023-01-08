package net.derbyparty.jpp.object;

import java.io.Serializable;
import javax.annotation.Generated;

public class Stat implements Serializable {

	private static final long serialVersionUID = 1L;

	private String Category;
	private int Starts;
	private int Wins;
	private int Places;
	private int Shows;
	private float WinPercent;
	private float ITMPercent;
	private float ROI;
	private int Earnings;

	@Generated("SparkTools")
	private Stat(Builder builder) {
		this.Category = builder.Category;
		this.Starts = builder.Starts;
		this.Wins = builder.Wins;
		this.Places = builder.Places;
		this.Shows = builder.Shows;
		this.WinPercent = builder.WinPercent;
		this.ITMPercent = builder.ITMPercent;
		this.ROI = builder.ROI;
		this.Earnings = builder.Earnings;
	}

	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public int getStarts() {
		return Starts;
	}
	public int getWins() {
		return Wins;
	}
	public void setWins(int wins) {
		Wins = wins;
	}
	public int getPlaces() {
		return Places;
	}
	public void setPlaces(int places) {
		Places = places;
	}
	public int getShows() {
		return Shows;
	}
	public void setShows(int shows) {
		Shows = shows;
	}
	public void setStarts(int starts) {
		Starts = starts;
	}
	public float getWinPercent() {
		return WinPercent;
	}
	public void setWinPercent(float winPercent) {
		WinPercent = winPercent;
	}
	public float getITMPercent() {
		return ITMPercent;
	}
	public void setITMPercent(float iTMPercent) {
		ITMPercent = iTMPercent;
	}
	public float getROI() {
		return ROI;
	}
	public void setROI(float rOI) {
		ROI = rOI;
	}
	public int getEarnings() {
		return Earnings;
	}

	public void setEarnings(int earnings) {
		Earnings = earnings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Category == null) ? 0 : Category.hashCode());
		result = prime * result + Earnings;
		result = prime * result + Float.floatToIntBits(ITMPercent);
		result = prime * result + Places;
		result = prime * result + Float.floatToIntBits(ROI);
		result = prime * result + Shows;
		result = prime * result + Starts;
		result = prime * result + Float.floatToIntBits(WinPercent);
		result = prime * result + Wins;
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
		Stat other = (Stat) obj;
		if (Category == null) {
			if (other.Category != null)
				return false;
		} else if (!Category.equals(other.Category))
			return false;
		if (Earnings != other.Earnings)
			return false;
		if (Float.floatToIntBits(ITMPercent) != Float.floatToIntBits(other.ITMPercent))
			return false;
		if (Places != other.Places)
			return false;
		if (Float.floatToIntBits(ROI) != Float.floatToIntBits(other.ROI))
			return false;
		if (Shows != other.Shows)
			return false;
		if (Starts != other.Starts)
			return false;
		if (Float.floatToIntBits(WinPercent) != Float.floatToIntBits(other.WinPercent))
			return false;
		if (Wins != other.Wins)
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrainerStat [Category=").append(Category).append(", Starts=").append(Starts)
				.append(", WinPercent=").append(WinPercent).append(", ITMPercent=").append(ITMPercent).append(", ROI=")
				.append(ROI).append("]");
		return builder.toString();
	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	@Generated("SparkTools")
	public static final class Builder {
		private String Category;
		private int Starts;
		private int Wins;
		private int Places;
		private int Shows;
		private float WinPercent;
		private float ITMPercent;
		private float ROI;
		private int Earnings;

		private Builder() {
		}

		public Builder withCategory(String Category) {
			this.Category = Category;
			return this;
		}

		public Builder withStarts(int Starts) {
			this.Starts = Starts;
			return this;
		}

		public Builder withWins(int Wins) {
			this.Wins = Wins;
			return this;
		}

		public Builder withPlaces(int Places) {
			this.Places = Places;
			return this;
		}

		public Builder withShows(int Shows) {
			this.Shows = Shows;
			return this;
		}

		public Builder withWinPercent(float WinPercent) {
			this.WinPercent = WinPercent;
			return this;
		}

		public Builder withITMPercent(float ITMPercent) {
			this.ITMPercent = ITMPercent;
			return this;
		}

		public Builder withROI(float ROI) {
			this.ROI = ROI;
			return this;
		}

		public Builder withEarnings(int Earnings) {
			this.Earnings = Earnings;
			return this;
		}

		public Stat build() {
			return new Stat(this);
		}
	}
	public Stat() {
		super();
	}

	
}
