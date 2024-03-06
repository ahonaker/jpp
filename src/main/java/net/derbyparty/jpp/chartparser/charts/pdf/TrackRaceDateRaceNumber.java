package net.derbyparty.jpp.chartparser.charts.pdf;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.derbyparty.jpp.chartparser.exceptions.ChartParserException;

/**
 * Parses and stores the track name, the race date (as a {@link Date} instance) and the race
 * number
 */
public class TrackRaceDateRaceNumber {
    static final Pattern TRACK_DATE_NUMBER_PATTERN =
            Pattern.compile("([A-Z0-9\\s&]+)\\s-\\s(.+)+\\s-\\sRace\\s(\\d+)");

    private static final SimpleDateFormat MONTH_DAY_YEAR_FORMATTER =
    		new SimpleDateFormat("MMMM d, yyyy");
            //DateTimeFormatter.ofPattern("MMMM d, yyyy");

    public String trackName;
    public Date raceDate;
    public int raceNumber;

    protected TrackRaceDateRaceNumber(final String trackName, final Date raceDate,
            final int raceNumber) {
        this.trackName = trackName;
        this.raceDate = raceDate;
        this.raceNumber = raceNumber;
    }

    public static TrackRaceDateRaceNumber parse(final List<List<ChartCharacter>> lines)
            throws Exception {
        if (lines == null || lines.isEmpty()) {
            throw new NoLinesToParse();
        }

        for (List<ChartCharacter> line : lines) {
            String text = Chart.convertToText(line).replaceAll("[*/\\[?<]","");
            Optional<TrackRaceDateRaceNumber> trackRaceDateRaceNumber =
                    buildTrackRaceDateRaceNumber(text);
            if (trackRaceDateRaceNumber.isPresent()) {
                return trackRaceDateRaceNumber.get();
            }
        }

        throw new InvalidRaceException("Unable to detect a valid race track, date and number");
    }

    static Optional<TrackRaceDateRaceNumber> buildTrackRaceDateRaceNumber(String text) throws Exception {
    	//System.out.println(text);
        Matcher matcher = TRACK_DATE_NUMBER_PATTERN.matcher(text);
        if (matcher.find()) {
            String trackName = matcher.group(1);
            Date raceDate = parseRaceDate(matcher.group(2));
            int raceNumber = Integer.parseInt(matcher.group(3));
            return Optional.of(new TrackRaceDateRaceNumber(trackName, raceDate, raceNumber));
        }
        return Optional.empty();
    }

    static Date parseRaceDate(String raceDateText) throws Exception {
        return MONTH_DAY_YEAR_FORMATTER.parse(raceDateText);
    }

    @Override
    public String toString() {
        return "TrackRaceDateRaceNumber{" +
                "trackName='" + trackName + '\'' +
                ", raceDate=" + raceDate +
                ", raceNumber=" + raceNumber +
                '}';
    }

    public String getTrackName() {
        return trackName;
    }

    public Date getRaceDate() {
        return raceDate;
    }

    public int getRaceNumber() {
        return raceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackRaceDateRaceNumber that = (TrackRaceDateRaceNumber) o;

        if (raceNumber != that.raceNumber) return false;
        if (trackName != null ? !trackName.equals(that.trackName) : that.trackName != null)
            return false;
        return raceDate != null ? raceDate.equals(that.raceDate) : that.raceDate == null;

    }

    @Override
    public int hashCode() {
        int result = trackName != null ? trackName.hashCode() : 0;
        result = 31 * result + (raceDate != null ? raceDate.hashCode() : 0);
        result = 31 * result + raceNumber;
        return result;
    }

    public TrackRaceDateRaceNumber() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static class InvalidRaceException extends ChartParserException {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidRaceException(String message) {
            super(message);
        }
    }

    public static class NoLinesToParse extends ChartParserException {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public NoLinesToParse() {
            super("No content available to be parsed.");
        }
    }

}
