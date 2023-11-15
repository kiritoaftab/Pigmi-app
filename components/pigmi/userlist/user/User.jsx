import { View,Text,TouchableOpacity ,Image} from "react-native";
import styles from "./user.styles";
import {images} from "../../../../constants"


const User = ({user, handleNavigate}) => {

    return(
        <TouchableOpacity style={styles.container} onPress={handleNavigate}>
            <View
                style={styles.profileWrapper}
            >
                <Image
                    source={images.profile}
                    resizeMode="contain"
                    style={styles.profileImg}
                />
            </View>
            <View style={styles.textWrapper}>
                <Text style={styles.name}>{user.customerName}</Text>
                <Text style={styles.custId}>Customer Id: {user.id}</Text>
            </View>
        </TouchableOpacity>
    )
}
export default User;