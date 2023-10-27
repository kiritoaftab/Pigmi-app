import { View, Text, Image, TextInput, TouchableOpacity } from "react-native";
import styles from "./topmoney.styles";
import { COLORS, icons, images, SIZES } from "../../../constants";
import { useState } from "react";

const TopMoneybanner = () => {
  const [searchTerm, setSearchTerm] = useState(null);
  return (
    <View style={styles.container}>
      <View style={styles.bankHeaderWrapper}>
        <Image
          source={images.banklogo}
          resizeMode="contain"
          style={styles.banklogo}
        />
      </View>
    
    </View>
  );
};

export default TopMoneybanner;
