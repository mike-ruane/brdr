<script>
	import {Route, Router, Link} from "svelte-routing";
	import AddSighting from "./AddSighting.svelte";
	import SightingsOverview from "./SightingsOverview.svelte";
	import Sightings from "./Sightings.svelte";

	export let url = "";
</script>

<Router url="{url}">
	<header>
		<nav>
			<Link to="/">Sightings</Link>
			<Link to="add-sighting">Add Sighting</Link>
		</nav>
	</header>
	<hr/>
	<div>
		<Route path="/">
			<Sightings/>
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
