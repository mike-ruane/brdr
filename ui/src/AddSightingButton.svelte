<script>
  import {getContext, onMount} from 'svelte';
  import AddSightingPopup from "./AddSightingPopup.svelte";
  const { open } = getContext('simple-modal');
  let counties = [];
  let species = [];
  onMount(async () => {
    await fetch(`http://localhost:8000/v1/species`)
    .then(response => response.json())
    .then(data => species = data)
    .catch((error) => {
      console.error('Error:', error);
    });
    await fetch(`http://localhost:8000/v1/counties`)
    .then(response => response.json())
    .then(data => counties = data)
    .catch((error) => {
      console.error('Error:', error);
    });
  })

  const showAddSighting = () => open(AddSightingPopup, { counties: counties, species: species });
</script>

<button on:click={showAddSighting}>Add a sighting!</button>

<style>
  button {
    position: absolute;
    z-index: 100;
  }

</style>