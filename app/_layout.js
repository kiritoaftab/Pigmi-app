import { Stack } from "expo-router";

import { useCallback } from "react";
import { useFonts } from "expo-font";
import * as SplashScreen from 'expo-splash-screen'

SplashScreen.preventAutoHideAsync();

const Layout = () => {

    const [fontsLoaded] = useFonts({
        OpenSansBold: require('../assets/fonts/OpenSans-Bold.ttf'),
        OpenSansExtraBold: require('../assets/fonts/OpenSans-ExtraBold.ttf'),
        OpenSansLight: require('../assets/fonts/OpenSans-Light.ttf'),
        OpenSansMedium: require('../assets/fonts/OpenSans-Medium.ttf'),
        OpenSansRegular: require('../assets/fonts/OpenSans-Regular.ttf'),
        OpenSansSemiBold: require('../assets/fonts/OpenSans-SemiBold.ttf')
    })

    const onLayoutRootView = useCallback(async () => {
        if(fontsLoaded){
            await SplashScreen.hideAsync();
        }
    },[fontsLoaded])

    if(!fontsLoaded) return null;
    return <Stack onLayout={onLayoutRootView}/>
}

export default Layout;