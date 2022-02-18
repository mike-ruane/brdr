<script>
  import dayjs from 'dayjs';

  export let species = '';
  export let location = '';
  export let date = '';
  export let success;

  function buildSuccessMessage() {
    return `Added your sighting of ${species} in ${location} on ${dayjs(date).format('ddd MMM D, YYYY')}`
  }

  function buildDuplicateMessage() {
    return `You've already tracked a sighting of ${species} in ${location}!`
  }
</script>

{#if success}
  <p><span>🪶 🪶 🪶</span><span> {buildSuccessMessage()} </span></p>
{:else }
  <p><span>❌ ❌ ❌</span><span> {buildDuplicateMessage()} </span></p>
{/if}
<style>
  p {
    display: inline-grid;
    margin: auto;
    text-align: center;
  }
</style>