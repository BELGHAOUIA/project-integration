import apiClient from "./AxiosHeader";

export default {

     fetchAll() {
      return apiClient.get('/salle/fetchAll')
        .then(response => {
            //console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },

     fetchProjecteur(numeroSalle) {
      return apiClient.get(`/salle/projecteur/fetch?numeroSalle=${numeroSalle}`)
        .then(response => {
            //console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },

     putProjecteur(numeroSalle,value) {
      return apiClient.put(`/salle/projecteur/update?numeroSalle=${numeroSalle}&value=${value}`)
        .then(response => {
            //console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },

     fetchAllLogiciels(numeroSalle) {
      return apiClient.get(`/salle/logiciel/fetchAll?numeroSalle=${numeroSalle}`)
        .then(response => {
            //console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    }
    ,

     fetchAllActifs(numeroSalle) {
      return apiClient.get(`/salle/actif/fetchAll?numeroSalle=${numeroSalle}`)
        .then(response => {
            //console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },

    putTablesNombres(numeroSalle,nombre) {
        return apiClient.put(`/salle/nombreTable/update?numeroSalle=${numeroSalle}&nombre=${nombre}`)
        .then(response => {
            //console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    }
    ,

    fetchTablesNombres(numeroSalle) {
        return apiClient.get(`/salle/nombreTable/fetch?numeroSalle=${numeroSalle}`)
        .then(response => {
            //console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    }
    ,

    generatePDF(numeroSalle) {
        return apiClient.get(`/pdf/generate?numeroSalle=${numeroSalle}`,{ responseType: 'arraybuffer' })
        .then(response => {
            //console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    }
}