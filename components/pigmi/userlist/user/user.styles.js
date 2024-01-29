import { StyleSheet } from "react-native";

import {COLORS, FONT, SIZES, SHADOWS} from "../../../../constants";

const styles = StyleSheet.create({
    container:{
        width:"90%",
        backgroundColor:COLORS.lightWhite,
        margin:SIZES.small,
        shadowRadius:5,
        shadowOpacity:0.5,
        shadowColor:COLORS.white,
        borderRadius:20,
        paddingTop:SIZES.small,
        borderWidth:0.2,
        flex:1,
        flexDirection:"row",
        justifyContent:"space-evenly"
    },
    profileWrapper:{
        width: 80,
        height: 80,
        borderRadius: 80 /2,
        justifyContent: "center",
        alignItems: "center",
        marginTop:-10
    },
    profileImg:{
        width: "60%",
        height: "60%",
        borderRadius: 100 / 1.25,
    },
    textWrapper:{
        
        flex:1,
        flexDirection:"column",
    },
    name:{
        fontFamily:FONT.bold,
        fontSize:15,
        textTransform: 'capitalize', 
    },
    custId:{
        fontFamily:FONT.light,
    },
    btnWrapper:{
        backgroundColor:COLORS.green,
        width:40,
        height:40,
    }
});

export default styles;