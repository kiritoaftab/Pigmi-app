import { View, Text, ScrollView, SafeAreaView, TouchableOpacity } from "react-native";
import { useState } from "react";
import { Stack, useRouter } from "expo-router";

import { COLORS, icons, images, SIZES } from "../constants";

import {Topbanner, AddCustomer, AddPigmi} from '../components'


const Home = () => {
    const router = useRouter();

    return (
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
                    flex:1,
                    padding:SIZES.medium,
                    backgroundColor:COLORS.green,
                    
                    borderBottomLeftRadius:30,
                    borderBottomRightRadius:30,   
                }}> 
                    <Topbanner/>
                    
                </View>
                <View style={{
                    marginTop:-36,
                    backgroundColor:COLORS.lightWhite,
                    width:"80%",
                    alignItems:"center",
                    alignSelf:"center",
                    height:"69%",
                    borderRadius:30,
                }}>
                    <TouchableOpacity onPress={() => router.push('/home')}>
                        <Text>Testing</Text>
                    </TouchableOpacity>
                    <AddPigmi 
                        handleClick={()=> router.push('/pigmi')}
                    />
                </View>
                
            </ScrollView>
        </SafeAreaView>
    )
}

export default Home;