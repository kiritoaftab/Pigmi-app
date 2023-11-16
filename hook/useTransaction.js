import { useState, useEffect } from "react";
import axios from "axios";
import { BASE_URL } from "../constants";

const useTransaction = (txnId) => {
  
  console.log(`Txn id data fetch ${JSON.stringify(txnId)}`)
    const [data,setData] = useState([]);
    const [isLoading,setIsLoading] = useState(false)
    const [error,setError]= useState(null)
    
    const options = {
        method: 'GET',
        url: BASE_URL+'/transaction/findByTransactionId/'+txnId,
      };

      const fetchData = async () => {
        setIsLoading(true)

        try{
          const response = await axios.request(options);
          console.log(response.data.data)
          setData(response.data.data)
          setIsLoading(false)
        }catch(error){
          setError(error)
          alert('There is an error')
          console.log(error)
        }finally{
          setIsLoading(false)
        }
      }

      useEffect(() => {
        if(txnId)
          fetchData();
    }, []);

      const refetch = () => {
        setIsLoading(true);
        fetchData();
      }
      
      return {data, isLoading, error, refetch}
}

export default useTransaction;
