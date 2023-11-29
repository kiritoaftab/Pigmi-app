import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  Image,
  TouchableOpacity,
  TextInput,
  KeyboardAvoidingView,
  Platform,
  TouchableWithoutFeedback,
  Modal,
  Keyboard,
} from "react-native";
import DateTimePicker from "@react-native-community/datetimepicker";
import { useRouter } from "expo-router";
import axios from "axios";
import styles from "./addmoney.styles";
import { BASE_URL, images } from "../../../constants";
import { ActivityIndicator } from "react-native-paper";

const AddMoney = ({ user }) => {
  const router = useRouter();

  const [date, setDate] = useState(new Date());
  const [show, setShow] = useState(false);
  const [txnId, setTxnId] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [amount, setAmount] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [selectedAccountType, setSelectedAccountType] = useState("savings");
  const [selectedAccount, setSelectedAccount] = useState(null);

  const handleAccountTypeChange = (type) => {
    setSelectedAccount(
      user.customerAccount.find((acc) => acc.accountType === type) || null
    );
    setSelectedAccountType(type);
  };

  const onChange = (e, selectedDate) => {
    setDate(selectedDate);
    setShow(false);
  };

  const txnApiCall = async (txnData) => {
    const url = BASE_URL + "/transaction/save";
    try {
      const response = await axios.post(url, txnData);
      router.push(`/txn/${response?.data?.data?.transaction?.id}`);
    } catch (error) {
      console.log(JSON.stringify(error) + " while fetching transaction");
      alert("There is an error");
    }
  };

  const handleTransaction = (mode) => {
    setIsModalVisible(false);
    if (mode) {
      const txnData = {
        accountNumber:
          Array.isArray(user?.customerAccount) &&
          user.customerAccount?.length > 0
            ? user.customerAccount[0]?.accountNumber
            : "",
        accountType:
          Array.isArray(user?.customerAccount) &&
          user.customerAccount?.length > 0
            ? user.customerAccount[0]?.accountType
            : "",
        agentId: user?.agentId,
        amount: parseInt(amount),
        customerId: user?.customerId,
        mode: mode,
        status: true,
      };
      txnApiCall(txnData);
    }
  };

  const handleUpi = () => {
    if (!amount || amount < 1) {
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
            <View style={styles.imgWrapper}>
              <Image
                source={images.profile}
                resizeMode="contain"
                style={styles.img}
              />
            </View>
            <View style={styles.contentWrapper}>
              <Text style={styles.nameText}>{user.name}</Text>
              <Text style={styles.customerId}>
                Customer Id: {user.customerId}
              </Text>
              <Text style={styles.address}>{user.address}</Text>
              <Text style={styles.phone}>{user.phone}</Text>
            </View>
          </View>
          <View style={styles.formWrapper}>
            <View style={styles.topRow}>
              <View style={styles.dateWrapper}>
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
              {show && (
                <DateTimePicker
                  value={date}
                  onChange={onChange}
                  mode="date"
                  is24Hour={true}
                />
              )}
            </View>

            <View style={styles.accountTypeSelection}>
              <Text style={styles.accountTypeLabel}>Account Type</Text>
              <View style={styles.radioGroup}>
                <TouchableOpacity
                  onPress={() => handleAccountTypeChange("savings")}
                  style={[
                    styles.radioButton,
                    selectedAccountType === "savings" &&
                      styles.radioButtonSelected,
                  ]}
                >
                  <Text style={styles.radioText}>Savings</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  onPress={() => handleAccountTypeChange("Umra")}
                  style={[
                    styles.radioButton,
                    selectedAccountType === "Umra" &&
                      styles.radioButtonSelected,
                  ]}
                >
                  <Text style={styles.radioText}>Umra</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  onPress={() => handleAccountTypeChange("rd")}
                  style={[
                    styles.radioButton,
                    selectedAccountType === "rd" && styles.radioButtonSelected,
                  ]}
                >
                  <Text style={styles.radioText}>RD</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  onPress={() => handleAccountTypeChange("dd")}
                  style={[
                    styles.radioButton,
                    selectedAccountType === "dd" && styles.radioButtonSelected,
                  ]}
                >
                  <Text style={styles.radioText}>DD</Text>
                </TouchableOpacity>
              </View>
            </View>

            <View style={styles.accountNumberWrap}>
              <Text style={styles.accNumHead}>Account Details</Text>
              {selectedAccount && (
                <>
                  <Text style={styles.accountNumber}>
                    {selectedAccount.accountNumber}
                  </Text>
                  <Text style={styles.accountType}>
                    {selectedAccount.accountType}
                  </Text>
                  <Text style={styles.balance}>
                    Rs. {selectedAccount.balance}
                  </Text>
                </>
              )}
            </View>

            {selectedAccountType === "dd" && (
              <View style={styles.accountNumberWrap}>
                <Text style={styles.accNumHead}>DD Account Details</Text>
                {selectedAccount && (
                  <>
                    <Text style={styles.accountNumber}>
                      {selectedAccount.accountNumber}
                    </Text>
                    <Text style={styles.accountType}>
                      {selectedAccount.accountType}
                    </Text>
                    <Text style={styles.balance}>
                      Rs. {selectedAccount.balance}
                    </Text>
                  </>
                )}
              </View>
            )}

            {selectedAccountType === "Umra" && (
              <View style={styles.accountNumberWrap}>
                <Text style={styles.accNumHead}>Umra Account Details</Text>
                {selectedAccount && (
                  <>
                    <Text style={styles.accountNumber}>
                      {selectedAccount.accountNumber}
                    </Text>
                    <Text style={styles.accountType}>
                      {selectedAccount.accountType}
                    </Text>
                    <Text style={styles.balance}>
                      Rs. {selectedAccount.balance}
                    </Text>
                  </>
                )}
              </View>
            )}

            <View style={styles.payWrapper}>
              <View style={styles.upiContainer}>
                <Text style={styles.upiLabel}>UPI</Text>
                <TouchableOpacity style={styles.upiWrapper} onPress={handleUpi}>
                  <Text style={styles.upi}>Scanner</Text>
                </TouchableOpacity>
              </View>
              <View style={styles.upiContainer}>
                <Text style={styles.upiLabel}>Cash</Text>
                <TouchableOpacity
                  style={styles.upiWrapper}
                  onPress={() => handleTransaction("CASH")}
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