<template>
	<div>
		<navbar-view :status="status"></navbar-view>
		<b-navbar id="nav" toggleable="sm" class="pt-0" >						
			<b-navbar-nav>
				<b-nav-form>
                    <b-form-select
                        id="ppTrack"
                        class="mx-2"
						siz="sm"
                        :options="ppTracks"
                        v-model="ppTrack"
                        placeholder="Select Track"
                    ></b-form-select>
                    <b-form-datepicker 
                        value-as-date id="ppDate" 
                        v-model="ppDate" 
                        class="mx-2"
						style="min-width: 250px;"
						size="sm"
                        :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                        :date-disabled-fn="disableDates"
                        no-highlight-today
                        hide-header
                    ></b-form-datepicker>					
					<b-form-file
						id="filenameform"
						size="sm" 
						v-model="file"
						:state="Boolean(file)"
						placeholder="Choose or drop here..."
						drop-placeholder="Drop file here..."
						class="text-left"
					></b-form-file>
				</b-nav-form>
			</b-navbar-nav>				
			<b-navbar-nav small>
				<b-nav-item :disabled="!file" @click="handle('load')"><b-icon-cloud-upload v-b-tooltip.hover.bottom title="Upload"></b-icon-cloud-upload></b-nav-item>
				<b-nav-item :disabled="races.length == 0 || !file" @click="handle('augment')"><b-icon-cloud-plus title="Augment"></b-icon-cloud-plus></b-nav-item>
				<b-nav-item :disabled="races.length == 0 || !file" @click="handle('addProgramNumbers')"><b-icon-file-earmark-binary title="Add Program Numbers"></b-icon-file-earmark-binary></b-nav-item>
				<b-nav-item :disabled="races.length == 0" @click="handle('getChanges')"><b-icon-triangle-fill v-b-tooltip.hover.bottom title="Get Changes"></b-icon-triangle-fill></b-nav-item>
				<b-nav-item :disabled="races.length == 0" @click="handle('getResults')"><b-icon-currency-dollar v-b-tooltip.hover.bottom title="Results"></b-icon-currency-dollar></b-nav-item>					
				<b-nav-item :disabled="races.length == 0" @click="handle('calculate')"><b-icon-calculator-fill v-b-tooltip.hover.bottom title="Calculate"></b-icon-calculator-fill></b-nav-item>
				<b-nav-item :disabled="races.length == 0" @click="handle('updatePPs')"><b-icon-arrow-clockwise v-b-tooltip.hover.bottom title="Update PPs"></b-icon-arrow-clockwise></b-nav-item>
				<b-nav-item :disabled="races.length == 0" @click="handle('save')"><b-icon-file-earmark-arrow-up v-b-tooltip.hover.bottom title="Save"></b-icon-file-earmark-arrow-up></b-nav-item>			
				<b-nav-item :disabled="!ppTrack && !ppDate" @click="handle('retrieve')"><b-icon-file-earmark-arrow-down v-b-tooltip.hover.bottom title="Retrieve"></b-icon-file-earmark-arrow-down></b-nav-item>
				<b-nav-item @click="handle('getAll')"><b-icon-cloud-download v-b-tooltip.hover.bottom title="Download"></b-icon-cloud-download></b-nav-item>
				<b-nav-item :disabled="races.length == 0" @click="handle('clearRaces')"><b-icon-eraser-fill v-b-tooltip.hover.bottom title="Clear Races"></b-icon-eraser-fill></b-nav-item>	
			</b-navbar-nav>
		</b-navbar>

		<b-tabs>
			<b-tab v-for="(race, racekey) in races" :key="racekey">
				<template #title>
					Race {{race.raceNumber}}&nbsp;
				</template>

				<div class="card-body">
					<b-row>
						<b-col class="text-left">
							<h5 class="card-title">
								<span>{{race.track}} Race {{race.raceNumber}} - {{formatDate(race.date)}}</span>
							</h5>
						</b-col>
						<b-col v-if="hasResults[racekey]" class="text-right">
							<a 
								:href="'https://www.twinspires.com/bet/product/download/INC/TB/' 
									+ race.track + '/'
									+ m(race.date).year + '-' + str_pad_left(m(race.date).month,0,2) + '-' + str_pad_left(m(race.date).date,0,2) 
									+ '/D/' + race.raceNumber" 
								target="_blank"            
							><b-icon-bar-chart-steps></b-icon-bar-chart-steps>
							</a>&nbsp;
							<a 
								:href="'https://www.twinspires.com/bet/video/replay/'
									+ m(race.date[0]).year + '-' + str_pad_left(m(race.date).month,0,2) + '-' + str_pad_left(m(race.date).date,0,2) 
									+ '/' + race.track
									+ '/Thoroughbred/' + race.raceNumber" 
								target="_blank"  
							><b-icon-camera-video-fill></b-icon-camera-video-fill>
							</a>
						</b-col>
					</b-row>
					<b-row>
						<b-col v-b-tooltip.hover.bottom :title="race.description" cols="5">
							${{race.purse}} <span  v-b-toggle="'collapseNotes-'+race.race_id">{{race.classification}}</span>
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
					<b-collapse :id="'collapseNotes-'+race.race_id" visible>
						<b-row class = "border text-info m-2">
							<b-col cols="12">
								<span v-for="(note, index) in race.handicappingNotes" :key="index">{{note}}<br></span>
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
					</b-collapse>
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
					<b-collapse :id="'collapseScenario-'+race.race_id">
						<b-row class="border m-2 text-info">
							<b-col>
								{{paceScenarioComment(race.paceScenario)}}
							</b-col>
						</b-row>
					</b-collapse>
					<b-row class="my-2">
						<b-col cols="10">
							<b-form-textarea
								size="sm"
								v-model="race.note"
								placeholder="Enter something..."
								max-rows="4"
								debounce="500" 
							></b-form-textarea>
						</b-col>
						<b-col>
							<b-button size="sm" class="my-3" variant="primary" @click="setRaceNote(race)"><b-icon-cloud-upload-fill></b-icon-cloud-upload-fill></b-button>
						</b-col>
					</b-row>
					<b-row v-if="hasResults[racekey]">
						<b-col :class = "(wPayout[racekey] > 0) ? 'text-success' : 'text-danger'">
							$2 Win: <span v-b-tooltip.hover :title="formatCurrency2(wPayoutSum)">{{formatCurrency2(wPayout[racekey])}}</span>
						</b-col>
						<b-col :class = "(wpPayout[racekey] > 0) ? 'text-success' : 'text-danger'">
							$2 WP: <span v-b-tooltip.hover :title="formatCurrency2(wpPayoutSum)">{{formatCurrency2(wpPayout[racekey])}}</span>
						</b-col>
						<b-col :class = "(wpsPayout[racekey] > 0) ? 'text-success' : 'text-danger'">
							$2 WPS: <span v-b-tooltip.hover :title="formatCurrency2(wpsPayoutSum)">{{formatCurrency2(wpsPayout[racekey])}}</span>
						</b-col>																
					</b-row>
				</div>

				<b-navbar id="nav" class="py-2">
					
					<!-- Right aligned nav items -->
					<b-navbar-nav>
						<b-nav-form>
							<b-form-select v-model="race.trackCondition" :options="trackConditions" @change="updateCondition(race)"></b-form-select>		
							<b-form-checkbox switch class="m-2" v-if="race.surface &&  race.surface.indexOf('TURF') > -1" v-model="race.offTheTurfFlag" @change="toggleOffTheTurf(race)">Off</b-form-checkbox>
							<b-form-checkbox switch class="m-2" v-if="race.offTheTurfFlag" v-model="race.ontoAllWeatherFlag" @change="toggleOntoAllWeather(race)">Onto AW</b-form-checkbox>
							<b-form-select class="m-2" v-model="options.distance" :options="distanceOptions" @change="calculate"></b-form-select>
							<b-form-select class="m-2" v-model="options.surface" :options="surfaceOptions" @change="calculate"></b-form-select>
							<b-form-select class="m-2" v-model="options.condition" :options="conditionOptions" @change="calculate"></b-form-select>	
							<b-form-checkbox switch class="m-2" v-model="hideML">Hide ML</b-form-checkbox>
						</b-nav-form>
					</b-navbar-nav>
					<b-navbar-nav class="ml-auto" small>
						<b-nav-item v-if="race.multiRaceWagers.length > 0" v-b-toggle.ticketmaker><b-icon-cash-stack font-scale="2"></b-icon-cash-stack></b-nav-item>
						<b-nav-item @click="toggleAll(race, true)"><b-icon-plus font-scale="2"></b-icon-plus></b-nav-item>
						<b-nav-item @click="toggleAll(race, false)"><b-icon-dash font-scale="2"></b-icon-dash></b-nav-item>
					</b-navbar-nav>
				</b-navbar>		

				<b-collapse v-if="race.multiRaceWagers.length > 0" id="ticketmaker" class="mt-2">
					<b-row>
						<b-col v-for="wager in race.multiRaceWagers" :key="wager.name">
							<b-row>
								<b-col cols="" class="text-center">	
									<h6>{{wager.name}} ({{formatCurrency2(wager.min)}}) ({{wager.firstRace}}-{{wager.firstRace + wager.numRaces - 1}})</h6>
								</b-col>
							</b-row>
							<b-row class="my-2">
								<b-col cols="3">
									Total
								</b-col>
								<b-col cols="4">
									<strong>{{formatCurrency2(
										(tmA ? wager.tmACost : 0) +
										(tmAB ? wager.tmABCost : 0) +
										(tmB1 ? wager.tmB1Cost : 0) +
										(tmB2 ? wager.tmB2Cost : 0) +
										(tmC1 ? wager.tmC1Cost : 0)
										)}}</strong>
								</b-col>
							</b-row>
							<b-row class="my-2">
								<b-col cols="3">
									<b-form-checkbox v-model="tmA">All As</b-form-checkbox>
								</b-col>
								<b-col cols="7" >
									<strong>{{formatCurrency2(wager.tmACost)}}</strong>
								</b-col>
								<b-col v-if="wager.tmA">
									<b-button size="sm" class="my-1" :variant="wager.tmAchecked ? 'success' : 'outline-dark'" @click="wager.tmAchecked = !wager.tmAchecked; races.splice()">{{wager.tmA}}</b-button>
								</b-col>
							</b-row>
							<b-row class="my-2">
								<b-col cols="3">
									<b-form-checkbox v-model="tmAB">All As & Bs</b-form-checkbox>
								</b-col>
								<b-col cols="7">
									<strong>{{formatCurrency2(wager.tmABCost)}}</strong>
								</b-col>
								<b-col v-if="wager.tmAB">
									<b-button size="sm" class="my-1" :variant="wager.tmABchecked ? 'success' : 'outline-dark'" @click="wager.tmABchecked = !wager.tmABchecked; races.splice()">{{wager.tmAB}}</b-button>
								</b-col>									
							</b-row>
							<b-row class="my-2">
								<b-col cols="3">
									<b-form-checkbox v-model="tmB1">All As & One B</b-form-checkbox>
								</b-col>
								<b-col cols="7">
									<strong>{{formatCurrency2(wager.tmB1Cost)}}</strong>
								</b-col>
								<b-col>
									<span v-for="(combo,n) in wager.tmB1" :key="n">
										<b-button size="sm" class="my-1" :variant="wager.tmB1checked[n] ? 'success' : 'outline-dark'" @click="wager.tmB1checked[n] = !wager.tmB1checked[n]; races.splice()">{{combo}}</b-button><br>
									</span>
								</b-col>									
							</b-row>
							<b-row class="my-2">
								<b-col cols="3">
									<b-form-checkbox v-model="tmB2">All As & Two Bs</b-form-checkbox>
								</b-col>
								<b-col cols="7">
									<strong>{{formatCurrency2(wager.tmB2Cost)}}</strong>
								</b-col>
								<b-col>
									<span v-for="(combo,n) in wager.tmB2" :key="n">
										<b-button size="sm" class="my-1" :variant="wager.tmB2checked[n] ? 'success' : 'outline-dark'" @click="wager.tmB2checked[n] = !wager.tmB2checked[n]; races.splice()">{{combo}}</b-button><br>
									</span>
								</b-col>									
							</b-row>
							<b-row class="my-2">
								<b-col cols="3">
									<b-form-checkbox v-model="tmC1">All As & 1 C</b-form-checkbox>
								</b-col>
								<b-col cols="7">
									<strong>{{formatCurrency2(wager.tmC1Cost)}}</strong>
								</b-col>
								<b-col>
									<span v-for="(combo,n) in wager.tmC1" :key="n">
										<b-button size="sm" class="my-1" :variant="wager.tmC1checked[n] ? 'success' : 'outline-dark'" @click="wager.tmC1checked[n] = !wager.tmC1checked[n]; races.splice()">{{combo}}</b-button><br>
									</span>
								</b-col>
							</b-row>									
						</b-col>
					</b-row>
				</b-collapse>
				<race-view :race="race" :hideML="hideML" :tracks="tracks" @selectionUpdate="tmGenerate" @togglePick="togglePick"></race-view>
			</b-tab>
		</b-tabs>	
	</div>
