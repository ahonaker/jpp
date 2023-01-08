<template>
    <b-tabs>
        <b-tab v-for="(race, racekey) in races" :key="racekey">
            <template #title>
                Race {{race.raceNumber}}&nbsp;
            </template>

            <div class="card-body">
                    <b-row>
                        <b-col class="text-left">
                            <h5 class="card-title">
                                <span>Race {{race.raceNumber}}</span>
                                <small v-b-toggle="'collapse-'+race.race_id"> - <u>show/hide details</u></small>
                                
                            </h5>
                        </b-col>
                        <b-col v-if="hasResults[racekey]" class="text-right">
                            <a 
                                :href="'https://www.twinspires.com/bet/product/download/INC/TB/' 
                                    + race.track + '/'
                                    + race.date[0] + '-' + str_pad_left(race.date[1],0,2) + '-' + str_pad_left(race.date[2],0,2) 
                                    + '/D/' + race.raceNumber" 
                                target="_blank"            
                            ><b-icon-bar-chart-steps></b-icon-bar-chart-steps>
                            </a>&nbsp;
                            <a 
                                :href="'https://www.twinspires.com/bet/video/replay/'
                                    + race.date[0] + '-' + str_pad_left(race.date[1],0,2) + '-' + str_pad_left(race.date[2],0,2) 
                                    + '/' + race.track
                                    + '/Thoroughbred/' + race.raceNumber" 
                                target="_blank"  
                            ><b-icon-camera-video-fill></b-icon-camera-video-fill>
                            </a>
                        </b-col>
                    </b-row>
                    <b-collapse :id="'collapse-'+race.race_id" visible>
                        <b-row>
                            <b-col v-b-tooltip.hover.right :title="race.raceConditions" cols="5">
                                ${{race.purse}} <span  v-b-toggle="'collapseNotes-'+race.race_id">{{race.classification}}</span>
                            </b-col>
                            <b-col cols="2" class="text-center">
                                {{race.surface}} <span v-if="race.distance<0"><sup>*</sup></span>
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
                        </b-collapse>
                        <b-row>
                            <b-col cols="10">
                                {{race.wagerTypes}}
                            </b-col>
                            <b-col class="text-right">
                                {{race.postTimes}}
                            </b-col>
                        </b-row>
                    </b-collapse>
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
                            MaxE2Avg: {{race.e2Avg}}
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
                                sizae="sm"
                                v-model="race.note"
                                placeholder="Enter something..."
                                max-rows="4"
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
                            <b-form-checkbox switch class="m-2" v-if="race.surface.indexOf('TURF') > -1" v-model="race.offTheTurfFlag" @change="toggleOffTheTurf(race)">Off</b-form-checkbox>
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
            <horse-view :race="race" :hideML="hideML" @selectionUpdate="tmGenerate" @togglePick="togglePick"></horse-view>
        </b-tab>
    </b-tabs>
</template>

<script>
import { BIconPlus, BIconDash, BIconCashStack, BIconCloudUploadFill, BIconBarChartSteps, BIconCameraVideoFill } from 'bootstrap-vue'
import HorseView from '@/components/HorseView'

import axios from 'axios'
import _ from 'underscore'

