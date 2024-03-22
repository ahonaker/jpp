<template>
    <b-container fluid :class="{pp: true, border: true, scratchedDetail: entry.scratchedFlag, 'text-center': entry.scratchedFlag}">
        <span v-if="entry.scratchedFlag" class="h4 text-white">{{entry.programNumber}} - {{entry.name}} is scratched</span>
        <b-row v-if="!entry.scratchedFlag">
            <b-col cols="4">
                <b-row>
                    <b-col cols="1">
                        <span class="h5"><strong>{{entry.programNumber}}</strong>
                        <span v-if="entry.postPosition != parseInt(entry.programNumber)" class="pp"><sup>pp{{entry.postPosition}}</sup></span>																							
                        </span><br>
                        <span class="h6">                               
                            <span>{{entry.mlodds}}</span>
                        </span><br>
                    </b-col>
                    <b-col>
                        <span class="h5"><strong>
                            <b-icon-star-fill 
                                variant="success" 
                                v-if="entry.flag == 'Star'"                                       
                            ></b-icon-star-fill>
                            {{entry.name}} ({{entry.runStyle}} {{entry.speedPoints}})</strong>&nbsp;
                            <span style="float:right;">
                                <span v-if="entry.claimingPrice">{{formatCurrency(entry.claimingPrice)}}<br></span>
                                <b-badge class="text-right" v-if="entry.mtoflag == 'M'" variant="dark">MTO</b-badge>
                                <b-badge v-if="entry.mtoflag == 'A'" variant="dark">AE</b-badge>
                            </span>
                        </span>
                        <br>
                        <strong>Own: {{entry.owner}}</strong><br>
                        {{entry.ownerSilks}}
                    </b-col>
                </b-row>
            </b-col>
            <b-col cols="3">
                <strong>
                    {{entry.color}}. {{entry.sex}}. {{entry.age}} ({{entry.foalingMonth}})  {{entry.auctionLocationDate}}  <span :class="entry.auctionPrice > 100000 && entry.lifetimeWins == 0 ? 'greenHighlight' : ''">{{formatCurrency(entry.auctionPrice)}}</span><br>
                    <b-row>
                        <b-col class="pr-0" cols="2">
                            Sire :
                        </b-col>
                        <b-col>
                            {{entry.sire}} ({{entry.siresSire}}) {{formatCurrency(entry.sireStudFee)}}<br>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col class="pr-0" cols="2">
                            Dam  :  
                        </b-col>
                        <b-col>
                            {{entry.dam}} ({{entry.damsSire}})<br>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col class="pr-0" cols="2">
                            Brdr :  
                        </b-col>
                        <b-col>
                            {{entry.breeder}}<br>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col class="pr-0" cols="2">
                            Trnr :  
                        </b-col>
                        <b-col>
                            {{entry.trainer.name}}  ({{entry.trainer.currentMeetStarts}} {{entry.trainer.currentMeetWins}}-{{entry.trainer.currentMeetPlaces}}-{{entry.trainer.currentYearShows}} <span :class="(entry.trainer.currentMeetStarts == 0 ? 0 : (entry.trainer.currentMeetWins/entry.trainer.currentMeetStarts*100) >= 20 && entry.trainer.currentMeetStarts >= 20) ? 'greenHighlight' : ''">{{entry.trainer.currentMeetStarts == 0 ? 0 : (entry.trainer.currentMeetWins/entry.trainer.currentMeetStarts*100).toFixed(0)}}%</span>)
                        </b-col>
                    </b-row>																																	 
                </strong>
            </b-col>
            <b-col cols="1" class="text-right">
                <br><strong><span v-if="entry.equipment == 'BLINKERS_ON'">Blnkr On</span><span v-if="entry.equipment == 'BLINKERS_OFF'">Blnkr Off</span></strong>
                <br><strong><span v-if="entry.sex == 'g'"><b-badge pill variant="dark">G</b-badge></span><span v-if="entry.medication == 'LASIX_FIRST'"><b-badge variant="dark">L</b-badge></span><span v-if="entry.medication == 'LASIX'">L</span> {{entry.weight}}</strong>
            </b-col>
            <b-col cols="4">
                <b-row>
                    <b-col cols="5" class="border-right">
                        <b-row class="border-left border-bottom">
                            <b-col cols="2">
                                <strong>Life:</strong>  
                            </b-col>
                            <b-col cols="5">
                                {{entry.lifetimeStarts}}&nbsp;&nbsp;{{entry.lifetimeWins}}-{{entry.lifetimePlaces}}-{{entry.lifetimeShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(entry.lifetimeEarnings)}}
                            </b-col>
                            <b-col cols="2">
                                <strong>{{entry.lifetimeBestSpeed}}</strong>
                            </b-col>
                        </b-row>													
                        <b-row>
                            <b-col cols="2">
                                <strong>{{entry.currentYear}}</strong> 
                            </b-col>
                            <b-col cols="5">
                                {{entry.currentYearStarts}}&nbsp;&nbsp;{{entry.currentYearWins}}-{{entry.currentYearPlaces}}-{{entry.currentYearShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(entry.currentYearEarnings)}}
                            </b-col>
                            <b-col cols="2">
                                <strong>{{entry.currentYearBestSpeed}}</strong>
                            </b-col>										
                        </b-row>	
                        <b-row>
                            <b-col cols="2">
                                <strong>{{entry.previousYear}}</strong>  
                            </b-col>
                            <b-col cols="5">
                                {{entry.previousYearStarts}}&nbsp;&nbsp;{{entry.previousYearWins}}-{{entry.previousYearPlaces}}-{{entry.previousYearShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(entry.previousYearEarnings)}}
                            </b-col>
                            <b-col cols="2">
                                <strong>{{entry.previousYearBestSpeed}}</strong>
                            </b-col>													
                        </b-row>	
                        <b-row>
                            <b-col cols="2">
                                <strong>{{entry.currentTrack}}</strong> 
                            </b-col>
                            <b-col cols="5" :class="entry.trackWins > 0 || entry.trackPlaces > 2 ? 'greenHighlight' : ''">
                                {{entry.trackStarts}}&nbsp;&nbsp;{{entry.trackWins}}-{{entry.trackPlaces}}-{{entry.trackShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(entry.trackEarnings)}}
                            </b-col>
                            <b-col cols="2">
                                <strong>{{entry.trackBestSpeed}}</strong>
                            </b-col>													
                        </b-row>
                    </b-col>
                    <b-col cols="7">
                        <b-row>
                            <b-col cols="4">
                                <strong>Fst <span v-if="entry.pedigreeRatingDirt">({{entry.pedigreeRatingDirt}})</span></strong>  
                            </b-col>
                            <b-col cols="3">
                                {{entry.dirtStarts}}&nbsp;{{entry.dirtWins}}-{{entry.dirtPlaces}}-{{entry.dirtShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(entry.dirtEarnings)}}
                            </b-col>
                            <b-col cols="1">
                                <strong>{{entry.dirtBestSpeed}}</strong>
                            </b-col>
                        </b-row>													
                        <b-row>
                            <b-col cols="4" >
                                <strong>Off <span v-if="entry.pedigreeRatingMud">({{entry.pedigreeRatingMud}})</span></strong> 
                            </b-col>
                            <b-col cols="3" :class="race.trackCondition != 'ft' && race.trackCondition != 'fm' && (entry.wetWins > 0 || entry.wetPlaces > 2) ? 'greenHighlight' : ''">
                                {{entry.wetStarts}}&nbsp;{{entry.wetWins}}-{{entry.wetPlaces}}-{{entry.wetShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(entry.wetEarnings)}}
                            </b-col>
                            <b-col cols="1">
                                <strong>{{entry.wetBestSpeed}}</strong>
                            </b-col>										
                        </b-row>	
                        <b-row>
                            <b-col cols="4">
                                <strong>Dis  <span v-if="entry.pedigreeRatingDist">({{entry.pedigreeRatingDist}})</span></strong>  
                            </b-col>
                            <b-col cols="3">
                                {{entry.distanceStarts}}&nbsp;{{entry.distanceWins}}-{{entry.distancePlaces}}-{{entry.distanceShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(entry.distanceEarnings)}}
                            </b-col>
                            <b-col cols="1">
                                <strong>{{entry.distanceBestSpeed}}</strong>
                            </b-col>													
                        </b-row>	
                        <b-row>
                            <b-col cols="4">
                                <strong>Trf  <span v-if="entry.pedigreeRatingTurf">({{entry.pedigreeRatingTurf}})</span></strong> 
                            </b-col>
                            <b-col cols="3">
                                {{entry.turfStarts}}&nbsp;{{entry.turfWins}}-{{entry.turfPlaces}}-{{entry.turfShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(entry.turfEarnings)}}
                            </b-col>
                            <b-col cols="1">
                                <strong>{{entry.turfBestSpeed}}</strong>
                            </b-col>													
                        </b-row>
                        <b-row>
                            <b-col cols="4">
                                <strong>AW</strong> 
                            </b-col>
                            <b-col cols="3">
                                {{entry.allWeatherStarts}}&nbsp;{{entry.allWeatherWins}}-{{entry.allWeatherPlaces}}-{{entry.allWeatherShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(entry.allWeatherEarnings)}}
                            </b-col>
                            <b-col cols="1">
                                <strong>{{entry.allWeatherBestSpeed}}</strong>
                            </b-col>													
                        </b-row>																																																		
                    </b-col>
                    <b-col>

                    </b-col>
                </b-row>
            </b-col>

        </b-row>
        <b-row v-if="!entry.scratchedFlag">
            <b-col>
                {{properize(entry.jockey.name)}} ({{entry.jockey.currentMeetStarts}} {{entry.jockey.currentMeetWins}}-{{entry.jockey.currentMeetPlaces}}-{{entry.jockey.currentMeetShows}}<span :class="(entry.jockey.currentMeetStarts == 0 ? 0 : (entry.jockey.currentMeetWins/entry.jockey.currentMeetStarts*100) >= 20 && entry.jockey.currentMeetStarts >= 20) ? 'greenHighlight' : ''"> {{entry.jockey.currentMeetStarts == 0 ? 0 : (entry.jockey.currentMeetWins/entry.jockey.currentMeetStarts*100).toFixed(0)}}%)</span><br>
            </b-col>
        </b-row>
        <b-row v-if="!entry.scratchedFlag">
            <b-col cols="3">
                <b-row>
                    <b-col cols="4">
                        {{entry.currentYear}}
                    </b-col>
                    <b-col cols="2">
                        {{entry.jockey.currentYearStarts}}
                    </b-col>
                    <b-col :class="entry.jockey.currentYearStarts >= 20 && entry.trainer.currentYearWins/entry.jockey.currentYearStarts*100 >= 20 ? 'greenHighlight' : ''">
                        {{entry.jockey.currentYearStarts == 0 ? 0 : (entry.jockey.currentYearWins/entry.jockey.currentYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{entry.jockey.currentYearStarts == 0 ? 0 : ((entry.jockey.currentYearWins + entry.jockey.currentYearPlaces + entry.jockey.currentYearShows)/entry.jockey.currentYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{entry.jockey.currentYearROI}}
                    </b-col>											
                </b-row>											
                <b-row>
                    <b-col cols="4">
                        {{entry.previousYear}}
                    </b-col>
                    <b-col cols="2">
                        {{entry.jockey.previousYearStarts}}
                    </b-col>
                    <b-col>
                        {{entry.jockey.previousYearStarts == 0 ? 0 : (entry.jockey.previousYearWins/entry.jockey.previousYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{entry.jockey.previousYearStarts == 0 ? 0 : ((entry.jockey.previousYearWins + entry.jockey.previousYearPlaces + entry.jockey.previousYearShows)/entry.jockey.previousYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{entry.jockey.previousYearROI}}
                    </b-col>											
                </b-row>
                <b-row v-for="stat in entry.jockey.stats" :key="stat.category">
                    <b-col cols="4">
                        JKYw/ {{stat.category}}
                    </b-col>
                    <b-col cols="2">
                        {{stat.starts}}
                    </b-col>
                    <b-col :class="stat.winPercent > 20 || (stat.starts > 0 && stat.wins / stat.starts > 0.2) ? 'greenHighlight' : ''">
                        <span v-if="stat.winPercent == 0">{{stat.starts == 0 ? 0 : (stat.wins / stat.starts * 100).toFixed(0)}}%</span>
                        <span v-else>{{stat.winPercent}}%</span>
                    </b-col>
                    <b-col>
                        <span v-if="stat.itmpercent == 0">{{stat.starts == 0 ? 0 : ((stat.wins + stat.places + stat.shows)/ stat.starts * 100).toFixed(0)}}%</span>
                        <span v-else>{{stat.itmpercent}}%</span>
                    </b-col>
                    <b-col>
                        {{stat.roi}}
                    </b-col>											
                </b-row>	                      									
            </b-col>
            <b-col cols="3" offset="1">
                <b-row>
                    <b-col cols="4">
                        {{entry.currentYear}}
                    </b-col>
                    <b-col cols="2">
                        {{entry.trainer.currentYearStarts}}
                    </b-col>
                    <b-col :class="entry.trainer.currentYearStarts >= 20 && entry.trainer.currentYearWins/entry.trainer.currentYearStarts*100 >= 20 ? 'greenHighlight' : ''">
                        {{entry.trainer.currentYearStarts == 0 ? 0 : (entry.trainer.currentYearWins/entry.trainer.currentYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{entry.trainer.currentYearStarts == 0 ? 0 : ((entry.trainer.currentYearWins + entry.trainer.currentYearPlaces + entry.trainer.currentYearShows)/entry.trainer.currentYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{entry.trainer.currentYearROI}}
                    </b-col>											
                </b-row>											
                <b-row v-for="stat in entry.trainer.trainerStats" :key="stat.category">
                    <b-col class="pr-0" cols="4">
                        {{stat.category}}
                    </b-col>
                    <b-col cols="2">
                        {{stat.starts}}
                    </b-col>
                    <b-col :class="stat.winPercent >= 20 ? 'greenHighlight' : ''">
                        {{stat.winPercent.toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{stat.itmpercent.toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{stat.roi.toFixed(2)}}
                    </b-col>											
                </b-row>	
            </b-col>
            <b-col cols="4" offset="1">
                <b-row v-if="entry.sireAWD">
                    <b-col cols="3" class="pr-0">
                        Sire Stats: AWD {{format1Place(entry.sireAWD)}} 
                    </b-col>
                    <b-col class="px-0 text-center">
                        {{entry.sireMudPercent}}%Mud
                    </b-col>
                    <b-col class="px-0 text-center">
                        {{entry.sireMudStarts}}MudSts
                    </b-col>
                    <b-col class="px-0 text-center" v-if="entry.sireTurfPercent > 0">
                        {{entry.sireTurfPercent}}%Turf
                    </b-col>
                    <b-col class="px-0 text-center" v-if="entry.sireFirstTurfPercent > 0">
                        {{entry.sireFirstTurfPercent}}%1stT
                    </b-col>
                    <b-col class="px-0 text-center" v-if="entry.sireFirstPercent > 0">
                        {{entry.sireFirstPercent}}%1st
                    </b-col>
                    <b-col class="pl-0 text-right">
                        {{format2Places(entry.sireSPI)}}spi
                    </b-col>
                </b-row>
                <b-row v-if="entry.damSireAWD" :class="entry.damDescription ? '' : 'mb-1'">
                    <b-col class="pr-0" cols="3">
                        Dam's Sire: AWD {{format1Place(entry.damSireAWD)}} 
                    </b-col>
                    <b-col class="px-0 text-center">
                        {{entry.damSireMudPercent}}%Mud
                    </b-col>
                    <b-col class="px-0 text-center">
                        {{entry.damSireMudStarts}}MudSts
                    </b-col>
                    <b-col class="px-0 text-center" v-if="entry.damSireTurfPercent > 0">
                        {{entry.damSireTurfPercent}}%Turf
                    </b-col>
                    <b-col class="px-0 text-center" v-if="entry.damSireFirstTurfPercent > 0">
                        {{entry.damSireFirstTurfPercent}}%1stT
                    </b-col>                           
                    <b-col class="px-0 text-center" v-if="entry.damSireFirstPercent > 0">
                        {{entry.damSireFirstPercent}}%1st
                    </b-col>
                    <b-col class="pl-0 text-right">
                        {{format2Places(entry.damSireSPI)}}spi
                    </b-col>
                </b-row>
                <b-row v-if="entry.damDescription" class="mb-1">
                    <b-col class="pr-0" cols="4">
                        Dam's Stats: {{entry.damDescription}} 
                    </b-col>
                    <b-col class="px-0 text-center" v-if="entry.damTurfWinners">
                        {{entry.damTurfWinners}}trfW
                    </b-col>                            
                    <b-col class="px-0 text-center" v-if="entry.damTwoYearOldPercent">
                        {{entry.damTwoYearOldPercent}}%2yo
                    </b-col>
                    <b-col class="px-0 text-center">
                        {{entry.damStarters}}str
                    </b-col>
                    <b-col class="px-0 text-center">
                        {{entry.damWinners}}w
                    </b-col>
                    <b-col class="px-0 text-center">
                        {{entry.damStakesWinners}}sw
                    </b-col>                           
                    <b-col class="pl-0 text-right">
                        {{format2Places(entry.damDPI)}}dpi
                    </b-col>
                </b-row>                                               
                <b-row v-for="(angle,ndx) in entry.angles" :key="ndx">
                    <b-col>
                        <span v-if="angle.source != 'Summary'" :class="'font-weight-bold ' + (angle.type == '+' ? 'text-success' : (angle.type == '-' ? 'text-danger' : 'text-warning'))">
                            <b-icon-file-pdf v-if="angle.source == 'Augmented'"></b-icon-file-pdf>
                            <b-icon-lightbulb v-if="angle.source == 'Generated'"></b-icon-lightbulb>
                            {{angle.text}} 
                            <span v-if="angle.source=='Generated'">({{angle.total}} {{angle.winPercent}}% {{angle.itmPercent}}% ${{angle.roi}})</span>
                        </span>
                    </b-col>
                </b-row>
                    <b-row v-for="(alert,ndx) in comboAlerts(entry)" :key="ndx">
                    <b-col>
                        <b-icon-star-fill></b-icon-star-fill>
                        <strong> {{alert.trim()}}</strong>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>     
        <b-row v-if="!entry.scratchedFlag" class="mb-2 mx-2">
            <span v-if="entry.flag == 'Star'"><b-icon-star-fill variant="success"></b-icon-star-fill> {{entry.comment}}<br></span>        
            Note: {{entry.note}}
        </b-row>
        <entry-extra-view v-if="entry.pastPerformances.length > 3 && !entry.scratchedFlag" :entry="entry"></entry-extra-view>   
        <b-row v-if="entry.pastPerformances.length > 0" class="entry">
            <b-col cols="8" class="text-center">
                <span v-if="entry.pastPerformances[0].flag || entry.pastPerformances[0].comment || entry.pastPerformances[0].footnote">
                    <strong>Last race:</strong> {{entry.pastPerformances[0].flag}} <span v-if="entry.pastPerformances[0].flag">/</span> {{entry.pastPerformances[0].comment}}&nbsp;
                    <span v-if="entry.pastPerformances[0].flag">- </span>{{entry.pastPerformances[0].footnote}}<br>
                </span>
			</b-col>
			<b-col class="text-center">
                <span v-if="entry.pastPerformances[0].keyRace">
					<strong>Exits Key Race: {{entry.pastPerformances[0].keyRace.track}} {{formatDate(entry.pastPerformances[0].keyRace.raceDate)}} Race {{entry.pastPerformances[0].keyRace.raceNumber}}</strong><br>
					<span v-html="keyRaceHorsesFormat(entry.pastPerformances[0].keyRace.horses)"></span>
                </span>
            </b-col>
        </b-row>
        <b-row class="table b-table table-sm">       
            <past-performance-print-view v-if="!entry.scratchedFlag" :entry="entry" :race="race" :tracks="tracks"></past-performance-print-view>
        </b-row>
        <b-row v-if="!entry.scratchedFlag">
            <span v-for="workout in entry.workouts" :key="workout.date">
                <workout-view :workout="workout"></workout-view>
            </span>		
        </b-row>								
    </b-container>									
</template>

<script>
import { BIconStarFill, BIconFilePdf, BIconLightbulb } from 'bootstrap-vue'
import PastPerformancePrintView from '@/components/PastPerformancePrintView'
import WorkoutView from '@/components/WorkoutView'
import EntryExtraView from '@/components/EntryExtraView'

import _ from 'underscore'
import moment from 'moment'

export default {
    name: 'EntryPrintView',
    components: {
		PastPerformancePrintView, WorkoutView, EntryExtraView, BIconStarFill, BIconFilePdf, BIconLightbulb
    },
    props : ['race', 'entry', 'hideML', 'tracks'],
    data () {
		return {

        }
    },
    computed: {

    },
    methods:  {
		format2Places(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},
		format1Place(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 1, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 1, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},        
		formatCurrency(amount) {
			const formatter = new Intl.NumberFormat('en-US', {
				style: 'currency',
				currency: 'USD',

				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},
		formatCurrency2(amount) {
			const formatter = new Intl.NumberFormat('en-US', {
				style: 'currency',
				currency: 'USD',

				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},  
        formatDate (date) {
            return moment(date).format("MM/DD/YY")
            //return new Date(date).toLocaleDateString;
			//return date[1] + "/" + date[2] + "/" + date[0];
		},	              	            
		alsoInRace(nameToMatch, row) {
			var names = _.pluck(this.race.entries, "name");
			if (names.indexOf(nameToMatch) > -1 && nameToMatch != row.name) return "alsoInRace";

		},
        keyRaceHorsesFormat(horses) {
            var ret = "<span class=''>";
            for (var i=0; i < horses.length; i++) {
                ret += horses[i].name 
                    + " @ " + horses[i].track
                    + " " + this.formatDate(horses[i].raceDate)
                    + "<sup>" + horses[i].raceNumber + "</sup>"
                    + " Pos: " + horses[i].position 
                    + " BL: " + this.format2Places(horses[i].beatenLengths);
                if (i < horses.length-1) ret += "<br>";
            }
            return ret + "</span>";
        },        
        properize (name) {
            var upperCase = /^[A-Z]/; //Regexp for all UPPERCASE words
            var suffixes = new Array("II", "(II)", "III", "(III)", "IV", "(IV)", "VI", "(VI)", "VII", "(VII)", "2nd", "(2nd)", "3rd", "(3rd)", "4th", "(4th)", "5th", "(5th)");
            var surnames = new Array("ApShaw", "d'Albini", "d'Aubigney", "d'Aubigne", "d'Autry", "d'Entremont", "d'Hurst", "D'ovidio", "da Graca", "DaSilva", "DeAnda", "deAnnethe", "deAubigne", "deAubigny", "DeBardelaben", "DeBardeleben", "DeBaugh", "deBeauford", "DeBerry", "deBethune", "DeBetuile", "DeBoard", "DeBoer", "DeBohun", "DeBord", "DeBose", "DeBrouwer", "DeBroux", "DeBruhl", "deBruijn", "deBrus", "deBruse", "deBrusse", "DeBruyne", "DeBusk", "DeCamp", "deCastilla", "DeCello", "deClare", "DeClark", "DeClerck", "DeCoste", "deCote", "DeCoudres", "DeCoursey", "DeCredico", "deCuire", "DeCuyre", "DeDominicios", "DeDuyster", "DeDuytscher", "DeDuytser", "deFiennes", "DeFord", "DeForest", "DeFrance", "DeFriece", "DeGarmo", "deGraaff", "DeGraff", "DeGraffenreid", "DeGraw", "DeGrenier", "DeGroats", "DeGroft", "DeGrote", "DeHaan", "DeHaas", "DeHaddeclive", "deHannethe", "DeHatclyf", "DeHaven", "DeHeer", "DeJager", "DeJarnette", "DeJean", "DeJong", "deJonge", "deKemmeter", "deKirketon", "DeKroon", "deKype", "del-Rosario", "dela Chamotte", "DeLa Cuadra", "DeLa Force", "dela Fountaine", "dela Grena", "dela Place", "DeLa Ward", "DeLaci", "DeLacy", "DeLaet", "DeLalonde", "DelAmarre", "DeLancey", "DeLascy", "DelAshmutt", "DeLassy", "DeLattre", "DeLaughter", "DeLay", "deLessine", "DelGado", "DelGaudio", "DeLiberti", "DeLoache", "DeLoatch", "DeLoch", "DeLockwood", "DeLong", "DeLozier", "DeLuca", "DeLucenay", "deLucy", "DeMars", "DeMartino", "deMaule", "DeMello", "DeMinck", "DeMink", "DeMoree", "DeMoss", "DeMott", "DeMuynck", "deNiet", "DeNure", "DePalma", "DePasquale", "dePender", "dePercy", "DePoe", "DePriest", "DePu", "DePui", "DePuis", "DeReeper", "deRochette", "deRose", "DeRossett", "DeRover", "deRuggele", "deRuggle", "DeRuyter", "deSaint-Sauveur", "DeSantis", "desCuirs", "DeSentis", "DeShane", "DeSilva", "DesJardins", "DesMarest", "deSoleure", "DeSoto", "DeSpain", "DeStefano", "deSwaert", "deSwart", "DeVall", "DeVane", "DeVasher", "DeVasier", "DeVaughan", "DeVaughn", "DeVault", "DeVeau", "DeVeault", "deVilleneuve", "DeVilliers", "DeVinney", "DeVito", "deVogel", "DeVolder", "DeVolld", "DeVore", "deVos", "DeVries", "deVries", "DeWall", "DeWaller", "DeWalt", "deWashington", "deWerly", "deWessyngton", "DeWet", "deWinter", "DeWitt", "DeWolf", "DeWolfe", "DeWolff", "DeWoody", "DeYager", "DeYarmett", "DeYoung", "DiCicco", "DiCredico", "DiFillippi", "DiGiacomo", "DiMarco", "DiMeo", "DiMonte", "DiNonno", "DiPietro", "diPilato", "DiPrima", "DiSalvo", "du Bosc", "du Hurst", "DuFort", "DuMars", "DuPre", "DuPue", "DuPuy", "FitzUryan", "kummel", "LaBarge", "LaBarr", "LaBauve", "LaBean", "LaBelle", "LaBerteaux", "LaBine", "LaBonte", "LaBorde", "LaBounty", "LaBranche", "LaBrash", "LaCaille", "LaCasse", "LaChapelle", "LaClair", "LaComb", "LaCoste", "LaCount", "LaCour", "LaCroix", "LaFarlett", "LaFarlette", "LaFerry", "LaFlamme", "LaFollette", "LaForge", "LaFortune", "LaFoy", "LaFramboise", "LaFrance", "LaFuze", "LaGioia", "LaGrone", "LaLiberte", "LaLonde", "LaLone", "LaMaster", "LaMay", "LaMere", "LaMont", "LaMotte", "LaPeer", "LaPierre", "LaPlante", "LaPoint", "LaPointe", "LaPorte", "LaPrade", "LaRocca", "LaRochelle", "LaRose", "LaRue", "LaVallee", "LaVaque", "LaVeau", "LeBleu", "LeBoeuf", "LeBoiteaux", "LeBoyteulx", "LeCheminant", "LeClair", "LeClerc", "LeCompte", "LeCroy", "LeDuc", "LeFevbre", "LeFever", "LeFevre", "LeFlore", "LeGette", "LeGrand", "LeGrave", "LeGro", "LeGros", "LeJeune", "LeMaistre", "LeMaitre", "LeMaster", "LeMesurier", "LeMieux", "LeMoe", "LeMoigne", "LeMoine", "LeNeve", "LePage", "LeQuire", "LeQuyer", "LeRou", "LeRoy", "LeSuer", "LeSueur", "LeTardif", "LeVally", "LeVert", "LoMonaco", "Macabe", "Macaluso", "MacaTasney", "Macaulay", "Macchitelli", "Maccoone", "Maccurry", "Macdermattroe", "Macdiarmada", "Macelvaine", "Macey", "Macgraugh", "Machan", "Machann", "Machum", "Maciejewski", "Maciel", "Mackaben", "Mackall", "Mackartee", "Mackay", "Macken", "Mackert", "Mackey", "Mackie", "Mackin", "Mackins", "Macklin", "Macko", "Macksey", "Mackwilliams", "Maclean", "Maclinden", "Macomb", "Macomber", "Macon", "Macoombs", "Macraw", "Macumber", "Macurdy", "Macwilliams", "MaGuinness", "MakCubyn", "MakCumby", "Mcelvany", "Mcsherry", "Op den Dyck", "Op den Graeff", "regory", "StElmo", "StGelais", "StJacques", "te Boveldt", "VanAernam", "VanAken", "VanAlstine", "VanAmersfoort", "VanAntwerp", "VanArlem", "VanArnam", "VanArnem", "VanArnhem", "VanArnon", "VanArsdale", "VanArsdalen", "VanArsdol", "vanAssema", "vanAsten", "VanAuken", "VanAwman", "VanBaucom", "VanBebber", "VanBeber", "VanBenschoten", "VanBibber", "VanBilliard", "vanBlare", "vanBlaricom", "VanBuren", "VanBuskirk", "VanCamp", "VanCampen", "VanCleave", "VanCleef", "VanCleve", "VanCouwenhoven", "VanCovenhoven", "VanCowenhoven", "VanCuren", "VanDalsem", "VanDam", "VanDe Poel", "vanden Dijkgraaf", "vanden Kommer", "VanDer Aar", "vander Gouwe", "VanDer Honing", "VanDer Hooning", "vander Horst", "vander Kroft", "vander Krogt", "VanDer Meer", "vander Meulen", "vander Putte", "vander Schooren", "VanDer Veen", "VanDer Ven", "VanDer Wal", "VanDer Weide", "VanDer Willigen", "vander Wulp", "vander Zanden", "vander Zwan", "VanDer Zweep", "VanDeren", "VanDerlaan", "VanDerveer", "VanderWoude", "VanDeursen", "VanDeusen", "vanDijk", "VanDoren", "VanDorn", "VanDort", "VanDruff", "VanDryer", "VanDusen", "VanDuzee", "VanDuzen", "VanDuzer", "VanDyck", "VanDyke", "VanEman", "VanEmmen", "vanEmmerik", "VanEngen", "vanErp", "vanEssen", "VanFleet", "VanGalder", "VanGelder", "vanGerrevink", "VanGog", "vanGogh", "VanGorder", "VanGordon", "VanGroningen", "VanGuilder", "VanGundy", "VanHaaften", "VanHaute", "VanHees", "vanHeugten", "VanHise", "VanHoeck", "VanHoek", "VanHook", "vanHoorn", "VanHoornbeeck", "VanHoose", "VanHooser", "VanHorn", "VanHorne", "VanHouten", "VanHoye", "VanHuijstee", "VanHuss", "VanImmon", "VanKersschaever", "VanKeuren", "VanKleeck", "VanKoughnet", "VanKouwenhoven", "VanKuykendaal", "vanLeeuwen", "vanLent", "vanLet", "VanLeuven", "vanLingen", "VanLoozen", "VanLopik", "VanLuven", "vanMaasdijk", "VanMele", "VanMeter", "vanMoorsel", "VanMoorst", "VanMossevelde", "VanNaarden", "VanNamen", "VanNemon", "VanNess", "VanNest", "VanNimmen", "vanNobelen", "VanNorman", "VanNormon", "VanNostrunt", "VanNote", "VanOker", "vanOosten", "VanOrden", "VanOrder", "VanOrma", "VanOrman", "VanOrnum", "VanOstrander", "VanOvermeire", "VanPelt", "VanPool", "VanPoole", "VanPoorvliet", "VanPutten", "vanRee", "VanRhijn", "vanRijswijk", "VanRotmer", "VanSchaick", "vanSchelt", "VanSchoik", "VanSchoonhoven", "VanSciver", "VanScoy", "VanScoyoc", "vanSeters", "VanSickle", "VanSky", "VanSnellenberg", "vanStaveren", "VanStraten", "VanSuijdam", "VanTassel", "VanTassell", "VanTessel", "VanTexel", "VanTuyl", "VanValckenburgh", "vanValen", "VanValkenburg", "VanVelsor", "VanVelzor", "VanVlack", "VanVleck", "VanVleckeren", "VanWaard", "VanWart", "VanWassenhove", "VanWinkle", "VanWoggelum", "vanWordragen", "VanWormer", "VanZuidam", "VanZuijdam", "VonAdenbach", "vonAllmen", "vonBardeleben", "vonBerckefeldt", "VonBergen", "vonBreyman", "VonCannon", "vonFreymann", "vonHeimburg", "VonHuben", "vonKramer", "vonKruchenburg", "vonPostel", "VonRohr", "VonRohrbach", "VonSass", "VonSasse", "vonSchlotte", "VonSchneider", "VonSeldern", "VonSpringer", "VonVeyelmann", "VonZweidorff");
            surnames = surnames.concat(suffixes); //Append suffixes array to the end of surnames
            var mc = /^Mc(\w)(?=\w)/i; //Regexp for "Mc"
            var mac = /^Mac(\w)(?=\w)/i; //Regexp for "Mac"
            var hyphen_index = [];
            var hyphen = false;
            while (name.indexOf('-') > -1) { //Loops to record positions of hyphens (to put them back later) and convert the hyphen to a space (to break up name into individual words)
                var index = name.indexOf('-');
                if (index === 0) { //If name begins with hypen, just remove the first character from the name and loop again
                    name = name.substr(1);
                    continue;
                }
                hyphen_index.push(index); //Record hyphen position
                name = name.substring(0, index) + ' ' + name.substr(index + 1); //Change hyphen to a space
                hyphen = true;
            }
            var names = name.split(' '); //Put individual words in name into an array
            for (var i = 0; i < names.length; i++) //Loop through words in name if they are all CAPS, make them all lowercase
            if (upperCase.test(names[i])) names[i] = names[i].toLowerCase();
            for (i = 0; i < names.length; i++) //Loop through words in name and capitalize the first letter
            names[i] = names[i].charAt(0).toUpperCase() + names[i].substr(1); //Change word to capitalized version
            for (i = 0; i < names.length; i++) { //Loop through words in name and check for "mcx" and "macx"
                if (mc.test(names[i])) //Look for "Mc" start of name word
                names[i] = "Mc" + names[i].charAt(2).toUpperCase() + names[i].substr(3); //Change word to capitalized version
                if (mac.test(names[i])) //Look for "Mac" start of name word
                names[i] = "Mac" + names[i].charAt(3).toUpperCase() + names[i].substr(4); //Change word to capitalized version
            }
            name = names.join(' '); //Join words of name back together
            if (hyphen) //Add hyphens back if they originally existed
            for (i = 0; i < hyphen_index.length; i++) //Cycle through hyphen index
            name = name.substr(0, hyphen_index[i]) + '-' + name.substr(hyphen_index[i] + 1); //Replace positions in name from hyphen index with hyphens
            var apos = name.indexOf("'");
            if (apos > -1) {
                var ucApos = name.substr(apos + 1, 1).toUpperCase();
                name = name.substr(0, apos + 1) + ucApos + name.substr(apos + 2);
            }
            name = name.replace(/ De /gi, ' de '); //Replace ' De ' with ' de '
            name = name.replace(/ Dit /gi, ' dit '); //Replace ' Dit ' with ' dit '
            name = name.replace(/ Van /gi, ' van '); //Replace ' Van ' with ' van '
            var lcName = name.toLowerCase(); //Copy of name in lower-case
            for (i = 0; i < surnames.length; i++) {
                var pos = lcName.indexOf(surnames[i].toLowerCase());
                if (pos > -1) {
                    if (((pos === 0) || (pos > 0 && name.charAt(pos - 1) == ' ')) && ((name.length == pos + surnames[i].length) || (name.charAt(pos + surnames[i].length) == ' '))) name = name.substring(0, pos) + surnames[i] + name.substr(pos + surnames[i].length);
                }
            }
            return name;	
        },
        comboAlerts(entry) {
            if (entry.comboAlert) return entry.comboAlert.split(";");
            return [];
        }
    }

}
</script>


