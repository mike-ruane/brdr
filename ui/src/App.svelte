<script>
	import {Route, Router} from "svelte-routing";
	import SpeciesOverview from "./SpeciesOverview.svelte";
</script>

<Router url="">
	<div style="display: flex;">
		<div style="flex-grow: 1;">
		</div>
	</div>
	<hr/>
	<div>
		<Route path="/">
			<SpeciesOverview/>
		</Route>
	</div>
</Router>

<style>
	:global(body) {
		font-family: 'Courier New', monospace;
	}
</style>
