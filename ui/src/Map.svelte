<script>
  import { onDestroy, setContext } from 'svelte';
  import { mapbox, key } from './mapbox.js';

  setContext(key, {
    getMap: () => map,
  });

  export let lat;
  export let lon;
  export let zoom;

  let container;
  let map;

  function load() {
    map = new mapbox.Map({
      container,
      style: 'mapbox://styles/mapbox/outdoors-v11',
      center: [lon, lat],
      zoom,
    });
  }

  onDestroy(() => {
    if (map) map.remove();
  });
</script>

<svelte:head>
  <link
      rel="stylesheet"
      href="https://unpkg.com/mapbox-gl/dist/mapbox-gl.css"
      on:load={load}
  />
</svelte:head>

<div bind:this={container}>
  {#if map}
    <slot />
  {/if}
</div>

<style>
  div { position: absolute; top: 0; bottom: 0; width: 100%; }
</style>
