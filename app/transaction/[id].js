import axios from "axios";
import {
  View,
  Text,
  ActivityIndicator,
  ScrollView,
  SafeAreaView,
  useWindowDimensions,
  StyleSheet,
} from "react-native";
import { useGlobalSearchParams, Stack } from "expo-router";
import { useState, useEffect } from "react";
import { COLORS, SIZES, FONT, BASE_URL } from "../../constants";
import { TransactionHistory } from "../../components";
import DateTimePicker from "@react-native-community/datetimepicker";
import { TouchableOpacity } from "react-native-gesture-handler";

const Transaction = () => {
  const params = useGlobalSearchParams();
  console.log(params.id, "recieved agent Id");
  const [isLoading, setIsLoading] = useState(false);

  const [transactions, setTransactions] = useState([]);
  const [amount, setAmount] = useState([]);

  const [days, setDays] = useState(1);
  const [day1, setDay1] = useState();
  const [date, setDate] = useState(new Date());
  // const [startdate,setStartDate]=useState(null)
  // const [enddate,setEndDate]=useState(null)

  // const [show, setShow] = useState(false);

  const fetchTransactions = async (agentId, startDate,endDate) => {
    const year = startDate.getFullYear();
    const month = String(startDate.getMonth() + 1).padStart(2, '0');  // Months are zero-based, so add 1
    const day = String(startDate.getDate()).padStart(2, '0');
    const eyear = endDate.getFullYear();
    const emonth = String(endDate.getMonth() + 1).padStart(2, '0');  // Months are zero-based, so add 1
    const eday = String(endDate.getDate()).padStart(2, '0');
    setIsLoading(true);
    console.log('Fetching txns')
    try {
      const res = await axios.get(
        `${BASE_URL}transaction/searchTransactionHistory/${agentId}/${year}-${month}-${day}/${eyear}-${emonth}-${eday}`
      );
      console.log(`${BASE_URL}transaction/searchTransactionHistory/${agentId}/${year}-${month}-${day}/${eyear}-${emonth}-${eday}`);
      console.log(res.data.data, "Transaction history resp for ", startDate,endDate);
      setTransactions(res.data.data);
    } catch (error) {
      console.log(error);
    }finally{
      setIsLoading(false);
    }
  };

  useEffect(() => {
    if (params.id) {
      fetchTransactions(params.id, startDate,endDate);
      totalamount(params.id, startDate,endDate);
    }
  }, []);

  const totalamount = async (agentId, startDate,endDate) => {
    const year = startDate.getFullYear();
    const month = String(startDate.getMonth() + 1).padStart(2, '0');  // Months are zero-based, so add 1
    const day = String(startDate.getDate()).padStart(2, '0');
    const eyear = endDate.getFullYear();
    const emonth = String(endDate.getMonth() + 1).padStart(2, '0');  // Months are zero-based, so add 1
    const eday = String(endDate.getDate()).padStart(2, '0');
    try {
      const res = await axios.get(
        `${BASE_URL}/transaction/totalAmountNew?agentId=${agentId}&startDate=${year}-${month}-${day}&endDate=${eyear}-${emonth}-${eday}`
      );
      console.log(res.data, "total amount");
      setAmount(res.data.data.totalAmount);
    } catch (error) {
      console.log(error);
    }
  };

  // const onChange = (e, selectedDate) => {
  //   setDate(selectedDate);
  //   setShow(false);
  // };

  //datetimepicker
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [mode, setMode] = useState("date");
  const [show, setShow] = useState(false);
  const [show2, setShow2] = useState(false);
  const [pickerType, setPickerType] = useState("start");

  const onChange = (event, selectedDate) => {
    const currentDate =
      selectedDate || (pickerType === "start" ? startDate : endDate);
    setShow(Platform.OS === "ios");
    if (pickerType === "start") {
      setStartDate(currentDate);
      setPickerType("end");
      setShow(false);
    } else {
      setEndDate(currentDate);
      setShow2(false);
      fetchTransactions(params.id,startDate,endDate);
      totalamount(params.id,startDate,endDate);
    }
  };

  const showDatepicker = (type) => {
    setPickerType(type);
    setShow(true);
    setMode("date");
  };

  const showDatepicker2 = (type) => {
    setPickerType(type);
    setShow2(true);
    setMode("date");
  };

  return isLoading ? (
    <ActivityIndicator size={SIZES.large} color={COLORS.primary} />
  ) : (
    <SafeAreaView style={{ flex: 1, backgroundColor: COLORS.gray2 }}>
      <Stack.Screen
        options={{
          headerStyle: { backgroundColor: COLORS.green },
          headerShadowVisible: false,
          headerTitle: "",
        }}
      />
      <Text
        style={{
          fontSize: SIZES.xLarge,
          fontFamily: FONT.bold,
          padding: SIZES.medium,
        }}
      >
        View Transactions
      </Text>
      {/* <View
        style={{
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-evenly",
          marginTop: 0,
        }}
      >
        <TouchableOpacity
          style={days != 1 ? styles.chips : styles.clicked}
          onPress={() => setDays(1)}
        >
          <Text style={days == 1 ? styles.textClicked : ``}>1 Day</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={days != 3 ? styles.chips : styles.clicked}
          onPress={() => setDays(3)}
        >
          <Text style={days == 3 ? styles.textClicked : ``}>3 Days</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={days != 7 ? styles.chips : styles.clicked}
          onPress={() => setDays(7)}
        >
          <Text style={days == 7 ? styles.textClicked : ``}>7 Days</Text>
        </TouchableOpacity>
      </View> */}

      {/* {show ? (
        <DateTimePicker
          value={date}
          onChange={onChange}
          mode="date"
          is24Hour={false}
        />
      ) : (
        ``
      )}

      <TouchableOpacity onPress={() => setShow(true)}>
        <Text>{JSON.stringify(date)}</Text>

        
      </TouchableOpacity> */}
      <View style={{ flexDirection: "row",justifyContent:"space-evenly" }}>
        {show && (
          <DateTimePicker
            value={pickerType === "start" ? startDate : endDate}
            mode={mode}
            display="default"
            onChange={onChange}
          />
        )}
        {show2 && (
          <DateTimePicker
            value={pickerType === "start" ? startDate : endDate}
            mode={mode}
            display="default"
            onChange={onChange}
          />
        )}
        <TouchableOpacity onPress={() => showDatepicker("start")}
          style={{borderColor:"black", borderWidth:1, padding:9,borderRadius:5}}
        >
          <View style={{ marginLeft: 10 }}>
            {/* <Text style={{ fontWeight: 700 }}>Start Date</Text> */}
            <Text>{startDate.toDateString()}</Text>
          </View>
        </TouchableOpacity>
        <TouchableOpacity onPress={() => showDatepicker2("end")}
        style={{borderColor:"black", borderWidth:1, padding:9,borderRadius:5}}
        >
          <View style={{ marginLeft: 10 }}>
            {/* <Text style={{ fontWeight: 700 }}>End Date</Text> */}
            <Text>{endDate.toDateString()}</Text>
          </View>
        </TouchableOpacity>
      </View>

      {/* {show ? (
        <DateTimePicker
          value={enddate}
          onChange={onChange}
          mode="date"
          is24Hour={false}
        />
      ) : (
        ``
      )}

      <TouchableOpacity onPress={() => setShow(true)}>
        <Text>{JSON.stringify(enddate)}</Text>
      </TouchableOpacity> */}

      {/* <DateTimePicker
      value={startdate}
      selectedDate={startdate}
      onChange={(Date)=>setStartDate(Date)}
      selectsStart
      startdate={startdate}
      enddate={enddate}
      placeholderText="start Date"
      /> */}

      {/* {amount?.map((totalamount, ind) => {
        return (
          <View style={{ marginRight: "12%", marginTop: 14 }}>
            <Text
              style={{
                textAlign: "right",
                fontSize: SIZES.medium,
                fontFamily: FONT.bold,
              }}
            >
              Total Amount :{totalamount?.data?.totalAmount}
            </Text>
          </View>
        );
      })} */}
      <View style={{ paddingHorizontal: SIZES.medium, marginTop: 14 }}>
        <Text
          style={{
            textAlign: "left",
            fontSize: SIZES.medium,
            fontFamily: FONT.bold,
          }}
        >
          Total Amount : {amount}
        </Text>
      </View>

      {transactions ? <TransactionHistory transactions={transactions} /> : ``}
    </SafeAreaView>
  );
};

export default Transaction;

const styles = StyleSheet.create({
  chips: {
    borderRadius: 10,
    borderColor: COLORS.black,
    borderWidth: 1,
    padding: SIZES.small,
    elevation: 10,
    backgroundColor: COLORS.lightWhite,
  },
  clicked: {
    borderRadius: 10,
    borderColor: COLORS.black,
    borderWidth: 1,
    padding: SIZES.small,
    elevation: 10,
    backgroundColor: COLORS.black,
  },
  textClicked: {
    color: COLORS.lightWhite,
  },
});
