package net.derbyparty.jpp.chartparser.charts.pdf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import net.derbyparty.jpp.chartparser.charts.pdf.DistanceSurfaceTrackRecord.RaceDistance;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.HorseJockey;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.IndividualTime;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.LastRaced;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.MedicationEquipment;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.Odds;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.PastPerformanceRunningLinePreview;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.PointOfCallPosition;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.Weight;
import net.derbyparty.jpp.chartparser.charts.pdf.wagering.WagerPayoffPools.WinPlaceShowPayoffPool.WinPlaceShowPayoff;
import net.derbyparty.jpp.chartparser.exceptions.ChartParserException;
import net.derbyparty.jpp.chartparser.fractionals.FractionalPoint.Fractional;
import net.derbyparty.jpp.chartparser.fractionals.FractionalPoint.Split;
import net.derbyparty.jpp.chartparser.points_of_call.LengthsAhead;
import net.derbyparty.jpp.chartparser.points_of_call.PointOfCall;
import net.derbyparty.jpp.chartparser.points_of_call.PointsOfCall;
import net.derbyparty.jpp.chartparser.points_of_call.PointsOfCallService;
import net.derbyparty.jpp.chartparser.points_of_call.RelativePosition;
import net.derbyparty.jpp.chartparser.points_of_call.TotalLengthsBehind;
import net.derbyparty.jpp.chartparser.tracks.TrackService;
import net.derbyparty.jpp.object.PotentialKeyRace;
import net.derbyparty.jpp.object.RaceNote;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Everything to do with a participant in a {@link RaceResult}
 */
@JsonPropertyOrder({"lastRaced", "program", "horse", "jockey", "trainer", "owner", "weight",
        "medicationEquipment", "claim", "postPosition", "finishPosition", "officialPosition",
        "winner", "disqualified", "odds", "favorite", "wagering", "pointsOfCall", "fractionals",
        "splits", "aqha", "comments"})
public class Starter {

    public LastRaced lastRaced;
    public String program;
    public Horse horse;
    public Jockey jockey;
    public Weight weight;
    public MedicationEquipment medicationEquipment;
    public Integer postPosition;
    public Double odds;
    public Boolean favorite;
    public String comments;
    public List<PointOfCall> pointsOfCall;
    public Integer finishPosition;
    public Integer officialPosition;
    public Trainer trainer;
    public Owner owner;
    @JsonInclude(NON_NULL)
    public Claim claim;
    public Boolean disqualified;
    @JsonProperty("wagering")
    @JsonIgnoreProperties({"program", "horse"})
    @JsonInclude(NON_NULL)
    public WinPlaceShowPayoff winPlaceShowPayoff;
    @JsonInclude(NON_NULL)
    public Aqha aqha;
    public List<Fractional> fractionals;
    public List<Split> splits;
    public String note;
    public String raceFlag;
    public String horseFlag;
    public PotentialKeyRace lastRacedKeyRace;
    public String footnote;
    public RaceNote nextOutRaceNote;
    public int rawSpeedRating;
    public int adjustedSpeedRating;
    
    private Starter(Builder builder) {
        lastRaced = builder.lastRaced;
        program = builder.program;
        horse = (builder.horseJockey != null ? builder.horseJockey.getHorse() : null);
        jockey = (builder.horseJockey != null ? builder.horseJockey.getJockey() : null);
        weight = builder.weight;
        medicationEquipment = builder.medicationEquipment;
        postPosition = builder.postPosition;
        comments = builder.comments;

        pointsOfCall = builder.pointsOfCall;
        if (builder.pointsOfCall != null) {
            updateFinishPosition(builder.pointsOfCall);
        }

        if (builder.individualTimeMillis != null || builder.speedIndex != null) {
            aqha = new Aqha(builder.individualTimeMillis, builder.speedIndex);
        }

        odds = (builder.odds != null ? builder.odds.getValue() : null);
        favorite = (builder.odds != null ? builder.odds.isFavorite() : null);
    }

    @JsonCreator
    private Starter(LastRaced lastRaced, String program, Horse horse, Jockey jockey,
            Weight weight, MedicationEquipment medicationEquipment, Integer postPosition,
            Double odds, Boolean favorite, String comments, List<PointOfCall> pointsOfCall) {
        this.lastRaced = lastRaced;
        this.program = program;
        this.horse = horse;
        this.jockey = jockey;
        this.weight = weight;
        this.medicationEquipment = medicationEquipment;
        this.postPosition = postPosition;
        this.odds = odds;
        this.favorite = favorite;
        this.comments = comments;
        this.pointsOfCall = pointsOfCall;
    }

