<template>
    <b-badge variant="light" border-variant="dark" class="p0">
        <b-icon-circle-fill v-if="workout.timeOfWorkout < 0"></b-icon-circle-fill>
        {{workout.dateOfWorkoutString}} {{workout.trackOfWorkout}} {{workout.furlongs}}f <span v-if="workout.trackIndicator == 'MAIN_DIRT'"></span><span v-else-if="workout.trackIndicator == 'MAIN_TURF'"><b-badge variant="secondary">T</b-badge></span><span v-else-if="workout.trackIndicator == 'TRAINING_TRACK'">tr.t</span><span v-else>{{workout.trackIndicator}} </span>
        <span :class="fastWorkout(workout)">
        {{Math.floor( Math.abs(workout.timeOfWorkout) / 60)}}:{{str_pad_left(Math.floor(Math.abs(workout.timeOfWorkout) - Math.floor(Math.abs(workout.timeOfWorkout) / 60) * 60),'0',2)}}<sup v-if="(Math.floor((Math.abs(workout.timeOfWorkout) % 1) * 5) / 5).toFixed(1) * 5 != 0">{{(Math.floor((Math.abs(workout.timeOfWorkout) % 1) * 5) / 5).toFixed(1) * 5}}</sup>
        </span>
        {{workout.description}} {{workout.rank}}/{{workout.numberOfWorks}}&nbsp;&nbsp;&nbsp;
    </b-badge>
</template>

<script>
import { BIconCircleFill } from 'bootstrap-vue'

export default {
    name: 'WorkoutView',
    components: {
		BIconCircleFill
    },
    props : ['workout'],
    data () {
		return {
        
        }
    },
    methods : {
		fastWorkout(workout) {
            if (workout.furlongs >= 4 && workout.furlongs <= 5 && Math.abs(workout.timeOfWorkout) / workout.furlongs < 12) return "greenHighlight";
            if (workout.furlongs == 6 && Math.abs(workout.timeOfWorkout) / workout.furlongs < 63) return "greenHighlight";
            if (workout.furlongs == 7 && Math.abs(workout.timeOfWorkout) / workout.furlongs < 77) return "greenHighlight";
			if (workout.furlongs > 3 && Math.floor(Math.abs(workout.timeOfWorkout)) ==  12 * workout.furlongs) return "lightGreenHighlightBorder";
		},
		str_pad_left(string,pad,length) {
			return (new Array(length+1).join(pad)+string).slice(-length);
		},	

    }
}
</script>