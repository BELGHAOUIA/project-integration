import apiClient from "./AxiosHeader";

export default {

     add(numeroSalle,a) {
      console.log(a)
      return apiClient.put(`/salle/actif/add?numeroSalle=${numeroSalle}`,a)
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     deleteFromSalle(numeroSalle,actifId) {
      return apiClient.put(`/salle/actif/delete?numeroSalle=${numeroSalle}&actifId=${actifId}`,)
        .then(response => {
          //  console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     fetchAll() {
      return apiClient.get("/actif/fetchAll")
        .then(response => {
            console.log("actif" + response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
}