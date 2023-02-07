<template>
    <b-table
        id="pps"
        :items="race.horses"
        :fields="horseFields"
        small
        sort-icon-left
        sort-desc
        :tbody-tr-class="rowClass"
        primary-key="programNumber"
        :tbody-transition-props="transProps"
    >
        <template #head()="data">
            <span v-b-tooltip.hover :title="data.field.title">{{ data.label }}</span>
        </template>    
        <template #cell()="row">
            <span v-b-tooltip.hover :title="(row.item.scratchedFlag) ? '' : row.field.label + ((row.field.rank) ? ' (' + rankOf(row.unformatted, row.field.key, row.field.reverse) + ' of ' + race.unscratchedHorsesCount + ')' : '')">{{row.value}}</span>
        </template>         
        <template #cell(scratchButton)="row">	
            <span v-if="!row.item.scratchedFlag">			
                <b-button
                    @click="toggleScratch(row.item)"
                    size="sm"
                    variant="warning"
                    class="mr-1"
                    title="S">
                    <b-icon-type-strikethrough
                        aria-hidden="true">
                    ></b-icon-type-strikethrough>
                </b-button>
            </span>
            <span v-if="row.item.scratchedFlag">			
                <b-button
                    @click="toggleScratch(row.item)"
                    size="sm"
                    variant="primary"
                    class="mr-1"
                    title="S">
                    <b-icon-type-underline
                        aria-hidden="true">
                    ></b-icon-type-underline>
                </b-button>
            </span>	
        </template>	
        <template #cell(programNumber)="row">	
            {{row.value}}<sup v-if="row.item.postPosition != row.item.programNumber">{{row.item.postPosition}}</sup>
        </template>
        <template #cell(pickButton)="row">	
            <span>			
                <b-button
                    @click="togglePick(row.item)"
                    size="sm"
                    :variant="(row.item.pick) ? 'primary' : 'outline-secondary'"
                    class="mr-1"
                    v-b-tooltip.hover 
                    :title="payoutString(row.item)"
                >
                    {{((row.item.finishPosition == 99) ? 'P' : row.item.finishPosition)}}
                </b-button>
            </span>
        </template>	
        <template #cell(selection)="row">
            <b-form-select v-model="row.item.selection" :options="selectionOptions" @change="updateSelection(row.item)"></b-form-select>
        </template>
        <template #cell(bettingLine)="row">
            <b-form-select 
                :id="'bettingLine'+row.item.raceNumber+'-'+row.item.postPosition"
                class="fv"
                v-model="row.item.bettingLine" 
                :options="bettingLineOptions" 
                @change="updateBettingLine(row.item)" 
                :state="row.item.bettingLine == 0 ? null : (bettingLinesInvalid ? false : true)"
            ></b-form-select>
            <b-tooltip v-if="row.item.bettingLine == 0 && bettingLinesInvalid" :target="'bettingLine'+row.item.raceNumber+'-'+row.item.postPosition" placement="right">
                Total: {{format2Places(contenderTotal)}}%<br>
                <span v-for="(bettingLine,key) in matchingBettingLines" :key="key">
                    <span v-for="(odds, oddsKey) in bettingLine" :key="oddsKey">
                        {{convertOddsToFraction(odds)}} <span v-if="oddsKey < bettingLine.length - 1">-</span>
                    </span>
                    <br>
                </span>
            </b-tooltip> 
        </template>
        <template #cell(aratingFairValue)="row"> 
            <span v-if="!row.item.scratchedFlag">{{row.value}}</span>
         </template>
        <template #cell(mlodds)="row">       
            <span class="text-danger" v-if="hideML"><b-icon-eye-slash-fill></b-icon-eye-slash-fill></span>
            <span v-else v-b-tooltip.hover :title="(row.item.scratchedFlag) ? '' : row.field.label + ((row.field.rank) ? ' (' + rankOf(row.unformatted, row.field.key, row.field.reverse) + ' of ' + race.unscratchedHorsesCount + ')' : '')">{{row.item.mlodds}}</span>
        </template>     
        <template #cell(name)="row">
            <span v-b-tooltip.hover :title="properize(row.item.trainer.name) + '/' + properize(row.item.jockey.name)">{{row.value}}</span> 
        </template>
        <template #cell(style)="row">
            {{ row.item.runStyle }} {{ row.item.speedPoints }}
        </template>	 
        <template #cell(daysSinceLastRace)="row">
            <span v-b-tooltip.hover :title="'Furlong Days: ' + format2Places(row.item.furlongDays)">{{row.value}}</span>
        </template>
        <template #cell(classRating)="row">
            <span v-b-tooltip.hover.html :title="(row.item.scratchedFlag) ? '' : row.field.label 
                + ' (' + rankOf(row.unformatted, row.field.key, row.field.reverse) + ' of ' + race.unscratchedHorsesCount + ')'
                + (row.item.brisCurrentClass > 0 ? '<br>BRIS Cur: ' + format2Places(row.item.brisCurrentClass) + ' (' + rankOf(row.item.brisCurrentClass, 'brisCurrentClass', row.field.reverse) + ' of ' + race.unscratchedHorsesCount + ')': '') 
                + (row.item.brisAvgLast3Class > 0 ? '<br>BRIS Avg L3: ' + format2Places(row.item.brisAvgLast3Class) + ' (' + rankOf(row.item.brisAvgLast3Class, 'brisAvgLast3Class', row.field.reverse) + ' of ' + race.unscratchedHorsesCount + ')': '')">{{row.value}}</span>
        </template>    
        <template #cell(aratingForm)="row">
            <span v-b-tooltip.hover.html :title="(row.item.scratchedFlag) ? '' : row.field.label 
                + ' (' + rankOf(row.unformatted, row.field.key, row.field.reverse) + ' of ' + race.unscratchedHorsesCount + ')'
                + '<br>Basic Fitness: ' + row.item.basicFitness">{{row.value}}</span>
        </template>
        <template #cell(e2Avg)="row">
            <span v-b-tooltip.hover.html :title="(row.item.scratchedFlag) ? '' : row.field.label 
                + ' (' + rankOf(row.unformatted, row.field.key, row.field.reverse) + ' of ' + race.unscratchedHorsesCount + ')'
                + '<br>Early Position: ' +format2Places(row.item.earlyPosition) + ' (' + rankOf(row.item.earlyPosition, 'earlyPosition', false) + ' of ' + race.unscratchedHorsesCount + ')'">{{row.value}}</span>
        </template>
        <template #cell(latePaceAvg)="row">
            <span v-b-tooltip.hover.html :title="(row.item.scratchedFlag) ? '' : row.field.label 
                + ' (' + rankOf(row.unformatted, row.field.key, row.field.reverse) + ' of ' + race.unscratchedHorsesCount + ')'
                + '<br>Late Position: ' +format2Places(row.item.latePosition) + ' (' + rankOf(row.item.latePosition, 'latePosition', false) + ' of ' + race.unscratchedHorsesCount + ')'
                + '<br>Closing Ratio: ' + format2Places(row.item.closingRatio) + ' (' + rankOf(row.item.closingRatio, 'closingRatio', false) + ' of ' + race.unscratchedHorsesCount + ')'">{{row.value}}</span>
        </template>
        <template #cell(classShift)="row">
            <span v-b-tooltip.hover.html :title="(row.item.scratchedFlag) ? '' : row.field.label 
                + ' (' + rankOf(row.unformatted, row.field.key, row.field.reverse) + ' of ' + race.unscratchedHorsesCount + ')'
                + (row.item.lastRaceStrength > 0 ? '<br>Last Race Strength: ' + format2Places(row.item.lastRaceStrength) + ' (' + rankOf(row.item.lastRaceStrength, 'lastRaceStrength', false) + ' of ' + race.unscratchedHorsesCount + ')' : '')">{{row.value}}</span>
        </template>
        <template #cell(detailsButton)="row">				
            <b-button
                @click="toggleShowDetail(row)"
                size="sm"
                :variant="(row.item.note == null) ? 'outline-secondary' : 'primary'"
                v-b-tooltip.hover.left :title="row.item.note"
                class="mr-1">
                <b-icon-info
                    aria-hidden="true">
                ></b-icon-info>
            </b-button>
        </template>      	
        <template #row-details="row">
            <b-container v-if="row.item.scratchedFlag" fluid class="pp border scratchedDetail text-center m-2 p-2">
                <span class="h4 text-white">{{row.item.programNumber}} - {{row.item.name}} is scratched</span>
            </b-container>
            <b-container v-if="!row.item.scratchedFlag" fluid class="pp border">
                <b-row>
                    <b-col cols="4">
                        <b-row>
                            <b-col cols="1">
                                <span class="h5"><strong>{{row.item.programNumber}}</strong>
                                <span v-if="row.item.postPosition != parseInt(row.item.programNumber)" class="pp"><sup>pp{{row.item.postPosition}}</sup></span>																							
                                </span><br>
                                <span class="h6">
                                    <span class="text-danger" v-if="hideML"><b-icon-eye-slash-fill></b-icon-eye-slash-fill></span>
                                    <span v-else>{{row.item.mlodds}}</span>
                                </span><br>
                            </b-col>
                            <b-col>
                                <span class="h5"><strong>
                                    <b-icon-star-fill 
                                        variant="success" 
                                        v-if="row.item.flag == 'Star'"
                                        v-b-popover.hover.html.right="{content: row.item.comment, title: 'Horse To Watch'}"                                       
                                    ></b-icon-star-fill>
                                    {{row.item.name}} ({{row.item.runStyle}} {{row.item.speedPoints}})</strong>&nbsp;
                                    <span style="float:right;">
                                        <span v-if="row.item.claimingPrice">{{formatCurrency(row.item.claimingPrice)}}<br></span>
                                        <b-badge class="text-right" v-if="row.item.mtoflag == 'M'" variant="dark">MTO</b-badge>
                                        <b-badge v-if="row.item.mtoflag == 'A'" variant="dark">AE</b-badge>
                                    </span>
                                </span>
                                <br>
                                <strong>Own: {{row.item.owner}}</strong><br>
                                {{row.item.ownerSilks}}
                            </b-col>
                        </b-row>
                    </b-col>
                    <b-col cols="3">
                        <strong>
                            {{row.item.color}}. {{row.item.sex}}. {{row.item.age}} ({{row.item.foalingMonth}})  {{row.item.auctionLocationDate}}  <span :class="row.item.auctionPrice > 100000 && row.item.lifetimeWins == 0 ? 'greenHighlight' : ''">{{formatCurrency(row.item.auctionPrice)}}</span><br>
                            <b-row>
                                <b-col cols="2">
                                    Sire :
                                </b-col>
                                <b-col>
                                    {{row.item.sire}} ({{row.item.siresSire}}) {{formatCurrency(row.item.sireStudFee)}}<br>
                                </b-col>
                            </b-row>
                            <b-row>
                                <b-col cols="2">
                                    Dam  :  
                                </b-col>
                                <b-col>
                                    {{row.item.dam}} ({{row.item.damsSire}})<br>
                                </b-col>
                            </b-row>
                            <b-row>
                                <b-col cols="2">
                                    Brdr :  
                                </b-col>
                                <b-col>
                                    {{row.item.breeder}}<br>
                                </b-col>
                            </b-row>
                            <b-row>
                                <b-col cols="2">
                                    Trnr :  
                                </b-col>
                                <b-col>
                                    {{row.item.trainer.name}}  ({{row.item.trainer.currentMeetStarts}} {{row.item.trainer.currentMeetWins}}-{{row.item.trainer.currentMeetPlaces}}-{{row.item.trainer.currentYearShows}} <span :class="(row.item.trainer.currentMeetStarts == 0 ? 0 : (row.item.trainer.currentMeetWins/row.item.trainer.currentMeetStarts*100) >= 20 && row.item.trainer.currentMeetStarts >= 20) ? 'greenHighlight' : ''">{{row.item.trainer.currentMeetStarts == 0 ? 0 : (row.item.trainer.currentMeetWins/row.item.trainer.currentMeetStarts*100).toFixed(0)}}%</span>)
                                </b-col>
                            </b-row>																																	 
                        </strong>
                    </b-col>
                    <b-col cols="1" class="text-right">
                        <br><strong><span v-if="row.item.equipment == 'BLINKERS_ON'">Blnkr On</span><span v-if="row.item.equipment == 'BLINKERS_OFF'">Blnkr Off</span></strong>
                        <br><strong><span v-if="row.item.sex == 'g'"><b-badge pill variant="dark">G</b-badge></span><span v-if="row.item.medication == 'LASIX_FIRST'"><b-badge variant="dark">L</b-badge></span><span v-if="row.item.medication == 'LASIX'">L</span> {{row.item.weight}}</strong>
                    </b-col>
                    <b-col cols="4">
                        <b-row>
                            <b-col cols="5" class="border-right">
                                <b-row class="border-left border-bottom">
                                    <b-col cols="2">
                                        <strong>Life:</strong>  
                                    </b-col>
                                    <b-col cols="5">
                                        {{row.item.lifetimeStarts}}&nbsp;&nbsp;{{row.item.lifetimeWins}}-{{row.item.lifetimePlaces}}-{{row.item.lifetimeShows}}<br>
                                    </b-col>
                                    <b-col cols="3">
                                        {{formatCurrency(row.item.lifetimeEarnings)}}
                                    </b-col>
                                    <b-col cols="2">
                                        <strong>{{row.item.lifetimeBestSpeed}}</strong>
                                    </b-col>
                                </b-row>													
                                <b-row>
                                    <b-col cols="2">
                                        <strong>{{row.item.currentYear}}</strong> 
                                    </b-col>
                                    <b-col cols="5">
                                        {{row.item.currentYearStarts}}&nbsp;&nbsp;{{row.item.currentYearWins}}-{{row.item.currentYearPlaces}}-{{row.item.currentYearShows}}<br>
                                    </b-col>
                                    <b-col cols="3">
                                        {{formatCurrency(row.item.currentYearEarnings)}}
                                    </b-col>
                                    <b-col cols="2">
                                        <strong>{{row.item.currentYearBestSpeed}}</strong>
                                    </b-col>										
                                </b-row>	
                                <b-row>
                                    <b-col cols="2">
                                        <strong>{{row.item.previousYear}}</strong>  
                                    </b-col>
                                    <b-col cols="5">
                                        {{row.item.previousYearStarts}}&nbsp;&nbsp;{{row.item.previousYearWins}}-{{row.item.previousYearPlaces}}-{{row.item.previousYearShows}}<br>
                                    </b-col>
                                    <b-col cols="3">
                                        {{formatCurrency(row.item.previousYearEarnings)}}
                                    </b-col>
                                    <b-col cols="2">
                                        <strong>{{row.item.previousYearBestSpeed}}</strong>
                                    </b-col>													
                                </b-row>	
                                <b-row>
                                    <b-col cols="2">
                                        <strong>{{row.item.currentTrack}}</strong> 
                                    </b-col>
                                    <b-col cols="5" :class="row.item.trackWins > 0 || row.item.trackPlaces > 2 ? 'greenHighlight' : ''">
                                        {{row.item.trackStarts}}&nbsp;&nbsp;{{row.item.trackWins}}-{{row.item.trackPlaces}}-{{row.item.trackShows}}<br>
                                    </b-col>
                                    <b-col cols="3">
                                        {{formatCurrency(row.item.trackEarnings)}}
                                    </b-col>
                                    <b-col cols="2">
                                        <strong>{{row.item.trackBestSpeed}}</strong>
                                    </b-col>													
                                </b-row>
                            </b-col>
                            <b-col cols="7">
                                <b-row>
                                    <b-col cols="4">
                                        <strong>Fst <span v-if="row.item.pedigreeRatingDirt">({{row.item.pedigreeRatingDirt}})</span></strong>  
                                    </b-col>
                                    <b-col cols="3">
                                        {{row.item.dirtStarts}}&nbsp;{{row.item.dirtWins}}-{{row.item.dirtPlaces}}-{{row.item.dirtShows}}<br>
                                    </b-col>
                                    <b-col cols="3">
                                        {{formatCurrency(row.item.dirtEarnings)}}
                                    </b-col>
                                    <b-col cols="1">
                                        <strong>{{row.item.dirtBestSpeed}}</strong>
                                    </b-col>
                                </b-row>													
                                <b-row>
                                    <b-col cols="4" >
                                        <strong>Off <span v-if="row.item.pedigreeRatingMud">({{row.item.pedigreeRatingMud}})</span></strong> 
                                    </b-col>
                                    <b-col cols="3" :class="race.trackCondition != 'ft' && race.trackCondition != 'fm' && (row.item.wetWins > 0 || row.item.wetPlaces > 2) ? 'greenHighlight' : ''">
                                        {{row.item.wetStarts}}&nbsp;{{row.item.wetWins}}-{{row.item.wetPlaces}}-{{row.item.wetShows}}<br>
                                    </b-col>
                                    <b-col cols="3">
                                        {{formatCurrency(row.item.wetEarnings)}}
                                    </b-col>
                                    <b-col cols="1">
                                        <strong>{{row.item.wetBestSpeed}}</strong>
                                    </b-col>										
                                </b-row>	
                                <b-row>
                                    <b-col cols="4">
                                        <strong>Dis  <span v-if="row.item.pedigreeRatingDist">({{row.item.pedigreeRatingDist}})</span></strong>  
                                    </b-col>
                                    <b-col cols="3">
                                        {{row.item.distanceStarts}}&nbsp;{{row.item.distanceWins}}-{{row.item.distancePlaces}}-{{row.item.distanceShows}}<br>
                                    </b-col>
                                    <b-col cols="3">
                                        {{formatCurrency(row.item.distanceEarnings)}}
                                    </b-col>
                                    <b-col cols="1">
                                        <strong>{{row.item.distanceBestSpeed}}</strong>
                                    </b-col>													
                                </b-row>	
                                <b-row>
                                    <b-col cols="4">
                                        <strong>Trf  <span v-if="row.item.pedigreeRatingTurf">({{row.item.pedigreeRatingTurf}})</span></strong> 
                                    </b-col>
                                    <b-col cols="3">
                                        {{row.item.turfStarts}}&nbsp;{{row.item.turfWins}}-{{row.item.turfPlaces}}-{{row.item.turfShows}}<br>
                                    </b-col>
                                    <b-col cols="3">
                                        {{formatCurrency(row.item.turfEarnings)}}
                                    </b-col>
                                    <b-col cols="1">
                                        <strong>{{row.item.turfBestSpeed}}</strong>
                                    </b-col>													
                                </b-row>
                                <b-row>
                                    <b-col cols="4">
                                        <strong>AW</strong> 
                                    </b-col>
                                    <b-col cols="3">
                                        {{row.item.allWeatherStarts}}&nbsp;{{row.item.allWeatherWins}}-{{row.item.allWeatherPlaces}}-{{row.item.allWeatherShows}}<br>
                                    </b-col>
                                    <b-col cols="3">
                                        {{formatCurrency(row.item.allWeatherEarnings)}}
                                    </b-col>
                                    <b-col cols="1">
                                        <strong>{{row.item.allWeatherBestSpeed}}</strong>
                                    </b-col>													
                                </b-row>																																																		
                            </b-col>
                            <b-col>

                            </b-col>
                        </b-row>
                    </b-col>

                </b-row>
                <b-row>
                    <b-col>
                        {{properize(row.item.jockey.name)}} ({{row.item.jockey.currentMeetStarts}} {{row.item.jockey.currentMeetWins}}-{{row.item.jockey.currentMeetPlaces}}-{{row.item.jockey.currentMeetShows}}<span :class="(row.item.jockey.currentMeetStarts == 0 ? 0 : (row.item.jockey.currentMeetWins/row.item.jockey.currentMeetStarts*100) >= 20 && row.item.jockey.currentMeetStarts >= 20) ? 'greenHighlight' : ''"> {{row.item.jockey.currentMeetStarts == 0 ? 0 : (row.item.jockey.currentMeetWins/row.item.jockey.currentMeetStarts*100).toFixed(0)}}%)</span><br>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="3">
                        <b-row>
                            <b-col cols="4">
                                {{row.item.currentYear}}
                            </b-col>
                            <b-col cols="2">
                                {{row.item.jockey.currentYearStarts}}
                            </b-col>
                            <b-col :class="row.item.jockey.currentYearStarts >= 20 && row.item.trainer.currentYearWins/row.item.jockey.currentYearStarts*100 >= 20 ? 'greenHighlight' : ''">
                                {{row.item.jockey.currentYearStarts == 0 ? 0 : (row.item.jockey.currentYearWins/row.item.jockey.currentYearStarts*100).toFixed(0)}}%
                            </b-col>
                            <b-col>
                                {{row.item.jockey.currentYearStarts == 0 ? 0 : ((row.item.jockey.currentYearWins + row.item.jockey.currentYearPlaces + row.item.jockey.currentYearShows)/row.item.jockey.currentYearStarts*100).toFixed(0)}}%
                            </b-col>
                            <b-col>
                                {{row.item.jockey.currentYearROI}}
                            </b-col>											
                        </b-row>											
                        <b-row>
                            <b-col cols="4">
                                {{row.item.previousYear}}
                            </b-col>
                            <b-col cols="2">
                                {{row.item.jockey.previousYearStarts}}
                            </b-col>
                            <b-col>
                                {{row.item.jockey.previousYearStarts == 0 ? 0 : (row.item.jockey.previousYearWins/row.item.jockey.previousYearStarts*100).toFixed(0)}}%
                            </b-col>
                            <b-col>
                                {{row.item.jockey.previousYearStarts == 0 ? 0 : ((row.item.jockey.previousYearWins + row.item.jockey.previousYearPlaces + row.item.jockey.previousYearShows)/row.item.jockey.previousYearStarts*100).toFixed(0)}}%
                            </b-col>
                            <b-col>
                                {{row.item.jockey.previousYearROI}}
                            </b-col>											
                        </b-row>
                        <b-row v-for="stat in row.item.jockey.stats" :key="stat.category">
                            <b-col cols="4">
                                JKYw/ {{stat.category}}
                            </b-col>
                            <b-col cols="2">
                                {{stat.starts}}
                            </b-col>
                            <b-col :class="stat.winPercent > 20 || (stat.starts > 0 && stat.wins / stat.starts > 0.2) ? 'greenHighlight' : ''">
                                <span v-if="stat.winPercent == 0">{{stat.starts == 0 ? 0 : (stat.wins / stat.starts * 100).toFixed(0)}}%</span>
                                <span v-else>{{stat.winPercent}}%</span>
                            </b-col>
                            <b-col>
                                <span v-if="stat.itmpercent == 0">{{stat.starts == 0 ? 0 : ((stat.wins + stat.places + stat.shows)/ stat.starts * 100).toFixed(0)}}%</span>
                                <span v-else>{{stat.itmpercent}}%</span>
                            </b-col>
                            <b-col>
                                {{stat.roi}}
                            </b-col>											
                        </b-row>	
                        <b-row v-if="row.item.pastPerformances.length > 2" class="my-2">
                            <b-col>
                                <b-button class="ml-auto" size="sm" v-b-toggle="'extra-'+race.raceNumber+'-'+row.index" variant="primary">Trends</b-button>
                            </b-col>
                        </b-row>                       									
                    </b-col>
                    <b-col cols="3" offset="1">
                        <b-row>
                            <b-col cols="4">
                                {{row.item.currentYear}}
                            </b-col>
                            <b-col cols="2">
                                {{row.item.trainer.currentYearStarts}}
                            </b-col>
                            <b-col :class="row.item.trainer.currentYearStarts >= 20 && row.item.trainer.currentYearWins/row.item.trainer.currentYearStarts*100 >= 20 ? 'greenHighlight' : ''">
                                {{row.item.trainer.currentYearStarts == 0 ? 0 : (row.item.trainer.currentYearWins/row.item.trainer.currentYearStarts*100).toFixed(0)}}%
                            </b-col>
                            <b-col>
                                {{row.item.trainer.currentYearStarts == 0 ? 0 : ((row.item.trainer.currentYearWins + row.item.trainer.currentYearPlaces + row.item.trainer.currentYearShows)/row.item.trainer.currentYearStarts*100).toFixed(0)}}%
                            </b-col>
                            <b-col>
                                {{row.item.trainer.currentYearROI}}
                            </b-col>											
                        </b-row>											
                        <b-row v-for="stat in row.item.trainer.trainerStats" :key="stat.category">
                            <b-col cols="4">
                                {{stat.category}}
                            </b-col>
                            <b-col cols="2">
                                {{stat.starts}}
                            </b-col>
                            <b-col :class="stat.winPercent >= 20 ? 'greenHighlight' : ''">
                                {{stat.winPercent.toFixed(0)}}%
                            </b-col>
                            <b-col>
                                {{stat.itmpercent.toFixed(0)}}%
                            </b-col>
                            <b-col>
                                {{stat.roi.toFixed(2)}}
                            </b-col>											
                        </b-row>	
                    </b-col>
                    <b-col cols="4" offset="1">
                        <b-row v-if="row.item.sireAWD">
                            <b-col cols="3">
                                Sire Stats: AWD {{row.item.sireAWD}} 
                            </b-col>
                            <b-col>
                                {{row.item.sireMudPercent}}%Mud
                            </b-col>
                            <b-col>
                                {{row.item.sireMudStarts}}MudSts
                            </b-col>
                            <b-col v-if="row.item.sireTurfPercent > 0">
                                {{row.item.sireTurfPercent}}%Turf
                            </b-col>
                            <b-col v-if="row.item.sireFirstTurfPercent > 0">
                                {{row.item.sireFirstTurfPercent}}%1stT
                            </b-col>
                            <b-col v-if="row.item.sireFirstPercent > 0">
                                {{row.item.sireFirstPercent}}%1st
                            </b-col>
                            <b-col>
                                {{format2Places(row.item.sireSPI)}}spi
                            </b-col>
                        </b-row>
                        <b-row v-if="row.item.damSireAWD" :class="row.item.damDescription ? '' : 'mb-1'">
                            <b-col cols="3">
                                Dam's Sire: AWD {{row.item.damSireAWD}} 
                            </b-col>
                            <b-col>
                                {{row.item.damSireMudPercent}}%Mud
                            </b-col>
                            <b-col>
                                {{row.item.damSireMudStarts}}MudSts
                            </b-col>
                             <b-col v-if="row.item.damSireTurfPercent > 0">
                                {{row.item.damSireTurfPercent}}%Turf
                            </b-col>
                            <b-col v-if="row.item.damSireFirstTurfPercent > 0">
                                {{row.item.damSireFirstTurfPercent}}%1stT
                            </b-col>                           
                            <b-col v-if="row.item.damSireFirstPercent > 0">
                                {{row.item.damSireFirstPercent}}%1st
                            </b-col>
                            <b-col>
                                {{format2Places(row.item.damSireSPI)}}spi
                            </b-col>
                        </b-row>
                        <b-row v-if="row.item.damDescription" class="mb-1">
                            <b-col cols="3">
                                Dam's Stats: {{row.item.damDescription}} 
                            </b-col>
                            <b-col v-if="row.item.damTurfWinners">
                                {{row.item.damTurfWinners}}trfW
                            </b-col>                            
                            <b-col v-if="row.item.damTwoYearOldPercent">
                                {{row.item.damTwoYearOldPercent}}%2yo
                            </b-col>
                            <b-col>
                                {{row.item.damStarters}}str
                            </b-col>
                            <b-col>
                                {{row.item.damWinners}}w
                            </b-col>
                             <b-col>
                                {{row.item.damStakesWinners}}sw
                            </b-col>                           
                            <b-col>
                                {{format2Places(row.item.damDPI)}}dpi
                            </b-col>
                        </b-row>                                               
                        <b-row v-for="(angle,ndx) in row.item.angles" :key="ndx">
                            <b-col>
                                <span :class="'font-weight-bold ' + (angle.type == '+' ? 'text-success' : (angle.type == '-' ? 'text-danger' : 'text-warning'))">
                                    <b-icon-file-pdf v-if="angle.source == 'Augmented'"></b-icon-file-pdf>
                                    <b-icon-lightbulb v-if="angle.source == 'Generated'"></b-icon-lightbulb>
                                    {{angle.text}}
                                </span>
                            </b-col>
                        </b-row>
                    </b-col>
                </b-row>     
                <b-row class="mb-2">

                    <b-col cols="5">
                        <b-form-textarea
                            size="sm"
                            v-model="row.item.note"
                            placeholder="Enter something..."
                            max-rows="4"
                            debounce="500" 
                        ></b-form-textarea>
                    </b-col>
                    <b-col>
                        <b-button size="sm" class="my-3" variant="primary" @click="setHorseNote(row.item)"><b-icon-cloud-upload-fill></b-icon-cloud-upload-fill></b-button>
                    </b-col>
                </b-row>
                <b-collapse :id="'extra-'+race.raceNumber+'-'+row.index" class="mt-2">
                    <horse-extra-view :horse="row.item"></horse-extra-view>
                </b-collapse>               
                <past-performance-view :horse="row.item" :race="race" :charts="charts" @goToChart="goToChart"></past-performance-view>
                <span v-for="workout in row.item.workouts" :key="workout.date">
                    <workout-view :workout="workout"></workout-view>
                </span>										
            </b-container>
        </template>									
    </b-table>

