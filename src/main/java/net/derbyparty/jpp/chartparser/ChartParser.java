package net.derbyparty.jpp.chartparser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import net.derbyparty.jpp.chartparser.charts.pdf.*;
import net.derbyparty.jpp.chartparser.charts.pdf.DistanceSurfaceTrackRecord.RaceDistance;
import net.derbyparty.jpp.chartparser.charts.pdf.Starter.Claim;
import net.derbyparty.jpp.chartparser.charts.pdf.TrackRaceDateRaceNumber.InvalidRaceException;
import net.derbyparty.jpp.chartparser.charts.pdf.Winner.NoWinnersDeclaredException;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.PastPerformanceRunningLinePreview;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.RunningLine;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.RunningLineColumnIndex;
import net.derbyparty.jpp.chartparser.charts.pdf.running_line.RunningLineHeader;
import net.derbyparty.jpp.chartparser.charts.pdf.wagering.WagerPayoffPools;
import net.derbyparty.jpp.chartparser.charts.text.ChartStripper;
import net.derbyparty.jpp.chartparser.exceptions.ChartParserException;
import net.derbyparty.jpp.chartparser.formats.SimpleLocalDateDeserializer;
import net.derbyparty.jpp.chartparser.formats.SimpleLocalDateSerializer;
import net.derbyparty.jpp.chartparser.fractionals.FractionalPoint;
import net.derbyparty.jpp.chartparser.fractionals.FractionalPointRepository;
import net.derbyparty.jpp.chartparser.fractionals.FractionalService;
import net.derbyparty.jpp.chartparser.points_of_call.PointsOfCallRepository;
import net.derbyparty.jpp.chartparser.points_of_call.PointsOfCallService;
import net.derbyparty.jpp.chartparser.tracks.Track;
import net.derbyparty.jpp.chartparser.tracks.TrackRepository;
import net.derbyparty.jpp.chartparser.tracks.TrackService;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import static net.derbyparty.jpp.chartparser.charts.pdf.Chart.convertToText;
import static net.derbyparty.jpp.chartparser.charts.pdf.TrackRaceDateRaceNumber.NoLinesToParse;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a PDF race chart {@link File}, converting each race to a {@link RaceResult}
 */
public class ChartParser {
    public static final Pattern COPYRIGHT_PATTERN =
            Pattern.compile("^Copyright (\\d+) Equibase Company LLC. All Rights Reserved\\.$");

    private static ObjectMapper mapper;
    private static CsvMapper csvMapper;

    protected final TrackService trackService;
    protected final FractionalService fractionalService;
    protected final PointsOfCallService pointsOfCallService;

    public ChartParser(TrackService trackService, FractionalService fractionalService,
            PointsOfCallService pointsOfCallService) {
        this.trackService = trackService;
        this.fractionalService = fractionalService;
        this.pointsOfCallService = pointsOfCallService;
    }

    public static ChartParser create() {
        ObjectMapper jsonMapper = getObjectMapper();

        CsvMapper csvMapper = getCsvMapper();

        TrackService trackService = new TrackService(new TrackRepository(csvMapper));
        FractionalService fractionalService = new FractionalService(
                new FractionalPointRepository(jsonMapper));
        PointsOfCallService pointsOfCallService = new PointsOfCallService(
                new PointsOfCallRepository(jsonMapper));

        return new ChartParser(trackService, fractionalService, pointsOfCallService);
    }

    public static ObjectMapper getObjectMapper() {
        if (mapper != null) {
            return mapper;
        }

        SimpleModule simpleLocalDateModule = new SimpleModule();
        simpleLocalDateModule.addSerializer(LocalDate.class, new SimpleLocalDateSerializer());
        simpleLocalDateModule.addDeserializer(LocalDate.class, new SimpleLocalDateDeserializer());
        mapper = new ObjectMapper()
                // adds JDK 8 Parameter Name access for cleaner JSON-to-Object mapping
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
                .registerModule(simpleLocalDateModule);
        return mapper;
    }

    public static CsvMapper getCsvMapper() {
        if (csvMapper != null) {
            return csvMapper;
        }

        SimpleModule simpleLocalDateModule = new SimpleModule();
        simpleLocalDateModule.addSerializer(LocalDate.class, new SimpleLocalDateSerializer());
        simpleLocalDateModule.addDeserializer(LocalDate.class, new SimpleLocalDateDeserializer());

        csvMapper = (CsvMapper) new CsvMapper()// adds JDK 8 Parameter Name access for cleaner JSON-to-Object mapping
                .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
                .registerModule(simpleLocalDateModule);
        return csvMapper;
    }

