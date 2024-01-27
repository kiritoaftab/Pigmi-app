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
    address:{
        fontFamily:FONT.regular,
        fontSize:10,
        width:"70%",
        marginVertical:2,
    },
    phone:{
        fontFamily:FONT.medium,
        fontSize:15,
        marginVertical:2,
    },
    formWrapper:{
        height:"50%",
        borderRadius:30,
        // borderWidth:0.2,
        // padding:SIZES.medium,
        flexDirection:"column"
    },
    accountTypeSelection: {
        marginBottom: 20,
    },
    accountTypeLabel: {
        // fontSize: 18,
        // marginBottom: 10,
        // marginTop: 10,
        fontWeight: 'bold',
    },
    radioGroup: {
        flexDirection: 'row',
        justifyContent: 'space-between',
    },
    radioButton: {
        borderWidth: 1,
        borderColor: '#ccc',
        paddingVertical: 8,
        paddingHorizontal: 20,
        borderRadius: 5,
        marginRight:5,
        marginLeft:5
    },
    radioButtonSelected: {
        backgroundColor: 'lightblue',
        borderColor: 'blue',
    },
    radioText: {
        fontSize: 16,
        fontWeight: 'bold',
    },
    topRow:{
        flex:1,
        flexDirection:"row",
        justifyContent:"flex-start"
    },
    dateHeading:{
        fontFamily:FONT.extraBold,
        fontSize:SIZES.medium,
        
    },
    dateInputWrapper:{
        borderRadius:20,
        borderWidth:1,
        alignItems:"center",
        padding:SIZES.small,
    },
    dateInput:{
        fontFamily:FONT.medium,
        
    },
    balanceWrapper:{
        backgroundColor:COLORS.gray2,
        borderRadius:20,
        borderWidth:1,
        alignItems:"center",
        padding:SIZES.small,
    },
    balance:{
        fontFamily:FONT.bold,

    },
    amtWrapper:{
        flex:1,    
    },
    amountWrapper:{
        width:"90%",
        borderWidth:1,
        borderRadius:20,
        alignSelf:"center",
        padding:5
        // padding:SIZES.small,
    },
    amount:{
        fontFamily:FONT.semiBold,
    },
    payWrapper:{
        // marginTop:SIZES.large,
        padding:SIZES.medium,
        flex:1,
        flexDirection:"row",
        alignItems:"center",
        justifyContent:"space-around"
    },
    
    upiWrapper:{
        backgroundColor:COLORS.black,
        borderColor:COLORS.gray3,
        borderRadius:20,
        borderWidth:1,
        alignItems:"center",
        padding:SIZES.small,
    },

    upienable:{
        backgroundColor:COLORS.green,
        borderColor:COLORS.gray3,
        borderRadius:20,
        borderWidth:1,
        alignItems:"center",
        padding:SIZES.small,
    },


    upi:{
        fontFamily:FONT.light,
        color:COLORS.white
    },
    upiContainer:{
        alignItems:"center",
    },
    upiLabel:{
        fontFamily:FONT.extraBold,
        // paddingBottom:SIZES.xSmall
    },
    modalView:{
        padding:SIZES.large,
        flexDirection:"column",
        flex:1,
        alignItems:"center",
      
    },
    upiScannerWrapper:{
        width: "40%",
        height: "40%",
        justifyContent: "center",
        alignItems: "center",
        marginTop:30
    },
    upiImg:{
        // height:250,
        width:250,
    },
    header:{
        fontFamily:FONT.extraBold,
        fontSize:SIZES.xLarge,
        marginBottom:10
    },
    payableAmount:{
        fontFamily:FONT.bold,
        fontSize:SIZES.large,
        color:COLORS.green,
        marginTop:20
    },
    paymentButtonWrapper:{
        // marginVertical:SIZES.medium,
        backgroundColor:COLORS.green,
        // padding:SIZES.medium,
        borderRadius:15,
        padding:5
    },
    paymentButton:{
        color:COLORS.white,
        fontFamily:FONT.semiBold,
        
    },
    accountNumberWrap:{
        
        // marginTop:SIZES.medium,
        marginBottom:SIZES.medium,
    },
    accNumHead:{
        fontFamily:FONT.extraBold,
        // marginTop:SIZES.small,
    },
    accountNumber:{
        fontFamily:FONT.regular,
    },
    accountType:{
        fontFamily:FONT.regular,
    }


});

export default styles;