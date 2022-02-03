<script>
  import Select from "svelte-select";
  import { DateInput } from 'date-picker-svelte'
  let date = new Date()
  import {onMount} from "svelte";
  import dayjs from 'dayjs';
  import { getContext } from 'svelte';
  import Popup from './Popup.svelte';

  const { open } = getContext('simple-modal');
  const showSurprise = () => open(Popup, { species: selectedSpecies, location: selectedLocation, date: date });
  // species select
  let species = [];
  let selectedSpecies = "";
  const speciesOptionId = "id";
  const speciesLabelId = "preferredCommonName";

  // location select
  let locations = [];
  let selectedLocation = "";
  const locationsOptionId = "id";
  const locationsLabelId = "name";

  let store;

  let src = "";

  let formData = {};
  formData["date"] = date;

  function selectSpecies(event) {
    src = `images/${event.detail.id}.jpg`;
    formData["speciesId"] = event.detail.id;
    selectedSpecies = event.detail.preferredCommonName;
  }

  function selectLocation(event) {
    formData["locationId"] = event.detail.id;
    selectedLocation = event.detail.name;
  }

  function handleClear(event) {
    src = "";
  }

  async function handleSubmit() {
    formData["userId"] = "1";
    const settings = {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData)
    };
    // try {
      await fetch(`http://localhost:8000/v1/sightings`, settings)
      .then(response => {
        if (response.status === 201) {
          showSurprise();
        }
      })
  }

  const theme = {
    calendar: {
      width: "300px",
      legend: {
        height: "30px"
      },
      font: {
        regular: "1.0em",
        large: "10em"
      },
    }
  };

  onMount(async () => {
    await fetch(`http://localhost:8000/v1/species`)
    .then(response => response.json())
    .then(data => species = data)
    .catch((error) => {
      console.error('Error:', error);
    });
    await fetch(`http://localhost:8000/v1/locations`)
    .then(response => response.json())
    .then(data => locations = data)
    .catch((error) => {
      console.error('Error:', error);
    });
  })
</script>

<div class="addSighting">
  <div class="select">
    <Select
        items={species}
        optionIdentifier={speciesOptionId}
        labelIdentifier={speciesLabelId}
        placeholder="enter species..."
        on:select={selectSpecies}
        on:clear={handleClear}
    />
  </div>
  <div class="select">
    <Select
        items={locations}
        optionIdentifier={locationsOptionId}
        labelIdentifier={locationsLabelId}
        placeholder="enter location..."
        on:select={selectLocation}
    />
  </div>
  <DateInput bind:value={date} format="dd/MM/yyyy" closeOnSelection="true" on:select={() => formData["date"] = dayjs(date).format('YYYY-MM-DD')}/>
  <button on:click={handleSubmit}>Submit</button>
</div>

{#if src}
  <img class="image" {src} alt=""/>
{/if}

<style>

  .addSighting {
    padding: 80px 20px;
    display: flex;
    justify-content: center;
    column-gap: 30px;
  }

  .select {
    --height: 56px;
    min-width: 500px;
  }

  .image {
    display: block;
    margin-left: auto;
    margin-right: auto;
    border: 2px solid black;
  }

  button {
    height: 56px;
  }

  :global(input) {
    height: 56px;
  }

</style>
