package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;

import org.bson.codecs.pojo.annotations.BsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;

public class Race implements Serializable {

	private static final long serialVersionUID = 1L;
		
	private String Track;
	private Date Date;
	private String PostTimes;
	private int RaceNumber;
	private int Distance;
	private SurfaceType Surface;
	private RaceType RaceType;
	private AgeRestrictionType AgeRestriction;
	private AgeRestrictionRangeType AgeRestrictionRange;
	private SexRestrictionType SexRestriction;
	private String Classification;
	private int Purse;
	private int ClaimingPrice;
	private float TrackRecord;
	private String RaceConditions;
	private String LasixList;
	private String ButeList;
	private String CoupledList;
	private String MutuelList;
	private String SimulcastTrackCode;
	private int SimulcastTrackRaceNumber;
	private String AllWeatherSurfaceFlag;
	
	private int ParPace2F;
	private int ParPace4F;
	private int ParPace6F;
	private int ParSpeed;
	private int ParLatePace;
	

	private String WagerTypes;
	private List<MultiRaceWager> MultiRaceWagers;
	private List<Entry> Entries;
	
	@JsonIgnore
	@BsonIgnore
	private List<Entry> unscratchedHorses;
	
	private PaceScenarioType PaceScenario;
	private float E1Avg;
	private float E2Avg;	
	private int MaxE2;
	private int LatePaceBestLast3;
	private float MaxSpeedRating;
	private int MaxSpeed;
	private float AverageAdjustedSpeed;
	private int TotalSpeedPoints;
	

	private float furlongs;
	private float miles;
	@SuppressWarnings("unused")
	private int unscratchedEntriesCount;

	private List<String> advantagedEntries;
	
	private String trackCondition;
	private Boolean OffTheTurfFlag;
	private Boolean TurfFlag;
	private Boolean OntoAllWeatherFlag;
	
	private List<String> HandicappingNotes;
	private List<String> Changes;

	private String note;
	
	private TrackBias meetTrackBias;
	private TrackBias weekTrackBias;
	
	private Boolean oneTurn;
	
	private String description;

	@Generated("SparkTools")
	private Race(Builder builder) {
		this.Track = builder.Track;
		this.Date = builder.Date;
		this.PostTimes = builder.PostTimes;
		this.RaceNumber = builder.RaceNumber;
		this.Distance = builder.Distance;
		this.Surface = builder.Surface;
		this.RaceType = builder.RaceType;
		this.AgeRestriction = builder.AgeRestriction;
		this.AgeRestrictionRange = builder.AgeRestrictionRange;
		this.SexRestriction = builder.SexRestriction;
		this.Classification = builder.Classification;
		this.Purse = builder.Purse;
		this.ClaimingPrice = builder.ClaimingPrice;
		this.TrackRecord = builder.TrackRecord;
		this.RaceConditions = builder.RaceConditions;
		this.LasixList = builder.LasixList;
		this.ButeList = builder.ButeList;
		this.CoupledList = builder.CoupledList;
		this.MutuelList = builder.MutuelList;
		this.SimulcastTrackCode = builder.SimulcastTrackCode;
		this.SimulcastTrackRaceNumber = builder.SimulcastTrackRaceNumber;
		this.AllWeatherSurfaceFlag = builder.AllWeatherSurfaceFlag;
		this.ParPace2F = builder.ParPace2F;
		this.ParPace4F = builder.ParPace4F;
		this.ParPace6F = builder.ParPace6F;
		this.ParSpeed = builder.ParSpeed;
		this.ParLatePace = builder.ParLatePace;
		this.WagerTypes = builder.WagerTypes;
		this.MultiRaceWagers = builder.MultiRaceWagers;
		this.Entries = builder.Entries;
		this.PaceScenario = builder.PaceScenario;
		this.E1Avg = builder.E1Avg;
		this.E2Avg = builder.E2Avg;
		this.MaxE2 = builder.MaxE2;
		this.LatePaceBestLast3 = builder.LatePaceBestLast3;
		this.MaxSpeedRating = builder.MaxSpeedRating;
		this.MaxSpeed = builder.MaxSpeed;
		this.AverageAdjustedSpeed = builder.AverageAdjustedSpeed;
		this.TotalSpeedPoints = builder.TotalSpeedPoints;
		this.furlongs = builder.furlongs;
		this.miles = builder.miles;
		this.unscratchedEntriesCount = builder.unscratchedEntriesCount;
		this.advantagedEntries = builder.advantagedEntries;
		this.trackCondition = builder.trackCondition;
		this.OffTheTurfFlag = builder.OffTheTurfFlag;
		this.TurfFlag = builder.TurfFlag;
		this.HandicappingNotes = builder.HandicappingNotes;
		this.Changes = builder.Changes;
		this.note = builder.note;
		this.meetTrackBias = builder.meetTrackBias;
		this.weekTrackBias = builder.weekTrackBias;
		this.oneTurn = builder.oneTurn;
		this.description = builder.description;
	}

