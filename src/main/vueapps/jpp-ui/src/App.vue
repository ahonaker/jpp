<template>
	<div id="app">
		<b-container fluid>
			<router-view></router-view>	
		</b-container>
	</div>
</template>

<script>

export default {
	name: 'App',
	components: {
		
	},
	data () {
		return {
			
		}	
	},
	mounted() {

	},
	computed: {
		
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

.navbar-light .navbar-nav .nav-link {
	font-weight: bold;
}

table#pps .flip-list-move {
  transition: transform 1s;
}

.form-inline {
	flex-flow: row nowrap !important;
}

.chart-link {
	color: #0056b3;
}

.chart-link:hover {
	color: #0056b3;
	text-decoration: underline;
}

.fv {
	width: 150px !important;
}

.pp {
	font-size: xx-small;
}

.horse {
	font-size: small; 
}

.star {
	color: green;
	font-weight: bolder;
}

.winnerNextOut {
	color: green;
	font-weight: bolder;
	border: 1px solid green;
	border-radius: 15px;
	padding-left: 3px;
}

.placeNextOut { 
	text-decoration: underline;
}

.sup { 
	font-size: .50em;
	vertical-align: super;
	padding-left: 0px !important;
}


.brand {
	font-size: x-large; 
}

.raceDate {
	width: 198px !important;
}

.diff365 {
	border-top: solid orange;
}

.diff90 {
	border-top: solid purple;
}

.thisRace {
	border-top: solid blue;
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
