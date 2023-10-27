import { Text ,View, SafeAreaView, ScrollView, ActivityIndicator, RefreshControl } from 'react-native'
import {Stack, useRouter, useGlobalSearchParams} from 'expo-router';

import { COLORS, FONT, icons, images, SIZES } from "../../constants";
import {TopMoneybanner,AddMoney, ScreenHeaderBtn} from "../../components";

const AddAmount = () => {
    const router= useRouter();
    const params = useGlobalSearchParams();

    const user = {
        customerId : params.id,
        name : `Aftab Ahmed`,
        address: `Kankanagar, RT Nagar, Bangalore - 32`,
        phone : `7349185669`,
        balance: 4500,
    }

    return (
       <SafeAreaView
            style={{flex:1,  backgroundColor:COLORS.lightGreen, alignItems:"center"}}
       >
             <Stack.Screen
                options={{
                    headerStyle : {backgroundColor:COLORS.green},
                    headerShadowVisible:false,
                    headerTitle:'',
                    headerBackVisible:false,
                    headerLeft: () => (
                        <ScreenHeaderBtn
                        iconUrl={images.leftArrow}
                        dimension="60%"
                        handlePress={()=> router.back()}
                    />
                    )
                }}
            />

            <TopMoneybanner/>
            <AddMoney 
                user={user}
            />
       </SafeAreaView>
    )
}
export default AddAmount