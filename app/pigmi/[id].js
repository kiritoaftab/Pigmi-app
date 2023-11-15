import { View, Text, ScrollView, SafeAreaView, ActivityIndicator } from "react-native";
import { useState } from "react";
import { Stack, useRouter, useGlobalSearchParams } from "expo-router";

import { COLORS, FONT, icons, images, SIZES } from "../../constants";

import {Topbanner, AddCustomer,ScreenHeaderBtn, AddPigmi, Searchbanner, Userlist} from '../../components'

import useCustomerList from "../../hook/useCustomerList";

const pigmi = () => {
    const router = useRouter();
    const params = useGlobalSearchParams();
    console.log(params);

    const { data, isLoading, error, refetch } = useCustomerList(params.id);
    
    return (
        isLoading ? (
            <ActivityIndicator size={SIZES.large} color={COLORS.primary}/>
        ) : error ? (
            <Text>{error}</Text>
        ) :
        <SafeAreaView style={{
            flex:1,
            backgroundColor:COLORS.lightBlue,
            alignItems:"center"
        }}>
             <Stack.Screen
                options={{
                    headerStyle : {backgroundColor:COLORS.green},
                    headerShadowVisible:false,
                    headerTitle:'Add Pigmy',
                    headerTitleStyle: {
                        fontFamily:FONT.semiBold,
                        color:COLORS.lightWhite,
                        fontSize:20,
                    },
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

                <Searchbanner/>
                <Userlist  
                    customerList={data}
                />
        </SafeAreaView>
    )
}

export default pigmi;