	public float getE1Avg() {
		return E1Avg;
	}

	public void setE1Avg(float e1Avg) {
		E1Avg = e1Avg;
	}

	public float getE2Avg() {
		return E2Avg;
	}

	public void setE2Avg(float e2Avg) {
		E2Avg = e2Avg;
	}

	public int getMaxE2() {
		return MaxE2;
	}

	public void setMaxE2(int maxE2) {
		MaxE2 = maxE2;
	}

	public int getLatePaceBestLast3() {
		return LatePaceBestLast3;
	}

	public void setLatePaceBestLast3(int latePaceBestLast3) {
		LatePaceBestLast3 = latePaceBestLast3;
	}

	
	
	// Factors
	
	public String getTrackCondition() {
		return trackCondition;
	}


	public void setTrackCondition(String trackCondition) {
		this.trackCondition = trackCondition;
	}


	public Boolean getOffTheTurfFlag() {
		return OffTheTurfFlag;
	}


	public void setOffTheTurfFlag(Boolean offTheTurfFlag) {
		OffTheTurfFlag = offTheTurfFlag;
	}


	public Boolean getOntoAllWeatherFlag() {
		if (OntoAllWeatherFlag == null) return false;
		return OntoAllWeatherFlag;
	}

	public void setOntoAllWeatherFlag(Boolean ontoAllWeatherFlag) {
		OntoAllWeatherFlag = ontoAllWeatherFlag;
	}

	public List<String> getAdvantagedEntries() {
		return advantagedEntries;
	}



	public void setAdvantagedEntries(List<String> advantagedEntries) {
		this.advantagedEntries = advantagedEntries;
	}



	public float getMaxSpeedRating() {
		return MaxSpeedRating;
	}



	public void setMaxSpeedRating(float maxSpeedRating) {
		MaxSpeedRating = maxSpeedRating;
	}



	public int getMaxSpeed() {
		return MaxSpeed;
	}


	public void setMaxSpeed(int maxSpeed) {
		MaxSpeed = maxSpeed;
	}


	public Boolean getTurfFlag() {
		return TurfFlag;
	}


	public void setTurfFlag(Boolean turfFlag) {
		TurfFlag = turfFlag;
	}


	public float getAverageAdjustedSpeed() {
		return AverageAdjustedSpeed;
	}


	public void setAverageAdjustedSpeed(float averageAdjustedSpeed) {
		AverageAdjustedSpeed = averageAdjustedSpeed;
	}

	public int getTotalSpeedPoints() {
		return TotalSpeedPoints;
	}


	public void setTotalSpeedPoints(int totalSpeedPoints) {
		TotalSpeedPoints = totalSpeedPoints;
	}


	public List<String> getHandicappingNotes() {
		return HandicappingNotes;
	}


	public void setHandicappingNotes(List<String> handicappingNotes) {
		HandicappingNotes = handicappingNotes;
	}
	
	public List<String> getChanges() {
		return Changes;
	}


