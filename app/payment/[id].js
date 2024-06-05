import {
  View,
  Text,
  ScrollView,
  SafeAreaView,
  TouchableOpacity,
  ActivityIndicator,
  TextInput,
  StyleSheet,
  Modal,
  Image
} from "react-native";
import { useEffect, useState } from "react";
import { Stack, useRouter, useGlobalSearchParams } from "expo-router";

import { COLORS, icons, images, SIZES, FONT, BASE_URL } from "../../constants";
import axios from "axios";

import {
  Topbanner,
  AddCustomer,
  AddPigmi,
  ScreenHeaderBtn,
} from "../../components";

import useAgent from "../../hook/useAgent";
import ViewTransaction from "../../components/home/ViewTransaction/ViewTransaction";
import AsyncStorage from "@react-native-async-storage/async-storage";
import useAccount from "../../hook/useAccount";
// import styles from "../../components/login/loginform.styles";

const Payment = () => {
  const router = useRouter();
  const params = useGlobalSearchParams();
  console.log(params.id, "Account Number page");

  const [amount , setAmount]=useState(0)
  const [isModalVisible,setIsModalVisible]=useState(false)
  const [loader, setLoader]= useState(false);

  const { data, isLoading, error, refetch } = useAccount(params.id);
  console.log(data,"Account data")

  const handleUpi = () => {
    if (!amount || amount < 1) {
      setIsModalVisible(false);
      alert("Please enter amount");
    } else {
      setIsModalVisible(true);
    }
  };

  const txnApiCall = async (txnData) => {
    const url = BASE_URL + "transaction/save";
    try {
      console.log(`Transaction req body ${JSON.stringify(txnData)}`);
      const response = await axios.post(url, txnData);
      console.log("txn data resp" + JSON.stringify(response.data.data));
      setLoader(false);
      const txnApiData = response?.data?.data?.transaction;
      const loanApiData = response?.data?.data?.loan;
      const allTransactions = response?.data?.data?.allTransactions;
      console.log(txnApiData,loanApiData,allTransactions);

      if (txnApiData !== null) {
        router.push(`/txn/${txnApiData?.id}`);
      } else if (loanApiData !== null) {
        console.log("iam in Loan")
        router.push(`/txn/${loanApiData?.id}`);
      } else if(allTransactions !== null) {
        console.log("iam in AllTransaction")
        router.push(`/txn/${allTransactions?.id}`);
      } else {
    
        console.log("No valid entity found in the response");
      }
    } catch (error) {
        console.log(error);
      console.log(JSON.stringify(error) + " while fetching transaction");
      setLoader(false);
      if (error.response && error.response.status === 409) {
        alert("Transaction Already completed for the day");
      }
    }
  };

  const handleTransaction = (mode) => {
    if (!amount || amount < 1) {
      setIsModalVisible(false);
      alert("Please enter amount");
    } else {
      setIsModalVisible(false);
      if (mode) {
        const txnData = {
          accountNumber: data?.accountNumber,

          accountType: data?.accountType,

          accountCode: data?.accountCode,

          agentId: data?.customer?.agentId,
          amount: parseInt(amount),
          customerId: data?.customer?.id,
          mode: mode,
          status: true,
        };
        console.log('I am here')
        setLoader(true);
        txnApiCall(txnData);
      }
    }
  };
 
  return loader ? (
    <ActivityIndicator size={SIZES.large} color={COLORS.primary} />
  ) : (
    <SafeAreaView style={{ flex: 1, backgroundColor: COLORS.gray2 }}>
      <Stack.Screen
        options={{
          headerStyle: { backgroundColor: COLORS.green },
          headerShadowVisible: false,
          headerTitle: "",
          headerLeft: () => (
            <ScreenHeaderBtn
              iconUrl={images.leftArrow}
              dimension="60%"
              handlePress={() => router.back()}
            />
          ),
          headerBackVisible: false,
        }}
      />
      <ScrollView showsVerticalScrollIndicator={false}>
        <View
          style={{
            backgroundColor: COLORS.lightWhite,
            width: "90%",
            alignItems: "start",
            alignSelf: "center",
            height: "80%",
            borderRadius: 30,
            paddingBottom: 80,
            marginTop:30,
          }}
        >
          <View style={{ marginLeft: 20, marginTop: 10 }}>
          <Text
              style={{
                color: COLORS.secondary,
                fontSize: 16,
                fontWeight: 600,
                paddingBottom: 10,
              }}
            >
              Customer Name: {data?.customer?.customerName}
            </Text>
            <Text
              style={{
                color: COLORS.secondary,
                fontSize: 16,
                fontWeight: 600,
                paddingBottom: 10,
              }}
            >
              Account Number : {data.accountNumber}
            </Text>
            <Text
              style={{
                color: COLORS.secondary,
                fontSize: 16,
                fontWeight: 600,
                paddingBottom: 10,
              }}
            >
              Account type : {data.accountType}
            </Text>
            <Text
              style={{
                color: COLORS.secondary,
                fontSize: 16,
                fontWeight: 600,
              }}
            >
              Rs. {data.balance}
            </Text>
          </View>
          <View style={styles.amountWrapper}>
            <TextInput
              placeholder="Enter amount"
              onChangeText={(text) => setAmount(text)}
              keyboardType="numeric"
              style={styles.amount}
            />
          </View>
          <View style={styles.payWrapper}>
              <View style={styles.upiContainer}>
                <Text style={styles.upiLabel}>UPI</Text>
                <TouchableOpacity
                  style={styles.upiWrapper}
                  onPress={handleUpi}
                //   disabled={isUpiButtonDisabled}
                >
                  <Text style={styles.upi}>Scanner</Text>
                </TouchableOpacity>
              </View>
              <View style={styles.upiContainer}>
                <Text style={styles.upiLabel}>Cash</Text>
                <TouchableOpacity
                  style={styles.upiWrapper}
                  onPress={() => handleTransaction("CASH")}
                //   disabled={isCashButtonDisabled}
                >
                  <Text style={styles.upi}>Paid</Text>
                </TouchableOpacity>
              </View>
            </View>


            <Modal
            visible={isModalVisible}
            animationType="slide"
            presentationStyle="pageSheet"
            onRequestClose={() => setIsModalVisible(false)}
          >
            <View style={styles.modalView}>
              <Text style={styles.header}>UPI Scanner</Text>
              <View style={styles.upiScannerWrapper}>
                <Image
                  source={images.upiScanner}
                  style={styles.upiImg}
                  resizeMode="contain"
                />
              </View>
              <Text style={styles.payableAmount}>Pay Rs. {amount} </Text>
              <TouchableOpacity
                style={styles.paymentButtonWrapper}
                onPress={() => handleTransaction("UPI")}
              >
                <Text style={styles.paymentButton}>Confirm Payment</Text>
              </TouchableOpacity>
            </View>
          </Modal>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

export default Payment;
const styles = StyleSheet.create({
  amountWrapper: {
    width: "90%",
    borderWidth: 1,
    borderRadius: 20,
    alignSelf: "center",
    // marginLeft:10,
    borderColor: COLORS.black,
    padding: 8,
    marginTop: 20,
  },
  amount: {
    fontFamily: FONT.semiBold,
    textTransform: "capitalize",
  },
  payWrapper:{
    // marginTop:SIZES.large,
    padding:SIZES.medium,
    flex:1,
    flexDirection:"row",
    alignItems:"center",
    justifyContent:"space-around"
}, 
  upiWrapper:{
    backgroundColor:COLORS.black,
    borderColor:COLORS.gray3,
    borderRadius:20,
    width:"100%",
    borderWidth:1,
    alignItems:"center",
    padding:SIZES.small, 
},

upienable:{
    backgroundColor:COLORS.green,
    borderColor:COLORS.gray3,
    borderRadius:20,
    borderWidth:1,
    alignItems:"center",
    padding:SIZES.small,
},


upi:{
    fontFamily:FONT.light,
    color:COLORS.white
},
upiContainer:{
    alignItems:"center",
},
upiLabel:{
    fontFamily:FONT.extraBold,
    fontSize:17,
    // paddingBottom:SIZES.xSmall
},
modalView:{
    padding:SIZES.large,
    flexDirection:"column",
    flex:1,
    alignItems:"center",
  
},
upiScannerWrapper:{
    width: "40%",
    height: "40%",
    justifyContent: "center",
    alignItems: "center",
    marginTop:30
},
upiImg:{
    // height:250,
    width:250,
},
header:{
    fontFamily:FONT.extraBold,
    fontSize:SIZES.xLarge,
    marginBottom:10
},
payableAmount:{
    fontFamily:FONT.bold,
    fontSize:SIZES.large,
    color:COLORS.green,
    marginTop:20
},
paymentButtonWrapper:{
    // marginVertical:SIZES.medium,
    backgroundColor:COLORS.green,
    // padding:SIZES.medium,
    borderRadius:15,
    padding:5,
    marginTop:10,
},
paymentButton:{
    color:COLORS.white,
    fontFamily:FONT.semiBold,
    padding:10,
    
},
});
