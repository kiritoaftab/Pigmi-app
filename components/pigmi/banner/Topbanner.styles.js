import { StyleSheet } from "react-native";

import {COLORS, FONT, SIZES} from "../../../constants";

const styles = StyleSheet.create({
    container:{
        width:"100%",
        backgroundColor:COLORS.green,
        height:250,
        paddingBottom:60,
        borderBottomLeftRadius:30,
        borderBottomRightRadius:30,
    },
    bankHeaderWrapper:{
        padding:SIZES.medium,
        
    },
    banklogo:{
        height:50,
        width:170
    },
    searchContainer: {
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "row",
        marginTop: SIZES.large,
        height: 50,
        paddingHorizontal:SIZES.medium,
      },
    searchWrapper: {
        flex: 1,
        backgroundColor: COLORS.white,
        marginRight: SIZES.small,
        justifyContent: "center",
        alignItems: "center",
        borderRadius: SIZES.medium,
        height: "100%",
        
      },
      searchInput: {
        fontFamily: FONT.regular,
        width: "100%",
        height: "100%",
        paddingHorizontal: SIZES.medium,
      },
      searchBtn: {
        width: 50,
        height: "100%",
        backgroundColor: COLORS.lightWhite,
        borderRadius: SIZES.medium,
        justifyContent: "center",
        alignItems: "center",
      },
      searchBtnImage: {
        width: "50%",
        height: "50%",
        tintColor: "#000",
      },

})    

export default styles;