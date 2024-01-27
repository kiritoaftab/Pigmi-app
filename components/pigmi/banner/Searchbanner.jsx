import { View, Text, Image, TextInput, TouchableOpacity } from "react-native";
import styles from "./Topbanner.styles";
import { COLORS, icons, images, SIZES } from "../../../constants";
import { useState } from "react";

const Topbanner = ({setSearchQuery, setPerformSearch, callSearchApi}) => {
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
      <View style={styles.searchContainer}>
        <View style={styles.searchWrapper}>
          <TextInput
            style={styles.searchInput}
            value={searchTerm}
            onChangeText={(newText) => setSearchTerm(newText)} // no need to do e.target.value --> directly take as text
            placeholder="Search by Name or Customer Id"
          />
        </View>

        <TouchableOpacity style={styles.searchBtn} 
            onPress={()=>callSearchApi(searchTerm)}
        >
          <Image
            source={icons.search}
            resizeMode="contain"
            style={styles.searchBtnImage}
          />
        </TouchableOpacity>
      </View>
    </View>
  );
};

export default Topbanner;
