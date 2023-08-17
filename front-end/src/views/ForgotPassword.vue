<template>
    <div style="padding-top: 45px">
      <v-container class="login">
        <v-row align="center" justify="center">
          <v-col cols="12" sm="10">
            <v-card id="cardform" class="elevation-6 mt-10">
              <v-alert v-if="message.success" type="success">
                {{ message.message }}
              </v-alert>
              <v-window v-model="step">
                <v-window-item :value="1">
                  <v-row>
                    <v-col class="leftside">
                      <v-card-text class="mt-12">
                        <h4 class="text-center">Réinitialiser votre mot de passe</h4>
                        <h6 class="text-center grey--text">
                          Entrer votre adresse email
                        </h6>
                        <v-row align="center" justify="center">
                          <v-col cols="12" sm="8">
                            <v-form>
                              <v-text-field
                              v-if="completeRegistration"
                              id="password"
                              prepend-inner-icon="mdi-lock"
                              name="password"
                              label="Mot de passe"
                              v-model="password"
                              :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                              :type="show1 ? 'text' : 'password'"
                              @click:append="show1 = !show1"
                            ></v-text-field>

                              <v-text-field
                              v-if="completeRegistration"
                              id="password"
                              prepend-inner-icon="mdi-lock"
                              name="password"
                              label="Confirmer Mot de passe"
                              v-model="confirmPassword"
                              :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                              :type="show1 ? 'text' : 'password'"
                              @click:append="show1 = !show1"
                            ></v-text-field>
                            
                            <v-text-field
                              v-else
                                prepend-inner-icon="mdi-account"
                                name="login"
                                label="Adresse Email"
                                type="text"
                                id="email"
                                v-model="email"
                              ></v-text-field>
                            </v-form>
                            <div align="center">
                              <v-btn
                              v-if="completeRegistration"
                                style="
                                  display: flex;
                                  align-items: center;
                                  justify-content: center;
                                "
                                class="button-23 mt-12"
                                type=""
                                role="button"
                                @click.prevent="resetPassword"
                                ><v-icon left>mdi-login</v-icon>
                                Réinitialiser</v-btn
                              >
                              <v-btn
                              v-else
                                style="
                                  display: flex;
                                  align-items: center;
                                  justify-content: center;
                                "
                                class="button-23 mt-12"
                                type=""
                                role="button"
                                @click.prevent="sendEmail"
                                ><v-icon left>mdi-login</v-icon>
                                Envoyer</v-btn
                              >
                            </div>
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
  </template>
  
  <script>
   import TokenValidation from '@/services/auth.service'
  export default {
    name: "ForgotPassword",
    data() {
      return {
        message: "",
        step: 1,
        password: "",
        confirmPassword: "",
        email: "",
        show1: false,
        completeRegistration: false
      };
    },
    props: {
      source: String,
    },
    methods: {
      sendEmail() {
        TokenValidation.createTokenForPasswordReset(this.email)
          .then((response) => {
            console.log(response);
            this.message= response
          })
          .catch((error) => {
            console.log(error);
          });

      },
      async resetPassword() {
       await TokenValidation.completePasswordReset(this.$route.query.token, this.password)
          .then((response) => {
            console.log(response);
            this.message= response
          })
          .catch((error) => {
            console.log(error);
          });
          if(this.message.success) {
            this.$router.push('/LoginForm')
          }
      },
      start() {
        // console.log(this.$route.query.token)
        // this.completeRegistration = true;
        if(this.$route.query.token!= null) {
          this.completeRegistration = true;
        }
      }
    },
    created() {
        this.start()
    },
   
  };
  </script>
  
  <style scoped>
  .login {
    background: url("../assets/loginBackground.jpg") no-repeat center center fixed;
    background-size: cover;
    width: 1700px;
    height: 650px;
    padding-top: 65px;
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
  /* .navbarrr{
  display:none; } */
  </style>
  