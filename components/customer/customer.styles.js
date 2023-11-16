import { StyleSheet } from "react-native";

import {COLORS, FONT, SIZES} from "../../constants";

const styles = StyleSheet.create({
    containKeyboard:{
        flex:1,
    },
    container:{
        width:"100%",
        paddingTop:SIZES.xxLarge,
        paddingHorizontal:SIZES.large,
        flex:1,
        flexDirection:"column",
        
    },
    nameInput:{
        fontFamily: FONT.regular,
        width: "100%",
        height: "120%",
        paddingHorizontal: SIZES.medium,
        borderWidth:1,
        borderRadius:20
    },
    inputWrapper:{
        flex:0.2,
        margin:SIZES.small,
    },
    inputLabel:{
        fontFamily:FONT.semiBold,
        fontSize:SIZES.medium,
    },
    splWrapper:{
        flex:1,
        margin:SIZES.large,
        flexDirection:"row"
    },
    btn:{
        width:"100%",
        height:"80%",
        borderWidth:1,
        borderRadius:SIZES.medium,
        alignItems:"center",
        alignSelf:"center",
    },
    uploadLabel:{
        fontFamily:FONT.bold,
    },
    uploadWrapper:{
        alignSelf:"baseline",
        marginLeft:SIZES.xxLarge,
        marginTop:SIZES.small,
    },
    aadhaarWrapper:{
        width:"80%",
        height:"80%"
    },
    addWrapper:{
        margin:SIZES.medium,
        height:"10%",
        width:"40%",
        backgroundColor:COLORS.green,
        alignItems:"center",
        alignSelf:"center",
        borderWidth:0.5,
        borderRadius:10
    },
    addBtn:{
        fontFamily:FONT.extraBold,
        color:COLORS.white,
        paddingTop:SIZES.xSmall
    },
    modalView:{
        flex:1,
        backgroundColor:COLORS.lightWhite,
        alignItems:"center",
        padding:SIZES.large
    },
    tickWrapper:{
        marginVertical:SIZES.xLarge,
    },
    heading:{
        fontFamily:FONT.extraBold,
        fontSize:SIZES.xLarge,
        marginVertical:SIZES.medium
    },
    customerId:{
        fontFamily:FONT.light,
        fontSize:SIZES.medium,
        marginVertical:SIZES.medium,
    },
    para:{
        fontFamily:FONT.regular,
        textAlign:"center"
    },
    btnWrapper:{
        backgroundColor:COLORS.green,
        width:"20%",
        height:SIZES.xxLarge,
        alignItems:"center",
        borderRadius:10,
        marginVertical:SIZES.xxLarge
    },
    done:{
        fontFamily:FONT.bold,
        fontSize:SIZES.large,
        color:COLORS.white
    }
})

export default styles;