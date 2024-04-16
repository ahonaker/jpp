<template>
    <div>
		<navbar-view :status="status"></navbar-view>
        <b-tabs>
            <b-tab title="Ratings">
                <b-table striped hover bordered :items="statsArray" :fields="statsFields"></b-table>
            </b-tab>
            <b-tab title="Angles">
               <b-table striped hover :items="anglesArray" :fields="angleFields" >
                    <template #cell(name)="row">
                         <span v-b-tooltip.hover :title="row.item.text">{{row.item.name}}</span>
                    </template>
               </b-table>
            </b-tab>
            <b-tab title="Combos (2)">
                <b-table striped hover bordered :items="combos2Array" :fields="combosFields"></b-table>
            </b-tab>   
            <b-tab title="Combos (3)">
                <b-table striped hover bordered :items="combos3Array" :fields="combosFields"></b-table>
            </b-tab>                        
        </b-tabs>
    </div>    
</template>

<script>

import _ from "underscore"
import axios from 'axios'
import NavbarView from '@/views/NavbarView'

export default {
	name: 'RacesView',
	components: {
		NavbarView
	},
	data () {
		return {
			stats: [],
            angles: [],
            combos2: [],
            combos3: [],
            loadingStats: true,
            loadingAngles: true,
            loadingCombos2: true,
            loadingCombos3: true,
            statsFields: [
                {key: "Rating", sortable: false},
                {key: "Races", sortable: true},
                {key: "Wins", sortable: true},     
                {key: "Pct", sortable: true , formatter: value => {
                    const formatter = new Intl.NumberFormat('en-US', {

                        // These options are needed to round to whole numbers if that's what you want.
                        minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                        maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
                    });
			
                    return formatter.format(value * 100);
                    
                }
            },
                {key: "ROI", sortable: true , formatter: value => {
                    const formatter = new Intl.NumberFormat('en-US', {

                        // These options are needed to round to whole numbers if that's what you want.
                        minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                        maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
                    });
			
                    return formatter.format(value);
                    
                }},       
            ],
            angleFields: [
                {key: "source"},
                {key: "name"},
                {key: "type"},
                {key: "total", sortable: true},
                {key: "winners", sortable: true},
                {key: "winPercent", sortable: true, formatter: value => {
                    const formatter = new Intl.NumberFormat('en-US', {

                        // These options are needed to round to whole numbers if that's what you want.
                        minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                        maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
                    });
			
                    return formatter.format(value * 100);
                    
                }},
                {key: "itmPercent", label: "ITM Percent", sortable: true, formatter: value => {
                    const formatter = new Intl.NumberFormat('en-US', {

                        // These options are needed to round to whole numbers if that's what you want.
                        minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                        maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
                    });
			
                    return formatter.format(value * 100);
                    
                }},
                {key: "roi", label: "ROI", sortable: true, formatter: value => {
                    const formatter = new Intl.NumberFormat('en-US', {

                        // These options are needed to round to whole numbers if that's what you want.
                        minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                        maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
                    });
			
                    return formatter.format(value);
                    
                }},
            ],
            combosFields: [
                {key: "_id.angles", label: "Combo"},
                {key: "total", sortable: true},
                {key: "winners", sortable: true},
                {key: "winPercent", sortable: true, formatter: value => {
                    const formatter = new Intl.NumberFormat('en-US', {

                        // These options are needed to round to whole numbers if that's what you want.
                        minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                        maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
                    });
			
                    return formatter.format(value * 100);
                    
                }},
                {key: "itmPercent", label: "ITM Percent", sortable: true, formatter: value => {
                    const formatter = new Intl.NumberFormat('en-US', {

                        // These options are needed to round to whole numbers if that's what you want.
                        minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                        maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
                    });
			
                    return formatter.format(value * 100);
                    
                }},
                {key: "ROI", sortable: true, formatter: value => {
                    const formatter = new Intl.NumberFormat('en-US', {

                        // These options are needed to round to whole numbers if that's what you want.
                        minimumFractionDigits: 2, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                        maximumFractionDigits: 2, // (causes 2500.99 to be printed as $2,501)
                    });
			
                    return formatter.format(value);
                    
                }},
            ]        
        }
        
    },
	mounted() {
		this.getStats();
        this.getAngles();
        this.getCombos2();
        this.getCombos3();
	},
	computed: {
        status() {
            return (this.loadingStats || this.loadingAngles || this.loadingCombos2) ? "Loading" : "";
        },
        anglesArray() {
            return _.reject(this.angles, function(a){
                return (a.total == 0);
            })
        },
        combos2Array() {
            return _.reject(this.combos2, function(c){
                return (c.total < 20);
            })
        },   
        combos3Array() {
            return _.reject(this.combos3, function(c){
                return (c.total < 10);
            })
        },        
        statsArray() {
            var arr = [];
            arr.push({
                "Rating": "Favorite",
                "Races": this.stats.total,
                "Wins": this.stats.favorite,
                "Pct": this.stats.favoritePct,
                "ROI": this.stats.favoriteROI
            });
            arr.push({
                "Rating": "A Rating",
                "Races": this.stats.ARatingTotal,
                "Wins": this.stats.ARating,
                "Pct": this.stats.ARatingPct,
                "ROI": this.stats.ARatingROI
            });
            arr.push({
                "Rating": "A Rating ML > 4",
                "Races": this.stats.AMLGT4total,
                "Wins": this.stats.AMLGT4,
                "Pct": this.stats.AMLGT4Pct,
                "ROI": this.stats.AMLGT4ROI
            });
             arr.push({
                "Rating": "A Rating Odds > 4",
                "Races": this.stats.AOGT4total,
                "Wins": this.stats.AOGT4,
                "Pct": this.stats.AOGT4Pct,
                "ROI": this.stats.AOGT4ROI
            });
             arr.push({
                "Rating": "A Rating Odds > 8",
                "Races": this.stats.AOGT8total,
                "Wins": this.stats.AOGT8,
                "Pct": this.stats.AOGT8Pct,
                "ROI": this.stats.AOGT8ROI
            });
             arr.push({
                "Rating": "A Rating Odds Between 4 & 8",
                "Races": this.stats.AOBT48total,
                "Wins": this.stats.AOBT48,
                "Pct": this.stats.AOBT48Pct,
                "ROI": this.stats.AOBT48ROI
            });     
             arr.push({
                "Rating": "Prime Power",
                "Races": this.stats.primePowerTotal,
                "Wins": this.stats.primePower,
                "Pct": this.stats.primePowerPct,
                "ROI": this.stats.primePowerROI
            });   
            arr.push({
                "Rating": "Speed Rating",
                "Races": this.stats.speedRatingTotal,
                "Wins": this.stats.speedRating,
                "Pct": this.stats.speedRatingPct,
                "ROI": this.stats.speedRatingROI
            });  
            arr.push({
                "Rating": "Class Rating",
                "Races": this.stats.classRatingTotal,
                "Wins": this.stats.classRating,
                "Pct": this.stats.classRatingPct,
                "ROI": this.stats.classRatingROI
            });    
            arr.push({
                "Rating": "BRIS Avg Class Last 3",
                "Races": this.stats.brisAvgLast3ClassTotal,
                "Wins": this.stats.brisAvgLast3Class,
                "Pct": this.stats.brisAvgLast3ClassPct,
                "ROI": this.stats.brisAvgLast3ClassROI
            });             
            arr.push({
                "Rating": "BRIS Current Class",
                "Races": this.stats.brisCurrentClassTotal,
                "Wins": this.stats.brisCurrentClass,
                "Pct": this.stats.brisCurrentClassPct,
                "ROI": this.stats.brisCurrentClassROI
            }); 
            arr.push({
                "Rating": "Avg Competitive Level",
                "Races": this.stats.ACLtotal,
                "Wins": this.stats.ACL,
                "Pct": this.stats.ACLPct,
                "ROI": this.stats.ACLROI
            });  
            arr.push({
                "Rating": "A Rating Form",
                "Races": this.stats.ARatingFormTotal,
                "Wins": this.stats.ARatingForm,
                "Pct": this.stats.ARatingFormPct,
                "ROI": this.stats.ARatingFormROI
            });    
            arr.push({
                "Rating": "A Rating Connections",
                "Races": this.stats.ARatingConnectionsTotal,
                "Wins": this.stats.ARatingConnections,
                "Pct": this.stats.ARatingConnectionsPct,
                "ROI": this.stats.ARatingConnectionsROI
            });             
            arr.push({
                "Rating": "Combined Pace Avg",
                "Races": this.stats.combinedPaceAvgTotal,
                "Wins": this.stats.combinedPaceAvg,
                "Pct": this.stats.combinedPaceAvgPct,
                "ROI": this.stats.combinedPaceAvgROI
            }); 
            return arr;
        }
	},
    methods: {
		async getStats() {
			try {
				const response = await axios({
					url: 'getStats/',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.stats = response.data;
				this.loadingStats = false;
			} catch (err) {
				console.log(err.response);			
			}	
		},		
        async getAngles() {
			try {
				const response = await axios({
					url: 'getAngles/',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.angles = response.data;
				this.loadingAngles = false;
			} catch (err) {
				console.log(err.response);			
			}	
		},
        async getCombos2() {
			try {
				const response = await axios({
					url: 'getComboStats/2',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.combos2 = response.data;
				this.loadingCombos2 = false;
			} catch (err) {
				console.log(err.response);			
			}	
		},
        async getCombos3() {
			try {
				const response = await axios({
					url: 'getComboStats/3',
					method: 'GET',
					baseURL: 'http://localhost:8080/jpp/rest/remote/'
				});
				this.combos3= response.data;
				this.loadingCombos3 = false;
			} catch (err) {
				console.log(err.response);			
			}	
		}	            
    }
}
</script>
