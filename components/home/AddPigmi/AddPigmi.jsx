import { View, Text, Image, TouchableOpacity } from "react-native";

import styles from "./addpigmi.style";
import { COLORS, icons, images, SIZES } from "../../../constants";

const AddPigmi = ({agent,handleClick}) => {
    
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
                    <Text style={styles.headerText}>Add Daily Deposit </Text>
                </View>
            </TouchableOpacity>
        </View>
    )
}

export default AddPigmi;