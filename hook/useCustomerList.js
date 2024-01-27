import { useState, useEffect } from "react";
import axios from "axios";
import { BASE_URL } from "../constants";

const useCustomerList = (agentId) => {
    const [data,setData] = useState([]);
    const [isLoading,setIsLoading] = useState(false)
    const [error,setError]= useState(null)
    
   
    const url = BASE_URL+'/agent/getCustomers';
        
    const fetchData = async () => {
        setIsLoading(true)

        try{
        
            const response = await axios.get(url, {
                params: {
                  agentId:agentId
                },
              });
              console.log('i am here')
        console.log(JSON.stringify(response.data.data));
          setData(response.data.data)
          setIsLoading(false)
        }catch(error){
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
      
      return {data, isLoading, error, refetch,setData}
}

export default useCustomerList;