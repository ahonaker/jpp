package net.derbyparty.jpp.object;

import java.io.Serializable;
import javax.annotation.Generated;

public class RaceCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String raceCategory;
	private String surface;
	private Boolean isRoute;
	private int purseMin;
	private int purseMax;
	private int claimingMin;
	private int claimingMax;
	private int parSpeedRating;
	private int maxSpeedRating;
	private int minSpeedRating;
	private int raceCount;

	@Generated("SparkTools")
	private RaceCategory(Builder builder) {
		this.raceCategory = builder.raceCategory;
		this.surface = builder.surface;
		this.isRoute = builder.isRoute;
		this.purseMin = builder.purseMin;
		this.purseMax = builder.purseMax;
		this.claimingMin = builder.claimingMin;
		this.claimingMax = builder.claimingMax;
		this.parSpeedRating = builder.parSpeedRating;
		this.maxSpeedRating = builder.maxSpeedRating;
		this.minSpeedRating = builder.minSpeedRating;
		this.raceCount = builder.raceCount;
	}

	public String getCategory() {
		return raceCategory;
	}

	public void setCategory(String category) {
		this.raceCategory = category;
	}

	public String getSurface() {
		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public Boolean getIsRoute() {
		return isRoute;
	}

	public void setIsRoute(Boolean isRoute) {
		this.isRoute = isRoute;
	}

	public int getPurseMin() {
		return purseMin;
	}

	public void setPurseMin(int purseMin) {
		this.purseMin = purseMin;
	}

	public int getPurseMax() {
		return purseMax;
	}

	public void setPurseMax(int purseMax) {
		this.purseMax = purseMax;
	}

	public int getClaimingMin() {
		return claimingMin;
	}

	public void setClaimingMin(int claimingMin) {
		this.claimingMin = claimingMin;
	}

	public int getClaimingMax() {
		return claimingMax;
	}

	public void setClaimingMax(int claimingMax) {
		this.claimingMax = claimingMax;
	}

	public int getParSpeedRating() {
		return parSpeedRating;
	}

	public void setParSpeedRating(int parSpeedRating) {
		this.parSpeedRating = parSpeedRating;
	}

	public int getMaxSpeedRating() {
		return maxSpeedRating;
	}

	public void setMaxSpeedRating(int maxSpeedRating) {
		this.maxSpeedRating = maxSpeedRating;
	}

	public int getMinSpeedRating() {
		return minSpeedRating;
	}

	public void setMinSpeedRating(int minSpeedRating) {
		this.minSpeedRating = minSpeedRating;
	}

	public int getRaceCount() {
		return raceCount;
	}

	public void setRaceCount(int raceCount) {
		this.raceCount = raceCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + claimingMax;
		result = prime * result + claimingMin;
		result = prime * result + ((isRoute == null) ? 0 : isRoute.hashCode());
		result = prime * result + maxSpeedRating;
		result = prime * result + minSpeedRating;
		result = prime * result + parSpeedRating;
		result = prime * result + purseMax;
		result = prime * result + purseMin;
		result = prime * result + ((raceCategory == null) ? 0 : raceCategory.hashCode());
		result = prime * result + raceCount;
		result = prime * result + ((surface == null) ? 0 : surface.hashCode());
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
		RaceCategory other = (RaceCategory) obj;
		if (claimingMax != other.claimingMax)
			return false;
		if (claimingMin != other.claimingMin)
			return false;
		if (isRoute == null) {
			if (other.isRoute != null)
				return false;
		} else if (!isRoute.equals(other.isRoute))
			return false;
		if (maxSpeedRating != other.maxSpeedRating)
			return false;
		if (minSpeedRating != other.minSpeedRating)
			return false;
		if (parSpeedRating != other.parSpeedRating)
			return false;
		if (purseMax != other.purseMax)
			return false;
		if (purseMin != other.purseMin)
			return false;
		if (raceCategory == null) {
			if (other.raceCategory != null)
				return false;
		} else if (!raceCategory.equals(other.raceCategory))
			return false;
		if (raceCount != other.raceCount)
			return false;
		if (surface == null) {
			if (other.surface != null)
				return false;
		} else if (!surface.equals(other.surface))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("RaceCategory [raceCategory=").append(raceCategory).append(", surface=").append(surface)
				.append(", isRoute=").append(isRoute).append(", purseMin=").append(purseMin).append(", purseMax=")
				.append(purseMax).append(", claimingMin=").append(claimingMin).append(", claimingMax=")
				.append(claimingMax).append(", parSpeedRating=").append(parSpeedRating).append(", maxSpeedRating=")
				.append(maxSpeedRating).append(", minSpeedRating=").append(minSpeedRating).append(", raceCount=")
				.append(raceCount).append("]");
		return builder2.toString();
	}

	public RaceCategory(String raceCategory, String surface, Boolean isRoute, int purseMin, int purseMax,
			int claimingMin, int claimingMax, int parSpeedRating, int maxSpeedRating, int minSpeedRating,
			int raceCount) {
		super();
		this.raceCategory = raceCategory;
		this.surface = surface;
		this.isRoute = isRoute;
		this.purseMin = purseMin;
		this.purseMax = purseMax;
		this.claimingMin = claimingMin;
		this.claimingMax = claimingMax;
		this.parSpeedRating = parSpeedRating;
		this.maxSpeedRating = maxSpeedRating;
		this.minSpeedRating = minSpeedRating;
		this.raceCount = raceCount;
	}

	public RaceCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private String raceCategory;
		private String surface;
		private Boolean isRoute;
		private int purseMin;
		private int purseMax;
		private int claimingMin;
		private int claimingMax;
		private int parSpeedRating;
		private int maxSpeedRating;
		private int minSpeedRating;
		private int raceCount;

		private Builder() {
		}

		public Builder withRaceCategory(String raceCategory) {
			this.raceCategory = raceCategory;
			return this;
		}

		public Builder withSurface(String surface) {
			this.surface = surface;
			return this;
		}

		public Builder withIsRoute(Boolean isRoute) {
			this.isRoute = isRoute;
			return this;
		}

		public Builder withPurseMin(int purseMin) {
			this.purseMin = purseMin;
			return this;
		}

		public Builder withPurseMax(int purseMax) {
			this.purseMax = purseMax;
			return this;
		}

		public Builder withClaimingMin(int claimingMin) {
			this.claimingMin = claimingMin;
			return this;
		}

		public Builder withClaimingMax(int claimingMax) {
			this.claimingMax = claimingMax;
			return this;
		}

		public Builder withParSpeedRating(int parSpeedRating) {
			this.parSpeedRating = parSpeedRating;
			return this;
		}

		public Builder withMaxSpeedRating(int maxSpeedRating) {
			this.maxSpeedRating = maxSpeedRating;
			return this;
		}

		public Builder withMinSpeedRating(int minSpeedRating) {
			this.minSpeedRating = minSpeedRating;
			return this;
		}

		public Builder withRaceCount(int raceCount) {
			this.raceCount = raceCount;
			return this;
		}

		public RaceCategory build() {
			return new RaceCategory(this);
		}
	}
	
	
	
	
}
