const getWsUrl = () => {
    if (window.location.hostname.includes("localhost")) {
        console.log("using ws hostname: ", window.location.hostname + ":8080");
        return "ws://" + window.location.hostname + ":8080"
    }

    console.log("using ws hostname: api.", window.location.hostname);
    return "wss://api." + window.location.hostname
};

export {getWsUrl}
