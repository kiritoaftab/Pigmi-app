import axios from "axios";
import { View,Text, ActivityIndicator, ScrollView, SafeAreaView } from "react-native";
import { useGlobalSearchParams, Stack } from "expo-router";
import { useState } from "react";
import {COLORS, SIZES, FONT} from '../../constants'

const transaction = () => {
    
    const params = useGlobalSearchParams();
    console.log(params.id, "recieved agent Id");
    const [isLoading,setIsLoading] = useState(false);

    return (

        isLoading ? (
            <ActivityIndicator size={SIZES.large} color={COLORS.primary}/>
        ):(
            <SafeAreaView style={{flex:1, backgroundColor:COLORS.gray2}}>
            <Stack.Screen
                options={{
                    headerStyle : {backgroundColor:COLORS.green},
                    headerShadowVisible:false,
                    headerTitle:''
                }}
            />
            <ScrollView 
                showsVerticalScrollIndicator={false}
            >
               
                <View style={{
                    padding:SIZES.medium
                }}>
                    <Text style={{
                        fontSize:SIZES.medium,
                    }}>View Transactions</Text>
                </View>
                
            </ScrollView>
        </SafeAreaView>
        )

        
    )
}

export default transaction;