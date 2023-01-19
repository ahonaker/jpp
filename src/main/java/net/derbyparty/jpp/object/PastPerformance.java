package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Generated;

public class PastPerformance implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LocalDate RaceDate;
	private int DaysSinceLastRace;
	private String TrackCode;
	private String BRISTrackCode;
	private int RaceNumber;
	private String TrackCondition;
	private int Distance;
	private SurfaceType Surface;
	private String ChuteIndicator;
	private String WagerTypes;
	private int NumberOfEntrants;
	private int PostPosition;
	private String Equipment;
	private MedicationType Medication;
	private String TripComment;
	private String WinnersName;
	private String PlaceName;
	private String ShowName;
	private int WinnersWeight;
	private int PlaceWeight;
	private int ShowWeight;
	private float WinnersMargin;
	private float PlaceMargin;
	private float ShowMargin;
	private String ExtraCommentLine;
	private int Weight;
	private float Odds;
	private String EntryFlag;
	private String RaceClassification;
	private int ClaimingPrice;
	private int Purse;
	private String StartCallPosition;
	private String FirstCallPosition;
	private String SecondCallPosition;
	private String GateCallPosition;
	private String StretchPosition;
	private String FinishPosition;
	private String MoneyPosition;
	private float FirstCallBeatenLengthsLeader;
	private float FirstCallBeatenLengthsOnly;
	private float SecondCallBeatenLengthsLeader;
	private float SecondCallBeatenLengthsOnly;
	private float RaceShapeFirstCall;
	private float StretchBeatenLengthsLeader;
	private float StretchBeatenLengthsOnly;
	private float FinishBeatenLengthsLeader;
	private float FinishBeatenLengthsOnly;
	private float RaceShapeSecondCall;
	private int PaceFigure2F;
	private int PaceFigure4F;
	private int PaceFigure6F;
	private int PaceFigure8F;
	private int PaceFigure10F;
	private int PaceFigureLate;
	private float RaceRating;
	private float ClassRating;
	private int SpeedPar;
	private int BRISSpeedRating;
	private int SpeedRating;
	private int TrackVariant;
	private float Fraction2F;
	private float Fraction3F;
	private float Fraction4F;
	private float Fraction5F;
	private float Fraction6F;
	private float Fraction7F;
	private float Fraction8F;
	private float Fraction10F;
	private float Fraction12F;
	private float Fraction14F;
	private float Fraction16F;
	private float Fraction1;
	private float Fraction2;
	private float Fraction3;
	private float FinalTime;
	
	private float CalcFraction1;
	private float CalcFraction2;
	private float CalcFraction3;
	private float CalcFinalTime;
	private float Split1;
	private float Split2;
	private float Split3;
	
	private String ClaimedCode;
	private String Trainer;
	private String Jockey;
	private int ApprenticeWeightAllowed;
	private RaceType RaceType;
	private String CompleteRaceCondition;
	private AgeRestrictionType AgeRestriction;
	private AgeRestrictionRangeType AgeRestrictionRange;
	private SexRestrictionType SexRestriction;
	private String StatebredFlag;
	private String RestrictedQualifierFlag;
	private int FavoriteFlag;
	private int FrontBandagesFlag;
	private String BarShoeFlag;
	private String CompanyLineCodes;
	private int LowClaimingPriceOfRace;
	private int HighClaimingPriceOfRace;
	private String ExtendedStartComment;
	private String SealedTrackFlag;
	private String AllWeatherSurfaceFlag;
	private Boolean OffTheTurfFlag;
	private String TrainerChangeDate;
	private int TrainerChangeStarts;
	private int TrainerChangeWins;
	private int TrainerChangePlaces;
	private int TrainerChangeShows;
	private float TrainerChangeROI;
	
	// Factors
	
	private String name;
	private int thisRaceNumber;
	@SuppressWarnings("unused")
	private String trackName;
	private Boolean ignore;
	private float RaceStrength;
	private float Last5AverageSpeed;
	
	private Boolean over90Days;
	private Boolean over365Days;
	
	private Boolean TurfFlag;
	
	private String RaceShape;
	
	@SuppressWarnings("unused")
	private float furlongs;
	@SuppressWarnings("unused")
	private float miles;
	@SuppressWarnings("unused")
	private int adjustedSpeedRating;
	@SuppressWarnings("unused")
	private String raceDateString;
	@SuppressWarnings("unused")
	private int e1;
	@SuppressWarnings("unused")
	private int e2;
	
	private PotentialKeyRace keyRace;
	
	private Boolean oneTurn;
	
	private String footnote;
	private String flag;
	private String comment;

	@Generated("SparkTools")
	private PastPerformance(Builder builder) {
		this.RaceDate = builder.RaceDate;
		this.DaysSinceLastRace = builder.DaysSinceLastRace;
		this.TrackCode = builder.TrackCode;
		this.BRISTrackCode = builder.BRISTrackCode;
		this.RaceNumber = builder.RaceNumber;
		this.TrackCondition = builder.TrackCondition;
		this.Distance = builder.Distance;
		this.Surface = builder.Surface;
		this.ChuteIndicator = builder.ChuteIndicator;
		this.WagerTypes = builder.WagerTypes;
		this.NumberOfEntrants = builder.NumberOfEntrants;
		this.PostPosition = builder.PostPosition;
		this.Equipment = builder.Equipment;
		this.Medication = builder.Medication;
		this.TripComment = builder.TripComment;
		this.WinnersName = builder.WinnersName;
		this.PlaceName = builder.PlaceName;
		this.ShowName = builder.ShowName;
		this.WinnersWeight = builder.WinnersWeight;
		this.PlaceWeight = builder.PlaceWeight;
		this.ShowWeight = builder.ShowWeight;
		this.WinnersMargin = builder.WinnersMargin;
		this.PlaceMargin = builder.PlaceMargin;
		this.ShowMargin = builder.ShowMargin;
		this.ExtraCommentLine = builder.ExtraCommentLine;
		this.Weight = builder.Weight;
		this.Odds = builder.Odds;
		this.EntryFlag = builder.EntryFlag;
		this.RaceClassification = builder.RaceClassification;
		this.ClaimingPrice = builder.ClaimingPrice;
		this.Purse = builder.Purse;
		this.StartCallPosition = builder.StartCallPosition;
		this.FirstCallPosition = builder.FirstCallPosition;
		this.SecondCallPosition = builder.SecondCallPosition;
		this.GateCallPosition = builder.GateCallPosition;
		this.StretchPosition = builder.StretchPosition;
		this.FinishPosition = builder.FinishPosition;
		this.MoneyPosition = builder.MoneyPosition;
		this.FirstCallBeatenLengthsLeader = builder.FirstCallBeatenLengthsLeader;
		this.FirstCallBeatenLengthsOnly = builder.FirstCallBeatenLengthsOnly;
		this.SecondCallBeatenLengthsLeader = builder.SecondCallBeatenLengthsLeader;
		this.SecondCallBeatenLengthsOnly = builder.SecondCallBeatenLengthsOnly;
		this.RaceShapeFirstCall = builder.RaceShapeFirstCall;
		this.StretchBeatenLengthsLeader = builder.StretchBeatenLengthsLeader;
		this.StretchBeatenLengthsOnly = builder.StretchBeatenLengthsOnly;
		this.FinishBeatenLengthsLeader = builder.FinishBeatenLengthsLeader;
		this.FinishBeatenLengthsOnly = builder.FinishBeatenLengthsOnly;
		this.RaceShapeSecondCall = builder.RaceShapeSecondCall;
		this.PaceFigure2F = builder.PaceFigure2F;
		this.PaceFigure4F = builder.PaceFigure4F;
		this.PaceFigure6F = builder.PaceFigure6F;
		this.PaceFigure8F = builder.PaceFigure8F;
		this.PaceFigure10F = builder.PaceFigure10F;
		this.PaceFigureLate = builder.PaceFigureLate;
		this.RaceRating = builder.RaceRating;
		this.ClassRating = builder.ClassRating;
		this.SpeedPar = builder.SpeedPar;
		this.BRISSpeedRating = builder.BRISSpeedRating;
		this.SpeedRating = builder.SpeedRating;
		this.TrackVariant = builder.TrackVariant;
		this.Fraction2F = builder.Fraction2F;
		this.Fraction3F = builder.Fraction3F;
		this.Fraction4F = builder.Fraction4F;
		this.Fraction5F = builder.Fraction5F;
		this.Fraction6F = builder.Fraction6F;
		this.Fraction7F = builder.Fraction7F;
		this.Fraction8F = builder.Fraction8F;
		this.Fraction10F = builder.Fraction10F;
		this.Fraction12F = builder.Fraction12F;
		this.Fraction14F = builder.Fraction14F;
		this.Fraction16F = builder.Fraction16F;
		this.Fraction1 = builder.Fraction1;
		this.Fraction2 = builder.Fraction2;
		this.Fraction3 = builder.Fraction3;
		this.FinalTime = builder.FinalTime;
		this.CalcFraction1 = builder.CalcFraction1;
		this.CalcFraction2 = builder.CalcFraction2;
		this.CalcFraction3 = builder.CalcFraction3;
		this.CalcFinalTime = builder.CalcFinalTime;
		this.Split1 = builder.Split1;
		this.Split2 = builder.Split2;
		this.Split3 = builder.Split3;
		this.ClaimedCode = builder.ClaimedCode;
		this.Trainer = builder.Trainer;
		this.Jockey = builder.Jockey;
		this.ApprenticeWeightAllowed = builder.ApprenticeWeightAllowed;
		this.RaceType = builder.RaceType;
		this.CompleteRaceCondition = builder.CompleteRaceCondition;
		this.AgeRestriction = builder.AgeRestriction;
		this.AgeRestrictionRange = builder.AgeRestrictionRange;
		this.SexRestriction = builder.SexRestriction;
		this.StatebredFlag = builder.StatebredFlag;
		this.RestrictedQualifierFlag = builder.RestrictedQualifierFlag;
		this.FavoriteFlag = builder.FavoriteFlag;
		this.FrontBandagesFlag = builder.FrontBandagesFlag;
		this.BarShoeFlag = builder.BarShoeFlag;
		this.CompanyLineCodes = builder.CompanyLineCodes;
		this.LowClaimingPriceOfRace = builder.LowClaimingPriceOfRace;
		this.HighClaimingPriceOfRace = builder.HighClaimingPriceOfRace;
		this.ExtendedStartComment = builder.ExtendedStartComment;
		this.SealedTrackFlag = builder.SealedTrackFlag;
		this.AllWeatherSurfaceFlag = builder.AllWeatherSurfaceFlag;
		this.OffTheTurfFlag = builder.OffTheTurfFlag;
		this.TrainerChangeDate = builder.TrainerChangeDate;
		this.TrainerChangeStarts = builder.TrainerChangeStarts;
		this.TrainerChangeWins = builder.TrainerChangeWins;
		this.TrainerChangePlaces = builder.TrainerChangePlaces;
		this.TrainerChangeShows = builder.TrainerChangeShows;
		this.TrainerChangeROI = builder.TrainerChangeROI;
		this.name = builder.name;
		this.thisRaceNumber = builder.thisRaceNumber;
		this.trackName = builder.trackName;
		this.ignore = builder.ignore;
		this.RaceStrength = builder.RaceStrength;
		this.Last5AverageSpeed = builder.Last5AverageSpeed;
		this.over90Days = builder.over90Days;
		this.over365Days = builder.over365Days;
		this.TurfFlag = builder.TurfFlag;
		this.RaceShape = builder.RaceShape;
		this.furlongs = builder.furlongs;
		this.miles = builder.miles;
		this.adjustedSpeedRating = builder.adjustedSpeedRating;
		this.raceDateString = builder.raceDateString;
		this.e1 = builder.e1;
		this.e2 = builder.e2;
		this.keyRace = builder.keyRace;
		this.oneTurn = builder.oneTurn;
		this.footnote = builder.footnote;
		this.flag = builder.flag;
		this.comment = builder.comment;
	}

	public float getFurlongs () {
		return ((float) Distance / 220);
	}
	public float getMiles () {
		return ((float) Distance / 1760);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getThisRaceNumber() {
		return thisRaceNumber;
	}

	public void setThisRaceNumber(int thisRaceNumber) {
		this.thisRaceNumber = thisRaceNumber;
	}

	public String getTrackName() {
		HashMap<String, String> getTrackName = new HashMap<String, String>(); 
		
		getTrackName.put("AIK","Aiken");
		getTrackName.put("AJX","Ajax Downs");
		getTrackName.put("ALB","Albuquerque");
		getTrackName.put("AQU","Aqueduct");
		getTrackName.put("ARP","Arapahoe");
		getTrackName.put("AZD","Arizona Downs");
		getTrackName.put("ASD","Assiniboia");
		getTrackName.put("ATO","Atokad");
		getTrackName.put("BAQ","Belmont At The Big A");
		getTrackName.put("BEL","Belmont Park");
		getTrackName.put("BTP","Belterra Park");
		getTrackName.put("BKF","Black Foot");
		getTrackName.put("CMR","Camarero");
		getTrackName.put("CAM","Camden");
		getTrackName.put("CBY","Canterbury Park");
		getTrackName.put("CAS","Cassia Fair");
		getTrackName.put("CTM","Century Mile");
		getTrackName.put("CT","Charles Town");
		getTrackName.put("CHA","Charleston");
		getTrackName.put("CHL","Charlotte");
		getTrackName.put("CHE","Cheshire");
		getTrackName.put("CPW","Chippewa Downs");
		getTrackName.put("CD","Churchill Downs");
		getTrackName.put("DG","Cochise Fair");
		getTrackName.put("CNL","Colonial Downs");
		getTrackName.put("CLS","Columbus");
		getTrackName.put("PRV","Crooked River");
		getTrackName.put("DMR","Del Mar");
		getTrackName.put("DEL","Delaware Park");
		getTrackName.put("DED","Delta Downs");
		getTrackName.put("ELK","Elko Fair");
		getTrackName.put("ELP","Ellis Park");
		getTrackName.put("EMD","Emerald Downs");
		getTrackName.put("EDR","Energy Downs 307 Horse Racing");
		getTrackName.put("EVD","Evangeline");
		getTrackName.put("FG","Fair Grounds");
		getTrackName.put("FMT","Fair Meadows");
		getTrackName.put("FPL","Fair Play Park");
		getTrackName.put("FH","Far Hills");
		getTrackName.put("FER","Ferndale");
		getTrackName.put("FL","Finger Lakes");
		getTrackName.put("FON","Fonner Park");
		getTrackName.put("FE","Fort Erie");
		getTrackName.put("FTP","Fort Pierre");
		getTrackName.put("FX","Foxfield");
		getTrackName.put("FNO","Fresno");
		getTrackName.put("FAN","Fanduel Sportsbook And Horse Racing");
		getTrackName.put("GV","Genesee Valley");
		getTrackName.put("GIL","Gillespie Fairground");
		getTrackName.put("MID","Glenwood Park At Middleburg");
		getTrackName.put("GLN","Glyndon");
		getTrackName.put("GG","Golden Gate");
		getTrackName.put("GN","Grand National");
		getTrackName.put("GPR","Grande Prairie");
		getTrackName.put("GRP","Grants Pass");
		getTrackName.put("GF","Great Falls");
		getTrackName.put("GRM","Great Meadow");
		getTrackName.put("GP","Gulfstream Park");
		getTrackName.put("HST","Hastings");
		getTrackName.put("HAW","Hawthorne");
		getTrackName.put("HPO","Horsemen's Park");
		getTrackName.put("IND","Horseshoe Indianapolis");
		getTrackName.put("JRM","Jerome Fair");
		getTrackName.put("KEE","Keeneland");
		getTrackName.put("KD","Kentucky Downs");
		getTrackName.put("LBT","Laurel Brown");
		getTrackName.put("LRL","Laurel Park");
		getTrackName.put("LBG","Lethbridge - Rmtc");
		getTrackName.put("LS","Lone Star");
		getTrackName.put("LA","Los Alamitos Quarter Horse");
		getTrackName.put("LRC","Los Alamitos Thoroughbred");
		getTrackName.put("LAD","Louisiana Downs");
		getTrackName.put("LNN","Lincoln Race Course");
		getTrackName.put("MVR","Mahoning Valley Race Course");
		getTrackName.put("MAL","Malvern");
		getTrackName.put("MED","Meadowlands");
		getTrackName.put("MC","Miles City");
		getTrackName.put("MIL","Millarville");
		getTrackName.put("MON","Monkton");
		getTrackName.put("MTH","Monmouth Park");
		getTrackName.put("MTP","Montpelier");
		getTrackName.put("MMD","Moosomin D & E At Marquis Downs");
		getTrackName.put("MNR","Mountaineer");
		getTrackName.put("FAR","North Dakota");
		getTrackName.put("OP","Oaklawn Park");
		getTrackName.put("ONE","Oneida Fair");
		getTrackName.put("UN","Oregon Livestock");
		getTrackName.put("PRX","Parx Racing");
		getTrackName.put("PEN","Penn National");
		getTrackName.put("UNI","Pennsylvania Hunt Cup");
		getTrackName.put("PW","Percy Warner");
		getTrackName.put("PIM","Pimlico");
		getTrackName.put("PMT","Pine Mtn");
		getTrackName.put("PLN","Pleasanton");
		getTrackName.put("POD","Pocatello Downs");
		getTrackName.put("PRM","Prairie Meadows");
		getTrackName.put("PID","Presque Isle Downs");
		getTrackName.put("RP","Remington Park");
		getTrackName.put("RET","Retama Park");
		getTrackName.put("RIL","Rillito");
		getTrackName.put("RUI","Ruidoso Downs");
		getTrackName.put("SAC","Sacramento");
		getTrackName.put("HOU","Sam Houston");
		getTrackName.put("SA","Santa Anita");
		getTrackName.put("SON","Santa Cruz");
		getTrackName.put("SR","Santa Rosa");
		getTrackName.put("SAR","Saratoga");
		getTrackName.put("SHW","Shawan Downs");
		getTrackName.put("SUN","Sunland Park");
		getTrackName.put("SRP","Sunray Park");
		getTrackName.put("SWF","Sweetwater Downs");
		getTrackName.put("TAM","Tampa Bay");
		getTrackName.put("TTC","The Thoroughbred Center");
		getTrackName.put("TDN","Thistledown");
		getTrackName.put("TIL","Tillamook Fair");
		getTrackName.put("TIM","Timonium");
		getTrackName.put("TRY","Tryon");
		getTrackName.put("TUP","Turf Paradise");
		getTrackName.put("TP","Turfway Park");
		getTrackName.put("WBR","Weber Downs");
		getTrackName.put("ELY","White Pine Racing");
		getTrackName.put("WRD","Will Rogers");
		getTrackName.put("WIL","Willowdale Stp");
		getTrackName.put("WNT","Winterthur");
		getTrackName.put("WO","Woodbine");
		getTrackName.put("WYO","Wyoming Downs");
		getTrackName.put("ZIA","Zia Park");
		
		getTrackName.put("ABV","Abbeville");
		getTrackName.put("AGN","Agen");
		getTrackName.put("AIN","Aintree(UK)");
		getTrackName.put("ASA","Aire-Sur-L'Adour");
		getTrackName.put("AXB","Aix-Les-Bains");
		getTrackName.put("AJC","Ajaccio");
		getTrackName.put("AKS","Ak-Sar-Ben");
		getTrackName.put("ALN","Alencon");
		getTrackName.put("AMN","Amiens");
		getTrackName.put("ANG","Angers");
		getTrackName.put("ANL","Angouleme");
		getTrackName.put("ARG","Aregentan");
		getTrackName.put("AR","Argentino");
		getTrackName.put("AL","Arlington");
		getTrackName.put("ARL","Arlington");
		getTrackName.put("ARR","Arras");
		getTrackName.put("1","Asahikawa");
		getTrackName.put("ASC","Ascot");
		getTrackName.put("AST","Ascot");
		getTrackName.put("AS","Ascot, Aust");
		getTrackName.put("AC","Ascot, S.A.");
		getTrackName.put("AH","Ashburton");
		getTrackName.put("AUB","Aub-Sur-Nere");
		getTrackName.put("AUC","Auch");
		getTrackName.put("AK","Aukland");
		getTrackName.put("AUR","Aurillac");
		getTrackName.put("AUT","Auteuil");
		getTrackName.put("AVG","Avignon");
		getTrackName.put("AV","Avondale");
		getTrackName.put("AVN","Avon-SB");
		getTrackName.put("AVR","Avranches");
		getTrackName.put("AYR","Ayr(UK)");
		getTrackName.put("BA","Bad Doheran");
		getTrackName.put("BH","Bad Harzburg");
		getTrackName.put("BD","Baden-Baden");
		getTrackName.put("BGN","Bagneres-De-Luchon-Saint-Gaudens");
		getTrackName.put("BAG","Bagnoles-De-L'Orne");
		getTrackName.put("BK","Balaklava");
		getTrackName.put("BL","Ballaragt");
		getTrackName.put("BAL","Ballingrobe");
		getTrackName.put("BOD","Bangor(UK)");
		getTrackName.put("BOD","Bangor-on-Dee");
		getTrackName.put("BAS","Bastia");
		getTrackName.put("BTH","Bath");
		getTrackName.put("BAT","Bath(UK)");
		getTrackName.put("BY","Bay of Plenty");
		getTrackName.put("BLM","Beaumont-De-Lomagne");
		getTrackName.put("BPR","Beaupreau");
		getTrackName.put("BLW","Bellewstown");
		getTrackName.put("BP","Belmont, Aust");
		getTrackName.put("BN","Bendigo");
		getTrackName.put("BSM","Berch-Sur-Mer");
		getTrackName.put("BRN","Bernay");
		getTrackName.put("BEV","Beverley(UK)");
		getTrackName.put("BIG","Biguglia");
		getTrackName.put("BBG","Blain-Bouvron-Le-Gavre");
		getTrackName.put("BS","Blieskastel");
		getTrackName.put("BLO","Bloemfontein");
		getTrackName.put("BT","Boitsfort");
		getTrackName.put("BOL","Bollene");
		getTrackName.put("BDX","Bordeaux");
		getTrackName.put("BR","Borrowdale");
		getTrackName.put("BOL","Borrowdale");
		getTrackName.put("BHL","Brehal");
		getTrackName.put("BE","Bremen");
		getTrackName.put("BTN","Brighton");
		getTrackName.put("BRG","Brighton(UK)");
		getTrackName.put("BB","Bunbury");
		getTrackName.put("CGN","Cagnes Sur Mer");
		getTrackName.put("CSM","Cagnes-Sur-Mer");
		getTrackName.put("29","Camarero Race Track");
		getTrackName.put("CM","Cambridge");
		getTrackName.put("CB","Canberra");
		getTrackName.put("CY","Canterbury");
		getTrackName.put("CA","Carhaix");
		getTrackName.put("CAX","Carhaix");
		getTrackName.put("CAR","Carlisle(UK)");
		getTrackName.put("CRL","Carlisle(UK)");
		getTrackName.put("CPT","Carpentras");
		getTrackName.put("CTM","Cartmel");
		getTrackName.put("CTM","Cartmel(UK)");
		getTrackName.put("CSN","Castelsarrasin");
		getTrackName.put("CVN","Castera-Verduzan");
		getTrackName.put("CAB","Catterick Bridge(UK)");
		getTrackName.put("CAT","Catterick(UK)");
		getTrackName.put("CF","Caulfield");
		getTrackName.put("CVL","Cavaillon");
		getTrackName.put("CBT","Cazaubon-Barbotan-Les-Thermes");
		getTrackName.put("CHL","Chalais");
		getTrackName.put("CLL","Challens");
		getTrackName.put("CEC","Chalons En Champagne");
		getTrackName.put("CHY","Chantilly");
		getTrackName.put("CH","Charroux");
		getTrackName.put("CHB","Chateaubriant");
		getTrackName.put("CDL","Chateau-Du-Loir");
		getTrackName.put("CGR","Chateau-Gontier");
		getTrackName.put("CHX","Chateauroux");
		getTrackName.put("CRX","Chateauroux");
		getTrackName.put("CSC","Chatillion-Sur-Chalaronne");
		getTrackName.put("CE","Cheltenham");
		getTrackName.put("CHD","Chelmsford City(UK)");
		getTrackName.put("CHM","Cheltenham(UK)");
		getTrackName.put("CHP","Chepstow(UK)");
		getTrackName.put("CHE","Chester(UK)");
		getTrackName.put("CHI","Chinon");
		getTrackName.put("CHT","Cholet");
		getTrackName.put("2","Chuko");
		getTrackName.put("CHK","Chukyo");
		getTrackName.put("16","Chukyo");
		getTrackName.put("CJ","Cidade Jardim");
		getTrackName.put("CLD","Clairefontaine-Deauville");
		getTrackName.put("CW","Clairwood");
		getTrackName.put("CLA","Clairwood");
		getTrackName.put("CLN","Clonmel");
		getTrackName.put("CU","Cloppenbury");
		getTrackName.put("CP","Club Hipico");
		getTrackName.put("CO","Coffs");
		getTrackName.put("CG","Cologne");
		getTrackName.put("CMB","Combree");
		getTrackName.put("CMP","Compeigne");
		getTrackName.put("CRK","Cork");
		getTrackName.put("COR","Corlay");
		getTrackName.put("CN","Counties");
		getTrackName.put("CRA","Craon");
		getTrackName.put("CR","Cristal");
		getTrackName.put("CGH","Curragh");
		getTrackName.put("DV","Dannevirke Hunt");
		getTrackName.put("DG","Dargaville");
		getTrackName.put("DAX","DAX");
		getTrackName.put("DVL","Deauville");
		getTrackName.put("DPE","Dieppe");
		getTrackName.put("DIN","Dinan");
		getTrackName.put("DVB","Divonne-Les-Bains");
		getTrackName.put("DON","Doncaster(UK)");
		getTrackName.put("DM","Doomben");
		getTrackName.put("DR","Dortmund");
		getTrackName.put("DPT","Downpatrick");
		getTrackName.put("DI","Drensteingurt");
		getTrackName.put("DD","Dresden");
		getTrackName.put("DUD","Dundalk");
		getTrackName.put("DRB","Durbanville");
		getTrackName.put("DTL","Durtal");
		getTrackName.put("DS","Dusseldorf");
		getTrackName.put("EF","Eagle Farm");
		getTrackName.put("EUZ","Eauze");
		getTrackName.put("ECM","Ecommoy");
		getTrackName.put("EDN","Edinburgh");
		getTrackName.put("EG","Egmont");
		getTrackName.put("27","El Comandante");
		getTrackName.put("ELB","Elbeuf");
		getTrackName.put("EL","Elwick");
		getTrackName.put("EGH","Enghien");
		getTrackName.put("EPS","Epsom(UK)");
		getTrackName.put("ER","Erback");
		getTrackName.put("ERB","Erbray");
		getTrackName.put("EVR","Evreux");
		getTrackName.put("EVY","Evry");
		getTrackName.put("EXE","Exeter(UK)");
		getTrackName.put("FV","Fairview");
		getTrackName.put("FVW","Fairview");
		getTrackName.put("FRH","Fairyhousee");
		getTrackName.put("FKN","Fakenham(UK)");
		getTrackName.put("FRS","Feurs");
		getTrackName.put("FD","Fielding");
		getTrackName.put("FR","Firenze");
		getTrackName.put("FMG","Flamingo Park");
		getTrackName.put("FL","Flemington");
		getTrackName.put("FTB","Fontainbleau");
		getTrackName.put("FPR","Fontwell(UK)");
		getTrackName.put("FXT","Foxton");
		getTrackName.put("FF","Frankfurt");
		getTrackName.put("4","Fuchu");
		getTrackName.put("FK","Fukushima");
		getTrackName.put("3","Fukushima");
		getTrackName.put("5","Fukuyama");
		getTrackName.put("GAB","Gabaret");
		getTrackName.put("Gal","Galway");
		getTrackName.put("GL","Geelong");
		getTrackName.put("GK","Gelsenkirchen");
		getTrackName.put("GAC","Gemozac");
		getTrackName.put("GDN","Geraldine");
		getTrackName.put("GC","Gold Coast");
		getTrackName.put("GWD","Goodwood");
		getTrackName.put("GOO","Goodwood(UK)");
		getTrackName.put("GR","Gore");
		getTrackName.put("GSF","Gosford");
		getTrackName.put("GFP","Gosforth");
		getTrackName.put("GOS","Gosforth Park");
		getTrackName.put("GOW","Gowran Park");
		getTrackName.put("GFN","Grafton");
		getTrackName.put("GRT","Gramat");
		getTrackName.put("GRS","Granville Et Saint-Pur-Sur-Mer");
		getTrackName.put("GTY","Great Yarmouth");
		getTrackName.put("GSG","Grenade-Sur-Garonne");
		getTrackName.put("GY","Greyville");
		getTrackName.put("GRV","Greyville");
		getTrackName.put("GRV","Greyville");
		getTrackName.put("GND","Groendendaal");
		getTrackName.put("GT","Grosseto");
		getTrackName.put("GPN","Guemene-Penfao");
		getTrackName.put("GCQ","Guer-Coetquidan");
		getTrackName.put("GSQ","Guerlesquin");
		getTrackName.put("GUI","Guincamp");
		getTrackName.put("GVX","GV");
		getTrackName.put("HK","Hakodate");
		getTrackName.put("7","Hakodate");
		getTrackName.put("17","Hakodate");
		getTrackName.put("HL","Halle");
		getTrackName.put("HM","Hamburg");
		getTrackName.put("HAM","Hamilton Park(UK)");
		getTrackName.put("HMP","Hamilton Park(UK)");
		getTrackName.put("HR","Hanover");
		getTrackName.put("8","Hanshin");
		getTrackName.put("HAV","Happy Valley");
		getTrackName.put("HS","Hasslock");
		getTrackName.put("HB","Hawke's Bay");
		getTrackName.put("HW","Hawksbury");
		getTrackName.put("HAY","Haydock Park(UK)");
		getTrackName.put("HE","Heiligenwald");
		getTrackName.put("HER","Hereford(UK)");
		getTrackName.put("HH","Herschberg");
		getTrackName.put("HX","Herxheim");
		getTrackName.put("HEX","Hexham(UK)");
		getTrackName.put("HA","Hipodromo");
		getTrackName.put("HC","Hipodromo");
		getTrackName.put("SP2","Hipodromo (Chi)");
		getTrackName.put("HO","Hooksiel");
		getTrackName.put("HP","Hoppegarten");
		getTrackName.put("HTN","Huntington(UK)");
		getTrackName.put("HYR","Hyeres");
		getTrackName.put("JG","Jagersro");
		getTrackName.put("JAL","Jallais");
		getTrackName.put("JNC","Jarnac");
		getTrackName.put("JSN","Josselin");
		getTrackName.put("6","Junabashi");
		getTrackName.put("KG","Kalgoorlie");
		getTrackName.put("33","Kaminoyama");
		getTrackName.put("34","Kanozawa");
		getTrackName.put("KA","Karlsruhe");
		getTrackName.put("KS","Karslbad");
		getTrackName.put("9","Kasamatsu");
		getTrackName.put("10","Kawasaki");
		getTrackName.put("KEL","Kelso(UK)");
		getTrackName.put("KB","Kembla Grange");
		getTrackName.put("KEM","Kempton Park(UK)");
		getTrackName.put("KN","Kenilworth");
		getTrackName.put("KEN","Kenilworth");
		getTrackName.put("KBG","Kilbeggan");
		getTrackName.put("KIL","Killarney");
		getTrackName.put("KM","Kilmore");
		getTrackName.put("KP","Klampenborg");
		getTrackName.put("35","Kochi");
		getTrackName.put("KK","Kokura");
		getTrackName.put("11","Kokura");
		getTrackName.put("KL","Koln");
		getTrackName.put("KR","Krefeld");
		getTrackName.put("12","Kyoto");
		getTrackName.put("KY","Kyto");
		getTrackName.put("LCL","La Capelle");
		getTrackName.put("LGY","La Gacilly");
		getTrackName.put("GRB","La Guerche De");
		getTrackName.put("LGN","La Guerche-Nevers");
		getTrackName.put("LMN","La Mans");
		getTrackName.put("LP","La Plata");
		getTrackName.put("SP4","La Plata (Arg)");
		getTrackName.put("REO","La Reole");
		getTrackName.put("LR","La Rinconada");
		getTrackName.put("LRC","La Rochelle");
		getTrackName.put("LRY","La Roche-Sur-Yon");
		getTrackName.put("TDB","La Teste De");
		getTrackName.put("LBL","Lamballe");
		getTrackName.put("LDV","Landivisiau");
		getTrackName.put("LGD","Langdon");
		getTrackName.put("LMZ","Lannemezan");
		getTrackName.put("LST","Lasarte");
		getTrackName.put("LAY","Laytown");
		getTrackName.put("CRS","Le Croise-Laroche");
		getTrackName.put("LDT","Le Dorat");
		getTrackName.put("LGF","Le Grand-Fougeray");
		getTrackName.put("LLD","Le Lion-D'Angers");
		getTrackName.put("LPT","Le Pertre");
		getTrackName.put("LPH","Le Pin-Au-Haras");
		getTrackName.put("LPR","Le Sparre");
		getTrackName.put("LRP","Le Sparre");
		getTrackName.put("LTQ","Le Touquet");
		getTrackName.put("LE","Lebach");
		getTrackName.put("LEI","Leicester(UK)");
		getTrackName.put("LG","Leipzig");
		getTrackName.put("LEP","Leopardstown");
		getTrackName.put("LAN","Les Andelys");
		getTrackName.put("LSO","Les Sables-D'Olonne");
		getTrackName.put("LV","Levin");
		getTrackName.put("LIB","Libourne");
		getTrackName.put("LNR","Lignieres");
		getTrackName.put("LIM","Limerick");
		getTrackName.put("LMG","Limoges");
		getTrackName.put("LIN","Lingfield(UK)");
		getTrackName.put("LSX","Lisieux");
		getTrackName.put("LSS","L'Isle-Sur-Sorgue");
		getTrackName.put("LIS","Listowel");
		getTrackName.put("LCP","Longchamp");
		getTrackName.put("LCH","Longchamp");
		getTrackName.put("SP7","Longchamp");
		getTrackName.put("LOU","Loudeac");
		getTrackName.put("LUC","Lucon");
		getTrackName.put("LUD","Ludlow(UK)");
		getTrackName.put("LUX","Luxe");
		getTrackName.put("LYN","Lyon");
		getTrackName.put("MCH","Machecoul");
		getTrackName.put("MCC","Machecoul");
		getTrackName.put("MCL","Macon-Cluny");
		getTrackName.put("MA","Madrid");
		getTrackName.put("MU","Magdeburg");
		getTrackName.put("MLF","Maisons-Laffitte");
		getTrackName.put("MLW","Mallow");
		getTrackName.put("NH","Mannheim");
		getTrackName.put("MNS","Mansle");
		getTrackName.put("MN","Manwatu");
		getTrackName.put("MAR","Market Rasen");
		getTrackName.put("MRR","Market Rasen(UK)");
		getTrackName.put("MR","Market Rasen(UK)");
		getTrackName.put("MO","Maronas");
		getTrackName.put("SP8","Maronas (Ugy)");
		getTrackName.put("MRS","Marseille");
		getTrackName.put("MT","Marton");
		getTrackName.put("MS","Masterton");
		getTrackName.put("MM","Matamata");
		getTrackName.put("MBR","Maure-De-Bretagne");
		getTrackName.put("MRN","Mauron");
		getTrackName.put("MEL","Meral");
		getTrackName.put("MR","Merano");
		getTrackName.put("MRL","Mercal");
		getTrackName.put("MDM","Meslay-Du-Maine");
		getTrackName.put("ML","Milan");
		getTrackName.put("MIT","Milnerton");
		getTrackName.put("MLT","Milnerton");
		getTrackName.put("MLR","Molieres");
		getTrackName.put("31","Mombetsu");
		getTrackName.put("MGU","Montaigu");
		getTrackName.put("MTB","Montauban");
		getTrackName.put("MMN","Mont-De-Marsan");
		getTrackName.put("MDR","Montendre");
		getTrackName.put("MER","Montier-En-Der");
		getTrackName.put("MGC","Montignac");
		getTrackName.put("MNB","Montlucon-Neris-Les-Bains");
		getTrackName.put("MVT","Montrevault");
		getTrackName.put("MV","Moonee Valley");
		getTrackName.put("13","Morioka");
		getTrackName.put("MXL","Morlaix");
		getTrackName.put("MG","Mornington");
		getTrackName.put("MP","Morpheteville");
		getTrackName.put("MRT","Mortagne-Au-Perche");
		getTrackName.put("MLN","Moulins");
		getTrackName.put("MLM","Moulins-La-Marche");
		getTrackName.put("MW","Mowbray");
		getTrackName.put("MH","Muleim");
		getTrackName.put("MB","Murray Bridge");
		getTrackName.put("MUS","Musselburgh(UK)");
		getTrackName.put("MBH","Musselburgh(UK)");
		getTrackName.put("NAS","Nad Al Sheba");
		getTrackName.put("14","Nagoya");
		getTrackName.put("NK","Nakayama");
		getTrackName.put("15","Nakayma");
		getTrackName.put("NCY","Nancy");
		getTrackName.put("NTS","Nantes");
		getTrackName.put("NR","Napier");
		getTrackName.put("NPL","Naples");
		getTrackName.put("SP3","National Pick 3");
		getTrackName.put("NVN","Navan");
		getTrackName.put("NFB","Neufchatel-En-Bray");
		getTrackName.put("NS","Neuss");
		getTrackName.put("NBY","Newbury(UK)");
		getTrackName.put("NC","Newcastle(UK)");
		getTrackName.put("NM","Newmarket");
		getTrackName.put("NKT","Newmarket");
		getTrackName.put("NEW","Newmarket(UK)");
		getTrackName.put("NKT","Newmarket(UK)");
		getTrackName.put("NEW","Newmarket(UK)");
		getTrackName.put("NAB","Newton Abbot(UK)");
		getTrackName.put("NG","Niigata");
		getTrackName.put("18","Niigata");
		getTrackName.put("19","Niigata(NAR)");
		getTrackName.put("NMS","Nimes");
		getTrackName.put("NRT","Niort");
		getTrackName.put("NCB","North Canterbury");
		getTrackName.put("NSE","Nort-Sur-Erdre");
		getTrackName.put("NOT","Nottingham(UK)");
		getTrackName.put("NZY","Nozay");
		getTrackName.put("NSV","Nuille-Sur-Vicoin");
		getTrackName.put("OD","Odense");
		getTrackName.put("30","Ohi");
		getTrackName.put("21","Oi");
		getTrackName.put("ON","Onkaparinga");
		getTrackName.put("ORS","Oraison");
		getTrackName.put("ORL","Orleans");
		getTrackName.put("OS","Ostende");
		getTrackName.put("OT","Otago");
		getTrackName.put("OMI","Otaki-Maori");
		getTrackName.put("OV","Overoll");
		getTrackName.put("PA","Paeroa");
		getTrackName.put("PLM","Paray-Le-Monial");
		getTrackName.put("PML","Paray-Le-Monial");
		getTrackName.put("PAU","Pau");
		getTrackName.put("PER","Perth(UK)");
		getTrackName.put("PTH","Perth(UK)");
		getTrackName.put("PNX","Phoenix Park");
		getTrackName.put("PJ","Pinjarra");
		getTrackName.put("PS","Pisa");
		getTrackName.put("PLS","Plesse");
		getTrackName.put("PLE","Plestin-Les-Greves");
		getTrackName.put("PCT","Plouescat");
		getTrackName.put("PLU","Plumpton(UK)");
		getTrackName.put("PDR","Pompadour");
		getTrackName.put("PNS","Pons");
		getTrackName.put("PTC","Pontchateau");
		getTrackName.put("PNT","Pontefract");
		getTrackName.put("PON","Pontefract(UK)");
		getTrackName.put("PVY","Pontivy");
		getTrackName.put("PTB","Pornicheet-La Baule");
		getTrackName.put("PV","Poverty Bay");
		getTrackName.put("PN","PRU");
		getTrackName.put("PRU","Prunelli Di Fiumorbo");
		getTrackName.put("28","Puerto Rico");
		getTrackName.put("PCH","Punchestown");
		getTrackName.put("QU","Quackenbruck");
		getTrackName.put("QS","Queen's Park Savannah");
		getTrackName.put("QBT","Questembert");
		getTrackName.put("RBT","Rambouillet");
		getTrackName.put("RJF","Randjesfontein");
		getTrackName.put("RN","Randwick");
		getTrackName.put("RNS","Ranes");
		getTrackName.put("RTD","Rastede");
		getTrackName.put("RED","Redcar(UK)");
		getTrackName.put("RDN","Redon");
		getTrackName.put("RI","Riccarton");
		getTrackName.put("RIP","Ripon(UK)");
		getTrackName.put("RSL","Rochefort-Sur-Loire");
		getTrackName.put("RC","Rockhampton");
		getTrackName.put("RM","Rome");
		getTrackName.put("RSC","Roscommon");
		getTrackName.put("RS","Rosehill");
		getTrackName.put("RST","Rostrennen");
		getTrackName.put("RT","Rotorua");
		getTrackName.put("RUN","Rouen");
		getTrackName.put("RLP","Royan-La-Palmyre");
		getTrackName.put("SU","Saarbruchen");
		getTrackName.put("SSS","Sable-Sur-Sarthe");
		getTrackName.put("22","Saga");
		getTrackName.put("SGR","Saint Galmier");
		getTrackName.put("SML","Saint Malo");
		getTrackName.put("SBC","Saint-Brieuc");
		getTrackName.put("SCD","Saint-Cloud");
		getTrackName.put("STC","Saint-Cloud");
		getTrackName.put("SJM","Saint-Jean-De-Monts");
		getTrackName.put("SOT","Saint-Quen-Des-Toits");
		getTrackName.put("SAL","Salisbury(UK)");
		getTrackName.put("SLB","Salisbury(UK)");
		getTrackName.put("SPR","Salon-De-Provence");
		getTrackName.put("SI","San Isidro");
		getTrackName.put("SP5","San Isidro (Arg)");
		getTrackName.put("SD","Sandfield");
		getTrackName.put("SN","Sandown");
		getTrackName.put("SAN","Sandown Park(UK)");
		getTrackName.put("SNP","Sandown(UK)");
		getTrackName.put("SAT","Santa Rita");
		getTrackName.put("SP0","Santiago (Chi)");
		getTrackName.put("SP","Sapporo");
		getTrackName.put("23","Sapporo");
		getTrackName.put("20","Sapporo(NAR)");
		getTrackName.put("SMR","Saumur");
		getTrackName.put("SVY","Savenay");
		getTrackName.put("SW","Schwanewede");
		getTrackName.put("SS","Scottsville");
		getTrackName.put("SCO","Scottsville");
		getTrackName.put("SDG","Sedgefield(UK)");
		getTrackName.put("SSL","Seiche-Sur-Le Loir");
		getTrackName.put("SPC","Senonnes-Pouance");
		getTrackName.put("SRG","Serge");
		getTrackName.put("ST","Sha Tin");
		getTrackName.put("SGM","Sille-Le-Guillaume");
		getTrackName.put("SIN","Singapore");
		getTrackName.put("SLG","Sligo");
		getTrackName.put("32","Sonoda");
		getTrackName.put("SO","Sonsbeck");
		getTrackName.put("SAF","South Africa");
		getTrackName.put("SHD","Southland");
		getTrackName.put("SOU","Southwell(UK)");
		getTrackName.put("STA","St. Andrews");
		getTrackName.put("SB","Sterrebeek");
		getTrackName.put("SSB","Strasbourg");
		getTrackName.put("SF","Stratford");
		getTrackName.put("STR","Stratford(UK)");
		getTrackName.put("STA","Stratford(UK)");
		getTrackName.put("TB","Taby");
		getTrackName.put("24","Takasaki");
		getTrackName.put("TN","Taranaki");
		getTrackName.put("TRB","Tarbes");
		getTrackName.put("TU","Tarumao");
		getTrackName.put("TI","Taumarunui");
		getTrackName.put("TAU","Taunton(UK)");
		getTrackName.put("TA","Te Aroha");
		getTrackName.put("THI","Thirsk(UK)");
		getTrackName.put("THR","Thirsk(UK)");
		getTrackName.put("THS","Thouars");
		getTrackName.put("TLS","Thurles");
		getTrackName.put("TM","Timaru");
		getTrackName.put("TIP","Tipperary");
		getTrackName.put("TK","Tokyo");
		getTrackName.put("25","Tokyo");
		getTrackName.put("TW","Toowoomba");
		getTrackName.put("TR","Torino");
		getTrackName.put("TLU","Toulouse");
		getTrackName.put("TRS","Tours");
		getTrackName.put("TOU","Tours");
		getTrackName.put("TOW","Towcester(UK)");
		getTrackName.put("TS","Townsville");
		getTrackName.put("TRL","Tralee");
		getTrackName.put("TRA","Tramore");
		getTrackName.put("TF","Turffontein");
		getTrackName.put("TFF","Turfontein");
		getTrackName.put("UP","Union Park");
		getTrackName.put("26","Urawa");
		getTrackName.put("UTT","Uttoxeter(UK)");
		getTrackName.put("VL","Vaal");
		getTrackName.put("VAA","Vaal");
		getTrackName.put("VN","Valencia");
		getTrackName.put("VP","Valparaiso");
		getTrackName.put("SP3","Valparaiso");
		getTrackName.put("VNN","Vannes");
		getTrackName.put("VR","Varese");
		getTrackName.put("VEN","Venaco");
		getTrackName.put("VE","Verden");
		getTrackName.put("VRT","Vertou");
		getTrackName.put("VSL","Vesoul");
		getTrackName.put("VCB","Vic-Bigorre");
		getTrackName.put("VFZ","Vic-Fezenzac");
		getTrackName.put("VHY","Vichy");
		getTrackName.put("VC","Victoria Park");
		getTrackName.put("VLL","Villeneuve");
		getTrackName.put("VT","Vincent");
		getTrackName.put("VIR","Vire");
		getTrackName.put("VTR","Vitre");
		getTrackName.put("VTX","Vitteaux");
		getTrackName.put("VTL","Vittel");
		getTrackName.put("WG","Wagga");
		getTrackName.put("WK","Waikato");
		getTrackName.put("WP","Waipa");
		getTrackName.put("WI","Waipukau");
		getTrackName.put("WA","Wairarapa");
		getTrackName.put("WOF","Walldorf");
		getTrackName.put("WN","Wanganui");
		getTrackName.put("WF","Warwick Farm");
		getTrackName.put("WAR","Warwick(UK)");
		getTrackName.put("WRW","Warwick(UK)");
		getTrackName.put("WTF","Waterfor & Tramore");
		getTrackName.put("WL","Wellington");
		getTrackName.put("WR","Werribee");
		getTrackName.put("WET","Wetherby(UK)");
		getTrackName.put("WEX","Wexford");
		getTrackName.put("WH","Wildeshausen");
		getTrackName.put("WIN","Wincanton(UK)");
		getTrackName.put("WSR","Windsor(UK)");
		getTrackName.put("WIS","Wissenbourg");
		getTrackName.put("WLV","Wolverhampton");
		getTrackName.put("WOL","Wolverhampton(UK)");
		getTrackName.put("WD","Woodville");
		getTrackName.put("WRC","Worcester(UK)");
		getTrackName.put("WOR","Worcester(UK)");
		getTrackName.put("YAR","Yarmouth(UK)");
		getTrackName.put("YRK","York");
		getTrackName.put("YOR","York(UK)");
		getTrackName.put("ZON","Zonza");
		getTrackName.put("ZW","Zweibrucken");
		
		return getTrackName.get(TrackCode);
	}

	public Boolean getIgnore() {
		return ignore;
	}
	public void setIgnore(Boolean ignore) {
		this.ignore = ignore;
	}

	public float getRaceStrength() {
		return RaceStrength;
	}

	public void setRaceStrength(float raceStrength) {
		RaceStrength = raceStrength;
	}

	public float getLast5AverageSpeed() {
		return Last5AverageSpeed;
	}

	public void setLast5AverageSpeed(float last5AverageSpeed) {
		Last5AverageSpeed = last5AverageSpeed;
	}

	public Boolean getOver90Days() {
		return over90Days;
	}

	public void setOver90Days(Boolean over90Days) {
		this.over90Days = over90Days;
	}

	public Boolean getOver365Days() {
		return over365Days;
	}

	public void setOver365Days(Boolean over365Days) {
		this.over365Days = over365Days;
	}

	public Boolean getTurfFlag() {
		return TurfFlag;
	}

	public void setTurfFlag(Boolean turfFlag) {
		TurfFlag = turfFlag;
	}

	public String getRaceShape() {
		return RaceShape;
	}

	public void setRaceShape(String raceShape) {
		RaceShape = raceShape;
	}
	

	public PotentialKeyRace getKeyRace() {
		return keyRace;
	}
	public void setKeyRace(PotentialKeyRace keyRace) {
		this.keyRace = keyRace;
	}
	
	public Boolean getOneTurn() {
		return oneTurn;
	}
	public void setOneTurn(Boolean oneTurn) {
		this.oneTurn = oneTurn;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
	// Generated


	public LocalDate getRaceDate() {
		return RaceDate;
	}
	public void setRaceDate(LocalDate raceDate) {
		RaceDate = raceDate;
	}
	public String getRaceDateString() {
		return RaceDate.getDayOfMonth() +
				RaceDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) +
				(RaceDate.getYear()-2000);
	}
	public int getDaysSinceLastRace() {
		return DaysSinceLastRace;
	}
	public void setDaysSinceLastRace(int daysSinceLastRace) {
		DaysSinceLastRace = daysSinceLastRace;
	}
	public String getTrackCode() {
		return TrackCode;
	}
	public void setTrackCode(String trackCode) {
		TrackCode = trackCode;
	}
	public String getBRISTrackCode() {
		return BRISTrackCode;
	}
	public void setBRISTrackCode(String bRISTrackCode) {
		BRISTrackCode = bRISTrackCode;
	}
	public int getRaceNumber() {
		return RaceNumber;
	}
	public void setRaceNumber(int raceNumber) {
		RaceNumber = raceNumber;
	}
	public String getTrackCondition() {
		return TrackCondition;
	}
	public void setTrackCondition(String trackCondition) {
		TrackCondition = trackCondition;
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
	public String getChuteIndicator() {
		return ChuteIndicator;
	}
	public void setChuteIndicator(String chuteIndicator) {
		ChuteIndicator = chuteIndicator;
	}
	public String getWagerTypes() {
		return WagerTypes;
	}
	public void setWagerTypes(String wagerTypes) {
		WagerTypes = wagerTypes;
	}
	public int getNumberOfEntrants() {
		return NumberOfEntrants;
	}
	public void setNumberOfEntrants(int numberOfEntrants) {
		NumberOfEntrants = numberOfEntrants;
	}
	public int getPostPosition() {
		return PostPosition;
	}
	public void setPostPosition(int postPosition) {
		PostPosition = postPosition;
	}
	public String getEquipment() {
		return Equipment;
	}
	public void setEquipment(String equipment) {
		Equipment = equipment;
	}
	public MedicationType getMedication() {
		return Medication;
	}
	public void setMedication(MedicationType medication) {
		Medication = medication;
	}
	public String getTripComment() {
		return TripComment;
	}
	public void setTripComment(String tripComment) {
		TripComment = tripComment;
	}
	public String getWinnersName() {
		return WinnersName;
	}
	public void setWinnersName(String winnersName) {
		WinnersName = winnersName;
	}
	public String getPlaceName() {
		return PlaceName;
	}
	public void setPlaceName(String placeName) {
		PlaceName = placeName;
	}
	public String getShowName() {
		return ShowName;
	}
	public void setShowName(String showName) {
		ShowName = showName;
	}
	public int getWinnersWeight() {
		return WinnersWeight;
	}
	public void setWinnersWeight(int winnersWeight) {
		WinnersWeight = winnersWeight;
	}
	public int getPlaceWeight() {
		return PlaceWeight;
	}
	public void setPlaceWeight(int placeWeight) {
		PlaceWeight = placeWeight;
	}
	public int getShowWeight() {
		return ShowWeight;
	}
	public void setShowWeight(int showWeight) {
		ShowWeight = showWeight;
	}
	public float getWinnersMargin() {
		return WinnersMargin;
	}
	public void setWinnersMargin(float winnersMargin) {
		WinnersMargin = winnersMargin;
	}
	public float getPlaceMargin() {
		return PlaceMargin;
	}
	public void setPlaceMargin(float placeMargin) {
		PlaceMargin = placeMargin;
	}
	public float getShowMargin() {
		return ShowMargin;
	}
	public void setShowMargin(float showMargin) {
		ShowMargin = showMargin;
	}
	public String getExtraCommentLine() {
		return ExtraCommentLine;
	}
	public void setExtraCommentLine(String extraCommentLine) {
		ExtraCommentLine = extraCommentLine;
	}
	public int getWeight() {
		return Weight;
	}
	public void setWeight(int weight) {
		Weight = weight;
	}
	public float getOdds() {
		return Odds;
	}
	public void setOdds(float odds) {
		Odds = odds;
	}
	public String getRaceClassification() {
		return RaceClassification;
	}
	public void setRaceClassification(String raceClassification) {
		RaceClassification = raceClassification;
	}
	public int getClaimingPrice() {
		return ClaimingPrice;
	}
	public void setClaimingPrice(int claimingPrice) {
		ClaimingPrice = claimingPrice;
	}
	public int getPurse() {
		return Purse;
	}
	public void setPurse(int purse) {
		Purse = purse;
	}
	public String getFirstCallPosition() {
		return FirstCallPosition;
	}
	public void setFirstCallPosition(String firstCallPosition) {
		FirstCallPosition = firstCallPosition;
	}
	public String getSecondCallPosition() {
		return SecondCallPosition;
	}
	public void setSecondCallPosition(String secondCallPosition) {
		SecondCallPosition = secondCallPosition;
	}
	public String getGateCallPosition() {
		return GateCallPosition;
	}
	public void setGateCallPosition(String gateCallPosition) {
		GateCallPosition = gateCallPosition;
	}
	public String getStretchPosition() {
		return StretchPosition;
	}
	public void setStretchPosition(String stretchPosition) {
		StretchPosition = stretchPosition;
	}
	public String getFinishPosition() {
		return FinishPosition;
	}
	public void setFinishPosition(String finishPosition) {
		FinishPosition = finishPosition;
	}
	public String getMoneyPosition() {
		return MoneyPosition;
	}
	public void setMoneyPosition(String moneyPosition) {
		MoneyPosition = moneyPosition;
	}
	public float getFirstCallBeatenLengthsLeader() {
		return FirstCallBeatenLengthsLeader;
	}
	public void setFirstCallBeatenLengthsLeader(float firstCallBeatenLengthsLeader) {
		FirstCallBeatenLengthsLeader = firstCallBeatenLengthsLeader;
	}
	public float getFirstCallBeatenLengthsOnly() {
		return FirstCallBeatenLengthsOnly;
	}
	public void setFirstCallBeatenLengthsOnly(float firstCallBeatenLengthsOnly) {
		FirstCallBeatenLengthsOnly = firstCallBeatenLengthsOnly;
	}
	public float getSecondCallBeatenLengthsLeader() {
		return SecondCallBeatenLengthsLeader;
	}
	public void setSecondCallBeatenLengthsLeader(float secondCallBeatenLengthsLeader) {
		SecondCallBeatenLengthsLeader = secondCallBeatenLengthsLeader;
	}
	public float getSecondCallBeatenLengthsOnly() {
		return SecondCallBeatenLengthsOnly;
	}
	public void setSecondCallBeatenLengthsOnly(float secondCallBeatenLengthsOnly) {
		SecondCallBeatenLengthsOnly = secondCallBeatenLengthsOnly;
	}
	public float getRaceShapeFirstCall() {
		return RaceShapeFirstCall;
	}
	public void setRaceShapeFirstCall(float raceShapeFirstCall) {
		RaceShapeFirstCall = raceShapeFirstCall;
	}
	public float getStretchBeatenLengthsLeader() {
		return StretchBeatenLengthsLeader;
	}
	public void setStretchBeatenLengthsLeader(float stretchBeatenLengthsLeader) {
		StretchBeatenLengthsLeader = stretchBeatenLengthsLeader;
	}
	public float getStretchBeatenLengthsOnly() {
		return StretchBeatenLengthsOnly;
	}
	public void setStretchBeatenLengthsOnly(float stretchBeatenLengthsOnly) {
		StretchBeatenLengthsOnly = stretchBeatenLengthsOnly;
	}
	public float getFinishBeatenLengthsLeader() {
		return FinishBeatenLengthsLeader;
	}
	public void setFinishBeatenLengthsLeader(float finishBeatenLengthsLeader) {
		FinishBeatenLengthsLeader = finishBeatenLengthsLeader;
	}
	public float getFinishBeatenLengthsOnly() {
		return FinishBeatenLengthsOnly;
	}
	public void setFinishBeatenLengthsOnly(float finishBeatenLengthsOnly) {
		FinishBeatenLengthsOnly = finishBeatenLengthsOnly;
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
	public int getE1() {
		return getFurlongs() < 8 ? PaceFigure2F : PaceFigure4F;
	}
	public int getE2() {
		return getFurlongs() < 8 ? PaceFigure4F : PaceFigure6F;
	}	
	public int getPaceFigureLate() {
		return PaceFigureLate;
	}
	public void setPaceFigureLate(int paceFigureLate) {
		PaceFigureLate = paceFigureLate;
	}
	public float getRaceRating() {
		return RaceRating;
	}
	public void setRaceRating(float raceRating) {
		RaceRating = raceRating;
	}
	public float getClassRating() {
		return ClassRating;
	}
	public void setClassRating(float classRating) {
		ClassRating = classRating;
	}
	public int getSpeedPar() {
		return SpeedPar;
	}
	public void setSpeedPar(int speedPar) {
		SpeedPar = speedPar;
	}
	public int getBRISSpeedRating() {
		return BRISSpeedRating;
	}
	public void setBRISSpeedRating(int bRISSpeedRating) {
		BRISSpeedRating = bRISSpeedRating;
	}
	public int getSpeedRating() {
		return SpeedRating;
	}
	public void setSpeedRating(int speedRating) {
		SpeedRating = speedRating;
	}
	public int getTrackVariant() {
		return TrackVariant;
	}
	public void setTrackVariant(int trackVariant) {
		TrackVariant = trackVariant;
	}
	public int getAdjustedSpeedRating() {
		return SpeedRating + TrackVariant;
	}
	public float getFraction2F() {
		return Fraction2F;
	}
	public void setFraction2F(float fraction2f) {
		Fraction2F = fraction2f;
	}
	public float getFraction3F() {
		return Fraction3F;
	}
	public void setFraction3F(float fraction3f) {
		Fraction3F = fraction3f;
	}
	public float getFraction4F() {
		return Fraction4F;
	}
	public void setFraction4F(float fraction4f) {
		Fraction4F = fraction4f;
	}
	public float getFraction5F() {
		return Fraction5F;
	}
	public void setFraction5F(float fraction5f) {
		Fraction5F = fraction5f;
	}
	public float getFraction6F() {
		return Fraction6F;
	}
	public void setFraction6F(float fraction6f) {
		Fraction6F = fraction6f;
	}
	public float getFraction7F() {
		return Fraction7F;
	}
	public void setFraction7F(float fraction7f) {
		Fraction7F = fraction7f;
	}
	public float getFraction8F() {
		return Fraction8F;
	}
	public void setFraction8F(float fraction8f) {
		Fraction8F = fraction8f;
	}
	public float getFraction10F() {
		return Fraction10F;
	}
	public void setFraction10F(float fraction10f) {
		Fraction10F = fraction10f;
	}
	public float getFraction12F() {
		return Fraction12F;
	}
	public void setFraction12F(float fraction12f) {
		Fraction12F = fraction12f;
	}
	public float getFraction14F() {
		return Fraction14F;
	}
	public void setFraction14F(float fraction14f) {
		Fraction14F = fraction14f;
	}
	public float getFraction16F() {
		return Fraction16F;
	}
	public void setFraction16F(float fraction16f) {
		Fraction16F = fraction16f;
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
	public float getCalcFraction1() {
		return CalcFraction1;
	}

	public void setCalcFraction1(float calcFraction1) {
		CalcFraction1 = calcFraction1;
	}

	public float getCalcFraction2() {
		return CalcFraction2;
	}

	public void setCalcFraction2(float calcFraction2) {
		CalcFraction2 = calcFraction2;
	}

	public float getCalcFraction3() {
		return CalcFraction3;
	}

	public void setCalcFraction3(float calcFraction3) {
		CalcFraction3 = calcFraction3;
	}

	public float getCalcFinalTime() {
		return CalcFinalTime;
	}

	public void setCalcFinalTime(float calcFinalTime) {
		CalcFinalTime = calcFinalTime;
	}

	public float getSplit1() {
		return Split1;
	}

	public void setSplit1(float split1) {
		Split1 = split1;
	}

	public float getSplit2() {
		return Split2;
	}

	public void setSplit2(float split2) {
		Split2 = split2;
	}

	public float getSplit3() {
		return Split3;
	}

	public void setSplit3(float split3) {
		Split3 = split3;
	}

	public String getClaimedCode() {
		return ClaimedCode;
	}
	public void setClaimedCode(String claimedCode) {
		ClaimedCode = claimedCode;
	}
	public String getTrainer() {
		return Trainer;
	}
	public void setTrainer(String trainer) {
		Trainer = trainer;
	}
	public String getJockey() {
		return Jockey;
	}
	public void setJockey(String jockey) {
		Jockey = jockey;
	}
	public int getApprenticeWeightAllowed() {
		return ApprenticeWeightAllowed;
	}
	public void setApprenticeWeightAllowed(int apprenticeWeightAllowed) {
		ApprenticeWeightAllowed = apprenticeWeightAllowed;
	}
	public RaceType getRaceType() {
		return RaceType;
	}
	public void setRaceType(RaceType raceType) {
		RaceType = raceType;
	}
	public String getCompleteRaceCondition() {
		return CompleteRaceCondition;
	}
	public void setCompleteRaceCondition(String completeRaceCondition) {
		CompleteRaceCondition = completeRaceCondition;
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
	public String getStatebredFlag() {
		return StatebredFlag;
	}
	public void setStatebredFlag(String statebredFlag) {
		StatebredFlag = statebredFlag;
	}
	public String getRestrictedQualifierFlag() {
		return RestrictedQualifierFlag;
	}
	public void setRestrictedQualifierFlag(String restrictedQualifierFlag) {
		RestrictedQualifierFlag = restrictedQualifierFlag;
	}
	public int getFavoriteFlag() {
		return FavoriteFlag;
	}
	public void setFavoriteFlag(int favoriteFlag) {
		FavoriteFlag = favoriteFlag;
	}
	public int getFrontBandagesFlag() {
		return FrontBandagesFlag;
	}
	public void setFrontBandagesFlag(int frontBandagesFlag) {
		FrontBandagesFlag = frontBandagesFlag;
	}
	public String getBarShoeFlag() {
		return BarShoeFlag;
	}
	public void setBarShoeFlag(String barShoeFlag) {
		BarShoeFlag = barShoeFlag;
	}
	public String getCompanyLineCodes() {
		return CompanyLineCodes;
	}
	public void setCompanyLineCodes(String companyLineCodes) {
		CompanyLineCodes = companyLineCodes;
	}
	public int getLowClaimingPriceOfRace() {
		return LowClaimingPriceOfRace;
	}
	public void setLowClaimingPriceOfRace(int lowClaimingPriceOfRace) {
		LowClaimingPriceOfRace = lowClaimingPriceOfRace;
	}
	public int getHighClaimingPriceOfRace() {
		return HighClaimingPriceOfRace;
	}
	public void setHighClaimingPriceOfRace(int highClaimingPriceOfRace) {
		HighClaimingPriceOfRace = highClaimingPriceOfRace;
	}
	public String getExtendedStartComment() {
		return ExtendedStartComment;
	}
	public void setExtendedStartComment(String extendedStartComment) {
		ExtendedStartComment = extendedStartComment;
	}
	public String getSealedTrackFlag() {
		return SealedTrackFlag;
	}
	public void setSealedTrackFlag(String sealedTrackFlag) {
		SealedTrackFlag = sealedTrackFlag;
	}
	public String getAllWeatherSurfaceFlag() {
		return AllWeatherSurfaceFlag;
	}
	public void setAllWeatherSurfaceFlag(String allWeatherSurfaceFlag) {
		AllWeatherSurfaceFlag = allWeatherSurfaceFlag;
	}
	public Boolean getOffTheTurfFlag() {
		return OffTheTurfFlag;
	}
	public void setOffTheTurfFlag(Boolean offTheTurfFlag) {
		OffTheTurfFlag = offTheTurfFlag;
	}
	public String getEntryFlag() {
		return EntryFlag;
	}
	public void setEntryFlag(String entryFlag) {
		EntryFlag = entryFlag;
	}
	public String getStartCallPosition() {
		return StartCallPosition;
	}
	public void setStartCallPosition(String startCallPosition) {
		StartCallPosition = startCallPosition;
	}
	public String getTrainerChangeDate() {
		return TrainerChangeDate;
	}

	public void setTrainerChangeDate(String trainerChangeDate) {
		TrainerChangeDate = trainerChangeDate;
	}

	public int getTrainerChangeStarts() {
		return TrainerChangeStarts;
	}

	public void setTrainerChangeStarts(int trainerChangeStarts) {
		TrainerChangeStarts = trainerChangeStarts;
	}

	public int getTrainerChangeWins() {
		return TrainerChangeWins;
	}

	public void setTrainerChangeWins(int trainerChangeWins) {
		TrainerChangeWins = trainerChangeWins;
	}

	public int getTrainerChangePlaces() {
		return TrainerChangePlaces;
	}

	public void setTrainerChangePlaces(int trainerChangePlaces) {
		TrainerChangePlaces = trainerChangePlaces;
	}

	public int getTrainerChangeShows() {
		return TrainerChangeShows;
	}

	public void setTrainerChangeShows(int trainerChangeShows) {
		TrainerChangeShows = trainerChangeShows;
	}

	public float getTrainerChangeROI() {
		return TrainerChangeROI;
	}

	public void setTrainerChangeROI(float trainerChangeROI) {
		TrainerChangeROI = trainerChangeROI;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((AgeRestriction == null) ? 0 : AgeRestriction.hashCode());
		result = prime * result + ((AgeRestrictionRange == null) ? 0 : AgeRestrictionRange.hashCode());
		result = prime * result + ((AllWeatherSurfaceFlag == null) ? 0 : AllWeatherSurfaceFlag.hashCode());
		result = prime * result + ApprenticeWeightAllowed;
		result = prime * result + BRISSpeedRating;
		result = prime * result + ((BRISTrackCode == null) ? 0 : BRISTrackCode.hashCode());
		result = prime * result + ((BarShoeFlag == null) ? 0 : BarShoeFlag.hashCode());
		result = prime * result + ((ChuteIndicator == null) ? 0 : ChuteIndicator.hashCode());
		result = prime * result + ((ClaimedCode == null) ? 0 : ClaimedCode.hashCode());
		result = prime * result + ClaimingPrice;
		result = prime * result + Float.floatToIntBits(ClassRating);
		result = prime * result + ((CompanyLineCodes == null) ? 0 : CompanyLineCodes.hashCode());
		result = prime * result + ((CompleteRaceCondition == null) ? 0 : CompleteRaceCondition.hashCode());
		result = prime * result + DaysSinceLastRace;
		result = prime * result + Distance;
		result = prime * result + ((EntryFlag == null) ? 0 : EntryFlag.hashCode());
		result = prime * result + ((Equipment == null) ? 0 : Equipment.hashCode());
		result = prime * result + ((ExtendedStartComment == null) ? 0 : ExtendedStartComment.hashCode());
		result = prime * result + ((ExtraCommentLine == null) ? 0 : ExtraCommentLine.hashCode());
		result = prime * result + FavoriteFlag;
		result = prime * result + Float.floatToIntBits(FinalTime);
		result = prime * result + Float.floatToIntBits(FinishBeatenLengthsLeader);
		result = prime * result + Float.floatToIntBits(FinishBeatenLengthsOnly);
		result = prime * result + ((FinishPosition == null) ? 0 : FinishPosition.hashCode());
		result = prime * result + Float.floatToIntBits(FirstCallBeatenLengthsLeader);
		result = prime * result + Float.floatToIntBits(FirstCallBeatenLengthsOnly);
		result = prime * result + ((FirstCallPosition == null) ? 0 : FirstCallPosition.hashCode());
		result = prime * result + Float.floatToIntBits(Fraction1);
		result = prime * result + Float.floatToIntBits(Fraction10F);
		result = prime * result + Float.floatToIntBits(Fraction12F);
		result = prime * result + Float.floatToIntBits(Fraction14F);
		result = prime * result + Float.floatToIntBits(Fraction16F);
		result = prime * result + Float.floatToIntBits(Fraction2);
		result = prime * result + Float.floatToIntBits(Fraction2F);
		result = prime * result + Float.floatToIntBits(Fraction3);
		result = prime * result + Float.floatToIntBits(Fraction3F);
		result = prime * result + Float.floatToIntBits(Fraction4F);
		result = prime * result + Float.floatToIntBits(Fraction5F);
		result = prime * result + Float.floatToIntBits(Fraction6F);
		result = prime * result + Float.floatToIntBits(Fraction7F);
		result = prime * result + Float.floatToIntBits(Fraction8F);
		result = prime * result + FrontBandagesFlag;
		result = prime * result + ((GateCallPosition == null) ? 0 : GateCallPosition.hashCode());
		result = prime * result + HighClaimingPriceOfRace;
		result = prime * result + ((Jockey == null) ? 0 : Jockey.hashCode());
		result = prime * result + Float.floatToIntBits(Last5AverageSpeed);
		result = prime * result + LowClaimingPriceOfRace;
		result = prime * result + ((Medication == null) ? 0 : Medication.hashCode());
		result = prime * result + ((MoneyPosition == null) ? 0 : MoneyPosition.hashCode());
		result = prime * result + NumberOfEntrants;
		result = prime * result + Float.floatToIntBits(Odds);
		result = prime * result + ((OffTheTurfFlag == null) ? 0 : OffTheTurfFlag.hashCode());
		result = prime * result + PaceFigure10F;
		result = prime * result + PaceFigure2F;
		result = prime * result + PaceFigure4F;
		result = prime * result + PaceFigure6F;
		result = prime * result + PaceFigure8F;
		result = prime * result + PaceFigureLate;
		result = prime * result + Float.floatToIntBits(PlaceMargin);
		result = prime * result + ((PlaceName == null) ? 0 : PlaceName.hashCode());
		result = prime * result + PlaceWeight;
		result = prime * result + PostPosition;
		result = prime * result + Purse;
		result = prime * result + ((RaceClassification == null) ? 0 : RaceClassification.hashCode());
		result = prime * result + ((RaceDate == null) ? 0 : RaceDate.hashCode());
		result = prime * result + RaceNumber;
		result = prime * result + Float.floatToIntBits(RaceRating);
		result = prime * result + Float.floatToIntBits(RaceShapeFirstCall);
		result = prime * result + Float.floatToIntBits(RaceShapeSecondCall);
		result = prime * result + Float.floatToIntBits(RaceStrength);
		result = prime * result + ((RaceType == null) ? 0 : RaceType.hashCode());
		result = prime * result + ((RestrictedQualifierFlag == null) ? 0 : RestrictedQualifierFlag.hashCode());
		result = prime * result + ((SealedTrackFlag == null) ? 0 : SealedTrackFlag.hashCode());
		result = prime * result + Float.floatToIntBits(SecondCallBeatenLengthsLeader);
		result = prime * result + Float.floatToIntBits(SecondCallBeatenLengthsOnly);
		result = prime * result + ((SecondCallPosition == null) ? 0 : SecondCallPosition.hashCode());
		result = prime * result + ((SexRestriction == null) ? 0 : SexRestriction.hashCode());
		result = prime * result + Float.floatToIntBits(ShowMargin);
		result = prime * result + ((ShowName == null) ? 0 : ShowName.hashCode());
		result = prime * result + ShowWeight;
		result = prime * result + SpeedPar;
		result = prime * result + SpeedRating;
		result = prime * result + ((StartCallPosition == null) ? 0 : StartCallPosition.hashCode());
		result = prime * result + ((StatebredFlag == null) ? 0 : StatebredFlag.hashCode());
		result = prime * result + Float.floatToIntBits(StretchBeatenLengthsLeader);
		result = prime * result + Float.floatToIntBits(StretchBeatenLengthsOnly);
		result = prime * result + ((StretchPosition == null) ? 0 : StretchPosition.hashCode());
		result = prime * result + ((Surface == null) ? 0 : Surface.hashCode());
		result = prime * result + ((TrackCode == null) ? 0 : TrackCode.hashCode());
		result = prime * result + ((TrackCondition == null) ? 0 : TrackCondition.hashCode());
		result = prime * result + TrackVariant;
		result = prime * result + ((Trainer == null) ? 0 : Trainer.hashCode());
		result = prime * result + ((TripComment == null) ? 0 : TripComment.hashCode());
		result = prime * result + ((WagerTypes == null) ? 0 : WagerTypes.hashCode());
		result = prime * result + Weight;
		result = prime * result + Float.floatToIntBits(WinnersMargin);
		result = prime * result + ((WinnersName == null) ? 0 : WinnersName.hashCode());
		result = prime * result + WinnersWeight;
		result = prime * result + ((ignore == null) ? 0 : ignore.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + thisRaceNumber;
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
		PastPerformance other = (PastPerformance) obj;
		if (AgeRestriction != other.AgeRestriction)
			return false;
		if (AgeRestrictionRange != other.AgeRestrictionRange)
			return false;
		if (AllWeatherSurfaceFlag == null) {
			if (other.AllWeatherSurfaceFlag != null)
				return false;
		} else if (!AllWeatherSurfaceFlag.equals(other.AllWeatherSurfaceFlag))
			return false;
		if (ApprenticeWeightAllowed != other.ApprenticeWeightAllowed)
			return false;
		if (BRISSpeedRating != other.BRISSpeedRating)
			return false;
		if (BRISTrackCode == null) {
			if (other.BRISTrackCode != null)
				return false;
		} else if (!BRISTrackCode.equals(other.BRISTrackCode))
			return false;
		if (BarShoeFlag == null) {
			if (other.BarShoeFlag != null)
				return false;
		} else if (!BarShoeFlag.equals(other.BarShoeFlag))
			return false;
		if (ChuteIndicator == null) {
			if (other.ChuteIndicator != null)
				return false;
		} else if (!ChuteIndicator.equals(other.ChuteIndicator))
			return false;
		if (ClaimedCode == null) {
			if (other.ClaimedCode != null)
				return false;
		} else if (!ClaimedCode.equals(other.ClaimedCode))
			return false;
		if (ClaimingPrice != other.ClaimingPrice)
			return false;
		if (Float.floatToIntBits(ClassRating) != Float.floatToIntBits(other.ClassRating))
			return false;
		if (CompanyLineCodes == null) {
			if (other.CompanyLineCodes != null)
				return false;
		} else if (!CompanyLineCodes.equals(other.CompanyLineCodes))
			return false;
		if (CompleteRaceCondition == null) {
			if (other.CompleteRaceCondition != null)
				return false;
		} else if (!CompleteRaceCondition.equals(other.CompleteRaceCondition))
			return false;
		if (DaysSinceLastRace != other.DaysSinceLastRace)
			return false;
		if (Distance != other.Distance)
			return false;
		if (EntryFlag == null) {
			if (other.EntryFlag != null)
				return false;
		} else if (!EntryFlag.equals(other.EntryFlag))
			return false;
		if (Equipment == null) {
			if (other.Equipment != null)
				return false;
		} else if (!Equipment.equals(other.Equipment))
			return false;
		if (ExtendedStartComment == null) {
			if (other.ExtendedStartComment != null)
				return false;
		} else if (!ExtendedStartComment.equals(other.ExtendedStartComment))
			return false;
		if (ExtraCommentLine == null) {
			if (other.ExtraCommentLine != null)
				return false;
		} else if (!ExtraCommentLine.equals(other.ExtraCommentLine))
			return false;
		if (FavoriteFlag != other.FavoriteFlag)
			return false;
		if (Float.floatToIntBits(FinalTime) != Float.floatToIntBits(other.FinalTime))
			return false;
		if (Float.floatToIntBits(FinishBeatenLengthsLeader) != Float.floatToIntBits(other.FinishBeatenLengthsLeader))
			return false;
		if (Float.floatToIntBits(FinishBeatenLengthsOnly) != Float.floatToIntBits(other.FinishBeatenLengthsOnly))
			return false;
		if (FinishPosition == null) {
			if (other.FinishPosition != null)
				return false;
		} else if (!FinishPosition.equals(other.FinishPosition))
			return false;
		if (Float.floatToIntBits(FirstCallBeatenLengthsLeader) != Float
				.floatToIntBits(other.FirstCallBeatenLengthsLeader))
			return false;
		if (Float.floatToIntBits(FirstCallBeatenLengthsOnly) != Float.floatToIntBits(other.FirstCallBeatenLengthsOnly))
			return false;
		if (FirstCallPosition == null) {
			if (other.FirstCallPosition != null)
				return false;
		} else if (!FirstCallPosition.equals(other.FirstCallPosition))
			return false;
		if (Float.floatToIntBits(Fraction1) != Float.floatToIntBits(other.Fraction1))
			return false;
		if (Float.floatToIntBits(Fraction10F) != Float.floatToIntBits(other.Fraction10F))
			return false;
		if (Float.floatToIntBits(Fraction12F) != Float.floatToIntBits(other.Fraction12F))
			return false;
		if (Float.floatToIntBits(Fraction14F) != Float.floatToIntBits(other.Fraction14F))
			return false;
		if (Float.floatToIntBits(Fraction16F) != Float.floatToIntBits(other.Fraction16F))
			return false;
		if (Float.floatToIntBits(Fraction2) != Float.floatToIntBits(other.Fraction2))
			return false;
		if (Float.floatToIntBits(Fraction2F) != Float.floatToIntBits(other.Fraction2F))
			return false;
		if (Float.floatToIntBits(Fraction3) != Float.floatToIntBits(other.Fraction3))
			return false;
		if (Float.floatToIntBits(Fraction3F) != Float.floatToIntBits(other.Fraction3F))
			return false;
		if (Float.floatToIntBits(Fraction4F) != Float.floatToIntBits(other.Fraction4F))
			return false;
		if (Float.floatToIntBits(Fraction5F) != Float.floatToIntBits(other.Fraction5F))
			return false;
		if (Float.floatToIntBits(Fraction6F) != Float.floatToIntBits(other.Fraction6F))
			return false;
		if (Float.floatToIntBits(Fraction7F) != Float.floatToIntBits(other.Fraction7F))
			return false;
		if (Float.floatToIntBits(Fraction8F) != Float.floatToIntBits(other.Fraction8F))
			return false;
		if (FrontBandagesFlag != other.FrontBandagesFlag)
			return false;
		if (GateCallPosition == null) {
			if (other.GateCallPosition != null)
				return false;
		} else if (!GateCallPosition.equals(other.GateCallPosition))
			return false;
		if (HighClaimingPriceOfRace != other.HighClaimingPriceOfRace)
			return false;
		if (Jockey == null) {
			if (other.Jockey != null)
				return false;
		} else if (!Jockey.equals(other.Jockey))
			return false;
		if (Float.floatToIntBits(Last5AverageSpeed) != Float.floatToIntBits(other.Last5AverageSpeed))
			return false;
		if (LowClaimingPriceOfRace != other.LowClaimingPriceOfRace)
			return false;
		if (Medication != other.Medication)
			return false;
		if (MoneyPosition == null) {
			if (other.MoneyPosition != null)
				return false;
		} else if (!MoneyPosition.equals(other.MoneyPosition))
			return false;
		if (NumberOfEntrants != other.NumberOfEntrants)
			return false;
		if (Float.floatToIntBits(Odds) != Float.floatToIntBits(other.Odds))
			return false;
		if (OffTheTurfFlag == null) {
			if (other.OffTheTurfFlag != null)
				return false;
		} else if (!OffTheTurfFlag.equals(other.OffTheTurfFlag))
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
		if (Float.floatToIntBits(PlaceMargin) != Float.floatToIntBits(other.PlaceMargin))
			return false;
		if (PlaceName == null) {
			if (other.PlaceName != null)
				return false;
		} else if (!PlaceName.equals(other.PlaceName))
			return false;
		if (PlaceWeight != other.PlaceWeight)
			return false;
		if (PostPosition != other.PostPosition)
			return false;
		if (Purse != other.Purse)
			return false;
		if (RaceClassification == null) {
			if (other.RaceClassification != null)
				return false;
		} else if (!RaceClassification.equals(other.RaceClassification))
			return false;
		if (RaceDate == null) {
			if (other.RaceDate != null)
				return false;
		} else if (!RaceDate.equals(other.RaceDate))
			return false;
		if (RaceNumber != other.RaceNumber)
			return false;
		if (Float.floatToIntBits(RaceRating) != Float.floatToIntBits(other.RaceRating))
			return false;
		if (Float.floatToIntBits(RaceShapeFirstCall) != Float.floatToIntBits(other.RaceShapeFirstCall))
			return false;
		if (Float.floatToIntBits(RaceShapeSecondCall) != Float.floatToIntBits(other.RaceShapeSecondCall))
			return false;
		if (Float.floatToIntBits(RaceStrength) != Float.floatToIntBits(other.RaceStrength))
			return false;
		if (RaceType != other.RaceType)
			return false;
		if (RestrictedQualifierFlag == null) {
			if (other.RestrictedQualifierFlag != null)
				return false;
		} else if (!RestrictedQualifierFlag.equals(other.RestrictedQualifierFlag))
			return false;
		if (SealedTrackFlag == null) {
			if (other.SealedTrackFlag != null)
				return false;
		} else if (!SealedTrackFlag.equals(other.SealedTrackFlag))
			return false;
		if (Float.floatToIntBits(SecondCallBeatenLengthsLeader) != Float
				.floatToIntBits(other.SecondCallBeatenLengthsLeader))
			return false;
		if (Float.floatToIntBits(SecondCallBeatenLengthsOnly) != Float
				.floatToIntBits(other.SecondCallBeatenLengthsOnly))
			return false;
		if (SecondCallPosition == null) {
			if (other.SecondCallPosition != null)
				return false;
		} else if (!SecondCallPosition.equals(other.SecondCallPosition))
			return false;
		if (SexRestriction != other.SexRestriction)
			return false;
		if (Float.floatToIntBits(ShowMargin) != Float.floatToIntBits(other.ShowMargin))
			return false;
		if (ShowName == null) {
			if (other.ShowName != null)
				return false;
		} else if (!ShowName.equals(other.ShowName))
			return false;
		if (ShowWeight != other.ShowWeight)
			return false;
		if (SpeedPar != other.SpeedPar)
			return false;
		if (SpeedRating != other.SpeedRating)
			return false;
		if (StartCallPosition == null) {
			if (other.StartCallPosition != null)
				return false;
		} else if (!StartCallPosition.equals(other.StartCallPosition))
			return false;
		if (StatebredFlag == null) {
			if (other.StatebredFlag != null)
				return false;
		} else if (!StatebredFlag.equals(other.StatebredFlag))
			return false;
		if (Float.floatToIntBits(StretchBeatenLengthsLeader) != Float.floatToIntBits(other.StretchBeatenLengthsLeader))
			return false;
		if (Float.floatToIntBits(StretchBeatenLengthsOnly) != Float.floatToIntBits(other.StretchBeatenLengthsOnly))
			return false;
		if (StretchPosition == null) {
			if (other.StretchPosition != null)
				return false;
		} else if (!StretchPosition.equals(other.StretchPosition))
			return false;
		if (Surface != other.Surface)
			return false;
		if (TrackCode == null) {
			if (other.TrackCode != null)
				return false;
		} else if (!TrackCode.equals(other.TrackCode))
			return false;
		if (TrackCondition == null) {
			if (other.TrackCondition != null)
				return false;
		} else if (!TrackCondition.equals(other.TrackCondition))
			return false;
		if (TrackVariant != other.TrackVariant)
			return false;
		if (Trainer == null) {
			if (other.Trainer != null)
				return false;
		} else if (!Trainer.equals(other.Trainer))
			return false;
		if (TripComment == null) {
			if (other.TripComment != null)
				return false;
		} else if (!TripComment.equals(other.TripComment))
			return false;
		if (WagerTypes == null) {
			if (other.WagerTypes != null)
				return false;
		} else if (!WagerTypes.equals(other.WagerTypes))
			return false;
		if (Weight != other.Weight)
			return false;
		if (Float.floatToIntBits(WinnersMargin) != Float.floatToIntBits(other.WinnersMargin))
			return false;
		if (WinnersName == null) {
			if (other.WinnersName != null)
				return false;
		} else if (!WinnersName.equals(other.WinnersName))
			return false;
		if (WinnersWeight != other.WinnersWeight)
			return false;
		if (ignore == null) {
			if (other.ignore != null)
				return false;
		} else if (!ignore.equals(other.ignore))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (thisRaceNumber != other.thisRaceNumber)
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("PastPerformance [RaceDate=").append(RaceDate).append(", DaysSinceLastRace=")
				.append(DaysSinceLastRace).append(", TrackCode=").append(TrackCode).append(", BRISTrackCode=")
				.append(BRISTrackCode).append(", RaceNumber=").append(RaceNumber).append(", TrackCondition=")
				.append(TrackCondition).append(", Distance=").append(Distance).append(", Surface=").append(Surface)
				.append(", ChuteIndicator=").append(ChuteIndicator).append(", WagerTypes=").append(WagerTypes)
				.append(", NumberOfEntrants=").append(NumberOfEntrants).append(", PostPosition=").append(PostPosition)
				.append(", Equipment=").append(Equipment).append(", Medication=").append(Medication)
				.append(", TripComment=").append(TripComment).append(", WinnersName=").append(WinnersName)
				.append(", PlaceName=").append(PlaceName).append(", ShowName=").append(ShowName)
				.append(", WinnersWeight=").append(WinnersWeight).append(", PlaceWeight=").append(PlaceWeight)
				.append(", ShowWeight=").append(ShowWeight).append(", WinnersMargin=").append(WinnersMargin)
				.append(", PlaceMargin=").append(PlaceMargin).append(", ShowMargin=").append(ShowMargin)
				.append(", ExtraCommentLine=").append(ExtraCommentLine).append(", Weight=").append(Weight)
				.append(", Odds=").append(Odds).append(", EntryFlag=").append(EntryFlag).append(", RaceClassification=")
				.append(RaceClassification).append(", ClaimingPrice=").append(ClaimingPrice).append(", Purse=")
				.append(Purse).append(", StartCallPosition=").append(StartCallPosition).append(", FirstCallPosition=")
				.append(FirstCallPosition).append(", SecondCallPosition=").append(SecondCallPosition)
				.append(", GateCallPosition=").append(GateCallPosition).append(", StretchPosition=")
				.append(StretchPosition).append(", FinishPosition=").append(FinishPosition).append(", MoneyPosition=")
				.append(MoneyPosition).append(", FirstCallBeatenLengthsLeader=").append(FirstCallBeatenLengthsLeader)
				.append(", FirstCallBeatenLengthsOnly=").append(FirstCallBeatenLengthsOnly)
				.append(", SecondCallBeatenLengthsLeader=").append(SecondCallBeatenLengthsLeader)
				.append(", SecondCallBeatenLengthsOnly=").append(SecondCallBeatenLengthsOnly)
				.append(", RaceShapeFirstCall=").append(RaceShapeFirstCall).append(", StretchBeatenLengthsLeader=")
				.append(StretchBeatenLengthsLeader).append(", StretchBeatenLengthsOnly=")
				.append(StretchBeatenLengthsOnly).append(", FinishBeatenLengthsLeader=")
				.append(FinishBeatenLengthsLeader).append(", FinishBeatenLengthsOnly=").append(FinishBeatenLengthsOnly)
				.append(", RaceShapeSecondCall=").append(RaceShapeSecondCall).append(", PaceFigure2F=")
				.append(PaceFigure2F).append(", PaceFigure4F=").append(PaceFigure4F).append(", PaceFigure6F=")
				.append(PaceFigure6F).append(", PaceFigure8F=").append(PaceFigure8F).append(", PaceFigure10F=")
				.append(PaceFigure10F).append(", PaceFigureLate=").append(PaceFigureLate).append(", RaceRating=")
				.append(RaceRating).append(", ClassRating=").append(ClassRating).append(", SpeedPar=").append(SpeedPar)
				.append(", BRISSpeedRating=").append(BRISSpeedRating).append(", SpeedRating=").append(SpeedRating)
				.append(", TrackVariant=").append(TrackVariant).append(", Fraction2F=").append(Fraction2F)
				.append(", Fraction3F=").append(Fraction3F).append(", Fraction4F=").append(Fraction4F)
				.append(", Fraction5F=").append(Fraction5F).append(", Fraction6F=").append(Fraction6F)
				.append(", Fraction7F=").append(Fraction7F).append(", Fraction8F=").append(Fraction8F)
				.append(", Fraction10F=").append(Fraction10F).append(", Fraction12F=").append(Fraction12F)
				.append(", Fraction14F=").append(Fraction14F).append(", Fraction16F=").append(Fraction16F)
				.append(", Fraction1=").append(Fraction1).append(", Fraction2=").append(Fraction2)
				.append(", Fraction3=").append(Fraction3).append(", FinalTime=").append(FinalTime)
				.append(", ClaimedCode=").append(ClaimedCode).append(", Trainer=").append(Trainer).append(", Jockey=")
				.append(Jockey).append(", ApprenticeWeightAllowed=").append(ApprenticeWeightAllowed)
				.append(", RaceType=").append(RaceType).append(", CompleteRaceCondition=").append(CompleteRaceCondition)
				.append(", AgeRestriction=").append(AgeRestriction).append(", AgeRestrictionRange=")
				.append(AgeRestrictionRange).append(", SexRestriction=").append(SexRestriction)
				.append(", StatebredFlag=").append(StatebredFlag).append(", RestrictedQualifierFlag=")
				.append(RestrictedQualifierFlag).append(", FavoriteFlag=").append(FavoriteFlag)
				.append(", FrontBandagesFlag=").append(FrontBandagesFlag).append(", BarShoeFlag=").append(BarShoeFlag)
				.append(", CompanyLineCodes=").append(CompanyLineCodes).append(", LowClaimingPriceOfRace=")
				.append(LowClaimingPriceOfRace).append(", HighClaimingPriceOfRace=").append(HighClaimingPriceOfRace)
				.append(", ExtendedStartComment=").append(ExtendedStartComment).append(", SealedTrackFlag=")
				.append(SealedTrackFlag).append(", AllWeatherSurfaceFlag=").append(AllWeatherSurfaceFlag).append("]");
		return builder2.toString();
	}
	public PastPerformance() {
		super();
	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	@Generated("SparkTools")
	public static final class Builder {
		private LocalDate RaceDate;
		private int DaysSinceLastRace;
		private String TrackCode;
		private String BRISTrackCode;
		private int RaceNumber;
		private String TrackCondition;
		private int Distance;
		private SurfaceType Surface;
		private String ChuteIndicator;
		private String WagerTypes;
		private int NumberOfEntrants;
		private int PostPosition;
		private String Equipment;
		private MedicationType Medication;
		private String TripComment;
		private String WinnersName;
		private String PlaceName;
		private String ShowName;
		private int WinnersWeight;
		private int PlaceWeight;
		private int ShowWeight;
		private float WinnersMargin;
		private float PlaceMargin;
		private float ShowMargin;
		private String ExtraCommentLine;
		private int Weight;
		private float Odds;
		private String EntryFlag;
		private String RaceClassification;
		private int ClaimingPrice;
		private int Purse;
		private String StartCallPosition;
		private String FirstCallPosition;
		private String SecondCallPosition;
		private String GateCallPosition;
		private String StretchPosition;
		private String FinishPosition;
		private String MoneyPosition;
		private float FirstCallBeatenLengthsLeader;
		private float FirstCallBeatenLengthsOnly;
		private float SecondCallBeatenLengthsLeader;
		private float SecondCallBeatenLengthsOnly;
		private float RaceShapeFirstCall;
		private float StretchBeatenLengthsLeader;
		private float StretchBeatenLengthsOnly;
		private float FinishBeatenLengthsLeader;
		private float FinishBeatenLengthsOnly;
		private float RaceShapeSecondCall;
		private int PaceFigure2F;
		private int PaceFigure4F;
		private int PaceFigure6F;
		private int PaceFigure8F;
		private int PaceFigure10F;
		private int PaceFigureLate;
		private float RaceRating;
		private float ClassRating;
		private int SpeedPar;
		private int BRISSpeedRating;
		private int SpeedRating;
		private int TrackVariant;
		private float Fraction2F;
		private float Fraction3F;
		private float Fraction4F;
		private float Fraction5F;
		private float Fraction6F;
		private float Fraction7F;
		private float Fraction8F;
		private float Fraction10F;
		private float Fraction12F;
		private float Fraction14F;
		private float Fraction16F;
		private float Fraction1;
		private float Fraction2;
		private float Fraction3;
		private float FinalTime;
		private float CalcFraction1;
		private float CalcFraction2;
		private float CalcFraction3;
		private float CalcFinalTime;
		private float Split1;
		private float Split2;
		private float Split3;
		private String ClaimedCode;
		private String Trainer;
		private String Jockey;
		private int ApprenticeWeightAllowed;
		private RaceType RaceType;
		private String CompleteRaceCondition;
		private AgeRestrictionType AgeRestriction;
		private AgeRestrictionRangeType AgeRestrictionRange;
		private SexRestrictionType SexRestriction;
		private String StatebredFlag;
		private String RestrictedQualifierFlag;
		private int FavoriteFlag;
		private int FrontBandagesFlag;
		private String BarShoeFlag;
		private String CompanyLineCodes;
		private int LowClaimingPriceOfRace;
		private int HighClaimingPriceOfRace;
		private String ExtendedStartComment;
		private String SealedTrackFlag;
		private String AllWeatherSurfaceFlag;
		private Boolean OffTheTurfFlag;
		private String TrainerChangeDate;
		private int TrainerChangeStarts;
		private int TrainerChangeWins;
		private int TrainerChangePlaces;
		private int TrainerChangeShows;
		private float TrainerChangeROI;
		private String name;
		private int thisRaceNumber;
		private String trackName;
		private Boolean ignore;
		private float RaceStrength;
		private float Last5AverageSpeed;
		private Boolean over90Days;
		private Boolean over365Days;
		private Boolean TurfFlag;
		private String RaceShape;
		private float furlongs;
		private float miles;
		private int adjustedSpeedRating;
		private String raceDateString;
		private int e1;
		private int e2;
		private PotentialKeyRace keyRace;
		private Boolean oneTurn;
		private String footnote;
		private String flag;
		private String comment;

		private Builder() {
		}

		public Builder withRaceDate(LocalDate RaceDate) {
			this.RaceDate = RaceDate;
			return this;
		}

		public Builder withDaysSinceLastRace(int DaysSinceLastRace) {
			this.DaysSinceLastRace = DaysSinceLastRace;
			return this;
		}

		public Builder withTrackCode(String TrackCode) {
			this.TrackCode = TrackCode;
			return this;
		}

		public Builder withBRISTrackCode(String BRISTrackCode) {
			this.BRISTrackCode = BRISTrackCode;
			return this;
		}

		public Builder withRaceNumber(int RaceNumber) {
			this.RaceNumber = RaceNumber;
			return this;
		}

		public Builder withTrackCondition(String TrackCondition) {
			this.TrackCondition = TrackCondition;
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

		public Builder withChuteIndicator(String ChuteIndicator) {
			this.ChuteIndicator = ChuteIndicator;
			return this;
		}

		public Builder withWagerTypes(String WagerTypes) {
			this.WagerTypes = WagerTypes;
			return this;
		}

		public Builder withNumberOfEntrants(int NumberOfEntrants) {
			this.NumberOfEntrants = NumberOfEntrants;
			return this;
		}

		public Builder withPostPosition(int PostPosition) {
			this.PostPosition = PostPosition;
			return this;
		}

		public Builder withEquipment(String Equipment) {
			this.Equipment = Equipment;
			return this;
		}

		public Builder withMedication(MedicationType Medication) {
			this.Medication = Medication;
			return this;
		}

		public Builder withTripComment(String TripComment) {
			this.TripComment = TripComment;
			return this;
		}

		public Builder withWinnersName(String WinnersName) {
			this.WinnersName = WinnersName;
			return this;
		}

		public Builder withPlaceName(String PlaceName) {
			this.PlaceName = PlaceName;
			return this;
		}

		public Builder withShowName(String ShowName) {
			this.ShowName = ShowName;
			return this;
		}

		public Builder withWinnersWeight(int WinnersWeight) {
			this.WinnersWeight = WinnersWeight;
			return this;
		}

		public Builder withPlaceWeight(int PlaceWeight) {
			this.PlaceWeight = PlaceWeight;
			return this;
		}

		public Builder withShowWeight(int ShowWeight) {
			this.ShowWeight = ShowWeight;
			return this;
		}

		public Builder withWinnersMargin(float WinnersMargin) {
			this.WinnersMargin = WinnersMargin;
			return this;
		}

		public Builder withPlaceMargin(float PlaceMargin) {
			this.PlaceMargin = PlaceMargin;
			return this;
		}

		public Builder withShowMargin(float ShowMargin) {
			this.ShowMargin = ShowMargin;
			return this;
		}

		public Builder withExtraCommentLine(String ExtraCommentLine) {
			this.ExtraCommentLine = ExtraCommentLine;
			return this;
		}

		public Builder withWeight(int Weight) {
			this.Weight = Weight;
			return this;
		}

		public Builder withOdds(float Odds) {
			this.Odds = Odds;
			return this;
		}

		public Builder withEntryFlag(String EntryFlag) {
			this.EntryFlag = EntryFlag;
			return this;
		}

		public Builder withRaceClassification(String RaceClassification) {
			this.RaceClassification = RaceClassification;
			return this;
		}

		public Builder withClaimingPrice(int ClaimingPrice) {
			this.ClaimingPrice = ClaimingPrice;
			return this;
		}

		public Builder withPurse(int Purse) {
			this.Purse = Purse;
			return this;
		}

		public Builder withStartCallPosition(String StartCallPosition) {
			this.StartCallPosition = StartCallPosition;
			return this;
		}

		public Builder withFirstCallPosition(String FirstCallPosition) {
			this.FirstCallPosition = FirstCallPosition;
			return this;
		}

		public Builder withSecondCallPosition(String SecondCallPosition) {
			this.SecondCallPosition = SecondCallPosition;
			return this;
		}

		public Builder withGateCallPosition(String GateCallPosition) {
			this.GateCallPosition = GateCallPosition;
			return this;
		}

		public Builder withStretchPosition(String StretchPosition) {
			this.StretchPosition = StretchPosition;
			return this;
		}

		public Builder withFinishPosition(String FinishPosition) {
			this.FinishPosition = FinishPosition;
			return this;
		}

		public Builder withMoneyPosition(String MoneyPosition) {
			this.MoneyPosition = MoneyPosition;
			return this;
		}

		public Builder withFirstCallBeatenLengthsLeader(float FirstCallBeatenLengthsLeader) {
			this.FirstCallBeatenLengthsLeader = FirstCallBeatenLengthsLeader;
			return this;
		}

		public Builder withFirstCallBeatenLengthsOnly(float FirstCallBeatenLengthsOnly) {
			this.FirstCallBeatenLengthsOnly = FirstCallBeatenLengthsOnly;
			return this;
		}

		public Builder withSecondCallBeatenLengthsLeader(float SecondCallBeatenLengthsLeader) {
			this.SecondCallBeatenLengthsLeader = SecondCallBeatenLengthsLeader;
			return this;
		}

		public Builder withSecondCallBeatenLengthsOnly(float SecondCallBeatenLengthsOnly) {
			this.SecondCallBeatenLengthsOnly = SecondCallBeatenLengthsOnly;
			return this;
		}

		public Builder withRaceShapeFirstCall(float RaceShapeFirstCall) {
			this.RaceShapeFirstCall = RaceShapeFirstCall;
			return this;
		}

		public Builder withStretchBeatenLengthsLeader(float StretchBeatenLengthsLeader) {
			this.StretchBeatenLengthsLeader = StretchBeatenLengthsLeader;
			return this;
		}

		public Builder withStretchBeatenLengthsOnly(float StretchBeatenLengthsOnly) {
			this.StretchBeatenLengthsOnly = StretchBeatenLengthsOnly;
			return this;
		}

		public Builder withFinishBeatenLengthsLeader(float FinishBeatenLengthsLeader) {
			this.FinishBeatenLengthsLeader = FinishBeatenLengthsLeader;
			return this;
		}

		public Builder withFinishBeatenLengthsOnly(float FinishBeatenLengthsOnly) {
			this.FinishBeatenLengthsOnly = FinishBeatenLengthsOnly;
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

		public Builder withRaceRating(float RaceRating) {
			this.RaceRating = RaceRating;
			return this;
		}

		public Builder withClassRating(float ClassRating) {
			this.ClassRating = ClassRating;
			return this;
		}

		public Builder withSpeedPar(int SpeedPar) {
			this.SpeedPar = SpeedPar;
			return this;
		}

		public Builder withBRISSpeedRating(int BRISSpeedRating) {
			this.BRISSpeedRating = BRISSpeedRating;
			return this;
		}

		public Builder withSpeedRating(int SpeedRating) {
			this.SpeedRating = SpeedRating;
			return this;
		}

		public Builder withTrackVariant(int TrackVariant) {
			this.TrackVariant = TrackVariant;
			return this;
		}

		public Builder withFraction2F(float Fraction2F) {
			this.Fraction2F = Fraction2F;
			return this;
		}

		public Builder withFraction3F(float Fraction3F) {
			this.Fraction3F = Fraction3F;
			return this;
		}

		public Builder withFraction4F(float Fraction4F) {
			this.Fraction4F = Fraction4F;
			return this;
		}

		public Builder withFraction5F(float Fraction5F) {
			this.Fraction5F = Fraction5F;
			return this;
		}

		public Builder withFraction6F(float Fraction6F) {
			this.Fraction6F = Fraction6F;
			return this;
		}

		public Builder withFraction7F(float Fraction7F) {
			this.Fraction7F = Fraction7F;
			return this;
		}

		public Builder withFraction8F(float Fraction8F) {
			this.Fraction8F = Fraction8F;
			return this;
		}

		public Builder withFraction10F(float Fraction10F) {
			this.Fraction10F = Fraction10F;
			return this;
		}

		public Builder withFraction12F(float Fraction12F) {
			this.Fraction12F = Fraction12F;
			return this;
		}

		public Builder withFraction14F(float Fraction14F) {
			this.Fraction14F = Fraction14F;
			return this;
		}

		public Builder withFraction16F(float Fraction16F) {
			this.Fraction16F = Fraction16F;
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

		public Builder withCalcFraction1(float CalcFraction1) {
			this.CalcFraction1 = CalcFraction1;
			return this;
		}

		public Builder withCalcFraction2(float CalcFraction2) {
			this.CalcFraction2 = CalcFraction2;
			return this;
		}

		public Builder withCalcFraction3(float CalcFraction3) {
			this.CalcFraction3 = CalcFraction3;
			return this;
		}

		public Builder withCalcFinalTime(float CalcFinalTime) {
			this.CalcFinalTime = CalcFinalTime;
			return this;
		}

		public Builder withSplit1(float Split1) {
			this.Split1 = Split1;
			return this;
		}

		public Builder withSplit2(float Split2) {
			this.Split2 = Split2;
			return this;
		}

		public Builder withSplit3(float Split3) {
			this.Split3 = Split3;
			return this;
		}

		public Builder withClaimedCode(String ClaimedCode) {
			this.ClaimedCode = ClaimedCode;
			return this;
		}

		public Builder withTrainer(String Trainer) {
			this.Trainer = Trainer;
			return this;
		}

		public Builder withJockey(String Jockey) {
			this.Jockey = Jockey;
			return this;
		}

		public Builder withApprenticeWeightAllowed(int ApprenticeWeightAllowed) {
			this.ApprenticeWeightAllowed = ApprenticeWeightAllowed;
			return this;
		}

		public Builder withRaceType(RaceType RaceType) {
			this.RaceType = RaceType;
			return this;
		}

		public Builder withCompleteRaceCondition(String CompleteRaceCondition) {
			this.CompleteRaceCondition = CompleteRaceCondition;
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

		public Builder withStatebredFlag(String StatebredFlag) {
			this.StatebredFlag = StatebredFlag;
			return this;
		}

		public Builder withRestrictedQualifierFlag(String RestrictedQualifierFlag) {
			this.RestrictedQualifierFlag = RestrictedQualifierFlag;
			return this;
		}

		public Builder withFavoriteFlag(int FavoriteFlag) {
			this.FavoriteFlag = FavoriteFlag;
			return this;
		}

		public Builder withFrontBandagesFlag(int FrontBandagesFlag) {
			this.FrontBandagesFlag = FrontBandagesFlag;
			return this;
		}

		public Builder withBarShoeFlag(String BarShoeFlag) {
			this.BarShoeFlag = BarShoeFlag;
			return this;
		}

		public Builder withCompanyLineCodes(String CompanyLineCodes) {
			this.CompanyLineCodes = CompanyLineCodes;
			return this;
		}

		public Builder withLowClaimingPriceOfRace(int LowClaimingPriceOfRace) {
			this.LowClaimingPriceOfRace = LowClaimingPriceOfRace;
			return this;
		}

		public Builder withHighClaimingPriceOfRace(int HighClaimingPriceOfRace) {
			this.HighClaimingPriceOfRace = HighClaimingPriceOfRace;
			return this;
		}

		public Builder withExtendedStartComment(String ExtendedStartComment) {
			this.ExtendedStartComment = ExtendedStartComment;
			return this;
		}

		public Builder withSealedTrackFlag(String SealedTrackFlag) {
			this.SealedTrackFlag = SealedTrackFlag;
			return this;
		}

		public Builder withAllWeatherSurfaceFlag(String AllWeatherSurfaceFlag) {
			this.AllWeatherSurfaceFlag = AllWeatherSurfaceFlag;
			return this;
		}

		public Builder withOffTheTurfFlag(Boolean OffTheTurfFlag) {
			this.OffTheTurfFlag = OffTheTurfFlag;
			return this;
		}

		public Builder withTrainerChangeDate(String TrainerChangeDate) {
			this.TrainerChangeDate = TrainerChangeDate;
			return this;
		}

		public Builder withTrainerChangeStarts(int TrainerChangeStarts) {
			this.TrainerChangeStarts = TrainerChangeStarts;
			return this;
		}

		public Builder withTrainerChangeWins(int TrainerChangeWins) {
			this.TrainerChangeWins = TrainerChangeWins;
			return this;
		}

		public Builder withTrainerChangePlaces(int TrainerChangePlaces) {
			this.TrainerChangePlaces = TrainerChangePlaces;
			return this;
		}

		public Builder withTrainerChangeShows(int TrainerChangeShows) {
			this.TrainerChangeShows = TrainerChangeShows;
			return this;
		}

		public Builder withTrainerChangeROI(float TrainerChangeROI) {
			this.TrainerChangeROI = TrainerChangeROI;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withThisRaceNumber(int thisRaceNumber) {
			this.thisRaceNumber = thisRaceNumber;
			return this;
		}

		public Builder withTrackName(String trackName) {
			this.trackName = trackName;
			return this;
		}

		public Builder withIgnore(Boolean ignore) {
			this.ignore = ignore;
			return this;
		}

		public Builder withRaceStrength(float RaceStrength) {
			this.RaceStrength = RaceStrength;
			return this;
		}

		public Builder withLast5AverageSpeed(float Last5AverageSpeed) {
			this.Last5AverageSpeed = Last5AverageSpeed;
			return this;
		}

		public Builder withOver90Days(Boolean over90Days) {
			this.over90Days = over90Days;
			return this;
		}

		public Builder withOver365Days(Boolean over365Days) {
			this.over365Days = over365Days;
			return this;
		}

		public Builder withTurfFlag(Boolean TurfFlag) {
			this.TurfFlag = TurfFlag;
			return this;
		}

		public Builder withRaceShape(String RaceShape) {
			this.RaceShape = RaceShape;
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

		public Builder withAdjustedSpeedRating(int adjustedSpeedRating) {
			this.adjustedSpeedRating = adjustedSpeedRating;
			return this;
		}

		public Builder withRaceDateString(String raceDateString) {
			this.raceDateString = raceDateString;
			return this;
		}

		public Builder withE1(int e1) {
			this.e1 = e1;
			return this;
		}

		public Builder withE2(int e2) {
			this.e2 = e2;
			return this;
		}

		public Builder withKeyRace(PotentialKeyRace keyRace) {
			this.keyRace = keyRace;
			return this;
		}

		public Builder withOneTurn(Boolean oneTurn) {
			this.oneTurn = oneTurn;
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

		public Builder withComment(String comment) {
			this.comment = comment;
			return this;
		}

		public PastPerformance build() {
			return new PastPerformance(this);
		}
	}
	
	
}
