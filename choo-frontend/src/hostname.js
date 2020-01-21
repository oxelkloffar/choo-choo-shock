const getWsUrl = () => {
    if (window.location.hostname.includes("richodemus.com")) {
        console.log("using ws hostname: api.choo.richodemus.com");
        return "wss://api.choo.richodemus.com"
    }
    console.log("using ws hostname: ", window.location.hostname + ":8080");
    return "ws://" + window.location.hostname + ":8080"
};

export {getWsUrl}
