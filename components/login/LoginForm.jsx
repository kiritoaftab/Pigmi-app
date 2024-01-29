import {
  View,
  TextInput,
  TouchableOpacity,
  Text,
  Modal,
  ActivityIndicator,
} from "react-native";
import styles from "./loginform.styles";
import axios from "axios";
import { COLORS, icons, images, SIZES, BASE_URL } from "../../constants";
import { useState } from "react";
import { Stack, useRouter } from "expo-router";

const LoginForm = () => {
  const router = useRouter();

  const [isModalVisible, setIsModalVisible] = useState(false);
  const [email, setEmail] = useState(null);
  const [password, setPassword] = useState(null);

  const [data, setData] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [otp, setOtp] = useState(null);



  const loginApiCall = async (email, password) => {
    const url = BASE_URL + "agent/agentLogin/email/" + email + "/" + password;

    try {
      const response = await axios.get(url);
      console.log(response);
      setData(response.data.data);
     
      setIsLoading(false);
      console.log(data);
      setIsModalVisible(true);
    } catch (error) {
      console.log(error);
      // setError(error);
      setIsModalVisible(false);
      alert("Inavlid Email or password");
    } finally {
      setIsLoading(false);
    }
  };

  const handleLogin = () => {
     
    console.log(`Email = ${email} | Password = ${password}`);
    if (!emailValidator(email)) {
      setIsModalVisible(false);
      return;
    } else if (!passwordValidator(password)) {
      setIsModalVisible(false);
      return;
    } else {
      setIsLoading(true);
      loginApiCall(email, password);
    }
  };

  const otpApiCall = async (otp) => {
    const url = BASE_URL + "/agent/otp";
    try {
      const response = await axios.get(url, {
        params: {
          otp: otp,
        },
      });
      console.log(response);
      setData(response.data.data);
      setIsLoading(false);
      console.log(data);
      setIsModalVisible(false);
      alert("Otp verified");
      router.push(`/home/${data?.id}`);

    } catch (error) {
      console.log(error);
      // setError(error);
      setIsModalVisible(true);
      alert("Invalid Otp");
      // router.push('/')
    } finally {
      setIsLoading(false);
    }
  };

  const handleOtp = () => {
    if (otp && otp.length === 4) {
      
      setIsLoading(true);
      otpApiCall(otp);
    } else {
      alert("Please enter OTP");
    }
  };
  const emailValidator = (email) => {
    let reg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w\w+)+$/;
    if (reg.test(email) === false) {
      console.log("Email is Not Valid");
      alert(`Email id ${email} is invalid`);
      return false;
    } else {
      console.log("Email is Correct");
      return true;
    }
  };

  const passwordValidator = (password) => {
    if (password?.length > 2) {
      return true;
    } else {
      alert(`Please enter valid password`);
      return false;
    }
  };

  return isLoading ? (
    <ActivityIndicator size="large" color={COLORS.primary} />
  ) : error ? (
    <Text>{JSON.stringify(error)}</Text>
  ) : (
    <View
      style={{
        marginTop: -66,
        backgroundColor: COLORS.lightWhite,
        width: "80%",
        alignItems: "center",
        alignSelf: "center",

        borderRadius: 30,
      }}
    >
      <View style={styles.container}>
        <Text style={styles.loginHeader}>Login </Text>
        <Text style={styles.emailLabel}>Enter Email Address</Text>
        <TextInput
          placeholder="Enter registered email address"
          style={styles.emailInput}
          onChangeText={(text) => setEmail(text)}
        />
        <Text style={styles.passLabel}>Enter Password</Text>
        <TextInput
          placeholder="Enter Password"
          secureTextEntry={true}
          style={styles.passInput}
          onChangeText={(text) => setPassword(text)}
        />
        <TouchableOpacity style={styles.loginWrapper} onPress={handleLogin}>
          <Text style={styles.login}>LOGIN</Text>
        </TouchableOpacity>
      </View>

      <Modal
        visible={isModalVisible}
        animationType="slide"
        presentationStyle="pageSheet"
        onRequestClose={() => setIsModalVisible(false)}
      >
        <View style={styles.modalView}>
          <View style={styles.otpForm}>
            <Text style={styles.otpHeader}>Enter OTP sent to mail </Text>
            <Text style={styles.emailOtp}>{email}</Text>
            <TextInput
              style={styles.otpInput}
              keyboardType="decimal-pad"
              placeholder="OTP"
              onChangeText={(text) => setOtp(text)}
            />
            <TouchableOpacity style={styles.submitWrapper} onPress={handleOtp}>
              <Text style={styles.submit}>SUBMIT</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </View>
  );
};

export default LoginForm;
