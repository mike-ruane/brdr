<script>
  import dayjs from 'dayjs';

  export let species = '';
  export let location = '';
  export let date = '';

  function buildMessage() {
    return `Added your sighting of ${species} in ${location} on ${dayjs(date).format('ddd MMM D, YYYY')}`
  }

</script>

<p><span>🪶 🪶 🪶</span><span> {buildMessage()} </span></p>
<style>
  p {
    display: inline-grid;
    margin: auto;
    text-align: center;
  }
</style>