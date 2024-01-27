import { View,Text,TouchableOpacity ,Image, ScrollView} from "react-native";
import styles from "./user.styles";
import {images} from "../../../../constants"



const User = ({user, handleNavigate}) => {

    return(
        <ScrollView>
        <TouchableOpacity style={styles.container} onPress={handleNavigate}>
            <View style={styles.profileWrapper}
            >
                <Image
                    src={user.customerProfilePic}
                    resizeMode="contain"
                    style={styles.profileImg}
                />
            </View>
            <View style={styles.textWrapper}>
                <Text style={styles.name}>{user.customerName}</Text>
                <Text style={styles.custId}>Customer Id: {user.id}</Text>
            </View>
        </TouchableOpacity>
        </ScrollView>
    )
}
export default User;