	public void setChanges(List<String> changes) {
		Changes = changes;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public TrackBias getMeetTrackBias() {
		return meetTrackBias;
	}


	public void setMeetTrackBias(TrackBias meetTrackBias) {
		this.meetTrackBias = meetTrackBias;
	}


	public TrackBias getWeekTrackBias() {
		return weekTrackBias;
	}


	public void setWeekTrackBias(TrackBias weekTrackBias) {
		this.weekTrackBias = weekTrackBias;
	}


	public Boolean getOneTurn() {
		return oneTurn;
	}

	public void setOneTurn(Boolean oneTurn) {
		this.oneTurn = oneTurn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrack() {
		return Track;
	}
	public void setTrack(String track) {
		Track = track;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public String getPostTimes() {
		return PostTimes;
	}
	public void setPostTimes(String postTimes) {
		PostTimes = postTimes;
	}
	public int getRaceNumber() {
		return RaceNumber;
	}
	public void setRaceNumber(int raceNumber) {
		RaceNumber = raceNumber;
	}
	public int getDistance() {
		return Distance;
	}
	public void setDistance(int distance) {
		Distance = distance;
	}
	public SurfaceType getSurface() {
		return Surface;
	}
	public void setSurface(SurfaceType surface) {
		Surface = surface;
	}
	public RaceType getRaceType() {
		return RaceType;
	}
	public void setRaceType(RaceType raceType) {
		RaceType = raceType;
	}
	public AgeRestrictionType getAgeRestriction() {
		return AgeRestriction;
	}
	public void setAgeRestriction(AgeRestrictionType ageRestriction) {
		AgeRestriction = ageRestriction;
	}
	public AgeRestrictionRangeType getAgeRestrictionRange() {
		return AgeRestrictionRange;
	}
	public void setAgeRestrictionRange(AgeRestrictionRangeType ageRestrictionRange) {
		AgeRestrictionRange = ageRestrictionRange;
	}
	public SexRestrictionType getSexRestriction() {
		return SexRestriction;
	}
	public void setSexRestriction(SexRestrictionType sexRestriction) {
		SexRestriction = sexRestriction;
	}
	public String getClassification() {
		return Classification;
	}
	public void setClassification(String classification) {
		Classification = classification;
	}
	public int getPurse() {
		return Purse;
	}
	public void setPurse(int purse) {
		Purse = purse;
	}
	public int getClaimingPrice() {
		return ClaimingPrice;
	}
	public void setClaimingPrice(int claimingPrice) {
		ClaimingPrice = claimingPrice;
	}
	public float getTrackRecord() {
		return TrackRecord;
	}
	public void setTrackRecord(float trackRecord) {
		TrackRecord = trackRecord;
	}
	public String getRaceConditions() {
		return RaceConditions;
	}
	public void setRaceConditions(String raceConditions) {
		RaceConditions = raceConditions;
	}
	public String getLasixList() {
		return LasixList;
	}
	public void setLasixList(String lasixList) {
		LasixList = lasixList;
	}
	public String getButeList() {
		return ButeList;
	}
	public void setButeList(String buteList) {
		ButeList = buteList;
	}
	public String getCoupledList() {
		return CoupledList;
	}
	public void setCoupledList(String coupledList) {
		CoupledList = coupledList;
	}
	public String getMutuelList() {
		return MutuelList;
	}
	public void setMutuelList(String mutuelList) {
		MutuelList = mutuelList;
	}
	public String getSimulcastTrackCode() {
		return SimulcastTrackCode;
	}
	public void setSimulcastTrackCode(String simulcastTrackCode) {
		SimulcastTrackCode = simulcastTrackCode;
	}
	public int getSimulcastTrackRaceNumber() {
		return SimulcastTrackRaceNumber;
	}
	public void setSimulcastTrackRaceNumber(int simulcastTrackRaceNumber) {
		SimulcastTrackRaceNumber = simulcastTrackRaceNumber;
	}
	public String getAllWeatherSurfaceFlag() {
		return AllWeatherSurfaceFlag;
	}
	public void setAllWeatherSurfaceFlag(String allWeatherSurfaceFlag) {
		AllWeatherSurfaceFlag = allWeatherSurfaceFlag;
	}
	public int getParPace2F() {
		return ParPace2F;
	}
	public void setParPace2F(int parPace2F) {
		ParPace2F = parPace2F;
	}
	public int getParSpeed() {
		return ParSpeed;
	}
	public void setParSpeed(int parSpeed) {
		ParSpeed = parSpeed;
	}
	public int getParLatePace() {
		return ParLatePace;
	}
	public void setParLatePace(int parLatePace) {
		ParLatePace = parLatePace;
	}
	public int getParPace4F() {
		return ParPace4F;
	}
	public void setParPace4F(int parPace4F) {
		ParPace4F = parPace4F;
	}
	public int getParPace6F() {
		return ParPace6F;
	}
	public void setParPace6F(int parPace6F) {
		ParPace6F = parPace6F;
	}
	public String getWagerTypes() {
		return WagerTypes;
	}
	public void setWagerTypes(String wagerTypes) {
		WagerTypes = wagerTypes;
	}
	public List<MultiRaceWager> getMultiRaceWagers() {
		return MultiRaceWagers;
	}


	public void setMultiRaceWagers(List<MultiRaceWager> multiRaceWagers) {
		MultiRaceWagers = multiRaceWagers;
	}


	public List<Entry> getEntries() {
		return Entries;
	}

	public void setEntries(List<Entry> entries) {
		Entries = entries;
	}
	
	@JsonIgnore
	public List<Entry> getUnscratchedEntries () {
		List<Entry> entries = new ArrayList<Entry>();
		if (Entries != null)
			for (Entry entry : Entries) {
				if (!entry.getScratchedFlag()) entries.add(entry);
			}
		return entries;
	}

	public int getUnscratchedEntriesCount () {
		return getUnscratchedEntries().size();
	}

	public PaceScenarioType getPaceScenario() {
		return PaceScenario;
	}

	public void setPaceScenario(PaceScenarioType paceScenario) {
		PaceScenario = paceScenario;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((AgeRestriction == null) ? 0 : AgeRestriction.hashCode());
		result = prime * result + ((AgeRestrictionRange == null) ? 0 : AgeRestrictionRange.hashCode());
		result = prime * result + ((AllWeatherSurfaceFlag == null) ? 0 : AllWeatherSurfaceFlag.hashCode());
		result = prime * result + Float.floatToIntBits(AverageAdjustedSpeed);
		result = prime * result + ((ButeList == null) ? 0 : ButeList.hashCode());
		result = prime * result + ((Changes == null) ? 0 : Changes.hashCode());
		result = prime * result + ClaimingPrice;
		result = prime * result + ((Classification == null) ? 0 : Classification.hashCode());
		result = prime * result + ((CoupledList == null) ? 0 : CoupledList.hashCode());
		result = prime * result + ((Date == null) ? 0 : Date.hashCode());
		result = prime * result + Distance;
		result = prime * result + Float.floatToIntBits(E1Avg);
		result = prime * result + Float.floatToIntBits(E2Avg);
		result = prime * result + ((HandicappingNotes == null) ? 0 : HandicappingNotes.hashCode());
		result = prime * result + ((Entries == null) ? 0 : Entries.hashCode());
		result = prime * result + ((LasixList == null) ? 0 : LasixList.hashCode());
		result = prime * result + LatePaceBestLast3;
		result = prime * result + MaxE2;
		result = prime * result + MaxSpeed;
		result = prime * result + Float.floatToIntBits(MaxSpeedRating);
		result = prime * result + ((MultiRaceWagers == null) ? 0 : MultiRaceWagers.hashCode());
		result = prime * result + ((MutuelList == null) ? 0 : MutuelList.hashCode());
		result = prime * result + ((OffTheTurfFlag == null) ? 0 : OffTheTurfFlag.hashCode());
		result = prime * result + ((PaceScenario == null) ? 0 : PaceScenario.hashCode());
		result = prime * result + ParLatePace;
		result = prime * result + ParPace2F;
		result = prime * result + ParPace4F;
		result = prime * result + ParPace6F;
		result = prime * result + ParSpeed;
		result = prime * result + ((PostTimes == null) ? 0 : PostTimes.hashCode());
		result = prime * result + Purse;
		result = prime * result + ((RaceConditions == null) ? 0 : RaceConditions.hashCode());
		result = prime * result + RaceNumber;
		result = prime * result + ((RaceType == null) ? 0 : RaceType.hashCode());
		result = prime * result + ((SexRestriction == null) ? 0 : SexRestriction.hashCode());
		result = prime * result + ((SimulcastTrackCode == null) ? 0 : SimulcastTrackCode.hashCode());
		result = prime * result + SimulcastTrackRaceNumber;
		result = prime * result + ((Surface == null) ? 0 : Surface.hashCode());
		result = prime * result + TotalSpeedPoints;
		result = prime * result + ((Track == null) ? 0 : Track.hashCode());
		result = prime * result + Float.floatToIntBits(TrackRecord);
		result = prime * result + ((TurfFlag == null) ? 0 : TurfFlag.hashCode());
		result = prime * result + ((WagerTypes == null) ? 0 : WagerTypes.hashCode());
		result = prime * result + ((advantagedEntries == null) ? 0 : advantagedEntries.hashCode());
		result = prime * result + Float.floatToIntBits(furlongs);
		result = prime * result + ((meetTrackBias == null) ? 0 : meetTrackBias.hashCode());
		result = prime * result + Float.floatToIntBits(miles);
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((trackCondition == null) ? 0 : trackCondition.hashCode());
		result = prime * result + ((weekTrackBias == null) ? 0 : weekTrackBias.hashCode());
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
		Race other = (Race) obj;
		if (AgeRestriction != other.AgeRestriction)
			return false;
		if (AgeRestrictionRange != other.AgeRestrictionRange)
			return false;
		if (AllWeatherSurfaceFlag == null) {
			if (other.AllWeatherSurfaceFlag != null)
				return false;
		} else if (!AllWeatherSurfaceFlag.equals(other.AllWeatherSurfaceFlag))
			return false;
		if (Float.floatToIntBits(AverageAdjustedSpeed) != Float.floatToIntBits(other.AverageAdjustedSpeed))
			return false;
		if (ButeList == null) {
			if (other.ButeList != null)
				return false;
		} else if (!ButeList.equals(other.ButeList))
			return false;
		if (Changes == null) {
			if (other.Changes != null)
				return false;
		} else if (!Changes.equals(other.Changes))
			return false;
		if (ClaimingPrice != other.ClaimingPrice)
			return false;
		if (Classification == null) {
			if (other.Classification != null)
				return false;
		} else if (!Classification.equals(other.Classification))
			return false;
		if (CoupledList == null) {
			if (other.CoupledList != null)
				return false;
		} else if (!CoupledList.equals(other.CoupledList))
			return false;
		if (Date == null) {
			if (other.Date != null)
				return false;
		} else if (!Date.equals(other.Date))
			return false;
		if (Distance != other.Distance)
			return false;
		if (Float.floatToIntBits(E1Avg) != Float.floatToIntBits(other.E1Avg))
			return false;
		if (Float.floatToIntBits(E2Avg) != Float.floatToIntBits(other.E2Avg))
			return false;
		if (HandicappingNotes == null) {
			if (other.HandicappingNotes != null)
				return false;
		} else if (!HandicappingNotes.equals(other.HandicappingNotes))
			return false;
		if (Entries == null) {
			if (other.Entries != null)
				return false;
		} else if (!Entries.equals(other.Entries))
			return false;
		if (LasixList == null) {
			if (other.LasixList != null)
				return false;
		} else if (!LasixList.equals(other.LasixList))
			return false;
		if (LatePaceBestLast3 != other.LatePaceBestLast3)
			return false;
		if (MaxE2 != other.MaxE2)
			return false;
		if (MaxSpeed != other.MaxSpeed)
			return false;
		if (Float.floatToIntBits(MaxSpeedRating) != Float.floatToIntBits(other.MaxSpeedRating))
			return false;
		if (MultiRaceWagers == null) {
			if (other.MultiRaceWagers != null)
				return false;
		} else if (!MultiRaceWagers.equals(other.MultiRaceWagers))
			return false;
		if (MutuelList == null) {
			if (other.MutuelList != null)
				return false;
		} else if (!MutuelList.equals(other.MutuelList))
			return false;
		if (OffTheTurfFlag == null) {
			if (other.OffTheTurfFlag != null)
				return false;
		} else if (!OffTheTurfFlag.equals(other.OffTheTurfFlag))
			return false;
		if (PaceScenario != other.PaceScenario)
			return false;
		if (ParLatePace != other.ParLatePace)
			return false;
		if (ParPace2F != other.ParPace2F)
			return false;
		if (ParPace4F != other.ParPace4F)
			return false;
		if (ParPace6F != other.ParPace6F)
			return false;
		if (ParSpeed != other.ParSpeed)
			return false;
		if (PostTimes == null) {
			if (other.PostTimes != null)
				return false;
		} else if (!PostTimes.equals(other.PostTimes))
			return false;
		if (Purse != other.Purse)
			return false;
		if (RaceConditions == null) {
			if (other.RaceConditions != null)
				return false;
		} else if (!RaceConditions.equals(other.RaceConditions))
			return false;
		if (RaceNumber != other.RaceNumber)
			return false;
		if (RaceType != other.RaceType)
			return false;
		if (SexRestriction != other.SexRestriction)
			return false;
		if (SimulcastTrackCode == null) {
			if (other.SimulcastTrackCode != null)
				return false;
		} else if (!SimulcastTrackCode.equals(other.SimulcastTrackCode))
			return false;
		if (SimulcastTrackRaceNumber != other.SimulcastTrackRaceNumber)
			return false;
		if (Surface != other.Surface)
			return false;
		if (TotalSpeedPoints != other.TotalSpeedPoints)
			return false;
		if (Track == null) {
			if (other.Track != null)
				return false;
		} else if (!Track.equals(other.Track))
			return false;
		if (Float.floatToIntBits(TrackRecord) != Float.floatToIntBits(other.TrackRecord))
			return false;
		if (TurfFlag == null) {
			if (other.TurfFlag != null)
				return false;
		} else if (!TurfFlag.equals(other.TurfFlag))
			return false;
		if (WagerTypes == null) {
			if (other.WagerTypes != null)
				return false;
		} else if (!WagerTypes.equals(other.WagerTypes))
			return false;
		if (advantagedEntries == null) {
			if (other.advantagedEntries != null)
				return false;
		} else if (!advantagedEntries.equals(other.advantagedEntries))
			return false;
		if (Float.floatToIntBits(furlongs) != Float.floatToIntBits(other.furlongs))
			return false;
		if (meetTrackBias == null) {
			if (other.meetTrackBias != null)
				return false;
		} else if (!meetTrackBias.equals(other.meetTrackBias))
			return false;
		if (Float.floatToIntBits(miles) != Float.floatToIntBits(other.miles))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (trackCondition == null) {
			if (other.trackCondition != null)
				return false;
		} else if (!trackCondition.equals(other.trackCondition))
			return false;
		if (weekTrackBias == null) {
			if (other.weekTrackBias != null)
				return false;
		} else if (!weekTrackBias.equals(other.weekTrackBias))
			return false;
		return true;
	}



	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Race [Track=").append(Track).append(", Date=").append(Date).append(", RaceNumber=")
				.append(RaceNumber).append(", Distance=").append(Distance).append(", Surface=").append(Surface)
				.append(", RaceType=").append(RaceType).append(", AgeRestriction=").append(AgeRestriction)
				.append(", AgeRestrictionRange=").append(AgeRestrictionRange).append(", SexRestriction=")
				.append(SexRestriction).append(", Classification=").append(Classification).append(", Purse=")
				.append(Purse).append(", ClaimingPrice=").append(ClaimingPrice).append(", TrackRecord=")
				.append(TrackRecord).append(", RaceConditions=").append(RaceConditions).append(", LasixList=")
				.append(LasixList).append(", ButeList=").append(ButeList).append(", CoupledList=").append(CoupledList)
				.append(", MutuelList=").append(MutuelList).append(", SimulcastTrackCode=").append(SimulcastTrackCode)
				.append(", SimulcastTrackRaceNumber=").append(SimulcastTrackRaceNumber)
				.append(", AllWeatherSurfaceFlag=").append(AllWeatherSurfaceFlag).append(", ParPace2F=")
				.append(ParPace2F).append(", ParPace4F=").append(ParPace4F).append(", ParPace6F=").append(ParPace6F)
				.append(", ParSpeed=").append(ParSpeed).append(", ParLatePace=").append(ParLatePace)
				.append(", WagerTypes=").append(WagerTypes).append(", Entries=").append(Entries).append("]");
		return builder2.toString();
	}
	public float getFurlongs () {
		return ((float) Distance / 220);
	}
	public float getMiles () {
		return ((float) Distance / 1760);
	}
	public Race() {
		
	}
	
	public Entry getEntryWithName(String name) {
		for (Entry entry: Entries) {
			if (entry.getName().toUpperCase().equals(name.toUpperCase())) return entry;
		}
		return null;
	}


	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	@Generated("SparkTools")
	public static final class Builder {
		private String Track;
		private Date Date;
		private String PostTimes;
		private int RaceNumber;
		private int Distance;
		private SurfaceType Surface;
		private RaceType RaceType;
		private AgeRestrictionType AgeRestriction;
		private AgeRestrictionRangeType AgeRestrictionRange;
		private SexRestrictionType SexRestriction;
		private String Classification;
		private int Purse;
		private int ClaimingPrice;
		private float TrackRecord;
		private String RaceConditions;
		private String LasixList;
		private String ButeList;
		private String CoupledList;
		private String MutuelList;
		private String SimulcastTrackCode;
		private int SimulcastTrackRaceNumber;
		private String AllWeatherSurfaceFlag;
		private int ParPace2F;
		private int ParPace4F;
		private int ParPace6F;
		private int ParSpeed;
		private int ParLatePace;
		private String WagerTypes;
		private List<MultiRaceWager> MultiRaceWagers = Collections.emptyList();
		private List<Entry> Entries = Collections.emptyList();
		private PaceScenarioType PaceScenario;
		private float E1Avg;
		private float E2Avg;
		private int MaxE2;
		private int LatePaceBestLast3;
		private float MaxSpeedRating;
		private int MaxSpeed;
		private float AverageAdjustedSpeed;
		private int TotalSpeedPoints;
		private float furlongs;
		private float miles;
		private int unscratchedEntriesCount;
		private List<String> advantagedEntries = Collections.emptyList();
		private String trackCondition;
		private Boolean OffTheTurfFlag;
		private Boolean TurfFlag;
		private List<String> HandicappingNotes = Collections.emptyList();
		private List<String> Changes = Collections.emptyList();
		private String note;
		private TrackBias meetTrackBias;
		private TrackBias weekTrackBias;
		private Boolean oneTurn;
		private String description;

		private Builder() {
		}

		public Builder withTrack(String Track) {
			this.Track = Track;
			return this;
		}

		public Builder withDate(Date Date) {
			this.Date = Date;
			return this;
		}

		public Builder withPostTimes(String PostTimes) {
			this.PostTimes = PostTimes;
			return this;
		}

		public Builder withRaceNumber(int RaceNumber) {
			this.RaceNumber = RaceNumber;
			return this;
		}

		public Builder withDistance(int Distance) {
			this.Distance = Distance;
			return this;
		}

		public Builder withSurface(SurfaceType Surface) {
			this.Surface = Surface;
			return this;
		}

		public Builder withRaceType(RaceType RaceType) {
			this.RaceType = RaceType;
			return this;
		}

		public Builder withAgeRestriction(AgeRestrictionType AgeRestriction) {
			this.AgeRestriction = AgeRestriction;
			return this;
		}

		public Builder withAgeRestrictionRange(AgeRestrictionRangeType AgeRestrictionRange) {
			this.AgeRestrictionRange = AgeRestrictionRange;
			return this;
		}

		public Builder withSexRestriction(SexRestrictionType SexRestriction) {
			this.SexRestriction = SexRestriction;
			return this;
		}

		public Builder withClassification(String Classification) {
			this.Classification = Classification;
			return this;
		}

		public Builder withPurse(int Purse) {
			this.Purse = Purse;
			return this;
		}

		public Builder withClaimingPrice(int ClaimingPrice) {
			this.ClaimingPrice = ClaimingPrice;
			return this;
		}

		public Builder withTrackRecord(float TrackRecord) {
			this.TrackRecord = TrackRecord;
			return this;
		}

		public Builder withRaceConditions(String RaceConditions) {
			this.RaceConditions = RaceConditions;
			return this;
		}

		public Builder withLasixList(String LasixList) {
			this.LasixList = LasixList;
			return this;
		}

		public Builder withButeList(String ButeList) {
			this.ButeList = ButeList;
			return this;
		}

		public Builder withCoupledList(String CoupledList) {
			this.CoupledList = CoupledList;
			return this;
		}

		public Builder withMutuelList(String MutuelList) {
			this.MutuelList = MutuelList;
			return this;
		}

		public Builder withSimulcastTrackCode(String SimulcastTrackCode) {
			this.SimulcastTrackCode = SimulcastTrackCode;
			return this;
		}

		public Builder withSimulcastTrackRaceNumber(int SimulcastTrackRaceNumber) {
			this.SimulcastTrackRaceNumber = SimulcastTrackRaceNumber;
			return this;
		}

		public Builder withAllWeatherSurfaceFlag(String AllWeatherSurfaceFlag) {
			this.AllWeatherSurfaceFlag = AllWeatherSurfaceFlag;
			return this;
		}

		public Builder withParPace2F(int ParPace2F) {
			this.ParPace2F = ParPace2F;
			return this;
		}

		public Builder withParPace4F(int ParPace4F) {
			this.ParPace4F = ParPace4F;
			return this;
		}

		public Builder withParPace6F(int ParPace6F) {
			this.ParPace6F = ParPace6F;
			return this;
		}

		public Builder withParSpeed(int ParSpeed) {
			this.ParSpeed = ParSpeed;
			return this;
		}

		public Builder withParLatePace(int ParLatePace) {
			this.ParLatePace = ParLatePace;
			return this;
		}

		public Builder withWagerTypes(String WagerTypes) {
			this.WagerTypes = WagerTypes;
			return this;
		}

		public Builder withMultiRaceWagers(List<MultiRaceWager> MultiRaceWagers) {
			this.MultiRaceWagers = MultiRaceWagers;
			return this;
		}

		public Builder withEntries(List<Entry> Entries) {
			this.Entries = Entries;
			return this;
		}

		public Builder withPaceScenario(PaceScenarioType PaceScenario) {
			this.PaceScenario = PaceScenario;
			return this;
		}

		public Builder withE1Avg(float E1Avg) {
			this.E1Avg = E1Avg;
			return this;
		}

		public Builder withE2Avg(float E2Avg) {
			this.E2Avg = E2Avg;
			return this;
		}

		public Builder withMaxE2(int MaxE2) {
			this.MaxE2 = MaxE2;
			return this;
		}

		public Builder withLatePaceBestLast3(int LatePaceBestLast3) {
			this.LatePaceBestLast3 = LatePaceBestLast3;
			return this;
		}

		public Builder withMaxSpeedRating(float MaxSpeedRating) {
			this.MaxSpeedRating = MaxSpeedRating;
			return this;
		}

		public Builder withMaxSpeed(int MaxSpeed) {
			this.MaxSpeed = MaxSpeed;
			return this;
		}

		public Builder withAverageAdjustedSpeed(float AverageAdjustedSpeed) {
			this.AverageAdjustedSpeed = AverageAdjustedSpeed;
			return this;
		}

		public Builder withTotalSpeedPoints(int TotalSpeedPoints) {
			this.TotalSpeedPoints = TotalSpeedPoints;
			return this;
		}

		public Builder withFurlongs(float furlongs) {
			this.furlongs = furlongs;
			return this;
		}

		public Builder withMiles(float miles) {
			this.miles = miles;
			return this;
		}

		public Builder withUnscratchedEntriesCount(int unscratchedEntriesCount) {
			this.unscratchedEntriesCount = unscratchedEntriesCount;
			return this;
		}

		public Builder withAdvantagedEntries(List<String> advantagedEntries) {
			this.advantagedEntries = advantagedEntries;
			return this;
		}

		public Builder withTrackCondition(String trackCondition) {
			this.trackCondition = trackCondition;
			return this;
		}

		public Builder withOffTheTurfFlag(Boolean OffTheTurfFlag) {
			this.OffTheTurfFlag = OffTheTurfFlag;
			return this;
		}

		public Builder withTurfFlag(Boolean TurfFlag) {
			this.TurfFlag = TurfFlag;
			return this;
		}

		public Builder withHandicappingNotes(List<String> HandicappingNotes) {
			this.HandicappingNotes = HandicappingNotes;
			return this;
		}

		public Builder withChanges(List<String> Changes) {
			this.Changes = Changes;
			return this;
		}

		public Builder withNote(String note) {
			this.note = note;
			return this;
		}

		public Builder withMeetTrackBias(TrackBias meetTrackBias) {
			this.meetTrackBias = meetTrackBias;
			return this;
		}

		public Builder withWeekTrackBias(TrackBias weekTrackBias) {
			this.weekTrackBias = weekTrackBias;
			return this;
		}

		public Builder withOneTurn(Boolean oneTurn) {
			this.oneTurn = oneTurn;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((Date == null) ? 0 : Date.hashCode());
			result = prime * result + RaceNumber;
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
			Builder other = (Builder) obj;
			if (Date == null) {
				if (other.Date != null)
					return false;
			} else if (!Date.equals(other.Date))
				return false;
			if (RaceNumber != other.RaceNumber)
				return false;
			return true;
		}

		public Race build() {
			return new Race(this);
		}
	}
	
}
