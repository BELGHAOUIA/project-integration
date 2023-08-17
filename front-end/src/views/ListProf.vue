<template>



<div><RouterLink  to="../CompteProf" style="text-decoration: none;margin-right: 7px;">
        <v-btn text href="#" class="button-16"
          ><i class="bi bi-people-fill"></i>Ajouter Prof </v-btn
        ></RouterLink
      >    
        <br>
        <br>
    <v-row justify="space-around" style="display: grid; grid-template-columns: 1fr 1fr 1fr 1fr;">
        
    
        <v-col v-for="prof in profs" :key="prof.id">
            <v-card class="mx-auto" max-width="344" height="150px" outlined>
                <v-list-item three-line>
                    <v-list-item-content>
                        <div class="text-overline mb-4">
                            {{ prof.userName }}
                        </div>
                        <v-list-item-title class="text-overline mb-1">
                            <p> {{ prof.email }} </p>
                        </v-list-item-title>
                        <v-list-item-subtitle></v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-avatar height="70px" width="70">
                      <img :src="prof.profilePicture" alt="none">

                    </v-list-item-avatar>
                </v-list-item>

                <v-card-actions>
                    <i class="bi bi-trash3-fill hand" @click="confirmSupp = true, email = prof.email"></i>
                </v-card-actions>
            </v-card>
        </v-col>
    </v-row>
    <v-dialog v-model="confirmSupp" max-width="400">
            <v-card>
              <v-card-title>Confirmation de suppression</v-card-title>
              <v-card-text>
                Voulez-vous vraiment supprimer cet prof ?
              </v-card-text>
              <v-card-actions>
                <v-btn
                  outlined
                  color="red"
                  :style="{ borderColor: 'red' }"
                  @click="deleteProf"
                  >Supprimer</v-btn
                >
                <v-btn
                  outlined
                  color="gray"
                  :style="{ borderColor: 'black' }"
                  @click="confirmSupp = false"
                  >Annuler</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
</div>
</template>
<script>
import UserServices from "./../services/UserServices"
export default {
    name: "ListProf",
    data() {
        return {
            profs: [],
            email:"",
            confirmSupp:false,
        }
    },
    methods: {
        async deleteProf() {
            await UserServices.deleteByEmail(this.email)
                .then(response => {
                    console.log(response);
                    this.confirmSupp = !response
                })
                .catch(error => {
                    console.log(error);
                });

                this.start();
        },
        async start() {
            await UserServices.fetchAllProffessors()
                .then(response => {
                    console.log(response.data);
                    this.profs = response
                })
                .catch(error => {
                    console.log(error);
                });

                this.profs.forEach((prof) => {
        let objectUrl;
        UserServices.getTargetUserProfilePicture(prof.email)
          .then((response) => {
            const blob = new Blob([response]);
            objectUrl = URL.createObjectURL(blob);
            prof.profilePicture = objectUrl;
          })
          .catch((error) => {
            console.log(error);
          });
      });
        },
    },

    created() {
            this.start();
    }

}
</script>