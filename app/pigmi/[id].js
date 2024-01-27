import { View, Text, ScrollView, SafeAreaView, ActivityIndicator } from "react-native";
import { useEffect, useState } from "react";
import { Stack, useRouter, useGlobalSearchParams } from "expo-router";

import { BASE_URL, COLORS, FONT, icons, images, SIZES } from "../../constants";

import {Topbanner, AddCustomer,ScreenHeaderBtn, AddPigmi, Searchbanner, Userlist} from '../../components'

import useCustomerList from "../../hook/useCustomerList";
import axios from "axios";

const pigmi = (agentId) => {
    const router = useRouter();
    const params = useGlobalSearchParams();
    const [searchQuery,setSearchQuery] = useState("");
    const [performSearch,setPerformSearch] = useState(false);
    console.log(params);

    const { data, isLoading, error, refetch,setData } = useCustomerList(params.id);

    const callSearchApi = async(searchText) => {
        if(searchText){
            console.log(`Searching for ${searchText},${params.id}`);
            try {
                const res = await axios.get(`${BASE_URL}customer/verifyAgentIdAndCustomerName`,{
                    params:{
                        agentId:params.id,
                        query:searchText
                    }
                })
                
                console.log(res); 
                setData(res?.data?.data);
            } catch (error) {
                alert('No customer found')
            }
            
        }
        
    }

    

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
                    headerTitle:'Add Daily Deposit',
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

                <Searchbanner
                    setSearchQuery= {setSearchQuery}
                    setPerformSearch={setPerformSearch}
                    callSearchApi={callSearchApi}
                />
                <Userlist  
                    customerList={data}
                />
        </SafeAreaView>
    )
}

export default pigmi;