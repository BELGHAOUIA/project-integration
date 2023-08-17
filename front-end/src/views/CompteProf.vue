<template>
  
  <div style="padding-top: 45px">
    <div class="login">
      <v-container>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="10">
            <v-card id="cardform" class="elevation-6 mt-10">
              <v-window v-model="step">
                <v-window-item :value="1">
                  <v-row>
                    <v-col class="leftside">
                      <v-card-text class="mt-12">
                        <h4 class="text-center">
                          Enregistrement d'un Enseignant
                        </h4>
                        <h6 class="text-center grey--text">
                          Connectez-vous pour bénéficier de différents services
                        </h6>
                        <v-row align="center" justify="center">
                          <v-col cols="12" sm="8">
                            <v-form>
                              <v-row>
                                <v-col cols="12" sm="6">
                                  <v-text-field
                                    prepend-inner-icon="mdi-account"
                                    v-model="userName"
                                    label="Nom"
                                    :rules="nameRules"
                                    required
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6">
                                  <v-text-field
                                    prepend-inner-icon="bi bi-envelope-fill"
                                    v-model="email"
                                    label="Email"
                                    :rules="emailRules"
                                    required
                                  ></v-text-field>
                                </v-col>
                                
                                  <v-text-field
                                  prepend-inner-icon="bi bi-mortarboard-fill"
                                    v-model="Grade"
                                    label="Grade"
                                    :rules="GradeRules"
                                    required
                                  ></v-text-field>
                                
                              </v-row>
                              <v-row>
                                <v-col cols="12" sm="6">
                                  <v-text-field
                                    prepend-inner-icon="bi bi-person-fill-lock"
                                    v-model="password"
                                    type="password"
                                    label="Mot de passe"
                                    :rules="passwordRules"
                                    required
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6">
                                  <v-text-field
                                    prepend-inner-icon="bi bi-person-fill-lock"
                                    v-model="confirmerMdp"
                                    type="password"
                                    label="Confirmer le mot de passe"
                                    :rules="confirmerMdpRules"
                                    required
                                  ></v-text-field>
                                </v-col>
                              </v-row>
                              <v-row>
                                <v-col cols="12">
                                  <button
                                    style="
                                      display: flex;
                                      align-items: center;
                                      justify-content: center;
                                    "
                                    class="button-23"
                                    type=""
                                    role="button"
                                    @click.prevent="registerProf"
                                  >
                                    <span style="margin-right: 5px"
                                      >Enregistrer</span
                                    >
                                    <i class="bi bi-save"></i>
                                  </button>
                                </v-col>
                              </v-row>
                            </v-form>
                          </v-col>
                        </v-row>
                      </v-card-text>
                    </v-col>
                  </v-row>
                </v-window-item>
              </v-window>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </div>
    <v-snackbar
        v-model="snackbar"
      >
        {{ message }}
  
        <template v-slot:action="{ attrs }">
          <v-btn
            :color="color"
            text
            v-bind="attrs"
            @click="snackbar = false"
          >
            Close
          </v-btn>
        </template>
      </v-snackbar>
  </div>
</template>

<script>
import UserServices from "@/services/UserServices";
export default {
  name: "CompteProf",
  data() {
    return {
      loading: false,
      message: "",
      step: 1,
      show1: false,
      valid: false,
      userName: "",
      email: "",
      password: "",
      confirmerMdp: "",
      Grade:"",
      nameRules: [
        (value) => {
          if (value) return true;

          return "Le nom est requis.";
        }
      ],

      emailRules: [
        (value) => {
          if (value) return true;

          return "E-mail est requis.";
        },
        (value) => {
          if (/.+@.+\..+/.test(value)) return true;

          return "L'email doit être valide.";
        },
      ],
      GradeRules: [
        (value) => {
          if (value) return true;

          return "La Grade est requis.";
        },
      ],

      passwordRules: [
        (value) => {
          if (value) return true;

          return "Le mot de passe est requis.";
        },
        (value) => {
          if (value?.length >= 8) return true;

          return "Le mot de passe doit comporter plus de 8 caractères.";
        },
      ],

      confirmerMdpRules: [
        (value) => {
          if (value) return true;

          return "Vous devez confirmé le mot de passe.";
        },
        (value) => {
          if (value === this.password) return true;

          return "Le mot de passe est invalide.";
        },
      ],
      snackbar: false
    };
  },
  props: {
    source: String,
  },
  methods: {
    registerProf() {
      this.message = "Parametre erronee"
      if (this.email && this.password && this.userName && this.password == this.confirmerMdp) {
        UserServices.registerProf({
          userName: this.userName,
          email: this.email,
          password: this.password,
        })
          .then((response) => {
            console.log(response);
            this.message = response.message;
          })
          .catch((error) => {
            this.message = "Echec d'enregistrement";
            console.log(error);
          });
      }
      this.snackbar = true
      
      this.email = "";
      this.password = "";
      this.userName = "";
      this.Grade = "";
      this.confirmerMdp = "";
    },
  },
};
</script>

<style scoped>
.login {
  background: url("../assets/loginBackground.jpg") no-repeat;
  background-size: cover;
  width: 90%;
  height: 700px;
  padding-top: 65px;
  margin: auto;
}

#cardform {
  background-color: white;
  width: 586px;
  height: 441px;
}
.leftside {
  background-color: rgba(255, 255, 255, 1);
}

.custom-button {
  border-radius: 24px;
  background-color: #007aff;
  color: #fff;
}
#cardform {
  animation-name: slide-in-left;
  animation-duration: 1.5s;
}

@keyframes slide-in-left {
  from {
    transform: translateX(-100%);
  }
  to {
    transform: translateX(0%);
  }
}

.button-23 {
  background-color: #ffffff;
  border: 1px solid #222222;
  border-radius: 8px;
  box-sizing: border-box;
  color: #222222;
  cursor: pointer;
  display: inline-block;
  font-family: Circular, -apple-system, BlinkMacSystemFont, Roboto,
    "Helvetica Neue", sans-serif;
  font-size: 16px;
  font-weight: 600;
  line-height: 20px;
  width: 80px;
  margin-right: 10px;

  outline: none;
  padding-left: 150px;
  padding: 13px 203px;
  position: relative;
  text-align: center;
  text-decoration: none;
  touch-action: manipulation;
  transition: box-shadow 0.2s, -ms-transform 0.1s, -webkit-transform 0.1s,
    transform 0.1s;
  user-select: none;
  -webkit-user-select: none;
}

.button-23:focus-visible {
  box-shadow: #222222 0 0 0 2px, rgba(255, 255, 255, 0.8) 0 0 0 4px;
  transition: box-shadow 0.2s;
}

.button-23:active {
  background-color: #f7f7f7;
  border-color: #000000;
  transform: scale(0.96);
}

.button-23:disabled {
  border-color: #dddddd;
  color: #dddddd;
  cursor: not-allowed;
  opacity: 1;
}

.mdpoublié {
  color: #008080;
}

/*hedhi bech tbadel couleur l'input wel icone wakt yenzel, fi 3oudh bleu hatit eloun lakhdher ema ma mchetech*/
.v-text-field--outlined:focus {
  border-color: #008080;
}

.v-icon--active {
  color: #008080;
}
.formulaire {
  margin-bottom: 80%;
}
#cardform {
  margin-left: 10px;
}
/* .navbarrr{
display:none; } */
</style>
