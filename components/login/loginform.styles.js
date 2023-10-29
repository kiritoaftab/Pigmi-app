import { StyleSheet } from "react-native";

import {COLORS, FONT, SIZES} from "../../constants";

const styles = StyleSheet.create({
    container:{
        width:"100%",
        paddingTop:SIZES.xxLarge,
        paddingHorizontal:SIZES.large,
        flex:1,
        flexDirection:"column",
        alignItems:"center",
        justifyContent:"space-between"
    },
    loginHeader:{
        fontFamily:FONT.extraBold,
        fontSize:SIZES.xLarge,
        paddingBottom:SIZES.small,
    },
    emailInput:{
        fontFamily: FONT.regular,
        paddingVertical: SIZES.xSmall,
        borderWidth:1,
        borderRadius:20,
        width:"100%",
        paddingHorizontal:SIZES.medium,
        marginBottom:SIZES.small,
    },
    emailLabel:{
        fontFamily:FONT.bold,
        fontSize:SIZES.large,
        marginBottom:SIZES.small,
    },
    passLabel:{
        fontFamily:FONT.bold,
        fontSize:SIZES.large,
        marginBottom:SIZES.small,
    },
    passInput:{
        fontFamily: FONT.regular,
        paddingVertical: SIZES.xSmall,
        borderWidth:1,
        borderRadius:20,
        width:"100%",
        paddingHorizontal:SIZES.medium,
        marginBottom:SIZES.small,
    },
    loginWrapper:{
        margin:SIZES.medium,
        height:"10%",
        width:"40%",
        backgroundColor:COLORS.green,
        alignItems:"center",
        alignSelf:"center",
        borderWidth:0.5,
        borderRadius:10,
        
    },
    login:{
        fontFamily:FONT.extraBold,
        color:COLORS.white,
        paddingTop:2,
    },
    otpInput:{
        fontFamily: FONT.regular,
        paddingVertical: SIZES.xSmall,
        borderWidth:1,
        borderRadius:20,
        width:"40%",
        paddingHorizontal:SIZES.medium,
        marginBottom:SIZES.small,
        letterSpacing:10,
        textAlign:"center"
    },
    otpHeader:{
        fontFamily:FONT.bold,
        fontSize:SIZES.large,
        marginBottom:SIZES.small,
    },
    otpForm:{
        
        alignItems:"center",
        marginTop:SIZES.xxLarge,
    },
    emailOtp:{
        fontFamily:FONT.semiBold,
        color:COLORS.skyBlue,
        fontSize:SIZES.medium,
        marginBottom:SIZES.medium,
    },
    modalView:{
        justifyContent:"center",
        padding:SIZES.xxLarge
    },
    submitWrapper:{
        margin:SIZES.medium,
        padding:SIZES.small,
        width:"40%",
        backgroundColor:COLORS.green,
        alignItems:"center",
        alignSelf:"center",
        borderWidth:0.5,
        borderRadius:10,
        
    },
    submit:{
        fontFamily:FONT.extraBold,
        color:COLORS.white,
        paddingTop:2,
    },
})

export default styles;