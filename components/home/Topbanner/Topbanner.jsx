import {View, Text, Image} from "react-native"
import styles from "./topbanner.style";
import { COLORS, icons, images, SIZES } from "../../../constants";

const Topbanner = () => {
    return(
        <View  style={styles.container}>
            <View style={styles.bankHeaderWrapper}>
                <Image
                    source={images.banklogo}
                    resizeMode="contain"
                    style={styles.banklogo}
                />
            </View>
            <View style={styles.agentWrapper}>
                <View style={styles.profileImageWrapper}>
                    <Image 
                        source={images.profile}
                        resizeMode="cover"
                        style={styles.profileImage}
                    />
                </View>
                
                <View style={styles.middleTextWrapper}>
                    <Text style={styles.helloText}>Hello Agent ,</Text>
                    <Text style={styles.nameText}>Aftab Ahmed </Text>
                </View>
                <Text style={styles.agentText}>Agent Id : AG101</Text>
            </View>
            
        </View>
    )
}

export default Topbanner;