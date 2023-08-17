import axios from 'axios';

const API_URL = 'http://localhost:8088/user/';

class AuthService {
  login(user) {
    return axios
      .post(API_URL + 'authenticate', {
        email: user.email,
        password: user.password
      })
      .then(response => {
        if (response.data.jwt) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        console.log("jwt =" + response.data);
        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
  }

  registerEtudiant(user) {
    return axios.post(API_URL + 'register/etudiant', {
      userName: user.username,
      email: user.email,
      password: user.password
    })
    .then(response => {
      console.log(response.data);
      return response.data;
    });
  }

  createToken(email) {
    return axios.post(API_URL + `emailVerification/createToken?email=${email}`)
    .then(response => {
      console.log(response.data);
      return response.data;
    });
  }

  completeRegistration(token) {
    return axios.put(API_URL + `emailVerification/completeRegistration?token=${token}`)
    .then(response => {
      console.log(response.data);
      return response.data;
    });
  }

  createTokenForPasswordReset(email) {
    return axios.post(API_URL + `emailVerification/createTokenForResetPassword?email=${email}`)
    .then(response => {
      console.log(response.data);
      return response.data;
    });
  }

  completePasswordReset(token, newPassword) {
    return axios.put(API_URL + `emailVerification/resetPassword?token=${token}&newPassword=${newPassword}`)
    .then(response => {
      console.log(response.data);
      return response.data;
    });
  }


}

export default new AuthService();
