<template>
    <div>
        <navbar-view :status="status"></navbar-view>
        <b-row>
            <b-col>
                <b-form inline>
                    <label class="" for="chartTrack">Track</label>
                    <b-form-select
                        id="chartTrack"
                        class="mx-2"
                        :options="chartTracks"
                        v-model="track"
                        placeholder="Select Track"
                    ></b-form-select>

                    <b-form-datepicker 
                        value-as-date id="chartDate" 
                        v-model="chartDate" 
                        class="mx-2 raceDate"
                        :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                        :date-disabled-fn="disableDates"
                        no-highlight-today
                        hide-header
                    ></b-form-datepicker>

                    <b-button variant="primary" @click="getChart()">Load Chart</b-button>
                </b-form>						
            </b-col>
            <b-col class="text-right">
                <b-button v-if="this.chart" variant="primary" class="mr-2"  @click="saveNotes()">Save Notes</b-button>
				<b-button v-if="this.chart && chartReviewed" variant="success" @click="toggleChartReviewed">UnReview</b-button>
				<b-button v-if="this.chart && !chartReviewed" variant="danger" @click="toggleChartReviewed">Review</b-button>
            </b-col>
        </b-row>
        <b-tabs v-model="chartTabIndex" class="mt-2">
            <b-tab v-for="(race, racekey) in chart" :key="racekey">
                <template #title>
                    Race {{race.raceNumber}}&nbsp;
                </template>
                <chart-view :race="race" :starterFields="starterFields[racekey]" :charts="charts" @goToChart="goToChart"></chart-view>
            </b-tab>
        </b-tabs>
    </div>			
</template>

<script>
import ChartView from '@/components/ChartView'
import NavbarView from '@/views/NavbarView'

import axios from 'axios'
import _ from 'underscore'

