import { View, Text, ScrollView, SafeAreaView, TouchableOpacity, ActivityIndicator } from "react-native";
import { useEffect, useState } from "react";
import { Stack, useRouter, useGlobalSearchParams } from "expo-router";

import { COLORS, icons, images, SIZES } from "../../constants";

import {Topbanner, AddCustomer, AddPigmi} from '../../components'

import useAgent from "../../hook/useAgent";
import ViewTransaction from "../../components/home/ViewTransaction/ViewTransaction";
import AsyncStorage from "@react-native-async-storage/async-storage";

const Start = () => {
    const router = useRouter();
    const params = useGlobalSearchParams();

    const { data, isLoading, error, refetch } = useAgent(params.id);
    
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
                    flex:1,
                    padding:SIZES.medium,
                    backgroundColor:COLORS.green,
                    borderBottomLeftRadius:30,
                    borderBottomRightRadius:30,   
                }}> 
                    <Topbanner 
                        agent={data}
                    />
                    
                </View>
                <View style={{
                    marginTop:-46,
                    backgroundColor:COLORS.lightWhite,
                    width:"80%",
                    alignItems:"center",
                    alignSelf:"center",
                    height:"80%",
                    borderRadius:30,
                    paddingBottom:100
                }}>
                    
                    <AddPigmi 
                        agent={data}
                        handleClick={()=> router.push(`/pigmi/${params.id}`)}
                    />

                    <ViewTransaction
                        agent={data}
                        handleClick={()=> router.push(`/transaction/${params.id}`)}
                    />
                    
                </View>
                
            </ScrollView>
        </SafeAreaView>
        )

        
    )
}

export default Start;