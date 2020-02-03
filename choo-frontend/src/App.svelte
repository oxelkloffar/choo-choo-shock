<script>
    import {Router} from 'svelte-router-spa'
    import {routes} from './routes'
</script>
<svelte:head>
    <meta name="viewport" content="width=device-width, initial-scale=1, min-scale=1, max-scale=1">
</svelte:head>
<Router {routes}/>
