import SplashScreen from './pages/SpashScreen.svelte'
import BigScreen from './pages/BigScreen.svelte'
import ClientScreen from './pages/ClientScreen.svelte'

const routes = [
    {
        name: '/',
        component: SplashScreen
    },
    {
        name: 'big',
        component: BigScreen
    },
    {
        name: 'client',
        component: ClientScreen
    },
];

export {routes}
