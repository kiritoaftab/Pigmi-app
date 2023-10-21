import { StyleSheet } from "react-native";

import {COLORS, FONT, SIZES} from "../../../constants";

const styles = StyleSheet.create({
    container:{
        width:"100%",
        backgroundColor:COLORS.green,
        height:250,
        paddingBottom:60,
    },
    bankHeaderWrapper:{
        padding:SIZES.medium,
        
    },
    profileImage:{
        width: "60%",
        height: "60%",
        borderRadius: SIZES.small / 1.25,
    },
    profileImageWrapper:{
        width: 80,
        height: 80,
        borderRadius: 80/ 2,
        justifyContent: "center",
        alignItems: "center",
    },
    agentWrapper:{
        flex:1,
        flexDirection:"row",
        alignItems:"center"
    },
    middleTextWrapper:{
        flex:1,
        flexDirection:"column",
        alignItems:"stretch"
    },
    helloText:{
        fontSize:SIZES.xLarge,
        fontFamily:FONT.bold,
        color:COLORS.white,
        fontWeight:"600"
    },
    nameText:{
        fontFamily:FONT.light,
        fontSize:SIZES.medium,
        color:COLORS.lightWhite,
        fontWeight:"200",
        marginTop:"3%"
    },
    agentText:{
        color:COLORS.white,
        fontWeight:"200",
        fontFamily:FONT.light,
        fontSize:SIZES.small,
    },
    banklogo:{
        height:50,
        width:170
    }
})    

export default styles;