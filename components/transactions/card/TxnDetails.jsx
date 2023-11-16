import { View, Text, Image, TouchableOpacity } from "react-native";
import styles from "./txnDetails.styles";
import { formatIndianRupee } from "../../../utils";
 import { useRouter } from "expo-router";
 import useTransaction from "../../../hook/useTransaction";   
 import { images } from "../../../constants";

 const TxnDetails = ({ user, txnId,data }) => {
  const { data: txnData, isLoading, error } = useTransaction(txnId);
  const router = useRouter();
  return (
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
          <Text style={styles.nameText}>{data.customerName}</Text>
          <Text style={styles.customerId}>Customer Id: {data.customerId}</Text>
        </View>
      </View> 
      {/* <View>
        <Text>{JSON.stringify(data)}</Text>
      </View> */}
      <View style={styles.formWrapper}>
        <View style={styles.topRow}>
          <View style={styles.dateWrapper}>
            <Text style={styles.dateHeading}>Date</Text>
            <View style={styles.dateInputWrapper}>
              <Text style={styles.dateInput}>{data?.transaction?.localDateTime.substring(0,10)}</Text>
            </View>
          </View>
          <View >
            <Text style={styles.dateHeading}>Amount Paid</Text>
            <View style={styles.balanceWrapper} >
              <Text style={styles.balance}> Rs. {formatIndianRupee(data?.transaction?.amount)} </Text>
            </View>
          </View>
        </View>
        <View style={styles.idWrapper}>
          <Text style={styles.txnId}> Transaction ID : {data?.transaction?.id}</Text>
        </View>
        <Text style={styles.agentLabel}>
          Agent Name
        </Text>
        <Text style={styles.agentName}>
          {data?.agentName}
        </Text>
        <View style={styles.bottomRow}>
          <View style={styles.accBalanceWrapper}>
            <Text style={styles.balLabel}>
              Balance
            </Text>
            <Text style={styles.bal}>
              {data?.customerAccountBalance}
            </Text>
          </View>
          <View style={styles.msgWrapper}>
            <Text style={styles.msgText}>Message sent</Text>
            <Image
              source={Image.doubleTick}
              resizeMode="contain"
            />
          </View>
        </View> 
        <TouchableOpacity style={styles.printWrapper} onPress={() => router.push('/')}>
          <Text style={styles.print}>PRINT</Text>
        </TouchableOpacity>
      </View>
 
    </View>
  );
};

export default TxnDetails;
