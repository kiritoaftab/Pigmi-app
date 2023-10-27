import {View, Text, ScrollView, SafeAreaView} from 'react-native';

import { useState } from "react";
import { Stack, useRouter } from "expo-router";

import { COLORS, icons, images, SIZES } from "../constants";

import {Topbanner, AddCustomer, AddPigmi} from '../components'

const Home = () => {

    return(
        <View>
            <Text>This is Login page</Text>
        </View>
    )
}

export default Home;