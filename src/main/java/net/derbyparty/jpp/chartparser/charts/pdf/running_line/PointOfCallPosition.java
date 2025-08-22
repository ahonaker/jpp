package net.derbyparty.jpp.chartparser.charts.pdf.running_line;

import net.derbyparty.jpp.chartparser.charts.pdf.ChartCharacter;
import net.derbyparty.jpp.chartparser.charts.pdf.Starter;
import net.derbyparty.jpp.chartparser.points_of_call.RelativePosition;
import net.derbyparty.jpp.chartparser.points_of_call.LengthsAhead;

import static net.derbyparty.jpp.chartparser.charts.pdf.Chart.convertToText;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The position of the {@link Starter} and the lengths ahead of the next participant (if applicable)
 * at a particular point of call
 */
public class PointOfCallPosition {

	private static final Double SIX = (double) 6;
    private static final Double SEVEN = (double) 7;

    /**
     * Groups the chart characters by font size, then extracts and parses those designated for the
     * position at the particular point of call and the lengths ahead (if applicable) of the next
     * participant
     *
     * @return {@link RelativePosition} instance containing this {@link Starter}'s position and
     * lengths ahead
     */
    public static RelativePosition parse(List<ChartCharacter> characters) {
    	//System.out.println(characters);
        Map<Double, List<ChartCharacter>> charactersByFontSize = characters.stream().collect(
                Collectors.groupingBy(character ->  character.getxScale()));

        Integer position = getPosition(charactersByFontSize.get(SEVEN));
        LengthsAhead lengthsAhead = ChartLengthsAhead.parse(charactersByFontSize.get(SIX));

        return new RelativePosition(position, lengthsAhead);
    }

    protected static Integer getPosition(List<ChartCharacter> positionCharacters) {
        if (positionCharacters != null) {
            String pos = convertToText(positionCharacters);
            if (!pos.equals("---") && !pos.equals("*")) {
                try {
                    return Integer.parseInt(pos);
                } catch (NumberFormatException e) {
                	System.out.println("WARNING: " + String.format("Unable to parse position from following text: " +
                            "%s", pos));
                }
            }
        }
        return null;
    }
}
