import { View, Text, Image, TouchableOpacity,Linking } from "react-native";
import styles from "./txnDetails.styles";
import { formatIndianRupee } from "../../../utils";
 import { useRouter } from "expo-router";
 import useTransaction from "../../../hook/useTransaction";   
 import { BASE_URL, images } from "../../../constants";
import axios from "axios";
import { useEffect, useState } from "react";
import * as Print from "expo-print";
import { shareAsync } from "expo-sharing";

 const TxnDetails = ({ user, txnId,data,s3PdfUrl }) => {
  // const { data: txnData, isLoading, error, s3PdfUrl } = useTransaction(txnId);
  console.log(`Showing ${JSON.stringify(data)} with ${s3PdfUrl}`)
  const handlePrint = () => {
    console.log(`Opening ${s3PdfUrl}`)
    Linking.openURL(s3PdfUrl);
    router.push(`/home/${data?.agentId}`);
  }

  const [selectedPrinter, setSelectedPrinter] = useState();

  //PRINTING Logic
  const makeHtml = (data) => {
    const html = `
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    </head>
    <body style="text-align: center;">
        <h1 style="font-size: 50px; font-family: Helvetica Neue; font-weight: normal;">
        Union Cooperative Society Limited
        </h1>
        <h1 style="font-size:30px; font-family: Helvetica Neue">9986968880</h1>
        <div>
            <h3>Date Time : ${data?.transaction?.localDateTime}</h3>
            <h3>Amount : ${data?.transaction?.amount}</h3>
            <h3>Account Number: ${data?.accountNumber}</h3>
            <h3>Balance : ${data?.customerAccountBalance}</h3>
            <h3>Customer Name : ${data?.customerName}</h3>
            <h3>Agent Name : ${data?.agentName}</h3>
            <h3>Agent No : ${data?.agentPhone}</h3>
            <h3>Account Opening Date : ${data?.accountOpeningDate}</h3>
        </div>
    </body>
    </html>
`;
  return html;
    };

    const print = async () => {
      // On iOS/android prints the given html. On web prints the HTML from the current page.
      const html = makeHtml(data);
      await Print.printAsync({
        html,
        printerUrl: selectedPrinter?.url, // iOS only
      });
    };
  
    const printToFile = async () => {
      // On iOS/android prints the given html. On web prints the HTML from the current page.
      const { uri } = await Print.printToFileAsync({ html });
      console.log("File has been saved to:", uri);
      await shareAsync(uri, { UTI: ".pdf", mimeType: "application/pdf" });
    };
  
    const selectPrinter = async () => {
      const printer = await Print.selectPrinterAsync(); // iOS only
      setSelectedPrinter(printer);
    };


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
          
          <View style={{display:"flex", flexDirection:"row", justifyContent:"space-evenly"}}>
            <TouchableOpacity style={styles.printWrapper} onPress={() => handlePrint()}>
            <Text style={styles.print}>SAVE</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.printWrapper} onPress={() => print()}>
            <Text style={styles.print}>PRINT</Text>
          </TouchableOpacity>
          </View>
        
      </View>
 
    </View>
  );
};

export default TxnDetails;
