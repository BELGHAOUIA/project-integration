<template>
  <div>
    <div class="d-flex justify-center align-center flex-column ">
            <img
          src="../assets/Capture_d_écran_2023-03-30_183946-removebg-preview (2).png"
          style="height: 50px; padding-right: 0px"
       
        />
            <h1 class="text-center mb-4">Bienvenue au support informatique</h1>
            
          </div>
    <v-container fluid >
      <video class="background-video" autoplay loop muted>
      <source src="../assets/CoVideo.mp4" type="video/mp4">
 
    </video>
      <v-row justify="center">
        <v-col cols="12" >
       
          <div style="padding-top:450px ;">
            <h2 class="text-center mb-4">Nos services</h2>
            <div class="d-flex justify-space-around flex-wrap">
              <v-card class="mx-2 my-4 pa-5" max-width="350">
                <v-icon>mdi-magnify-scan</v-icon>
                <v-card-title class="text-center">Objets perdus et trouvés</v-card-title>
                <v-card-text class="text-center">Si vous avez perdu quelque chose sur le campus, notre équipe peut vous aider à le retrouver.</v-card-text>
              </v-card>
              <v-card class="mx-2 my-4 pa-5" max-width="350">
                <v-icon>mdi-application</v-icon>
                <v-card-title class="text-center">Problèmes de logiciel</v-card-title>
                <v-card-text class="text-center">Si vous avez des problèmes avec le logiciel sur votre ordinateur ou votre appareil, nous pouvons vous aider à résoudre le problème.</v-card-text>
              </v-card>
              <v-card class="mx-2 my-4 pa-5" max-width="350">
                <v-icon>mdi-desktop-classic</v-icon>
                <v-card-title class="text-center">Problèmes de matériel</v-card-title>
                <v-card-text class="text-center">Si vous rencontrez des problèmes matériels avec votre ordinateur ou votre appareil, tels qu'un écran cassé ou un clavier défectueux, nous pouvons vous aider à diagnostiquer et à réparer le problème.</v-card-text>
              </v-card>
            </div>
            <p class="text-center">Notre équipe est dédiée à aider les enseignants et les étudiants avec tous les problèmes techniques qu'ils peuvent rencontrer lors de l'utilisation des ressources universitaires. Veuillez sélectionner parmi les options suivantes :</p>
            <div class="d-flex justify-center">
              
            </div>
          </div>
        </v-col>
      </v-row>
      <div>

      </div>
  </v-container>
</div>
</template>

<script>
export default {
  name: 'HomePage'
}
</script>
<style>
.background-video {
  padding-top: 0px;
  padding-left: 350px;
  position:fixed;
 
  width: 80%;
  /* height: 50%; */
   z-index: 0;
  object-fit: cover;
}
</style>