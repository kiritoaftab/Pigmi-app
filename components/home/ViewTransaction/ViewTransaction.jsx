import { TouchableOpacity, View,Text, Image } from "react-native"
import styles from "./viewtransaction.style"
import { images } from "../../../constants"


const ViewTransaction = ({agent,handleClick}) => {
    return(
        <View style={styles.container}>
            <TouchableOpacity style={styles.innerContainer} onPress={handleClick}>
                <View style={styles.imageContainer}>
                    <Image
                        source={images.addMoney}
                        resizeMode="cover"
                        style={styles.icon}
                    />
                </View>
                <View style={styles.customerWrapper}>
                    <Text style={styles.headerText}>View Transactions </Text>
                </View>
            </TouchableOpacity>
        </View>
    )
}

export default ViewTransaction;