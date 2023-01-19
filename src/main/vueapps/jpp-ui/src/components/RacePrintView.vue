<template>
    <div>
		<b-row>
			<b-col class="text-left">
				<h5 class="card-title">
					<span>Race {{race.raceNumber}}</span>   
				</h5>
			</b-col>
		</b-row>
		<b-row>
			<b-col cols="5">
				${{race.purse}} {{race.classification}}
			</b-col>
			<b-col cols="2" class="text-center">
				{{(race.allWeatherSurfaceFlag == 'A') ? 'ALL WEATHER' : race.surface}} <span v-if="race.distance<0"><sup>*</sup></span>
				<span v-if="Math.abs(race.miles==1.5)">1&frac12; Miles</span>
				<span v-else-if="Math.abs(race.distance)==1800">1<sup>40</sup> Miles</span>
				<span v-else-if="Math.abs(race.distance)==1830">1<sup>70</sup> Miles</span>
				<span v-else-if="Math.abs(race.miles)==1.375">1&frac38; Miles</span>
				<span v-else-if="Math.abs(race.miles)==1.25">1&frac14; Miles</span>
				<span v-else-if="Math.abs(race.miles)==1.125">1&frac18; Miles</span>
				<span v-else-if="Math.abs(race.miles)==1.0625">1<sup>1</sup>&frasl;<sub>16</sub> Mile</span>
				<span v-else-if="Math.abs(race.miles)==1">1 Mile</span>
				<span v-else-if="Math.abs(race.furlongs)==4.5">4&frac12; Furlongs</span>
				<span v-else-if="Math.abs(race.furlongs)==5.5">5&frac12; Furlongs</span>
				<span v-else-if="Math.abs(race.furlongs)==6.5">6&frac12; Furlongs</span>
				<span v-else-if="Math.abs(race.furlongs)==7.5">7&frac12; Furlongs</span>
				<span v-else>{{Math.abs(race.furlongs)}} Furlongs</span>						
			</b-col>
			<b-col cols="5" class="text-right">
				<span v-if="race.sexRestriction == 'FILLIES_AND_MARES'">Fillies & Mares </span>
				<span v-else-if="race.sexRestriction == 'FILLIES'">Fillies </span>
				<span v-else-if="race.sexRestriction == 'COLTS_AND_GELDINGS'">Colts & Geldings </span>
				<span v-if="race.ageRestriction == 'TWO_YEAR_OLDS'">Two Year Olds </span>
				<span v-else-if="race.ageRestriction == 'THREE_YEAR_OLDS'">Three Year Olds </span>
				<span v-else-if="race.ageRestriction == 'FOUR_YEAR_OLDS'">Four Year Olds </span>
				<span v-else-if="race.ageRestriction == 'FIVE_YEAR_OLDS'">Five Year Olds </span>
				<span v-else-if="race.ageRestriction == 'THREE_AND_FOUR_YEAR_OLDS'">Three & Four Year Olds </span>
				<span v-else-if="race.ageRestriction == 'FOUR_AND_FIVE_YEAR_OLDS'">Four & Five Year Olds </span>
				<span v-else-if="race.ageRestriction == 'THREE_FOUR_AND_FIVE_YEAR_OLDS'">Three, Four & Five Year Olds </span>
				<span v-if="race.ageRestrictionRange != 'ALL_AGES' && race.ageRestrictionRange == 'THAT_AGE_AND_UP'">And Upward </span>
			</b-col>
		</b-row>
		<b-row>
			<b-col>
				{{race.description}}
			</b-col>
		</b-row>
		<b-row v-if="race.meetTrackBias" class="mx-2 border">
			<b-col cols="2" offset="5" class="text-center">
				<h6>Track Bias Stats</h6>
			</b-col>
			<b-col cols="6" class="border">
				<b-row>											
					<b-col  v-if="race.meetTrackBias" class="text-center">
						*  MEET Totals  *
						<b-row>
							<b-col>
								{{(race.allWeatherSurfaceFlag == 'A') ? 'ALL WEATHER' : race.surface}} <span v-if="race.distance<0"><sup>*</sup></span>
								<span v-if="Math.abs(race.miles==1.5)">1&frac12;m</span>
								<span v-else-if="Math.abs(race.distance)==1800">1<sup>40</sup>m</span>
								<span v-else-if="Math.abs(race.distance)==1830">1<sup>70</sup>m</span>
								<span v-else-if="Math.abs(race.miles)==1.375">1&frac38;m</span>
								<span v-else-if="Math.abs(race.miles)==1.25">1&frac14;m</span>
								<span v-else-if="Math.abs(race.miles)==1.125">1&frac18;m</span>
								<span v-else-if="Math.abs(race.miles)==1.0625">1<sup>1</sup>&frasl;<sub>16</sub>m</span>
								<span v-else-if="Math.abs(race.miles)==1">1m</span>
								<span v-else-if="Math.abs(race.furlongs)==4.5">4&frac12;f</span>
								<span v-else-if="Math.abs(race.furlongs)==5.5">5&frac12;f</span>
								<span v-else-if="Math.abs(race.furlongs)==6.5">6&frac12;f</span>
								<span v-else-if="Math.abs(race.furlongs)==7.5">7&frac12;f</span>
								<span v-else>{{Math.abs(race.furlongs)}}f</span>
							</b-col>
							<b-col>
								Speed Bias: {{race.meetTrackBias.speedBias}}%
							</b-col>
							<b-col>
								WnrAvgBl
							</b-col>
						</b-row>
						<b-row>
							<b-col>
								#Races {{race.meetTrackBias.numberOfRaces}} 
							</b-col>
							<b-col>
								{{race.meetTrackBias.dates}}
							</b-col>
							<b-col>
								1st Call: {{race.meetTrackBias.winnerABLFirstCall}}
							</b-col>
						</b-row>
						<b-row>
							<b-col>
								%Wire: {{race.meetTrackBias.wirePercent}}%
							</b-col>
							<b-col>
								&nbsp;
							</b-col>
							<b-col>
								2nd Call: {{race.meetTrackBias.winnerABLSecondCall}}
							</b-col>
						</b-row>
						<b-row class="mt-2">
							<b-col>
								RunStyle:
							</b-col>
							<b-col>
								E
							</b-col>
							<b-col>
								E/P
							</b-col>
							<b-col>
								P
							</b-col>
							<b-col>
								S
							</b-col>	
						</b-row>
						<b-row>
							<b-col>
								%Races Won:
							</b-col>
							<b-col>
								{{race.meetTrackBias.percentEWon}}%
							</b-col>
							<b-col>
								{{race.meetTrackBias.percentEPWon}}%
							</b-col>
							<b-col>
								{{race.meetTrackBias.percentPWon}}%
							</b-col>
							<b-col>
								{{race.meetTrackBias.percentSWon}}%
							</b-col>	
						</b-row>																																																																
						<b-row class="mt-2">
							<b-col>
								Post Bias:
							</b-col>
							<b-col>
								RAIL
							</b-col>
							<b-col>
								1-3
							</b-col>
							<b-col>
								4-7
							</b-col>
							<b-col>
								8+
							</b-col>	
						</b-row>
						<b-row>
							<b-col>
								%Races Won:
							</b-col>
							<b-col>
								{{race.meetTrackBias.percentRailWon}}%
							</b-col>
							<b-col>
								{{race.meetTrackBias.percent123Won}}%
							</b-col>
							<b-col>
								{{race.meetTrackBias.percent4567Won}}%
							</b-col>
							<b-col>
								{{race.meetTrackBias.percent8PlusWon}}%
							</b-col>	
						</b-row>												
					</b-col>
																
				</b-row>
			</b-col>
			<b-col cols="6" class="border">
				<b-row>											
					<b-col v-if="race.weekTrackBias" class="text-center">
						*  Week Totals  *
						<b-row>
							<b-col>
								{{(race.allWeatherSurfaceFlag == 'A') ? 'ALL WEATHER' : race.surface}} <span v-if="race.distance<0"><sup>*</sup></span>
								<span v-if="Math.abs(race.miles==1.5)">1&frac12;m</span>
								<span v-else-if="Math.abs(race.distance)==1800">1<sup>40</sup>m</span>
								<span v-else-if="Math.abs(race.distance)==1830">1<sup>70</sup>m</span>
								<span v-else-if="Math.abs(race.miles)==1.375">1&frac38;m</span>
								<span v-else-if="Math.abs(race.miles)==1.25">1&frac14;m</span>
								<span v-else-if="Math.abs(race.miles)==1.125">1&frac18;m</span>
								<span v-else-if="Math.abs(race.miles)==1.0625">1<sup>1</sup>&frasl;<sub>16</sub>m</span>
								<span v-else-if="Math.abs(race.miles)==1">1m</span>
								<span v-else-if="Math.abs(race.furlongs)==4.5">4&frac12;f</span>
								<span v-else-if="Math.abs(race.furlongs)==5.5">5&frac12;f</span>
								<span v-else-if="Math.abs(race.furlongs)==6.5">6&frac12;f</span>
								<span v-else-if="Math.abs(race.furlongs)==7.5">7&frac12;f</span>
								<span v-else>{{Math.abs(race.furlongs)}}f</span>
							</b-col>
							<b-col>
								Speed Bias: {{race.weekTrackBias.speedBias}}%
							</b-col>
							<b-col>
								WnrAvgBl
							</b-col>
						</b-row>
						<b-row>
							<b-col>
								#Races {{race.weekTrackBias.numberOfRaces}} 
							</b-col>
							<b-col>
								{{race.weekTrackBias.dates}}
							</b-col>
							<b-col>
								1st Call: {{race.weekTrackBias.winnerABLFirstCall}}
							</b-col>
						</b-row>
						<b-row>
							<b-col>
								%Wire: {{race.weekTrackBias.wirePercent}}%
							</b-col>
							<b-col>
								&nbsp;
							</b-col>
							<b-col>
								2nd Call: {{race.weekTrackBias.winnerABLSecondCall}}
							</b-col>
						</b-row>
						<b-row class="mt-2">
							<b-col>
								RunStyle:
							</b-col>
							<b-col>
								E
							</b-col>
							<b-col>
								E/P
							</b-col>
							<b-col>
								P
							</b-col>
							<b-col>
								S
							</b-col>	
						</b-row>
						<b-row>
							<b-col>
								%Races Won:
							</b-col>
							<b-col>
								{{race.weekTrackBias.percentEWon}}%
							</b-col>
							<b-col>
								{{race.weekTrackBias.percentEPWon}}%
							</b-col>
							<b-col>
								{{race.weekTrackBias.percentPWon}}%
							</b-col>
							<b-col>
								{{race.weekTrackBias.percentSWon}}%
							</b-col>	
						</b-row>																																																																
						<b-row class="mt-2">
							<b-col>
								Post Bias:
							</b-col>
							<b-col>
								RAIL
							</b-col>
							<b-col>
								1-3
							</b-col>
							<b-col>
								4-7
							</b-col>
							<b-col>
								8+
							</b-col>	
						</b-row>
						<b-row>
							<b-col>
								%Races Won:
							</b-col>
							<b-col>
								{{race.weekTrackBias.percentRailWon}}%
							</b-col>
							<b-col>
								{{race.weekTrackBias.percent123Won}}%
							</b-col>
							<b-col>
								{{race.weekTrackBias.percent4567Won}}%
							</b-col>
							<b-col>
								{{race.weekTrackBias.percent8PlusWon}}%
							</b-col>	
						</b-row>																																																															
					</b-col>																						
				</b-row>
			</b-col>
		</b-row>
		<b-row class="mt-2">
			<b-col cols="10">
				{{race.wagerTypes}}
			</b-col>
			<b-col class="text-right">
				{{race.postTimes}}
			</b-col>
		</b-row>
        <b-row>
            <b-col cols="12">
                <span v-for="(change, index) in race.changes" :key="index" class="text-danger">
                    <span v-if="change.indexOf('scratched') == -1">{{change}}; </span>
                </span>
            </b-col>
        </b-row>
        <b-row>
            <b-col class="text-left">
                MaxSpeed: {{race.maxSpeed}}
            </b-col>								
            <b-col class="text-center">
                MaxE1Avg: {{race.e1Avg}}
            </b-col>
            <b-col class="text-center">
                MaxE2Avg: {{format2Places(race.e2Avg)}}
            </b-col>
            <b-col class="text-center">
                MaxE2: {{race.maxE2}}
            </b-col>
            <b-col class="text-right">
                LatePaceBestL3: {{race.latePaceBestLast3}}
            </b-col>								
            <b-col class="text-center" v-b-toggle="'collapseScenario-'+race.race_id">
                {{race.paceScenario}}
            </b-col>
            <b-col cols="2" class="text-right">
                Pars: {{race.furlongs &lt; 8 ? race.parPace2F : race.parPace4F}} {{race.furlongs &lt; 8 ? race.parPace4F : race.parPace6F}} / {{race.parLatePace}} - {{race.parSpeed}} 
            </b-col>								
        </b-row>
        <b-row class="my-2">
            <b-col cols="10">
                {{race.note}}
            </b-col>
        </b-row>    
    </div>
</template>

<script>
export default {
    name: 'RacePrintView',
    components: {
		
    },
    props : ['race'],
    data () {
		return {

        }
    },
    methods: {
		format2Places(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		}, 
    }
}
</script>