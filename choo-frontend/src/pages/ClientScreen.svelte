<script>
    import {onMount} from 'svelte'
    import uuidv4 from 'uuid/v4'


    let ws = null;
    let status = null;
    let uuid = null;
    onMount(() => {
        uuid = uuidv4();
        ws = new WebSocket('ws://' + window.location.hostname + ':8080/ws');
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

    function tap() {
        if (!ws || ws.readyState !== WebSocket.OPEN) {
            status = "no ws"
        }

        ws.send(JSON.stringify({
            type: "tap",
            id: uuid,
        }));
    }
</script>
<h1>Tiny little mobile phone</h1>
<button on:click={tap}>Let me tap it!</button>
<p>{status}</p>
