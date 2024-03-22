<template>
    <b-table
        v-if="entry.pastPerformances.length > 0"
        :items="entry.pastPerformances"
        :fields="ppFields"
        :tbody-tr-class="ppRowClass"
        @row-clicked="toggleIgnored"
    >
        <template #head()="data">
            {{ data.label }}
        </template>  
        <template #cell(raceDateString)="row">
            {{row.item.raceDateString}}<sup>{{row.item.raceNumber}}</sup>
            &nbsp;
            <span v-if="row.item.keyRace != null">
                <b-icon-key variant="success" font-scale="2" rotate="90"></b-icon-key>&nbsp;
            </span>
        </template>    
        <template #cell(dist)="row">
            <span v-if="row.item.distance<0"><sup>*</sup></span>
            <span v-if="Math.abs(row.item.miles)==1.5">1&frac12;m</span>
            <span v-else-if="Math.abs(row.item.distance)==1800">1<sup>40</sup>m</span>
            <span v-else-if="Math.abs(row.item.distance)==1830">1<sup>70</sup>m</span>
            <span v-else-if="Math.abs(row.item.miles)==1.375">1&frac38;m</span>
            <span v-else-if="Math.abs(row.item.miles)==1.25">1&frac14;m</span>
            <span v-else-if="Math.abs(row.item.miles)==1.125">1&frac18;m</span>
            <span v-else-if="Math.abs(row.item.miles)==1.0625">1<sup>1</sup>&frasl;<sub>16</sub>m</span>
            <span v-else-if="Math.abs(row.item.miles)==1.1875">1<sup>3</sup>&frasl;<sub>16</sub>m</span>
            <span v-else-if="Math.abs(row.item.miles)==1.3125">1<sup>5</sup>&frasl;<sub>16</sub>m</span>
            <span v-else-if="Math.abs(row.item.miles)==1.4375">1<sup>7</sup>&frasl;<sub>16</sub>m</span>
            <span v-else-if="Math.abs(row.item.miles)==1">1m</span>
            <span v-else-if="Math.abs(row.item.furlongs)==4.5">4&frac12;f</span>
            <span v-else-if="Math.abs(row.item.furlongs)==5.5">5&frac12;f</span>
            <span v-else-if="Math.abs(row.item.furlongs)==6.5">6&frac12;f</span>
            <span v-else-if="Math.abs(row.item.furlongs)==7.5">7&frac12;f</span>
            <span v-else-if="Math.abs(row.item.miles) > 1.47 && Math.abs(row.item.miles) < 1.52"><sup>*</sup>1&frac12;m</span>
            <span v-else-if="Math.abs(row.item.miles) > 1.22 && Math.abs(row.item.miles) < 1.27"><sup>*</sup>1&frac14;m</span>
            <span v-else>{{Math.abs(row.item.furlongs)}}f</span>
            <span v-if="row.item.oneTurn">&nbsp;&nbsp;<b-icon-circle-half></b-icon-circle-half></span>
        </template>
        <template #cell(surface)="row">
            <span v-if="row.item.surface == 'INNER_DIRT'"><b-badge variant="secondary"><b-icon-circle-fill></b-icon-circle-fill></b-badge></span>
            <span v-if="row.item.surface == 'TURF'"><b-badge pill variant="secondary">&nbsp;T&nbsp;</b-badge></span>
            <span v-if="row.item.surface == 'INNER_TURF'"><b-badge variant="secondary">&nbsp;T&nbsp;</b-badge></span>
            <span v-if="row.item.allWeatherSurfaceFlag == 'A'"><b-badge pill variant="secondary">&nbsp;A&nbsp;</b-badge></span>
            <span v-if="row.item.offTheTurfFlag"><b-badge pill variant="secondary">&nbsp;X&nbsp;</b-badge></span>
        </template>
        <template #cell(trainerAndClaimFlag)="row">
            <b-badge pill v-if="row.item.claimedCode != 'c' && row.item.claimingPrice > 0" variant="danger">C</b-badge>
            <b-badge pill v-if="row.item.claimedCode == 'c'" variant="success">C</b-badge>
            <b-badge pill v-if="row.item.trainerChangeDate" variant="success">T</b-badge>
             {{shortenName(row.item.trainer)}}
        </template>
        <template #cell(fraction1)="row">
            <span v-if="row.item.fraction1 != 0" v-b-tooltip.hover :title="'LDR: ' + Math.floor(row.item.fraction1 / 60) + ':' + format2Places(row.item.fraction1 - Math.floor(row.item.fraction1 / 60) * 60)">
            {{format2Places(row.item.calcFraction1)}}</span><br>
        </template>									
        <template #cell(fraction2)="row">
            <span v-if="row.item.fraction2 != 0">
                {{format2Places(row.item.split1)}}
            </span>
        </template>									
        <template #cell(fraction3)="row">
            <span v-if="row.item.calcFraction3 != 0">
                {{format2Places(row.item.split2)}}
            </span>
        </template>
        <template #cell(finalTime)="row">
            <span v-b-tooltip.hover :title="Math.floor(row.item.calcFinalTime / 60) + ':' + str_pad_left(Math.floor(row.item.calcFinalTime - Math.floor(row.item.calcFinalTime / 60) * 60),'0',2) + '.' + ((row.item.calcFinalTime - Math.floor(row.item.calcFinalTime)) * 100).toFixed(0)
            + ' (LDR : '
            + Math.floor(row.item.finalTime / 60) + ':' +  str_pad_left(Math.floor(row.item.finalTime - Math.floor(row.item.finalTime / 60) * 60),'0',2) + '.' + str_pad_left(((row.item.finalTime - Math.floor(row.item.finalTime)) * 100).toFixed(0),'0',2) + ')'">
            {{format2Places(row.item.split3)}}</span>
        </template>
        <template #cell(ageRange)="row">
            <span v-if="row.item.ageRestriction == 'TWO_YEAR_OLDS'">2</span>
            <span v-else-if="row.item.ageRestriction == 'THREE_YEAR_OLDS'">3</span>
            <span v-else-if="row.item.ageRestriction == 'FOUR_YEAR_OLDS'">3</span>
            <span v-if="row.item.ageRestrictionRange == 'THAT_AGE_AND_UP'"><b-icon-caret-up-fill></b-icon-caret-up-fill></span>
        </template>
        <template #cell(raceClassification)="row">
            <b-badge pill variant="info" v-b-tooltip.hover :title="row.item.raceType">{{raceTypeCode(row.item.raceType)}}</b-badge>&nbsp;
            <span v-if="row.item.raceClassification.substring(0,1) == 's'"><b-badge variant="secondary">S</b-badge>&nbsp;{{row.item.raceClassification.substring(1)}}</span>
            <span v-else-if="row.item.raceClassification.substring(0,1) == 'f'"><b-badge variant="secondary">F</b-badge>&nbsp;{{row.item.raceClassification.substring(1)}}</span>
            <span v-else-if="row.item.raceClassification.substring(0,1) == 'r'"><b-badge variant="secondary">R</b-badge>&nbsp;{{row.item.raceClassification.substring(1)}}</span>
            <span v-else>{{row.item.raceClassification}}</span>
        </template>
        <template #cell(raceStrength)="row">
            <span v-if="row.item.raceStrength != 0">{{format2Places(row.item.raceStrength)}}</span>
        </template>        
        <template #cell(e1)="row">
            <span v-if="row.item.e1 != 0">
                <span :class="highlightPace(row.value, row.key, row.item)">{{row.item.e1}}</span>
                <span :class="highlightRaceShape(row.value, row.key, row.item)"><br><span v-if="row.item.raceShapeFirstCall > 0">+</span>{{row.item.raceShapeFirstCall}}</span>
            </span>
        </template>        
        <template #cell(e2)="row">
            <span v-if="row.item.e2 != 0">
                <span :class="highlightPace(row.value, row.key, row.item)">{{row.item.e2}}/</span>
                <span :class="highlightRaceShape(row.value, row.key, row.item)"><br><span v-if="row.item.raceShapeFirstCall > 0">+</span>{{row.item.raceShapeFirstCall}}</span>
            </span>
        </template>
        <template #cell(paceFigureLate)="row">
            <span v-if="row.item.paceFigureLate != 0">{{row.item.paceFigureLate}}</span>
        </template>	
         <template #cell(brisspeedRating)="row">
            <span :class="highlightSpeedInRange(row.value, row.key, row.item)">
                <span v-if="row.item.brisspeedRating != 0">{{row.item.brisspeedRating}}</span>
                <span v-if="row.item.speedPar != 0" :class="{'text-success font-weight-bold': race && row.item.speedPar >= race.parSpeed}"> / {{row.item.speedPar}}
            </span>
            <span :class="highlightRaceShape2(row.value, row.key, row.item)"><br>{{row.item.raceShape}}</span></span>
        </template>						
        <template #cell(speedRating)="row">
            <span v-if="row.item.speedRating != 0">
                {{row.item.speedRating}}-{{row.item.trackVariant}}
                <b-icon-arrow-up v-if="row.item.trackVariant<16"></b-icon-arrow-up>
                <b-icon-arrow-down v-if="row.item.trackVariant>19"></b-icon-arrow-down>
            </span>
        </template>	   
         <template #cell(postPosition)="row">
            <span v-if="row.item.postPosition != 0">{{row.item.postPosition}}</span>
        </template>            			
        <template #cell(firstCallPosition)="row">
            <span v-if="row.item.firstCallPosition != 0" :class="{'font-weight-bold': row.item.firstCallPosition == 1}">{{row.item.firstCallPosition}}<sup>{{row.item.firstCallBeatenLengthsLeader.toFixed(2)}}</sup></span>
        </template>	
        <template #cell(secondCallPosition)="row">
            <span v-if="row.item.secondCallPosition != 0" :class="{'font-weight-bold': row.item.secondCallPosition == 1}">{{row.item.secondCallPosition}}<sup>{{row.item.secondCallBeatenLengthsLeader.toFixed(2)}}</sup></span>
        </template>
        <template #cell(stretchPosition)="row">
           <span v-if="row.item.stretchPosition != 0" :class="{'font-weight-bold': row.item.stretchPosition == 1}"> {{row.item.stretchPosition}}<sup>{{row.item.stretchBeatenLengthsLeader.toFixed(2)}}</sup></span>
        </template>	
        <template #cell(finishPosition)="row">
            <span :class="highlightFinish(row.value, row.key, row.item)">
                <span v-if="row.item.extraCommentLine.substring(0,1) == '(' || row.item.extraCommentLine == 'Dead heat'" class="text-danger">*</span>{{row.item.finishPosition}}<sup>{{row.item.finishBeatenLengthsLeader.toFixed(2)}}</sup>
            </span>
            <br><span :class="highlightBeatenFavorite(row.value, row.key, row.item)"><span v-if="row.item.favoriteFlag > 0"><sup>*</sup></span>{{format2Places(row.item.odds)}}</span>
        </template>	
        <template #cell(jockey)="row">	
            {{shortenName(row.item.jockey)}}<sup>{{row.item.weight}}</sup>
        </template>		
        <template #cell(medication)="row">	
            {{row.item.equipment}}<span v-if="row.item.medication == 'LASIX'">L</span>
        </template>																		
        <template #cell(finishers)="row">	
            <span :class="alsoInRace(row.item.winnersName, row.item)">{{row.item.winnersName}}</span><sup>{{row.item.winnersMargin.toFixed(2)}}</sup>
            <span :class="alsoInRace(row.item.placeName, row.item)">{{row.item.placeName}}</span><sup>{{row.item.placeMargin.toFixed(2)}}</sup>
            <span :class="alsoInRace(row.item.showName, row.item)">{{row.item.showName}}</span><sup>{{row.item.showMargin.toFixed(2)}}</sup>
        </template>		
        <template #cell(tripComment)="row">	
            <span>{{row.item.tripComment}}</span>
        </template>	
    </b-table>   
