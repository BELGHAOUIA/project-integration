import apiClient from "./AxiosHeader";

export default {

     add(numeroSalle,a) {
      return apiClient.put(`/salle/logiciel/add?numeroSalle=${numeroSalle}`,a)
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },

     putLogo(nom, file) {
        return apiClient.put(`/logiciel/logo/put?nom=${nom}`, file).then((response) => {
           console.log(response.data);
          return response.data;
        })
        .catch(error => {
            console.log(error);
        });
      },
    
  
     fetchImage(id) {
      return apiClient.get(`/logiciel/photo/fetch?id=${id}`, { responseType: 'blob' })
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },

     deleteFromSalle(numeroSalle,logicielId) {
      return apiClient.put(`/salle/logiciel/delete?numeroSalle=${numeroSalle}&logicielId=${logicielId}`,)
        .then(response => {
          //  console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
}