export default {
	name: 'ChartsView',
	components: {
		ChartView, NavbarView
	},
	data () {
		return {
			chart: null,
			charts: [],
			chartDate: null,
			chartReviewed: false,
			chartTabIndex: 0,
			tracks: [],
			track: null,
            status: ""
		}	
	},
	async mounted() {
		await this.getCharts();
		this.getTracks();
        if (this.$route.params.track) {
			this.track = this.$route.params.track;
		}
        if (this.$route.params.year) {
            this.chartDate = new Date (this.$route.params.year, this.$route.params.month - 1, this.$route.params.day);
        }
        if (this.track && this.chartDate) {
            await this.getChart();
            this.chartTabIndex = this.$route.params.raceNumber - 1;
        }
	},
    created() {
        this.$watch(
             () => this.$route.params,
                async function () {
                    if (this.$route.params.track) {
                            this.track = this.$route.params.track;
                        }
                        if (this.$route.params.year) {
                            this.chartDate = new Date (this.$route.params.year, this.$route.params.month, this.$route.params.day);
                        }
                        if (this.track && this.chartDate) {
                            await this.getChart();
                            this.chartTabIndex = this.$route.params.raceNumber - 1;
                        }
                }
        )
    },    
	computed: {
		chartTracks() {
			return _.uniq(_.pluck(this.charts, "code"));
		},
		chartDates() {
            var str_pad_left = this.str_pad_left;
			if (!this.track) return [];
            var track = _.findWhere(this.charts, {code: this.track});
			return _.map(
                _.pluck(_.where(track.raceDates, {hasChartFlag: true}), "raceDate")
                , function (d) {
					return d[0] + "-" 
						+ str_pad_left(d[1],0,2)  + "-"
						+ str_pad_left(d[2],0,2); 
                }); 
		},
		starterFields() {
			var starterFields = [];

			for (var i = 0; i < this.chart.length; i++) {
				var fields=[];
				fields.push({key: "lastRaced", title: "Last Raced", label: "Last Raced/Next Out"}); 
				fields.push({key: "program", title: "Program", label: "Pgm"});
				fields.push({key: "horsename", title: "Horse Name and Jockey", label: "Horse Name (Jockey)"});
				fields.push({key: "medicationEquipment.text", title: "Medication and Equipment", label: "M/E"});
				fields.push({key: "postPosition", title: "Post Position", label: "PP"});
				if (this.chart[i].starters) {
					for (var j = 0; j < this.chart[i].starters[0].pointsOfCall.length; j++) {
						fields.push({
							key: "pointsOfCall" + j,
							label: this.chart[i].starters[0].pointsOfCall[j].text
						});
					}
				}
				fields.push({key: "odds", title: "Odds", label: "Odds"});
				fields.push({key: "comments", title: "Comments", label: "Comments"});
				fields.push({key: "note", title: "Note", label: "Note"});
				fields.push({key: "horseFlag", title: "Horse Flag", label: ""});
				fields.push({key: "raceFlag", title: "Race Flags", label: "Flags"});
				starterFields.push(fields);
			}
			return starterFields;
			
		}
	},
	methods: {	
		async getCharts() {
			try {
                this.status = "Loading";
				const response = await axios({
					url: 'getCharts/',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.charts = response.data;
				this.status = "";
			} catch (err) {
				console.log(err.response);
							
			}	
		},
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
		async getChart() {
			try {
				this.status = "Loading";
				const response = await axios({
					url: 'getChart/' + this.track + "/" + this.chartDate.getFullYear() + "/" + (this.chartDate.getMonth() + 1)+ "/" + this.chartDate.getDate(),
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.chart = response.data;
				var chartDate = [this.chartDate.getFullYear(), this.chartDate.getMonth()+1, this.chartDate.getDate()];
				var track = _.findWhere(this.charts, {code: this.track});
				this.chartReviewed = _.find(track.raceDates, function(d){				
					return (d.raceDate[0] == chartDate[0] && d.raceDate[1] == chartDate[1] && d.raceDate[2] == chartDate[2]);
				}).reviewedFlag;
				this.status = "";
			} catch (err) {
				console.log(err.response);
							
			}	
		},
		async saveNotes() {
            try {
				this.status = "Loading";
				var races = [];
				for (var i = 0; i < this.chart.length; i++) {
					var notes = [];
					for (var j = 0; j < this.chart[i].starters.length; j++) {
						if (this.chart[i].starters[j].note
							|| this.chart[i].starters[j].raceFlag != null 
							|| this.chart[i].starters[j].horseFlag)
							notes.push({
								program: this.chart[i].starters[j].program,
								name: this.chart[i].starters[j].horse.name,
								position: this.chart[i].starters[j].finishPosition,
								beatenLengths: (this.chart[i].starters[j].finishPosition == 1) ? 0 : this.chart[i].starters[j].pointsOfCall[this.chart[i].starters[j].pointsOfCall.length-1].relativePosition.totalLengthsBehind.lengths,
								note: this.chart[i].starters[j].note,
								raceFlag: this.chart[i].starters[j].raceFlag,
								horseFlag: this.chart[i].starters[j].horseFlag,
                                claimingPrice: this.chart[i].starters[j].claim ? this.chart[i].starters[j].claim.price : 0
							});
					}
					if (notes.length > 0)
						races.push({
							track: this.chart[i].track.code,
							raceDate: this.chart[i].raceDate,
							raceNumber: this.chart[i].raceNumber,
                            raceClassification: this.chart[i].conditions.raceClassification,
                            purse: this.chart[i].purse.value,
                            distance: this.chart[i].distance.value,
                            exactDistanceFlag: this.chart[i].distance.exact,
                            surface: this.chart[i].surface,
                            offTurfFlag: this.chart[i].offTurf,
                            trackCondition: this.chart[i].trackCondition,
							starters: notes
						});
				}
                var formData = new FormData();
                formData.append("notes", JSON.stringify(races));
                await axios({
                    url: 'saveNotes',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });
                //console.log(response);
				this.status = "";
            } catch (err) {
                console.log(err);
                
            }
		},		
		async toggleChartReviewed() {
			try {
				this.status = "Updating";
				await axios({
					url: 'toggleChartReviewed/' + this.track + "/" + this.chartDate.getFullYear() + "/" + (this.chartDate.getMonth() + 1)+ "/" + this.chartDate.getDate(),
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.chartReviewed = !this.chartReviewed;
				this.status = "";
			} catch (err) {
				console.log(err.response);
							
			}
		},			
		str_pad_left(string,pad,length) {
			return (new Array(length+1).join(pad)+string).slice(-length);
		},
		disableDates(ymd) {
			return this.chartDates.indexOf(ymd) ==  -1;
		},        
        goToChart(e) {
            console.log(e);
        }
    }
}
</script>