    public List<RaceResult> parse(File pdfChartFile) {
        List<RaceResult> raceResults = new ArrayList<>();

        List<String> chartCsvs;
        try {
            chartCsvs = convertToCsv(pdfChartFile);
        } catch (ChartParserException e) {
            e.printStackTrace();
            return raceResults;
        }

        for (int index = 0; index < chartCsvs.size(); index++) {
            String chartCsv = chartCsvs.get(index);
            List<ChartCharacter> chartCharacters;
            try {
                chartCharacters = convertToChartCharacters(chartCsv);
            } catch (ChartParserException e) {
            	e.printStackTrace();
                continue;
            }
            List<List<ChartCharacter>> lines = separateIntoLines(chartCharacters);

            RaceResult.Builder raceResultBuilder = new RaceResult.Builder();

            try {
                TrackRaceDateRaceNumber trackRaceDateRaceNumber =
                        TrackRaceDateRaceNumber.parse(lines);

                Optional<Track> track = trackService.getTrackWithName(
                        trackRaceDateRaceNumber.getTrackName());
                if (!track.isPresent()) {
                    throw new ChartParserException(String.format("Unable to find Track with name:" +
                            " %s", trackRaceDateRaceNumber.getTrackName()));
                }

                // Track
                // Race Date
                // Race Number
                raceResultBuilder.track(track.get())
                        .raceDate(trackRaceDateRaceNumber.getRaceDate())
                        .raceNumber(trackRaceDateRaceNumber.getRaceNumber());

                // Check for Cancellation
                Cancellation cancellation = Cancellation.parse(lines);
                raceResultBuilder.cancellation(cancellation);
                if (cancellation.isCancelled()) {
                    RaceResult raceResult = raceResultBuilder.build();
                    raceResults.add(raceResult);
                    continue;
                }

                // Race Type
                // Race Name
                // Race Grade, Stakes Indicator, Black Type categorization
                // Breed
                RaceTypeNameBlackTypeBreed raceTypeNameBlackTypeBreed =
                        RaceTypeNameBlackTypeBreed.parse(lines);
                raceResultBuilder.raceTypeAndRaceNameAndBlackTypeAndBreed(
                        raceTypeNameBlackTypeBreed);

                // Race Conditions
                RaceConditions raceConditions =
                        RaceConditions.parse(lines);
                raceResultBuilder.raceConditionsAndClaimingPricesRange(
                        raceConditions);

                // Race Distance
                // Surface
                // Track Record
                DistanceSurfaceTrackRecord distanceSurfaceTrackRecord =
                        DistanceSurfaceTrackRecord.parse(lines);
                raceResultBuilder.distanceAndSurfaceAndTrackRecord(distanceSurfaceTrackRecord);

                // Purse
                Purse purse = Purse.parse(lines);
                raceResultBuilder.purse(purse);

                // Wind Speed
                // Wind Direction
                Optional<WindSpeedDirection> windSpeedDirection =
                        WindSpeedDirection.parse(lines);
                if (windSpeedDirection.isPresent()) {
                    raceResultBuilder.windSpeedAndDirection(windSpeedDirection.get());
                }

                // Weather Description
                // Track Condition
                Optional<WeatherTrackCondition> weatherTrackCondition =
                        WeatherTrackCondition.parse(lines);
                if (weatherTrackCondition.isPresent()) {
                    raceResultBuilder.weatherAndTrackCondition(weatherTrackCondition.get());
                }

                // Post Time
                // Start Comments
                // Timer Type
                Optional<PostTimeStartCommentsTimer> postTimeStartCommentsTimer =
                        PostTimeStartCommentsTimer.parse(lines);
                if (postTimeStartCommentsTimer.isPresent()) {
                    raceResultBuilder.postTimeAndStartCommentsAndTimer(
                            postTimeStartCommentsTimer.get());
                }

                List<List<ChartCharacter>> runningLines = getRunningLines(lines);
                List<ChartCharacter> headerCharacters = runningLines.get(0);
                TreeSet<RunningLineColumnIndex> runningLineColumnIndices =
                        RunningLineHeader.createIndexOfRunningLineColumns(headerCharacters);

                // remove running line header
                runningLines = runningLines.subList(1, runningLines.size());

                runningLines = SplitTimes.removeSplitTimesIfPresent(runningLines);

                RaceDistance raceDistance = distanceSurfaceTrackRecord.getRaceDistance();

                // Race Fractions
                ArrayList<String> fractions = FractionalTimes.parse(runningLines);
                List<FractionalPoint.Fractional> fractionalPointsForDistance =
                        fractionalService.getFractionalPointsForDistance(fractions,
                                raceDistance.getValue(), raceTypeNameBlackTypeBreed.getBreed());
                raceResultBuilder.fractionals(fractionalPointsForDistance);

                // Run-Up
                raceResultBuilder.runUp(RunUp.parse(runningLines));

                List<Starter> starters = new ArrayList<>();
                for (List<ChartCharacter> runningLine : runningLines) {
                    Map<String, List<ChartCharacter>> runningLineCharactersByColumn =
                            RunningLine.groupRunningLineCharactersByColumn(
                                    runningLineColumnIndices, runningLine);

                    // Running Line for each Starter
                    Starter starter = Starter.parseRunningLineData(
                            runningLineCharactersByColumn, trackRaceDateRaceNumber.getRaceDate(),
                            raceTypeNameBlackTypeBreed.getBreed(), raceDistance,
                            trackService, pointsOfCallService);

                    starters.add(starter);
                }

                // Winner(s)
                List<Winner> winners = new ArrayList<>();
                try {
                    winners = Winner.parse(lines);
                } catch (NoWinnersDeclaredException e) {
                	e.printStackTrace();
                }

                for (Winner winner : winners) {
                    for (Starter starter : starters) {
                        if (winner.getHorseName().equals(starter.getHorse().getName())) {
                            starter.updateWinner(winner);
                        }
                    }
                }

                // used to combine claim-related information
                Map<Starter, ClaimedHorse> starterClaimedHorseMap = new LinkedHashMap<>();

                // the horses that were claimed
                List<ClaimedHorse> claimedHorses = ClaimedHorse.parse(lines);
                if (!claimedHorses.isEmpty()) {
                    for (ClaimedHorse claimedHorse : claimedHorses) {
                        for (Starter starter : starters) {
                            if (claimedHorse.getHorse().getName().equals(starter.getHorse().getName())) {
                                // save for later
                                starterClaimedHorseMap.put(starter, claimedHorse);
                                break;
                            }
                        }
                    }
                }

                // the registered claiming prices for each starter (if applicable)
                List<ClaimingPrice> claimingPrices = ClaimingPrice.parse(lines);
                if (!claimingPrices.isEmpty()) {
                    for (ClaimingPrice claimingPrice : claimingPrices) {
                        for (Starter starter : starters) {
                            if (matchesStarter(claimingPrice, starter)) {
                                // combine the claim-related information
                                ClaimedHorse claimedHorse = null;
                                if (starterClaimedHorseMap.containsKey(starter)) {
                                    claimedHorse = starterClaimedHorseMap.get(starter);
                                }
                                Claim claim = new Claim(claimingPrice, claimedHorse);
                                starter.setClaim(claim);
                                break;
                            }
                        }
                    }
                }

                // the trainer of each starter
                List<Trainer> trainers = Trainer.parse(lines);
                if (!trainers.isEmpty()) {
                    for (int i = 0; i < trainers.size(); i++) {
                        Trainer trainer = trainers.get(i);
                        if (trainer.getProgram() != null) {
                            for (Starter starter : starters) {
                                if (trainer.getProgram().equals(
                                        starter.getProgram())) {
                                    starter.setTrainer(trainer);
                                    break;
                                }
                            }
                        } else {
                            // no program number, so assign based on index position
                            Starter starter = starters.get(i);
                            starter.setTrainer(trainer);
                            break;
                        }
                    }
                }

                // the owner of each starter
                List<Owner> owners = Owner.parse(lines);
                if (!owners.isEmpty()) {
                    for (int i = 0; i < owners.size(); i++) {
                        Owner owner = owners.get(i);
                        if (owner.getProgram() != null) {
                            for (Starter starter : starters) {
                                if (owner.getProgram().equals(starter.getProgram())) {
                                    starter.setOwner(owner);
                                    break;
                                }
                            }
                        } else {
                            // no program number, so assign based on index position
                            Starter starter = starters.get(i);
                            starter.setOwner(owner);
                            break;
                        }
                    }
                }

                // horses scratched from the race
                List<Scratch> scratches = Scratch.parse(lines);
                raceResultBuilder.scratches(scratches);

                // whether the race resulted in a dead heat
                boolean isDeadHeat = DeadHeat.parse(lines);
                raceResultBuilder.deadHeat(isDeadHeat);

                // update result if affected by disqualifications
                List<Disqualification> disqualifications = Disqualification.parse(lines);
                for (Disqualification disqualification : disqualifications) {
                    for (Starter starter : starters) {
                        if (matchesStarter(disqualification, starter)) {
                            starter.updateDisqualification(disqualification);
                            continue;
                        }

                        if (starter.getOfficialPosition() == null) {
                            starter.setOfficialPosition(starter.getFinishPosition());
                        }

                        // adjust official positions of starters affected by disqualifications
                        if (officialPositionAffectedByDisqualification(disqualification, starter)) {
                            starter.setOfficialPosition(starter.getOfficialPosition() - 1);
                        }
                    }
                }

                // parse the wagering pools and payoffs (WPS and exotics)
                WagerPayoffPools wagerPayoffPools = WagerPayoffPools.parse(lines);
                raceResultBuilder.wagerPoolsAndPayoffs(wagerPayoffPools);
                

                // Footnotes
                String footnotes = Footnotes.parse(lines);
                raceResultBuilder.footnotes(footnotes);
                
                
                for (Starter starter : starters) {
                	footnotes = footnotes.replace(starter.getHorse().getName().toUpperCase(), "~~"+starter.getProgram()+"~~");
                }
                
                String[] starterFootnotes = footnotes.split("\\.\\s");
                
                for (Starter starter : starters) {
                	for (String footnote :starterFootnotes) {
                		if (footnote.startsWith("~~"+starter.getProgram()+"~~")) { 
                			String footnoteSubbed = footnote;
                			for (Starter st : starters) {
                				footnoteSubbed = footnoteSubbed.replace("~~"+st.getProgram()+"~~", st.getHorse().getName().toUpperCase());
                			}
                			starter.setFootnote(footnoteSubbed);
                		}
                	}
                }

                // update each starter with the total lengths behind at each point of call (if
                // applicable)
                starters = PastPerformanceRunningLinePreview.parse(lines, starters);
                raceResultBuilder.starters(starters);


                RaceResult raceResult = raceResultBuilder.build();
                raceResults.add(raceResult);
            } catch (InvalidRaceException | NoLinesToParse e) {
            	e.printStackTrace();
                continue;
            } catch (ChartParserException e) {
            	e.printStackTrace();
                continue;
            }
        }

        return raceResults;
    }

