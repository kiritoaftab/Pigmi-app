import {
  View,
  Text,
  TextInput,
  Image,
  KeyboardAvoidingView,
  Platform,
  TouchableWithoutFeedback,
  Keyboard,
  TouchableOpacity,
} from "react-native";
import styles from "./customer.styles";
import { images } from "../../constants";

const CustomerForm = () => {
  return (
    <KeyboardAvoidingView 
    behavior={Platform.OS === "ios" ? "padding" : "height"}
    >
        <TouchableWithoutFeedback
            onPress={Keyboard.dismiss}
        >
    
    <View style={styles.container}>
      <View style={styles.inputWrapper}>
        <Text style={styles.inputLabel}>Name</Text>
        <TextInput style={styles.nameInput} placeholder="Enter your name" />
      </View>
      <View style={styles.inputWrapper}>
        <Text style={styles.inputLabel}>Phone No</Text>
        <TextInput
          style={styles.nameInput}
          placeholder="Enter your Phone number"
        />
      </View>
      <View style={styles.inputWrapper}>
        <Text style={styles.inputLabel}>Address</Text>
        <TextInput style={styles.nameInput} placeholder="Enter your address" />
      </View>
      <View style={styles.splWrapper}>
        <View style={styles.aadhaarWrapper}>
          <Text style={styles.inputLabel}>Aadhaar Number</Text>
          <TextInput
            style={styles.nameInput}
            placeholder="Enter Aadhaar number"
          />
        </View>
        <View style={styles.uploadWrapper}>
          <Text style={styles.uploadLabel}> Upload </Text>
          <TouchableOpacity >
          <Image source={images.addMoney} resizeMode="contain" style={styles.btn}/>
          </TouchableOpacity>
        </View>
      </View>

      
            <TouchableOpacity style={styles.addWrapper}>
                <Text  style={styles.addBtn}>Add</Text>
            </TouchableOpacity>
   
    </View>
    </TouchableWithoutFeedback>
    </KeyboardAvoidingView>
  );
};

export default CustomerForm;
