import { Text ,View, SafeAreaView, ScrollView, ActivityIndicator, RefreshControl } from 'react-native'
import {Stack, useRouter, useGlobalSearchParams} from 'expo-router';

import { COLORS, FONT, icons, images, SIZES } from "../../constants";
import {TopMoneybanner,AddMoney, ScreenHeaderBtn, TopTxnBanner,TxnDetails} from "../../components";

const Transaction = () => {
    const user ={
        name:"Aftab Ahmed",
        customerId: "C101",
        balance: 4600,
    }

    const txn ={
        txnId:"TXN101",
        agentName:"Vinod Khanna",
        amount: 100,
        date: "23-10-2023",
        customerId:"C101"
    }

    return(
        <SafeAreaView
            style={{flex:1,  backgroundColor:COLORS.lightGreen, alignItems:"center"}}
       >
                <Stack.Screen
                    options={{
                        headerStyle : {backgroundColor:COLORS.green},
                        headerShadowVisible:false,
                        headerTitle:'',
                        headerBackVisible: false,
                    }}
                />
                <TopTxnBanner/>
                <TxnDetails 
                    user={user}
                    txn={txn}
                />
       </SafeAreaView>
    )
}
export default Transaction;