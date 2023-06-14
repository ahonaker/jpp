/* eslint-disable vue/valid-v-slot */
<template>
    <div class="card-body mt-2">
		<a 
            :href="'https://www.twinspires.com/bet/video/replay/'
                + race.raceDate[0] + '-' + str_pad_left(race.raceDate[1],0,2) + '-' + str_pad_left(race.raceDate[2],0,2) 
                + '/' + race.track.code
                + '/Thoroughbred/' + race.raceNumber" 
            target="_blank"  
			><b-icon-camera-video-fill></b-icon-camera-video-fill>
		</a>&nbsp;
        <strong>{{race.track.name}} {{formatDate(race.raceDate)}} Race {{race.raceNumber}} <b-icon-key v-if="race.keyRace != null" variant="success" font-scale="1.5" rotate="90"></b-icon-key></strong><br>
        <b-alert variant="danger" :show="race.cancelled">Race Canceled</b-alert>
        <div v-if="!race.cancelled">
            {{race.conditions.type}}<br>
            {{race.conditions.text}}<br>
            {{race.distance.text}} on the {{race.surface}}<span v-if="race.offTurf"> (Originally scheduled for {{race.scheduledSurface}})</span><br>
            <strong>Purse:</strong> {{race.purse.text}}<br>
            <span v-for="(enhancement, eindex) in race.purse.enhancements" :key="eindex"><strong>{{enhancement.type}}:</strong> {{enhancement.text}} </span><br>
            <strong>Available Money:</strong> {{race.purse.availableMoney}}<br>
            <strong>Weather:</strong> {{race.weather.text}} <strong>Track:</strong> {{race.trackCondition}}<br>
            <strong>Off at:</strong> {{race.postTime}} <strong>Start:</strong> {{race.startComments}} <strong>Timer:</strong> {{race.timer}}
            <b-table
                id="runningLines"
                :items="race.starters"
                :fields="starterFields"
                small
                primary-key="program"
                class="mt-2 horse"
            >
                <template #cell(lastRaced)="row">
                    <b-link 
                        v-if="hasChart(row.item.lastRaced)" 
                        target="_blank" 
                        :to="'/charts/'+row.item.lastRaced.track.code+'/'+row.item.lastRaced.raceDate[0]+'/'+row.item.lastRaced.raceDate[1]+'/'+row.item.lastRaced.raceDate[2]+'/'+row.item.lastRaced.raceNumber"
                    >
                        <b-icon-bar-chart-steps></b-icon-bar-chart-steps>
                    </b-link>
                    <span v-if="row.item.lastRaced && row.item.lastRacedKeyRace != null" v-b-popover.hover.html.right="keyRaceHorsesFormat(row.item.lastRacedKeyRace.horses)" :title="row.item.lastRacedKeyRace.track + ' ' + formatDate(row.item.lastRacedKeyRace.raceDate) + ' Race ' + row.item.lastRacedKeyRace.raceNumber">
                        {{formatDate(row.item.lastRaced.raceDate)}}<sup>{{row.item.lastRaced.raceNumber}}</sup> {{row.item.lastRaced.track.code}}<sup>{{row.item.lastRaced.officialPosition}}</sup>
                        <b-icon-key v-if="row.item.lastRacedKeyRace != null" variant="success" font-scale="1.5" rotate="90"></b-icon-key>
                    </span>
                    <span v-if="row.item.lastRaced && row.item.lastRacedKeyRace == null">
                        {{formatDate(row.item.lastRaced.raceDate)}}<sup>{{row.item.lastRaced.raceNumber}}</sup> {{row.item.lastRaced.track.code}}<sup :class="{'font-weight-bold': row.item.lastRaced.officialPosition == 1}">{{row.item.lastRaced.officialPosition}}</sup>
                    </span>
                    <span v-if="row.item.nextOutRaceNote != null">
                        <br>
                        <b-link 
                            v-if="hasChart(row.item.nextOutRaceNote)" 
                            target="_blank" 
                            :to="'/charts/'+row.item.nextOutRaceNote.track+'/'+row.item.nextOutRaceNote.raceDate[0]+'/'+row.item.nextOutRaceNote.raceDate[1]+'/'+row.item.nextOutRaceNote.raceDate[2]+'/'+row.item.nextOutRaceNote.raceNumber"
                        >
                            <b-icon-bar-chart-steps></b-icon-bar-chart-steps>
                        </b-link>
                        <span :id="'next-'+row.item.horse.name" v-b-tooltip.right>
                            {{formatDate(row.item.nextOutRaceNote.raceDate)}}<sup>{{row.item.nextOutRaceNote.raceNumber}}</sup> {{row.item.nextOutRaceNote.track}}<sup :class="{'font-weight-bold': row.item.nextOutRaceNote.position == 1}">{{row.item.nextOutRaceNote.position}}</sup>
                        </span>
                        <b-tooltip :target="'next-'+row.item.horse.name">
                            {{row.item.nextOutRaceNote.type}} {{row.item.nextOutRaceNote.raceClassification}} ${{row.item.nextOutRaceNote.purse}}
                        </b-tooltip>
                        <b-icon-arrow-up variant="success" v-if="riseInClass(row.item)"></b-icon-arrow-up>
                        <b-icon-arrow-down variant="danger" v-if="dropInClass(row.item)"></b-icon-arrow-down>
                        <b-badge pill variant="secondary" v-if="switchToTurf(row.item)" class="ml-1">&nbsp;T&nbsp;</b-badge>
                        <b-badge pill variant="secondary" v-if="switchToDirt(row.item)" class="ml-1">&nbsp;D&nbsp;</b-badge>
                        <b-badge pill variant="secondary" v-if="switchToAW(row.item)" class="ml-1">&nbsp;A&nbsp;</b-badge>
                        <b-badge pill variant="secondary" v-if="offTrack(row.item)" class="ml-1">&nbsp;X&nbsp;</b-badge>
                        <span v-if="switchToSprint(row.item)" class="ml-1">Sp</span>
                        <span v-if="switchToRoute(row.item)" class="ml-1">Rt</span>
                    </span>
                </template>
                <template #cell(horsename)="row">
                    <span v-if="row.item.disqualified"><strong>DQ</strong> - </span>
                    <span :class="{winnerNextOut: row.item.nextOutRaceNote && row.item.nextOutRaceNote.position == 1, placeNextOut: row.item.nextOutRaceNote && row.item.nextOutRaceNote.position == 2}">
                        {{row.item.horse.name}}
                    </span> ({{row.item.jockey.name}})
                </template> 
                <template #cell(pointsOfCall0)="row">
                    {{row.item.pointsOfCall[0].relativePosition.position}}
                    <sup v-if="row.item.pointsOfCall[0].relativePosition.lengthsAhead" v-b-tooltip.hover :title="(row.item.pointsOfCall[0].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[0].relativePosition.totalLengthsBehind.text + ' Total LB' : ''">{{row.item.pointsOfCall[0].relativePosition.lengthsAhead.text}}</sup>
                </template> 
                <template #cell(pointsOfCall1)="row">
                    {{row.item.pointsOfCall[1].relativePosition.position}}
                    <sup v-if="row.item.pointsOfCall[1].relativePosition.lengthsAhead" v-b-tooltip.hover :title="(row.item.pointsOfCall[1].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[1].relativePosition.totalLengthsBehind.text + ' Total LB' : ''">{{row.item.pointsOfCall[1].relativePosition.lengthsAhead.text}}</sup>
                    <sup v-else class="text-danger"><em>{{(row.item.pointsOfCall[1].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[1].relativePosition.totalLengthsBehind.text : ""}}</em></sup>
                </template> 
                <template #cell(pointsOfCall2)="row">
                    {{row.item.pointsOfCall[2].relativePosition.position}}
                    <sup v-if="row.item.pointsOfCall[2].relativePosition.lengthsAhead" v-b-tooltip.hover :title="(row.item.pointsOfCall[2].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[2].relativePosition.totalLengthsBehind.text + ' Total LB' : ''">{{row.item.pointsOfCall[2].relativePosition.lengthsAhead.text}}</sup>
                    <sup v-else class="text-danger"><em>{{(row.item.pointsOfCall[2].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[2].relativePosition.totalLengthsBehind.text : ""}}</em></sup>
                </template> 
                <template #cell(pointsOfCall3)="row">
                    {{row.item.pointsOfCall[3].relativePosition.position}}
                    <sup v-if="row.item.pointsOfCall[3].relativePosition.lengthsAhead" v-b-tooltip.hover :title="(row.item.pointsOfCall[3].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[3].relativePosition.totalLengthsBehind.text + ' Total LB' : ''">{{row.item.pointsOfCall[3].relativePosition.lengthsAhead.text}}</sup>
                    <sup v-else class="text-danger"><em>{{(row.item.pointsOfCall[3].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[3].relativePosition.totalLengthsBehind.text : ""}}</em></sup>
                </template> 
                <template #cell(pointsOfCall4)="row">
                    {{row.item.pointsOfCall[4].relativePosition.position}}
                    <sup v-if="row.item.pointsOfCall[4].relativePosition.lengthsAhead" v-b-tooltip.hover :title="(row.item.pointsOfCall[4].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[4].relativePosition.totalLengthsBehind.text + ' Total LB' : ''">{{row.item.pointsOfCall[4].relativePosition.lengthsAhead.text}}</sup>
                    <sup v-else class="text-danger"><em>{{(row.item.pointsOfCall[4].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[4].relativePosition.totalLengthsBehind.text : ""}}</em></sup>
                </template> 
                <template #cell(pointsOfCall5)="row">
                    {{row.item.pointsOfCall[5].relativePosition.position}}
                    <sup v-if="row.item.pointsOfCall[5].relativePosition.lengthsAhead" v-b-tooltip.hover :title="(row.item.pointsOfCall[5].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[5].relativePosition.totalLengthsBehind.text + ' Total LB' : ''">{{row.item.pointsOfCall[5].relativePosition.lengthsAhead.text}}</sup>
                    <sup v-else class="text-danger"><em>{{(row.item.pointsOfCall[5].relativePosition.totalLengthsBehind != null) ? row.item.pointsOfCall[5].relativePosition.totalLengthsBehind.text : ""}}</em></sup>
                </template>    
                <template #cell(odds)="row">
                    {{row.value}}<span v-if="row.item.favorite">*</span>
                </template> 
                <template #cell(comments)="row">
                    <span  v-b-tooltip.hover :title="row.item.footnote">{{row.value}}</span>
                </template>             			 
                <template #cell(note)="row">
                    <b-form-input v-model="row.item.note" placeholder="Enter Note"></b-form-input>
                </template>  
                <template #cell(horseFlag)="row">
                    <b-icon-star-fill v-if="row.item.horseFlag == 'Star'" variant="success" @click="row.item.horseFlag = ''"></b-icon-star-fill><b-icon-star v-else @click="row.item.horseFlag = 'Star'"></b-icon-star>
                </template>  
                <template #cell(raceFlag)="row">
                    <b-form-select v-model="row.item.raceFlag" :options="flags"></b-form-select>
                </template>			                                            
            </b-table>     
            <strong>Fractional Times: </strong>&nbsp;&nbsp;<span v-for="(fractional, findex) in race.fractionals" :key="'f' + findex">{{fractional.time}}&nbsp;&nbsp;&nbsp;</span><br>
            <strong>Split Times: </strong>&nbsp;&nbsp;<span v-for="(split, sindex) in race.splits" :key="'s' + sindex">({{split.time}})&nbsp;&nbsp;&nbsp;</span><br><br>
            <strong>Winner: </strong>{{winner.horse.program}} - {{winner.horse.name}}, {{winner.horse.color}} {{winner.horse.sex}}, by {{winner.horse.sire.name}} out of {{winner.horse.dam.name}} by {{winner.horse.damSire.name}}. Foaled {{formatDate(winner.horse.foalingDate)}} in {{winner.horse.foalingLocation}}<br>
            <strong>Winning Owner: </strong>{{race.starters[0].owner.name}}<br>
            <span v-if="race.keyRace != null">
                <strong>Key Race Next Out Performances: </strong>
                <span v-for="(horse, khindex) in race.keyRace.horses" :key="khindex">
                    <b-row>
                        <b-col offset="2" cols="2">
                            <strong>{{horse.name}}</strong>
                        </b-col>
                        <b-col cols="4">
                            @ {{horse.track}} Race {{horse.raceNumber}} on {{formatDate(horse.raceDate)}}
                        </b-col>  
                        <b-col>
                            Finish: {{horse.position}} <span v-if="horse.position > 1">(BL: {{horse.beatenLengths}})</span>
                        </b-col>   
                    </b-row>
                </span>
                <br>
            </span>  
            <span v-for="(starter, stcindex) in claimed" :key="'stc' + starter.program">
                <b-row>
                    <b-col>
                        <span v-if="stcindex == 0"><strong>{{claimed.length}} Claimed Horse(s): </strong></span>
                    </b-col>
                    <b-col> 
                        <strong>{{starter.horse.name}}</strong>
                    </b-col>
                    <b-col>
                        {{starter.claim.newTrainerName}}
                    </b-col>
                    <b-col cols="6">
                        {{starter.claim.newOwnerName}}
                    </b-col>                      
                </b-row>
            </span>
            <span v-if="inForClaim > 0">
                <strong>Claiming Prices: </strong>	
                <span v-for="starter in race.starters" :key="'c' + starter.program">
                    <span v-if="starter.claim">{{starter.program}}  - {{starter.horse.name}}: {{formatCurrency(starter.claim.price)}};&nbsp;</span>
                </span> 
                <br>
            </span>
            <span v-if="race.scratches.length > 0"><strong>Scratched Horses: </strong><span v-for="(scratch, scrindex) in race.scratches" :key="scratch.horse.name">{{scratch.horse.name}} ({{scratch.reason}})<span v-if="scrindex < race.scratches.length - 1"></span>;&nbsp;</span></span><br>
            <strong>Total WPS Pool: </strong>{{formatCurrency(race.wagering.winPlaceShow.totalWPSPool)}}
            <b-row>
                <b-col cols="5">
                    <b-row>
                        <b-col cols="1"><strong>Pgm</strong></b-col>
                        <b-col cols="4"><strong>Horse</strong></b-col>
                        <b-col><strong>Win</strong></b-col>
                        <b-col><strong>Place</strong></b-col>
                        <b-col><strong>Show</strong></b-col>
                    </b-row>
                    <b-row v-for="payoff in race.wagering.winPlaceShow.payoffs" :key="'py' + payoff.program">
                        <b-col cols="1">{{payoff.program}}</b-col>
                        <b-col cols="4">{{payoff.horse.name}}</b-col>
                        <b-col><span v-if="payoff.win">{{format2PlacesCurrency(payoff.win.payoff)}}</span></b-col>
                        <b-col><span v-if="payoff.place">{{format2PlacesCurrency(payoff.place.payoff)}}</span></b-col>
                        <b-col><span v-if="payoff.show">{{format2PlacesCurrency(payoff.show.payoff)}}</span></b-col>
                    </b-row>
                </b-col>
                <b-col>
                    <b-row>
                        <b-col cols="4"><strong>Wager Type</strong></b-col>
                        <b-col cols="4"><strong>Winning Numbers</strong></b-col>
                        <b-col><strong>Payoff</strong></b-col>
                        <b-col><strong>Pool</strong></b-col>
                    </b-row>
                    <b-row v-for="exotic in race.wagering.exotics" :key="'ex'  + race.raceNumber + 'r' + exotic.unit + exotic.name + exotic.numberCorrect + exotic.exoticwinningNumbers">
                        <b-col cols="4">{{format2PlacesCurrency(exotic.unit)}} {{exotic.name}}</b-col>
                        <b-col cols="4">{{exotic.winningNumbers}} <span v-if="exotic.numberCorrect">({{exotic.numberCorrect}} correct)</span></b-col>
                        <b-col>{{format2PlacesCurrency(exotic.payoff)}}</b-col>
                        <b-col>{{formatCurrency(exotic.pool)}}</b-col>
                    </b-row>
                </b-col>
            </b-row><br>
            <strong>Trainers: </strong><span v-for="(starter,trindex) in race.starters" :key="'tr' + starter.program">{{starter.program}} - {{starter.trainer.name}}<span v-if="trindex < race.scratches.length - 1"></span>;&nbsp;</span><br>
            <strong>Owners: </strong><span v-for="(starter,owindex) in startersWithOwners" :key="'ow' + starter.program">{{starter.program}} - {{starter.owner.name}}<span v-if="owindex < race.scratches.length - 1"></span>;&nbsp;</span><br>
            <strong>Footnotes</strong><br>
            {{race.footnotes}}
        </div>
    </div>
</template>

<script>
import { BIconStar, BIconStarFill, BIconCameraVideoFill, BIconKey, BIconBarChartSteps, BIconArrowUp, BIconArrowDown } from 'bootstrap-vue'
import _ from 'underscore'

export default {
    name: 'ChartView',
    components: {
		BIconStar, BIconStarFill, BIconCameraVideoFill, BIconKey, BIconBarChartSteps, BIconArrowUp, BIconArrowDown
    },
    props : ['race', 'starterFields', 'charts'],
    data () {
		return {
			flags: ['', 'Wide', 'Trouble', 'Bad Start', 'Good Trip', 'Duel', 'Early Speed', 'Late Kick', 'No Factor']
        }
    },
    computed: {
        claimed() {
            return _.filter(this.race.starters, function(s){
                return (s.claim && s.claim.claimed);
            });
        },
        inForClaim() {
			var c = 0;
            for (var i = 0; i < this.race.starters.length; i++) {
                if (this.race.starters[i].claim) {
					c++;
                }
            }
            return c;
        },	
        startersWithOwners() {
            return _.filter(this.race.starters, function(s){
                return s.owner != null;
            })
        },
        winner() {
            return _.findWhere(this.race.starters, {winner: true});
        }
    },
    methods: {
		formatDate (date) {
			return date[1] + "/" + date[2] + "/" + date[0];
		},	
		formatCurrency(amount) {
			const formatter = new Intl.NumberFormat('en-US', {
				style: 'currency',
				currency: 'USD',

				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},
		str_pad_left(string,pad,length) {
			return (new Array(length+1).join(pad)+string).slice(-length);
		},
		format2Places(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},
		format2PlacesCurrency(amount) {
			const formatter = new Intl.NumberFormat('en-US', {
                style: 'currency',
				currency: 'USD',

				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},        
        keyRaceHorsesFormat(horses) {
            var ret = "<span class='pp'>";
            for (var i=0; i < horses.length; i++) {
                ret += horses[i].name 
                    + " @ " + horses[i].track
                    + " " + this.formatDate(horses[i].raceDate)
                    + " Pos: " + horses[i].position 
                    + " BL: " + this.format2Places(horses[i].beatenLengths);
                if (i < horses.length-1) ret += "<br>";
            }
            return ret + "</span>";
        },
        hasChart(race) {
            if (race == null) return false;	
			var trackCode = (race.track.code || race.track);
            var str_pad_left = this.str_pad_left;
            var track = _.findWhere(this.charts, {code: trackCode});
            if (track == null) return false;
			const chartDates = _.map(
                _.pluck(_.where(track.raceDates, {hasChartFlag: true}), "raceDate")
                , function (d) {
					return d[0] + "-" 
						+ str_pad_left(d[1],0,2)  + "-"
						+ str_pad_left(d[2],0,2); 
                });

            const day = race.raceDate[0] + "-"
				+ str_pad_left(race.raceDate[1],0,2)   + "-"
				+ str_pad_left(race.raceDate[2],0,2);
			return chartDates.indexOf(day) >  -1;
        },
		riseInClass(starter) {
			if (!starter.nextOutRaceNote) return false;
			return starter.nextOutRaceNote.purse > this.race.purse.value;
		},
		dropInClass(starter) {
			if (!starter.nextOutRaceNote) return false;
			return starter.nextOutRaceNote.purse < this.race.purse.value;
		},   
		switchToTurf(starter) {
			if (!starter.nextOutRaceNote) return false;
			return starter.nextOutRaceNote.surface == 'Turf' && this.race.surface != 'Turf';
		},
		switchToDirt(starter) {
			if (!starter.nextOutRaceNote) return false;
			return starter.nextOutRaceNote.surface == 'Dirt' && this.race.surface != 'Dirt';
		},	
		switchToAW(starter) {
			if (!starter.nextOutRaceNote) return false;
			return starter.nextOutRaceNote.surface == 'All Weather Track' && this.race.surface != 'All Weather Track';
		},
		switchToSprint(starter) {
			if (!starter.nextOutRaceNote) return false;
			return starter.nextOutRaceNote.distance < 5280 && this.race.distance.value >= 5280;
		},
		switchToRoute(starter) {
			if (!starter.nextOutRaceNote) return false;
			return starter.nextOutRaceNote.distance >= 5280 && this.race.distance.value < 5280;
		},		
		offTrack(starter) {
			if (!starter.nextOutRaceNote) return false;
			return starter.nextOutRaceNote.trackCondition != 'Fast' && starter.nextOutRaceNote.trackCondition != 'Firm';
		}					
    }
}
</script>