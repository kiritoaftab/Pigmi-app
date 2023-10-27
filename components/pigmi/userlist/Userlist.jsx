import {View,Text, ScrollView} from 'react-native';
import {User} from '../../../components'
import {COLORS, SIZES} from '../../../constants'
import { useRouter } from 'expo-router';

const Userlist = () => {

    const router = useRouter();

    const data = [
        {
            "custId":"C101",
            "name":"Aftab Ahmed",
        },
        {
            "custId":"C102",
            "name":"Vipul Jujar",
        },
        {
            "custId":"C103",
            "name":"Saif Badami",
        },
        {
            "custId":"C101",
            "name":"Aftab Ahmed",
        },
        {
            "custId":"C102",
            "name":"Vipul Jujar",
        },
        {
            "custId":"C101",
            "name":"Aftab Ahmed",
        },
        {
            "custId":"C102",
            "name":"Vipul Jujar",
        },
        {
            "custId":"C101",
            "name":"Aftab Ahmed",
        },
        {
            "custId":"C102",
            "name":"Vipul Jujar",
        },
    ]

    return (
        <ScrollView 
            showsVerticalScrollIndicator={false}
            style={{
                backgroundColor:COLORS.lightWhite,
                width:"90%",
                marginTop:-46,
                borderRadius:30,
                padding:SIZES.large,
                
            }}
        >
            {
                data?.map((user,index)=> (
                    <User
                        user={user}
                        handleNavigate={()=> router.push(`/add_amount/${user?.custId}`) }
                    />
                ))
            }
        </ScrollView>
    )
}

export default Userlist;