</template>

<script>
import { BIconCircleFill, BIconArrowDown, BIconArrowUp, BIconCaretUpFill, BIconKey, BIconCircleHalf } from 'bootstrap-vue'

import axios from 'axios'
import _ from 'underscore'
import moment from 'moment'

export default {
    name: 'PastPerformancePrintView',
    components: {
		BIconCircleFill, BIconArrowDown, BIconArrowUp, BIconCaretUpFill, BIconKey, BIconCircleHalf
    },
    props : ['entry', 'race', 'tracks'],
    data () {
		return {
            ppFields: [
				{key: "raceDateString", label: "DATE"},
				{key: "daysSinceLastRace", label: "LR", title: "Days Since Last Race", tdClass: this.highlightDaysSincePP},
				{key: "trackCode", label: "TRK", title: "Track"},
				{key: "surface", label: "", tdClass: this.highlightSimilarSurfDist},
				{key: "dist", label: "DIST", title: "Distance", tdClass: this.highlightSimilarSurfDist},
				{key: "trackCondition", label: "", tdClass: this.highlightSimilarSurfDist},
				{key: "fraction1", label: "1C", title: "1st Call Fraction"},
				{key: "fraction2", label: "2C", title: "2nd Call Fraction"},
				{key: "fraction3", label: "STR", title: "Stretch Call Fraction"},
				{key: "finalTime", label: "FIN", title: "Final Fraction", tdClass: this.highlightFinalFraction},
				{key: "raceStrength", label: "RS", title: "Race Strength (Winner's Speed Figure)", formatter: this.format2Places, tdClass: this.highlightRaceStrength},
                {key: "ageRange", label: ""},
				{key: "raceClassification", label: "RACETYPE", title: "Race Classification"},
				{key: "purse", tdClass: this.highlightPurseShift},
                {key: "trainerAndClaimFlag", label: "Trnr"},
				{key: "e1", title: "1st Call Pace Rating (Start to 2F in sprints, 4F in routes)"},
				{key: "e2", label: "E2/", title: "2nd Call Pace Rating (Start to 4F in sprints, 6F in most routes)"},
				{key: "paceFigureLate", label: "LP", title: "Late Pace Rating (2nd Call to Finish)", tdClass: this.highlightPace},
				//{key: "raceShapeFirstCall", title: "1st Call Race Shape (leader's pace compared to average)", label: "1c", tdClass: this.highlightRaceShape},
				//{key: "raceShapeSecondCall", title: "2nd Call Race Shape (leader's pace compared to average)", label: "2c", tdClass: this.highlightRaceShape},
				{key: "brisspeedRating", label: "SPD", title: "BRIS Speed Rating and Race Par", class: "strong"},
                //{key: "raceShape", label: "Shp", title: "Race Shape (1st Call Pace and Final Speed - e.g., FF is Fast/Fast, AS is Average/Slow)", tdClass: this.highlightRaceShape2},
                //{key: "speedRating", title: "DRF Speed Rating and Track Variant", label: "SR-TV"},
                {key: "postPosition", title: "Post Position", label: "PP"},
				{key: "startCallPosition", title: "Start Call Position", label: "ST"},
				{key: "firstCallPosition", title: "First Call Position", label: "1C"},
				{key: "secondCallPosition", title: "Second Call Position", label: "2C"},
				{key: "stretchPosition", title: "Stretch Call Position", label: "STR"},
				{key: "finishPosition", title: "Finish Position", label: "FIN"},
				{key: "jockey", label: "JOCKEY"},
				{key: "medication", label: ""},
				//{key: "odds", label: "ODDS", tdClass: this.highlightBeatenFavorite},
				{key: "finishers", label: "Top Finishers"},
				{key: "tripComment", label: "Comment"},
				{key: "numberOfEntrants", label: ""}
			],
        }
    },
    computed : {

    },
    methods:  {
		async toggleIgnored(item) {
            try {
                if (!this.race) return;
                var formData = new FormData();
                formData.append("raceNumber", this.race.raceNumber);
                formData.append("name", this.entry.name);
                formData.append("year", moment(item.raceDate).year());
                formData.append("month", moment(item.raceDate).month()+1);
                formData.append("day", moment(item.raceDate).date());
                await axios({
                    url: 'toggleIgnored',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                }); 
                item.ignore = !item.ignore;
            } catch (err) {
                console.log(err.response);
                
            }
		},			
		ppRowClass(item, type) {
			if (!item || type !== 'row') return;
			var c = "";
			
			if (item.over365Days) c += "diff365";
			if (item.over90Days && !item.over365Days) c += "diff90";
			
			if (item.ignore) c += " table-dark ignored";
			
			return c;
		},
		fractionString(decimal) {
			switch(decimal) {
				case 0.13:
					return "hd";
				case 0.25:
					return "&frac14;";
				case 0.5:
					return "&frac12;";
				case 0.75:
					return "&frac34;";
			}
		},
		shortenName(value) {
            var words = this.properize(value.replace("*","")).split(" ");
            var ret = words[0];
			for (var i = 1; i< words.length; i++) {
                ret+=words[i].charAt(0);
            }
            return ret;
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
		format2Places(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		} ,
		formatInt(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
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
        formatDate (date) {
            return new Date(date).toLocaleDateString;
			//return date[1] + "/" + date[2] + "/" + date[0];
		},	
		str_pad_left(string,pad,length) {
			return (new Array(length+1).join(pad)+string).slice(-length);
		},	
		highlightDaysSincePP(value, key , item) {
			if (item.daysSinceLastRace  > 180) return "gt180";
			if (item.daysSinceLastRace > 90) return "gt90";
			if (item.daysSinceLastRace > 45) return "gt45";
		},	
		highlightFinish(value, key, item) {
			if (item.finishPosition == "1") return "greenHighlight";
			if (item.finishPosition == "2" || item.finishPosition == "3") return "lightGreenHighlight";
		},	
		highlightBeatenFavorite(value,key,item) {
			if (item.favoriteFlag && item.finishPosition != "1") return "yellowHighlight";
		},
		highlightPurseShift(value,key,item) {
            if (!this.race) return;
			if (item.purse >= this.race.purse + 20000 ) return "greenHighlight";
			if (item.purse > this.race.purse) return "lightGreenHighlight";
			if (item.purse == this.race.purse) return "";
			if (item.purse < this.race.purse - 10000) return "yellowHighlight";
			if (item.purse < this.race.purse - 20000) return "lightRedHighlight";
			if (item.purse < this.race.purse - 30000) return "redHighlight";
		},
		highlightSpeedInRange(value,key,item) {
            if (!this.race) return;
			if (item.brisspeedRating >= this.race.parSpeed) return "greenHighlight";
			if (item.brisspeedRating >= this.race.parSpeed - 3) return "lightGreenHighlight";
		},
        highlightRaceStrength(value,key,item) {
            if (!this.race) return;
            if (item.raceStrength >= this.race.parSpeed - 2) return "greenHighlight";
        },
        highlightPace(value,key,item) {
            if (!this.race) return;
            if (item.e1 >= (this.race.furlongs < 8 ? this.race.parPace2F : this.race.parPace4F) -2 
                && item.e2 >= (this.race.furlongs < 8 ? this.race.parPace4F : this.race.parPace6F) -2 
                && item.paceFigureLate >= this.race.parLatePace -2)
                    return "greenHighlight"
        },
        highlightRaceShape(value, key, item) {
            if (item.raceShapeFirstCall > 9 && item.raceShapeSecondCall > 9 &&
                this.entry.runStyle == 'E') return 'yellowHighlight';
            if (item.raceShapeFirstCall > 9 && item.raceShapeSecondCall > 9 &&
                (this.entry.runStyle == 'P' || this.entry.runStyle == 'S')) return 'greenHighlight';
            if (item.raceShapeFirstCall < -9 && item.raceShapeSecondCall < -9 &&
                this.entry.runStyle == 'E') return 'greenHighlight';
            if (item.raceShapeFirstCall < -9 && item.raceShapeSecondCall < -9 &&
                (this.entry.runStyle == 'P' || this.entry.runStyle == 'S')) return 'yellowHighlight';                
        },
        highlightRaceShape2(value,key,item) {
            switch (item.raceShape) {
                case "FF":
                    if ((!isNaN(item.finishPosition) && item.finishPosition <= 3) || item.finishBeatenLengthsOnly < 2) return "greenHighlight";
                    return;
                case "SS":
                    return "text-info strong";
                case "SF":
                    if (item.finishPosition == "1" && (this.entry.runStyle == "E" || this.entry.runstyle == "E/P")) return "yellowHighlight";
                    if (item.finishPosition == "1" && (this.entry.runStyle == "P" || this.entry.runstyle == "S")) return "greenHighlight"; 
                    return;
                case "FS":
                    if (item.finishPosition == "1" && (this.entry.runStyle == "P" || this.entry.runstyle == "S")) return "yellowHighlight"; 
                    return;
            }
        },
        highlightFinalFraction(value,key,item) {
            if (!this.race) return;
            if (this.race.turfFlag && this.race.furlongs >= 8) {
                var final;
                var target;

                // mile
                if (
                        Math.abs(item.furlongs) == 8 
                        || (Math.abs(item.distance) >=1800 && Math.abs(item.distance) <= 1830)
                        || Math.abs(item.furlongs) == 10
                        || Math.abs(item.furlongs) == 12
                        ) {
                    final = item.split3;
                    target = 23.5;
                }  else if (
                        Math.abs(item.furlongs) == 8.5
                        ) {
                    final = item.split3;
                    target = 29.5;
                } else if (
                        Math.abs(item.furlongs) == 9
                        || Math.abs(item.furlongs) == 9.5
                        || Math.abs(item.furlongs) == 11
                        ) {
                    final = item.split2 + item.split3;
                    target = 35.5;                   
                }
                if (item.raceType.substring(0,3) == "MAID") target += .4;
                return (final < target ? "greenHighlight" : "");
            }
        },
		highlightShift(value) {
			if (value < 0) return "greenHighlight";
			if (value > 0) return "yellowHighlight";
		},
		highlightSimilarSurfDist(value, key, item) {
            if (!this.race) return;
			if (this.race.surface.indexOf("TURF") > -1 && !this.race.offTheTurfFlag) {
				if (
					item.surface.indexOf("TURF") > -1 
					&& ((this.race.trackCondition == "fm" && item.trackCondition == "fm") || (this.race.trackCondition != "fm" && ["tf","gd","gf","hd","yl","sf","hy","sl"].indexOf(item.trackCondition) > -1))
					&& ((Math.abs(this.race.furlongs) < 8 && Math.abs(item.furlongs) < 8) || (Math.abs(this.race.furlongs) >= 8 && Math.abs(item.furlongs) >= 8))) 
					return "blueHighlight";
			} else {
				if ((item.surface.indexOf("TURF") == -1 || item.offTheTurfFlag)
					&& ((this.race.trackCondition == "ft" && item.trackCondition == "ft") || (this.race.trackCondition != "ft" && ["gd","hy","my","sl","sy","wf"].indexOf(item.trackCondition) > -1))
					&& ((Math.abs(this.race.furlongs) < 8 && Math.abs(item.furlongs) < 8) || (Math.abs(this.race.furlongs) >= 8 && Math.abs(item.furlongs) >= 8))
                    && this.race.allWeatherSurfaceFlag == item.allWeatherSurfaceFlag) 
					return "blueHighlight";
			}
		},
		alsoInRace(nameToMatch, row) {
            if (!this.race) return;
			var names = _.pluck(this.race.entries, "name");
			if (names.indexOf(nameToMatch) > -1 && nameToMatch != row.name) return "alsoInRace";

		},
        raceTypeCode(type) {
            switch (type) {
                case "GRADE_1": return "G1";
                case "GRADE_2": return "G2";
                case "GRADE_3": return "G3";
                case "NON_GRADED": return "N";
                case "ALLOWANCE": return "A";
                case "STARTER_ALLOWANCE": return "R";
                case "STARTER_HANDICAP": return "T";
                case "CLAIMING": return "C";
                case "OPTIONAL_CLAIMING": return "CO";
                case "MAIDEN_SPECIAL_WEIGHT": return "S";
                case "MAIDEN_CLAIMING": return "M";
                case "ALLOWANCE_OPTIONAL_CLAIMING": return "AO";
                case "MAIDEN_OPTIONAL_CLAIMING": return "MO";
                case "OPTIONAL_CLAIMING_STAKES": return "NO";
            }
        },
        keyRaceHorsesFormat(horses) {
            var ret = "<span class='pp'>";
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
        goToChart(pp) {
            this.$emit("goToChart", pp);
        },
        hasChart(pp) {	
			const chartDates =  _.pluck(_.where(this.tracks, {track: pp.trackCode}),'date');	
			return chartDates.indexOf(pp.raceDate) >  -1;
        },
        dayOfWeek(raceDate) {
            return moment(raceDate).format("dddd");
        }  
    }
}
</script>
