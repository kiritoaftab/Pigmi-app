import { View, Text, Image, TouchableOpacity,Linking } from "react-native";
import styles from "./txnDetails.styles";
import { formatIndianRupee } from "../../../utils";
 import { useRouter } from "expo-router";
 import useTransaction from "../../../hook/useTransaction";   
 import { BASE_URL, images } from "../../../constants";
import axios from "axios";
import { useEffect, useState } from "react";

 const TxnDetails = ({ user, txnId,data,s3PdfUrl }) => {
  // const { data: txnData, isLoading, error, s3PdfUrl } = useTransaction(txnId);
  console.log(`Showing ${JSON.stringify(data)} with ${s3PdfUrl}`)
  const handlePrint = () => {
    console.log(`Opening ${s3PdfUrl}`)
    Linking.openURL(s3PdfUrl);
    router.push(`/home/${data?.agentId}`);
  }

  const router = useRouter();
  return (
    <View style={styles.container}>
      <View style={styles.customerWrapper}>
        {/* <View style={styles.imgWrapper}>
          <Image
            source={{ uri: data?.customerProfilePic }}
            resizeMode="contain"
            style={styles.img}
          />
        </View> */}
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
            </View> 
          
        <TouchableOpacity style={styles.printWrapper} onPress={() => handlePrint()}>
          <Text style={styles.print}>PRINT</Text>
        </TouchableOpacity>
      </View>
 
    </View>
  );
};

export default TxnDetails;
