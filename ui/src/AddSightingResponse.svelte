<script>
  import dayjs from 'dayjs';

  export let species = '';
  export let location = '';
  export let date = '';
  export let success;

  function buildSuccessMessage() {
    if (species.length === 1) {
      return `Added your sighting of ${species} in ${location} on ${dayjs(date).format(
          'ddd MMM D, YYYY')}`
    } else {
      return `Added the following sightings in ${location} on ${dayjs(date).format(
          'ddd MMM D, YYYY')}:\n${species.join('\n')}`
    }
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