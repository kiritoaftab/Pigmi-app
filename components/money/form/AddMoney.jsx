import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  Linking,
  TouchableOpacity,
  TextInput,
  KeyboardAvoidingView,
  Platform,
  TouchableWithoutFeedback,
  Modal,
  Keyboard,
  ScrollView,
  FlatList,
  Image,
} from "react-native";
import DateTimePicker from "@react-native-community/datetimepicker";
import { useRouter } from "expo-router";
import axios from "axios";
import styles from "./addmoney.styles";
import { BASE_URL, images } from "../../../constants";
import { ActivityIndicator } from "react-native-paper";


const AddMoney = ({ user }) => {
  const router = useRouter();

  const [agentId, setAgentId] = useState(null);
  const [date, setDate] = useState(new Date());
  const [show, setShow] = useState(false);
  const [txnId, setTxnId] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [amount, setAmount] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [selectedAccountType, setSelectedAccountType] = useState("SAVINGS");
  const [selectedAccount, setSelectedAccount] = useState(null);
  const [isUpiButtonDisabled, setIsUpiButtonDisabled] = useState(false);
  const [isCashButtonDisabled, setIsCashButtonDisabled] = useState(false);

  const handleAccountTypeChange = (type) => {
    setSelectedAccount(
      user.customerAccount.find((acc) => acc.accountType === type) || null
    );
    setSelectedAccountType(type);
  };

  // useEffect(() => {
  //   // Function to retrieve agentId from cookies when the component mounts
  //   const getAgentIdFromCookies = async () => {
  //     try {
  //       const cookies = await CookieManager.get(BASE_URL);

  //       // Extract agentId from cookies
  //       const agentIdCookie = cookies.agentId;
  //       if (agentIdCookie) {
  //         setAgentId(agentIdCookie);
  //       }
  //     } catch (error) {
  //       console.log(error);
  //       // Handle error if needed
  //     }
  //   };

  //   getAgentIdFromCookies(); // Call the function when the component mounts
  // }, []);

  const onChange = (e, selectedDate) => {
    setDate(selectedDate);
    setShow(false);
  };

  // const beforeCallApi= async (txnData)=>{

  //   // console.log(`Transaction req body ${JSON.stringify(txnData.customerId, txnData.agentId, txnData.accountNumber)}`)
  //   const url = BASE_URL + `transaction/fetchTransactionStatus/${txnData.customerId}/${txnData.agentId}/${txnData.accountNumber}`;
  //   try {
  //     const response = await axios.post(url)
  //     console.log(txnData.customerId, txnData.agentId, txnData.accountNumber)
  //     console.log(response)

  //   } catch (error) {

  //   }
  // }

  const txnApiCall = async (txnData) => {
    const url = BASE_URL + "transaction/save";
    try {
      console.log(`Transaction req body ${JSON.stringify(txnData)}`);
      const response = await axios.post(url, txnData);
      console.log("txn data resp" + JSON.stringify(response.data.data));
      setIsLoading(false);
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
      console.log(JSON.stringify(error) + " while fetching transaction");
      setIsLoading(false);
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
          accountNumber: selectedAccount?.accountNumber,

          accountType: selectedAccount?.accountType,

          accountCode: selectedAccount?.accountCode,

          agentId: user?.agentId,
          amount: parseInt(amount),
          customerId: user?.customerId,
          mode: mode,
          status: true,
        };
        setIsLoading(true);
        txnApiCall(txnData);
      }
    }
  };

  const checkTransaction = () => {
    console.log(
      selectedAccount?.accountNumber,
      user?.customerId,
      user?.agentId
    );
    axios
      .get(
        `${BASE_URL}transaction/fetchTransactionStatus/${user?.customerId}/${user?.agentId}/${selectedAccount?.accountNumber}`
      )
      .then((response) => {
        console.log(response.data);
        if (response.data === false) {
          setIsCashButtonDisabled(true);
          setIsUpiButtonDisabled(true);
          alert("Transaction is Completed For The Day For This Account");
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    checkTransaction();
  }, [selectedAccount]);

  const handlePhoneClick = (phoneNumber) => {
    const url = `tel:${phoneNumber}`;
    Linking.canOpenURL(url)
      .then((supported) => {
        if (supported) {
          return Linking.openURL(url);
        } else {
          console.log("Phone dialer not supported");
        }
      })
      .catch((error) => console.error("An error occurred", error));
  };

  const handleUpi = () => {
    if (selectedAccount === null) {
      setIsModalVisible(false);
      alert("Please select Account");
    } else if (!amount || amount < 1) {
      setIsModalVisible(false);
      alert("Please enter amount");
    } else {
      setIsModalVisible(true);
    }
  };

  return isLoading ? (
    <ActivityIndicator size="large" color="primary" />
  ) : (
    <KeyboardAvoidingView
      behavior={Platform.OS === "ios" ? "padding" : "height"}
      style={{ flex: 1 }}
    >
      <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
        <View style={styles.container}>
          <View style={styles.customerWrapper}>
           
           {/* [id]/[type].js */}

            <View style={styles.contentWrapper}>
              <Text style={styles.nameText}>{user.name}</Text>
              <Text style={styles.customerId}>
                Customer Id: {user.customerId}
              </Text>
              <Text style={styles.address}>{user.address}</Text>
              <TouchableOpacity onPress={() => handlePhoneClick(user.phone)}>
                <Text style={styles.phone}>{user.phone}</Text>
              </TouchableOpacity>
            </View>
          </View>
          <View style={styles.formWrapper}>
            <View style={styles.topRow}>
              <View style={styles.amtWrapper}>
                <Text style={styles.dateHeading}>Date</Text>
                <TouchableOpacity
                  onPress={() => setShow(true)}
                  style={styles.dateInputWrapper}
                >
                  <Text style={styles.dateInput}>{`${JSON.stringify(
                    date.getDate()
                  )}-${JSON.stringify(date.getMonth() + 1)}-${JSON.stringify(
                    date.getFullYear()
                  )}`}</Text>
                </TouchableOpacity>
              </View>
              {/* {show && (
                <DateTimePicker
                  value={date}
                  onChange={onChange}
                  mode="date"
                  is24Hour={true}
                />
              )} */}
            </View>

            <View style={styles.accountTypeSelection}>
              <Text style={styles.accountTypeLabel}>Account Type</Text>
              <View style={styles.radioGroup}>
                <ScrollView>
                  <FlatList
                    horizontal
                    data={user.customerAccount}
                    keyExtractor={(item) => item.accountNumber.toString()}
                    renderItem={({ item }) => (
                      <TouchableOpacity
                        onPress={() =>
                          handleAccountTypeChange(item.accountType)
                        }
                        style={[
                          styles.radioButton,
                          selectedAccountType === item.accountType &&
                            styles.radioButtonSelected,
                        ]}
                      >
                        <Text style={styles.radioText}>{item.accountType}</Text>
                      </TouchableOpacity>
                    )}
                  />
                </ScrollView>
              </View>
            </View>

            <View style={styles.accountNumberWrap}>
              <Text style={styles.accNumHead}>Account Details</Text>
              {selectedAccount && (
                <>
                  <Text style={styles.accountNumber}>
                    Account Number: {selectedAccount.accountNumber}
                  </Text>
                  <Text style={styles.accountType}>
                    Account Type: {selectedAccount.accountType}
                  </Text>
                  <Text style={styles.balance}>
                    Rs. {selectedAccount.balance}
                  </Text>
                </>
              )}
            </View>

            <View style={styles.payWrapper}>
              <View style={styles.upiContainer}>
                <Text style={styles.upiLabel}>UPI</Text>
                <TouchableOpacity
                  style={styles.upiWrapper}
                  onPress={handleUpi}
                  disabled={isUpiButtonDisabled}
                >
                  <Text style={styles.upi}>Scanner</Text>
                </TouchableOpacity>
              </View>
              <View style={styles.upiContainer}>
                <Text style={styles.upiLabel}>Cash</Text>
                <TouchableOpacity
                  style={styles.upiWrapper}
                  onPress={() => handleTransaction("CASH")}
                  disabled={isCashButtonDisabled}
                >
                  <Text style={styles.upi}>Paid</Text>
                </TouchableOpacity>
              </View>
            </View>

            <View style={styles.amountWrapper}>
              <TextInput
                placeholder="Enter amount"
                onChangeText={(text) => setAmount(text)}
                keyboardType="numeric"
                style={styles.amount}
              />
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
      </TouchableWithoutFeedback>
    </KeyboardAvoidingView>
  );
};

export default AddMoney;
