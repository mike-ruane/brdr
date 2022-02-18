<script>
  import {onMount} from "svelte";
  import Map from './Map.svelte';
  import MapMarker from './MapMarker.svelte';
  import Modal from "svelte-simple-modal";
  import AddSightingButton from "./AddSightingButton.svelte";

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

<Modal styleContent={{ 'display': 'flex', 'justify-content': 'center' }}>
  <AddSightingButton/>
</Modal>
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
    top: 0;
    bottom: 40px;
    width: 100%;
    height: 100vh;
  }
</style>