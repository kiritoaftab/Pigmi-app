import {COLORS,FONT,SIZES} from '../../../constants'
import { StyleSheet } from 'react-native';

const styles = StyleSheet.create({
    container:{
        backgroundColor:COLORS.lightYellow,
        width:"80%",
        marginTop:"10%",
        
        borderRadius:20,
        padding:SIZES.large
    },
    innerContainer:{
        flex:1,
        flexDirection:"row",
        alignItems:"baseline",
        gap:20,
        height:"50%",
    },
    customerWrapper:{
        flex:1,
        flexDirection:"column",
        // columnGap:10,        
    },
    imageContainer:{
        paddingTop:SIZES.large,
        paddingLeft:SIZES.medium,
    },
    headerText:{
        fontSize:SIZES.large,
        fontWeight:"bold",
        fontFamily:FONT.bold
    },
    para:{
        fontSize:SIZES.small,
        paddingVertical:SIZES.small,
        paddingRight:SIZES.medium,
        fontFamily:FONT.regular
        
    }
})

export default styles;