<script>
  import {onMount} from "svelte"
  let rows = [];

  onMount(async () => {
    console.log("Mounting IntegrationsOverview");
    await fetch(`http://localhost:8000/v1/species`)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      rows = data;
      console.log("Mounted IntegrationsOverview ", rows);
    }).catch((error) => {
      console.error('Error:', error);
    });
  })
</script>

<div>
<table>
  <thead>
  <tr>
    <th>Scientific Name</th>
    <th>Preferred Common Name</th>
    <th>Habitat</th>
    <th>Genus</th>
    <th>family</th>
    <th>order</th>
    <th>Breeding Population</th>
    <th>Winter Visiting Population</th>
    <th>Image</th>
  </tr>
  </thead>
  <tbody>
  {#each rows as row}
    <tr>
      <td>
        { row.scientificName }
      </td>
      <td>
        { row.preferredCommonName }
      </td>
      <td>
        { #if (row.habitat) }
          { row.habitat }
        { /if }
      </td>
      <td>
        { row.genus }
      </td>
      <td>
        { row.family }
      </td>
      <td>
        { row.familyOrder }
      </td>
      <td>
        { #if (row.breedingPopulation) }
          { row.breedingPopulation }
        {/if}
      </td>
      <td>
        { #if (row.winterVisitorPopulation) }
          { row.winterVisitorPopulation }
        { /if }
      </td>
      <td>
        { #if row.id == 1 }
          <img src="images/{row.id}.png" alt="birdy" />
        { /if }
      </td>
    </tr>
  {/each}
  </tbody>
</table>
</div>

<style>
  table,
  th,
  td {
  }
  table {
    width: 100%;
    margin: 0 auto;
    display: block;
    overflow-x: auto;
    border-spacing: 0;
  }
  tbody {
    white-space: nowrap;
  }
  th,
  td {
    padding: 5px 10px;
  }
  td:nth-child(9) {
    text-align: center;
  }
  th {
    position: sticky;
    top: 0;
    background: #fff;
    vertical-align: bottom;
  }
  tr:nth-child(even) {
    background-color: #f5f5f5;
  }
</style>