<template>
    <div>
		<navbar-view></navbar-view>
        <b-navbar id="nav" toggleable="sm" class="py-2" >						
            <b-navbar-nav small>			
                <b-nav-item @click="generatePDF()"><b-icon-file-pdf v-b-tooltip.hover.bottom title="Generate PDF"></b-icon-file-pdf></b-nav-item>             	
            </b-navbar-nav>   
            <b-navbar-nav class="mx-auto text-right">
                {{status}}
            </b-navbar-nav>                             
        </b-navbar>		
		<b-table
			id="summary"
			:items="races"
			:fields="cols"
		>
			<template #cell(AHorses)="row">
				<span v-html="ABCText('A',row.item.horses)"></span>
			</template> 
			<template #cell(BHorses)="row">
				<span v-html="ABCText('B',row.item.horses)"></span>
			</template> 
			<template #cell(CHorses)="row">
				<span v-html="ABCText('C',row.item.horses)"></span>
			</template> 
			<template #cell(Others)="row">
				<span v-html="otherText(row.item.horses)"></span>
			</template> 	
			<template #cell(pick)="row">
				<span v-html="pickText(row.item.horses)"></span>
			</template> 												
		</b-table>
    </div>
</template>

<script>
//import _ from 'underscore'
import axios from 'axios'
import NavbarView from '@/views/NavbarView'

export default {
	name: 'RacesView',
	components: {
		NavbarView
	},
	data () {
		return {
			races: [],
			status: null,
			cols: [
				{key: "raceNumber", label: "Race"},
				{key: "AHorses", label: "A"},
				{key: "BHorses", label: "B"},
				{key: "CHorses", label: "C"},
				{key: "Others", label: "Others"},
				{key: "pick", label: "Pick"},
			]
        }       
    },
	mounted() {
		this.getSelectionSummary();
	},
	methods : {
		async getSelectionSummary() {
			try {
				this.status = "Loading";
				const response = await axios({
					url: 'getSelectionSummary/',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.races = response.data;
				this.status = "Loaded";
			} catch (err) {
				console.log(err.response);
							
			}	
		},	
		generatedPDF() {
			//
		},
		format2Places(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},		
		display(horse) {
			return "<strong class='"
				+ ((horse.finishPosition == 1) ? "greenHighlight" : "")
				+ ((horse.finishPosition == 2) ? "yellowHighlight" : "")
				+ ((horse.finishPosition == 3) ? "blueHighlight" : "")
				+ "'>"
				+ horse.programNumber + "</strong> ("
				+ horse.bettingLine + " - " 
				//+ this.format2Places(horse.afv) + " - " 
				+ horse.mlodds
				+ ")";
		},
		ABCText (sel, horses) {
			var txt= "";
			for (var i = 0; i < horses.length; i++) {
				if (horses[i].selection == sel) txt += 
					((txt.length > 0) ? ", " : "")
					+ this.display(horses[i]);
			}
			return txt;
		},
		pickText (horses) {
			var txt= "";
			for (var i = 0; i < horses.length; i++) {
				if (horses[i].pick) txt += 
					this.display(horses[i]);
			}
			return txt;
		},		
		otherText (horses) {
			var txt="";
			for (var i = 0; i < horses.length; i++) {
				if (["A","B","C"].indexOf(horses[i].selection) == -1 && horses[i].bettingLine > 0) txt += 
					((txt.length > 0) ? ", " : "")
					+ this.display(horses[i]);
			}
			return txt;			
		}
	}
}
</script>
