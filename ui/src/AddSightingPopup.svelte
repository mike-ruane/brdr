<script>
  import Select from "svelte-select";
  import { DateInput } from 'date-picker-svelte'
  import dayjs from 'dayjs';
  import { getContext } from 'svelte';
  import Popup from './AddSightingResponse.svelte';

  export let species = [];
  export let counties = []
  let locations = [];
  export let date = new Date();
  let selectedSpecies = "";
  let selectedLocation = "";
  let formData = {};
  formData["date"] = date;

  const { open } = getContext('simple-modal');
  const showResponseModal = () => open(Popup, { species: selectedSpecies, location: selectedLocation, date: date });

  function selectSpecies(event) {
    formData["speciesId"] = event.detail.id;
    selectedSpecies = event.detail.preferredCommonName;
  }

  async function selectCounty(event) {
    const countyId = event.detail.id;
    await fetch(`http://localhost:8000/v1/locations/${countyId}`)
    .then(response => response.json())
    .then(data => locations = data)
  }

  function selectLocation(event) {
    formData["locationId"] = event.detail.id;
    selectedLocation = event.detail.name;
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
    await fetch(`http://localhost:8000/v1/sightings`, settings)
    .then(response => {
      if (response.status === 201) {
        showResponseModal();
      }
    })
  }


</script>

<div class="addSighting">
  <div class="select">
    <Select
        items={species}
        optionIdentifier="id"
        labelIdentifier="preferredCommonName"
        placeholder="enter species..."
        on:select={selectSpecies}
    />
  </div>
  <div class="select">
    <Select
        items={counties}
        optionIdentifier="id"
        labelIdentifier="name"
        placeholder="enter county..."
        on:select={selectCounty}
    />
  </div>
    <div class="select">
      <Select
          items={locations}
          optionIdentifier="id"
          labelIdentifier="name"
          placeholder="enter location..."
          on:select={selectLocation}
      />
  </div>
  <div class="lastRow">
    <div class="dateInput">
      <DateInput
          bind:value={date}
          format="dd/MM/yyyy"
          closeOnSelection="true"
          on:select={() => formData["date"] = dayjs(date).format('YYYY-MM-DD')}
      />
    </div>
    <button on:click={handleSubmit}>Submit</button>
  </div>
</div>

<style>

  .addSighting {
    flex: 1;
    padding: 30px 30px;
  }

  .select {
    padding: 10px 0 10px;
  }

  .lastRow {
    display: flex;
    column-gap: 30px;
  }

  .dateInput {
    flex: 4;
  }

  button {
    flex: 1;
  }
</style>
