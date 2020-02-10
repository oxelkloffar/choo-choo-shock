<script>
    import {navigateTo} from 'svelte-router-spa'

    function joinAsTv(event) {
        event.preventDefault();
        navigateTo('tv/lobby')
    }

    function joinAsMobile(event) {
        event.preventDefault();
        navigateTo('mobile/lobby')
    }

    let roomNumber = null;
    let name = null;
</script>

<style>
    button {
        color: hotpink;
        background-color: turquoise;
    }
</style>

<p>Land</p>
<div>
    <button on:click={joinAsTv}>Click me on the TV</button>
</div>
<div>
    <input type="text" placeholder="who dis?" bind:value={name}/>
    <input type="tel" placeholder="where to?" bind:value={roomNumber}/>
    <button on:click={joinAsMobile}>Click me on the phone</button>
</div>
