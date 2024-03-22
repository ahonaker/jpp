<template>
    <b-table
        id="pps"
        :items="race.entries"
        :fields="entriesFields"
        small
        :tbody-tr-class="rowClass"
        primary-key="programNumber"
    >
        <template #head()="data">
            {{ data.label }}
        </template>    
        <template #cell()="row">
            {{row.value}}
        </template>         
        <template #cell(programNumber)="row">	
            {{row.value}}<sup v-if="row.item.postPosition != row.item.programNumber">{{row.item.postPosition}}</sup>
        </template>
        <template #cell(pickButton)="row">	
            <span>		
                <b-badge
                    :variant="(row.item.pick) ? 'primary' : 'outline-secondary'"
                >
                    {{row.item.pick ? 'P' : ''}}
                </b-badge>	
            </span>
        </template>	 
        <template #cell(style)="row">
            {{ row.item.runStyle }} {{ row.item.speedPoints }}
        </template>	 
        <template #cell(daysSinceLastRace)="row">
            {{row.value}} / {{format2Places(row.item.furlongDays)}}
        </template>
        <template #cell(classRating)="row">
            {{row.value}} / {{row.item.brisCurrentClass}} / {{row.item.brisAvgLast3Class}}
        </template>    
        <template #cell(aratingForm)="row">
            {{row.value}} / {{row.item.basicFitness}}
        </template>    								
    </b-table>

</template>

<script>

import _ from 'underscore'