    private boolean officialPositionAffectedByDisqualification(Disqualification disqualification,
            Starter starter) {
        if (starter.getOfficialPosition() != null) {
            return (starter.getOfficialPosition() > disqualification.getOriginalPosition())
                    && (starter.getOfficialPosition() <= disqualification.getNewPosition());
        }
        return false;
    }

    // by program number, falling back to horse name
    private boolean matchesStarter(Disqualification disqualification, Starter starter) {
        return (disqualification.getProgram() != null &&
                disqualification.getProgram().equals(starter.getProgram())) ||
                (disqualification.getHorse() != null &&
                        disqualification.getHorse().getName().equals(
                                starter.getHorse().getName()));
    }

    // by program number, falling back to horse name
    private boolean matchesStarter(ClaimingPrice claimingPrice, Starter starter) {
        return (claimingPrice.getProgram() != null &&
                claimingPrice.getProgram().equals(starter.getProgram())) ||
                (claimingPrice.getHorse() != null &&
                        claimingPrice.getHorse().getName().equals(
                                starter.getHorse().getName()));
    }

    /**
     * Uses {@link ChartStripper} (an extension of Apache PDFBox's {@link PDFTextStripper}) to
     * extract the text from the PDF and, adding a header row, write a CSV String with each row
     * being a character from the PDF with its location etc.
     */
    static String createCsvChart(PDDocument raceChart) throws IOException {
        ChartStripper chartStripper = new ChartStripper(new StringWriter());
        try (StringWriter writer = chartStripper.getWriter()) {
            try (StringWriter throwawayWriter = new StringWriter()) {
                writer.write("xDirAdj|yDirAdj|fontSize|xScale|height|widthOfSpace|widthDirAdj|" +
                        "unicode");
                chartStripper.writeText(raceChart, throwawayWriter);
            }
            return writer.getBuffer().toString();
        }
    }

