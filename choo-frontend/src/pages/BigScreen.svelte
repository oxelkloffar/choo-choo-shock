<script>
    import {onMount} from 'svelte'
    import {fade} from 'svelte/transition';
    import {getWsUrl} from "../hostname";

    let status = "";
    let ws = null;

    let clients = {};

    onMount(() => {
        ws = new WebSocket(getWsUrl() + '/ws');
        ws.onmessage = function (data) {
            const message = JSON.parse(data.data);
            console.log("received: ", message);
            switch (message.type) {
                case "tap":
                    animateTap(message.id);
                    break;
                case "connect":
                    clients[message.id] = {isTapped: false}
                    break;
            }
        };

        ws.onopen = () => {
            // ws.send(JSON.stringify({
            //     type: "connect",
            // }));
            status = "connected";
        };

        // setConnected(true)

        function disconnect() {
            if (ws != null) {
                ws.close()
            }
            console.log("Disconnected")
        }

        // function sendName() {
        //     ws.send($("#name").val())
        // }

        function showGreeting(message) {
            console.log("received: " + message)
        }
    })

    let visible = false;

    function animateTap(id) {
        clients[id].isTapped = true;
        setTimeout(() => clients[id].isTapped = false, 1000)
        console.log("tap from ", id)
    }
</script>

<h1>Big cool TV</h1>
<ul>
    {#each Object.keys(clients) as id}
        <li>
            {id}
            {#if clients[id].isTapped}👍{/if}
        </li>
    {/each}
</ul>
<!--{#if visible}-->
<!--    <p in:fade out:fade>-->
<!--        Flies in, fades out-->
<!--    </p>-->
<!--{/if}-->
<p>{status}</p>
