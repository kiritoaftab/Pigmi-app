import { View, Text, ScrollView, SafeAreaView } from "react-native";
import { useState } from "react";
import { Stack, useRouter } from "expo-router";

import { COLORS, FONT, icons, images, SIZES } from "../constants";

import {Topbanner, AddCustomer,ScreenHeaderBtn, AddPigmi, Searchbanner, Userlist} from '../components'

const pigmi = () => {
    const router = useRouter();

    return (
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
                <Userlist/>
        </SafeAreaView>
    )
}

export default pigmi;