export default {
    name: 'EntriesPrintView',
    components: {
		
    },
    props : ['race'],
    data () {
		return {
            transProps: {
				name: 'flip-list'
			},
			entriesFields: [
				{key: "programNumber", label:"#", title: "Program Number (& Post Position)"},
                {key: "pickButton", label:""},
                {key: "selection", label: "ABC", title: "ABC Selection"},
                {key: "bettingLine", label: "FV", title: "Betting Fair Value Line"},  
                {key: "aratingFairValue", label: "AFV", title: "A Rating Fair Value Line", formatter: this.format1Place},           
				{key: "mlodds", "label": "ML", title: "Morning Line Odds", tdClass: this.formatOdds, rank: true, reverse: true},
				{key: "name", tdClass: this.highlightName},
				{key: "daysSinceLastRace", label:"l/r", title: "Days Since Last Race", tdClass: this.highlightDaysSince},
				{key: "style", label: "Style", title: "Run Style and Speed Points", tdClass: this.highlightPaceAdvantage, rank: false },
				{key: "classRating", label:"Class/BrisAvg/BrisLast3", title: "Weighted Average Speed Par", formatter: this.format2Places, tdClass: this.highlightMax, rank: true},
				{key: "averageCompetitiveLevel", title: "Average Competitive Level (Weighted Average Speed Par where finish in the money or within 3 lengths)", label:"ACL", formatter: this.format2Places, tdClass: this.highlightMax, rank: true},
				{key: "speedRating", label:"Speed", title: "Weighted Average BRIS Speed Figure", formatter: this.format2Places, tdClass: this.highlightMax, rank: true},
                {key: "primePower", label:"PP", title: "Prime Power", formatter: this.format2Places, tdClass: this.highlightMax, rank: true},
                {key: "arating", label: "AR", title: "A Rating", formatter: this.format2Places, tdClass: this.highlightMax, rank: true},
				{key: "aratingForm", label:"AR F", title: "A Form Rating", formatter: this.format2Places, tdClass: this.highlightMax, rank: true}, 
				{key: "aratingConnections", label:"AR Co", title: "A Connections Rating", formatter: this.format2Places, tdClass: this.highlightMax, rank: true},
				{key: "e1Avg", label:"E1", title: "Average E1 Pace", formatter: this.formatInt, tdClass: this.highlightMax, rank: true},
				{key: "e2Avg", label:"E2", title: "Average E2 Pace", formatter: this.formatInt, tdClass: this.highlightMax, rank: true},
				{key: "turnTime", label:"TT", title: "Average Turn Time (E2-E1)", formatter: this.formatInt, tdClass: this.highlightMax, rank: true},
				{key: "latePaceAvg", label:"Late", title: "Average Late Pace", formatter: this.formatInt, tdClass: this.highlightMax, rank: true},
				{key: "combinedPaceAvg", label:"Comb", title: "Average Combined Pace (E2+Late)", formatter: this.format2Places, tdClass: this.highlightMax, rank: true},
				{key: "classShift", label:"cSh", title: "Class Rating Shift (Par change from previous race)", formatter: this.format2Places, tdClass: this.highlightShift, reverse: true, rank: true},
				{key: "purseShift", label:"pSh", title: "Purse Shift (change from previous race)", formatter: this.formatInt, tdClass: this.highlightShift, reverse: true, rank: true},
			],

        }
    },
    computed: {

    },
    methods:  {
		rowClass(item, type) {
            var cl = "horse ";
			if (!item || type !== 'row') return cl;
			if (item.scratchedFlag) return cl + 'table-dark scratched';
            if (item.selection  == 'A') return cl + 'aHorse';
            if (item.selection  == 'B') return cl + 'bHorse';
            if (item.selection  == 'C') return cl + 'cHorse';
            if (item.selection  == 'X') return cl + 'xHorse';
			return cl + 'notScratched';
		},		
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
			return new Date(date).toLocaleDateString;
			//return date[1] + "/" + date[2] + "/" + date[0];
		},  
        formatOdds (value, key) {
            return "text-center " + ((this.hideML) ? "" : this.highlightMin(value,key));

        },
        highlightName (value, key, item) {
            if (item.flag == 'Star') return "star";
        },   
		highlightMin(value, key) {
			var values = _.pluck(_.reject(this.race.entries, function(h) {
				return h.scratchedFlag;
			}), key);

			if (value == _.min(values)) {
				return "greenHighlight";
			}
		},          
		highlightMax(value, key) {
			var values = _.pluck(_.reject(this.race.entries, function(h) {
				return h.scratchedFlag;
			}), key);

			if (value == _.max(values)) {
				return "greenHighlight";
			}
		},
		highlightDaysSince(value) {
			if (value  > 180) return "gt180";
			if (value > 90) return "gt90";
			if (value > 45) return "gt45";
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
			if (item.purse >= this.race.purse + 20000 ) return "greenHighlight";
			if (item.purse > this.race.purse) return "lightGreenHighlight";
			if (item.purse == this.race.purse) return "";
			if (item.purse < this.race.purse - 10000) return "yellowHighlight";
			if (item.purse < this.race.purse - 20000) return "lightRedHighlight";
			if (item.purse < this.race.purse - 30000) return "redHighlight";
		},
		highlightSpeedInRange(value,key,item) {
			if (item.brisspeedRating >= this.race.parSpeed) return "greenHighlight";
			if (item.brisspeedRating >= this.race.parSpeed - 3) return "lightGreenHighlight";
		},
		highlightPaceAdvantage(value, key, item) {
			if (_.contains(this.race.advantagedEntries, item.name)) return "greenHighlight";
		},
		highlightShift(value) {
			if (value < 0) return "greenHighlight";
			if (value > 0) return "yellowHighlight";
		},
		highlightSimilarSurfDist(value, key, item) {
			if (this.race.surface.indexOf("TURF") > -1 && !this.race.offTheTurfFlag) {
				if (
					item.surface.indexOf("TURF") > -1 
					&& ((this.race.trackCondition == "fm" && item.trackCondition == "fm") || (this.race.trackCondition != "fm" && ["tf","gd","gf","hd","yl","sf","hy","sl"].indexOf(item.trackCondition) > -1))
					&& ((this.race.furlongs < 8 && item.furlongs < 8) || (this.race.furlongs >= 8 && item.furlongs >= 8))) 
					return "blueHighlight";
			} else {
				if ((item.surface.indexOf("TURF") == -1 || item.offTheTurfFlag)
					&& ((this.race.trackCondition == "ft" && item.trackCondition == "ft") || (this.race.trackCondition != "ft" && ["gd","hy","my","sl","sy","wf"].indexOf(item.trackCondition) > -1))
					&& ((this.race.furlongs < 8 && item.furlongs < 8) || (this.race.furlongs >= 8 && item.furlongs >= 8))) 
					return "blueHighlight";
			}
		},
		alsoInRace(nameToMatch, row) {
			var names = _.pluck(this.race.entries, "name");
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


