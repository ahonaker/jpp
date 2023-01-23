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
            </b-navbar-nav>                              
        </b-navbar>

        <span v-if="race">
            <span id="raceToPrint" style="width: 1400px">
                <race-print-view :race="race" class="my-2" style="width: 1400px"></race-print-view>
                <horses-print-view :race="race" class="my-2 page1" style="width: 1400px"></horses-print-view>
                <horse-print-view v-for="horse in race.horses" :race="race" :key="horse.name" :horse="horse" :charts="charts" class="my-2" style="width: 1400px"></horse-print-view>
            </span>
        </span>
    </span>
</template>

<script>
import NavbarView from '@/views/NavbarView'
import RacePrintView from '@/components/RacePrintView'
import HorsesPrintView from '@/components/HorsesPrintView'
import HorsePrintView from '@/components/HorsePrintView'

import {  BIconFilePdf  } from 'bootstrap-vue'
import axios from 'axios'
import html2pdf from 'html2pdf.js'

export default {
	name: 'PrintView',
	components: {
        NavbarView, RacePrintView, HorsesPrintView, HorsePrintView, BIconFilePdf
	},    
    data () {
		return {
            numRaces: null,
			race: null,
            status: "",
            charts: [],
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
		this.getNumRaces();
	},
    methods: {
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
			return "" + date[0] + this.str_pad_left(date[1],0,2) + this.str_pad_left(date[2],0,2);
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