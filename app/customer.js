import { View, Text, ScrollView, SafeAreaView } from "react-native";
import { useState } from "react";
import { Stack, useRouter } from "expo-router";

import { COLORS, FONT, icons, images, SIZES } from "../constants";

import {Topbanner, AddCustomer, AddPigmi , ScreenHeaderBtn, CustomerForm} from '../components'

const customer = () => {
    const router = useRouter();

    return (
        <SafeAreaView style={{flex:1,  backgroundColor:COLORS.lightWhite}} >
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
            <ScrollView showsVerticalScrollIndicator={false}>
            <View style={{ 
                    flex:1,
                    padding:SIZES.medium,
                    backgroundColor:COLORS.green, 
                }}> 
                    <Topbanner/>    
                </View>

                <View style={{
                    marginTop:-56,
                    zIndex:1,
                    backgroundColor:COLORS.lightBlue,
                    height:"8%",
                    width:"60%",
                    alignItems:"center",
                    alignSelf:"center",
                    borderRadius:20,
                    
                }}>
                    <Text style={{
                        fontSize:SIZES.large,
                        fontWeight:"800",
                        paddingTop:SIZES.xSmall,
                        paddingBottom:SIZES.small,
                        fontFamily:FONT.extraBold
                    }}>Add Customer</Text>
                </View>
                <View style={{
                    marginTop:-40,
                    backgroundColor:COLORS.lightWhite,
                    borderTopRightRadius:40,
                    borderTopLeftRadius:40,
                }}>
                    <CustomerForm/>
                </View>
            </ScrollView>
            

        </SafeAreaView>
    )
}

export default customer;