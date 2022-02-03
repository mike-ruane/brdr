<script>
  import {onMount} from "svelte";
  import Map from './Map.svelte';
  import MapMarker from './MapMarker.svelte';
  import dayjs from "dayjs";

  import mbxGeocode from "@mapbox/mapbox-sdk/services/geocoding";

  let sightings = [];
  let showList = false;
  let locationToShow;

  onMount(async () => {
    await fetch(`http://localhost:8000/v1/sightings/1`)
    .then(response => response.json())
    .then(data => {
      sightings = data;
    })});
</script>

<div class="container">
  <Map lat={55} lon={-2.7885207382742863} zoom={5}>
    {#each sightings as sighting}
      <MapMarker
          lat={sighting.lat}
          lon={sighting.lon}
          count={sighting.sightings.length}
          location={sighting.name}
          bind:show={showList}
          bind:listLocation={locationToShow}/>
    {/each}
  </Map>
</div>

<style>
  .container {
    position: relative;
    top: 0px;
    bottom: 40px;
    width: 100%;
    height: 100vh;
  }
</style>