    public LastRaced getLastRaced() {
        return lastRaced;
    }

    public String getProgram() {
        return program;
    }

    public Horse getHorse() {
        return horse;
    }

    public Jockey getJockey() {
        return jockey;
    }

    public Weight getWeight() {
        return weight;
    }

    public MedicationEquipment getMedicationEquipment() {
        return medicationEquipment;
    }

    public Integer getPostPosition() {
        return postPosition;
    }

    public Double getOdds() {
        return odds;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public Aqha getAqha() {
        return aqha;
    }

    public void setAqha(Aqha aqha) {
        this.aqha = aqha;
    }

    public String getComments() {
        return comments;
    }

    public List<PointOfCall> getPointsOfCall() {
        return pointsOfCall;
    }

    public void setLastRaced(LastRaced lastRaced) {
		this.lastRaced = lastRaced;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public void setHorse(Horse horse) {
		this.horse = horse;
	}

	public void setJockey(Jockey jockey) {
		this.jockey = jockey;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	public void setMedicationEquipment(MedicationEquipment medicationEquipment) {
		this.medicationEquipment = medicationEquipment;
	}

	public void setPostPosition(Integer postPosition) {
		this.postPosition = postPosition;
	}

	public void setOdds(Double odds) {
		this.odds = odds;
	}

	public void setFavorite(Boolean favorite) {
		this.favorite = favorite;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setPointsOfCall(List<PointOfCall> pointsOfCall) {
		this.pointsOfCall = pointsOfCall;
	}

	public void setFinishPosition(Integer finishPosition) {
		this.finishPosition = finishPosition;
	}

	public void setDisqualified(Boolean disqualified) {
		this.disqualified = disqualified;
	}

	public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public boolean isDisqualified() {
        return (disqualified != null ? disqualified : false);
    }

    public void setDisqualified(boolean disqualified) {
        this.disqualified = disqualified;
    }

    public void updateDisqualification(Disqualification disqualification) {
        this.disqualified = (disqualification != null);
        this.officialPosition = (disqualification != null ?
                disqualification.getNewPosition() : null);
    }

    public Integer getFinishPosition() {
        return finishPosition;
    }

    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRaceFlag() {
		return raceFlag;
	}

	public void setRaceFlag(String raceFlag) {
		this.raceFlag = raceFlag;
	}

	public String getHorseFlag() {
		return horseFlag;
	}

	public void setHorseFlag(String horseFlag) {
		this.horseFlag = horseFlag;
	}

	public PotentialKeyRace getLastRacedKeyRace() {
		return lastRacedKeyRace;
	}

	public void setLastRacedKeyRace(PotentialKeyRace lastRacedKeyRace) {
		this.lastRacedKeyRace = lastRacedKeyRace;
	}

	public String getFootnote() {
		return footnote;
	}

	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}

	public RaceNote getNextOutRaceNote() {
		return nextOutRaceNote;
	}

	public void setNextOutRaceNote(RaceNote nextOutRaceNote) {
		this.nextOutRaceNote = nextOutRaceNote;
	}

	public int getRawSpeedRating() {
		return rawSpeedRating;
	}

	public void setRawSpeedRating(int rawSpeedRating) {
		this.rawSpeedRating = rawSpeedRating;
	}

	public int getAdjustedSpeedRating() {
		return adjustedSpeedRating;
	}

	public void setAdjustedSpeedRating(int adjustedSpeedRating) {
		this.adjustedSpeedRating = adjustedSpeedRating;
	}

	/**
     * Return the position at the last point of call (which should always be the finish)
     */
    public void updateFinishPosition(List<PointOfCall> pointsOfCall) {
        if (pointsOfCall != null && !pointsOfCall.isEmpty()) {
            finishPosition = pointsOfCall.get(pointsOfCall.size() - 1).getRelativePosition()
                    .getPosition();
        }

    }

    /**
     * Returns the official position marked on the chart or, if that is not present, the position
     * from the final point of call (the finish)
     */
    public Integer getOfficialPosition() {
        return (officialPosition != null ? officialPosition : finishPosition);
    }

    public void setOfficialPosition(Integer officialPosition) {
        this.officialPosition = officialPosition;
    }

    public WinPlaceShowPayoff getWinPlaceShowPayoff() {
        return winPlaceShowPayoff;
    }

    public void setWinPlaceShowPayoff(WinPlaceShowPayoff winPlaceShowPayoff) {
        this.winPlaceShowPayoff = winPlaceShowPayoff;
    }

    public List<Fractional> getFractionals() {
        return fractionals;
    }

    public void setFractionals(List<Fractional> fractionals) {
        this.fractionals = fractionals;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }

    public boolean matchesProgramOrName(String program, String horseName) {
        return (program != null && this.program.equals(program)) ||
                horse.getName().equals(horseName);
    }

    @JsonIgnore
    public Fractional getFinishFractional() {
        if (fractionals != null && !fractionals.isEmpty()) {
            return fractionals.get(fractionals.size() - 1);
        }
        return null;
    }

    /**
     * Add the total lengths behind to the {@link Starter}'s {@link RelativePosition} for the
     * applicable {@link PointOfCall}
     */
    public Starter setTotalLengthsBehindAtPointOfCall(String column,
            RelativePosition relativePosition) throws ChartParserException {
        Optional<PointOfCall> pointOfCall = getPointOfCall(column);
        if (pointOfCall.isPresent()) {
            TotalLengthsBehind totalLengthsBehind = createTotalLengthsBehind(relativePosition);
            RelativePosition pocRelPosition = pointOfCall.get().getRelativePosition();
            if (pocRelPosition != null) {
                pocRelPosition.setTotalLengthsBehind(totalLengthsBehind);
            }
        } else {
            throw new ChartParserException(String.format("Point of call not found for column: " +
                    "%s", column));
        }
        return this;
    }

    /**
     * For horses that were not the leader at the point of call, the total lengths behind is taken
     * from the {@link PastPerformanceRunningLinePreview}
     */
    private TotalLengthsBehind createTotalLengthsBehind(RelativePosition relativePosition) {
        Integer position = relativePosition.getPosition();
        if (position != null && position != 1) {
            LengthsAhead runningLinePreviewLengths =
                    relativePosition.getLengthsAhead();
            if (runningLinePreviewLengths != null) {
                String chart = runningLinePreviewLengths.getText();
                Double lengths = runningLinePreviewLengths.getLengths();
                if (chart != null && lengths != null) {
                    return new TotalLengthsBehind(chart, lengths);
                }
            }
        }
        return null;
    }

    /**
     * Get the point of call that matches the specified distance (in feet)
     */
    public Optional<PointOfCall> getPointOfCall(int feet) {
        List<PointOfCall> pointsOfCall = getPointsOfCall();
        if (pointsOfCall != null) {
            for (PointOfCall pointOfCall : pointsOfCall) {
                if (pointOfCall.getFeet() != null && pointOfCall.getFeet() == feet) {
                    return Optional.of(pointOfCall);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Get the point of call that matches the specified description in the chart
     */
    public Optional<PointOfCall> getPointOfCall(String chartPointOfCall) {
        List<PointOfCall> pointsOfCall = getPointsOfCall();
        if (pointsOfCall != null) {
            for (PointOfCall pointOfCall : pointsOfCall) {
                if (pointOfCall.getText().equals(chartPointOfCall)) {
                    return Optional.of(pointOfCall);
                }
            }
        }
        return Optional.empty();
    }

    public boolean isWinner() {
        return (getOfficialPosition() != null && getOfficialPosition() == 1);
    }

    /**
     * A race's winner(s) contain additional breeding information - update the {@link Starter}'s
     * {@link Horse} instance with this information
     */
    public Horse updateWinner(Winner winner) {
        if (horse != null && winner != null) {
            horse.setColor(winner.getHorseColor());
            horse.setSex(winner.getHorseSex());
            horse.setSire(winner.getSireName() != null ? new Horse(winner.getSireName()) : null);
            horse.setDam(winner.getDamName() != null ? new Horse(winner.getDamName()) : null);
            horse.setDamSire(winner.getDamSireName() != null ?
                    new Horse(winner.getDamSireName()) : null);
            horse.setFoalingDate(winner.getFoalingDate());
            horse.setFoalingLocation(winner.getFoalingLocation());
            horse.setBreeder(winner.getBreeder());
        }
        return horse;
    }

    /**
     * Parses the running line grid and associates the individual column data to the appropriate
     * fields for the {@link Starter} in question
     * @throws Exception 
     */
    public static Starter parseRunningLineData(
            Map<String, List<ChartCharacter>> runningLineCharactersByColumn,
            Date raceDate, Breed breed, RaceDistance raceDistance, TrackService trackService,
            PointsOfCallService pointsOfCallService) throws Exception {
        Builder builder = new Builder();

        List<List<ChartCharacter>> pointsOfCall = new ArrayList<>();

        for (String column : runningLineCharactersByColumn.keySet()) {
            List<ChartCharacter> chartCharacters = runningLineCharactersByColumn.get(column);
            switch (column) {
                case "LastRaced":
                    builder.lastRaced(LastRaced.parse(chartCharacters, raceDate, trackService));
                    break;
                case "Pgm":
                    builder.program(Chart.convertToText(chartCharacters));
                    break;
                case "HorseName(Jockey)":
                    builder.horseAndJockey(HorseJockey.parse(chartCharacters));
                    break;
                case "Wgt":
                    builder.weight(Weight.parse(chartCharacters));
                    break;
                case "PP":
                    String postPositionText = Chart.convertToText(chartCharacters);
                    if (!postPositionText.isEmpty()) {
                        if (postPositionText.contains("|") || postPositionText.contains(" ")) {
                            String[] split = postPositionText.split("\\||\\s");
                            if (split.length == 2) {
                            	System.out.println("WARNING: " + String.format("Detected PP affected by M/E in text: " +
                                                "%s; extracting the PP to be %s", postPositionText,
                                        split[1]));
                                postPositionText = split[1];
                            }
                        }
                        builder.postPosition(Integer.parseInt(postPositionText));
                    }
                    break;
                case "Odds":
                	//System.out.println(chartCharacters);
                    builder.odds(Odds.parse(chartCharacters));
                    break;
                case "Ind.Time":
                    builder.individualTimeMillis(
                            IndividualTime.parse(Chart.convertToText(chartCharacters)));
                    break;
                case "Sp.In.":
                    String speedIndexText = Chart.convertToText(chartCharacters);
                    if (!speedIndexText.isEmpty()) {
                        int speedIndex = 0;
                        try {
                            speedIndex = Integer.parseInt(speedIndexText);
                        } catch (NumberFormatException e) {

                        }
                        builder.speedIndex(speedIndex);
                    }
                    break;
                case "M/E":
                    builder.medicationAndEquipment(MedicationEquipment.parse(chartCharacters));
                    break;
                case "Comments":
                	//System.out.println(chartCharacters);
                    builder.comments(Chart.convertToText(chartCharacters));
                    break;
                default:
                    pointsOfCall.add(chartCharacters);
                    break;
            }
        }

        PointsOfCall pointsOfCallForDistance =
                pointsOfCallService.getPointsOfCallForDistance(breed, raceDistance);

        // handle Mixed/QH races with inconsistent points of call (this certainly could be improved)
        if (pointsOfCall.size() > 0 && pointsOfCallForDistance.getCalls() != null) {
            if ((breed.equals(Breed.MIXED) || breed.equals(Breed.QUARTER_HORSE)) &&
                    (pointsOfCall.size() < pointsOfCallForDistance.getCalls().size())) {
                for (PointOfCall pointOfCall : pointsOfCallForDistance.getCalls()) {
                    if (pointOfCall.getText().equals("Str2")) {
                    	System.out.println("WARNING: " + String.format("Removing the extraneous \"Str2\"" +
                                        " point of call from the race of distance \"" +
                                        "%s\" for starter \"%s\"", raceDistance.getText(),
                                builder.horseJockey.getHorse().getName()));
                        pointsOfCallForDistance.getCalls().remove(pointOfCall);
                        break;
                    }
                }

                // if still not right, give up
                if (pointsOfCall.size() != pointsOfCallForDistance.getCalls().size()) {
                    throw new InvalidPointsOfCallException(String.format("Unable to parse values " +
                                    "for all of the following points of call: %s",
                            pointsOfCallForDistance));
                }
            }
        }

        // set the relative position (lengths ahead/behind) for each point of call
        for (int i = 0; i < pointsOfCall.size(); i++) {
            PointOfCall pointOfCall = pointsOfCallForDistance.getCalls().get(i);

            List<ChartCharacter> characters = pointsOfCall.get(i);
            RelativePosition relativePosition = PointOfCallPosition.parse(characters);

            pointOfCall.setRelativePosition(relativePosition);
        }

        // set finish distance for QH/Mixed races (as points of calls cover a variety of
        // distance up to or between a certain number of yards)
        Optional<PointOfCall> finishPointOfCall = pointsOfCallForDistance.getFinishPointOfCall();
        if (finishPointOfCall.isPresent()) {
            PointOfCall pointOfCall = finishPointOfCall.get();
            if (!pointOfCall.hasKnownDistance()) {
                pointOfCall.setFeet(raceDistance.getValue());
            }
        }

        builder.pointsOfCall(pointsOfCallForDistance.getCalls());

        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Starter starter = (Starter) o;

        if (lastRaced != null ? !lastRaced.equals(starter.lastRaced) : starter.lastRaced != null)
            return false;
        if (program != null ? !program.equals(starter.program) : starter.program != null)
            return false;
        if (horse != null ? !horse.equals(starter.horse) : starter.horse != null) return false;
        if (jockey != null ? !jockey.equals(starter.jockey) : starter.jockey != null) return false;
        if (weight != null ? !weight.equals(starter.weight) : starter.weight != null) return false;
        if (medicationEquipment != null ? !medicationEquipment.equals(starter
                .medicationEquipment) : starter.medicationEquipment != null)
            return false;
        if (postPosition != null ? !postPosition.equals(starter.postPosition) : starter
                .postPosition != null)
            return false;
        if (odds != null ? !odds.equals(starter.odds) : starter.odds != null) return false;
        if (favorite != null ? !favorite.equals(starter.favorite) : starter.favorite != null)
            return false;
        if (comments != null ? !comments.equals(starter.comments) : starter.comments != null)
            return false;
        if (pointsOfCall != null ? !pointsOfCall.equals(starter.pointsOfCall) : starter
                .pointsOfCall != null)
            return false;
        if (officialPosition != null ? !officialPosition.equals(starter.officialPosition) :
                starter.officialPosition != null)
            return false;
        if (trainer != null ? !trainer.equals(starter.trainer) : starter.trainer != null)
            return false;
        if (owner != null ? !owner.equals(starter.owner) : starter.owner != null) return false;
        if (claim != null ? !claim.equals(starter.claim) : starter.claim != null) return false;
        if (disqualified != null ? !disqualified.equals(starter.disqualified) : starter.disqualified
                != null)
            return false;
        if (winPlaceShowPayoff != null ? !winPlaceShowPayoff.equals(starter.winPlaceShowPayoff) :
                starter.winPlaceShowPayoff != null)
            return false;
        if (aqha != null ? !aqha.equals(starter.aqha) : starter.aqha != null) return false;
        if (fractionals != null ? !fractionals.equals(starter.fractionals) : starter.fractionals
                != null)
            return false;
        return splits != null ? splits.equals(starter.splits) : starter.splits == null;
    }

    @Override
    public int hashCode() {
        int result = lastRaced != null ? lastRaced.hashCode() : 0;
        result = 31 * result + (program != null ? program.hashCode() : 0);
        result = 31 * result + (horse != null ? horse.hashCode() : 0);
        result = 31 * result + (jockey != null ? jockey.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (medicationEquipment != null ? medicationEquipment.hashCode() : 0);
        result = 31 * result + (postPosition != null ? postPosition.hashCode() : 0);
        result = 31 * result + (odds != null ? odds.hashCode() : 0);
        result = 31 * result + (favorite != null ? favorite.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (pointsOfCall != null ? pointsOfCall.hashCode() : 0);
        result = 31 * result + (officialPosition != null ? officialPosition.hashCode() : 0);
        result = 31 * result + (trainer != null ? trainer.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (claim != null ? claim.hashCode() : 0);
        result = 31 * result + (disqualified != null ? disqualified.hashCode() : 0);
        result = 31 * result + (winPlaceShowPayoff != null ? winPlaceShowPayoff.hashCode() : 0);
        result = 31 * result + (aqha != null ? aqha.hashCode() : 0);
        result = 31 * result + (fractionals != null ? fractionals.hashCode() : 0);
        result = 31 * result + (splits != null ? splits.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Starter{" +
                "lastRaced=" + lastRaced +
                ", program='" + program + '\'' +
                ", horse=" + horse +
                ", jockey=" + jockey +
                ", weight=" + weight +
                ", medicationEquipment=" + medicationEquipment +
                ", postPosition=" + postPosition +
                ", odds=" + odds +
                ", favorite=" + favorite +
                ", comments='" + comments + '\'' +
                ", pointsOfCall=" + pointsOfCall +
                ", officialPosition=" + officialPosition +
                ", trainer=" + trainer +
                ", owner=" + owner +
                ", claim=" + claim +
                ", disqualified=" + disqualified +
                ", winPlaceShowPayoff=" + winPlaceShowPayoff +
                ", aqha=" + aqha +
                ", fractionals=" + fractionals +
                ", splits=" + splits +
                '}';
    }

    public Starter() {
    	super();
		
		// TODO Auto-generated constructor stub
	}

	/**
     * Builder pattern used to create {@link Starter} instances
     */
    public static class Builder {
        private LastRaced lastRaced;
        private String program;
        private HorseJockey horseJockey;
        private Weight weight;
        private MedicationEquipment medicationEquipment;
        private Integer postPosition;
        private List<PointOfCall> pointsOfCall;
        private Odds odds;
        private Integer speedIndex;
        private Long individualTimeMillis;
        private String comments;

        public Builder lastRaced(final LastRaced lastRaced) {
            this.lastRaced = lastRaced;
            return this;
        }

        public Builder program(final String program) {
            this.program = program;
            return this;
        }

        public Builder horseAndJockey(final HorseJockey horseJockey) {
            this.horseJockey = horseJockey;
            return this;
        }

        public Builder weight(final Weight weight) {
            this.weight = weight;
            return this;
        }

        public Builder medicationAndEquipment(final MedicationEquipment medicationEquipment) {
            this.medicationEquipment = medicationEquipment;
            return this;
        }

        public Builder postPosition(final Integer postPosition) {
            this.postPosition = postPosition;
            return this;
        }

        public Builder pointsOfCall(final List<PointOfCall> pointsOfCall) {
            this.pointsOfCall = pointsOfCall;
            return this;
        }

        public Builder odds(final Odds odds) {
            this.odds = odds;
            return this;
        }

        public Builder speedIndex(final Integer speedIndex) {
            this.speedIndex = speedIndex;
            return this;
        }

        public Builder individualTimeMillis(final Long individualTimeMillis) {
            this.individualTimeMillis = individualTimeMillis;
            return this;
        }

        public Builder comments(final String comments) {
            this.comments = comments;
            return this;
        }

        public Starter build() {
            return new Starter(this);
        }
        
    }

    public static class InvalidPointsOfCallException extends ChartParserException {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidPointsOfCallException(String message) {
            super(message);
        }
    }

    public static class Claim {
    	public int price;
    	public boolean claimed;
        @JsonInclude(NON_NULL)
        public String newTrainerName;
        @JsonInclude(NON_NULL)
        public String newOwnerName;

        public Claim(ClaimingPrice claimingPrice, ClaimedHorse claimedHorse) {
            if (claimingPrice != null) {
                price = claimingPrice.getClaimingPrice();
            } else {
                price = 0;
            }

            if (claimedHorse != null) {
                claimed = true;
                newTrainerName = claimedHorse.getNewTrainerName();
                newOwnerName = claimedHorse.getNewOwnerName();
            } else {
                claimed = false;
                newTrainerName = null;
                newOwnerName = null;
            }
        }

        public int getPrice() {
            return price;
        }

        public boolean isClaimed() {
            return claimed;
        }

        public String getNewTrainerName() {
            return newTrainerName;
        }

        public String getNewOwnerName() {
            return newOwnerName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Claim claim = (Claim) o;

            if (price != claim.price) return false;
            if (claimed != claim.claimed) return false;
            if (newTrainerName != null ? !newTrainerName.equals(claim.newTrainerName) : claim
                    .newTrainerName != null)
                return false;
            return newOwnerName != null ? newOwnerName.equals(claim.newOwnerName) : claim
                    .newOwnerName == null;
        }

        @Override
        public int hashCode() {
            int result = price;
            result = 31 * result + (claimed ? 1 : 0);
            result = 31 * result + (newTrainerName != null ? newTrainerName.hashCode() : 0);
            result = 31 * result + (newOwnerName != null ? newOwnerName.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Claim{" +
                    "price=" + price +
                    ", claimed=" + claimed +
                    ", newTrainerName='" + newTrainerName + '\'' +
                    ", newOwnerName='" + newOwnerName + '\'' +
                    '}';
        }

		public Claim() {
			super();
			// TODO Auto-generated constructor stub
		}
    }

}
