<template>
  <v-app id="inspire">
    <NavBar v-if="loggedIn" />
    <nav class="navbarrr" v-if="this.$route.name !== 'Login'"></nav>
    <v-main>
      <v-container fluid>
        <router-view />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import NavBar from "./components/NavBar.vue";

export default {
  name: "App",
  components: { NavBar },
  data: () => ({
    // countNotification: null
  }),
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
//   methods: {

//     }
// ,
//   created() {
//     if (!this.loggedIn && this.$route.query.token == null) {
//       this.$router.push("/loginForm");
//     }
//   },
};
</script>

<style scoped>
navBar {
  z-index: 9999;
}
</style>

