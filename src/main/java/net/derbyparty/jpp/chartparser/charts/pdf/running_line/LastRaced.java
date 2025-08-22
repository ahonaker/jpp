package net.derbyparty.jpp.chartparser.charts.pdf.running_line;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import net.derbyparty.jpp.chartparser.charts.pdf.Chart;
import net.derbyparty.jpp.chartparser.charts.pdf.ChartCharacter;
import net.derbyparty.jpp.chartparser.charts.pdf.Starter;
import net.derbyparty.jpp.chartparser.exceptions.ChartParserException;
import net.derbyparty.jpp.chartparser.tracks.Track;
import net.derbyparty.jpp.chartparser.tracks.TrackService;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static net.derbyparty.jpp.chartparser.charts.pdf.running_line.LastRaced.LastRacePerformance.parseFromLastRaced;

/**
 * Stores the {@link Date} instance of the last race date, the number of days between this race
 * date and then, and the {@link Track}, race number, and finishing position of the {@link
 * Starter}'s last performance (if applicable and they exist)
 */
public class LastRaced {

	public Date raceDate;
	public Integer daysSince;
    @JsonUnwrapped
    public LastRacePerformance lastRacePerformance;

    public LastRaced(Date raceDate, Integer daysSince, LastRacePerformance
            lastRacePerformance) {
        this.raceDate = raceDate;
        this.daysSince = daysSince;
        this.lastRacePerformance = lastRacePerformance;
    }

    private static LastRaced noLastRace() {
        return null;
    }

    public boolean hasLastRace() {
        return (raceDate != null);
    }

