import apiClient from "./AxiosHeader";

export default {
     delete(id) {
      return apiClient.delete(`/notification/delete?id=${id}`)
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     deleteAll() {
      return apiClient.delete(`/notification/deleteAll`)
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     fetchAllByUser() {
      return apiClient.get("/notification/fetchAllByUser")
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     countByUser() {
      return apiClient.get("/notification/countByUser")
        .then(response => {
            console.log("t3adet requette");
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
}