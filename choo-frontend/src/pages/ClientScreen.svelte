<script>
    import {onMount} from 'svelte'
    import uuidv4 from 'uuid/v4'
    import {getWsUrl} from "../hostname";


    let ws = null;
    let status = null;
    let uuid = null;
    onMount(() => {
        uuid = uuidv4();
        ws = new WebSocket(getWsUrl() + '/ws');
        ws.onmessage = function (data) {
            showGreeting(data.data)
        };

        ws.onopen = () => {
            ws.send(JSON.stringify({
                type: "connect",
                id: uuid,
            }));
            status = "connected";
        };

        function disconnect() {
            if (ws != null) {
                ws.close()
            }
            console.log("Disconnected")
        }

        function showGreeting(message) {
            console.log("received: " + message)
        }
    })

    function tap(e) {
        e.preventDefault();
        e.stopPropagation();

        if (!ws || ws.readyState !== WebSocket.OPEN) {
            status = "no ws"
        }

        ws.send(JSON.stringify({
            type: "tap",
            id: uuid,
        }));
    }

    function stopDrag(e) {
        e.preventDefault();
        e.stopPropagation();
    }
</script>

<style>
button {
    display: block;
    width: 90vw;
    height: 20vh;
}
</style>

<h1>Tiny little mobile phone</h1>
<button on:click={tap} on:touchmove={stopDrag}>Pump it!</button>
<p>{status}</p>
