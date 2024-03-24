import * as React from "react";
import { View, StyleSheet, Button, Platform, Text } from "react-native";
import * as Print from "expo-print";
import { shareAsync } from "expo-sharing";
import useTransaction from "../hook/useTransaction";

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
        <h3>Date Time : 2024-03-24 12:21:12</h3>
        <h3>Amount : 400</h3>
        <h3>Account Number: NN100200300</h3>
        <h3>Balance : 4600</h3>
        <h3>Customer Name : Tanveer Ahmed Waddo</h3>
        <h3>Agent Name : Abdul Khaliq</h3>
        <h3>Agent No : 9036517539</h3>
        <h3>Account Opening Date : 2023-07-19</h3>
    </div>
  </body>
</html>
`;

const print = () => {
  const { data, isLoading, error, s3PdfUrl, refetch } =
    useTransaction("TRANSACTION-101");

  console.log(data, "from  useTransaction");

  const [selectedPrinter, setSelectedPrinter] = React.useState();

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
            <h3>Account Number: NN100200300</h3>
            <h3>Balance : ${data?.customerAccountBalance}</h3>
            <h3>Customer Name : ${data?.customerName}</h3>
            <h3>Agent Name : ${data?.agentName}</h3>
            <h3>Agent No : 9036517539</h3>
            <h3>Account Opening Date : 2023-07-19</h3>
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

  return (
    <View>
      <Button title="Print" onPress={print} />
      <View style={styles.spacer} />
      <Button title="Print to PDF file" onPress={printToFile} />
      {Platform.OS === "ios" && (
        <>
          <View style={styles.spacer} />
          <Button title="Select printer" onPress={selectPrinter} />
          <View style={styles.spacer} />
          {selectedPrinter ? (
            <Text
              style={styles.printer}
            >{`Selected printer: ${selectedPrinter.name}`}</Text>
          ) : undefined}
        </>
      )}
    </View>
  );
};

export default print;

const styles = StyleSheet.create({});
