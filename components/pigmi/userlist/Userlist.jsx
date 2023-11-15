import {View,Text, ScrollView} from 'react-native';
import {User} from '../../../components'
import {COLORS, SIZES} from '../../../constants'
import { useRouter } from 'expo-router';

const Userlist = (customerList) => {

    const router = useRouter();

    console.log('i am heree')
    console.log(customerList.customerList)
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
                customerList?.customerList?.map((user,index)=> (
                    <User
                        key={index}
                        user={user}
                        handleNavigate={()=> router.push(`/add_amount/${user?.id}`) }
                    />
                ))
            }
        </ScrollView>
    )
}

export default Userlist;
