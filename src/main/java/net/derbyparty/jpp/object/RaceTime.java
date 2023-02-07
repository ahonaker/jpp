package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javax.annotation.Generated;

public class RaceTime implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String track;
	private LocalDate raceDate;
	private int raceNumber;
	private int distance;
	private SurfaceType surfaceType;
	private RaceType raceType;
	private String allWeatherFlag;
	private String raceClassification;
	private String trackCondition;
	private float Fraction1;
	private float Fraction2;
	private float Fraction3;
	private float FinalTime;
	private int SpeedPar;
	private int WinnerPar;
	private float RaceShapeFirstCall;
	private float RaceShapeSecondCall;
	private int PaceFigure2F;
	private int PaceFigure4F;
	private int PaceFigure6F;
	private int PaceFigure8F;
	private int PaceFigure10F;
	private int PaceFigureLate;

	@Generated("SparkTools")
	private RaceTime(Builder builder) {
		this.track = builder.track;
		this.raceDate = builder.raceDate;
		this.raceNumber = builder.raceNumber;
		this.distance = builder.distance;
		this.surfaceType = builder.surface;
		this.raceType = builder.raceType;
		this.allWeatherFlag = builder.allWeatherFlag;
		this.raceClassification = builder.raceClassification;
		this.trackCondition = builder.trackCondition;
		this.Fraction1 = builder.Fraction1;
		this.Fraction2 = builder.Fraction2;
		this.Fraction3 = builder.Fraction3;
		this.FinalTime = builder.FinalTime;
		this.SpeedPar = builder.SpeedPar;
		this.WinnerPar = builder.WinnerPar;
		this.RaceShapeFirstCall = builder.RaceShapeFirstCall;
		this.RaceShapeSecondCall = builder.RaceShapeSecondCall;
		this.PaceFigure2F = builder.PaceFigure2F;
		this.PaceFigure4F = builder.PaceFigure4F;
		this.PaceFigure6F = builder.PaceFigure6F;
		this.PaceFigure8F = builder.PaceFigure8F;
		this.PaceFigure10F = builder.PaceFigure10F;
		this.PaceFigureLate = builder.PaceFigureLate;
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
	public int getDistance() {
		return distance;
	}
	public float getFurlongs () {
		return ((float) Math.abs(distance) / 220);
	}
	public float getMiles () {
		return ((float) Math.abs(distance) / 1760);
	}
	public Boolean getExactDistanceFlag () {
		return (distance > 0);
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public SurfaceType getSurfaceType() {
		return surfaceType;
	}
	public void setSurfaceType(SurfaceType surface) {
		this.surfaceType = surface;
	}
	public String getSurface() {
		return (allWeatherFlag.equals("A")) ? "ALL_WEATHER" : surfaceType.toString();
	}
	public RaceType getRaceType() {
		return raceType;
	}
	public String getAllWeatherFlag() {
		return allWeatherFlag;
	}
	public void setAllWeatherFlag(String allWeatherFlag) {
		this.allWeatherFlag = allWeatherFlag;
	}
	public void setRaceType(RaceType raceType) {
		this.raceType = raceType;
	}
	public String getRaceClassification() {
		return raceClassification;
	}
	public void setRaceClassification(String raceClassification) {
		this.raceClassification = raceClassification;
	}
	public String getTrackCondition() {
		return trackCondition;
	}
	public void setTrackCondition(String trackCondition) {
		this.trackCondition = trackCondition;
	}
	public float getFraction1() {
		return Fraction1;
	}
	public void setFraction1(float fraction1) {
		Fraction1 = fraction1;
	}
	public float getFraction2() {
		return Fraction2;
	}
	public void setFraction2(float fraction2) {
		Fraction2 = fraction2;
	}
	public float getFraction3() {
		return Fraction3;
	}
	public void setFraction3(float fraction3) {
		Fraction3 = fraction3;
	}
	public float getFinalTime() {
		return FinalTime;
	}
	public void setFinalTime(float finalTime) {
		FinalTime = finalTime;
	}
	public String getFinalTimeMinutes() {
		int min = (int) Math.floor(FinalTime / 60);
		double sec = (double) FinalTime - min * 60;
		
		String ret = min + ":" + new DecimalFormat("00.00").format(sec);
		return ret;		
	}
	public int getSpeedPar() {
		return SpeedPar;
	}
	public void setSpeedPar(int speedPar) {
		SpeedPar = speedPar;
	}
	public int getWinnerPar() {
		return WinnerPar;
	}
	public void setWinnerPar(int winnerPar) {
		WinnerPar = winnerPar;
	}
	public float getRaceShapeFirstCall() {
		return RaceShapeFirstCall;
	}
	public void setRaceShapeFirstCall(float raceShapeFirstCall) {
		RaceShapeFirstCall = raceShapeFirstCall;
	}
	public float getRaceShapeSecondCall() {
		return RaceShapeSecondCall;
	}
	public void setRaceShapeSecondCall(float raceShapeSecondCall) {
		RaceShapeSecondCall = raceShapeSecondCall;
	}
	public int getPaceFigure2F() {
		return PaceFigure2F;
	}
	public void setPaceFigure2F(int paceFigure2F) {
		PaceFigure2F = paceFigure2F;
	}
	public int getPaceFigure4F() {
		return PaceFigure4F;
	}
	public void setPaceFigure4F(int paceFigure4F) {
		PaceFigure4F = paceFigure4F;
	}
	public int getPaceFigure6F() {
		return PaceFigure6F;
	}
	public void setPaceFigure6F(int paceFigure6F) {
		PaceFigure6F = paceFigure6F;
	}
	public int getPaceFigure8F() {
		return PaceFigure8F;
	}
	public void setPaceFigure8F(int paceFigure8F) {
		PaceFigure8F = paceFigure8F;
	}
	public int getPaceFigure10F() {
		return PaceFigure10F;
	}
	public void setPaceFigure10F(int paceFigure10F) {
		PaceFigure10F = paceFigure10F;
	}
	public int getPaceFigureLate() {
		return PaceFigureLate;
	}
	public void setPaceFigureLate(int paceFigureLate) {
		PaceFigureLate = paceFigureLate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(FinalTime);
		result = prime * result + Float.floatToIntBits(Fraction1);
		result = prime * result + Float.floatToIntBits(Fraction2);
		result = prime * result + Float.floatToIntBits(Fraction3);
		result = prime * result + PaceFigure10F;
		result = prime * result + PaceFigure2F;
		result = prime * result + PaceFigure4F;
		result = prime * result + PaceFigure6F;
		result = prime * result + PaceFigure8F;
		result = prime * result + PaceFigureLate;
		result = prime * result + Float.floatToIntBits(RaceShapeFirstCall);
		result = prime * result + Float.floatToIntBits(RaceShapeSecondCall);
		result = prime * result + SpeedPar;
		result = prime * result + WinnerPar;
		result = prime * result + ((allWeatherFlag == null) ? 0 : allWeatherFlag.hashCode());
		result = prime * result + distance;
		result = prime * result + ((raceClassification == null) ? 0 : raceClassification.hashCode());
		result = prime * result + ((raceDate == null) ? 0 : raceDate.hashCode());
		result = prime * result + raceNumber;
		result = prime * result + ((raceType == null) ? 0 : raceType.hashCode());
		result = prime * result + ((surfaceType == null) ? 0 : surfaceType.hashCode());
		result = prime * result + ((track == null) ? 0 : track.hashCode());
		result = prime * result + ((trackCondition == null) ? 0 : trackCondition.hashCode());
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
		RaceTime other = (RaceTime) obj;
		if (Float.floatToIntBits(FinalTime) != Float.floatToIntBits(other.FinalTime))
			return false;
		if (Float.floatToIntBits(Fraction1) != Float.floatToIntBits(other.Fraction1))
			return false;
		if (Float.floatToIntBits(Fraction2) != Float.floatToIntBits(other.Fraction2))
			return false;
		if (Float.floatToIntBits(Fraction3) != Float.floatToIntBits(other.Fraction3))
			return false;
		if (PaceFigure10F != other.PaceFigure10F)
			return false;
		if (PaceFigure2F != other.PaceFigure2F)
			return false;
		if (PaceFigure4F != other.PaceFigure4F)
			return false;
		if (PaceFigure6F != other.PaceFigure6F)
			return false;
		if (PaceFigure8F != other.PaceFigure8F)
			return false;
		if (PaceFigureLate != other.PaceFigureLate)
			return false;
		if (Float.floatToIntBits(RaceShapeFirstCall) != Float.floatToIntBits(other.RaceShapeFirstCall))
			return false;
		if (Float.floatToIntBits(RaceShapeSecondCall) != Float.floatToIntBits(other.RaceShapeSecondCall))
			return false;
		if (SpeedPar != other.SpeedPar)
			return false;
		if (WinnerPar != other.WinnerPar)
			return false;
		if (allWeatherFlag == null) {
			if (other.allWeatherFlag != null)
				return false;
		} else if (!allWeatherFlag.equals(other.allWeatherFlag))
			return false;
		if (distance != other.distance)
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
		if (raceType != other.raceType)
			return false;
		if (surfaceType != other.surfaceType)
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
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("RaceTime [track=").append(track).append(", raceDate=").append(raceDate).append(", raceNumber=")
				.append(raceNumber).append(", distance=").append(distance).append(", surface=").append(surfaceType)
				.append(", raceType=").append(raceType).append(", allWeatherFlag=").append(allWeatherFlag)
				.append(", raceClassification=").append(raceClassification).append(", trackCondition=")
				.append(trackCondition).append(", Fraction1=").append(Fraction1).append(", Fraction2=")
				.append(Fraction2).append(", Fraction3=").append(Fraction3).append(", FinalTime=").append(FinalTime)
				.append(", SpeedPar=").append(SpeedPar).append(", WinnerPar=").append(WinnerPar)
				.append(", RaceShapeFirstCall=").append(RaceShapeFirstCall).append(", RaceShapeSecondCall=")
				.append(RaceShapeSecondCall).append(", PaceFigure2F=").append(PaceFigure2F).append(", PaceFigure4F=")
				.append(PaceFigure4F).append(", PaceFigure6F=").append(PaceFigure6F).append(", PaceFigure8F=")
				.append(PaceFigure8F).append(", PaceFigure10F=").append(PaceFigure10F).append(", PaceFigureLate=")
				.append(PaceFigureLate).append("]");
		return builder2.toString();
	}
	
	public RaceTime(String track, LocalDate raceDate, int raceNumber, int distance, SurfaceType surface,
			RaceType raceType, String allWeatherFlag, String raceClassification, String trackCondition, float fraction1,
			float fraction2, float fraction3, float finalTime, int speedPar, int winnerPar, float raceShapeFirstCall,
			float raceShapeSecondCall, int paceFigure2F, int paceFigure4F, int paceFigure6F, int paceFigure8F,
			int paceFigure10F, int paceFigureLate) {
		super();
		this.track = track;
		this.raceDate = raceDate;
		this.raceNumber = raceNumber;
		this.distance = distance;
		this.surfaceType = surface;
		this.raceType = raceType;
		this.allWeatherFlag = allWeatherFlag;
		this.raceClassification = raceClassification;
		this.trackCondition = trackCondition;
		Fraction1 = fraction1;
		Fraction2 = fraction2;
		Fraction3 = fraction3;
		FinalTime = finalTime;
		SpeedPar = speedPar;
		WinnerPar = winnerPar;
		RaceShapeFirstCall = raceShapeFirstCall;
		RaceShapeSecondCall = raceShapeSecondCall;
		PaceFigure2F = paceFigure2F;
		PaceFigure4F = paceFigure4F;
		PaceFigure6F = paceFigure6F;
		PaceFigure8F = paceFigure8F;
		PaceFigure10F = paceFigure10F;
		PaceFigureLate = paceFigureLate;
	}

	public RaceTime() {
		
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
		private int distance;
		private SurfaceType surface;
		private RaceType raceType;
		private String allWeatherFlag;
		private String raceClassification;
		private String trackCondition;
		private float Fraction1;
		private float Fraction2;
		private float Fraction3;
		private float FinalTime;
		private int SpeedPar;
		private int WinnerPar;
		private float RaceShapeFirstCall;
		private float RaceShapeSecondCall;
		private int PaceFigure2F;
		private int PaceFigure4F;
		private int PaceFigure6F;
		private int PaceFigure8F;
		private int PaceFigure10F;
		private int PaceFigureLate;

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

		public Builder withDistance(int distance) {
			this.distance = distance;
			return this;
		}

		public Builder withSurface(SurfaceType surface) {
			this.surface = surface;
			return this;
		}

		public Builder withRaceType(RaceType raceType) {
			this.raceType = raceType;
			return this;
		}

		public Builder withAllWeatherFlag(String allWeatherFlag) {
			this.allWeatherFlag = allWeatherFlag;
			return this;
		}

		public Builder withRaceClassification(String raceClassification) {
			this.raceClassification = raceClassification;
			return this;
		}

		public Builder withTrackCondition(String trackCondition) {
			this.trackCondition = trackCondition;
			return this;
		}

		public Builder withFraction1(float Fraction1) {
			this.Fraction1 = Fraction1;
			return this;
		}

		public Builder withFraction2(float Fraction2) {
			this.Fraction2 = Fraction2;
			return this;
		}

		public Builder withFraction3(float Fraction3) {
			this.Fraction3 = Fraction3;
			return this;
		}

		public Builder withFinalTime(float FinalTime) {
			this.FinalTime = FinalTime;
			return this;
		}

		public Builder withSpeedPar(int SpeedPar) {
			this.SpeedPar = SpeedPar;
			return this;
		}

		public Builder withWinnerPar(int WinnerPar) {
			this.WinnerPar = WinnerPar;
			return this;
		}

		public Builder withRaceShapeFirstCall(float RaceShapeFirstCall) {
			this.RaceShapeFirstCall = RaceShapeFirstCall;
			return this;
		}

		public Builder withRaceShapeSecondCall(float RaceShapeSecondCall) {
			this.RaceShapeSecondCall = RaceShapeSecondCall;
			return this;
		}

		public Builder withPaceFigure2F(int PaceFigure2F) {
			this.PaceFigure2F = PaceFigure2F;
			return this;
		}

		public Builder withPaceFigure4F(int PaceFigure4F) {
			this.PaceFigure4F = PaceFigure4F;
			return this;
		}

		public Builder withPaceFigure6F(int PaceFigure6F) {
			this.PaceFigure6F = PaceFigure6F;
			return this;
		}

		public Builder withPaceFigure8F(int PaceFigure8F) {
			this.PaceFigure8F = PaceFigure8F;
			return this;
		}

		public Builder withPaceFigure10F(int PaceFigure10F) {
			this.PaceFigure10F = PaceFigure10F;
			return this;
		}

		public Builder withPaceFigureLate(int PaceFigureLate) {
			this.PaceFigureLate = PaceFigureLate;
			return this;
		}

		public RaceTime build() {
			return new RaceTime(this);
		}
	}
	
}
