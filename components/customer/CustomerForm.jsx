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
  ActivityIndicator,
  Modal
} from "react-native";
import styles from "./customer.styles";
import { COLORS, images } from "../../constants";
import { useState } from "react";

import { useRouter } from 'expo-router'

const CustomerForm = () => {

  const router = useRouter();

  const [name,setName] = useState(null);
  const [phone,setPhone] = useState(null);
  const [address,setAddress] =useState(null);
  const [aadhaar,setAadhaar] = useState(null);

  const [isLoading,setIsLoading] = useState(false);
  const [error,setError] = useState(null);

  const [isModalVisible, setIsModalVisible] = useState(false);

  const formData = {
    name:name,
    phone:phone,
    address:address,
    aadhaar:aadhaar,
  }

  const handleAdd = () => {
    console.log(formData);
    let fieldValidation = true;
    if(!formData.name || formData.name.length<1){
      fieldValidation=false;
      alert('Please enter name')
    }
    if(!formData.phone || formData.phone.length!==10){
      fieldValidation=false;
      alert('Please enter valid Phone number')
    }
    if(!formData.address || formData.address.length<3){
      fieldValidation=false;
      alert('Please enter valid address')
    }
    if(!formData.aadhaar || formData.aadhaar.length!=12){
      fieldValidation=false;
      alert('Please enter valid aadhaar')
    }
    if(fieldValidation){
      console.log('all data feilds validated');
      setIsModalVisible(true);
    }else{
      console.log('data fields not validated')
    }
      
  }


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
        <TextInput style={styles.nameInput} placeholder="Enter your name" onChangeText={(text)=> setName(text) } />
      </View>
      <View style={styles.inputWrapper}>
        <Text style={styles.inputLabel}>Phone No</Text>
        <TextInput
          style={styles.nameInput}
          placeholder="Enter your Phone number"
          onChangeText={(text)=> setPhone(text)}
        />
      </View>
      <View style={styles.inputWrapper}>
        <Text style={styles.inputLabel}>Address</Text>
        <TextInput style={styles.nameInput} placeholder="Enter your address" onChangeText={(text)=> setAddress(text)} />
      </View>
      <View style={styles.splWrapper}>
        <View style={styles.aadhaarWrapper}>
          <Text style={styles.inputLabel}>Aadhaar Number</Text>
          <TextInput
            style={styles.nameInput}
            placeholder="Enter Aadhaar number"
            onChangeText={(text)=> setAadhaar(text)}
          />
        </View>
        <View style={styles.uploadWrapper}>
          <Text style={styles.uploadLabel}> Upload </Text>
          <TouchableOpacity >
          <Image source={images.addMoney} resizeMode="contain" style={styles.btn}/>
          </TouchableOpacity>
        </View>
      </View>
          <TouchableOpacity style={styles.addWrapper} onPress={handleAdd}>
              <Text  style={styles.addBtn}>Add</Text>
          </TouchableOpacity>

          {/* modal */}
          <Modal 
            visible={isModalVisible}
            animationType="slide"
            presentationStyle="pageSheet"
            onRequestClose={()=> setIsModalVisible(false)}
          >
            <View style={styles.modalView}>
                <View style={styles.tickWrapper}>
                    <Image 
                      source={images.greentick}
                      resizeMode="contain"
                      style={styles.tick}
                    />
                </View>
                <Text style={styles.heading}>Customer Added!</Text>
                <Text style={styles.customerId}>Customer ID: C104 </Text>
                <Text style={styles.para}>Customer is added successfully to the system. You can proceed ahead to Add pigmi amount to the customer on the Home screen</Text>
                <TouchableOpacity 
                  onPress={()=>{
                    
                    setIsModalVisible(false);
                    router.push('/')
                  }}
                  style={styles.btnWrapper}
                >
                  <Text style={styles.done}>Done</Text>
                </TouchableOpacity>
            </View>
          </Modal>  
    </View>

       </TouchableWithoutFeedback>
    </KeyboardAvoidingView>
  );
};

export default CustomerForm;
