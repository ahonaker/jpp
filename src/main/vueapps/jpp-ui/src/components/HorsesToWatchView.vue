<template>
    <div>
        <b-row>
            <b-col cols="6">
                <b-form inline>
                    <label class="sr-only" for="name">Name</label>
                    <b-form-input
                        id="name"
                        class="mb-2 mr-sm-2 mb-sm-0"
                        placeholder="Filter Horse Name"
                        v-model="filter"
                    ></b-form-input>
                </b-form>
            </b-col>
            <b-col class="text-right">
                <b-button variant="primary" @click="getHorsesToWatch()" class="mx-2">Refresh</b-button>
                <b-button variant="primary" @click="save()">Save</b-button>
            </b-col>
        </b-row>
        <b-row class="mt-2">
            <b-card
                v-for="horse in filtered" :key="horse.name"
                tag="article"
                style="min-width: 300px;"
                class="m-2"
            >
                <template #header>
                    <h6 class="mb-0">
                        <b-row>
                            <b-col>
                                <b-icon-star-fill variant="success" v-if="horse.flag =='Star'" @click="horse.flag = ''"></b-icon-star-fill>
                                <b-icon-star v-else @click="horse.flag = 'Star'"></b-icon-star>
                                {{horse.name}}
                            </b-col>
                            <b-col class="text-right">
                                <b-icon-trash variant="danger" @click="removeHorse(horse)"></b-icon-trash>
                            </b-col>
                        </b-row>
                    </h6>
                </template>

                <b-form-textarea
                    v-model="horse.comment"
                    placeholder="Enter something..."
                    max-rows="4"
                ></b-form-textarea>

                <b-table 
                    :items="sortedRaceNotes(horse.raceNotes)"
                    :fields="horseFields"
                    small
                    class="mx-2"
                >
                    <template #cell(raceDate)="row">
                        {{formatDate(row.item.raceDate)}}<sup>{{row.item.raceNumber}}</sup> {{row.item.track}}
                    </template>
                    <template #cell(position)="row">
                        {{row.item.position}}<sup v-if="row.item.beatenLengths >0">{{row.item.beatenLengths}}</sup>
                    </template>
                    <template #cell(deleteButton)="row">
                        <b-icon-trash variant="danger" @click="removeRaceNote(horse, row.item)"></b-icon-trash>
                    </template>                    
                </b-table>

            </b-card>            
        </b-row>
    </div>
</template>

<script>
import { BIconStar, BIconStarFill, BIconTrash } from 'bootstrap-vue'
import axios from 'axios'
import _ from 'underscore'

export default {
    name: 'HorsesToWatchView',
    components: {
		BIconStar, BIconStarFill, BIconTrash
    },
    props : [],
    data () {
		return {
            horsesToWatch: [],
			filter: '',
            horseFields: [
                {key: "raceDate", label: "Race"},
                {key: "position" , label: "Fin"},
                {key: "comment"},
                {key: "flag"},
                {key: 'deleteButton', label: ""}
            ]
        }
    },
    mounted() {
		this.getHorsesToWatch();
    },
    computed: {
        filtered () {
            var filter = this.filter;
            var dateToNumber = this.dateToNumber;
            return _.sortBy(_.filter(this.horsesToWatch, function(h){
                return h.name.toLowerCase().indexOf(filter.toLowerCase()) > -1 || filter == '';
            }), function (h) {
                var s = 0;              
                if (h.flag != "Star") s += 10000000;
                if (h.raceNotes.length == 0) return s;
                return s + dateToNumber(h.raceNotes[h.raceNotes.length - 1].raceDate);
            });
        }
    },
    methods: {
		async getHorsesToWatch() {
			try {
				const response = await axios({
					url: 'getHorsesToWatch/',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.horsesToWatch = response.data;
			} catch (err) {
				console.log(err.response);
							
			}	
		},
		async save() {
            try {
				this.loading = true;
                var formData = new FormData();
                formData.append("data", JSON.stringify(this.horsesToWatch));
                await axios({
                    url: 'saveHorsesToWatch',
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
        sortedRaceNotes(raceNotes) {
            var dateToNumber = this.dateToNumber;
            return _.sortBy(raceNotes, function(n){
                return dateToNumber(n.raceDate) * -1;
            });
        },
        dateToNumber (date) {
            return date[0] * 10000 + date[2] * ( 100) + date[1];
        },
		formatDate (date) {
			return date[1] + "/" + date[2] + "/" + date[0];
		},
        removeHorse (horse) {
            this.horsesToWatch = _.reject(this.horsesToWatch, function(h){
                return h.name == horse.name;
            })
        },
        removeRaceNote (horse, note) {
            horse.raceNotes = _.reject(horse.raceNotes, function(n){
                return n.raceDate == note.raceDate && n.track == note.track && n.raceNumber == note.raceNumber;
            })
        },

    }
}
</script>
