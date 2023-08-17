import apiClient from "./AxiosHeader";

export default {

     add(a) {
        console.log(a)
      return apiClient.post("/ticket/add",a)
        .then(response => {
           // console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     fetchPerte() {
      return apiClient.get("/ticket/perte/fetchAll")
        .then(response => {
           console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     fetchProbleme() {
      return apiClient.get("/ticket/probleme/fetchAll")
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     fetchAllByUser() {
      return apiClient.get("/ticket/ticketByUser/fetchAll")
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
    fetchAllAcceptedTickets() {
      return apiClient.get("/ticket/acceptedTickets/fetchAll")
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     
     updateStatus(id,status) {
      return apiClient.put(`/ticket/status/update?id=${id}&status=${status}`)
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     
}