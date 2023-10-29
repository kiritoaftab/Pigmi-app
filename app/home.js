import {
  View,
  Text,
  ScrollView,
  SafeAreaView,
  TouchableOpacity,
  TextInput
} from "react-native";

import { useState } from "react";
import { Stack, useRouter } from "expo-router";

import { COLORS, icons, images, SIZES } from "../constants";

import { Topbanner, AddCustomer, AddPigmi, TopTxnBanner , LoginForm } from "../components";

const [isLoading,setIsLoading] = useState(false);

const Home = () => {
  return (
    <SafeAreaView style={{ flex: 1, backgroundColor: COLORS.gray2 }}>
      <Stack.Screen
        options={{
          headerStyle: { backgroundColor: COLORS.green },
          headerShadowVisible: false,
          headerTitle: "",
          
        }}
      />
      <ScrollView showsVerticalScrollIndicator={false}>
        <View
          style={{
            flex: 1,
            padding: SIZES.medium,
            backgroundColor: COLORS.green,

            borderBottomLeftRadius: 30,
            borderBottomRightRadius: 30,
          }}
        >
          <TopTxnBanner />
        </View>
            <LoginForm/>
      </ScrollView>
    </SafeAreaView>
  );
};

export default Home;
