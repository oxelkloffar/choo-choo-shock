import LandingPage from './pages/LandingPage.svelte'
import TvGamePage from './pages/tv/GamePage.svelte'
import MobileGamePage from './pages/mobile/GamePage.svelte'
import MobileLobbyPage from './pages/mobile/LobbyPage.svelte'
import TvLobbyPage from './pages/tv/LobbyPage.svelte'

const routes = [
    {
        name: '/',
        component: LandingPage
    },
    {
        name: 'tv/game',
        component: TvGamePage
    },
    {
        name: 'mobile/game',
        component: MobileGamePage
    },
    {
        name: 'tv/lobby',
        component: TvLobbyPage
    },
    {
        name: 'mobile/lobby',
        component: MobileLobbyPage
    },
];

export {routes}
