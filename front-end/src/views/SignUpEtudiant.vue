<template>
  <div style="padding-top: 45px;">
    <v-container class="signup">
      <v-row align="center" justify="center">
        <v-col cols="12" sm="10">
          <v-card id="cardform" class="elevation-6 mt-10">
            <v-alert type="info" v-if="messageVerification.success">
              Merci de vous être inscrit sur notre site ! Pour commencer à utiliser toutes les fonctionnalités de notre site, veuillez vérifier votre adresse e-mail en cliquant sur le lien que nous venons de vous envoyer. Si vous n'avez pas reçu l'e-mail, veuillez vérifier votre dossier de courrier indésirable ou cliquer sur le bouton ci-dessous pour renvoyer l'e-mail de vérification.
            </v-alert>
            <div v-if="messageVerification.success" align="center">
              <v-btn style="display: flex; align-items: center; justify-content: center;" class="button-23" type="" role="button" @click.prevent="resendEmail">
                <v-icon left>mdi-email</v-icon>Renvoyer l'email
              </v-btn>
            </div>
            <v-window v-model="step" v-if="!messageVerification.success">
              <v-window-item :value="1">
                <v-row>
                  <v-col cols="12" md="12">
                    <v-card-text class="mt-12">
                      <h4 class="text-center">Formulaire d'inscription</h4>
                      <h6 class="text-center grey--text">Chers étudiants, en vous inscrivant sur cette plateforme,<br> vous aurez la possibilité de bénéficier de différents services <br> tels que la demande d'un logiciel, l'annonce d'une perte..</h6>
                      <v-row align="center" justify="center">
                        <v-col cols="12" sm="8">
                          <v-row>
                            <v-col cols="12" sm="6">
                              <v-text-field v-model="lastName" :rules="[v => !!v || 'Le nom est requis']" label="Nom" prepend-inner-icon="mdi-account" autocomplete="false" />
                            </v-col>
                            <v-col cols="12" sm="6">
                              <v-text-field v-model="firstName" :rules="[v => !!v || 'Le prénom est requis']" label="Prénom" prepend-inner-icon="mdi-account" autocomplete="false" />
                            </v-col>
                          </v-row>
                          <v-text-field v-model="user.email" :rules="[v => !!v || 'L\'adresse e-mail est requise', v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'L\'adresse e-mail n\'est pas valide']" label="Adresse Email" prepend-inner-icon="mdi-email" autocomplete="false" />
                          <v-text-field v-model="user.password" :rules="[v => !!v || 'Le mot de passe est requis', v => v.length >= 6 || 'Le mot de passe doit contenir au moins 6 caractères']" label="Mot de passe" prepend-inner-icon="mdi-lock" autocomplete="false" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :type="show1 ? 'text' : 'password'" @click:append="show1 = !show1" />
                          <v-text-field v-model="confirmPassword" :rules="[v => !!v || 'La confirmation du mot de passe est requise', v => v === user.password || 'Les mots de passe ne correspondent pas']" label="Confirmer votre mot de passe" prepend-inner-icon="mdi-lock" autocomplete="false" :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'" :type="show2 ? 'text' : 'password'" @click:append="show2 = !show2" />
                          <div align="center">
                            <v-btn style="display: flex; align-items: center; justify-content: center;" class="button-23" type="" role="button" @click.prevent="signUpEtudiant">
                              <v-icon left>mdi-account-plus</v-icon>S'inscrire
                            </v-btn>
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
import User from "../models/user";
import RegisterEtudiant from "@/services/auth.service"
export default {
    data : () => ({
        step : 1,
        show1: false,
        show2: false,
        user: new User("", "",""),
        message: '',
        confirmPassword: '',
        email: '',
        messageVerification: false
    }),
    name: 'SignUpEtudiant',
    props: {
        source: String
    },
    methods: {
      async signUpEtudiant() {
        this.user.username = this.lastName + " " + this.firstName
        if (this.user.email && this.user.password && this.user.username
        && this.confirmPassword == this.user.password) {
        await RegisterEtudiant.registerEtudiant(this.user)
        .then((response) => {
            console.log(response);
            this.message = response;
          })
          .catch((error) => {
            console.log(error);
          });

          if(this.message.success) {
            this.email = this.user.email
            RegisterEtudiant.createToken(this.user.email)
            .then((response) => {
            console.log(response);
            this.messageVerification = response;
          })
          .catch((error) => {
            console.log(error);
          });
          }
        }
        this.lastName = "" 
         this.firstName= "" 
        this.user = new User("","","")
        this.confirmPassword = ""
      },
      resendEmail() {
            RegisterEtudiant.createToken(this.email)
            .then((response) => {
            console.log(response);
            this.messageVerification = response;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    }
}

</script>

<style scoped>

.signup {
  background: url('../assets/loginBackground.jpg') no-repeat center center fixed;
  background-size: cover;
width: 1700px;
height: 650px;
padding-top: 65px;
}

#cardform {
  background-color: white;
  width:586px;
  height:500px;
}
.leftside{
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
  background-color: #FFFFFF;
  border: 1px solid #222222;
  border-radius: 8px;
  box-sizing: border-box;
  color: #222222;
  cursor: pointer;
  display: inline-block;
  font-family: Circular,-apple-system,BlinkMacSystemFont,Roboto,"Helvetica Neue",sans-serif;
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
  transition: box-shadow .2s,-ms-transform .1s,-webkit-transform .1s,transform .1s;
  user-select: none;
  -webkit-user-select: none;

}

.button-23:focus-visible {
  box-shadow: #222222 0 0 0 2px, rgba(255, 255, 255, 0.8) 0 0 0 4px;
  transition: box-shadow .2s;
}

.button-23:active {
  background-color: #F7F7F7;
  border-color: #000000;
  transform: scale(.96);
}

.button-23:disabled {
  border-color: #DDDDDD;
  color: #DDDDDD;
  cursor: not-allowed;
  opacity: 1;
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
