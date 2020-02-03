<script>
    import {onMount} from 'svelte'
    import {fade} from 'svelte/transition';
    import {getWsUrl} from "../hostname";

    let status = "";
    let ws = null;

    let clients = {};

    let plankAngle = 'A';
    let playerA = null;
    let playerB = null;

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

                    if (!playerA) {
                        playerA = message.id;
                    }
                    else if (!playerB) {
                        playerB = message.id;
                    }

                    break;
            }
        };

        ws.onopen = () => {
            status = "connected";
        };

        function disconnect() {
            if (ws != null) {
                ws.close()
            }
            console.log("Disconnected")
        }
    })

    //let visible = false;

    let speed = 100;
    setInterval(() => {
        if (speed > 0) {
            speed -= Math.max(
                Math.pow(speed, 2) * 0.0012,
                2
            );
            if (speed < 0) {
                speed = 0;
            }
        }
    }, 1000);

    function animateTap(id) {
        switch (id) {
            case playerA:
                if (plankAngle !== 'A') {
                    speed += 1;
                }
                plankAngle = 'A';
                break;

            case playerB:
                if (plankAngle !== 'B') {
                    speed += 1;
                }
                plankAngle = 'B';
                break;
        }
    }
</script>

<style>
section {
    position: relative;
    width: 100%;
    height: 100%;
}
section.tappedA {
    transform: rotate(-25deg);
}
section.tappedB {
    transform: rotate(25deg);
}

i {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    width: 90vw;
    height: 5vw;
    background-color: darkgrey;
}
div {
    position: absolute;
    top: 50%;
    transform: translate(0,-50%);
    margin-top: -5vw;
    padding: 1vw;
    font-size: 5vw;
    background-color: aquamarine;
}
.A {
    left: 5vw;
}
.B {
    right: 5vw;
}
</style>

<h1>Big cool TV</h1>
<h2>{Math.round(speed)} km/h</h2>

<section class={plankAngle == 'A' ? 'tappedA' : plankAngle == 'B' ? 'tappedB' : ''}>
    <i></i>
    <div class="A">A</div>
    <div class="B">B</div>
</section>

<p>{status}</p>
