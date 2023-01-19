package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;

public class Horse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int RaceNumber;
	
	private String Owner;
	private String OwnerSilks;
	private String MTOFlag;
	private String ProgramNumber;
	private int PostPosition;
	private String Entry;
	private float MLOdds;
	private String Name;
	private int YearOfBirth;
	private String FoalingMonth;
	private String Sex;
	private String Color;
	private int Weight;
	private int ApprenticeWeightAllowed;
	private String Sire;
	private String SiresSire;
	private String Dam;
	private String DamsSire;
	private String Breeder;
	private float SireStudFee;
	private float AuctionPrice;
	private String AuctionLocationDate;
	private String PedigreeRatingDirt;
	private String PedigreeRatingMud;
	private String PedigreeRatingTurf;
	private String PedigreeRatingDist;
	private String StateCountry;
	private int ProgramPostPosition;
	private MedicationType Medication;
	private EquipmentChangeType Equipment;
	private int LifetimeStarts;
	private int LifetimeWins;
	private int LifetimePlaces;
	private int LifetimeShows;
	private int LifetimeBestSpeed;
	private float LifetimeEarnings;
	private int DistanceStarts;
	private int DistanceWins;
	private int DistancePlaces;
	private int DistanceShows;
	private float DistanceEarnings;
	private int DistanceBestSpeed;
	private int TrackStarts;
	private int TrackWins;
	private int TrackPlaces;
	private int TrackShows;
	private float TrackEarnings;
	private int TrackBestSpeed;
	private int DirtStarts;
	private int DirtWins;
	private int DirtPlaces;
	private int DirtShows;
	private float DirtEarnings;
	private int DirtBestSpeed;
	private String CurrentTrack;
	private int TurfStarts;
	private int TurfWins;
	private int TurfPlaces;
	private int TurfShows;
	private float TurfEarnings;
	private int TurfBestSpeed;
	private int WetStarts;
	private int WetWins;
	private int WetPlaces;
	private int WetShows;
	private float WetEarnings;
	private int WetBestSpeed;
	private int AllWeatherStarts;
	private int AllWeatherWins;
	private int AllWeatherPlaces;
	private int AllWeatherShows;
	private int AllWeatherBestSpeed;
	private float AllWeatherEarnings;	
	private int CurrentYear;
	private int CurrentYearStarts;
	private int CurrentYearWins;
	private int CurrentYearPlaces;
	private int CurrentYearShows;
	private float CurrentYearEarnings;
	private int PreviousYear;
	private int PreviousYearStarts;
	private int PreviousYearWins;
	private int PreviousYearPlaces;
	private int PreviousYearShows;
	private float PreviousYearEarnings;
	private int MostRecentYearBestSpeed;
	private int SecondMostRecentYearBestSpeed;
	private String RunStyle;
	private int SpeedPoints;
	private float ClaimingPrice;
	private int DaysSinceLastRace;
	private float PrimePowerRating;
	private float AverageClassRatingLastThree;
	private String StatebredFlag;
	private Trainer Trainer;
	private Jockey Jockey;
	private List<Workout> Workouts;
	private List<PastPerformance> PastPerformances;
	
	//Factors
	
	private float E1Avg;
	private float E2Avg;	
	private int MaxE2;
	private float LatePaceAvg;
	private float EarlyPosition;
	private float LatePosition;
	private float ClosingRatio;
	private int LatePaceBestLast3;
	private float PaceAdjustedLate;
	private int LatePaceLast;
	private float ClassRating;
	private float AverageCompetitiveLevel;
	private float LastRaceStrength;
	private float ClassShift;
	private int PurseShift;
	private float SpeedRating;
	private int BasicFitness;
	private int FormPoints;
	private float FurlongDays;
	private float TurnTime;
	private float AvgAdjustedSpeedRating;
	@SuppressWarnings("unused")
	private float combinedPaceAvg;
	@SuppressWarnings("unused")
	private int age;
	
	private float ARatingClass;
	private float ARatingForm;
	private float ARatingConnections;
	private float ARating;

	private Boolean scratchedFlag;
	private Boolean _showDetails;
	
	private List<String> Angles;
	private String selection;
	private float bettingLine;
	
	private String note;
	private Boolean pick;
	private int finishPosition;
	private float winPayout;
	private float placePayout;
	private float showPayout;
	
	private float ARatingFairValue;
	
	private float PrimePower;
	private float SireAWD;
	private int SireMudPercent;
	private int SireMudStarts;
	private int SireFirstPercent;
	private int SireTurfPercent;
	private int SireFirstTurfPercent;
	private float SireSPI;
	private float DamSireAWD;
	private int DamSireMudPercent;
	private int DamSireMudStarts;
	private int DamSireFirstPercent;
	private int DamSireTurfPercent;
	private int DamSireFirstTurfPercent;
	private float DamSireSPI;
	private String DamDescription;
	private int DamTwoYearOldPercent;
	private int DamTurfWinners;
	private int DamStarters;
	private int DamWinners;
	private int DamStakesWinners;
	private float DamDPI;
	
	private float BrisCurrentClass;
	private float BrisAvgLast3Class;
	
	private String flag;
	private String comment;

	@Generated("SparkTools")
	private Horse(Builder builder) {
		this.RaceNumber = builder.RaceNumber;
		this.Owner = builder.Owner;
		this.OwnerSilks = builder.OwnerSilks;
		this.MTOFlag = builder.MTOFlag;
		this.ProgramNumber = builder.ProgramNumber;
		this.PostPosition = builder.PostPosition;
		this.Entry = builder.Entry;
		this.MLOdds = builder.MLOdds;
		this.Name = builder.Name;
		this.YearOfBirth = builder.YearOfBirth;
		this.FoalingMonth = builder.FoalingMonth;
		this.Sex = builder.Sex;
		this.Color = builder.Color;
		this.Weight = builder.Weight;
		this.ApprenticeWeightAllowed = builder.ApprenticeWeightAllowed;
		this.Sire = builder.Sire;
		this.SiresSire = builder.SiresSire;
		this.Dam = builder.Dam;
		this.DamsSire = builder.DamsSire;
		this.Breeder = builder.Breeder;
		this.SireStudFee = builder.SireStudFee;
		this.AuctionPrice = builder.AuctionPrice;
		this.AuctionLocationDate = builder.AuctionLocationDate;
		this.PedigreeRatingDirt = builder.PedigreeRatingDirt;
		this.PedigreeRatingMud = builder.PedigreeRatingMud;
		this.PedigreeRatingTurf = builder.PedigreeRatingTurf;
		this.PedigreeRatingDist = builder.PedigreeRatingDist;
		this.StateCountry = builder.StateCountry;
		this.ProgramPostPosition = builder.ProgramPostPosition;
		this.Medication = builder.Medication;
		this.Equipment = builder.Equipment;
		this.LifetimeStarts = builder.LifetimeStarts;
		this.LifetimeWins = builder.LifetimeWins;
		this.LifetimePlaces = builder.LifetimePlaces;
		this.LifetimeShows = builder.LifetimeShows;
		this.LifetimeBestSpeed = builder.LifetimeBestSpeed;
		this.LifetimeEarnings = builder.LifetimeEarnings;
		this.DistanceStarts = builder.DistanceStarts;
		this.DistanceWins = builder.DistanceWins;
		this.DistancePlaces = builder.DistancePlaces;
		this.DistanceShows = builder.DistanceShows;
		this.DistanceEarnings = builder.DistanceEarnings;
		this.DistanceBestSpeed = builder.DistanceBestSpeed;
		this.TrackStarts = builder.TrackStarts;
		this.TrackWins = builder.TrackWins;
		this.TrackPlaces = builder.TrackPlaces;
		this.TrackShows = builder.TrackShows;
		this.TrackEarnings = builder.TrackEarnings;
		this.TrackBestSpeed = builder.TrackBestSpeed;
		this.DirtStarts = builder.DirtStarts;
		this.DirtWins = builder.DirtWins;
		this.DirtPlaces = builder.DirtPlaces;
		this.DirtShows = builder.DirtShows;
		this.DirtEarnings = builder.DirtEarnings;
		this.DirtBestSpeed = builder.DirtBestSpeed;
		this.CurrentTrack = builder.CurrentTrack;
		this.TurfStarts = builder.TurfStarts;
		this.TurfWins = builder.TurfWins;
		this.TurfPlaces = builder.TurfPlaces;
		this.TurfShows = builder.TurfShows;
		this.TurfEarnings = builder.TurfEarnings;
		this.TurfBestSpeed = builder.TurfBestSpeed;
		this.WetStarts = builder.WetStarts;
		this.WetWins = builder.WetWins;
		this.WetPlaces = builder.WetPlaces;
		this.WetShows = builder.WetShows;
		this.WetEarnings = builder.WetEarnings;
		this.WetBestSpeed = builder.WetBestSpeed;
		this.AllWeatherStarts = builder.AllWeatherStarts;
		this.AllWeatherWins = builder.AllWeatherWins;
		this.AllWeatherPlaces = builder.AllWeatherPlaces;
		this.AllWeatherShows = builder.AllWeatherShows;
		this.AllWeatherBestSpeed = builder.AllWeatherBestSpeed;
		this.AllWeatherEarnings = builder.AllWeatherEarnings;
		this.CurrentYear = builder.CurrentYear;
		this.CurrentYearStarts = builder.CurrentYearStarts;
		this.CurrentYearWins = builder.CurrentYearWins;
		this.CurrentYearPlaces = builder.CurrentYearPlaces;
		this.CurrentYearShows = builder.CurrentYearShows;
		this.CurrentYearEarnings = builder.CurrentYearEarnings;
		this.PreviousYear = builder.PreviousYear;
		this.PreviousYearStarts = builder.PreviousYearStarts;
		this.PreviousYearWins = builder.PreviousYearWins;
		this.PreviousYearPlaces = builder.PreviousYearPlaces;
		this.PreviousYearShows = builder.PreviousYearShows;
		this.PreviousYearEarnings = builder.PreviousYearEarnings;
		this.MostRecentYearBestSpeed = builder.MostRecentYearBestSpeed;
		this.SecondMostRecentYearBestSpeed = builder.SecondMostRecentYearBestSpeed;
		this.RunStyle = builder.RunStyle;
		this.SpeedPoints = builder.SpeedPoints;
		this.ClaimingPrice = builder.ClaimingPrice;
		this.DaysSinceLastRace = builder.DaysSinceLastRace;
		this.PrimePowerRating = builder.PrimePowerRating;
		this.AverageClassRatingLastThree = builder.AverageClassRatingLastThree;
		this.StatebredFlag = builder.StatebredFlag;
		this.Trainer = builder.Trainer;
		this.Jockey = builder.Jockey;
		this.Workouts = builder.Workouts;
		this.PastPerformances = builder.PastPerformances;
		this.E1Avg = builder.E1Avg;
		this.E2Avg = builder.E2Avg;
		this.MaxE2 = builder.MaxE2;
		this.LatePaceAvg = builder.LatePaceAvg;
		this.EarlyPosition = builder.EarlyPosition;
		this.LatePosition = builder.LatePosition;
		this.ClosingRatio = builder.ClosingRatio;
		this.LatePaceBestLast3 = builder.LatePaceBestLast3;
		this.PaceAdjustedLate = builder.PaceAdjustedLate;
		this.LatePaceLast = builder.LatePaceLast;
		this.ClassRating = builder.ClassRating;
		this.AverageCompetitiveLevel = builder.AverageCompetitiveLevel;
		this.LastRaceStrength = builder.LastRaceStrength;
		this.ClassShift = builder.ClassShift;
		this.PurseShift = builder.PurseShift;
		this.SpeedRating = builder.SpeedRating;
		this.BasicFitness = builder.BasicFitness;
		this.FormPoints = builder.FormPoints;
		this.FurlongDays = builder.FurlongDays;
		this.TurnTime = builder.TurnTime;
		this.AvgAdjustedSpeedRating = builder.AvgAdjustedSpeedRating;
		this.combinedPaceAvg = builder.combinedPaceAvg;
		this.age = builder.age;
		this.ARatingClass = builder.ARatingClass;
		this.ARatingForm = builder.ARatingForm;
		this.ARatingConnections = builder.ARatingConnections;
		this.ARating = builder.ARating;
		this.scratchedFlag = builder.scratchedFlag;
		this._showDetails = builder._showDetails;
		this.Angles = builder.Angles;
		this.selection = builder.selection;
		this.bettingLine = builder.bettingLine;
		this.note = builder.note;
		this.pick = builder.pick;
		this.finishPosition = builder.finishPosition;
		this.winPayout = builder.winPayout;
		this.placePayout = builder.placePayout;
		this.showPayout = builder.showPayout;
		this.ARatingFairValue = builder.ARatingFairValue;
		this.PrimePower = builder.PrimePower;
		this.SireAWD = builder.SireAWD;
		this.SireMudPercent = builder.SireMudPercent;
		this.SireMudStarts = builder.SireMudStarts;
		this.SireFirstPercent = builder.SireFirstPercent;
		this.SireTurfPercent = builder.SireTurfPercent;
		this.SireFirstTurfPercent = builder.SireFirstTurfPercent;
		this.SireSPI = builder.SireSPI;
		this.DamSireAWD = builder.DamSireAWD;
		this.DamSireMudPercent = builder.DamSireMudPercent;
		this.DamSireMudStarts = builder.DamSireMudStarts;
		this.DamSireFirstPercent = builder.DamSireFirstPercent;
		this.DamSireTurfPercent = builder.DamSireTurfPercent;
		this.DamSireFirstTurfPercent = builder.DamSireFirstTurfPercent;
		this.DamSireSPI = builder.DamSireSPI;
		this.DamDescription = builder.DamDescription;
		this.DamTwoYearOldPercent = builder.DamTwoYearOldPercent;
		this.DamTurfWinners = builder.DamTurfWinners;
		this.DamStarters = builder.DamStarters;
		this.DamWinners = builder.DamWinners;
		this.DamStakesWinners = builder.DamStakesWinners;
		this.DamDPI = builder.DamDPI;
		this.BrisCurrentClass = builder.BrisCurrentClass;
		this.BrisAvgLast3Class = builder.BrisAvgLast3Class;
		this.flag = builder.flag;
		this.comment = builder.comment;
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
	public float getLatePaceAvg() {
		return LatePaceAvg;
	}
	public void setLatePaceAvg(float latePaceAvg) {
		LatePaceAvg = latePaceAvg;
	}
	public float getCombinedPaceAvg() {
		return E2Avg + LatePaceAvg;
	}
	public float getClosingRatio() {
		return ClosingRatio;
	}
	public void setClosingRatio(float closingRatio) {
		ClosingRatio = closingRatio;
	}
	public float getEarlyPosition() {
		return EarlyPosition;
	}
	public void setEarlyPosition(float earlyPosition) {
		EarlyPosition = earlyPosition;
	}
	public float getLatePosition() {
		return LatePosition;
	}
	public void setLatePosition(float latePosition) {
		LatePosition = latePosition;
	}	
	public int getLatePaceBestLast3() {
		return LatePaceBestLast3;
	}
	public void setLatePaceBestLast3(int latePaceBestLast3) {
		LatePaceBestLast3 = latePaceBestLast3;
	}
	public float getPaceAdjustedLate() {
		return PaceAdjustedLate;
	}
	public void setPaceAdjustedLate(float paceAdjustedLate) {
		PaceAdjustedLate = paceAdjustedLate;
	}
	public int getLatePaceLast() {
		return LatePaceLast;
	}
	public void setLatePaceLast(int latePaceLast) {
		LatePaceLast = latePaceLast;
	}
	public float getClassRating() {
		return ClassRating;
	}
	public void setClassRating(float classRating) {
		ClassRating = classRating;
	}
	public float getAverageCompetitiveLevel() {
		return AverageCompetitiveLevel;
	}

	public void setAverageCompetitiveLevel(float averageCompetitiveLevel) {
		AverageCompetitiveLevel = averageCompetitiveLevel;
	}

	public float getLastRaceStrength() {
		return LastRaceStrength;
	}
	public void setLastRaceStrength(float lastRaceStrength) {
		LastRaceStrength = lastRaceStrength;
	}
	public float getClassShift() {
		return ClassShift;
	}
	public void setClassShift(float classShift) {
		ClassShift = classShift;
	}
	public int getPurseShift() {
		return PurseShift;
	}
	public void setPurseShift(int purseShift) {
		PurseShift = purseShift;
	}
	public float getSpeedRating() {
		return SpeedRating;
	}
	public void setSpeedRating(float speedRating) {
		SpeedRating = speedRating;
	}
	public int getBasicFitness() {
		return BasicFitness;
	}
	public void setBasicFitness(int basicFitness) {
		BasicFitness = basicFitness;
	}
	public int getFormPoints() {
		return FormPoints;
	}
	public void setFormPoints(int formPoints) {
		FormPoints = formPoints;
	}
	public float getFurlongDays() {
		return FurlongDays;
	}
	public void setFurlongDays(float furlongDays) {
		FurlongDays = furlongDays;
	}
	public float getTurnTime() {
		return TurnTime;
	}
	public void setTurnTime(float turnTime) {
		TurnTime = turnTime;
	}
	public float getARatingClass() {
		return ARatingClass;
	}
	public void setARatingClass(float aRatingClass) {
		ARatingClass = aRatingClass;
	}
	public float getARatingForm() {
		return ARatingForm;
	}
	public void setARatingForm(float aRatingForm) {
		ARatingForm = aRatingForm;
	}
	public float getARatingConnections() {
		return ARatingConnections;
	}
	public void setARatingConnections(float aRatingConnections) {
		ARatingConnections = aRatingConnections;
	}
	public float getARating() {
		return ARating;
	}
	public void setARating(float aRating) {
		ARating = aRating;
	}
	public Boolean getScratchedFlag() {
		return scratchedFlag;
	}
	public void setScratchedFlag(Boolean scratchedFlag) {
		this.scratchedFlag = scratchedFlag;
	}
	public Boolean get_showDetails() {
		return _showDetails;
	}

	public void set_showDetails(Boolean _showDetails) {
		this._showDetails = _showDetails;
	}

	public List<String> getAngles() {
		return Angles;
	}

	public void setAngles(List<String> angles) {
		Angles = angles;
	}

	public String getSelection() {
		return selection;
	}

	public float getBettingLine() {
		return bettingLine;
	}

	public void setBettingLine(float bettingLine) {
		this.bettingLine = bettingLine;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public float getAvgAdjustedSpeedRating() {
		return AvgAdjustedSpeedRating;
	}

	public void setAvgAdjustedSpeedRating(float avgAdjustedSpeedRating) {
		AvgAdjustedSpeedRating = avgAdjustedSpeedRating;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public float getARatingFairValue() {
		return ARatingFairValue;
	}

	public void setARatingFairValue(float aRatingFairValue) {
		ARatingFairValue = aRatingFairValue;
	}

	public Boolean getPick() {
		return pick;
	}


	public void setPick(Boolean pick) {
		this.pick = pick;
	}


	public int getFinishPosition() {
		return finishPosition;
	}


	public void setFinishPosition(int finishPosition) {
		this.finishPosition = finishPosition;
	}


	public float getWinPayout() {
		return winPayout;
	}


	public void setWinPayout(float winPayout) {
		this.winPayout = winPayout;
	}


	public float getPlacePayout() {
		return placePayout;
	}


	public void setPlacePayout(float placePayout) {
		this.placePayout = placePayout;
	}


	public float getShowPayout() {
		return showPayout;
	}


	public void setShowPayout(float showPayout) {
		this.showPayout = showPayout;
	}


	public float getPrimePower() {
		return PrimePower;
	}


	public void setPrimePower(float primePower) {
		PrimePower = primePower;
	}


	public float getSireAWD() {
		return SireAWD;
	}


	public void setSireAWD(float sireAWD) {
		SireAWD = sireAWD;
	}


	public int getSireMudPercent() {
		return SireMudPercent;
	}


	public void setSireMudPercent(int sireMudPercent) {
		SireMudPercent = sireMudPercent;
	}


	public int getSireMudStarts() {
		return SireMudStarts;
	}


	public void setSireMudStarts(int sireMudStarts) {
		SireMudStarts = sireMudStarts;
	}


	public int getSireFirstPercent() {
		return SireFirstPercent;
	}


	public void setSireFirstPercent(int sireFirstPercent) {
		SireFirstPercent = sireFirstPercent;
	}


	public float getSireSPI() {
		return SireSPI;
	}


	public void setSireSPI(float sireSPI) {
		SireSPI = sireSPI;
	}


	public float getDamSireAWD() {
		return DamSireAWD;
	}


	public void setDamSireAWD(float damSireAWD) {
		DamSireAWD = damSireAWD;
	}


	public int getDamSireMudPercent() {
		return DamSireMudPercent;
	}


	public void setDamSireMudPercent(int damSireMudPercent) {
		DamSireMudPercent = damSireMudPercent;
	}


	public int getDamSireMudStarts() {
		return DamSireMudStarts;
	}


	public void setDamSireMudStarts(int damSireMudStarts) {
		DamSireMudStarts = damSireMudStarts;
	}


	public int getDamSireFirstPercent() {
		return DamSireFirstPercent;
	}


	public void setDamSireFirstPercent(int damSireFirstPercent) {
		DamSireFirstPercent = damSireFirstPercent;
	}


	public float getDamSireSPI() {
		return DamSireSPI;
	}


	public void setDamSireSPI(float damSireSPI) {
		DamSireSPI = damSireSPI;
	}


	public String getDamDescription() {
		return DamDescription;
	}


	public void setDamDescription(String damDescription) {
		DamDescription = damDescription;
	}


	public int getDamTwoYearOldPercent() {
		return DamTwoYearOldPercent;
	}


	public void setDamTwoYearOldPercent(int damTwoYearOldPercent) {
		DamTwoYearOldPercent = damTwoYearOldPercent;
	}


	public int getDamStarters() {
		return DamStarters;
	}


	public void setDamStarters(int damStarters) {
		DamStarters = damStarters;
	}


	public int getDamWinners() {
		return DamWinners;
	}


	public void setDamWinners(int damWinners) {
		DamWinners = damWinners;
	}


	public int getDamStakesWinners() {
		return DamStakesWinners;
	}


	public void setDamStakesWinners(int damStakesWinners) {
		DamStakesWinners = damStakesWinners;
	}


	public float getDamDPI() {
		return DamDPI;
	}


	public void setDamDPI(float damDPI) {
		DamDPI = damDPI;
	}


	public int getSireTurfPercent() {
		return SireTurfPercent;
	}

	public void setSireTurfPercent(int sireTurfPercent) {
		SireTurfPercent = sireTurfPercent;
	}

	public int getSireFirstTurfPercent() {
		return SireFirstTurfPercent;
	}

	public void setSireFirstTurfPercent(int sireFirstTurfPercent) {
		SireFirstTurfPercent = sireFirstTurfPercent;
	}

	public int getDamSireTurfPercent() {
		return DamSireTurfPercent;
	}

	public void setDamSireTurfPercent(int damSireTurfPercent) {
		DamSireTurfPercent = damSireTurfPercent;
	}

	public int getDamSireFirstTurfPercent() {
		return DamSireFirstTurfPercent;
	}

	public void setDamSireFirstTurfPercent(int damSireFirstTurfPercent) {
		DamSireFirstTurfPercent = damSireFirstTurfPercent;
	}

	public int getDamTurfWinners() {
		return DamTurfWinners;
	}

	public void setDamTurfWinners(int damTurfWinners) {
		DamTurfWinners = damTurfWinners;
	}

	public float getBrisCurrentClass() {
		return BrisCurrentClass;
	}


	public void setBrisCurrentClass(float brisCurrentClass) {
		BrisCurrentClass = brisCurrentClass;
	}


	public float getBrisAvgLast3Class() {
		return BrisAvgLast3Class;
	}


	public void setBrisAvgLast3Class(float brisAvgLast3Class) {
		BrisAvgLast3Class = brisAvgLast3Class;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getRaceNumber() {
		return RaceNumber;
	}
	public void setRaceNumber(int raceNumber) {
		RaceNumber = raceNumber;
	}
	public String getOwner() {
		return Owner;
	}
	public void setOwner(String owner) {
		Owner = owner;
	}
	public String getOwnerSilks() {
		return OwnerSilks;
	}
	public void setOwnerSilks(String ownerSilks) {
		OwnerSilks = ownerSilks;
	}
	public String getMTOFlag() {
		return MTOFlag;
	}
	public void setMTOFlag(String mTOFlag) {
		MTOFlag = mTOFlag;
	}
	public String getProgramNumber() {
		return ProgramNumber;
	}
	public void setProgramNumber(String programNumber) {
		ProgramNumber = programNumber;
	}
	public int getPostPosition() {
		return PostPosition;
	}

	public void setPostPosition(int postPosition) {
		PostPosition = postPosition;
	}

	public String getEntry() {
		return Entry;
	}
	public void setEntry(String entry) {
		Entry = entry;
	}
	public float getMLOdds() {
		return MLOdds;
	}
	public void setMLOdds(float mLOdds) {
		MLOdds = mLOdds;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getYearOfBirth() {
		return YearOfBirth;
	}
	public void setYearOfBirth(int yearOfBirth) {
		YearOfBirth = yearOfBirth;
	}
	
	public int getAge() {
		return LocalDate.now().getYear() - (YearOfBirth + 2000);
	}
	public void setAge() {
		
	}
	public String getFoalingMonth() {
		return FoalingMonth;
	}
	public void setFoalingMonth(String foalingMonth) {
		FoalingMonth = foalingMonth;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
	public int getWeight() {
		return Weight;
	}
	public void setWeight(int weight) {
		Weight = weight;
	}
	public String getSire() {
		return Sire;
	}
	public void setSire(String sire) {
		Sire = sire;
	}
	public String getSiresSire() {
		return SiresSire;
	}
	public void setSiresSire(String siresSire) {
		SiresSire = siresSire;
	}
	public String getDam() {
		return Dam;
	}
	public void setDam(String dam) {
		Dam = dam;
	}
	public String getDamsSire() {
		return DamsSire;
	}
	public void setDamsSire(String damsSire) {
		DamsSire = damsSire;
	}
	public String getBreeder() {
		return Breeder;
	}
	public void setBreeder(String breeder) {
		Breeder = breeder;
	}
	public String getStateCountry() {
		return StateCountry;
	}
	public void setStateCountry(String stateCountry) {
		StateCountry = stateCountry;
	}
	public int getProgramPostPosition() {
		return ProgramPostPosition;
	}
	public void setProgramPostPosition(int programPostPosition) {
		ProgramPostPosition = programPostPosition;
	}
	public MedicationType getMedication() {
		return Medication;
	}
	public void setMedication(MedicationType medication) {
		Medication = medication;
	}
	public EquipmentChangeType getEquipment() {
		return Equipment;
	}
	public void setEquipment(EquipmentChangeType equipment) {
		Equipment = equipment;
	}
	public int getLifetimeStarts() {
		return LifetimeStarts;
	}
	public void setLifetimeStarts(int lifetimeStarts) {
		LifetimeStarts = lifetimeStarts;
	}
	public int getLifetimeWins() {
		return LifetimeWins;
	}
	public void setLifetimeWins(int lifetimeWins) {
		LifetimeWins = lifetimeWins;
	}
	public int getLifetimePlaces() {
		return LifetimePlaces;
	}
	public void setLifetimePlaces(int lifetimePlaces) {
		LifetimePlaces = lifetimePlaces;
	}
	public int getLifetimeShows() {
		return LifetimeShows;
	}
	public void setLifetimeShows(int lifetimeShows) {
		LifetimeShows = lifetimeShows;
	}
	public float getLifetimeEarnings() {
		return LifetimeEarnings;
	}
	public void setLifetimeEarnings(float lifetimeEarnings) {
		LifetimeEarnings = lifetimeEarnings;
	}
	public int getDistanceStarts() {
		return DistanceStarts;
	}
	public void setDistanceStarts(int distanceStarts) {
		DistanceStarts = distanceStarts;
	}
	public int getDistanceWins() {
		return DistanceWins;
	}
	public void setDistanceWins(int distanceWins) {
		DistanceWins = distanceWins;
	}
	public int getDistancePlaces() {
		return DistancePlaces;
	}
	public void setDistancePlaces(int distancePlaces) {
		DistancePlaces = distancePlaces;
	}
	public int getDistanceShows() {
		return DistanceShows;
	}
	public void setDistanceShows(int distanceShows) {
		DistanceShows = distanceShows;
	}
	public float getDistanceEarnings() {
		return DistanceEarnings;
	}
	public void setDistanceEarnings(float distanceEarnings) {
		DistanceEarnings = distanceEarnings;
	}
	public String getCurrentTrack() {
		return CurrentTrack;
	}
	public void setCurrentTrack(String currentTrack) {
		CurrentTrack = currentTrack;
	}
	public int getTrackStarts() {
		return TrackStarts;
	}
	public void setTrackStarts(int trackStarts) {
		TrackStarts = trackStarts;
	}
	public int getTrackWins() {
		return TrackWins;
	}
	public void setTrackWins(int trackWins) {
		TrackWins = trackWins;
	}
	public int getTrackPlaces() {
		return TrackPlaces;
	}
	public void setTrackPlaces(int trackPlaces) {
		TrackPlaces = trackPlaces;
	}
	public int getTrackShows() {
		return TrackShows;
	}
	public void setTrackShows(int trackShows) {
		TrackShows = trackShows;
	}
	public float getTrackEarnings() {
		return TrackEarnings;
	}
	public void setTrackEarnings(float trackEarnings) {
		TrackEarnings = trackEarnings;
	}
	public int getDirtStarts() {
		return DirtStarts;
	}
	public void setDirtStarts(int dirtStarts) {
		DirtStarts = dirtStarts;
	}
	public int getDirtWins() {
		return DirtWins;
	}
	public void setDirtWins(int dirtWins) {
		DirtWins = dirtWins;
	}
	public int getDirtPlaces() {
		return DirtPlaces;
	}
	public void setDirtPlaces(int dirtPlaces) {
		DirtPlaces = dirtPlaces;
	}
	public int getDirtShows() {
		return DirtShows;
	}
	public void setDirtShows(int dirtShows) {
		DirtShows = dirtShows;
	}
	public float getDirtEarnings() {
		return DirtEarnings;
	}
	public void setDirtEarnings(float dirtEarnings) {
		DirtEarnings = dirtEarnings;
	}
	public int getTurfStarts() {
		return TurfStarts;
	}
	public void setTurfStarts(int turfStarts) {
		TurfStarts = turfStarts;
	}
	public int getTurfWins() {
		return TurfWins;
	}
	public void setTurfWins(int turfWins) {
		TurfWins = turfWins;
	}
	public int getTurfPlaces() {
		return TurfPlaces;
	}
	public void setTurfPlaces(int turfPlaces) {
		TurfPlaces = turfPlaces;
	}
	public int getTurfShows() {
		return TurfShows;
	}
	public void setTurfShows(int turfShows) {
		TurfShows = turfShows;
	}
	public float getTurfEarnings() {
		return TurfEarnings;
	}
	public void setTurfEarnings(float turfEarnings) {
		TurfEarnings = turfEarnings;
	}
	public int getWetStarts() {
		return WetStarts;
	}
	public void setWetStarts(int wetStarts) {
		WetStarts = wetStarts;
	}
	public int getWetWins() {
		return WetWins;
	}
	public void setWetWins(int wetWins) {
		WetWins = wetWins;
	}
	public int getWetPlaces() {
		return WetPlaces;
	}
	public void setWetPlaces(int wetPlaces) {
		WetPlaces = wetPlaces;
	}
	public int getWetShows() {
		return WetShows;
	}
	public void setWetShows(int wetShows) {
		WetShows = wetShows;
	}
	public float getWetEarnings() {
		return WetEarnings;
	}
	public void setWetEarnings(float wetEarnings) {
		WetEarnings = wetEarnings;
	}
	public int getAllWeatherStarts() {
		return AllWeatherStarts;
	}
	public void setAllWeatherStarts(int allWeatherStarts) {
		AllWeatherStarts = allWeatherStarts;
	}
	public int getAllWeatherWins() {
		return AllWeatherWins;
	}
	public void setAllWeatherWins(int allWeatherWins) {
		AllWeatherWins = allWeatherWins;
	}
	public int getAllWeatherPlaces() {
		return AllWeatherPlaces;
	}
	public void setAllWeatherPlaces(int allWeatherPlaces) {
		AllWeatherPlaces = allWeatherPlaces;
	}
	public int getAllWeatherShows() {
		return AllWeatherShows;
	}
	public void setAllWeatherShows(int allWeatherShows) {
		AllWeatherShows = allWeatherShows;
	}
	public float getAllWeatherEarnings() {
		return AllWeatherEarnings;
	}
	public void setAllWeatherEarnings(float allWeatherEarnings) {
		AllWeatherEarnings = allWeatherEarnings;
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
	public float getCurrentYearEarnings() {
		return CurrentYearEarnings;
	}
	public void setCurrentYearEarnings(float currentYearEarnings) {
		CurrentYearEarnings = currentYearEarnings;
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
	public float getPreviousYearEarnings() {
		return PreviousYearEarnings;
	}
	public void setPreviousYearEarnings(float previousYearEarnings) {
		PreviousYearEarnings = previousYearEarnings;
	}
	public String getRunStyle() {
		return RunStyle;
	}
	public void setRunStyle(String runStyle) {
		RunStyle = runStyle;
	}
	public int getSpeedPoints() {
		return SpeedPoints;
	}
	public void setSpeedPoints(int speedPoints) {
		SpeedPoints = speedPoints;
	}
	public int getCurrentYear() {
		return CurrentYear;
	}
	public void setCurrentYear(int currentYear) {
		CurrentYear = currentYear;
	}
	public int getPreviousYear() {
		return PreviousYear;
	}
	public void setPreviousYear(int previousYEar) {
		PreviousYear = previousYEar;
	}
	public Trainer getTrainer() {
		return Trainer;
	}
	public void setTrainer(Trainer trainer) {
		Trainer = trainer;
	}
	public List<Workout> getWorkouts() {
		return Workouts;
	}
	public void setWorkouts(List<Workout> workouts) {
		Workouts = workouts;
	}
	public float getSireStudFee() {
		return SireStudFee;
	}
	public void setSireStudFee(float sireStudFee) {
		SireStudFee = sireStudFee;
	}
	public float getAuctionPrice() {
		return AuctionPrice;
	}
	public void setAuctionPrice(float auctionPrice) {
		AuctionPrice = auctionPrice;
	}
	public String getAuctionLocationDate() {
		return AuctionLocationDate;
	}
	public void setAuctionLocationDate(String auctionLocationDate) {
		AuctionLocationDate = auctionLocationDate;
	}
	public String getPedigreeRatingDirt() {
		return PedigreeRatingDirt;
	}
	public void setPedigreeRatingDirt(String pedigreeRatingDirt) {
		PedigreeRatingDirt = pedigreeRatingDirt;
	}
	public String getPedigreeRatingMud() {
		return PedigreeRatingMud;
	}
	public void setPedigreeRatingMud(String pedigreeRatingMud) {
		PedigreeRatingMud = pedigreeRatingMud;
	}
	public String getPedigreeRatingTurf() {
		return PedigreeRatingTurf;
	}
	public void setPedigreeRatingTurf(String pedigreeRatingTurf) {
		PedigreeRatingTurf = pedigreeRatingTurf;
	}
	public String getPedigreeRatingDist() {
		return PedigreeRatingDist;
	}
	public void setPedigreeRatingDist(String pedigreeRatingDist) {
		PedigreeRatingDist = pedigreeRatingDist;
	}
	public int getLifetimeBestSpeed() {
		return LifetimeBestSpeed;
	}
	public void setLifetimeBestSpeed(int lifetimeBestSpeed) {
		LifetimeBestSpeed = lifetimeBestSpeed;
	}
	public int getDistanceBestSpeed() {
		return DistanceBestSpeed;
	}
	public void setDistanceBestSpeed(int distanceBestSpeed) {
		DistanceBestSpeed = distanceBestSpeed;
	}
	public int getTrackBestSpeed() {
		return TrackBestSpeed;
	}
	public void setTrackBestSpeed(int trackBestSpeed) {
		TrackBestSpeed = trackBestSpeed;
	}
	public int getDirtBestSpeed() {
		return DirtBestSpeed;
	}
	public void setDirtBestSpeed(int dirtBestSpeed) {
		DirtBestSpeed = dirtBestSpeed;
	}
	public int getTurfBestSpeed() {
		return TurfBestSpeed;
	}
	public void setTurfBestSpeed(int turfBestSpeed) {
		TurfBestSpeed = turfBestSpeed;
	}
	public int getWetBestSpeed() {
		return WetBestSpeed;
	}
	public void setWetBestSpeed(int wetBestSpeed) {
		WetBestSpeed = wetBestSpeed;
	}
	public int getAllWeatherBestSpeed() {
		return AllWeatherBestSpeed;
	}
	public void setAllWeatherBestSpeed(int allWeatherBestSpeed) {
		AllWeatherBestSpeed = allWeatherBestSpeed;
	}
	public int getMostRecentYearBestSpeed() {
		return MostRecentYearBestSpeed;
	}
	public void setMostRecentYearBestSpeed(int mostRecentYearBestSpeed) {
		MostRecentYearBestSpeed = mostRecentYearBestSpeed;
	}
	public int getSecondMostRecentYearBestSpeed() {
		return SecondMostRecentYearBestSpeed;
	}
	public void setSecondMostRecentYearBestSpeed(int secondMostRecentYearBestSpeed) {
		SecondMostRecentYearBestSpeed = secondMostRecentYearBestSpeed;
	}
	public float getClaimingPrice() {
		return ClaimingPrice;
	}
	public void setClaimingPrice(float claimingPrice) {
		ClaimingPrice = claimingPrice;
	}
	public String getStatebredFlag() {
		return StatebredFlag;
	}
	public void setStatebredFlag(String statebredFlag) {
		StatebredFlag = statebredFlag;
	}
	public int getDaysSinceLastRace() {
		return DaysSinceLastRace;
	}
	public void setDaysSinceLastRace(int daysSinceLastRace) {
		DaysSinceLastRace = daysSinceLastRace;
	}
	public float getPrimePowerRating() {
		return PrimePowerRating;
	}
	public void setPrimePowerRating(float primePowerRating) {
		PrimePowerRating = primePowerRating;
	}
	public float getAverageClassRatingLastThree() {
		return AverageClassRatingLastThree;
	}
	public void setAverageClassRatingLastThree(float averageClassRatingLastThree) {
		AverageClassRatingLastThree = averageClassRatingLastThree;
	}
	public List<PastPerformance> getPastPerformances() {
		return PastPerformances;
	}
	public void setPastPerformances(List<PastPerformance> pastPerformances) {
		PastPerformances = pastPerformances;
	}
	@JsonIgnore
	public List<PastPerformance> getUnignoredPastPerformances() {
		List<PastPerformance> pps = new ArrayList<PastPerformance>();
		if (PastPerformances != null)
			for (PastPerformance pp : PastPerformances) {
				if (!pp.getIgnore()) pps.add(pp);
			}
		return pps;
	}	
	public Jockey getJockey() {
		return Jockey;
	}
	public void setJockey(Jockey jockey) {
		Jockey = jockey;
	}
	public int getApprenticeWeightAllowed() {
		return ApprenticeWeightAllowed;
	}
	public void setApprenticeWeightAllowed(int apprenticeWeightAllowed) {
		ApprenticeWeightAllowed = apprenticeWeightAllowed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(ARating);
		result = prime * result + Float.floatToIntBits(ARatingClass);
		result = prime * result + Float.floatToIntBits(ARatingConnections);
		result = prime * result + Float.floatToIntBits(ARatingFairValue);
		result = prime * result + Float.floatToIntBits(ARatingForm);
		result = prime * result + AllWeatherBestSpeed;
		result = prime * result + Float.floatToIntBits(AllWeatherEarnings);
		result = prime * result + AllWeatherPlaces;
		result = prime * result + AllWeatherShows;
		result = prime * result + AllWeatherStarts;
		result = prime * result + AllWeatherWins;
		result = prime * result + ((Angles == null) ? 0 : Angles.hashCode());
		result = prime * result + ApprenticeWeightAllowed;
		result = prime * result + ((AuctionLocationDate == null) ? 0 : AuctionLocationDate.hashCode());
		result = prime * result + Float.floatToIntBits(AuctionPrice);
		result = prime * result + Float.floatToIntBits(AverageClassRatingLastThree);
		result = prime * result + Float.floatToIntBits(AverageCompetitiveLevel);
		result = prime * result + Float.floatToIntBits(AvgAdjustedSpeedRating);
		result = prime * result + BasicFitness;
		result = prime * result + ((Breeder == null) ? 0 : Breeder.hashCode());
		result = prime * result + Float.floatToIntBits(BrisAvgLast3Class);
		result = prime * result + Float.floatToIntBits(BrisCurrentClass);
		result = prime * result + Float.floatToIntBits(ClaimingPrice);
		result = prime * result + Float.floatToIntBits(ClassRating);
		result = prime * result + Float.floatToIntBits(ClassShift);
		result = prime * result + Float.floatToIntBits(ClosingRatio);
		result = prime * result + ((Color == null) ? 0 : Color.hashCode());
		result = prime * result + ((CurrentTrack == null) ? 0 : CurrentTrack.hashCode());
		result = prime * result + CurrentYear;
		result = prime * result + Float.floatToIntBits(CurrentYearEarnings);
		result = prime * result + CurrentYearPlaces;
		result = prime * result + CurrentYearShows;
		result = prime * result + CurrentYearStarts;
		result = prime * result + CurrentYearWins;
		result = prime * result + ((Dam == null) ? 0 : Dam.hashCode());
		result = prime * result + Float.floatToIntBits(DamDPI);
		result = prime * result + ((DamDescription == null) ? 0 : DamDescription.hashCode());
		result = prime * result + Float.floatToIntBits(DamSireAWD);
		result = prime * result + DamSireFirstPercent;
		result = prime * result + DamSireFirstTurfPercent;
		result = prime * result + DamSireMudPercent;
		result = prime * result + DamSireMudStarts;
		result = prime * result + Float.floatToIntBits(DamSireSPI);
		result = prime * result + DamSireTurfPercent;
		result = prime * result + DamStakesWinners;
		result = prime * result + DamStarters;
		result = prime * result + DamTurfWinners;
		result = prime * result + DamTwoYearOldPercent;
		result = prime * result + DamWinners;
		result = prime * result + ((DamsSire == null) ? 0 : DamsSire.hashCode());
		result = prime * result + DaysSinceLastRace;
		result = prime * result + DirtBestSpeed;
		result = prime * result + Float.floatToIntBits(DirtEarnings);
		result = prime * result + DirtPlaces;
		result = prime * result + DirtShows;
		result = prime * result + DirtStarts;
		result = prime * result + DirtWins;
		result = prime * result + DistanceBestSpeed;
		result = prime * result + Float.floatToIntBits(DistanceEarnings);
		result = prime * result + DistancePlaces;
		result = prime * result + DistanceShows;
		result = prime * result + DistanceStarts;
		result = prime * result + DistanceWins;
		result = prime * result + Float.floatToIntBits(E1Avg);
		result = prime * result + Float.floatToIntBits(E2Avg);
		result = prime * result + Float.floatToIntBits(EarlyPosition);
		result = prime * result + ((Entry == null) ? 0 : Entry.hashCode());
		result = prime * result + ((Equipment == null) ? 0 : Equipment.hashCode());
		result = prime * result + ((FoalingMonth == null) ? 0 : FoalingMonth.hashCode());
		result = prime * result + FormPoints;
		result = prime * result + Float.floatToIntBits(FurlongDays);
		result = prime * result + ((Jockey == null) ? 0 : Jockey.hashCode());
		result = prime * result + Float.floatToIntBits(LastRaceStrength);
		result = prime * result + Float.floatToIntBits(LatePaceAvg);
		result = prime * result + LatePaceBestLast3;
		result = prime * result + LatePaceLast;
		result = prime * result + Float.floatToIntBits(LatePosition);
		result = prime * result + LifetimeBestSpeed;
		result = prime * result + Float.floatToIntBits(LifetimeEarnings);
		result = prime * result + LifetimePlaces;
		result = prime * result + LifetimeShows;
		result = prime * result + LifetimeStarts;
		result = prime * result + LifetimeWins;
		result = prime * result + Float.floatToIntBits(MLOdds);
		result = prime * result + ((MTOFlag == null) ? 0 : MTOFlag.hashCode());
		result = prime * result + MaxE2;
		result = prime * result + ((Medication == null) ? 0 : Medication.hashCode());
		result = prime * result + MostRecentYearBestSpeed;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((Owner == null) ? 0 : Owner.hashCode());
		result = prime * result + ((OwnerSilks == null) ? 0 : OwnerSilks.hashCode());
		result = prime * result + Float.floatToIntBits(PaceAdjustedLate);
		result = prime * result + ((PastPerformances == null) ? 0 : PastPerformances.hashCode());
		result = prime * result + ((PedigreeRatingDirt == null) ? 0 : PedigreeRatingDirt.hashCode());
		result = prime * result + ((PedigreeRatingDist == null) ? 0 : PedigreeRatingDist.hashCode());
		result = prime * result + ((PedigreeRatingMud == null) ? 0 : PedigreeRatingMud.hashCode());
		result = prime * result + ((PedigreeRatingTurf == null) ? 0 : PedigreeRatingTurf.hashCode());
		result = prime * result + PostPosition;
		result = prime * result + PreviousYear;
		result = prime * result + Float.floatToIntBits(PreviousYearEarnings);
		result = prime * result + PreviousYearPlaces;
		result = prime * result + PreviousYearShows;
		result = prime * result + PreviousYearStarts;
		result = prime * result + PreviousYearWins;
		result = prime * result + Float.floatToIntBits(PrimePower);
		result = prime * result + Float.floatToIntBits(PrimePowerRating);
		result = prime * result + ((ProgramNumber == null) ? 0 : ProgramNumber.hashCode());
		result = prime * result + ProgramPostPosition;
		result = prime * result + PurseShift;
		result = prime * result + RaceNumber;
		result = prime * result + ((RunStyle == null) ? 0 : RunStyle.hashCode());
		result = prime * result + SecondMostRecentYearBestSpeed;
		result = prime * result + ((Sex == null) ? 0 : Sex.hashCode());
		result = prime * result + ((Sire == null) ? 0 : Sire.hashCode());
		result = prime * result + Float.floatToIntBits(SireAWD);
		result = prime * result + SireFirstPercent;
		result = prime * result + SireFirstTurfPercent;
		result = prime * result + SireMudPercent;
		result = prime * result + SireMudStarts;
		result = prime * result + Float.floatToIntBits(SireSPI);
		result = prime * result + Float.floatToIntBits(SireStudFee);
		result = prime * result + SireTurfPercent;
		result = prime * result + ((SiresSire == null) ? 0 : SiresSire.hashCode());
		result = prime * result + SpeedPoints;
		result = prime * result + Float.floatToIntBits(SpeedRating);
		result = prime * result + ((StateCountry == null) ? 0 : StateCountry.hashCode());
		result = prime * result + ((StatebredFlag == null) ? 0 : StatebredFlag.hashCode());
		result = prime * result + TrackBestSpeed;
		result = prime * result + Float.floatToIntBits(TrackEarnings);
		result = prime * result + TrackPlaces;
		result = prime * result + TrackShows;
		result = prime * result + TrackStarts;
		result = prime * result + TrackWins;
		result = prime * result + ((Trainer == null) ? 0 : Trainer.hashCode());
		result = prime * result + TurfBestSpeed;
		result = prime * result + Float.floatToIntBits(TurfEarnings);
		result = prime * result + TurfPlaces;
		result = prime * result + TurfShows;
		result = prime * result + TurfStarts;
		result = prime * result + TurfWins;
		result = prime * result + Float.floatToIntBits(TurnTime);
		result = prime * result + Weight;
		result = prime * result + WetBestSpeed;
		result = prime * result + Float.floatToIntBits(WetEarnings);
		result = prime * result + WetPlaces;
		result = prime * result + WetShows;
		result = prime * result + WetStarts;
		result = prime * result + WetWins;
		result = prime * result + ((Workouts == null) ? 0 : Workouts.hashCode());
		result = prime * result + YearOfBirth;
		result = prime * result + ((_showDetails == null) ? 0 : _showDetails.hashCode());
		result = prime * result + age;
		result = prime * result + Float.floatToIntBits(bettingLine);
		result = prime * result + Float.floatToIntBits(combinedPaceAvg);
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + finishPosition;
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((pick == null) ? 0 : pick.hashCode());
		result = prime * result + Float.floatToIntBits(placePayout);
		result = prime * result + ((scratchedFlag == null) ? 0 : scratchedFlag.hashCode());
		result = prime * result + ((selection == null) ? 0 : selection.hashCode());
		result = prime * result + Float.floatToIntBits(showPayout);
		result = prime * result + Float.floatToIntBits(winPayout);
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
		Horse other = (Horse) obj;
		if (Float.floatToIntBits(ARating) != Float.floatToIntBits(other.ARating))
			return false;
		if (Float.floatToIntBits(ARatingClass) != Float.floatToIntBits(other.ARatingClass))
			return false;
		if (Float.floatToIntBits(ARatingConnections) != Float.floatToIntBits(other.ARatingConnections))
			return false;
		if (Float.floatToIntBits(ARatingFairValue) != Float.floatToIntBits(other.ARatingFairValue))
			return false;
		if (Float.floatToIntBits(ARatingForm) != Float.floatToIntBits(other.ARatingForm))
			return false;
		if (AllWeatherBestSpeed != other.AllWeatherBestSpeed)
			return false;
		if (Float.floatToIntBits(AllWeatherEarnings) != Float.floatToIntBits(other.AllWeatherEarnings))
			return false;
		if (AllWeatherPlaces != other.AllWeatherPlaces)
			return false;
		if (AllWeatherShows != other.AllWeatherShows)
			return false;
		if (AllWeatherStarts != other.AllWeatherStarts)
			return false;
		if (AllWeatherWins != other.AllWeatherWins)
			return false;
		if (Angles == null) {
			if (other.Angles != null)
				return false;
		} else if (!Angles.equals(other.Angles))
			return false;
		if (ApprenticeWeightAllowed != other.ApprenticeWeightAllowed)
			return false;
		if (AuctionLocationDate == null) {
			if (other.AuctionLocationDate != null)
				return false;
		} else if (!AuctionLocationDate.equals(other.AuctionLocationDate))
			return false;
		if (Float.floatToIntBits(AuctionPrice) != Float.floatToIntBits(other.AuctionPrice))
			return false;
		if (Float.floatToIntBits(AverageClassRatingLastThree) != Float
				.floatToIntBits(other.AverageClassRatingLastThree))
			return false;
		if (Float.floatToIntBits(AverageCompetitiveLevel) != Float.floatToIntBits(other.AverageCompetitiveLevel))
			return false;
		if (Float.floatToIntBits(AvgAdjustedSpeedRating) != Float.floatToIntBits(other.AvgAdjustedSpeedRating))
			return false;
		if (BasicFitness != other.BasicFitness)
			return false;
		if (Breeder == null) {
			if (other.Breeder != null)
				return false;
		} else if (!Breeder.equals(other.Breeder))
			return false;
		if (Float.floatToIntBits(BrisAvgLast3Class) != Float.floatToIntBits(other.BrisAvgLast3Class))
			return false;
		if (Float.floatToIntBits(BrisCurrentClass) != Float.floatToIntBits(other.BrisCurrentClass))
			return false;
		if (Float.floatToIntBits(ClaimingPrice) != Float.floatToIntBits(other.ClaimingPrice))
			return false;
		if (Float.floatToIntBits(ClassRating) != Float.floatToIntBits(other.ClassRating))
			return false;
		if (Float.floatToIntBits(ClassShift) != Float.floatToIntBits(other.ClassShift))
			return false;
		if (Float.floatToIntBits(ClosingRatio) != Float.floatToIntBits(other.ClosingRatio))
			return false;
		if (Color == null) {
			if (other.Color != null)
				return false;
		} else if (!Color.equals(other.Color))
			return false;
		if (CurrentTrack == null) {
			if (other.CurrentTrack != null)
				return false;
		} else if (!CurrentTrack.equals(other.CurrentTrack))
			return false;
		if (CurrentYear != other.CurrentYear)
			return false;
		if (Float.floatToIntBits(CurrentYearEarnings) != Float.floatToIntBits(other.CurrentYearEarnings))
			return false;
		if (CurrentYearPlaces != other.CurrentYearPlaces)
			return false;
		if (CurrentYearShows != other.CurrentYearShows)
			return false;
		if (CurrentYearStarts != other.CurrentYearStarts)
			return false;
		if (CurrentYearWins != other.CurrentYearWins)
			return false;
		if (Dam == null) {
			if (other.Dam != null)
				return false;
		} else if (!Dam.equals(other.Dam))
			return false;
		if (Float.floatToIntBits(DamDPI) != Float.floatToIntBits(other.DamDPI))
			return false;
		if (DamDescription == null) {
			if (other.DamDescription != null)
				return false;
		} else if (!DamDescription.equals(other.DamDescription))
			return false;
		if (Float.floatToIntBits(DamSireAWD) != Float.floatToIntBits(other.DamSireAWD))
			return false;
		if (DamSireFirstPercent != other.DamSireFirstPercent)
			return false;
		if (DamSireFirstTurfPercent != other.DamSireFirstTurfPercent)
			return false;
		if (DamSireMudPercent != other.DamSireMudPercent)
			return false;
		if (DamSireMudStarts != other.DamSireMudStarts)
			return false;
		if (Float.floatToIntBits(DamSireSPI) != Float.floatToIntBits(other.DamSireSPI))
			return false;
		if (DamSireTurfPercent != other.DamSireTurfPercent)
			return false;
		if (DamStakesWinners != other.DamStakesWinners)
			return false;
		if (DamStarters != other.DamStarters)
			return false;
		if (DamTurfWinners != other.DamTurfWinners)
			return false;
		if (DamTwoYearOldPercent != other.DamTwoYearOldPercent)
			return false;
		if (DamWinners != other.DamWinners)
			return false;
		if (DamsSire == null) {
			if (other.DamsSire != null)
				return false;
		} else if (!DamsSire.equals(other.DamsSire))
			return false;
		if (DaysSinceLastRace != other.DaysSinceLastRace)
			return false;
		if (DirtBestSpeed != other.DirtBestSpeed)
			return false;
		if (Float.floatToIntBits(DirtEarnings) != Float.floatToIntBits(other.DirtEarnings))
			return false;
		if (DirtPlaces != other.DirtPlaces)
			return false;
		if (DirtShows != other.DirtShows)
			return false;
		if (DirtStarts != other.DirtStarts)
			return false;
		if (DirtWins != other.DirtWins)
			return false;
		if (DistanceBestSpeed != other.DistanceBestSpeed)
			return false;
		if (Float.floatToIntBits(DistanceEarnings) != Float.floatToIntBits(other.DistanceEarnings))
			return false;
		if (DistancePlaces != other.DistancePlaces)
			return false;
		if (DistanceShows != other.DistanceShows)
			return false;
		if (DistanceStarts != other.DistanceStarts)
			return false;
		if (DistanceWins != other.DistanceWins)
			return false;
		if (Float.floatToIntBits(E1Avg) != Float.floatToIntBits(other.E1Avg))
			return false;
		if (Float.floatToIntBits(E2Avg) != Float.floatToIntBits(other.E2Avg))
			return false;
		if (Float.floatToIntBits(EarlyPosition) != Float.floatToIntBits(other.EarlyPosition))
			return false;
		if (Entry == null) {
			if (other.Entry != null)
				return false;
		} else if (!Entry.equals(other.Entry))
			return false;
		if (Equipment != other.Equipment)
			return false;
		if (FoalingMonth == null) {
			if (other.FoalingMonth != null)
				return false;
		} else if (!FoalingMonth.equals(other.FoalingMonth))
			return false;
		if (FormPoints != other.FormPoints)
			return false;
		if (Float.floatToIntBits(FurlongDays) != Float.floatToIntBits(other.FurlongDays))
			return false;
		if (Jockey == null) {
			if (other.Jockey != null)
				return false;
		} else if (!Jockey.equals(other.Jockey))
			return false;
		if (Float.floatToIntBits(LastRaceStrength) != Float.floatToIntBits(other.LastRaceStrength))
			return false;
		if (Float.floatToIntBits(LatePaceAvg) != Float.floatToIntBits(other.LatePaceAvg))
			return false;
		if (LatePaceBestLast3 != other.LatePaceBestLast3)
			return false;
		if (LatePaceLast != other.LatePaceLast)
			return false;
		if (Float.floatToIntBits(LatePosition) != Float.floatToIntBits(other.LatePosition))
			return false;
		if (LifetimeBestSpeed != other.LifetimeBestSpeed)
			return false;
		if (Float.floatToIntBits(LifetimeEarnings) != Float.floatToIntBits(other.LifetimeEarnings))
			return false;
		if (LifetimePlaces != other.LifetimePlaces)
			return false;
		if (LifetimeShows != other.LifetimeShows)
			return false;
		if (LifetimeStarts != other.LifetimeStarts)
			return false;
		if (LifetimeWins != other.LifetimeWins)
			return false;
		if (Float.floatToIntBits(MLOdds) != Float.floatToIntBits(other.MLOdds))
			return false;
		if (MTOFlag == null) {
			if (other.MTOFlag != null)
				return false;
		} else if (!MTOFlag.equals(other.MTOFlag))
			return false;
		if (MaxE2 != other.MaxE2)
			return false;
		if (Medication != other.Medication)
			return false;
		if (MostRecentYearBestSpeed != other.MostRecentYearBestSpeed)
			return false;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (Owner == null) {
			if (other.Owner != null)
				return false;
		} else if (!Owner.equals(other.Owner))
			return false;
		if (OwnerSilks == null) {
			if (other.OwnerSilks != null)
				return false;
		} else if (!OwnerSilks.equals(other.OwnerSilks))
			return false;
		if (Float.floatToIntBits(PaceAdjustedLate) != Float.floatToIntBits(other.PaceAdjustedLate))
			return false;
		if (PastPerformances == null) {
			if (other.PastPerformances != null)
				return false;
		} else if (!PastPerformances.equals(other.PastPerformances))
			return false;
		if (PedigreeRatingDirt == null) {
			if (other.PedigreeRatingDirt != null)
				return false;
		} else if (!PedigreeRatingDirt.equals(other.PedigreeRatingDirt))
			return false;
		if (PedigreeRatingDist == null) {
			if (other.PedigreeRatingDist != null)
				return false;
		} else if (!PedigreeRatingDist.equals(other.PedigreeRatingDist))
			return false;
		if (PedigreeRatingMud == null) {
			if (other.PedigreeRatingMud != null)
				return false;
		} else if (!PedigreeRatingMud.equals(other.PedigreeRatingMud))
			return false;
		if (PedigreeRatingTurf == null) {
			if (other.PedigreeRatingTurf != null)
				return false;
		} else if (!PedigreeRatingTurf.equals(other.PedigreeRatingTurf))
			return false;
		if (PostPosition != other.PostPosition)
			return false;
		if (PreviousYear != other.PreviousYear)
			return false;
		if (Float.floatToIntBits(PreviousYearEarnings) != Float.floatToIntBits(other.PreviousYearEarnings))
			return false;
		if (PreviousYearPlaces != other.PreviousYearPlaces)
			return false;
		if (PreviousYearShows != other.PreviousYearShows)
			return false;
		if (PreviousYearStarts != other.PreviousYearStarts)
			return false;
		if (PreviousYearWins != other.PreviousYearWins)
			return false;
		if (Float.floatToIntBits(PrimePower) != Float.floatToIntBits(other.PrimePower))
			return false;
		if (Float.floatToIntBits(PrimePowerRating) != Float.floatToIntBits(other.PrimePowerRating))
			return false;
		if (ProgramNumber == null) {
			if (other.ProgramNumber != null)
				return false;
		} else if (!ProgramNumber.equals(other.ProgramNumber))
			return false;
		if (ProgramPostPosition != other.ProgramPostPosition)
			return false;
		if (PurseShift != other.PurseShift)
			return false;
		if (RaceNumber != other.RaceNumber)
			return false;
		if (RunStyle == null) {
			if (other.RunStyle != null)
				return false;
		} else if (!RunStyle.equals(other.RunStyle))
			return false;
		if (SecondMostRecentYearBestSpeed != other.SecondMostRecentYearBestSpeed)
			return false;
		if (Sex == null) {
			if (other.Sex != null)
				return false;
		} else if (!Sex.equals(other.Sex))
			return false;
		if (Sire == null) {
			if (other.Sire != null)
				return false;
		} else if (!Sire.equals(other.Sire))
			return false;
		if (Float.floatToIntBits(SireAWD) != Float.floatToIntBits(other.SireAWD))
			return false;
		if (SireFirstPercent != other.SireFirstPercent)
			return false;
		if (SireFirstTurfPercent != other.SireFirstTurfPercent)
			return false;
		if (SireMudPercent != other.SireMudPercent)
			return false;
		if (SireMudStarts != other.SireMudStarts)
			return false;
		if (Float.floatToIntBits(SireSPI) != Float.floatToIntBits(other.SireSPI))
			return false;
		if (Float.floatToIntBits(SireStudFee) != Float.floatToIntBits(other.SireStudFee))
			return false;
		if (SireTurfPercent != other.SireTurfPercent)
			return false;
		if (SiresSire == null) {
			if (other.SiresSire != null)
				return false;
		} else if (!SiresSire.equals(other.SiresSire))
			return false;
		if (SpeedPoints != other.SpeedPoints)
			return false;
		if (Float.floatToIntBits(SpeedRating) != Float.floatToIntBits(other.SpeedRating))
			return false;
		if (StateCountry == null) {
			if (other.StateCountry != null)
				return false;
		} else if (!StateCountry.equals(other.StateCountry))
			return false;
		if (StatebredFlag == null) {
			if (other.StatebredFlag != null)
				return false;
		} else if (!StatebredFlag.equals(other.StatebredFlag))
			return false;
		if (TrackBestSpeed != other.TrackBestSpeed)
			return false;
		if (Float.floatToIntBits(TrackEarnings) != Float.floatToIntBits(other.TrackEarnings))
			return false;
		if (TrackPlaces != other.TrackPlaces)
			return false;
		if (TrackShows != other.TrackShows)
			return false;
		if (TrackStarts != other.TrackStarts)
			return false;
		if (TrackWins != other.TrackWins)
			return false;
		if (Trainer == null) {
			if (other.Trainer != null)
				return false;
		} else if (!Trainer.equals(other.Trainer))
			return false;
		if (TurfBestSpeed != other.TurfBestSpeed)
			return false;
		if (Float.floatToIntBits(TurfEarnings) != Float.floatToIntBits(other.TurfEarnings))
			return false;
		if (TurfPlaces != other.TurfPlaces)
			return false;
		if (TurfShows != other.TurfShows)
			return false;
		if (TurfStarts != other.TurfStarts)
			return false;
		if (TurfWins != other.TurfWins)
			return false;
		if (Float.floatToIntBits(TurnTime) != Float.floatToIntBits(other.TurnTime))
			return false;
		if (Weight != other.Weight)
			return false;
		if (WetBestSpeed != other.WetBestSpeed)
			return false;
		if (Float.floatToIntBits(WetEarnings) != Float.floatToIntBits(other.WetEarnings))
			return false;
		if (WetPlaces != other.WetPlaces)
			return false;
		if (WetShows != other.WetShows)
			return false;
		if (WetStarts != other.WetStarts)
			return false;
		if (WetWins != other.WetWins)
			return false;
		if (Workouts == null) {
			if (other.Workouts != null)
				return false;
		} else if (!Workouts.equals(other.Workouts))
			return false;
		if (YearOfBirth != other.YearOfBirth)
			return false;
		if (_showDetails == null) {
			if (other._showDetails != null)
				return false;
		} else if (!_showDetails.equals(other._showDetails))
			return false;
		if (age != other.age)
			return false;
		if (Float.floatToIntBits(bettingLine) != Float.floatToIntBits(other.bettingLine))
			return false;
		if (Float.floatToIntBits(combinedPaceAvg) != Float.floatToIntBits(other.combinedPaceAvg))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (finishPosition != other.finishPosition)
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (pick == null) {
			if (other.pick != null)
				return false;
		} else if (!pick.equals(other.pick))
			return false;
		if (Float.floatToIntBits(placePayout) != Float.floatToIntBits(other.placePayout))
			return false;
		if (scratchedFlag == null) {
			if (other.scratchedFlag != null)
				return false;
		} else if (!scratchedFlag.equals(other.scratchedFlag))
			return false;
		if (selection == null) {
			if (other.selection != null)
				return false;
		} else if (!selection.equals(other.selection))
			return false;
		if (Float.floatToIntBits(showPayout) != Float.floatToIntBits(other.showPayout))
			return false;
		if (Float.floatToIntBits(winPayout) != Float.floatToIntBits(other.winPayout))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Horse [RaceNumber=").append(RaceNumber).append(", Owner=").append(Owner)
				.append(", OwnerSilks=").append(OwnerSilks).append(", MTOFlag=").append(MTOFlag)
				.append(", ProgramNumber=").append(ProgramNumber).append(", MLOdds=").append(MLOdds).append(", Name=")
				.append(Name).append(", YearOfBirth=").append(YearOfBirth).append(", FoalingMonth=")
				.append(FoalingMonth).append(", Sex=").append(Sex).append(", Color=").append(Color).append(", Weight=")
				.append(Weight).append(", ApprenticeWeightAllowed=").append(ApprenticeWeightAllowed).append(", Sire=")
				.append(Sire).append(", SiresSire=").append(SiresSire).append(", Dam=").append(Dam)
				.append(", DamsSire=").append(DamsSire).append(", Breeder=").append(Breeder).append(", SireStudFee=")
				.append(SireStudFee).append(", AuctionPrice=").append(AuctionPrice).append(", AuctionLocationDate=")
				.append(AuctionLocationDate).append(", PedigreeRatingDirt=").append(PedigreeRatingDirt)
				.append(", PedigreeRatingMud=").append(PedigreeRatingMud).append(", PedigreeRatingTurf=")
				.append(PedigreeRatingTurf).append(", PedigreeRatingDist=").append(PedigreeRatingDist)
				.append(", StateCountry=").append(StateCountry).append(", ProgramPostPosition=")
				.append(ProgramPostPosition).append(", Medication=").append(Medication).append(", Equipment=")
				.append(Equipment).append(", LifetimeStarts=").append(LifetimeStarts).append(", LifetimeWins=")
				.append(LifetimeWins).append(", LifetimePlaces=").append(LifetimePlaces).append(", LifetimeShows=")
				.append(LifetimeShows).append(", LifetimeBestSpeed=").append(LifetimeBestSpeed)
				.append(", LifetimeEarnings=").append(LifetimeEarnings).append(", DistanceStarts=")
				.append(DistanceStarts).append(", DistanceWins=").append(DistanceWins).append(", DistancePlaces=")
				.append(DistancePlaces).append(", DistanceShows=").append(DistanceShows).append(", DistanceEarnings=")
				.append(DistanceEarnings).append(", DistanceBestSpeed=").append(DistanceBestSpeed)
				.append(", TrackStarts=").append(TrackStarts).append(", TrackWins=").append(TrackWins)
				.append(", TrackPlaces=").append(TrackPlaces).append(", TrackShows=").append(TrackShows)
				.append(", TrackEarnings=").append(TrackEarnings).append(", TrackBestSpeed=").append(TrackBestSpeed)
				.append(", DirtStarts=").append(DirtStarts).append(", DirtWins=").append(DirtWins)
				.append(", DirtPlaces=").append(DirtPlaces).append(", DirtShows=").append(DirtShows)
				.append(", DirtEarnings=").append(DirtEarnings).append(", DirtBestSpeed=").append(DirtBestSpeed)
				.append(", TurfStarts=").append(TurfStarts).append(", TurfWins=").append(TurfWins)
				.append(", TurfPlaces=").append(TurfPlaces).append(", TurfShows=").append(TurfShows)
				.append(", TurfEarnings=").append(TurfEarnings).append(", TurfBestSpeed=").append(TurfBestSpeed)
				.append(", WetStarts=").append(WetStarts).append(", WetWins=").append(WetWins).append(", WetPlaces=")
				.append(WetPlaces).append(", WetShows=").append(WetShows).append(", WetEarnings=").append(WetEarnings)
				.append(", WetBestSpeed=").append(WetBestSpeed).append(", AllWeatherStarts=").append(AllWeatherStarts)
				.append(", AllWeatherWins=").append(AllWeatherWins).append(", AllWeatherPlaces=")
				.append(AllWeatherPlaces).append(", AllWeatherShows=").append(AllWeatherShows)
				.append(", AllWeatherBestSpeed=").append(AllWeatherBestSpeed).append(", AllWeatherEarnings=")
				.append(AllWeatherEarnings).append(", CurrentYear=").append(CurrentYear).append(", CurrentYearStarts=")
				.append(CurrentYearStarts).append(", CurrentYearWins=").append(CurrentYearWins)
				.append(", CurrentYearPlaces=").append(CurrentYearPlaces).append(", CurrentYearShows=")
				.append(CurrentYearShows).append(", CurrentYearEarnings=").append(CurrentYearEarnings)
				.append(", PreviousYear=").append(PreviousYear).append(", PreviousYearStarts=")
				.append(PreviousYearStarts).append(", PreviousYearWins=").append(PreviousYearWins)
				.append(", PreviousYearPlaces=").append(PreviousYearPlaces).append(", PreviousYearShows=")
				.append(PreviousYearShows).append(", PreviousYearEarnings=").append(PreviousYearEarnings)
				.append(", MostRecentYearBestSpeed=").append(MostRecentYearBestSpeed)
				.append(", SecondMostRecentYearBestSpeed=").append(SecondMostRecentYearBestSpeed).append(", RunStyle=")
				.append(RunStyle).append(", SpeedPoints=").append(SpeedPoints).append(", LowClaimingPrice=")
				.append(ClaimingPrice).append(", DaysSinceLastRace=").append(DaysSinceLastRace)
				.append(", PrimePowerRating=").append(PrimePowerRating).append(", AverageClassRatingLastThree=")
				.append(AverageClassRatingLastThree).append(", StatebredFlag=").append(StatebredFlag)
				.append(", Trainer=").append(Trainer).append(", Jockey=").append(Jockey).append(", Workouts=")
				.append(Workouts).append(", PastPerformances=").append(PastPerformances).append(", E1Avg=")
				.append(E1Avg).append(", E2Avg=").append(E2Avg).append(", MaxE2=").append(MaxE2)
				.append(", LatePaceAvg=").append(LatePaceAvg).append(", EarlyPosition=").append(EarlyPosition)
				.append(", LatePosition=").append(LatePosition).append(", ClosingRatio=").append(ClosingRatio)
				.append(", LatePaceBestLast3=").append(LatePaceBestLast3).append(", PaceAdjustedLate=")
				.append(PaceAdjustedLate).append(", LatePaceLast=").append(LatePaceLast).append(", ClassRating=")
				.append(ClassRating).append(", LastRaceStrength=").append(LastRaceStrength).append(", ClassShift=")
				.append(ClassShift).append(", PurseShift=").append(PurseShift).append(", SpeedRating=")
				.append(SpeedRating).append(", BasicFitness=").append(BasicFitness).append(", FormPoints=")
				.append(FormPoints).append(", FurlongDays=").append(FurlongDays).append(", TurnTime=").append(TurnTime)
				.append(", ARatingClass=").append(ARatingClass).append(", ARatingForm=").append(ARatingForm)
				.append(", ARatingConnections=").append(ARatingConnections).append(", ARating=").append(ARating)
				.append("]");
		return builder2.toString();
	}
	public Horse() {
		super();
	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	@Generated("SparkTools")
	public static final class Builder {
		private int RaceNumber;
		private String Owner;
		private String OwnerSilks;
		private String MTOFlag;
		private String ProgramNumber;
		private int PostPosition;
		private String Entry;
		private float MLOdds;
		private String Name;
		private int YearOfBirth;
		private String FoalingMonth;
		private String Sex;
		private String Color;
		private int Weight;
		private int ApprenticeWeightAllowed;
		private String Sire;
		private String SiresSire;
		private String Dam;
		private String DamsSire;
		private String Breeder;
		private float SireStudFee;
		private float AuctionPrice;
		private String AuctionLocationDate;
		private String PedigreeRatingDirt;
		private String PedigreeRatingMud;
		private String PedigreeRatingTurf;
		private String PedigreeRatingDist;
		private String StateCountry;
		private int ProgramPostPosition;
		private MedicationType Medication;
		private EquipmentChangeType Equipment;
		private int LifetimeStarts;
		private int LifetimeWins;
		private int LifetimePlaces;
		private int LifetimeShows;
		private int LifetimeBestSpeed;
		private float LifetimeEarnings;
		private int DistanceStarts;
		private int DistanceWins;
		private int DistancePlaces;
		private int DistanceShows;
		private float DistanceEarnings;
		private int DistanceBestSpeed;
		private int TrackStarts;
		private int TrackWins;
		private int TrackPlaces;
		private int TrackShows;
		private float TrackEarnings;
		private int TrackBestSpeed;
		private int DirtStarts;
		private int DirtWins;
		private int DirtPlaces;
		private int DirtShows;
		private float DirtEarnings;
		private int DirtBestSpeed;
		private String CurrentTrack;
		private int TurfStarts;
		private int TurfWins;
		private int TurfPlaces;
		private int TurfShows;
		private float TurfEarnings;
		private int TurfBestSpeed;
		private int WetStarts;
		private int WetWins;
		private int WetPlaces;
		private int WetShows;
		private float WetEarnings;
		private int WetBestSpeed;
		private int AllWeatherStarts;
		private int AllWeatherWins;
		private int AllWeatherPlaces;
		private int AllWeatherShows;
		private int AllWeatherBestSpeed;
		private float AllWeatherEarnings;
		private int CurrentYear;
		private int CurrentYearStarts;
		private int CurrentYearWins;
		private int CurrentYearPlaces;
		private int CurrentYearShows;
		private float CurrentYearEarnings;
		private int PreviousYear;
		private int PreviousYearStarts;
		private int PreviousYearWins;
		private int PreviousYearPlaces;
		private int PreviousYearShows;
		private float PreviousYearEarnings;
		private int MostRecentYearBestSpeed;
		private int SecondMostRecentYearBestSpeed;
		private String RunStyle;
		private int SpeedPoints;
		private float ClaimingPrice;
		private int DaysSinceLastRace;
		private float PrimePowerRating;
		private float AverageClassRatingLastThree;
		private String StatebredFlag;
		private Trainer Trainer;
		private Jockey Jockey;
		private List<Workout> Workouts = Collections.emptyList();
		private List<PastPerformance> PastPerformances = Collections.emptyList();
		private float E1Avg;
		private float E2Avg;
		private int MaxE2;
		private float LatePaceAvg;
		private float EarlyPosition;
		private float LatePosition;
		private float ClosingRatio;
		private int LatePaceBestLast3;
		private float PaceAdjustedLate;
		private int LatePaceLast;
		private float ClassRating;
		private float AverageCompetitiveLevel;
		private float LastRaceStrength;
		private float ClassShift;
		private int PurseShift;
		private float SpeedRating;
		private int BasicFitness;
		private int FormPoints;
		private float FurlongDays;
		private float TurnTime;
		private float AvgAdjustedSpeedRating;
		private float combinedPaceAvg;
		private int age;
		private float ARatingClass;
		private float ARatingForm;
		private float ARatingConnections;
		private float ARating;
		private Boolean scratchedFlag;
		private Boolean _showDetails;
		private List<String> Angles = Collections.emptyList();
		private String selection;
		private float bettingLine;
		private String note;
		private Boolean pick;
		private int finishPosition;
		private float winPayout;
		private float placePayout;
		private float showPayout;
		private float ARatingFairValue;
		private float PrimePower;
		private float SireAWD;
		private int SireMudPercent;
		private int SireMudStarts;
		private int SireFirstPercent;
		private int SireTurfPercent;
		private int SireFirstTurfPercent;
		private float SireSPI;
		private float DamSireAWD;
		private int DamSireMudPercent;
		private int DamSireMudStarts;
		private int DamSireFirstPercent;
		private int DamSireTurfPercent;
		private int DamSireFirstTurfPercent;
		private float DamSireSPI;
		private String DamDescription;
		private int DamTwoYearOldPercent;
		private int DamTurfWinners;
		private int DamStarters;
		private int DamWinners;
		private int DamStakesWinners;
		private float DamDPI;
		private float BrisCurrentClass;
		private float BrisAvgLast3Class;
		private String flag;
		private String comment;

		private Builder() {
		}

		public Builder withRaceNumber(int RaceNumber) {
			this.RaceNumber = RaceNumber;
			return this;
		}

		public Builder withOwner(String Owner) {
			this.Owner = Owner;
			return this;
		}

		public Builder withOwnerSilks(String OwnerSilks) {
			this.OwnerSilks = OwnerSilks;
			return this;
		}

		public Builder withMTOFlag(String MTOFlag) {
			this.MTOFlag = MTOFlag;
			return this;
		}

		public Builder withProgramNumber(String ProgramNumber) {
			this.ProgramNumber = ProgramNumber;
			return this;
		}

		public Builder withPostPosition(int PostPosition) {
			this.PostPosition = PostPosition;
			return this;
		}

		public Builder withEntry(String Entry) {
			this.Entry = Entry;
			return this;
		}

		public Builder withMLOdds(float MLOdds) {
			this.MLOdds = MLOdds;
			return this;
		}

		public Builder withName(String Name) {
			this.Name = Name;
			return this;
		}

		public Builder withYearOfBirth(int YearOfBirth) {
			this.YearOfBirth = YearOfBirth;
			return this;
		}

		public Builder withFoalingMonth(String FoalingMonth) {
			this.FoalingMonth = FoalingMonth;
			return this;
		}

		public Builder withSex(String Sex) {
			this.Sex = Sex;
			return this;
		}

		public Builder withColor(String Color) {
			this.Color = Color;
			return this;
		}

		public Builder withWeight(int Weight) {
			this.Weight = Weight;
			return this;
		}

		public Builder withApprenticeWeightAllowed(int ApprenticeWeightAllowed) {
			this.ApprenticeWeightAllowed = ApprenticeWeightAllowed;
			return this;
		}

		public Builder withSire(String Sire) {
			this.Sire = Sire;
			return this;
		}

		public Builder withSiresSire(String SiresSire) {
			this.SiresSire = SiresSire;
			return this;
		}

		public Builder withDam(String Dam) {
			this.Dam = Dam;
			return this;
		}

		public Builder withDamsSire(String DamsSire) {
			this.DamsSire = DamsSire;
			return this;
		}

		public Builder withBreeder(String Breeder) {
			this.Breeder = Breeder;
			return this;
		}

		public Builder withSireStudFee(float SireStudFee) {
			this.SireStudFee = SireStudFee;
			return this;
		}

		public Builder withAuctionPrice(float AuctionPrice) {
			this.AuctionPrice = AuctionPrice;
			return this;
		}

		public Builder withAuctionLocationDate(String AuctionLocationDate) {
			this.AuctionLocationDate = AuctionLocationDate;
			return this;
		}

		public Builder withPedigreeRatingDirt(String PedigreeRatingDirt) {
			this.PedigreeRatingDirt = PedigreeRatingDirt;
			return this;
		}

		public Builder withPedigreeRatingMud(String PedigreeRatingMud) {
			this.PedigreeRatingMud = PedigreeRatingMud;
			return this;
		}

		public Builder withPedigreeRatingTurf(String PedigreeRatingTurf) {
			this.PedigreeRatingTurf = PedigreeRatingTurf;
			return this;
		}

		public Builder withPedigreeRatingDist(String PedigreeRatingDist) {
			this.PedigreeRatingDist = PedigreeRatingDist;
			return this;
		}

		public Builder withStateCountry(String StateCountry) {
			this.StateCountry = StateCountry;
			return this;
		}

		public Builder withProgramPostPosition(int ProgramPostPosition) {
			this.ProgramPostPosition = ProgramPostPosition;
			return this;
		}

		public Builder withMedication(MedicationType Medication) {
			this.Medication = Medication;
			return this;
		}

		public Builder withEquipment(EquipmentChangeType Equipment) {
			this.Equipment = Equipment;
			return this;
		}

		public Builder withLifetimeStarts(int LifetimeStarts) {
			this.LifetimeStarts = LifetimeStarts;
			return this;
		}

		public Builder withLifetimeWins(int LifetimeWins) {
			this.LifetimeWins = LifetimeWins;
			return this;
		}

		public Builder withLifetimePlaces(int LifetimePlaces) {
			this.LifetimePlaces = LifetimePlaces;
			return this;
		}

		public Builder withLifetimeShows(int LifetimeShows) {
			this.LifetimeShows = LifetimeShows;
			return this;
		}

		public Builder withLifetimeBestSpeed(int LifetimeBestSpeed) {
			this.LifetimeBestSpeed = LifetimeBestSpeed;
			return this;
		}

		public Builder withLifetimeEarnings(float LifetimeEarnings) {
			this.LifetimeEarnings = LifetimeEarnings;
			return this;
		}

		public Builder withDistanceStarts(int DistanceStarts) {
			this.DistanceStarts = DistanceStarts;
			return this;
		}

		public Builder withDistanceWins(int DistanceWins) {
			this.DistanceWins = DistanceWins;
			return this;
		}

		public Builder withDistancePlaces(int DistancePlaces) {
			this.DistancePlaces = DistancePlaces;
			return this;
		}

		public Builder withDistanceShows(int DistanceShows) {
			this.DistanceShows = DistanceShows;
			return this;
		}

		public Builder withDistanceEarnings(float DistanceEarnings) {
			this.DistanceEarnings = DistanceEarnings;
			return this;
		}

		public Builder withDistanceBestSpeed(int DistanceBestSpeed) {
			this.DistanceBestSpeed = DistanceBestSpeed;
			return this;
		}

		public Builder withTrackStarts(int TrackStarts) {
			this.TrackStarts = TrackStarts;
			return this;
		}

		public Builder withTrackWins(int TrackWins) {
			this.TrackWins = TrackWins;
			return this;
		}

		public Builder withTrackPlaces(int TrackPlaces) {
			this.TrackPlaces = TrackPlaces;
			return this;
		}

		public Builder withTrackShows(int TrackShows) {
			this.TrackShows = TrackShows;
			return this;
		}

		public Builder withTrackEarnings(float TrackEarnings) {
			this.TrackEarnings = TrackEarnings;
			return this;
		}

		public Builder withTrackBestSpeed(int TrackBestSpeed) {
			this.TrackBestSpeed = TrackBestSpeed;
			return this;
		}

		public Builder withDirtStarts(int DirtStarts) {
			this.DirtStarts = DirtStarts;
			return this;
		}

		public Builder withDirtWins(int DirtWins) {
			this.DirtWins = DirtWins;
			return this;
		}

		public Builder withDirtPlaces(int DirtPlaces) {
			this.DirtPlaces = DirtPlaces;
			return this;
		}

		public Builder withDirtShows(int DirtShows) {
			this.DirtShows = DirtShows;
			return this;
		}

		public Builder withDirtEarnings(float DirtEarnings) {
			this.DirtEarnings = DirtEarnings;
			return this;
		}

		public Builder withDirtBestSpeed(int DirtBestSpeed) {
			this.DirtBestSpeed = DirtBestSpeed;
			return this;
		}

		public Builder withCurrentTrack(String CurrentTrack) {
			this.CurrentTrack = CurrentTrack;
			return this;
		}

		public Builder withTurfStarts(int TurfStarts) {
			this.TurfStarts = TurfStarts;
			return this;
		}

		public Builder withTurfWins(int TurfWins) {
			this.TurfWins = TurfWins;
			return this;
		}

		public Builder withTurfPlaces(int TurfPlaces) {
			this.TurfPlaces = TurfPlaces;
			return this;
		}

		public Builder withTurfShows(int TurfShows) {
			this.TurfShows = TurfShows;
			return this;
		}

		public Builder withTurfEarnings(float TurfEarnings) {
			this.TurfEarnings = TurfEarnings;
			return this;
		}

		public Builder withTurfBestSpeed(int TurfBestSpeed) {
			this.TurfBestSpeed = TurfBestSpeed;
			return this;
		}

		public Builder withWetStarts(int WetStarts) {
			this.WetStarts = WetStarts;
			return this;
		}

		public Builder withWetWins(int WetWins) {
			this.WetWins = WetWins;
			return this;
		}

		public Builder withWetPlaces(int WetPlaces) {
			this.WetPlaces = WetPlaces;
			return this;
		}

		public Builder withWetShows(int WetShows) {
			this.WetShows = WetShows;
			return this;
		}

		public Builder withWetEarnings(float WetEarnings) {
			this.WetEarnings = WetEarnings;
			return this;
		}

		public Builder withWetBestSpeed(int WetBestSpeed) {
			this.WetBestSpeed = WetBestSpeed;
			return this;
		}

		public Builder withAllWeatherStarts(int AllWeatherStarts) {
			this.AllWeatherStarts = AllWeatherStarts;
			return this;
		}

		public Builder withAllWeatherWins(int AllWeatherWins) {
			this.AllWeatherWins = AllWeatherWins;
			return this;
		}

		public Builder withAllWeatherPlaces(int AllWeatherPlaces) {
			this.AllWeatherPlaces = AllWeatherPlaces;
			return this;
		}

		public Builder withAllWeatherShows(int AllWeatherShows) {
			this.AllWeatherShows = AllWeatherShows;
			return this;
		}

		public Builder withAllWeatherBestSpeed(int AllWeatherBestSpeed) {
			this.AllWeatherBestSpeed = AllWeatherBestSpeed;
			return this;
		}

		public Builder withAllWeatherEarnings(float AllWeatherEarnings) {
			this.AllWeatherEarnings = AllWeatherEarnings;
			return this;
		}

		public Builder withCurrentYear(int CurrentYear) {
			this.CurrentYear = CurrentYear;
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

		public Builder withCurrentYearEarnings(float CurrentYearEarnings) {
			this.CurrentYearEarnings = CurrentYearEarnings;
			return this;
		}

		public Builder withPreviousYear(int PreviousYear) {
			this.PreviousYear = PreviousYear;
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

		public Builder withPreviousYearEarnings(float PreviousYearEarnings) {
			this.PreviousYearEarnings = PreviousYearEarnings;
			return this;
		}

		public Builder withMostRecentYearBestSpeed(int MostRecentYearBestSpeed) {
			this.MostRecentYearBestSpeed = MostRecentYearBestSpeed;
			return this;
		}

		public Builder withSecondMostRecentYearBestSpeed(int SecondMostRecentYearBestSpeed) {
			this.SecondMostRecentYearBestSpeed = SecondMostRecentYearBestSpeed;
			return this;
		}

		public Builder withRunStyle(String RunStyle) {
			this.RunStyle = RunStyle;
			return this;
		}

		public Builder withSpeedPoints(int SpeedPoints) {
			this.SpeedPoints = SpeedPoints;
			return this;
		}

		public Builder withClaimingPrice(float ClaimingPrice) {
			this.ClaimingPrice = ClaimingPrice;
			return this;
		}

		public Builder withDaysSinceLastRace(int DaysSinceLastRace) {
			this.DaysSinceLastRace = DaysSinceLastRace;
			return this;
		}

		public Builder withPrimePowerRating(float PrimePowerRating) {
			this.PrimePowerRating = PrimePowerRating;
			return this;
		}

		public Builder withAverageClassRatingLastThree(float AverageClassRatingLastThree) {
			this.AverageClassRatingLastThree = AverageClassRatingLastThree;
			return this;
		}

		public Builder withStatebredFlag(String StatebredFlag) {
			this.StatebredFlag = StatebredFlag;
			return this;
		}

		public Builder withTrainer(Trainer Trainer) {
			this.Trainer = Trainer;
			return this;
		}

		public Builder withJockey(Jockey Jockey) {
			this.Jockey = Jockey;
			return this;
		}

		public Builder withWorkouts(List<Workout> Workouts) {
			this.Workouts = Workouts;
			return this;
		}

		public Builder withPastPerformances(List<PastPerformance> PastPerformances) {
			this.PastPerformances = PastPerformances;
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

		public Builder withLatePaceAvg(float LatePaceAvg) {
			this.LatePaceAvg = LatePaceAvg;
			return this;
		}

		public Builder withEarlyPosition(float EarlyPosition) {
			this.EarlyPosition = EarlyPosition;
			return this;
		}

		public Builder withLatePosition(float LatePosition) {
			this.LatePosition = LatePosition;
			return this;
		}

		public Builder withClosingRatio(float ClosingRatio) {
			this.ClosingRatio = ClosingRatio;
			return this;
		}

		public Builder withLatePaceBestLast3(int LatePaceBestLast3) {
			this.LatePaceBestLast3 = LatePaceBestLast3;
			return this;
		}

		public Builder withPaceAdjustedLate(float PaceAdjustedLate) {
			this.PaceAdjustedLate = PaceAdjustedLate;
			return this;
		}

		public Builder withLatePaceLast(int LatePaceLast) {
			this.LatePaceLast = LatePaceLast;
			return this;
		}

		public Builder withClassRating(float ClassRating) {
			this.ClassRating = ClassRating;
			return this;
		}

		public Builder withAverageCompetitiveLevel(float AverageCompetitiveLevel) {
			this.AverageCompetitiveLevel = AverageCompetitiveLevel;
			return this;
		}

		public Builder withLastRaceStrength(float LastRaceStrength) {
			this.LastRaceStrength = LastRaceStrength;
			return this;
		}

		public Builder withClassShift(float ClassShift) {
			this.ClassShift = ClassShift;
			return this;
		}

		public Builder withPurseShift(int PurseShift) {
			this.PurseShift = PurseShift;
			return this;
		}

		public Builder withSpeedRating(float SpeedRating) {
			this.SpeedRating = SpeedRating;
			return this;
		}

		public Builder withBasicFitness(int BasicFitness) {
			this.BasicFitness = BasicFitness;
			return this;
		}

		public Builder withFormPoints(int FormPoints) {
			this.FormPoints = FormPoints;
			return this;
		}

		public Builder withFurlongDays(float FurlongDays) {
			this.FurlongDays = FurlongDays;
			return this;
		}

		public Builder withTurnTime(float TurnTime) {
			this.TurnTime = TurnTime;
			return this;
		}

		public Builder withAvgAdjustedSpeedRating(float AvgAdjustedSpeedRating) {
			this.AvgAdjustedSpeedRating = AvgAdjustedSpeedRating;
			return this;
		}

		public Builder withCombinedPaceAvg(float combinedPaceAvg) {
			this.combinedPaceAvg = combinedPaceAvg;
			return this;
		}

		public Builder withAge(int age) {
			this.age = age;
			return this;
		}

		public Builder withARatingClass(float ARatingClass) {
			this.ARatingClass = ARatingClass;
			return this;
		}

		public Builder withARatingForm(float ARatingForm) {
			this.ARatingForm = ARatingForm;
			return this;
		}

		public Builder withARatingConnections(float ARatingConnections) {
			this.ARatingConnections = ARatingConnections;
			return this;
		}

		public Builder withARating(float ARating) {
			this.ARating = ARating;
			return this;
		}

		public Builder withScratchedFlag(Boolean scratchedFlag) {
			this.scratchedFlag = scratchedFlag;
			return this;
		}

		public Builder with_showDetails(Boolean _showDetails) {
			this._showDetails = _showDetails;
			return this;
		}

		public Builder withAngles(List<String> Angles) {
			this.Angles = Angles;
			return this;
		}

		public Builder withSelection(String selection) {
			this.selection = selection;
			return this;
		}

		public Builder withBettingLine(float bettingLine) {
			this.bettingLine = bettingLine;
			return this;
		}

		public Builder withNote(String note) {
			this.note = note;
			return this;
		}

		public Builder withPick(Boolean pick) {
			this.pick = pick;
			return this;
		}

		public Builder withFinishPosition(int finishPosition) {
			this.finishPosition = finishPosition;
			return this;
		}

		public Builder withWinPayout(float winPayout) {
			this.winPayout = winPayout;
			return this;
		}

		public Builder withPlacePayout(float placePayout) {
			this.placePayout = placePayout;
			return this;
		}

		public Builder withShowPayout(float showPayout) {
			this.showPayout = showPayout;
			return this;
		}

		public Builder withARatingFairValue(float ARatingFairValue) {
			this.ARatingFairValue = ARatingFairValue;
			return this;
		}

		public Builder withPrimePower(float PrimePower) {
			this.PrimePower = PrimePower;
			return this;
		}

		public Builder withSireAWD(float SireAWD) {
			this.SireAWD = SireAWD;
			return this;
		}

		public Builder withSireMudPercent(int SireMudPercent) {
			this.SireMudPercent = SireMudPercent;
			return this;
		}

		public Builder withSireMudStarts(int SireMudStarts) {
			this.SireMudStarts = SireMudStarts;
			return this;
		}

		public Builder withSireFirstPercent(int SireFirstPercent) {
			this.SireFirstPercent = SireFirstPercent;
			return this;
		}

		public Builder withSireTurfPercent(int SireTurfPercent) {
			this.SireTurfPercent = SireTurfPercent;
			return this;
		}

		public Builder withSireFirstTurfPercent(int SireFirstTurfPercent) {
			this.SireFirstTurfPercent = SireFirstTurfPercent;
			return this;
		}

		public Builder withSireSPI(float SireSPI) {
			this.SireSPI = SireSPI;
			return this;
		}

		public Builder withDamSireAWD(float DamSireAWD) {
			this.DamSireAWD = DamSireAWD;
			return this;
		}

		public Builder withDamSireMudPercent(int DamSireMudPercent) {
			this.DamSireMudPercent = DamSireMudPercent;
			return this;
		}

		public Builder withDamSireMudStarts(int DamSireMudStarts) {
			this.DamSireMudStarts = DamSireMudStarts;
			return this;
		}

		public Builder withDamSireFirstPercent(int DamSireFirstPercent) {
			this.DamSireFirstPercent = DamSireFirstPercent;
			return this;
		}

		public Builder withDamSireTurfPercent(int DamSireTurfPercent) {
			this.DamSireTurfPercent = DamSireTurfPercent;
			return this;
		}

		public Builder withDamSireFirstTurfPercent(int DamSireFirstTurfPercent) {
			this.DamSireFirstTurfPercent = DamSireFirstTurfPercent;
			return this;
		}

		public Builder withDamSireSPI(float DamSireSPI) {
			this.DamSireSPI = DamSireSPI;
			return this;
		}

		public Builder withDamDescription(String DamDescription) {
			this.DamDescription = DamDescription;
			return this;
		}

		public Builder withDamTwoYearOldPercent(int DamTwoYearOldPercent) {
			this.DamTwoYearOldPercent = DamTwoYearOldPercent;
			return this;
		}

		public Builder withDamTurfWinners(int DamTurfWinners) {
			this.DamTurfWinners = DamTurfWinners;
			return this;
		}

		public Builder withDamStarters(int DamStarters) {
			this.DamStarters = DamStarters;
			return this;
		}

		public Builder withDamWinners(int DamWinners) {
			this.DamWinners = DamWinners;
			return this;
		}

		public Builder withDamStakesWinners(int DamStakesWinners) {
			this.DamStakesWinners = DamStakesWinners;
			return this;
		}

		public Builder withDamDPI(float DamDPI) {
			this.DamDPI = DamDPI;
			return this;
		}

		public Builder withBrisCurrentClass(float BrisCurrentClass) {
			this.BrisCurrentClass = BrisCurrentClass;
			return this;
		}

		public Builder withBrisAvgLast3Class(float BrisAvgLast3Class) {
			this.BrisAvgLast3Class = BrisAvgLast3Class;
			return this;
		}

		public Builder withFlag(String flag) {
			this.flag = flag;
			return this;
		}

		public Builder withComment(String comment) {
			this.comment = comment;
			return this;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + AllWeatherBestSpeed;
			result = prime * result + Float.floatToIntBits(AllWeatherEarnings);
			result = prime * result + AllWeatherPlaces;
			result = prime * result + AllWeatherShows;
			result = prime * result + AllWeatherStarts;
			result = prime * result + AllWeatherWins;
			result = prime * result + ApprenticeWeightAllowed;
			result = prime * result + ((AuctionLocationDate == null) ? 0 : AuctionLocationDate.hashCode());
			result = prime * result + Float.floatToIntBits(AuctionPrice);
			result = prime * result + Float.floatToIntBits(AverageClassRatingLastThree);
			result = prime * result + ((Breeder == null) ? 0 : Breeder.hashCode());
			result = prime * result + ((Color == null) ? 0 : Color.hashCode());
			result = prime * result + CurrentYear;
			result = prime * result + Float.floatToIntBits(CurrentYearEarnings);
			result = prime * result + CurrentYearPlaces;
			result = prime * result + CurrentYearShows;
			result = prime * result + CurrentYearStarts;
			result = prime * result + CurrentYearWins;
			result = prime * result + ((Dam == null) ? 0 : Dam.hashCode());
			result = prime * result + ((DamsSire == null) ? 0 : DamsSire.hashCode());
			result = prime * result + DaysSinceLastRace;
			result = prime * result + DirtBestSpeed;
			result = prime * result + Float.floatToIntBits(DirtEarnings);
			result = prime * result + DirtPlaces;
			result = prime * result + DirtShows;
			result = prime * result + DirtStarts;
			result = prime * result + DirtWins;
			result = prime * result + DistanceBestSpeed;
			result = prime * result + Float.floatToIntBits(DistanceEarnings);
			result = prime * result + DistancePlaces;
			result = prime * result + DistanceShows;
			result = prime * result + DistanceStarts;
			result = prime * result + DistanceWins;
			result = prime * result + ((Equipment == null) ? 0 : Equipment.hashCode());
			result = prime * result + ((FoalingMonth == null) ? 0 : FoalingMonth.hashCode());
			result = prime * result + ((Jockey == null) ? 0 : Jockey.hashCode());
			result = prime * result + LifetimeBestSpeed;
			result = prime * result + Float.floatToIntBits(LifetimeEarnings);
			result = prime * result + LifetimePlaces;
			result = prime * result + LifetimeShows;
			result = prime * result + LifetimeStarts;
			result = prime * result + LifetimeWins;
			result = prime * result + Float.floatToIntBits(ClaimingPrice);
			result = prime * result + Float.floatToIntBits(MLOdds);
			result = prime * result + ((MTOFlag == null) ? 0 : MTOFlag.hashCode());
			result = prime * result + ((Medication == null) ? 0 : Medication.hashCode());
			result = prime * result + MostRecentYearBestSpeed;
			result = prime * result + ((Name == null) ? 0 : Name.hashCode());
			result = prime * result + ((Owner == null) ? 0 : Owner.hashCode());
			result = prime * result + ((OwnerSilks == null) ? 0 : OwnerSilks.hashCode());
			result = prime * result + ((PastPerformances == null) ? 0 : PastPerformances.hashCode());
			result = prime * result + ((PedigreeRatingDirt == null) ? 0 : PedigreeRatingDirt.hashCode());
			result = prime * result + ((PedigreeRatingDist == null) ? 0 : PedigreeRatingDist.hashCode());
			result = prime * result + ((PedigreeRatingMud == null) ? 0 : PedigreeRatingMud.hashCode());
			result = prime * result + ((PedigreeRatingTurf == null) ? 0 : PedigreeRatingTurf.hashCode());
			result = prime * result + PreviousYear;
			result = prime * result + Float.floatToIntBits(PreviousYearEarnings);
			result = prime * result + PreviousYearPlaces;
			result = prime * result + PreviousYearShows;
			result = prime * result + PreviousYearStarts;
			result = prime * result + PreviousYearWins;
			result = prime * result + Float.floatToIntBits(PrimePowerRating);
			result = prime * result + ((ProgramNumber == null) ? 0 : ProgramNumber.hashCode());
			result = prime * result + ProgramPostPosition;
			result = prime * result + ((RunStyle == null) ? 0 : RunStyle.hashCode());
			result = prime * result + SecondMostRecentYearBestSpeed;
			result = prime * result + ((Sex == null) ? 0 : Sex.hashCode());
			result = prime * result + ((Sire == null) ? 0 : Sire.hashCode());
			result = prime * result + Float.floatToIntBits(SireStudFee);
			result = prime * result + ((SiresSire == null) ? 0 : SiresSire.hashCode());
			result = prime * result + SpeedPoints;
			result = prime * result + ((StateCountry == null) ? 0 : StateCountry.hashCode());
			result = prime * result + ((StatebredFlag == null) ? 0 : StatebredFlag.hashCode());
			result = prime * result + TrackBestSpeed;
			result = prime * result + Float.floatToIntBits(TrackEarnings);
			result = prime * result + TrackPlaces;
			result = prime * result + TrackShows;
			result = prime * result + TrackStarts;
			result = prime * result + TrackWins;
			result = prime * result + ((Trainer == null) ? 0 : Trainer.hashCode());
			result = prime * result + TurfBestSpeed;
			result = prime * result + Float.floatToIntBits(TurfEarnings);
			result = prime * result + TurfPlaces;
			result = prime * result + TurfShows;
			result = prime * result + TurfStarts;
			result = prime * result + TurfWins;
			result = prime * result + Weight;
			result = prime * result + WetBestSpeed;
			result = prime * result + Float.floatToIntBits(WetEarnings);
			result = prime * result + WetPlaces;
			result = prime * result + WetShows;
			result = prime * result + WetStarts;
			result = prime * result + WetWins;
			result = prime * result + ((Workouts == null) ? 0 : Workouts.hashCode());
			result = prime * result + YearOfBirth;
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
			if (AllWeatherBestSpeed != other.AllWeatherBestSpeed)
				return false;
			if (Float.floatToIntBits(AllWeatherEarnings) != Float.floatToIntBits(other.AllWeatherEarnings))
				return false;
			if (AllWeatherPlaces != other.AllWeatherPlaces)
				return false;
			if (AllWeatherShows != other.AllWeatherShows)
				return false;
			if (AllWeatherStarts != other.AllWeatherStarts)
				return false;
			if (AllWeatherWins != other.AllWeatherWins)
				return false;
			if (ApprenticeWeightAllowed != other.ApprenticeWeightAllowed)
				return false;
			if (AuctionLocationDate == null) {
				if (other.AuctionLocationDate != null)
					return false;
			} else if (!AuctionLocationDate.equals(other.AuctionLocationDate))
				return false;
			if (Float.floatToIntBits(AuctionPrice) != Float.floatToIntBits(other.AuctionPrice))
				return false;
			if (Float.floatToIntBits(AverageClassRatingLastThree) != Float
					.floatToIntBits(other.AverageClassRatingLastThree))
				return false;
			if (Breeder == null) {
				if (other.Breeder != null)
					return false;
			} else if (!Breeder.equals(other.Breeder))
				return false;
			if (Color == null) {
				if (other.Color != null)
					return false;
			} else if (!Color.equals(other.Color))
				return false;
			if (CurrentYear != other.CurrentYear)
				return false;
			if (Float.floatToIntBits(CurrentYearEarnings) != Float.floatToIntBits(other.CurrentYearEarnings))
				return false;
			if (CurrentYearPlaces != other.CurrentYearPlaces)
				return false;
			if (CurrentYearShows != other.CurrentYearShows)
				return false;
			if (CurrentYearStarts != other.CurrentYearStarts)
				return false;
			if (CurrentYearWins != other.CurrentYearWins)
				return false;
			if (Dam == null) {
				if (other.Dam != null)
					return false;
			} else if (!Dam.equals(other.Dam))
				return false;
			if (DamsSire == null) {
				if (other.DamsSire != null)
					return false;
			} else if (!DamsSire.equals(other.DamsSire))
				return false;
			if (DaysSinceLastRace != other.DaysSinceLastRace)
				return false;
			if (DirtBestSpeed != other.DirtBestSpeed)
				return false;
			if (Float.floatToIntBits(DirtEarnings) != Float.floatToIntBits(other.DirtEarnings))
				return false;
			if (DirtPlaces != other.DirtPlaces)
				return false;
			if (DirtShows != other.DirtShows)
				return false;
			if (DirtStarts != other.DirtStarts)
				return false;
			if (DirtWins != other.DirtWins)
				return false;
			if (DistanceBestSpeed != other.DistanceBestSpeed)
				return false;
			if (Float.floatToIntBits(DistanceEarnings) != Float.floatToIntBits(other.DistanceEarnings))
				return false;
			if (DistancePlaces != other.DistancePlaces)
				return false;
			if (DistanceShows != other.DistanceShows)
				return false;
			if (DistanceStarts != other.DistanceStarts)
				return false;
			if (DistanceWins != other.DistanceWins)
				return false;
			if (Equipment != other.Equipment)
				return false;
			if (FoalingMonth == null) {
				if (other.FoalingMonth != null)
					return false;
			} else if (!FoalingMonth.equals(other.FoalingMonth))
				return false;
			if (Jockey == null) {
				if (other.Jockey != null)
					return false;
			} else if (!Jockey.equals(other.Jockey))
				return false;
			if (LifetimeBestSpeed != other.LifetimeBestSpeed)
				return false;
			if (Float.floatToIntBits(LifetimeEarnings) != Float.floatToIntBits(other.LifetimeEarnings))
				return false;
			if (LifetimePlaces != other.LifetimePlaces)
				return false;
			if (LifetimeShows != other.LifetimeShows)
				return false;
			if (LifetimeStarts != other.LifetimeStarts)
				return false;
			if (LifetimeWins != other.LifetimeWins)
				return false;
			if (Float.floatToIntBits(ClaimingPrice) != Float.floatToIntBits(other.ClaimingPrice))
				return false;
			if (Float.floatToIntBits(MLOdds) != Float.floatToIntBits(other.MLOdds))
				return false;
			if (MTOFlag == null) {
				if (other.MTOFlag != null)
					return false;
			} else if (!MTOFlag.equals(other.MTOFlag))
				return false;
			if (Medication != other.Medication)
				return false;
			if (MostRecentYearBestSpeed != other.MostRecentYearBestSpeed)
				return false;
			if (Name == null) {
				if (other.Name != null)
					return false;
			} else if (!Name.equals(other.Name))
				return false;
			if (Owner == null) {
				if (other.Owner != null)
					return false;
			} else if (!Owner.equals(other.Owner))
				return false;
			if (OwnerSilks == null) {
				if (other.OwnerSilks != null)
					return false;
			} else if (!OwnerSilks.equals(other.OwnerSilks))
				return false;
			if (PastPerformances == null) {
				if (other.PastPerformances != null)
					return false;
			} else if (!PastPerformances.equals(other.PastPerformances))
				return false;
			if (PedigreeRatingDirt == null) {
				if (other.PedigreeRatingDirt != null)
					return false;
			} else if (!PedigreeRatingDirt.equals(other.PedigreeRatingDirt))
				return false;
			if (PedigreeRatingDist == null) {
				if (other.PedigreeRatingDist != null)
					return false;
			} else if (!PedigreeRatingDist.equals(other.PedigreeRatingDist))
				return false;
			if (PedigreeRatingMud == null) {
				if (other.PedigreeRatingMud != null)
					return false;
			} else if (!PedigreeRatingMud.equals(other.PedigreeRatingMud))
				return false;
			if (PedigreeRatingTurf == null) {
				if (other.PedigreeRatingTurf != null)
					return false;
			} else if (!PedigreeRatingTurf.equals(other.PedigreeRatingTurf))
				return false;
			if (PreviousYear != other.PreviousYear)
				return false;
			if (Float.floatToIntBits(PreviousYearEarnings) != Float.floatToIntBits(other.PreviousYearEarnings))
				return false;
			if (PreviousYearPlaces != other.PreviousYearPlaces)
				return false;
			if (PreviousYearShows != other.PreviousYearShows)
				return false;
			if (PreviousYearStarts != other.PreviousYearStarts)
				return false;
			if (PreviousYearWins != other.PreviousYearWins)
				return false;
			if (Float.floatToIntBits(PrimePowerRating) != Float.floatToIntBits(other.PrimePowerRating))
				return false;
			if (ProgramNumber == null) {
				if (other.ProgramNumber != null)
					return false;
			} else if (!ProgramNumber.equals(other.ProgramNumber))
				return false;
			if (ProgramPostPosition != other.ProgramPostPosition)
				return false;
			if (RunStyle == null) {
				if (other.RunStyle != null)
					return false;
			} else if (!RunStyle.equals(other.RunStyle))
				return false;
			if (SecondMostRecentYearBestSpeed != other.SecondMostRecentYearBestSpeed)
				return false;
			if (Sex == null) {
				if (other.Sex != null)
					return false;
			} else if (!Sex.equals(other.Sex))
				return false;
			if (Sire == null) {
				if (other.Sire != null)
					return false;
			} else if (!Sire.equals(other.Sire))
				return false;
			if (Float.floatToIntBits(SireStudFee) != Float.floatToIntBits(other.SireStudFee))
				return false;
			if (SiresSire == null) {
				if (other.SiresSire != null)
					return false;
			} else if (!SiresSire.equals(other.SiresSire))
				return false;
			if (SpeedPoints != other.SpeedPoints)
				return false;
			if (StateCountry == null) {
				if (other.StateCountry != null)
					return false;
			} else if (!StateCountry.equals(other.StateCountry))
				return false;
			if (StatebredFlag == null) {
				if (other.StatebredFlag != null)
					return false;
			} else if (!StatebredFlag.equals(other.StatebredFlag))
				return false;
			if (TrackBestSpeed != other.TrackBestSpeed)
				return false;
			if (Float.floatToIntBits(TrackEarnings) != Float.floatToIntBits(other.TrackEarnings))
				return false;
			if (TrackPlaces != other.TrackPlaces)
				return false;
			if (TrackShows != other.TrackShows)
				return false;
			if (TrackStarts != other.TrackStarts)
				return false;
			if (TrackWins != other.TrackWins)
				return false;
			if (Trainer == null) {
				if (other.Trainer != null)
					return false;
			} else if (!Trainer.equals(other.Trainer))
				return false;
			if (TurfBestSpeed != other.TurfBestSpeed)
				return false;
			if (Float.floatToIntBits(TurfEarnings) != Float.floatToIntBits(other.TurfEarnings))
				return false;
			if (TurfPlaces != other.TurfPlaces)
				return false;
			if (TurfShows != other.TurfShows)
				return false;
			if (TurfStarts != other.TurfStarts)
				return false;
			if (TurfWins != other.TurfWins)
				return false;
			if (Weight != other.Weight)
				return false;
			if (WetBestSpeed != other.WetBestSpeed)
				return false;
			if (Float.floatToIntBits(WetEarnings) != Float.floatToIntBits(other.WetEarnings))
				return false;
			if (WetPlaces != other.WetPlaces)
				return false;
			if (WetShows != other.WetShows)
				return false;
			if (WetStarts != other.WetStarts)
				return false;
			if (WetWins != other.WetWins)
				return false;
			if (Workouts == null) {
				if (other.Workouts != null)
					return false;
			} else if (!Workouts.equals(other.Workouts))
				return false;
			if (YearOfBirth != other.YearOfBirth)
				return false;
			return true;
		}

		public Horse build() {
			return new Horse(this);
		}
	}
	
}
