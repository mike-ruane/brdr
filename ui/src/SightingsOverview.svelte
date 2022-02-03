<script>

  import {onMount} from "svelte";
  import {mapbox} from "./mapbox";

  let sightings = [];
  let map;

  onMount(async () => {
    const res = await fetch(`http://localhost:8000/v1/sightings/1`)
    sightings = await res.json();

    map = new mapbox.Map({
      container: 'map',
      style: 'mapbox://styles/mapbox/streets-v9',
      center: [-2.7885207382742863, 55],
      zoom: 6
    });
  });

</script>
<div id="map"></div>
<style>

  #map {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
  }

</style>