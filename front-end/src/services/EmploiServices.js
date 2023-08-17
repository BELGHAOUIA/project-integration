import apiClient from "./AxiosHeader";

export default {
    fetchAll(numeroSalle) {
        return apiClient.get(`/emploi/fetchAllByNumeroSalle?numeroSalle=${numeroSalle}`)
          .then(response => {
              //console.log(response.data);
              return response.data
          })
          .catch(error => {
              console.log(error);
          });
      },
    update(emploi,numeroSalle) {
        return apiClient.put(`/emploi/update?numeroSalle=${numeroSalle}`,emploi)
          .then(response => {
              //console.log(response.data);
              return response.data
          })
          .catch(error => {
              console.log(error);
          });
      },
  
}