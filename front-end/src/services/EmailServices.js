import apiClient from "./AxiosHeader";

export default {

     sendEmail(to,subject,text) {
      return apiClient.post(`/email?ticketId=${to}&subject=${subject}&text=${text}`)
        .then(response => {
            console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
}