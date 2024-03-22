<template>
	<b-row>
		<b-col cols="2">
			<h6 class="text-center">Speed</h6>
			<trend
				:data="speeds"
				:padding="8"
				:stroke-width="1"
				auto-draw
				:height="100"
				:min="0"
			>
			</trend>
		</b-col>
		<b-col cols="2">
			<h6 class="text-center">E1 Pace</h6>
			<trend
				:data="e1s"
				:padding="8"
				:stroke-width="1"
				auto-draw
				:height="100"
				:min="0"
			>
			</trend>
		</b-col>
		<b-col cols="2">
			<h6 class="text-center">E2 Pace</h6>
			<trend
				:data="e2s"
				:padding="8"
				:stroke-width="1"
				auto-draw
				:height="100"
				:min="0"
			>
			</trend>
		</b-col>
		<b-col cols="2">
			<h6 class="text-center">Late Pace</h6>
			<trend
				:data="lates"
				:padding="8"
				:stroke-width="1"
				auto-draw
				:height="100"
				:min="0"
			>
			</trend>
		</b-col>
		<b-col cols="2">
			<h6 class="text-center">Purse</h6>
			<trend
				:data="purses"
				:padding="8"
				:stroke-width="1"
				auto-draw
				:height="100"
				:min="0"
			>
			</trend>
		</b-col>
		<b-col cols="2">
			<h6 class="text-center">Class</h6>
			<trend
				:data="classes"
				:padding="8"
				:stroke-width="1"
				auto-draw
				:height="100"
				:min="0"
			>
			</trend>
		</b-col>										
	</b-row>
</template>

<script>
import Trend from  "vuetrend"
import _ from 'underscore'


export default {
    name: 'EntryExtraView',
    components: {
		Trend
    },
    props : ['entry'],
    data () {
		return {
			
        }
    },
	computed : {
		speeds () {
			return _.pluck(this.entry.pastPerformances, "brisspeedRating").reverse(); 
		},
		e1s () {
			return _.pluck(this.entry.pastPerformances, "e1").reverse(); 
		},	
		e2s () {
			return _.pluck(this.entry.pastPerformances, "e2").reverse(); 
		},	
		lates () {
			return _.pluck(this.entry.pastPerformances, "paceFigureLate").reverse(); 
		},
		purses () {
			return _.pluck(this.entry.pastPerformances, "purse").reverse(); 
		},
		classes () {
			return _.pluck(this.entry.pastPerformances, "speedPar").reverse(); 
		}	
	},
    methods : {


    }
}
</script>