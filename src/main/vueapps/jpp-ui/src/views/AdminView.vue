<template>
    <div>
		<navbar-view></navbar-view>
        <b-row>
            <b-col>
                <b-button variant="primary" @click="parseCharts">Parse Charts</b-button>
            </b-col>
            <b-col>
				<b-form-file
					id="filenameform"
					size="sm" 
					v-model="file"
					:state="Boolean(file)"
					placeholder="Choose or drop here..."
					drop-placeholder="Drop file here..."
					class="text-left"
				></b-form-file>
            </b-col>
            <b-col>				
				<b-button variant="secondary" @click="extractPP">Extract PP</b-button>
            </b-col>
            <b-col cols="4" class="text-center">
                <h5>Race Dates</h5>
                <b-button variant="primary" @click="saveDates()" class="my-2">Save Dates</b-button>
                <b-form inline>
                    <label class="" for="chartTrack">Track</label>
                    <b-form-select
                        id="chartTrack"
                        class="mx-2"
                        :options="chartTracks"
                        v-model="track"
                        placeholder="Select Track"
                    ></b-form-select>

                    <b-calendar
                        class="mx-2 raceDate"
                        :date-info-fn="raceDatesClass"
                        selected-variant="x"
                        @selected="addRemoveRaceDate"
                        :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                        no-highlight-today
                        hide-header
                    ></b-calendar>			
                </b-form>	
                
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <span v-for="(line, index) in pp" :key="index">{{line}}<br></span>
            </b-col>
        </b-row>
    </div>    
</template>

<script>

import _ from 'underscore'
import axios from 'axios'
import NavbarView from '@/views/NavbarView'

export default {
	name: 'RacesView',
	components: {
		NavbarView
	},
	data () {
		return {
			track: null,
			file: null,
			pp: null,
			charts: [],
			tracks: []
        }
        
    },
	mounted() {
		this.getCharts();
		this.getTracks();
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
		reviewedDates() {
            var str_pad_left = this.str_pad_left;
			if (!this.track) return [];
            var track = _.findWhere(this.charts, {code: this.track});
			return _.map(
                _.pluck(_.where(track.raceDates, {reviewedFlag: true}), "raceDate")
                , function (d) {
					return d[0] + "-" 
						+ str_pad_left(d[1],0,2)  + "-"
						+ str_pad_left(d[2],0,2); 
                });
            
		},	
		raceDates() {
            var str_pad_left = this.str_pad_left;
			if (!this.track) return [];
            var track = _.findWhere(this.charts, {code: this.track});
			return _.map(
                _.pluck(track.raceDates, "raceDate")
                , function (d) {
					return d[0] + "-" 
						+ str_pad_left(d[1],0,2)  + "-"
						+ str_pad_left(d[2],0,2); 
                });
            
		}		
	},
    methods: {
		async getCharts() {
			try {
				const response = await axios({
					url: 'getCharts/',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.charts = response.data;
				this.loadingCharts = false;
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
				this.loadingTracks = false;
			} catch (err) {
				console.log(err.response);
							
			}	
		},			
		async parseCharts() {
            try {
				this.loading = true;
                await axios({
                    url: 'parseDirectory',
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.loading = false;
				this.getCharts();
            } catch (err) {
                console.log(err);
                
            }
		},	
		async extractPP() {
            try {
				this.loading = true;
                var formData = new FormData();
                formData.append("data", this.file);
                formData.append("filename", this.file.name);
                const response = await axios({
                    url: 'extractPP',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });
                //console.log(response.data);
				this.pp = response.data;
				this.loading = false;
            } catch (err) {
                console.log(err);
                
            }
		},
		str_pad_left(string,pad,length) {
			return (new Array(length+1).join(pad)+string).slice(-length);
		},		
		raceDatesClass(ymd) {	
			
			if (this.reviewedDates.indexOf(ymd) > -1) return 'table-success';
			if (this.chartDates.indexOf(ymd) > -1) return 'table-danger';
			if (this.raceDates.indexOf(ymd) > -1) return 'table-info';
		},   
		addRemoveRaceDate(ymd,date) {
			const day = [date.getFullYear(), date.getMonth()+1, date.getDate()];

			if (this.raceDates.indexOf(ymd) ==  -1) {
				var found = false;
				for (var i = 0; i < this.tracks.length; i++) {
					if (this.tracks[i].code == this.track) {
						found = true;
						this.tracks[i].raceDates.push({raceDate: day, reviewedFlag: false, hasChartFlag: false});
					}
				}
				if (!found) {
					this.tracks.push({
						code: this.track,
						raceDates: [{raceDate: day, reviewedFlag: false, hasChartFlag: false}]
					});
				}
			} else {
				for (i = 0; i < this.tracks.length; i++) {
					if (this.tracks[i].code == this.track) {
						this.tracks[i].raceDates = _.reject(this.tracks[i].raceDates, function(d){
							return (d.raceDate[0] == day[0] && d.raceDate[1] == day[1] && d.raceDate[2] == day[2]);
						})
					}
				}
			}
		}		     
    }
}
</script>
