import { StyleSheet, Text, View, ScrollView, TouchableOpacity } from 'react-native'
import React from 'react'
import { SIZES, FONT } from '../../constants'
import { useRouter } from 'expo-router'

const TransactionHistory = ({ transactions }) => {
    const router = useRouter();

    // Function to format date to Indian format
    const formatDateTime = (dateString) => {
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero indexed
        const year = date.getFullYear();
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');
        return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;    }

    return (
        <ScrollView
            showsVerticalScrollIndicator={false}
        >
            <View style={{
                padding: SIZES.medium,
                marginBottom: 2
            }}>
                <View>
                    {transactions?.map((trans, ind) => {
                        return (
                            <TouchableOpacity key={ind} style={{
                                display: "flex", flexDirection: "row", borderRadius: 20,
                                borderColor: "#000", borderWidth: 1, padding: SIZES.medium,
                                justifyContent: "space-between", elevation: 5, backgroundColor: "#fefefe", marginTop: 7
                            }}
                                onPress={() => router.push(`/txn/${trans?.transactionId}`)}
                            >
                                <View style={{ display: "flex", flexDirection: "column", justifyContent: "space-evenly" }}>
                                    <Text style={{ fontFamily: FONT.bold, fontSize: SIZES.small, width: "80%" }}>{trans?.customerName}</Text>
                                    {/* <Text>{trans?.transactionId}</Text> */}
                                    <Text>{formatDateTime(trans?.date)}</Text>                                
                                </View>

                                <View style={{ display: "flex", flexDirection: "column", justifyContent: "space-evenly", paddingLeft: SIZES.small }}>
                                    <Text style={{ fontFamily: FONT.bold, fontSize: SIZES.medium }}>Rs. {trans?.amount}</Text>
                                    <Text>{trans?.accountType}</Text>
                                </View>
                            </TouchableOpacity>
                        )
                    })}
                </View>
            </View>
        </ScrollView>
    )
}

export default TransactionHistory

const styles = StyleSheet.create({})