</template>

<script>
import { BIconTypeUnderline, BIconTypeStrikethrough, BIconInfo, BIconEyeSlashFill, BIconCloudUploadFill, BIconStarFill, BIconFilePdf, BIconLightbulb } from 'bootstrap-vue'
import PastPerformanceView from '@/components/PastPerformanceView'
import WorkoutView from '@/components/WorkoutView'
import HorseExtraView from '@/components/HorseExtraView'

import axios from 'axios'
import _ from 'underscore'

export default {
    name: 'RaceView',
    components: {
		PastPerformanceView, WorkoutView, HorseExtraView, BIconTypeUnderline, BIconTypeStrikethrough, BIconInfo, BIconEyeSlashFill, BIconCloudUploadFill, BIconStarFill, BIconFilePdf, BIconLightbulb 
    },
    props : ['race', 'hideML', 'charts'],
    data () {
		return {
            transProps: {
				name: 'flip-list'
			},
			horseFields: [
				{key: "scratchButton", label:""},
				{key: "programNumber", label:"#", title: "Program Number (& Post Position)", sortable:true},
                {key: "pickButton", label:""},
                {key: "selection", label: "ABC", title: "ABC Selection"},
                {key: "bettingLine", label: "FV", title: "Betting Fair Value Line"},  
                {key: "aratingFairValue", label: "AFV", title: "A Rating Fair Value Line", formatter: this.format1Place},           
				{key: "mlodds", "label": "ML", title: "Morning Line Odds", sortable:true, tdClass: this.formatOdds, rank: true, reverse: true},
				{key: "name", tdClass: this.highlightName},
				{key: "daysSinceLastRace", label:"l/r", title: "Days Since Last Race", tdClass: this.highlightDaysSince},
				{key: "style", label: "Style", title: "Run Style and Speed Points", tdClass: this.highlightPaceAdvantage, rank: false },
				{key: "classRating", label:"Class", title: "Weighted Average Speed Par", formatter: this.format2Places, sortable: true, tdClass: this.highlightMax, rank: true},
				{key: "averageCompetitiveLevel", title: "Average Competitive Level (Weighted Average Speed Par where finish in the money or within 3 lengths)", label:"ACL", formatter: this.format2Places, sortable: true, tdClass: this.highlightMax, rank: true},
				{key: "speedRating", label:"Speed", title: "Weighted Average BRIS Speed Figure", formatter: this.format2Places, sortable: true, tdClass: this.highlightMax, rank: true},
                {key: "primePower", label:"PP", title: "Prime Power", formatter: this.format2Places, sortable: true, tdClass: this.highlightMax, rank: true},
                {key: "arating", label: "AR", title: "A Rating", formatter: this.format2Places, sortable: true, tdClass: this.highlightMax, rank: true},
				{key: "aratingForm", label:"AR F", title: "A Form Rating", formatter: this.format2Places, sortable: true, tdClass: this.highlightMax, rank: true}, 
				{key: "aratingConnections", label:"AR Co", title: "A Connections Rating", formatter: this.format2Places, sortable: true, tdClass: this.highlightMax, rank: true},
				{key: "e1Avg", label:"E1", title: "Average E1 Pace", formatter: this.formatInt, sortable: true, tdClass: this.highlightMax, rank: true},
				{key: "e2Avg", label:"E2", title: "Average E2 Pace", formatter: this.formatInt, sortable: true, tdClass: this.highlightMax, rank: true},
				{key: "turnTime", label:"TT", title: "Average Turn Time (E2-E1)", formatter: this.formatInt, sortable: true, tdClass: this.highlightMax, rank: true},
				{key: "latePaceAvg", label:"Late", title: "Average Late Pace", formatter: this.formatInt, sortable: true, tdClass: this.highlightMax, rank: true},
				{key: "combinedPaceAvg", label:"Comb", title: "Average Combined Pace (E2+Late)", formatter: this.format2Places, sortable: true, tdClass: this.highlightMax, rank: true},
				{key: "classShift", label:"cSh", title: "Class Rating Shift (Par change from previous race)", formatter: this.format2Places, sortable: true, tdClass: this.highlightShift, reverse: true, rank: true},
				{key: "purseShift", label:"pSh", title: "Purse Shift (change from previous race)", formatter: this.formatInt, sortable: true, tdClass: this.highlightShift, reverse: true, rank: true},
                {key: "detailsButton", label:""},
				{key: "_showDetails", thClass: 'd-none', tdClass: 'd-none' }

			],
			trackConditions: [
				{value: "ft", text: "Fast"},
				{value: "my", text: "Muddy"},
				{value: "wf", text: "Wet Fast"},
				{value: "yl", text: "Yielding"},
				{value: "hy", text: "Heavy"},
				{value: "gd", text: "Good"},
				{value: "sy", text: "Sloppy"},
				{value: "fm", text: "Firm"},
				{value: "sf", text: "Soft"},
				{value: "sl", text: "Slow"}
			],
            selectionOptions : [
                "A","B","C","S","X",""
            ],
            bettingLineOptions : [
                {value: 0, text: "99"},
                {value: 1, text: "Even - 50%"},
                {value: 1.2, text: "6/5 - 45.5%"},
                {value: 1.4, text: "7/5 - 41.7%"},
                {value: 1.5, text: "3/2 - 40%"},
                {value: 1.6, text: "8/5 - 38.5%"},
                {value: 1.8, text: "9/5 - 35.7%"},
                {value: 2, text: "2 - 33.3%"},
                {value: 2.5, text: "5/2 - 28.6%"},
                {value: 3, text: "3 - 25%"},
                {value: 3.5, text: "7/2 - 22.2%"},
                {value: 4, text: "4 - 20%"},
                {value: 4.5, text: "9/2 - 18.2%"},
                {value: 5, text: "5 - 16.7%"},
                {value: 6, text: "6 - 14.3%"},
                {value: 7, text: "7 - 12.5%"},
                {value: 8, text: "8 - 11.1%"},
                {value: 9, text: "9 - 10%"}
            ],
            validBettingLineCombinations: [
                [1,5,6],
                [1.2,3.5,6],[1.2,4,5],[1.2,4,6],[1.2,4.5,4.5],[1.2,4.5,5],
                [1.4,3,6],[1.4,3.5,4.5],[1.4,3.5,5],[1.4,4,4],[1.4,4,4.5],
                [1.5,3,6],[1.5,3.5,4.5],[1.5,4,4],
                [1.6,2.5,6],[1.6,3,4.5],[1.6,3,5],[1.6,3.5,4],[1.6,6,6,6],
                [1.8,2.5,4.5],[1.8,2.5,5],[1.8,3,4],[1.8,3.5,3.5],[1.8,4.5,6,6],[1.8,5,5,6],[1.8,5,6,6],
                [2,2,6],[2,2.5,4],[2,2.5,4.5],[2,3,3.5],[2,4,6,6],[2,4.5,5,6],[2,5,5,6],
                [2.5,2.5,3],[2.5,4,6,6],[2.5,3.5,5,6],[2.5,4,5,5],[2.5,4.5,5,6],
                [3,3,5,6],[3,3.5,4,6],[3,3.5,4.5,5],[3,3.5,4.5,6],[3,3.5,5,5],[3,4,4,5],[3,4,4,6],[3,4,4.5,4.5],[3,4,4.5,5],[3,4.5,4.5,4.5],
                [3.5,3.5,3.5,6],[3.5,3.5,4,5],[3.5,3.5,4.5,4.5],[3.5,4,4,4.5],
                [4,4,4,4]
            ]
        }
    },
    computed: {
        unscratchedHorses () {
            return _.where(this.race.horses, {scratchedFlag: false});
        },
        bettingLines () {
            return _.filter(_.pluck(this.race.horses, 'bettingLine'), function(l) {
                return l > 0;
            }).sort();
        },
        bettingPercentages () {
            return _.map(this.bettingLines, function(l){
                return 1/(l+1) * 100;
            })
        },
        contenderTotal () {
            return _.reduce(this.bettingPercentages, function(memo, num){ return memo + num; }, 0)
        },
        matchingBettingLines () {
            var bettingLines = this.bettingLines;
            if (bettingLines.length == 0) return;
            return _.filter(this.validBettingLineCombinations, function(c){
                var match = true;
                for (var i = 0; i < bettingLines.length; i++) {
                    if (bettingLines[i] != c[i]) match = false;
                }
                return match;
            });

        },
        bettingLinesInvalid () {
            return this.contenderTotal <= 77.49 || this.contenderTotal >= 82.51;
        }
    },
    methods:  {
		async toggleScratch(item) {
            try {
                await axios({
                    url: 'toggleScratch/' + this.race.raceNumber + '/' + item.name,
                    method: 'GET',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/'
                });
                item.scratchedFlag = !item.scratchedFlag;
                if (item.scratchedFlag) {
                    item.selection = "";
                    item.pickFlag = false;
                }
            } catch (err) {
                console.log(err.response);
                
            }
		},
		async togglePick(item) {
            try {
                var formData = new FormData();
                formData.append("raceNumber", this.race.raceNumber);
                formData.append("name", item.name);
                await axios({
                    url: 'togglePick',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });

                item.pick = !item.pick;
                if (item.pick) {
                    for (var i = 0; i < this.race.horses.length; i++) {
                        if (this.race.horses[i].programNumber != item.programNumber && this.race.horses[i].pick) this.$emit('togglePick', this.race.horses[i]);
                    }
                }                
            } catch (err) {
                console.log(err.response);
                
            }
		},        	
        async toggleShowDetail(row) {
            try {
                row.toggleDetails();
                var formData = new FormData();
                formData.append("raceNumber", this.race.raceNumber);
                formData.append("name", row.item.name);
                await axios({
                    url: 'toggleShowDetail',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });	                
            } catch (err) {
                console.log(err.response);
                
            }               
        },
        async updateSelection(horse) {
            try {
                var formData = new FormData();
                formData.append("raceNumber", horse.raceNumber);
                formData.append("name", horse.name);
                formData.append("selection", horse.selection);
                const response = await axios({
                    url: 'setSelection',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });
                this.$emit('selectionUpdate', response.data);
            } catch (err) {
                console.log(err.response);
                
            }               
        },       
        async updateBettingLine(horse) {
            try {
                var formData = new FormData();
                formData.append("raceNumber", horse.raceNumber);
                formData.append("name", horse.name);
                formData.append("bettingLine", horse.bettingLine);
                const response = await axios({
                    url: 'setBettingLine',
                    method: 'POST',
                    baseURL: 'http://localhost:8080/jpp/rest/remote/',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    data: formData
                });                
                this.$emit('bettingLineUpdate', response.data);
            } catch (err) {
                console.log(err.response);
                
            }  
        },
		async setHorseNote(horse) {
            try {
				this.loading = true;
                var formData = new FormData();
                formData.append("raceNumber", horse.raceNumber);
                formData.append("name", horse.name);
                formData.append("note", horse.note);
                await axios({
                    url: 'setHorseNote',
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
		rowClass(item, type) {
            var cl = "horse ";
			if (!item || type !== 'row') return cl;
			if (item.scratchedFlag) return cl + 'table-dark scratched';
            if (item.selection  == 'A') return cl + 'aHorse';
            if (item.selection  == 'B') return cl + 'bHorse';
            if (item.selection  == 'C') return cl + 'cHorse';
            if (item.selection  == 'X') return cl + 'xHorse';
			return cl + 'notScratched';
		},		
		fractionString(decimal) {
			switch(decimal) {
				case 0.13:
					return "hd";
				case 0.25:
					return "&frac14;";
				case 0.5:
					return "&frac12;";
				case 0.75:
					return "&frac34;";
			}
		},
        convertOddsToFraction(odds) {
            switch (odds) {
                case 1: return "Even";
                case 1.2 : return "6/5";
                case 1.4 : return "7/5";
                case 1.5 : return "3/2";
                case 1.6 : return "8/5";
                case 1.8 : return "9/5";
                case 2 : return "2";
                case 2.5 : return "5/2";
                case 3 : return "3";
                case 3.5 : return "7/2";
                case 4 : return "4";
                case 4.5 : return "9/2";
                case 5 : return "5";
                case 6 : return "6";
            }
            return odds + "";
        },
		shortenJockeyName(value) {
			return value.split(" ")[0] + value.split(" ")[1].charAt(0);
		},
		format2Places(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},
		format1Place(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 1, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 1, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},
		formatInt(amount) {
			const formatter = new Intl.NumberFormat('en-US', {


				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
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
		formatCurrency2(amount) {
			const formatter = new Intl.NumberFormat('en-US', {
				style: 'currency',
				currency: 'USD',

				// These options are needed to round to whole numbers if that's what you want.
				minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
				maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
			});
			return formatter.format(amount);
		},        	
		formatDate (date) {
			return date[1] + "/" + date[2] + "/" + date[0];
		},  
        formatOdds (value, key) {
            return "text-center " + ((this.hideML) ? "" : this.highlightMin(value,key));

        },
        highlightName (value, key, item) {
            if (item.flag == 'Star') return "star";
        },   
		highlightMin(value, key) {
			var values = _.pluck(_.reject(this.race.horses, function(h) {
				return h.scratchedFlag;
			}), key);

			if (value == _.min(values)) {
				return "greenHighlight";
			}
		},          
		highlightMax(value, key) {
			var values = _.pluck(_.reject(this.race.horses, function(h) {
				return h.scratchedFlag;
			}), key);

			if (value == _.max(values)) {
				return "greenHighlight";
			}
		},
		highlightDaysSince(value) {
			if (value  > 180) return "gt180";
			if (value > 90) return "gt90";
			if (value > 45) return "gt45";
		},
		highlightDaysSincePP(value, key , item) {
			if (item.daysSinceLastRace  > 180) return "gt180";
			if (item.daysSinceLastRace > 90) return "gt90";
			if (item.daysSinceLastRace > 45) return "gt45";
		},	
		highlightFinish(value, key, item) {
			if (item.finishPosition == "1") return "greenHighlight";
			if (item.finishPosition == "2" || item.finishPosition == "3") return "lightGreenHighlight";
		},	
		highlightBeatenFavorite(value,key,item) {
			if (item.favoriteFlag && item.finishPosition != "1") return "yellowHighlight";
		},
		highlightPurseShift(value,key,item) {
			if (item.purse >= this.race.purse + 20000 ) return "greenHighlight";
			if (item.purse > this.race.purse) return "lightGreenHighlight";
			if (item.purse == this.race.purse) return "";
			if (item.purse < this.race.purse - 10000) return "yellowHighlight";
			if (item.purse < this.race.purse - 20000) return "lightRedHighlight";
			if (item.purse < this.race.purse - 30000) return "redHighlight";
		},
		highlightSpeedInRange(value,key,item) {
			if (item.brisspeedRating >= this.race.parSpeed) return "greenHighlight";
			if (item.brisspeedRating >= this.race.parSpeed - 3) return "lightGreenHighlight";
		},
		highlightPaceAdvantage(value, key, item) {
			if (_.contains(this.race.advantagedHorses, item.name)) return "greenHighlight";
		},
		highlightShift(value) {
			if (value < 0) return "greenHighlight";
			if (value > 0) return "yellowHighlight";
		},
		highlightSimilarSurfDist(value, key, item) {
			if (this.race.surface.indexOf("TURF") > -1 && !this.race.offTheTurfFlag) {
				if (
					item.surface.indexOf("TURF") > -1 
					&& ((this.race.trackCondition == "fm" && item.trackCondition == "fm") || (this.race.trackCondition != "fm" && ["tf","gd","gf","hd","yl","sf","hy","sl"].indexOf(item.trackCondition) > -1))
					&& ((this.race.furlongs < 8 && item.furlongs < 8) || (this.race.furlongs >= 8 && item.furlongs >= 8))) 
					return "blueHighlight";
			} else {
				if ((item.surface.indexOf("TURF") == -1 || item.offTheTurfFlag)
					&& ((this.race.trackCondition == "ft" && item.trackCondition == "ft") || (this.race.trackCondition != "ft" && ["gd","hy","my","sl","sy","wf"].indexOf(item.trackCondition) > -1))
					&& ((this.race.furlongs < 8 && item.furlongs < 8) || (this.race.furlongs >= 8 && item.furlongs >= 8))) 
					return "blueHighlight";
			}
		},
		alsoInRace(nameToMatch, row) {
			var names = _.pluck(this.race.horses, "name");
			if (names.indexOf(nameToMatch) > -1 && nameToMatch != row.name) return "alsoInRace";

		},
        properize (name) {
            var upperCase = /^[A-Z]/; //Regexp for all UPPERCASE words
            var suffixes = new Array("II", "(II)", "III", "(III)", "IV", "(IV)", "VI", "(VI)", "VII", "(VII)", "2nd", "(2nd)", "3rd", "(3rd)", "4th", "(4th)", "5th", "(5th)");
            var surnames = new Array("ApShaw", "d'Albini", "d'Aubigney", "d'Aubigne", "d'Autry", "d'Entremont", "d'Hurst", "D'ovidio", "da Graca", "DaSilva", "DeAnda", "deAnnethe", "deAubigne", "deAubigny", "DeBardelaben", "DeBardeleben", "DeBaugh", "deBeauford", "DeBerry", "deBethune", "DeBetuile", "DeBoard", "DeBoer", "DeBohun", "DeBord", "DeBose", "DeBrouwer", "DeBroux", "DeBruhl", "deBruijn", "deBrus", "deBruse", "deBrusse", "DeBruyne", "DeBusk", "DeCamp", "deCastilla", "DeCello", "deClare", "DeClark", "DeClerck", "DeCoste", "deCote", "DeCoudres", "DeCoursey", "DeCredico", "deCuire", "DeCuyre", "DeDominicios", "DeDuyster", "DeDuytscher", "DeDuytser", "deFiennes", "DeFord", "DeForest", "DeFrance", "DeFriece", "DeGarmo", "deGraaff", "DeGraff", "DeGraffenreid", "DeGraw", "DeGrenier", "DeGroats", "DeGroft", "DeGrote", "DeHaan", "DeHaas", "DeHaddeclive", "deHannethe", "DeHatclyf", "DeHaven", "DeHeer", "DeJager", "DeJarnette", "DeJean", "DeJong", "deJonge", "deKemmeter", "deKirketon", "DeKroon", "deKype", "del-Rosario", "dela Chamotte", "DeLa Cuadra", "DeLa Force", "dela Fountaine", "dela Grena", "dela Place", "DeLa Ward", "DeLaci", "DeLacy", "DeLaet", "DeLalonde", "DelAmarre", "DeLancey", "DeLascy", "DelAshmutt", "DeLassy", "DeLattre", "DeLaughter", "DeLay", "deLessine", "DelGado", "DelGaudio", "DeLiberti", "DeLoache", "DeLoatch", "DeLoch", "DeLockwood", "DeLong", "DeLozier", "DeLuca", "DeLucenay", "deLucy", "DeMars", "DeMartino", "deMaule", "DeMello", "DeMinck", "DeMink", "DeMoree", "DeMoss", "DeMott", "DeMuynck", "deNiet", "DeNure", "DePalma", "DePasquale", "dePender", "dePercy", "DePoe", "DePriest", "DePu", "DePui", "DePuis", "DeReeper", "deRochette", "deRose", "DeRossett", "DeRover", "deRuggele", "deRuggle", "DeRuyter", "deSaint-Sauveur", "DeSantis", "desCuirs", "DeSentis", "DeShane", "DeSilva", "DesJardins", "DesMarest", "deSoleure", "DeSoto", "DeSpain", "DeStefano", "deSwaert", "deSwart", "DeVall", "DeVane", "DeVasher", "DeVasier", "DeVaughan", "DeVaughn", "DeVault", "DeVeau", "DeVeault", "deVilleneuve", "DeVilliers", "DeVinney", "DeVito", "deVogel", "DeVolder", "DeVolld", "DeVore", "deVos", "DeVries", "deVries", "DeWall", "DeWaller", "DeWalt", "deWashington", "deWerly", "deWessyngton", "DeWet", "deWinter", "DeWitt", "DeWolf", "DeWolfe", "DeWolff", "DeWoody", "DeYager", "DeYarmett", "DeYoung", "DiCicco", "DiCredico", "DiFillippi", "DiGiacomo", "DiMarco", "DiMeo", "DiMonte", "DiNonno", "DiPietro", "diPilato", "DiPrima", "DiSalvo", "du Bosc", "du Hurst", "DuFort", "DuMars", "DuPre", "DuPue", "DuPuy", "FitzUryan", "kummel", "LaBarge", "LaBarr", "LaBauve", "LaBean", "LaBelle", "LaBerteaux", "LaBine", "LaBonte", "LaBorde", "LaBounty", "LaBranche", "LaBrash", "LaCaille", "LaCasse", "LaChapelle", "LaClair", "LaComb", "LaCoste", "LaCount", "LaCour", "LaCroix", "LaFarlett", "LaFarlette", "LaFerry", "LaFlamme", "LaFollette", "LaForge", "LaFortune", "LaFoy", "LaFramboise", "LaFrance", "LaFuze", "LaGioia", "LaGrone", "LaLiberte", "LaLonde", "LaLone", "LaMaster", "LaMay", "LaMere", "LaMont", "LaMotte", "LaPeer", "LaPierre", "LaPlante", "LaPoint", "LaPointe", "LaPorte", "LaPrade", "LaRocca", "LaRochelle", "LaRose", "LaRue", "LaVallee", "LaVaque", "LaVeau", "LeBleu", "LeBoeuf", "LeBoiteaux", "LeBoyteulx", "LeCheminant", "LeClair", "LeClerc", "LeCompte", "LeCroy", "LeDuc", "LeFevbre", "LeFever", "LeFevre", "LeFlore", "LeGette", "LeGrand", "LeGrave", "LeGro", "LeGros", "LeJeune", "LeMaistre", "LeMaitre", "LeMaster", "LeMesurier", "LeMieux", "LeMoe", "LeMoigne", "LeMoine", "LeNeve", "LePage", "LeQuire", "LeQuyer", "LeRou", "LeRoy", "LeSuer", "LeSueur", "LeTardif", "LeVally", "LeVert", "LoMonaco", "Macabe", "Macaluso", "MacaTasney", "Macaulay", "Macchitelli", "Maccoone", "Maccurry", "Macdermattroe", "Macdiarmada", "Macelvaine", "Macey", "Macgraugh", "Machan", "Machann", "Machum", "Maciejewski", "Maciel", "Mackaben", "Mackall", "Mackartee", "Mackay", "Macken", "Mackert", "Mackey", "Mackie", "Mackin", "Mackins", "Macklin", "Macko", "Macksey", "Mackwilliams", "Maclean", "Maclinden", "Macomb", "Macomber", "Macon", "Macoombs", "Macraw", "Macumber", "Macurdy", "Macwilliams", "MaGuinness", "MakCubyn", "MakCumby", "Mcelvany", "Mcsherry", "Op den Dyck", "Op den Graeff", "regory", "StElmo", "StGelais", "StJacques", "te Boveldt", "VanAernam", "VanAken", "VanAlstine", "VanAmersfoort", "VanAntwerp", "VanArlem", "VanArnam", "VanArnem", "VanArnhem", "VanArnon", "VanArsdale", "VanArsdalen", "VanArsdol", "vanAssema", "vanAsten", "VanAuken", "VanAwman", "VanBaucom", "VanBebber", "VanBeber", "VanBenschoten", "VanBibber", "VanBilliard", "vanBlare", "vanBlaricom", "VanBuren", "VanBuskirk", "VanCamp", "VanCampen", "VanCleave", "VanCleef", "VanCleve", "VanCouwenhoven", "VanCovenhoven", "VanCowenhoven", "VanCuren", "VanDalsem", "VanDam", "VanDe Poel", "vanden Dijkgraaf", "vanden Kommer", "VanDer Aar", "vander Gouwe", "VanDer Honing", "VanDer Hooning", "vander Horst", "vander Kroft", "vander Krogt", "VanDer Meer", "vander Meulen", "vander Putte", "vander Schooren", "VanDer Veen", "VanDer Ven", "VanDer Wal", "VanDer Weide", "VanDer Willigen", "vander Wulp", "vander Zanden", "vander Zwan", "VanDer Zweep", "VanDeren", "VanDerlaan", "VanDerveer", "VanderWoude", "VanDeursen", "VanDeusen", "vanDijk", "VanDoren", "VanDorn", "VanDort", "VanDruff", "VanDryer", "VanDusen", "VanDuzee", "VanDuzen", "VanDuzer", "VanDyck", "VanDyke", "VanEman", "VanEmmen", "vanEmmerik", "VanEngen", "vanErp", "vanEssen", "VanFleet", "VanGalder", "VanGelder", "vanGerrevink", "VanGog", "vanGogh", "VanGorder", "VanGordon", "VanGroningen", "VanGuilder", "VanGundy", "VanHaaften", "VanHaute", "VanHees", "vanHeugten", "VanHise", "VanHoeck", "VanHoek", "VanHook", "vanHoorn", "VanHoornbeeck", "VanHoose", "VanHooser", "VanHorn", "VanHorne", "VanHouten", "VanHoye", "VanHuijstee", "VanHuss", "VanImmon", "VanKersschaever", "VanKeuren", "VanKleeck", "VanKoughnet", "VanKouwenhoven", "VanKuykendaal", "vanLeeuwen", "vanLent", "vanLet", "VanLeuven", "vanLingen", "VanLoozen", "VanLopik", "VanLuven", "vanMaasdijk", "VanMele", "VanMeter", "vanMoorsel", "VanMoorst", "VanMossevelde", "VanNaarden", "VanNamen", "VanNemon", "VanNess", "VanNest", "VanNimmen", "vanNobelen", "VanNorman", "VanNormon", "VanNostrunt", "VanNote", "VanOker", "vanOosten", "VanOrden", "VanOrder", "VanOrma", "VanOrman", "VanOrnum", "VanOstrander", "VanOvermeire", "VanPelt", "VanPool", "VanPoole", "VanPoorvliet", "VanPutten", "vanRee", "VanRhijn", "vanRijswijk", "VanRotmer", "VanSchaick", "vanSchelt", "VanSchoik", "VanSchoonhoven", "VanSciver", "VanScoy", "VanScoyoc", "vanSeters", "VanSickle", "VanSky", "VanSnellenberg", "vanStaveren", "VanStraten", "VanSuijdam", "VanTassel", "VanTassell", "VanTessel", "VanTexel", "VanTuyl", "VanValckenburgh", "vanValen", "VanValkenburg", "VanVelsor", "VanVelzor", "VanVlack", "VanVleck", "VanVleckeren", "VanWaard", "VanWart", "VanWassenhove", "VanWinkle", "VanWoggelum", "vanWordragen", "VanWormer", "VanZuidam", "VanZuijdam", "VonAdenbach", "vonAllmen", "vonBardeleben", "vonBerckefeldt", "VonBergen", "vonBreyman", "VonCannon", "vonFreymann", "vonHeimburg", "VonHuben", "vonKramer", "vonKruchenburg", "vonPostel", "VonRohr", "VonRohrbach", "VonSass", "VonSasse", "vonSchlotte", "VonSchneider", "VonSeldern", "VonSpringer", "VonVeyelmann", "VonZweidorff");
            surnames = surnames.concat(suffixes); //Append suffixes array to the end of surnames
            var mc = /^Mc(\w)(?=\w)/i; //Regexp for "Mc"
            var mac = /^Mac(\w)(?=\w)/i; //Regexp for "Mac"
            var hyphen_index = [];
            var hyphen = false;
            while (name.indexOf('-') > -1) { //Loops to record positions of hyphens (to put them back later) and convert the hyphen to a space (to break up name into individual words)
                var index = name.indexOf('-');
                if (index === 0) { //If name begins with hypen, just remove the first character from the name and loop again
                    name = name.substr(1);
                    continue;
                }
                hyphen_index.push(index); //Record hyphen position
                name = name.substring(0, index) + ' ' + name.substr(index + 1); //Change hyphen to a space
                hyphen = true;
            }
            var names = name.split(' '); //Put individual words in name into an array
            for (var i = 0; i < names.length; i++) //Loop through words in name if they are all CAPS, make them all lowercase
            if (upperCase.test(names[i])) names[i] = names[i].toLowerCase();
            for (i = 0; i < names.length; i++) //Loop through words in name and capitalize the first letter
            names[i] = names[i].charAt(0).toUpperCase() + names[i].substr(1); //Change word to capitalized version
            for (i = 0; i < names.length; i++) { //Loop through words in name and check for "mcx" and "macx"
                if (mc.test(names[i])) //Look for "Mc" start of name word
                names[i] = "Mc" + names[i].charAt(2).toUpperCase() + names[i].substr(3); //Change word to capitalized version
                if (mac.test(names[i])) //Look for "Mac" start of name word
                names[i] = "Mac" + names[i].charAt(3).toUpperCase() + names[i].substr(4); //Change word to capitalized version
            }
            name = names.join(' '); //Join words of name back together
            if (hyphen) //Add hyphens back if they originally existed
            for (i = 0; i < hyphen_index.length; i++) //Cycle through hyphen index
            name = name.substr(0, hyphen_index[i]) + '-' + name.substr(hyphen_index[i] + 1); //Replace positions in name from hyphen index with hyphens
            var apos = name.indexOf("'");
            if (apos > -1) {
                var ucApos = name.substr(apos + 1, 1).toUpperCase();
                name = name.substr(0, apos + 1) + ucApos + name.substr(apos + 2);
            }
            name = name.replace(/ De /gi, ' de '); //Replace ' De ' with ' de '
            name = name.replace(/ Dit /gi, ' dit '); //Replace ' Dit ' with ' dit '
            name = name.replace(/ Van /gi, ' van '); //Replace ' Van ' with ' van '
            var lcName = name.toLowerCase(); //Copy of name in lower-case
            for (i = 0; i < surnames.length; i++) {
                var pos = lcName.indexOf(surnames[i].toLowerCase());
                if (pos > -1) {
                    if (((pos === 0) || (pos > 0 && name.charAt(pos - 1) == ' ')) && ((name.length == pos + surnames[i].length) || (name.charAt(pos + surnames[i].length) == ' '))) name = name.substring(0, pos) + surnames[i] + name.substr(pos + surnames[i].length);
                }
            }
            return name;	
        },
        rankOf(value, property, reverse) {
            var sa = _.pluck(this.unscratchedHorses, property);
            var sas = _.sortBy(sa, function(h) { return h;});
            if (!reverse) sas.reverse();
            return sas.indexOf(value) + 1;
        },
        payoutString(horse) {
            return (horse.winPayout > 0 ? this.formatCurrency2(horse.winPayout) + " " : "") 
                + (horse.placePayout > 0 ? this.formatCurrency2(horse.placePayout) + " " : "")
                + (horse.showPayout > 0 ? this.formatCurrency2(horse.showPayout) : "")
        },
        goToChart(pp) {
            this.$emit("goToChart", pp);
        }
    }

}
</script>


