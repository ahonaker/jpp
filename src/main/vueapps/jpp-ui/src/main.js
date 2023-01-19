import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import RacesView from './views/RacesView.vue'
import PrintView from './views/PrintView.vue'
import AdminView from './views/AdminView.vue'
import ChartsView from './views/ChartsView.vue'

Vue.use(VueRouter)

const routes = [
	{
		path: "/",
		name: "Home",
		component: RacesView
	},
	{
		path: "/races",
		name: "RacesView",
		component: RacesView
	},
	{
		path: "/print",
		name: "PrintView",
		component: PrintView
	},
	{
		path: "/admin",
		name: "AdminView",
		component: AdminView
	},
	{
		path: "/charts",
		name: "ChartsView",
		component: ChartsView
	},
	{
		path: "/charts/:track/:year/:month/:day/:raceNumber",
		name: "ChartsViewRetrieve",
		component: ChartsView
	}
];

const router = new VueRouter({
	routes // short for `routes: routes`
})

import {
	AlertPlugin,
	BadgePlugin,
	BreadcrumbPlugin,
	ButtonGroupPlugin,
	ButtonPlugin,
	ButtonToolbarPlugin,
	CalendarPlugin,
	CardPlugin,
	CollapsePlugin,
	DropdownPlugin,
	FormPlugin,
	FormFilePlugin,
	FormInputPlugin,
	FormRadioPlugin,
	FormSelectPlugin,
	FormGroupPlugin,
	FormCheckboxPlugin,
	FormTextareaPlugin,
	FormDatepickerPlugin,
	ImagePlugin,
	InputGroupPlugin,
	ListGroupPlugin,
	LayoutPlugin,
	LinkPlugin,
	ModalPlugin,
	NavPlugin,
	NavbarPlugin,
	ProgressPlugin,
	SkeletonPlugin,
	SpinnerPlugin,
	TablePlugin,
	TabsPlugin,
	ToastPlugin,
	TooltipPlugin,
	VBPopoverPlugin
} from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(AlertPlugin)
Vue.use(BadgePlugin)
Vue.use(BreadcrumbPlugin)
Vue.use(ButtonGroupPlugin)
Vue.use(ButtonPlugin)
Vue.use(ButtonToolbarPlugin)
Vue.use(CalendarPlugin)
Vue.use(CardPlugin)
Vue.use(CollapsePlugin)
Vue.use(DropdownPlugin)
Vue.use(FormPlugin)
Vue.use(FormFilePlugin)
Vue.use(FormInputPlugin)
Vue.use(FormRadioPlugin)
Vue.use(FormSelectPlugin)
Vue.use(FormGroupPlugin)
Vue.use(FormCheckboxPlugin)
Vue.use(FormTextareaPlugin)
Vue.use(FormDatepickerPlugin)
Vue.use(ImagePlugin)
Vue.use(InputGroupPlugin)
Vue.use(LinkPlugin)
Vue.use(ListGroupPlugin)
Vue.use(LayoutPlugin)
Vue.use(ModalPlugin)
Vue.use(NavPlugin)
Vue.use(NavbarPlugin)
Vue.use(ProgressPlugin)
Vue.use(SkeletonPlugin)
Vue.use(SpinnerPlugin)
Vue.use(TablePlugin)
Vue.use(TabsPlugin)
Vue.use(ToastPlugin)
Vue.use(TooltipPlugin)
Vue.use(VBPopoverPlugin)

Vue.config.productionTip = false

new Vue({
	router,
	render: h => h(App)
}).$mount('#app')
