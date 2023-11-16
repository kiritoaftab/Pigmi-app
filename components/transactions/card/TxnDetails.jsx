import { View, Text, Image, TouchableOpacity } from "react-native";
import styles from "./txnDetails.styles";
import { formatIndianRupee } from "../../../utils";
 import { useRouter } from "expo-router";
 import useTransaction from "../../../hook/useTransaction";   

 const TxnDetails = ({ user, txnId }) => {
  const { data: txnData, isLoading, error } = useTransaction({ txnId }); // Pass txnId as an object


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
          <Text style={styles.nameText}>{user.name}</Text>
          <Text style={styles.customerId}>Customer Id: {user.customerId}</Text>
        </View>
      </View> 
      <View style={styles.formWrapper}>
        <View style={styles.topRow}>
          <View style={styles.dateWrapper}>
            <Text style={styles.dateHeading}>Date</Text>
            <View style={styles.dateInputWrapper}>
              <Text style={styles.dateInput}>{txn?.date}</Text>
            </View>
          </View>
          <View >
            <Text style={styles.dateHeading}>Amount Paid</Text>
            <View style={styles.balanceWrapper} >
              <Text style={styles.balance}> Rs. {formatIndianRupee(txn?.amount)} </Text>
            </View>
          </View>
        </View>
        <View style={styles.idWrapper}>
          <Text style={styles.txnId}> Transaction ID : {txn.txnId}</Text>
        </View>
        <Text style={styles.agentLabel}>
          Agent Name
        </Text>
        <Text style={styles.agentName}>
          {txn.agentName}
        </Text>
        <View style={styles.bottomRow}>
          <View style={styles.accBalanceWrapper}>
            <Text style={styles.balLabel}>
              Balance
            </Text>
            <Text style={styles.bal}>
              {user?.balance}
            </Text>
          </View>
          <View style={styles.msgWrapper}>
            <Text style={styles.msgText}>Message sent</Text>
            <Image
              source={images.doubleTick}
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
