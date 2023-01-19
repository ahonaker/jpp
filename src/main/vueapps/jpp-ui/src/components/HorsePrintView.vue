<template>
    <b-container fluid :class="{pp: true, border: true, scratchedDetail: horse.scratchedFlag, 'text-center': horse.scratchedFlag}">
        <span v-if="horse.scratchedFlag" class="h4 text-white">{{horse.programNumber}} - {{horse.name}} is scratched</span>
        <b-row v-if="!horse.scratchedFlag">
            <b-col cols="4">
                <b-row>
                    <b-col cols="1">
                        <span class="h5"><strong>{{horse.programNumber}}</strong>
                        <span v-if="horse.postPosition != parseInt(horse.programNumber)" class="pp"><sup>pp{{horse.postPosition}}</sup></span>																							
                        </span><br>
                        <span class="h6">                               
                            <span>{{horse.mlodds}}</span>
                        </span><br>
                    </b-col>
                    <b-col>
                        <span class="h5"><strong>
                            <b-icon-star-fill 
                                variant="success" 
                                v-if="horse.flag == 'Star'"                                       
                            ></b-icon-star-fill>
                            {{horse.name}} ({{horse.runStyle}} {{horse.speedPoints}})</strong>&nbsp;
                            <span style="float:right;">
                                <span v-if="horse.claimingPrice">{{formatCurrency(horse.claimingPrice)}}<br></span>
                                <b-badge class="text-right" v-if="horse.mtoflag == 'M'" variant="dark">MTO</b-badge>
                                <b-badge v-if="horse.mtoflag == 'A'" variant="dark">AE</b-badge>
                            </span>
                        </span>
                        <br>
                        <strong>Own: {{horse.owner}}</strong><br>
                        {{horse.ownerSilks}}
                    </b-col>
                </b-row>
            </b-col>
            <b-col cols="3">
                <strong>
                    {{horse.color}}. {{horse.sex}}. {{horse.age}} ({{horse.foalingMonth}})  {{horse.auctionLocationDate}}  <span :class="horse.auctionPrice > 100000 && horse.lifetimeWins == 0 ? 'greenHighlight' : ''">{{formatCurrency(horse.auctionPrice)}}</span><br>
                    <b-row>
                        <b-col cols="2">
                            Sire :
                        </b-col>
                        <b-col>
                            {{horse.sire}} ({{horse.siresSire}}) {{formatCurrency(horse.sireStudFee)}}<br>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="2">
                            Dam  :  
                        </b-col>
                        <b-col>
                            {{horse.dam}} ({{horse.damsSire}})<br>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="2">
                            Brdr :  
                        </b-col>
                        <b-col>
                            {{horse.breeder}}<br>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="2">
                            Trnr :  
                        </b-col>
                        <b-col>
                            {{horse.trainer.name}}  ({{horse.trainer.currentMeetStarts}} {{horse.trainer.currentMeetWins}}-{{horse.trainer.currentMeetPlaces}}-{{horse.trainer.currentYearShows}} <span :class="(horse.trainer.currentMeetStarts == 0 ? 0 : (horse.trainer.currentMeetWins/horse.trainer.currentMeetStarts*100) >= 20 && horse.trainer.currentMeetStarts >= 20) ? 'greenHighlight' : ''">{{horse.trainer.currentMeetStarts == 0 ? 0 : (horse.trainer.currentMeetWins/horse.trainer.currentMeetStarts*100).toFixed(0)}}%</span>)
                        </b-col>
                    </b-row>																																	 
                </strong>
            </b-col>
            <b-col cols="1" class="text-right">
                <br><strong><span v-if="horse.equipment == 'BLINKERS_ON'">Blnkr On</span><span v-if="horse.equipment == 'BLINKERS_OFF'">Blnkr Off</span></strong>
                <br><strong><span v-if="horse.sex == 'g'"><b-badge pill variant="dark">G</b-badge></span><span v-if="horse.medication == 'LASIX_FIRST'"><b-badge variant="dark">L</b-badge></span><span v-if="horse.medication == 'LASIX'">L</span> {{horse.weight}}</strong>
            </b-col>
            <b-col cols="4">
                <b-row>
                    <b-col cols="5" class="border-right">
                        <b-row class="border-left border-bottom">
                            <b-col cols="2">
                                <strong>Life:</strong>  
                            </b-col>
                            <b-col cols="5">
                                {{horse.lifetimeStarts}}&nbsp;&nbsp;{{horse.lifetimeWins}}-{{horse.lifetimePlaces}}-{{horse.lifetimeShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(horse.lifetimeEarnings)}}
                            </b-col>
                            <b-col cols="2">
                                <strong>{{horse.lifetimeBestSpeed}}</strong>
                            </b-col>
                        </b-row>													
                        <b-row>
                            <b-col cols="2">
                                <strong>{{horse.currentYear}}</strong> 
                            </b-col>
                            <b-col cols="5">
                                {{horse.currentYearStarts}}&nbsp;&nbsp;{{horse.currentYearWins}}-{{horse.currentYearPlaces}}-{{horse.currentYearShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(horse.currentYearEarnings)}}
                            </b-col>
                            <b-col cols="2">
                                <strong>{{horse.currentYearBestSpeed}}</strong>
                            </b-col>										
                        </b-row>	
                        <b-row>
                            <b-col cols="2">
                                <strong>{{horse.previousYear}}</strong>  
                            </b-col>
                            <b-col cols="5">
                                {{horse.previousYearStarts}}&nbsp;&nbsp;{{horse.previousYearWins}}-{{horse.previousYearPlaces}}-{{horse.previousYearShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(horse.previousYearEarnings)}}
                            </b-col>
                            <b-col cols="2">
                                <strong>{{horse.previousYearBestSpeed}}</strong>
                            </b-col>													
                        </b-row>	
                        <b-row>
                            <b-col cols="2">
                                <strong>{{horse.currentTrack}}</strong> 
                            </b-col>
                            <b-col cols="5" :class="horse.trackWins > 0 || horse.trackPlaces > 2 ? 'greenHighlight' : ''">
                                {{horse.trackStarts}}&nbsp;&nbsp;{{horse.trackWins}}-{{horse.trackPlaces}}-{{horse.trackShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(horse.trackEarnings)}}
                            </b-col>
                            <b-col cols="2">
                                <strong>{{horse.trackBestSpeed}}</strong>
                            </b-col>													
                        </b-row>
                    </b-col>
                    <b-col cols="7">
                        <b-row>
                            <b-col cols="4">
                                <strong>Fst <span v-if="horse.pedigreeRatingDirt">({{horse.pedigreeRatingDirt}})</span></strong>  
                            </b-col>
                            <b-col cols="3">
                                {{horse.dirtStarts}}&nbsp;{{horse.dirtWins}}-{{horse.dirtPlaces}}-{{horse.dirtShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(horse.dirtEarnings)}}
                            </b-col>
                            <b-col cols="1">
                                <strong>{{horse.dirtBestSpeed}}</strong>
                            </b-col>
                        </b-row>													
                        <b-row>
                            <b-col cols="4" >
                                <strong>Off <span v-if="horse.pedigreeRatingMud">({{horse.pedigreeRatingMud}})</span></strong> 
                            </b-col>
                            <b-col cols="3" :class="race.trackCondition != 'ft' && race.trackCondition != 'fm' && (horse.wetWins > 0 || horse.wetPlaces > 2) ? 'greenHighlight' : ''">
                                {{horse.wetStarts}}&nbsp;{{horse.wetWins}}-{{horse.wetPlaces}}-{{horse.wetShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(horse.wetEarnings)}}
                            </b-col>
                            <b-col cols="1">
                                <strong>{{horse.wetBestSpeed}}</strong>
                            </b-col>										
                        </b-row>	
                        <b-row>
                            <b-col cols="4">
                                <strong>Dis  <span v-if="horse.pedigreeRatingDist">({{horse.pedigreeRatingDist}})</span></strong>  
                            </b-col>
                            <b-col cols="3">
                                {{horse.distanceStarts}}&nbsp;{{horse.distanceWins}}-{{horse.distancePlaces}}-{{horse.distanceShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(horse.distanceEarnings)}}
                            </b-col>
                            <b-col cols="1">
                                <strong>{{horse.distanceBestSpeed}}</strong>
                            </b-col>													
                        </b-row>	
                        <b-row>
                            <b-col cols="4">
                                <strong>Trf  <span v-if="horse.pedigreeRatingTurf">({{horse.pedigreeRatingTurf}})</span></strong> 
                            </b-col>
                            <b-col cols="3">
                                {{horse.turfStarts}}&nbsp;{{horse.turfWins}}-{{horse.turfPlaces}}-{{horse.turfShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(horse.turfEarnings)}}
                            </b-col>
                            <b-col cols="1">
                                <strong>{{horse.turfBestSpeed}}</strong>
                            </b-col>													
                        </b-row>
                        <b-row>
                            <b-col cols="4">
                                <strong>AW</strong> 
                            </b-col>
                            <b-col cols="3">
                                {{horse.allWeatherStarts}}&nbsp;{{horse.allWeatherWins}}-{{horse.allWeatherPlaces}}-{{horse.allWeatherShows}}<br>
                            </b-col>
                            <b-col cols="3">
                                {{formatCurrency(horse.allWeatherEarnings)}}
                            </b-col>
                            <b-col cols="1">
                                <strong>{{horse.allWeatherBestSpeed}}</strong>
                            </b-col>													
                        </b-row>																																																		
                    </b-col>
                    <b-col>

                    </b-col>
                </b-row>
            </b-col>

        </b-row>
        <b-row v-if="!horse.scratchedFlag">
            <b-col>
                {{properize(horse.jockey.name)}} ({{horse.jockey.currentMeetStarts}} {{horse.jockey.currentMeetWins}}-{{horse.jockey.currentMeetPlaces}}-{{horse.jockey.currentMeetShows}}<span :class="(horse.jockey.currentMeetStarts == 0 ? 0 : (horse.jockey.currentMeetWins/horse.jockey.currentMeetStarts*100) >= 20 && horse.jockey.currentMeetStarts >= 20) ? 'greenHighlight' : ''"> {{horse.jockey.currentMeetStarts == 0 ? 0 : (horse.jockey.currentMeetWins/horse.jockey.currentMeetStarts*100).toFixed(0)}}%)</span><br>
            </b-col>
        </b-row>
        <b-row v-if="!horse.scratchedFlag">
            <b-col cols="3">
                <b-row>
                    <b-col cols="4">
                        {{horse.currentYear}}
                    </b-col>
                    <b-col cols="2">
                        {{horse.jockey.currentYearStarts}}
                    </b-col>
                    <b-col :class="horse.jockey.currentYearStarts >= 20 && horse.trainer.currentYearWins/horse.jockey.currentYearStarts*100 >= 20 ? 'greenHighlight' : ''">
                        {{horse.jockey.currentYearStarts == 0 ? 0 : (horse.jockey.currentYearWins/horse.jockey.currentYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{horse.jockey.currentYearStarts == 0 ? 0 : ((horse.jockey.currentYearWins + horse.jockey.currentYearPlaces + horse.jockey.currentYearShows)/horse.jockey.currentYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{horse.jockey.currentYearROI}}
                    </b-col>											
                </b-row>											
                <b-row>
                    <b-col cols="4">
                        {{horse.previousYear}}
                    </b-col>
                    <b-col cols="2">
                        {{horse.jockey.previousYearStarts}}
                    </b-col>
                    <b-col>
                        {{horse.jockey.previousYearStarts == 0 ? 0 : (horse.jockey.previousYearWins/horse.jockey.previousYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{horse.jockey.previousYearStarts == 0 ? 0 : ((horse.jockey.previousYearWins + horse.jockey.previousYearPlaces + horse.jockey.previousYearShows)/horse.jockey.previousYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{horse.jockey.previousYearROI}}
                    </b-col>											
                </b-row>
                <b-row v-for="stat in horse.jockey.stats" :key="stat.category">
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
                        {{horse.currentYear}}
                    </b-col>
                    <b-col cols="2">
                        {{horse.trainer.currentYearStarts}}
                    </b-col>
                    <b-col :class="horse.trainer.currentYearStarts >= 20 && horse.trainer.currentYearWins/horse.trainer.currentYearStarts*100 >= 20 ? 'greenHighlight' : ''">
                        {{horse.trainer.currentYearStarts == 0 ? 0 : (horse.trainer.currentYearWins/horse.trainer.currentYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{horse.trainer.currentYearStarts == 0 ? 0 : ((horse.trainer.currentYearWins + horse.trainer.currentYearPlaces + horse.trainer.currentYearShows)/horse.trainer.currentYearStarts*100).toFixed(0)}}%
                    </b-col>
                    <b-col>
                        {{horse.trainer.currentYearROI}}
                    </b-col>											
                </b-row>											
                <b-row v-for="stat in horse.trainer.trainerStats" :key="stat.category">
                    <b-col cols="4">
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
                <b-row v-if="horse.sireAWD">
                    <b-col cols="3">
                        Sire Stats: AWD {{horse.sireAWD}} 
                    </b-col>
                    <b-col>
                        {{horse.sireMudPercent}}%Mud
                    </b-col>
                    <b-col>
                        {{horse.sireMudStarts}}MudSts
                    </b-col>
                    <b-col v-if="horse.sireTurfPercent > 0">
                        {{horse.sireTurfPercent}}%Turf
                    </b-col>
                    <b-col v-if="horse.sireFirstTurfPercent > 0">
                        {{horse.sireFirstTurfPercent}}%1stT
                    </b-col>
                    <b-col v-if="horse.sireFirstPercent > 0">
                        {{horse.sireFirstPercent}}%1st
                    </b-col>
                    <b-col>
                        {{format2Places(horse.sireSPI)}}spi
                    </b-col>
                </b-row>
                <b-row v-if="horse.damSireAWD" :class="horse.damDescription ? '' : 'mb-1'">
                    <b-col cols="3">
                        Dam's Sire: AWD {{horse.damSireAWD}} 
                    </b-col>
                    <b-col>
                        {{horse.damSireMudPercent}}%Mud
                    </b-col>
                    <b-col>
                        {{horse.damSireMudStarts}}MudSts
                    </b-col>
                        <b-col v-if="horse.damSireTurfPercent > 0">
                        {{horse.damSireTurfPercent}}%Turf
                    </b-col>
                    <b-col v-if="horse.damSireFirstTurfPercent > 0">
                        {{horse.damSireFirstTurfPercent}}%1stT
                    </b-col>                           
                    <b-col v-if="horse.damSireFirstPercent > 0">
                        {{horse.damSireFirstPercent}}%1st
                    </b-col>
                    <b-col>
                        {{format2Places(horse.damSireSPI)}}spi
                    </b-col>
                </b-row>
                <b-row v-if="horse.damDescription" class="mb-1">
                    <b-col cols="3">
                        Dam's Stats: {{horse.damDescription}} 
                    </b-col>
                    <b-col v-if="horse.damTurfWinners">
                        {{horse.damTurfWinners}}trfW
                    </b-col>                            
                    <b-col v-if="horse.damTwoYearOldPercent">
                        {{horse.damTwoYearOldPercent}}%2yo
                    </b-col>
                    <b-col>
                        {{horse.damStarters}}str
                    </b-col>
                    <b-col>
                        {{horse.damWinners}}w
                    </b-col>
                        <b-col>
                        {{horse.damStakesWinners}}sw
                    </b-col>                           
                    <b-col>
                        {{format2Places(horse.damDPI)}}dpi
                    </b-col>
                </b-row>                                               
                <b-row v-for="(angle,ndx) in horse.angles" :key="ndx">
                    <b-col>
                        <span :class="'font-weight-bold ' + (angle.charAt(0) == '+' ? 'text-success' : (angle.charAt(0) == '-' ? 'text-danger' : 'text-warning'))">{{angle.substring(1)}}</span>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>     
        <b-row v-if="!horse.scratchedFlag" class="mb-2">
            <span v-if="horse.flag == 'Star'"><b-icon-star-fill variant="success"></b-icon-star-fill> {{horse.comment}}<br></span>        
            {{horse.note}}
        </b-row>
        <horse-extra-view v-if="horse.pastPerformances.length > 3 && !horse.scratchedFlag" :horse="horse"></horse-extra-view>      
        <b-row class="table b-table table-sm">       
            <past-performance-view v-if="!horse.scratchedFlag" :horse="horse" :race="race" :charts="charts"></past-performance-view>
        </b-row>
        <b-row v-if="!horse.scratchedFlag">
            <span v-for="workout in horse.workouts" :key="workout.date">
                <workout-view :workout="workout"></workout-view>
            </span>		
        </b-row>								
    </b-container>									
</template>

<script>
import { BIconStarFill } from 'bootstrap-vue'
import PastPerformanceView from '@/components/PastPerformanceView'
import WorkoutView from '@/components/WorkoutView'
import HorseExtraView from '@/components/HorseExtraView'

import _ from 'underscore'

export default {
    name: 'HorsePrintView',
    components: {
		PastPerformanceView, WorkoutView, HorseExtraView, BIconStarFill
    },
    props : ['race', 'horse', 'hideML', 'charts'],
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
		alsoInRace(nameToMatch, row) {
			var names = _.pluck(this.race.horses, "name");
			if (names.indexOf(nameToMatch) > -1 && nameToMatch != row.name) return "alsoInRace";

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
        }
    }

}
</script>


