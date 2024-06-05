import { useState, useEffect } from "react";
import axios from "axios";
import { BASE_URL } from "../constants";

const useAccount = (accountId) => {
    const [data,setData] = useState([]);
    const [isLoading,setIsLoading] = useState(false)
    const [error,setError]= useState(null)
    
    const options = {
        method: 'GET',
        url: BASE_URL+'/accounts/app/fetchAccount/'+accountId,
      };

      const fetchData = async () => {
        setIsLoading(true)

        try{
          const response = await axios.request(options);
          
          setData(response.data.data)
          setIsLoading(false)
        }catch(error){
          console.log(error);
          setError(error)
          alert('There is an error')
        }finally{
          setIsLoading(false)
        }
      }

      useEffect( () => {

        fetchData();
      },[])

      const refetch = () => {
        setIsLoading(true);
        fetchData();
      }
      
      return {data, isLoading, error, refetch}
}

export default useAccount;