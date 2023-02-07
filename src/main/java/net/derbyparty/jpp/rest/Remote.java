package net.derbyparty.jpp.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Arrays;

import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import net.derbyparty.jpp.chart.ProcessChart;
import net.derbyparty.jpp.main.Analytics;
import net.derbyparty.jpp.main.Main;
import net.derbyparty.jpp.object.HorseToWatch;
import net.derbyparty.jpp.object.Track;
import net.derbyparty.jpp.pastperformanceparser.PastPerformanceParser;


@Path("remote")
@Produces("application/json")
@Consumes("application/json")
public class Remote {
	
	ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	final static String dir = "/Users/ahonaker/Google Drive/pp/jpp/";

	@Path("upload")
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")
	public Response upload(  @FormDataParam("data") InputStream fileInputStream,
	                    @FormDataParam("filename") String filename) throws Exception {
	

	  try {
		    int read = 0;
		    byte[] bytes = new byte[1024];
		 
		    OutputStream out = new FileOutputStream(new File(dir+ filename));
		    while ((read = fileInputStream.read(bytes)) != -1) 
		    {
		      out.write(bytes, 0, read);
		    }
		    out.flush();
		    out.close();
		    
		    return Response.ok().entity(Main.load(filename)).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  
	}
	
	@Path("getChanges")	
	@GET
	public Response getChanges() throws Exception {
		
		try {
			Main.getChanges();
			return getAll();
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 

		

	}
	
	@Path("getResults")	
	@GET
	public Response getResults() throws Exception {
		
		try {
			Main.getResults();
			return getAll();
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 

		

	}
	
	@Path("save")	
	@GET
	public Response save() throws Exception {
		
		try {
			Main.save();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 

		return Response.noContent().build();

	}
	
	@Path("retrieve/{track}/{year}/{month}/{day}")	
	@GET
	public Response retrieve(@PathParam("track") String track ,
			@PathParam("year") int year,
			@PathParam("month") int month,
			@PathParam("day") int day) throws Exception {
		
		try {
			return Response.ok().entity(Main.retrieve(track, LocalDate.of(year, month, day))).build();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 


	}
	
	@Path("getSaved")
	@GET
	public Response getSaved() throws Exception {
	  
	  try {
		    
		    return Response.ok().entity(Main.getSavedList()).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	}
	
	
	@Path("getAll")	
	@GET
	public Response getAll() throws Exception {
		
		try {
			return Response.ok().entity(Main.getAll()).build();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 


	}
	
	@Path("get/{raceNumber}")	
	@GET
	public Response get(@PathParam("raceNumber") int raceNumber) throws Exception {
		
		try {
			return Response.ok().entity(Main.get(raceNumber)).build();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 


	}
	
	@Path("getNumRaces")	
	@GET
	public Response getNumRaces() throws Exception {
		
		try {
			return Response.ok().entity(Main.getNumRaces()).build();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 


	}	
	@Path("getSelectionSummary")	
	@GET
	public Response getSelectionSummary() throws Exception {
		
		try {
			return Response.ok().entity(Main.getSelectionSummary()).build();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 


	}		
	
	@Path("calculate")	
	@GET	
	public Response calculate() throws Exception {
		
		try {
			Main.calculate();
			return Response.ok().entity(Main.getAll()).build();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 


	}
	
	@Path("calculate/{distanceOption}/{surfaceOption}/{conditionOption}")	
	@GET
	public Response calculate(@PathParam("distanceOption") String distanceOption, @PathParam("surfaceOption") String surfaceOption, @PathParam("conditionOption") String conditionOption) throws Exception {
		
		try {		
			Main.updateOptions(distanceOption, surfaceOption, conditionOption);
			Main.calculate();
			return Response.ok().entity(Main.getAll()).build();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 


	}
	
	@Path("uploadAndCalculate")
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")
	public Response uploadAndCalculate(  @FormDataParam("data") InputStream fileInputStream,
	                    @FormDataParam("filename") String filename,
	                    @FormDataParam("distanceOption") String distanceOption,
	                    @FormDataParam("surfaceOption") String surfaceOption,
	                    @FormDataParam("conditionOption") String conditionOption) throws Exception {
	  
	  try {
		    int read = 0;
		    byte[] bytes = new byte[1024];
		 
		    OutputStream out = new FileOutputStream(new File(dir + filename));
		    while ((read = fileInputStream.read(bytes)) != -1) 
		    {
		      out.write(bytes, 0, read);
		    }
		    out.flush();
		    out.close();
		    
		    Main.load(filename);
		    calculate(distanceOption, surfaceOption, conditionOption);
		    return Response.ok().entity(Main.getAll()).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  
	}
	
	@Path("toggleScratch/{raceNumber}/{name}")	
	@GET

	public Response toggleScratch(@PathParam("raceNumber") int raceNumber, @PathParam("name") String name) throws Exception {
		
		try {
			Main.toggleScratch(raceNumber, name);
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 
		
		return calculate();

	}

	@Path("togglePick")	
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")

	public Response togglePick(  @FormDataParam("raceNumber") int raceNumber,
			@FormDataParam("name") String name) throws Exception {
		
		try {
			Main.togglePick(raceNumber, name);
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 
		
		return calculate();

	}
	
	@Path("toggleShowDetail")	
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")

	public Response toggleShowDetail(@FormDataParam("raceNumber") int raceNumber, @FormDataParam("name") String name) throws Exception {
		
		try {
			Main.toggleShowDetail(raceNumber, name);
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 
		
		return calculate();

	}	
	
	@Path("toggleIgnored")	
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")

	public Response toggleIgnored(@FormDataParam("raceNumber") int raceNumber, @FormDataParam("name") String name, @FormDataParam("year") int year, @FormDataParam("month") int month, @FormDataParam("day") int day) throws Exception {
		
		try {
			Main.toggleIgnored(raceNumber, name, LocalDate.of(year, month, day));
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 
		
		return calculate();

	}
	
	@Path("toggleOffTheTurf/{raceNumber}")	
	@GET

	public Response toggleOffTheTurf(@PathParam("raceNumber") int raceNumber) throws Exception {
		
		try {
			Main.toggleOffTheTurf(raceNumber);
			return calculate();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 
		
		

	}	
	
	@Path("setTrackCondition/{raceNumber}/{condition}")	
	@GET

	public Response setTrackCondition(@PathParam("raceNumber") int raceNumber, @PathParam("condition") String condition) throws Exception {
		
		try {
			Main.setTrackCondition(raceNumber, condition);
			return calculate();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 
		
		

	}	
	
	@Path("setSelection")	
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")
	
	public Response setSelection(  @FormDataParam("raceNumber") int raceNumber,
			@FormDataParam("name") String name,
			@FormDataParam("selection") String selection) throws Exception {
		
		try {
			return Response.ok().entity(Main.setSelection(raceNumber, name, selection)).build();
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 
		
		

	}
	
	@Path("setBettingLine")	
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")
	
	public Response setBettlingLine(  @FormDataParam("raceNumber") int raceNumber,
			@FormDataParam("name") String name,
			@FormDataParam("bettingLine") float bettingLine) throws Exception {
		
		try {
			Main.setBettingLine(raceNumber, name, bettingLine);
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 
		
		return Response.noContent().build();

	}
	
	@Path("setRaceNote")
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")
	public Response setRaceNote(  @FormDataParam("raceNumber") int raceNumber,
	                    @FormDataParam("note") String note) throws Exception {
	

	  
	  try {
		  Main.setRaceNote(raceNumber, note);
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	  
	}
	
	@Path("setHorseNote")
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")
	public Response setHorseNote(  @FormDataParam("raceNumber") int raceNumber,
								@FormDataParam("name") String name,
								@FormDataParam("note") String note) throws Exception {
	

	  
	  try {
		  Main.setHorseNote(raceNumber, name, note);
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	  
	}
	
	@Path("getKeyRaces")
	@GET
	public Response getKeyRaces() throws Exception {
	  
	  try {
		    
		    return Response.ok().entity(ProcessChart.getKeyRaces()).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	}
	
	@Path("getChart/{track}/{year}/{month}/{day}")
	@GET
	public Response getChart(@PathParam("track") String track ,
			@PathParam("year") int year,
			@PathParam("month") int month,
			@PathParam("day") int day) throws Exception {
	  
	  try {
		    
		    return Response.ok().entity(ProcessChart.getChart(track, LocalDate.of(year, month, day))).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	}	

	@Path("parseDirectory")
	@GET
	public Response parseDirectory() throws Exception {
	  
	  try {
		    
		    ProcessChart.parseDirectory();;
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	  
	}
	
	@Path("parsePP")
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")
	public Response parsePP(  @FormDataParam("data") InputStream fileInputStream,
	                    @FormDataParam("filename") String filename) throws Exception {
	  
	  try {
		    int read = 0;
		    byte[] bytes = new byte[1024];
		 
		    OutputStream out = new FileOutputStream(new File(dir + filename));
		    while ((read = fileInputStream.read(bytes)) != -1) 
		    {
		      out.write(bytes, 0, read);
		    }
		    out.flush();
		    out.close();
		   
		    return Response.ok().entity(Main.augment(new File(dir + filename))).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  
	}
	
	@Path("extractPP")
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")
	public Response extractPP(  @FormDataParam("data") InputStream fileInputStream,
	                    @FormDataParam("filename") String filename) throws Exception {
	  
	  try {
		    int read = 0;
		    byte[] bytes = new byte[1024];
		 
		    OutputStream out = new FileOutputStream(new File(dir + filename));
		    while ((read = fileInputStream.read(bytes)) != -1) 
		    {
		      out.write(bytes, 0, read);
		    }
		    out.flush();
		    out.close();
		   
		    return Response.ok().entity(PastPerformanceParser.extractText(new File(dir + filename))).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  
	}
	
	@Path("addProgramNumbers")
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")
	public Response addProgramNumbers(  @FormDataParam("data") InputStream fileInputStream,
	                    @FormDataParam("filename") String filename) throws Exception {
	  
		  try {
			    int read = 0;
			    byte[] bytes = new byte[1024];
			 
			    OutputStream out = new FileOutputStream(new File(dir+ filename));
			    while ((read = fileInputStream.read(bytes)) != -1) 
			    {
			      out.write(bytes, 0, read);
			    }
			    out.flush();
			    out.close();
			    
			    return Response.ok().entity(Main.addLateData(filename)).build();
		  
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
			} 
	  
	  
	}
	
	@Path("getCharts")
	@GET
	public Response getCharts() throws Exception {
	  
	  try {
		    
		    return Response.ok().entity(ProcessChart.getCharts()).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	}
	
	@Path("saveNotes")
	@POST
	@Consumes("multipart/form-data")
	public Response saveNotes(  @FormDataParam("notes") String notes) throws Exception {
	  
	  try {	
		    Main.saveNotes(mapper.readTree(notes));
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	}
	
	@Path("getTracks")
	@GET
	public Response getTracks() throws Exception {
	  
	  try {
		    
		    return Response.ok().entity(Main.getTracks()).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	}
	
	@Path("saveTracks")
	@POST
	@Consumes("multipart/form-data")
	public Response saveTracks(  @FormDataParam("data") String data) throws Exception {
	  
	  try {	
		    Main.saveTracks(Arrays.asList(mapper.readValue(data, Track[].class)));
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	}
	
	@Path("convertRaceDates")
	@GET
	public Response convertRaceDates() throws Exception {
	  
	  try {
		    
		    Main.convertRaceDates();;
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	  
	}
	
	@Path("getHorsesToWatch")
	@GET
	public Response getHorsesToWatch() throws Exception {
	  
	  try {
		    
		    return Response.ok().entity(Main.getHorsesToWatch()).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	}
	
	@Path("getHorseToWatch/{name}")
	@GET
	public Response getHorseToWatch(@PathParam("name") String name) throws Exception {
	  
	  try {
		    
		    return Response.ok().entity(Main.getHorseToWatch(name)).build();
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	}
	
	@Path("saveHorseToWatch")
	@POST
	@Consumes("multipart/form-data")
	public Response saveHorseToWatch(  @FormDataParam("data") String data) throws Exception {
	  
	  try {	
		    Main.saveHorseToWatch(mapper.readValue(data, HorseToWatch.class));;
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	}
	
	@Path("convertNotes")
	@GET
	public Response convertNotes() throws Exception {
	  
	  try {
		    
		    Main.convertNotes();;
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	  
	}
	
	@Path("markChartsReviewed")
	@GET
	public Response markChartsReviewed() throws Exception {
	  
	  try {
		    
		    Main.markChartsReviewed();;
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	  
	}
	
	@Path("updateHorsesToWatchWithPPs/{track}/{year}/{month}/{day}")	
	@GET
	public Response updateHorsesToWatchWithPPs(@PathParam("track") String track ,
			@PathParam("year") int year,
			@PathParam("month") int month,
			@PathParam("day") int day) throws Exception {
		
		try {
			Main.updateHorsesToWatchWithPPs(track, LocalDate.of(year, month, day));
			
 		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
 		} 
		return Response.noContent().build();

	}
	
	@Path("updateAllHorsesToWatchWithPPs")
	@GET
	public Response uppdateAllHorsesToWatchWithPPs() throws Exception {
	  
	  try {
		    
		    Main.updateAllHorsesToWatchWithPPs();;
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	  
	}
	
	@Path("generateRaceTimes")
	@GET
	public Response generateRaceTimes() throws Exception {
	  
	  try {
		    
		    Analytics.generateRaceTimesCSV();;
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	  
	}
	
	@Path("retrieveCalculateAndSaveAll")
	@GET
	public Response retrieveCalculateAndSaveAll() throws Exception {
	  
	  try {
		    
		    Main.retrieveCalculateAndSaveAll();;
	  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		} 
	  
	  	return Response.noContent().build();
	  
	}
	
}
