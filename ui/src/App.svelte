<script>
	import {Route, Router, Link} from "svelte-routing";
	import SpeciesOverview from "./SpeciesOverview.svelte";
	import AddSighting from "./AddSighting.svelte";

	export let url = "";
</script>

<Router url="{url}">
	<header>
		<nav>
			<Link to="/">Species</Link>
			<Link to="add-sighting">Add Sighting</Link>
		</nav>
	</header>
	<div style="display: flex;">
		<div style="flex-grow: 1;">
		</div>
	</div>
	<hr/>
	<div>
		<Route path="/">
			<SpeciesOverview/>
		</Route>
		<Route path="add-sighting">
			<AddSighting/>
		</Route>
	</div>
</Router>

<style>
	:global(body) {
		font-family: 'Courier New', monospace;
	}
</style>
