import { Text ,View, SafeAreaView, ScrollView, ActivityIndicator, RefreshControl } from 'react-native'
import {Stack, useRouter, useGlobalSearchParams} from 'expo-router';

import { COLORS, FONT, icons, images, SIZES } from "../../constants";
import {TopMoneybanner,AddMoney, ScreenHeaderBtn} from "../../components";

import useCustomer from '../../hook/useCustomer';

const AddAmount = () => {
    const router= useRouter();
    const params = useGlobalSearchParams();

    const { data, isLoading, error, refetch } = useCustomer(params.id);
    console.log(data)
    const user = {
        customerId : params.id,
        name : data?.customerName,
        address: data?.address,
        phone : data?.phone,
        balance: 0,
        customerAccount: data?.customerAccount,
        agentId: data?.agentId
    }

   return (
            isLoading? (
                <ActivityIndicator size={SIZES.large} color={COLORS.primary}/>
            ) : error? (
                <Text>{error}</Text>
            ) :
    

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
                <ScrollView showsVerticalScrollIndicator={false} style={{width:"100%", height:"100%"}}>
                    <View style={{alignItems:"center", }}>
                    <TopMoneybanner/>
                            <AddMoney  
                                user={user}
                            />
                    </View>
                
                </ScrollView>
               
            </SafeAreaView>
        
   )
    
        
    
}
export default AddAmount