import { Text ,View, SafeAreaView, ScrollView, ActivityIndicator, RefreshControl } from 'react-native'
import {Stack, useRouter, useGlobalSearchParams, router} from 'expo-router';

import { COLORS, FONT, icons, images, SIZES } from "../../constants";
import {TopMoneybanner,AddMoney, ScreenHeaderBtn, TopTxnBanner,TxnDetails} from "../../components";

import useTransaction from '../../hook/useTransaction';

const Transaction = () => {
    const params = useGlobalSearchParams();
    console.log(params);

    const { data, isLoading, error, s3PdfUrl, refetch } = useTransaction(params?.id);
    console.log("DATA" + JSON.stringify(data));
    const user ={
        name:"Aftab Ahmed",
        customerId: "C101",
        balance: 4600,
    }

    const txn ={
        txnId:params.id,
        agentName:"Vinod Khanna",
        amount: 100,
        date: "23-10-2023",
        customerId:"C101"
    }

    return(
        isLoading? (
            <ActivityIndicator size={SIZES.large} color={COLORS.primary}/>
        ) : error ? (
            <Text>{JSON.stringify(error)}</Text>
            //alert('There is error')
        ) :
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
                    data={data}
                    user={user}
                    txn={txn}
                    s3PdfUrl={s3PdfUrl}
                />
       </SafeAreaView>
    )
}
export default Transaction;