<template>
  <v-row  justify="space-around" style="display: grid; grid-template-columns: 1fr 1fr 1fr 1fr;"  >
    <v-col v-for="salle in salles" :key="salle.id">
        <v-card class="mx-auto card2" max-width="320"  data-aos="fade-up" data-aos-duration="1000" data-aos-offset="200"  >
          <v-img data-aos="
          ease-in-back" class="align-end text-white" height="200" src="../assets/1000_F_199308539_uNyXqfehxKppMaVkkNJVCHxavZlldxZN.jpg" cover>
          <v-card-title  style="color: grey;">{{ salle.numeroSalle }}</v-card-title>
        </v-img>
        
        <v-card-subtitle class="pt-4">
          <h4 style="color: black;"><i class="bi bi-pc-display"></i>
            Nombre de poste : {{ salle.nombrePoste }}</h4>
          </v-card-subtitle>
          
          <v-card-text style="padding-top:7px;">
            <div class="projecteur"><i class="bi bi-usb-micro-fill"></i>
            <h4>Projecteur :</h4>
            <h5 :class="{ disponible: salle.projecteur, indisponible: !salle.projecteur }">
              {{ salle.projecteur ? 'Disponible' : 'Indisponible' }}
            </h5>
          </div>
          
        </v-card-text>
        
        <div class="fin">
          <v-card-text>
            
            <h6>
              <i class="bi bi-wifi" style="display: inline-block;"></i> Wi-Fi : {{ salle.debit }} Mb/s
            </h6>

           
          </v-card-text>
          
          <v-card-actions style="text-align: center;">
            <router-link to="./PagePc"  style="text-decoration: none;" > 
              <v-btn style="text-decoration: none;" color="#c3c2cd" @click="setNumeroSalle(salle.numeroSalle)">
                Info <i class="bi bi-info-square-fill"></i>
              </v-btn>
            </router-link>
            
            
          </v-card-actions>
        </div>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import SalleServices from "./../services/SalleServices"
//import { mapActions } from "vuex";
export default {
  name: "PremiereEtage",
  data() {
    return {
      sallesF: []
    }
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
    salles() {
      return this.sallesF.filter(salle => {
        return salle.numeroSalle.toLowerCase().startsWith("i1");
        });
    }
  },
  methods: {
    async start() {
         await SalleServices.fetchAll()
        .then(response => {
          //console.log(response.data);
          this.sallesF = response
        })
        .catch(error => {
          console.log(error);
        });
      },
    setNumeroSalle(n) {
      //mapActions.setNumeroSalle(n);
      this.$store.commit('setNumeroSalle', n)
    }
  },

  created() {
   if (this.loggedIn) {
       this.start();
   }
    
    // else {
    //   this.$router.push('/loginForm');
    // }
  }
}
</script>

<style scoped>
/*
.card {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-gap: 50px;
}
*/
.projecteur {
  display: flex;
  align-items: center;
}

.projecteur h3 {
  margin-right: 10px;
  color: #000;
}

 .projecteur h3 {
  font-weight: bold;
  padding: 5px;
  border-radius: 5px;
} 

.disponible {
  color: rgb(166, 248, 166);

}

.indisponible {
  color: #ff6060;

}


.card2 {
  box-shadow: rgba(255, 255, 255, 0.0) 0px 19px 38px, rgba(255, 255, 255, 0) 0px 15px 12px;
  box-shadow: rgba(255, 255, 255, 0.2) 0px 0px 0px 1px inset, rgba(255, 255, 0, 0) 0px 0px 0px 1px;
}
.card2:hover {
  box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset;
  box-shadow: rgba(255, 255, 255, 0.2) 0px 0px 0px 1px inset, rgba(154, 154, 154, 0.9) 0px 0px 0px 1px;
}
</style>