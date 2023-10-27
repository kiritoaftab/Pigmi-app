import { StyleSheet } from "react-native";

import {COLORS, FONT, SIZES} from "../../../constants";

const styles = StyleSheet.create({
    container :{
        flex:1,
        width:"80%",
        backgroundColor:COLORS.lightWhite,
        marginTop:-120,
        borderRadius:60,
        padding:SIZES.xLarge,
    },
    customerWrapper:{
        flexDirection:"row",
    },
    imgWrapper:{
        width: 80,
        height: 80,
        borderRadius: 80 /2,
        justifyContent: "center",
        alignItems: "center",
    },
    img :{
        width: "80%",
        height: "80%",
        borderRadius: 100 / 1.25,
    },
    nameText:{
        fontFamily:FONT.extraBold,
        fontSize:20,
    },
    customerId:{
        fontFamily:FONT.medium,
        fontSize:12,
        marginVertical:3,
    },
    formWrapper:{
        height:"80%",
        borderRadius:30,
        borderWidth:0.2,
        padding:SIZES.large,
        
        
    },
    topRow:{
        flex:0.2,
        flexDirection:"row",
        justifyContent:"space-between"
    },
    dateHeading:{
        fontFamily:FONT.bold,
        fontSize:SIZES.medium,
        
    },
    dateInputWrapper:{
        
        alignItems:"center",
       
    },
    dateInput:{
        fontFamily:FONT.medium,
    },
    balanceWrapper:{
        alignItems:"center",
        
    },
    balance:{
        fontFamily:FONT.medium,
    },
    txnId:{
        marginTop:SIZES.large,
        fontFamily:FONT.light,
        textAlign:"center"
    },
    agentLabel:{
        marginTop:SIZES.medium,
        fontFamily:FONT.bold
    },
    agentName:{
        fontFamily: FONT.regular
    },
    bottomRow:{
        flex:1,
        flexDirection:"row",
        justifyContent:"space-between",
        marginTop:SIZES.medium,
    },
    accBalanceWrapper:{
        flexDirection:"column"
    },
    balLabel:{
        fontFamily:FONT.extraBold
    },
    bal:{
        fontFamily:FONT.semiBold,
        color:COLORS.gray3,
        fontSize:SIZES.xLarge
    },
    idWrapper:{
        padding:SIZES.small,
    },
    msgWrapper:{
        flexDirection:"row",
        alignSelf:"flex-start",
        marginTop:SIZES.large,
        borderRadius:20,
        borderWidth:1,
        borderColor:COLORS.green,
        width:"60%",
        justifyContent:"space-between",
        padding:SIZES.small,
    },
    msgText:{
        fontFamily:FONT.light,
        fontSize:10,
        color:COLORS.green
    },
    printWrapper:{
        alignSelf:"center",
        backgroundColor:COLORS.black,
        marginBottom:SIZES.xxLarge,
        padding:SIZES.xLarge,
        borderRadius:40,
    },
    print:{
        fontFamily:FONT.bold,
        color:COLORS.white,
    }
})

export default styles;