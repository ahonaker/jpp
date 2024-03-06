<template>
    <div>
        <navbar-view :status="status"></navbar-view>
        <b-row v-show="!horse">
            <b-col>
                <b-form inline>
                    <b-form-input v-model="search" placeholder="Search for horse" debounce="500"></b-form-input>
					<b-button variant="outline-secondary" size="sm" class="mx-2" @click="search=''">Clear</b-button>
					<b-form-checkbox v-model="listStarred" class="mr-2" >Show Starred</b-form-checkbox>
					Showing {{horsesFiltered.length}} of&nbsp;<span v-if="horses.length == 0">...</span><span v-else>{{horses.length}}</span>
                </b-form>						
            </b-col>
        </b-row>
		<b-card v-if="horse"
			class="mb-2"
		>	
			<b-card-title>
				<b-icon-star-fill variant="success" v-if="horse.flag == 'Star'" @click="horse.flag = ''" class="mx-1"></b-icon-star-fill>
				<b-icon-star variant="secondary" v-else @click="horse.flag = 'Star'" class="mx-1"></b-icon-star>
				{{horse.name}}
			</b-card-title>
			<b-card-text>
				<b-form-textarea
					v-model="horse.comment"
					placeholder="Enter something..."
					max-rows="4"
					debounce="500" 
				></b-form-textarea>	
				<b-list-group flush>
					<b-list-group-item v-for="(note, ndx) in horse.raceNotes" :key="ndx" class="flex-column align-items-start">
						<b-link 
							class="chart-link"
							target="_blank"
							:to="'/charts/'+note.track+'/'+note.raceDate[0]+'/'+note.raceDate[1]+'/'+note.raceDate[2]+'/'+note.raceNumber"
						><b-icon-bar-chart-steps class="mx-1"></b-icon-bar-chart-steps>
						</b-link>
						<a 
							:href="'https://www.twinspires.com/bet/video/replay/'
								+ note.raceDate[0] + '-' + str_pad_left(note.raceDate[1],0,2) + '-' + str_pad_left(note.raceDate[2],0,2) 
								+ '/' + note.track
								+ '/Thoroughbred/' + note.raceNumber" 
							target="_blank"  
						><b-icon-camera-video-fill></b-icon-camera-video-fill>
						</a>						
						{{formatDate(note.raceDate)}} &nbsp;&nbsp; {{note.track}}<sup>{{note.raceNumber}}</sup> &nbsp;&nbsp; {{note.position}} <span v-if="note.beatenLengths > 0"> - {{note.beatenLengths}}BL</span>
						&nbsp;&nbsp; {{note.type}}  &nbsp;&nbsp; {{note.raceClassification}} Purse: ${{note.purse}} <span v-if="note.claimingPrice > 0"> &nbsp;&nbsp; Claiming Price: ${{note.claimingPrice}}</span>
						<br>
						<strong>{{note.flag}} </strong>
						<span v-if="note.flag">/ </span>
						<strong>{{note.comment}}</strong>
						<span v-if="note.flag || note.comment"> : </span>
						<p class="mb-1">{{note.footnote}}</p>
					</b-list-group-item>
				</b-list-group>
			</b-card-text>
			<b-button href="#" variant="primary" @click="save" class="mx-2">Save and Go Back</b-button>
			<b-button href="#" variant="danger" @click="horse = null">Discard Changes</b-button>
		</b-card>
		<b-row v-if="horse && horse.pastPerformances"  class="table b-table table-sm">
			<b-col>
				<h5 class="text-center">Past Performances</h5>
				<b-container fluid class="pp border">
					<past-performance-view :horse="horse"></past-performance-view>
				</b-container>
			</b-col>
		</b-row>
		<b-list-group v-show="!horse">
			<b-list-group-item v-for="listedHorse in horsesFiltered" :key="listedHorse.name" @click="getHorse(listedHorse.name)" href="#">
				<b-icon-star-fill variant="success" v-if="listedHorse.flag == 'Star'" class="mx-1"></b-icon-star-fill>{{listedHorse.name}}<span v-if="listedHorse.comment">&nbsp;&nbsp;: {{listedHorse.comment}}</span>
			</b-list-group-item>
		</b-list-group>
    </div>			
</template>

<script>
import NavbarView from '@/views/NavbarView'
import PastPerformanceView from '@/components/PastPerformanceView'
import { BIconStar, BIconStarFill, BIconCameraVideoFill, BIconBarChartSteps} from 'bootstrap-vue'


import axios from 'axios'
import _ from 'underscore'

export default {
	name: 'HorsesView',
	components: {
		NavbarView, PastPerformanceView, BIconStar, BIconStarFill, BIconCameraVideoFill, BIconBarChartSteps
	},
	data () {
		return {
			horse: null,
			horses: [],
            search: "",
			listStarred: false,
            status: ""
		}	
	},
	async mounted() {
		await this.getHorses();
	},
    created() {
        this.$watch(
             () => this.$route.params,
                async function () {
                    if (this.$route.params.horse) {
                            // get horse
                        }
                }
        )
    },    
	computed: {
        horsesFiltered() {
            if (this.search == "" && !this.listStarred) return [];
            var search = this.search;
			var listStarred = this.listStarred;
            return _.filter(this.horses, function(h){
				if (search == '') return h.flag == "Star";
				var starred = (h.flag == 'Star');
                var filtered =  h.name.toLowerCase().includes(search.toLowerCase());
				if (listStarred) {
					return (starred && filtered);
				} else {
					return (filtered);
				}
            }).sort();
        }
    },
	methods: {	
		async getHorses() {
			try {
                this.status = "Loading";
				const response = await axios({
					url: 'getHorses',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.horses = response.data;
				this.status = "";
			} catch (err) {
				console.log(err.response);
							
			}	
		},	
		async getHorse(name) {
			try {
				this.status = "Loading";
				const response = await axios({
					url: 'getHorse/' + name,
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.horse = response.data;
				this.status = "";
			} catch (err) {
				console.log(err.response);
							
			}	
		},
        async save() {
            try {
				this.status = "Saving";
                var formData = new FormData();
                formData.append("data", JSON.stringify(this.horse));
                await axios({
                    url: 'saveHorse',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });
                this.status = "";
				this.horse = null;
            } catch (err) {
                console.log(err.response);
                
            }               
        },		 		
		str_pad_left(string,pad,length) {
			return (new Array(length+1).join(pad)+string).slice(-length);
		},
		formatDate (date) {
			return new Date(date).toLocaleDateString();
			//return date[1] + "/" + date[2] + "/" + date[0];
		},			
    }
}
</script>