    /**
     * Loads the file into PDFBox's PDDocument, splits it into pages, and converts to CSV strings
     */
    public static List<String> test(File pdfChartFile) throws ChartParserException {
        List<String> csvCharts = new ArrayList<>();
        try (PDDocument charts = PDDocument.load(pdfChartFile)) {
            Splitter splitter = new Splitter();
            List<PDDocument> raceCharts = splitter.split(charts);
            for (int i = 0; i < raceCharts.size(); i++) {
                try (PDDocument raceChart = raceCharts.get(i)) {                	
                    String csvChart = createCsvChart(raceChart);
                    csvCharts.add(Chart.convertToText(convertToChartCharacters(csvChart)));
                }
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }

        return csvCharts;
    }

    
    static List<String> convertToCsv(File pdfChartFile) throws ChartParserException {
        List<String> csvCharts = new ArrayList<>();
        try (PDDocument charts = PDDocument.load(pdfChartFile)) {
            Splitter splitter = new Splitter();
            List<PDDocument> raceCharts = splitter.split(charts);
            for (int i = 0; i < raceCharts.size(); i++) {
                try (PDDocument raceChart = raceCharts.get(i)) {
                    String csvChart = createCsvChart(raceChart);
                    csvCharts.add(csvChart);
                }
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }

        // some charts are spread over two pages; detect and combine them
        List<String> prunedCsvCharts = new ArrayList<>();
        String previousChart = null;
        for (int i = 0; i < csvCharts.size(); i++) {
            String csvChart = csvCharts.get(i);
            List<ChartCharacter> chartCharacters = convertToChartCharacters(csvChart);
            List<List<ChartCharacter>> lines = separateIntoLines(chartCharacters);

            // if Copyright notice is not the last line, the text continued to the next page
            List<ChartCharacter> lastLine = lines.get(lines.size() - 1);
            String text = Chart.convertToText(lastLine);
            Matcher matcher = COPYRIGHT_PATTERN.matcher(text);
            if (matcher.find()) {
                if (previousChart != null) {
                    csvChart = csvChart.substring(csvChart.indexOf(System.lineSeparator()));
                    previousChart = previousChart.concat(csvChart);
                    prunedCsvCharts.add(previousChart);
                    previousChart = null;
                } else {
                    prunedCsvCharts.add(csvChart);
                }
            } else {
                previousChart = csvChart;
            }
        }
        return prunedCsvCharts;
    }

    static List<ChartCharacter> convertToChartCharacters(String chart) throws ChartParserException {
        return readChartCsv(chart);
    }

    static List<List<ChartCharacter>> separateIntoLines(List<ChartCharacter> data) {
        List<List<ChartCharacter>> lines = new ArrayList<>();
        List<ChartCharacter> line = new ArrayList<>();
        boolean firstTime = true;
        for (ChartCharacter d : data) {
            if (firstTime) {
                line.add(d);
                firstTime = false;
            } else {
                // start of line or "Past Performance Running Line Preview"
                if (d.getxDirAdj() == 9.92 ||
                        (d.getxDirAdj() == 209.385 && d.getUnicode() == 'P')) {
                    lines.add(line);
                    line = new ArrayList<>();
                }

                line.add(d);
            }
        }
        lines.add(line);
        return lines;
    }

    /**
     * Extracts the running line rows/characters
     */
    static List<List<ChartCharacter>> getRunningLines(List<List<ChartCharacter>> lines) {
        List<List<ChartCharacter>> runningLines = new ArrayList<>();
        boolean runningLineSectionsAreActive = false;
        for (List<ChartCharacter> line : lines) {
            String text = convertToText(line);
            if (text.startsWith("Last Raced|Pgm")) {
                runningLineSectionsAreActive = true;
            } else if (text.startsWith("Run-Up:")) {
                runningLines.add(line);
                runningLineSectionsAreActive = false;
            }

            if (runningLineSectionsAreActive) {
                runningLines.add(line);
            }
        }
        return runningLines;
    }

    /**
     * Uses a {@link CsvMapper} to reading a String representing a CSV representation of a PDF
     * Chart, returning a list of {@link ChartCharacter}s
     */
    static List<ChartCharacter> readChartCsv(String csvChart) throws ChartParserException {
        CsvSchema schema = CsvSchema.emptySchema()
                .withHeader()
                .withColumnSeparator('|')
                .withoutQuoteChar();

        try {
            MappingIterator<ChartCharacter> mappingIterator =
                    getCsvMapper().readerFor(ChartCharacter.class)
                            .with(schema)
                            .readValues(csvChart);
            return mappingIterator.readAll();
        } catch (Exception e) {
            throw new ChartParserException("Error deserializing the Chart CSV data", e);
        }
    }

}
