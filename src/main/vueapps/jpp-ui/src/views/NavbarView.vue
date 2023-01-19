<template>
    <b-navbar id="nav" toggleable="sm" class="pt-2 pb-0" >						
        <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav small class="align-items-center">
                <b-nav-item class="brand">JPP-UI</b-nav-item>
                <b-nav-item to="/">Races</b-nav-item>
                <b-nav-item to="/print">Print</b-nav-item>
                <b-nav-item to="/admin">Admin</b-nav-item>
                <b-nav-item to="/horses">Horses</b-nav-item>
                <b-nav-item to="/charts">Charts</b-nav-item>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script>
export default {
	name: 'NavbarView',
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