    /**
     * Attempt to parse a last race date, track, race number, and finishing position
     *
     * @param chartCharacters the PDF characters that may contain the last race details
     * @param raceDate        the {@link Date} of the current race result
     * @param trackService    the {@link TrackService} to look up a track by code
     * @return LastRaced instance containing, if they exist, the last race date, track, race number,
     * and finishing position
     * @throws Exception 
     */
    public static LastRaced parse(final List<ChartCharacter> chartCharacters,
            final Date raceDate, final TrackService trackService) throws
            Exception {
        if (chartCharacters.size() == 3) {
            return noLastRace();
        }

        Date lastRaceDate = parseLastRaceDate(chartCharacters);
        int daysSince = Math.toIntExact(ChronoUnit.DAYS.between(
        		lastRaceDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        		, raceDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

        LastRacePerformance lastRacePerformance = parseFromLastRaced(chartCharacters, trackService);

        return new LastRaced(lastRaceDate, daysSince, lastRacePerformance);
    }

    private static Date parseLastRaceDate(List<ChartCharacter> chartCharacters) throws Exception {
        ChartCharacter lastChartCharacter = null;
        List<ChartCharacter> lastRaceDateCharacters = new ArrayList<>();
        for (ChartCharacter columnCharacter : chartCharacters) {
            // handle when lastChartCharacter race number is unknown
            if (columnCharacter.getFontSize() == 5 ||
                    spaceDetected(lastChartCharacter, columnCharacter)) {
                break;
            }
            lastRaceDateCharacters.add(columnCharacter);
            lastChartCharacter = columnCharacter;
        }
        String lastRaceDateText = Chart.convertToText(lastRaceDateCharacters);
        // so that 97 becomes 1997 and 03 becomes 2003
        SimpleDateFormat formatter = new SimpleDateFormat("dMMMyy");
//        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
//                .appendPattern("dMMM")
//                .appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2, Date.now().minusYears(80))
//                .toFormatter();
        Date lastRaceDate = formatter.parse(lastRaceDateText); 
        //Date.parse(lastRaceDateText, dateTimeFormatter);

        chartCharacters.removeAll(lastRaceDateCharacters);

        return lastRaceDate;
    }

    /**
     * Returns true when the race number of the last performance is not present
     */
    private static boolean spaceDetected(ChartCharacter lastChartCharacter, ChartCharacter
            columnCharacter) {
        if (lastChartCharacter != null) {
            return (!Chart.addWhitespaceIfRequired(columnCharacter.getxDirAdj(),
                    lastChartCharacter.getxDirAdj(), lastChartCharacter.getWidthDirAdj())
                    .equals(""));
        }
        return false;
    }

    public Date getRaceDate() {
        return raceDate;
    }

    public Integer getDaysSince() {
        return daysSince;
    }

    public LastRacePerformance getLastRacePerformance() {
        return lastRacePerformance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastRaced lastRaced = (LastRaced) o;

        if (raceDate != null ? !raceDate.equals(lastRaced.raceDate) : lastRaced
                .raceDate != null)
            return false;
        if (daysSince != null ? !daysSince.equals(lastRaced.daysSince) : lastRaced.daysSince !=
                null)
            return false;
        return lastRacePerformance != null ? lastRacePerformance.equals(lastRaced
                .lastRacePerformance) : lastRaced.lastRacePerformance == null;
    }

    @Override
    public int hashCode() {
        int result = raceDate != null ? raceDate.hashCode() : 0;
        result = 31 * result + (daysSince != null ? daysSince.hashCode() : 0);
        result = 31 * result + (lastRacePerformance != null ? lastRacePerformance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LastRaced{" +
                "raceDate=" + raceDate +
                ", daysSince=" + daysSince +
                ", lastRacePerformance=" + lastRacePerformance +
                '}';
    }

    public LastRaced() {
    	super();
		
		// TODO Auto-generated constructor stub
	}

	/**
     * The {@link Track}, race number, and finishing position of the {@link Starter}'s last
     * performance (if applicable and they exist)
     */
    public static class LastRacePerformance {
    	public Track track;
    	public Integer raceNumber;
    	public Integer officialPosition;

        public LastRacePerformance(Integer raceNumber, Track track, Integer officialPosition) {
            this.raceNumber = raceNumber;
            this.track = track;
            this.officialPosition = officialPosition;
        }

        static LastRacePerformance parseFromLastRaced(List<ChartCharacter> chartCharacters,
                TrackService trackService) {
            boolean trackCodeFound = false;
            StringBuilder lastRaceNumberBuilder = new StringBuilder();
            StringBuilder trackCodeBuilder = new StringBuilder();
            StringBuilder finishPositionBuilder = new StringBuilder();
            for (ChartCharacter pdfCharacter : chartCharacters) {
            	//System.out.println(pdfCharacter.toString());
                if (pdfCharacter.getxScale() == 5) {
                    if (trackCodeFound) {
                        finishPositionBuilder.append(pdfCharacter.getUnicode());
                    } else {
                        lastRaceNumberBuilder.append(pdfCharacter.getUnicode());
                    }
                } else {
                    trackCodeBuilder.append(pdfCharacter.getUnicode());
                    trackCodeFound = true;
                }
            }

            String trackCode = trackCodeBuilder.toString();
            Optional<Track> track = trackService.getTrack(trackCode);
            if (track.isPresent()) {
                String lastRaceNumber = lastRaceNumberBuilder.toString();
                String finishPosition = finishPositionBuilder.toString();
                return new LastRacePerformance(
                        (!lastRaceNumber.isEmpty() ? parseInt(lastRaceNumber) : null),
                        track.get(),
                        (!finishPosition.isEmpty() ? parseInt(finishPosition) : null));
            } else {
                System.out.println("Unknown Track " + trackCode);
                return null;
            }
        }

        public Track getTrack() {
            return track;
        }

        public Integer getRaceNumber() {
            return raceNumber;
        }

        public Integer getOfficialPosition() {
            return officialPosition;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LastRacePerformance that = (LastRacePerformance) o;

            if (track != null ? !track.equals(that.track) : that.track != null) return false;
            if (raceNumber != null ? !raceNumber.equals(that.raceNumber) : that
                    .raceNumber != null)
                return false;
            return officialPosition != null ? officialPosition.equals(that.officialPosition) : that
                    .officialPosition == null;
        }

        @Override
        public int hashCode() {
            int result = track != null ? track.hashCode() : 0;
            result = 31 * result + (raceNumber != null ? raceNumber.hashCode() : 0);
            result = 31 * result + (officialPosition != null ? officialPosition.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "LastRacePerformance{" +
                    "track=" + track +
                    ", raceNumber=" + raceNumber +
                    ", officialPosition=" + officialPosition +
                    '}';
        }

		public LastRacePerformance() {
			super();
			
			// TODO Auto-generated constructor stub
		}
        
    }

    public static class UnknownTrackException extends ChartParserException {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public UnknownTrackException(String trackCode) {
            super(String.format("Unable to find Track for code %s", trackCode));
        }
    }
}
