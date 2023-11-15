import { View, Text, Image ,TextInput, TouchableOpacity} from "react-native";
import styles from "./topbanner.style";
import { COLORS, icons, images, SIZES } from "../../../constants";
import { useState } from "react";

const Topbanner = (agent) => {
    console.log(agent)
    const [searchTerm, setSearchTerm] = useState(null)
  return (
    <View style={styles.container}>
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
                    <Text style={styles.nameText}>{agent?.agent?.agentName} </Text>
                </View>
                <Text style={styles.agentText}>Agent Id : {agent?.agent?.id}</Text>
            </View>
    </View>
  );
};

export default Topbanner;
