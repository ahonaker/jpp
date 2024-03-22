<template>
    <span>
        <navbar-view :status="status"></navbar-view>
        <b-navbar id="nav" toggleable="sm" class="py-2" >						
            <b-navbar-nav small>			
                <b-nav-item @click="generatePDFAll()"><b-icon-file-pdf v-b-tooltip.hover.bottom title="Generate All PDFs"></b-icon-file-pdf></b-nav-item>
                <b-nav-item-dropdown
                    :disabled="!numRaces"
                    id="my-nav-dropdown"
                    text="Print Race PDF"
                    toggle-class="nav-link-custom"
                    right
                >
                    <b-dropdown-item v-for="raceNumber in numRaces" :key="raceNumber" @click="generatePDF(raceNumber)">Race {{raceNumber}}</b-dropdown-item>
                </b-nav-item-dropdown>    
                <b-nav-form>
                    <b-form-checkbox v-model="summaryOnly">Summary Only</b-form-checkbox>                    
                </b-nav-form>        	
            </b-navbar-nav>                              
        </b-navbar>

        <span v-if="race">
            <span id="raceToPrint" style="width: 1400px">
                <race-print-view :race="race" class="my-2" style="width: 1400px"></race-print-view>
                <entries-print-view :race="race" :class="entriesClass" style="width: 1400px"></entries-print-view>
                <span v-if="!summaryOnly">
                    <entry-print-view v-for="entry in race.entries" :race="race" :key="entry.name" :entry="entry" :tracks="tracks" class="my-2" style="width: 1400px"></entry-print-view>
                </span>
            </span>
        </span>
    </span>
</template>

<script>
import NavbarView from '@/views/NavbarView'
import RacePrintView from '@/components/RacePrintView'
import EntriesPrintView from '@/components/EntriesPrintView'
import EntryPrintView from '@/components/EntryPrintView'

import {  BIconFilePdf  } from 'bootstrap-vue'
import axios from 'axios'
import html2pdf from 'html2pdf.js'

export default {
	name: 'PrintView',
	components: {
        NavbarView, RacePrintView, EntriesPrintView, EntryPrintView, BIconFilePdf
	},    
    data () {
		return {
            tracks: [],
            numRaces: null,
			race: null,
            status: "",
            summaryOnly: false,
            options: {
                margin: 10,

                image: {
                    type: 'jpeg', 
                    quality: 0.98
                },

                enableLinks: false,

                pagebreak: { mode: 'avoid-all', after: '.page1' },

                html2canvas: {
                    scale: 2,
                    width: 1400,
                    logging: false
                },

                jsPDF: {
                    unit: 'px',
                    format: [1400, 1811],
                    orientation: 'portrait',
                    compress: true
                },
                hotfixes : ["px_scaling"]
            }
        }
    },
	mounted() {
        this.getTracks();
		this.getNumRaces();
	},
    computed: {
        entriesClass() {
            return "my-2" + (this.summaryOnly ? "" : " page1");
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
				this.loadingTracks = false;
			} catch (err) {
				console.log(err.response);
							
			}	
		},			
        async getNumRaces() {
            try {
				this.status = 'Getting number of races';
                const response = await axios({
                    url: 'getNumRaces',
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
                //console.log(response);
				this.numRaces = response.data;
				this.status += "..." + this.numRaces + " races";
            } catch (err) {
                console.log(err);
                
            }  
        },
        async get(raceNumber) {
            try {
				this.status = 'Getting Race ' + raceNumber;
                const response = await axios({
                    url: 'get/' + raceNumber,
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
                //console.log(response);
				this.race = response.data;
				this.status += "...Loaded";
            } catch (err) {
                console.log(err);
                
            }   
        },    
        async generatePDF(raceNumber) {     
            try {
                await this.get(raceNumber);
                var race = this.race;
                this.status = "Printing Race " + raceNumber;
                const response = await html2pdf()
                    .set(this.options)
                    .from(document.getElementById('raceToPrint'))
                    .save(race.track + this.formatDate(race.date) + "-" + race.raceNumber);
                if (!response) this.status += "...done";
            } catch (err) {
                console.log(err.response);
            }
        },
        async generatePDFAll() {     
            for (var i = 1; i <= this.numRaces; i++) {
                await this.generatePDF(i);
            }
        },
        formatDate (date) {
            var d  = new Date(date);
			return "" + d.getFullYear() + this.str_pad_left(d.getMonth(),0,2) + this.str_pad_left(d.getDate(),0,2);
		}, 
		str_pad_left(string,pad,length) {
			return (new Array(length+1).join(pad)+string).slice(-length);
		},        
    }
}
</script>

<style>
.html2canvas-container { 
    width: 1400px; 
}

</style>