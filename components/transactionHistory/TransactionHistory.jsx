import { StyleSheet, Text, View, ScrollView, TouchableOpacity } from 'react-native'
import React from 'react'
import { SIZES,FONT } from '../../constants'
import { useRouter } from 'expo-router'

const TransactionHistory = ({transactions}) => {
    const router = useRouter();
  return (
    <ScrollView 
                showsVerticalScrollIndicator={false}
            >
               
                <View style={{
                    padding:SIZES.medium,
                    marginBottom:2
                }}>
                    
                    <View>
                        {transactions?.map((trans,ind)=>{
                            return(
                                <TouchableOpacity key={ind} style={{display:"flex", flexDirection:"row", borderRadius:20,
                                     borderColor:"#000", borderWidth:1, padding:SIZES.medium,
                                      justifyContent:"space-between", elevation:5, backgroundColor:"#fefefe"}}
                                      onPress={()=> router.push(`/txn/${trans?.transactionId}`)}
                                      >
                                    <View style={{display:"flex", flexDirection:"column", justifyContent:"space-between"}}> 
                                        <Text style={{fontFamily:FONT.medium, fontSize:SIZES.medium}}>{trans?.customerName}</Text>
                                        <Text>{trans?.transactionId}</Text>
                                        <Text>{trans?.date}</Text>
                                    </View>
                                    
                                    <View style={{display:"flex", flexDirection:"column", justifyContent:"space-between", paddingLeft:SIZES.small}}>
                                        <Text style={{fontFamily:FONT.bold, fontSize:SIZES.large}}>Rs. {trans?.amount}</Text>
                                        
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