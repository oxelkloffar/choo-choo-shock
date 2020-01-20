<script>
    import { navigateTo } from 'svelte-router-spa'

    function newGameClick() {
        navigateTo('big')
    }

    function joinGame(e) {
        e.preventDefault();
        navigateTo('client')
    }
</script>

<h1>Choo Choo Shock</h1>
<p>
    <button on:click={newGameClick}>New Game</button>
</p>
<form on:submit={joinGame}>
    <input type="tel" id="roomNumber" />
    <button>Join Game</button>
</form>