export default {
	name: 'App',
	components: {
		HorseView, BIconPlus, BIconDash, BIconCashStack, BIconCloudUploadFill, BIconBarChartSteps, BIconCameraVideoFill
	},
    props: ['races'],
	data () {
		return {
			file: null,
			loading: false,
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
	computed: {
		hasResults() {
			var has = [];
			for (var i = 0; i < this.races.length; i++ ) {
				has[i] = false;
				for (var j =0; j < this.races[i].unscratchedHorses.length; j++) {
					if (this.races[i].unscratchedHorses[j].finishPosition == 1) has[i] = true;
				}
			}
			return has;
		},
		wPayout() {
			var payout = [];
			for (var i = 0; i < this.races.length; i++ ) {
				payout[i] = 0;
				for (var j =0; j < this.races[i].unscratchedHorses.length; j++) {
					if (this.races[i].unscratchedHorses[j].pick) payout[i] = this.races[i].unscratchedHorses[j].winPayout;
				}
			}	
			return payout;		
		},
		wpPayout() {
			var payout = [];
			for (var i = 0; i < this.races.length; i++ ) {
				payout[i] = 0;
				for (var j =0; j < this.races[i].unscratchedHorses.length; j++) {
					if (this.races[i].unscratchedHorses[j].pick) payout[i] = this.races[i].unscratchedHorses[j].winPayout
						+ this.races[i].unscratchedHorses[j].placePayout;
				}
			}	
			return payout;		
		},
		wpsPayout() {
			var payout = [];
			for (var i = 0; i < this.races.length; i++ ) {
				payout[i] = 0;
				for (var j =0; j < this.races[i].unscratchedHorses.length; j++) {
					if (this.races[i].unscratchedHorses[j].pick) payout[i] = this.races[i].unscratchedHorses[j].winPayout
						+ this.races[i].unscratchedHorses[j].placePayout
						+ this.races[i].unscratchedHorses[j].showPayout;
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
		load() {
			if (this.races.length == 0) {
				this.uploadAndCalculate();
			} else {
				this.$bvModal.msgBoxConfirm("Reload and Start Over?")
					.then(confirmed => {
						if (confirmed) {
							this.uploadAndCalculate();
						}
					});
			}
		},		
		async uploadAndCalculate() {
            try {
				this.loading = true;
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

				for (var i=0; i < this.races.length; i++) {
					this.combinations[i] = [];
					this.combinationsText[i] = []; 
					for (var j=0; j < this.races[i].multiRaceWagers.length; j++) {
						this.combinations[i][j] = {a: [], b:[], c:[]};
						this.combinationsText[i][j] = {tmA: "", tmAB: "", tmB1: "", tmB2: "", tmC1: ""};
					}
				}
				this.loading = false;
            } catch (err) {
                console.log(err);
                
            }
		},
		async parseChart() {
            try {
				this.loading = true;
                var formData = new FormData();
                formData.append("data", this.file);
                formData.append("filename", this.file.name);
                const response = await axios({
                    url: 'uploadChart',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });
                console.log(response);
				this.test = response.data;
				this.loading = false;
            } catch (err) {
                console.log(err);
                
            }
		},		
		async save() {
            try {
				this.loading = true;
                await axios({
                    url: 'save/' + this.file.name, 
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
				this.loading = false;
				this.$bvModal.msgBoxOk('Races saved.');									
            } catch (err) {
                console.log(err.response);
                
            }
		},
		retrieve() {
			try {
				this.$bvModal.msgBoxConfirm("Retrieve Saved Races?")
					.then(async confirmed => {
						if (confirmed) {
								this.loading = true;
								const response = await axios({
									url: 'retrieve/' + this.file.name,
									method: 'GET',
									baseURL: 'http://localhost:8080/jpp/rest/remote/'
								});
								this.races = response.data;
								this.loading = false;
						}
					});	
			} catch (err) {
				console.log(err.response);
							
			}	
		},
		async getChanges() {
            try {
				this.loading = true;
                const response = await axios({
                    url: 'getChanges/',
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
                this.races = response.data;
				this.loading = false;
            } catch (err) {
                console.log(err.response);
                
            }
		},	
		async getResults() {
            try {
				this.loading = true;
                const response = await axios({
                    url: 'getResults/',
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
                this.races = response.data;
				this.loading = false;
            } catch (err) {
                console.log(err.response);
                
            }
		},			
		async calculate() {
            try {
				this.loading = true;
                const response = await axios({
                    url: 'calculate/' + this.options.distance + '/' + this.options.surface + '/' + this.options.condition,
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
                this.races = response.data;
				this.loading = false;
            } catch (err) {
                console.log(err.response);
                
            }
		},
		async updateCondition(race) {
            try {
				this.loading = true;
                const response = await axios({
                    url: 'setTrackCondition/' + race.raceNumber + '/' + race.trackCondition,
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
				this.races = response.data;
				this.loading = false;
            } catch (err) {
                console.log(err.response);
                
            }		
		},
		async toggleOffTheTurf(race) {
            try {
				this.loading = true;
                const response = await axios({
                    url: 'toggleOffTheTurf/' + race.raceNumber,
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
				this.races = response.data;
				this.loading = false;
            } catch (err) {
                console.log(err.response);
                
            }		
		},
		toggleAll(race, b) {
			_.each(race.horses, async function(horse){		
				try {
					horse._showDetails = b;
					await axios({
						url: 'toggleShowDetail/' + race.raceNumber + '/' + horse.programNumber,
						method: 'GET',
						baseURL: 'http://localhost:8080/jpp/rest/remote/'
					});
				} catch (err) {
					console.log(err);
					
				}           
			});
		},
		async setRaceNote(race) {
            try {
				this.loading = true;
                var formData = new FormData();
                formData.append("raceNumber", race.raceNumber);
                formData.append("note", race.note);
                await axios({
                    url: 'setRaceNote',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });
                //console.log(response);
				this.loading = false;
            } catch (err) {
                console.log(err);
                
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
				console.log(horse);
                await axios({
                    url: 'togglePick/' + horse.raceNumber + '/' + horse.programNumber,
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
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
		}		
	}
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

.navbar-light .navbar-nav .nav-link {
	font-weight: bold;
}

table#pps .flip-list-move {
  transition: transform 1s;
}

#filenameform {
	width: 400px;
}

.pp {
	font-size: xx-small;
}

.horse {
	font-size: small; 
}

.diff365 {
	border-top: solid orange;
}

.diff90 {
	border-top: solid purple;
}

.workout {
	white-space: pre;
}

.alsoInRace, .strong {
	font-weight: bold;
}

.scratched, .ignored {
	text-decoration: line-through;
}

.scratchedDetail {
	background: gray;
}

.aHorse {
	border-left: solid thick greenyellow;
	border-right: solid thick greenyellow;
}

.bHorse {
	border-left: solid thick cornflowerblue;
	border-right: solid thick cornflowerblue;
}

.cHorse {
	border-left: solid thick gold;
	border-right: solid thick gold;
}

.xHorse {
	border-left: solid thick darkslategray;
	border-right: solid thick darkslategray;
}

.greenHighlight {
	background-color: greenyellow;
}

.lightGreenHighlight {
	background-color: rgb(223, 249, 184);
}

.lightGreenHighlightBorder {
	border-bottom: solid greenyellow;
}

.blueHighlightBorder {
	border-bottom: solid lightblue;
}

.blueHighlight {
	background-color: lightblue;
}

.gt180, redHighlight {
	background-color: red;
}

.gt90, lightRedHighlight {
	background-color: rgb(231, 194, 194)
}

.gt45, .yellowHighlight  {
	background-color: yellow;
}
</style>
