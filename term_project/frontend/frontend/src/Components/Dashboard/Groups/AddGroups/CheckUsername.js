import api from "../../../../baseUrl"
import authHeader from "../../../../Services/DataService";

const CheckUsername = async (username)=>{
   try{

       const response = await api.get(`/check-username/${username}`,{
           headers: authHeader()
       });

    const isAvailable = response.data;
    if(isAvailable === 1){
        return true;
    }

    return false;
}catch ( error ){
       return false;
   }}

export default CheckUsername;