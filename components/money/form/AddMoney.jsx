import {View, Text, Image ,Button, TouchableOpacity, TextInput, KeyboardAvoidingView, Platform,
TouchableWithoutFeedback,Modal,Keyboard} from "react-native";
import DateTimePicker from "@react-native-community/datetimepicker";
import { useRouter } from "expo-router";
import axios from "axios";

import DropDownPicker from 'react-native-dropdown-picker';

import styles from "./addmoney.styles";
import {BASE_URL, images} from "../../../constants";
import { useEffect, useState } from "react";



import { formatIndianRupee } from "../../../utils";
import { ActivityIndicator } from "react-native-paper";

const AddMoney = ({user}) => {
    const router = useRouter();

    const [date,setDate] = useState(new Date());
    const [show,setShow] = useState(false);
    const [txnId, setTxnId] = useState(null);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [amount,setAmount] = useState(null);
    const [isLoading,setIsLoading] = useState(false);
    // const [open, setOpen] = useState(false);
    // const [value, setValue] = useState(null);
    // const [items,setItems] = useState([])

    // useEffect( () => {
    //     user?.customerAccount?.map((acc,index)=> {
    //         setItems( items => ([...items,{label: acc.accountType, value: acc.accountType}]))
    //     })
    // },[])
    

    const onChange = (e,selectedDate) => {
        setDate(selectedDate);
        setShow(false);
    }
    

    const txnApiCall = async(txnData) => {
        const url = BASE_URL + "/transaction/save";
        try {
            const response = await axios.post(url,txnData);
            console.log(response.data.data);
            // setData(response.data.data);
            // setIsLoading(false);
            // console.log(data);
            // setIsModalVisible(true);
            router.push(`/txn/${response?.data?.data?.transaction?.id}`)
          } catch (error) {
            console.log(error);
            // setError(error);
            // setIsModalVisible(false);
            alert("There is an error");
          } finally {
            // setIsLoading(false);
          }
    }

    const handleTransaction = (mode) => {
        setIsModalVisible(false);
        if(mode){
            const txnData = {
                "accountNumber": Array.isArray(user?.customerAccount) && user.customerAccount?.length>0 ?  user.customerAccount[0]?.accountNumber : '',
                "accountType": Array.isArray(user?.customerAccount) && user.customerAccount?.length>0 ?  user.customerAccount[0]?.accountType : '',
                "agentId": user?.agentId,
                "amount": parseInt(amount),
                "customerId": user?.customerId,
                "mode": mode,
                "status": true
              }
              console.log(txnData);
              txnApiCall(txnData);
        }
        
        // const txnIdFromApi = 'txn101'; //After api call with server
        // router.push(`/txn/${txnIdFromApi}`)
    }

    const handleUpi = () => {
       
        if(!amount || amount<1){
            setIsModalVisible(false);
            alert('Please enter amount');
        }else{
            setIsModalVisible(true);
        }
    }

    return(
        isLoading ? (
            <ActivityIndicator size={SIZES.large} color={COLORS.primary}/>
        ) :
        <KeyboardAvoidingView 
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
        style={{
            flex:1,
        }}
    >
        <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
            
        <View style={styles.container}>
            {/* <Text>{JSON.stringify(user)}</Text> */}
            <View style={styles.customerWrapper}>
                <View style={styles.imgWrapper}>
                    <Image
                        source={images.profile}
                        resizeMode="contain"
                        style={styles.img} 
                    />
                </View>
                <View style={styles.contentWrapper}>
                    <Text style={styles.nameText}>{user.name}</Text>
                    <Text style={styles.customerId}>Customer Id: {user.customerId}</Text>
                    <Text style={styles.address}>{user.address}</Text>
                    <Text style={styles.phone}>{user.phone}</Text>
                </View>
            </View>
            <View style={styles.formWrapper}>
                <View style={styles.topRow}>
                    <View style={styles.dateWrapper}>
                        <Text style={styles.dateHeading}>Date</Text>
                        <TouchableOpacity onPress={() => setShow(true)} style={styles.dateInputWrapper}>
                                <Text style={styles.dateInput}>{`${JSON.stringify(date.getDate())}-${JSON.stringify(date.getMonth()+1)}-${JSON.stringify(date.getFullYear())}`}</Text>
                        </TouchableOpacity>
                    </View>
            
                    {
                        show && (
                            <DateTimePicker 
                                value={date}
                                onChange={onChange}
                                mode="date"
                                is24Hour={true}
                            />
                        )
                    }
                    
                </View> 
               
                <View style={styles.accountNumberWrap}>
                    <Text style={styles.accNumHead}>Account Details</Text>
                    {                       
                        Array.isArray(user.customerAccount) && user.customerAccount?.length>0 ? <Text style={styles.accountNumber}>{user.customerAccount[0]?.accountNumber}</Text> : ``
                    }
                    {                       
                        Array.isArray(user.customerAccount)&& user.customerAccount?.length>0 ? <Text style={styles.accountType}>{user.customerAccount[0]?.accountType}</Text> : ``
                    }
                    {                       
                        Array.isArray(user.customerAccount)&& user.customerAccount?.length>0 ? <Text style={styles.balance}>Rs. {user.customerAccount[0]?.balance}</Text> : ``
                    }
                </View>
                
                <View style={styles.amountWrapper}>
                    <TextInput 
                        placeholder="Enter amount"
                        onChangeText={(text)=> setAmount(text)}
                        keyboardType="numeric"
                        style={styles.amount}
                    />
                </View>
                

                
                <View style={styles.payWrapper}>
                    <View style={styles.upiContainer}>
                        <Text style={styles.upiLabel}>UPI</Text>
                        <TouchableOpacity style={styles.upiWrapper} onPress={handleUpi} >
                            <Text style={styles.upi}>Scanner</Text>
                        </TouchableOpacity>
                    </View>
                        
                    <View style={styles.upiContainer}>
                        <Text style={styles.upiLabel}>Cash</Text>
                        <TouchableOpacity style={styles.upiWrapper} onPress={() => handleTransaction("CASH")}>
                            <Text style={styles.upi}>Paid</Text> 
                        </TouchableOpacity>
                    </View>
                </View> 
            </View>
               
                <Modal 
                    visible={isModalVisible}
                    animationType="slide"
                    presentationStyle="pageSheet"
                    onRequestClose={()=> setIsModalVisible(false)}
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

                        <TouchableOpacity style={styles.paymentButtonWrapper} onPress={() => handleTransaction("UPI")}>
                            <Text style={styles.paymentButton}>Confirm Payment</Text>
                        </TouchableOpacity>
                    </View>
                </Modal>
        </View>
        </TouchableWithoutFeedback>
        </KeyboardAvoidingView>
    )
}

export default AddMoney;