import { useState, useEffect } from "react";
import axios from "axios";
import { BASE_URL } from "../constants";

const useTransaction = (txnId) => {
  
 
    const [data,setData] = useState([]);
    const [isLoading,setIsLoading] = useState(false)
    const [error,setError]= useState(null)
    const [s3PdfUrl,setS3PdfUrl] = useState("https://www.google.com/")
    
    const options = {
        method: 'GET',
        url: BASE_URL+'/transaction/findByTransactionId/'+txnId,
      };

      const options2 = {
        method: 'GET',
        url: BASE_URL+'/transaction/generateAndUploadPDF/'+txnId,
      };

      const fetchData = async () => {
        setIsLoading(true)

        try{
          console.log(`Txn id data fetch ${JSON.stringify(txnId)}`)
          const response = await axios.request(options);
          console.log(response.data.data);
          const response2 = await axios.request(options2);
          console.log(response2.data+" For retrieving pdf")
          setS3PdfUrl(response2.data);
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
      
      return {data, isLoading, error,s3PdfUrl, refetch}
}

export default useTransaction;
