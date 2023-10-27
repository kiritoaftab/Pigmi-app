import {View, Text, Image ,Button, TouchableOpacity, TextInput, KeyboardAvoidingView, Platform,
TouchableWithoutFeedback,Modal,Keyboard} from "react-native";
import DateTimePicker from "@react-native-community/datetimepicker";
import { useRouter } from "expo-router";

import styles from "./addmoney.styles";
import {images} from "../../../constants";
import { useState } from "react";

import { formatIndianRupee } from "../../../utils";

const AddMoney = ({user}) => {
    const router = useRouter();

    const [date,setDate] = useState(new Date());
    const [show,setShow] = useState(false);
    const [txnId, setTxnId] = useState(null);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [amount,setAmount] = useState(null);

    const onChange = (e,selectedDate) => {
        setDate(selectedDate);
        setShow(false);
    }

    const handleTransaction = () => {
        setIsModalVisible(false);
        const txnIdFromApi = 'txn101'; //After api call with server
        router.push(`/txn/${txnIdFromApi}`)
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
        <KeyboardAvoidingView 
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
        style={{
            flex:1,
        }}
    >
        <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
        <View style={styles.container}>
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
                    <View >
                        <Text style={styles.dateHeading}>Balance</Text>
                        <View style={styles.balanceWrapper} >
                                 <Text style={styles.balance}> Rs. {formatIndianRupee(user.balance)} </Text>
                        </View>
                        
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
                <View style={styles.amtWrapper}>
                    <Text style={styles.dateHeading}>Amount</Text>
                            <View style={styles.amountWrapper}>
                                <TextInput 
                                    placeholder="Enter Pigmy amount"
                                    keyboardType="number-pad"
                                    style={styles.amount}
                                    onChangeText={(text)=> setAmount(Number(text))}
                                />
                            </View>
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
                        <TouchableOpacity style={styles.upiWrapper} onPress={handleTransaction}>
                            <Text style={styles.upi}>Paid</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </View>
                {/* modal */}
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

                        <TouchableOpacity style={styles.paymentButtonWrapper} onPress={handleTransaction}>
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