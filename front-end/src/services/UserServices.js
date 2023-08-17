import apiClient from "./AxiosHeader";

export default {

  registerProf(user) {
    console.log(user);
    return apiClient.post('/user/register/prof',user)
    .then(response => {
      return response.data;
    });
  },
     fetchUserNames() {
      return apiClient.get("/user/userNames/fetchAll")
        .then(response => {
          //  console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     fetchUserDetails() {
      return apiClient.get("/user/userDetails/fetch")
        .then(response => {
           // console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
     setProfilePicture(file) {
      return apiClient.put("/user/profilePicture/put", file)
        .then(response => {
           // console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
    getProfilePicture() {
      return apiClient.get("/user/profilePicture/fetch", { responseType: 'blob' })
        .then(response => {
            // console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
    getTargetUserProfilePicture(email) {
      return apiClient.get(`/user/targetUser/profilePicture/fetch?email=${email}`, { responseType: 'blob' })
        .then(response => {
           // console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
    fetchAllProffessors() {
      return apiClient.get("/user/prof/userDetails/fetchAll")
        .then(response => {
            //console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
    deleteByEmail(email) {
      return apiClient.delete(`/user/prof/delete?email=${email}`)
        .then(response => {
            //console.log(response.data);
            return response.data
        })
        .catch(error => {
            console.log(error);
        });
    },
}