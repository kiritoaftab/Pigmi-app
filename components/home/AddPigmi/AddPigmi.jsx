import { View, Text, Image, TouchableOpacity } from "react-native";

import styles from "./addpigmi.style";
import { COLORS, icons, images, SIZES } from "../../../constants";

const AddPigmi = ({handleClick}) => {

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
                    <Text style={styles.headerText}>Add Pigmi </Text>
                    <Text style={styles.para}>With a quick and easy click, you have the power to seamlessly add the daily Pigmi amount to the customer's account, generate a printable slip, and provide it to them</Text>
                </View>
            </TouchableOpacity>
        </View>
    )
}

export default AddPigmi;