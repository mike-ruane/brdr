<script>
  import { getContext } from 'svelte';
  import { mapbox, key } from './mapbox.js';

  const { getMap } = getContext(key);
  const map = getMap();

  export let lat;
  export let lon;
  export let count;
  export let sighting = {};
  let tooltipId = `${sighting.name}-tooltip`

  const newDiv = document.createElement('div');
  newDiv.id = sighting.name;
  newDiv.style.borderRadius = `50%`;
  newDiv.style.width = `18px`;
  newDiv.style.height = `18px`;
  newDiv.style.padding = `4px`;
  newDiv.style.background = `#fff`;
  newDiv.style.border = `2px solid #666`;
  newDiv.style.color = `#666`;
  newDiv.style.textAlign = `center`;

  const newContent = document.createTextNode(count);
  newDiv.appendChild(newContent);

  const list = document.createElement('ul');
  list.style.fontFamily = `'Courier New', monospace`;
  list.style.listStyleType = `none`;
  list.style.textAlign = `left`;
  list.style.padding = `0`;
  list.style.margin = `0`;
  sighting.sightings.forEach(function (arrayItem) {
    const listEl = document.createElement('li');
    listEl.appendChild(document.createTextNode(arrayItem.species));
    list.appendChild(listEl);
  });

  const popup = new mapbox.Popup({ offset: 25 }).setDOMContent(list)
  const marker = new mapbox.Marker(newDiv)
  .setLngLat([lon, lat])
  .setPopup(popup)
  .addTo(map);
</script>

<style>
  :global(.mapboxgl-popup-content) {
    max-height: 300px;
    overflow-y: scroll;
  }
</style>