</template>

<script>
//import { } from 'bootstrap-vue'
import { BIconPlus, BIconDash, BIconCashStack, BIconCloudUploadFill, BIconBarChartSteps, BIconCameraVideoFill, BIconCloudUpload, BIconCloudDownload, BIconCloudPlus, BIconTriangleFill, BIconCurrencyDollar, BIconCalculatorFill, BIconFileEarmarkArrowUp, BIconFileEarmarkArrowDown, BIconFileEarmarkBinary, BIconEraserFill, BIconArrowClockwise  } from 'bootstrap-vue'
import RaceView from '@/components/RaceView'
import NavbarView from '@/views/NavbarView'

import axios from 'axios'
import _ from 'underscore'
import moment from 'moment'

export default {
	name: 'RacesView',
	components: {
		RaceView, NavbarView, BIconPlus, BIconDash, BIconCashStack, BIconCloudUploadFill, BIconBarChartSteps, BIconCameraVideoFill, BIconCloudUpload, BIconCloudDownload, BIconCloudPlus, BIconTriangleFill, BIconCurrencyDollar, BIconCalculatorFill, BIconFileEarmarkArrowUp, BIconFileEarmarkArrowDown, BIconFileEarmarkBinary, BIconEraserFill, BIconArrowClockwise
	},
	data () {
		return {
			status: "",
			races: [],
			tracks: [],
			saved: [],
			file: null,
			ppTrack: null,
			ppDate: null,
			pp: "",
			hideML: true,
			options: {
				surface: 'all',
				distance: 'all',
				condition: 'all'
			},
			trackConditions: [
				{value: "ft", text: "Fast"},
				{value: "my", text: "Muddy"},
				{value: "wf", text: "Wet Fast"},
				{value: "yl", text: "Yielding"},
				{value: "hy", text: "Heavy"},
				{value: "gd", text: "Good"},
				{value: "sy", text: "Sloppy"},
				{value: "fm", text: "Firm"},
				{value: "sf", text: "Soft"},
				{value: "sl", text: "Slow"}
			],
			surfaceOptions: [
				{value: 'all', text: 'All Surfaces'},
				{value: 'same', text: 'Same Surface Only'},
				{value: 'off', text: 'Off The Turf'}
			],
			distanceOptions: [
				{value: 'all', text: 'All Distances'},
				{value: 'same', text: 'Same Distance Only'},
				{value: 'similar', text: 'Similar Distances'}
			],
			conditionOptions: [
				{value: 'all', text: 'All Conditions'},
				{value: 'good', text: 'Good'},
				{value: 'off', text: 'Off'},
				{value: "my", text: "Muddy"},
				{value: "wf", text: "Wet Fast"},
				{value: "yl", text: "Yielding"},
				{value: "hy", text: "Heavy"},
				{value: "gd", text: "Good"},
				{value: "sy", text: "Sloppy"},
				{value: "sf", text: "Soft"},
				{value: "sl", text: "Slow"}
			],
			combinations: [],
			combinationsText: [],
			tmA: false,
			tmAB: true,
			tmB1: true,
			tmB2: false,
			tmC1: true,
			test: null
		}	
	},
	mounted() {
		this.getTracks();
		this.getSaved();
	},
	computed: {	
		ppTracks() {
			return _.uniq(_.pluck(this.saved, "track"));
		},
		ppDates() {   
			if (!this.ppTrack) return [];			
            return  _.map(_.pluck(_.where(this.saved, {track: this.ppTrack}), "date"), function (d) {
					return moment(d).format("yyyy-MM-DD");
                });
		},		
		hasResults() {
			var has = [];
			for (var i = 0; i < this.races.length; i++ ) {
				has[i] = false;
				for (var j =0; j < this.races[i].entries.length; j++) {
					if (this.races[i].entries[j].finishPosition == 1) has[i] = true;
				}
			}
			return has;
		},
		wPayout() {
			var payout = [];
			for (var i = 0; i < this.races.length; i++ ) {
				payout[i] = 0;
				for (var j =0; j < this.races[i].entries.length; j++) {
					if (this.races[i].entries[j].pick) payout[i] = this.races[i].entries[j].winPayout;
				}
			}	
			return payout;		
		},
		wpPayout() {
			var payout = [];
			for (var i = 0; i < this.races.length; i++ ) {
				payout[i] = 0;
				for (var j =0; j < this.races[i].entries.length; j++) {
					if (this.races[i].entries[j].pick) payout[i] = this.races[i].entries[j].winPayout
						+ this.races[i].entries[j].placePayout;
				}
			}	
			return payout;		
		},
		wpsPayout() {
			var payout = [];
			for (var i = 0; i < this.races.length; i++ ) {
				payout[i] = 0;
				for (var j =0; j < this.races[i].entries.length; j++) {
					if (this.races[i].entries[j].pick) payout[i] = this.races[i].entries[j].winPayout
						+ this.races[i].entries[j].placePayout
						+ this.races[i].entries[j].showPayout;
				}
			}	
			return payout;		
		},		
		wPayoutSum() {
			return _.reduce(this.wPayout, function(memo, num){ return memo + num; });
		},
		wpPayoutSum() {
			return _.reduce(this.wpPayout, function(memo, num){ return memo + num; });
		},		
		wpsPayoutSum() {
			return _.reduce(this.wpsPayout, function(memo, num){ return memo + num; });
		}
	},
	methods: {
		async getTracks() {
			try {
				const response = await axios({
					url: 'getTracks/',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.tracks = response.data;
			} catch (err) {
				console.log(err.response);
							
			}	
		},
		async getSaved() {
			try {
				const response = await axios({
					url: 'getSaved/',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.saved = response.data;
			} catch (err) {
				console.log(err.response);
							
			}	
		},
		handle(action) { 
			this.status = action + " started";
			switch (action) {
				case "load":
					this.load();
					break;
				case "augment": 
					this.augment();
					break;
				case "addProgramNumbers":
					this.addProgramNumbers();
					break;
				case "getChanges":
					this.getChanges();
					break;
				case "getResults": 
					this.getResults();
					break;
				case "calculate":
					this.calculate();
					break;
				case "updatePPs":
					this.updatePPs();
					break;
				case "save":
					this.save(false);
					break;
				case "retrieve":
					this.retrieve();
					break;
				case "getAll":
					this.getAll();
					break;
				case "clearRaces":
					this.clearRaces();
					break;

			} 
		},			
		load() {
			if (this.races.length == 0) {
				this.uploadAndCalculate();
			} else {
				this.$bvModal.msgBoxConfirm("Reload and Start Over?")
					.then(confirmed => {
						if (confirmed) {
							this.races = [];
							this.uploadAndCalculate();
						}
					});
			}
		},		
		getAll() {
			if (this.races.length == 0) {
				this.getAllRaces();
			} else {
				this.$bvModal.msgBoxConfirm("Reload and Start Over?")
					.then(confirmed => {
						if (confirmed) {
							this.races = [];
							this.getAllRaces();
						}
					});
			}
		},	
		async getAllRaces() {
            try {
                const response = await axios({
                    url: 'getAll',
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
				this.races = response.data;
				this.status = "races downloaded...cleaning up"
				Promise.all(this.postGetActions()).then(()=>{this.status = "";});	
            } catch (err) {
                console.log(err);
                
            }
		},
		postGetActions() {
			var promises = []
			for (var i=0; i < this.races.length; i++) {
				this.combinations[i] = [];
				this.combinationsText[i] = []; 
				for (var j=0; j < this.races[i].multiRaceWagers.length; j++) {
					this.combinations[i][j] = {a: [], b:[], c:[]};
					this.combinationsText[i][j] = {tmA: "", tmAB: "", tmB1: "", tmB2: "", tmC1: ""};
				}
				promises.push(Promise.all(this.toggleAll(this.races[i], false)));
			}
			return promises;
		},
		async uploadAndCalculate() {
            try {
                var formData = new FormData();
                formData.append("data", this.file);
                formData.append("filename", this.file.name);
				formData.append("distanceOption", this.options.distance);
				formData.append("surfaceOption", this.options.surface);
				formData.append("conditionOption", this.options.condition);
                const response = await axios({
                    url: 'uploadAndCalculate',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });
                //console.log(response);
				this.races = response.data;
				this.status = "races downloaded...cleaning up"
				Promise.all(this.postGetActions()).then(()=>{this.status = "";});	
            } catch (err) {
                console.log(err);
                
            }
		},
		async augment() {
            try {
				this.status = "Saving";
				await this.save(true);
				this.status = "Augmenting";
                var formData = new FormData();
                formData.append("data", this.file);
                formData.append("filename", this.file.name);
                const response = await axios({
                    url: 'parsePP',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });
                //console.log(response.data);
				this.races = response.data;
				this.status = "races downloaded...cleaning up";
				var promises = [];
				for (var i=0; i < this.races.length; i++) {
					promises.push(Promise.all(this.toggleAll(this.races[i], false)));
				}
				Promise.all(promises).then(()=>{this.status = ""});
            } catch (err) {
                console.log(err);
                
            }
		},
		async addProgramNumbers() {
            try {
				this.status = "Saving";
				await this.save(true);
				this.status = "Updating";
                var formData = new FormData();
                formData.append("data", this.file);
                formData.append("filename", this.file.name);
                const response = await axios({
                    url: 'addProgramNumbers',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });
                //console.log(response.data);
				this.races = response.data;
				this.status = "races downloaded...cleaning up"
				var promises = [];
				for (var i=0; i < this.races.length; i++) {
					promises.push(Promise.all(this.toggleAll(this.races[i], false)));
				}
				Promise.all(promises).then(()=>{this.status = ""});
            } catch (err) {
                console.log(err);
                
            }
		},					
		async save(noNotify) {
            try {
				var promises = [];
				this.status = "Updating Notes";
				await this.$nextTick();
				for (var i=0; i < this.races.length; i++) {
					promises.push(this.setRaceNote(this.races[i]));
					for (var j=0; j < this.races[i].entries.length; j++) {
						promises.push(this.setEntryNote(this.races[i].entries[j]));
					}
				}
				
				Promise.all(promises).then(async () => {
					this.status = "Saving";
					await axios({
						url: 'save', 
						method: 'GET',
						baseURL: 'http://localhost:8080/jpp/rest/remote/'
					});
					this.status = "";
					this.getSaved();
					if (!noNotify) this.$bvModal.msgBoxOk('Races saved.');	
				})

				
								
            } catch (err) {
                console.log(err.response);
                
            }
		},
		retrieve() {
			try {
				this.$bvModal.msgBoxConfirm("Retrieve Saved Races?")
					.then(async confirmed => {
						if (confirmed) {
								this.status = "Retrieving";
								const response = await axios({
									url: 'retrieve/' + this.ppTrack + "/" + this.ppDate.getFullYear() + "/" + (this.ppDate.getMonth() + 1)+ "/" + this.ppDate.getDate(),
									method: 'GET',
									baseURL: 'http://localhost:8080/jpp/rest/remote/'
								});
								this.races = response.data;
								this.status = "races downloaded...cleaning up";
								var promises = [];
								for (var i=0; i < this.races.length; i++) {
									promises.push(Promise.all(this.toggleAll(this.races[i], false)));
								}
								Promise.all(promises).then(()=>{this.status = ""});
						}
					});	
			} catch (err) {
				console.log(err.response);
							
			}	
		},
		async getChanges() {
            try {
				this.status = "Saving";
				await this.save(true);
				this.status = "Getting Changes";
                const response = await axios({
                    url: 'getChanges/',
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
                this.races = response.data;
				var promises = [];
				for (var i=0; i < this.races.length; i++) {
					promises.push(Promise.all(this.toggleAll(this.races[i], false)));
				}
				Promise.all(promises).then(()=>{this.status = ""});

            } catch (err) {
                console.log(err.response);
                
            }
		},	
		async getResults() {
            try {
				this.status = "Saving";
				await this.save(true);
				this.status = "Getting Results";
                const response = await axios({
                    url: 'getResults/',
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
                this.races = response.data;
				var promises = [];
				for (var i=0; i < this.races.length; i++) {
					promises.push(Promise.all(this.toggleAll(this.races[i], false)));
				}
				Promise.all(promises).then(()=>{this.status = ""});

            } catch (err) {
                console.log(err.response);
                
            }
		},			
		async calculate() {
            try {
				this.status = "Saving";
				await this.save(true);
				this.status = "Recalculating";
                const response = await axios({
                    url: 'calculate/' + this.options.distance + '/' + this.options.surface + '/' + this.options.condition,
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
                this.races = response.data;
				var promises = [];
				for (var i=0; i < this.races.length; i++) {
					promises.push(Promise.all(this.toggleAll(this.races[i], false)));
				}
				Promise.all(promises).then(()=>{this.status = ""});

            } catch (err) {
                console.log(err.response);
                
            }
		},
		async updatePPs() {
            try {
				this.status = "Saving";
				await this.save(true);
				this.status = "Updating";
                const response = await axios({
                    url: 'updatePPs',
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
                this.races = response.data;
				var promises = [];
				for (var i=0; i < this.races.length; i++) {
					promises.push(Promise.all(this.toggleAll(this.races[i], false)));
				}
				Promise.all(promises).then(()=>{this.status = ""});

            } catch (err) {
                console.log(err);
                
            }
		},		
		clearRaces() {
			this.$bvModal.msgBoxConfirm("Clear Races?")
				.then(confirmed => {
					if (confirmed) {
						this.races = [];
					}
				});		
				this.status="";
		},
		async updateCondition(race) {
            try {
				this.status = "Saving";
				await this.save(true);
				this.status = "Updating";
                const response = await axios({
                    url: 'setTrackCondition/' + race.raceNumber + '/' + race.trackCondition,
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
				this.races = response.data;
				this.status = "";
            } catch (err) {
                console.log(err.response);
                
            }		
		},
		async toggleOffTheTurf(race) {
            try {
				this.status = "Saving";
				await this.save(true);
				this.status = "Updating";
                const response = await axios({
                    url: 'toggleOffTheTurf/' + race.raceNumber,
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
				this.races = response.data;
				this.status = "";
            } catch (err) {
                console.log(err.response);
                
            }		
		},
		async toggleOntoAllWeather(race) {
            try {
				this.status = "Saving";
				await this.save(true);
				this.status = "Updating";
                const response = await axios({
                    url: 'toggleOntoAllWeather/' + race.raceNumber,
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
				this.races = response.data;
				this.status = "";
            } catch (err) {
                console.log(err.response);
                
            }		
		},		
		toggleAll(race, b) {
			var promises = [];
			_.each(race.entries, async function(horse){		
				try {
					horse._showDetails = b;
					var formData = new FormData();
					formData.append("raceNumber", race.raceNumber);
					formData.append("name", horse.name);
					promises.push(axios({
						url: 'toggleShowDetail',
						method: 'POST',
						baseURL: 'http://localhost:8080/jpp/rest/remote/',
						headers: {
							'Content-Type': 'multipart/form-data'
						},
						data: formData
					}));					
				} catch (err) {
					console.log(err);
					
				}           
			});
			return promises;
		},
		async setRaceNote(race) {
			if (race.note) {
				try {
					var formData = new FormData();
					formData.append("raceNumber", race.raceNumber);
					formData.append("note", race.note);
					return axios({
						url: 'setRaceNote',
						method: 'POST',
						baseURL: 'http://localhost:8080/jpp/rest/remote/',
						headers: {
							'Content-Type': 'multipart/form-data'
						},
						data: formData
					});

				} catch (err) {
					console.log(err);
					
				}
			}
		},	
		async setEntryNote(horse) {
			if (horse.note) {
				try {
					var formData = new FormData();
					formData.append("raceNumber", horse.raceNumber);
					formData.append("name", horse.name);
					formData.append("note", horse.note);
					return axios({
						url: 'setEntryNote',
						method: 'POST',
						baseURL: 'http://localhost:8080/jpp/rest/remote/',
						headers: {
							'Content-Type': 'multipart/form-data'
						},
						data: formData
					});

				} catch (err) {
					console.log(err);
				}
			}
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
				//minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				//maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},
		format2Places(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},		
		formatDate (date) {
			return new Date(date).toLocaleDateString();
			//return date[1] + "/" + date[2] + "/" + date[0];
		},	
		tmGenerate (results) {
			for (var i=0; i < this.races.length; i++) {
				for (var j=0; j < this.races[i].multiRaceWagers.length; j++) {
					this.races[i].multiRaceWagers[j] = results[i][j];
				}
			}
			this.races.splice();
		},
		comboCheck (arr, n) {
			console.log(arr);
			console.log(n);
			arr[n] = !arr[n];
		},
		async togglePick (horse) {
			try {
               var formData = new FormData();
                formData.append("raceNumber", horse.raceNumber);
                formData.append("name", horse.name);
                await axios({
                    url: 'togglePick',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });				
                horse.pick = false;

            } catch (err) {
                console.log(err.response);
                
            }
		},
		paceScenarioComment(scenario) {
			switch (scenario) {
				case "FAST_EARLY_PACE" :
					return 'These races will be run very fast to the second call because there are multiple “E” horses all trying to get the lead. Very often these “E” horses burn each other out setting the race up for a winner from off the pace. The first thing to do is to determine if one of the “E” horses is a good horse and has a strong BRIS or Hall Early Pace figure advantage over the other “E” horse(s). If there is such a powerful “E” horse, then you can forget the other “E” horses as they will probably finish out-of-the-money. If the dominant “E” horse is one of the top horses of all the horses in the race, it may well go gate-to-wire. However, such a dominant “E” horse has been severely compromised by all the pressure from the other “E” horse(s) in the race and could easily be passed by a horse coming from off the pace. If the dominant “E” horse is passed early in the stretch, it will most likely finish out of the money.'
					+ 'If there is no dominant “E” horse in the race, the chances are high that all the “E” horses will finish off the board. These races are generally won by good “EP” and “P” horses and sometimes by a good “S” horse. The key is to look for a good horse with the best Final Fraction pace rating.';
				case "LONE_EARLY_PACE" :
					return 'There may be no more dangerous horse than a good “E” or “EP” horse that figures to be loose on the lead. These horses are left alone to optimize the pace of the race for themselves. A good “E” or “EP” horse loose on the lead will, more often than not, win the race. Even apparently poor “E” and “EP” horses can “wake up” in this situation and unexpectedly wire the field at big mutuel payoffs. These apparently poor horses are a particularly good play if they have an ALL-Ways software workout rating of 1 or 2. As a “golden rule”, never ignore an “E” or “EP” horse in Lone Early Pace races. Look for the best good “P” and “S” horses to finish in-the-money. Pay particular attention to those with the best Final Fraction Pace figures.';
				case "HONEST_PACE" :
					return 'Most races fall into this category. While a “P” or “S” horse can certainly win such a race, they generally need a faster pace to set the race up for their late stretch run. These races tend to favor “E” and “EP” horses. In an E-EP shaped race, if a good “EP” horse has a Speed or Combined Pace rating better than the “E” horse, it will generally pass the “E” horse in the stretch run. If the “EP” horse also has an Early Pace rating that is superior to the “E” horse, it will generally pass the “E” horse early in the stretch run causing the “E” horse to finish off-the-board. EP-EP shape races lend themselves nicely to traditional form, class and speed handicapping. Note that a prevailing track pace bias tends to have the most influence on EP-EP races.';
				case 'SLOW_PACE' :
					return 'These races, as expected, tend to be run very slow to the second call because no horse in the field wants to be anywhere close to the lead. These races are generally won by good “P” or “S” horses with superior Combined Pace and/or Speed ratings. However, there is a solid spot play to look for that can yield large mutuel payoffs. While none of the horses wants the lead, one of them is going to get it and, more often than not, it is the “P” horse with the fastest Early Pace figure. Frequently, such a “P” horse is able to move into the lead by three to five lengths or more and hold on for a gate-to-wire win. An astute trainer will spot this opportunity and instruct the jockey to go right for the lead. You have probably heard the term “stole the race”. This often is what happens with a “P” horse in a Slow Pace race or a lone “EP” or “E” horse in a Lone Early Pace race as described earlier.';
			}
		},
		str_pad_left(string,pad,length) {
			return (new Array(length+1).join(pad)+string).slice(-length);
		},
		disableDates(ymd) {
			return this.ppDates.indexOf(ymd) ==  -1;
		},   
		m(date) {
            return {
                year: moment(date).year(),
                month: moment(date).month()+1,
                date: moment(date).date()
            };
        }		
	}
}
</script>

<style>

</style>