<template>
  <v-card>
    <v-card-title>
      <v-text-field v-model="search" append-icon="mdi-magnify" label="Search" single-line hide-details></v-text-field>
    </v-card-title>
    <v-data-table :headers="headers" :items="actifs" :search="search"></v-data-table>
  </v-card>
</template>
<script>
import ActifServices from '@/services/ActifServices';
export default {
  data() {
    return {
      search: '',
      headers: [
        {
          text: 'Type',
          align: 'start',
          filterable: false,
          value: 'type',
        },
        { text: 'Model', value: 'modele' },
        { text: 'Reference', value: 'reference' },
        { text: 'Etat', value: 'etat' },
        { text: 'Disponible', value: 'numeroSalle' },

      ],
      actifs: []
    }
  },
  methods : {
    async start() {
        await ActifServices.fetchAll()
          .then((response) => {
            //console.log(response);
            this.actifs = response;
          })
          .catch((error) => {
            console.log(error);
          });
    }
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
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