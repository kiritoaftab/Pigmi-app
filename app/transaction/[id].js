import axios from "axios";
import { View,Text, ActivityIndicator, ScrollView, SafeAreaView , useWindowDimensions, StyleSheet} from "react-native";
import { useGlobalSearchParams, Stack } from "expo-router";
import { useState, useEffect } from "react";
import {COLORS, SIZES, FONT, BASE_URL} from '../../constants'
import { TransactionHistory } from "../../components";
import { TouchableOpacity } from "react-native-gesture-handler";


const Transaction = () => {
    
    const params = useGlobalSearchParams();
    console.log(params.id, "recieved agent Id");
    const [isLoading,setIsLoading] = useState(false);

    const [transactions,setTransactions] = useState([]);

    const [days,setDays] = useState(1);


    const fetchTransactions = async(agentId,days) => {
        try {
            const res = await axios.get(`${BASE_URL}transaction/searchTransactionHistory/${agentId}/${days}`);
            console.log(res.data.data, "Transaction history resp for ",days);
            setTransactions(res.data.data);
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(()=> {
        if(params.id){
            fetchTransactions(params.id,days);
        }
    },[days])

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
            <Text style={{
                        fontSize:SIZES.xLarge,
                        fontFamily:FONT.bold,
                        padding:SIZES.medium
                        
                    }}>View Transactions</Text>
            <View style={{display:"flex",  flexDirection:"row", justifyContent:"space-evenly", marginTop:0}}>
                <TouchableOpacity style={days!=1 ? styles.chips : styles.clicked} onPress={()=> setDays(1)}>
                    <Text style={days==1 ? styles.textClicked : ``}>1 Day</Text>
                </TouchableOpacity>
                <TouchableOpacity
                    style={days!=3 ? styles.chips : styles.clicked}
                    onPress={()=> setDays(3)}
                >
                    <Text style={days==3 ? styles.textClicked : ``}>3 Days</Text>
                </TouchableOpacity>
                <TouchableOpacity
                    style={days!=7 ? styles.chips : styles.clicked}
                    onPress={()=>setDays(7)}
                >
                    <Text style={days==7 ? styles.textClicked : ``}>7 Days</Text>
                </TouchableOpacity>
            </View>
            {transactions ? (
                <TransactionHistory
                    transactions={transactions}
                />
            ):``}
            
        </SafeAreaView>
        )   
    )
}

export default Transaction;

const styles = StyleSheet.create({
    chips:{
        borderRadius:10, borderColor:COLORS.black, borderWidth:1,
        padding:SIZES.small, elevation:10, backgroundColor:COLORS.lightWhite
    },
    clicked:{
        borderRadius:10, borderColor:COLORS.black, borderWidth:1,
        padding:SIZES.small, elevation:10, backgroundColor:COLORS.black,
    },
    textClicked:{
        color:COLORS.lightWhite
    }
})