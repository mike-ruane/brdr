<script>
  import { getContext } from 'svelte';
  import { mapbox, key } from './mapbox.js';

  const { getMap } = getContext(key);
  const map = getMap();

  export let lat;
  export let lon;
  export let count;
  export let show = false;
  export let location;
  export let listLocation="";

  const newDiv = document.createElement("div");
  newDiv.id = location;
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
  newDiv.addEventListener('click', (e) => {
    if (!show) {
      show = true
    }
    listLocation = e.target.id;
  });

  const marker = new mapbox.Marker(newDiv)
  .setLngLat([lon, lat])
  .addTo(map);
</script>
