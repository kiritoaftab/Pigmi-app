import { View, Text, Image, TouchableOpacity } from "react-native";

import styles from "./addcustomer.style";
import { COLORS, icons, images, SIZES } from "../../../constants";

const AddCustomer = ({handleClick}) => {

    return(
        <View style={styles.container}>
            <TouchableOpacity style={styles.innerContainer} onPress={handleClick}>
                <View style={styles.imageContainer}>
                <Image
                    source={images.addicon}
                    resizeMode="cover"
                    style={styles.icon}
                />
                </View>
                
                <View style={styles.customerWrapper}>
                    <Text style={styles.headerText}>Add Customer</Text>
                    <Text style={styles.para}>With one click, you can effortlessly add a new customer to our system and initiate the process of creating their digital money signature with the bank.</Text>
                </View>
            </TouchableOpacity>
        </View>
    )
}

export default AddCustomer;