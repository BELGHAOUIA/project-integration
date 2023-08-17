





<template>
  <nav>
    <v-app-bar class="nav" app color="grayscale" elevate-on-scroll>
      <RouterLink to="/" style="text-decoration: none">
        <img
          src="../assets/Capture_d_écran_2023-03-30_183946-removebg-preview (2).png"
          style="height: 50px; padding-right: 0px"
        />
      </RouterLink>

      <RouterLink to="/" style="text-decoration: none">
        <RouterLink to="/" style="text-decoration: none"
          ><button class="button-26"></button>
        </RouterLink>
      </RouterLink>

      <v-spacer></v-spacer>

      <RouterLink v-if="role!='TECHNICIEN'" to="../ServicePage" style="text-decoration: none">
        <v-btn text href="#" class="menu-item"
          ><i class="bi bi-question-diamond"></i>Services</v-btn
        ></RouterLink
      >

      <RouterLink v-if="role=='TECHNICIEN'" to="../StockVue" style="text-decoration: none">
        <v-btn text href="#" class="menu-item">
          <i class="bi bi-pc"></i>Stock</v-btn
        ></RouterLink
      >
      <RouterLink to="../Demande" style="text-decoration: none">
        <v-btn text href="#" class="menu-item"
          ><i class="bi bi-archive"></i>Demandes</v-btn
        ></RouterLink
      >
      <div v-if="role!='ETUDIANT'" style="display: grid; grid-template-columns: 1fr;padding-right: 25px;">
        <RouterLink
          to="/PremiereEtage"
          style="text-decoration: none; color: black"
          ><i class="bi bi-1-square"></i>
        </RouterLink>
        <RouterLink to="/AcceilVue" style="text-decoration: none; color: black"
          ><i class="bi bi-0-square"></i>
        </RouterLink>
        








      </div>
      
      <div style="padding-right: 25px;">
        

 

      <v-menu offset-y>
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on">
            <v-badge color="green" :content="countNotification">
              <v-icon>mdi-bell</v-icon>
            </v-badge>
          </v-btn>
        </template>
        <v-card class="notification-card">
          <v-list>
            <v-subheader>Notifications</v-subheader>
            <v-list-item v-for="(notification, index) in notifications" :key="index">
              <v-list-item-avatar>
                <v-icon color="green">mdi-bell</v-icon>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title :title="notification.message">{{ notification.message }}</v-list-item-title>
                <v-list-item-subtitle>{{ timeAgo(notification.dateOpened) }}</v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-action>
                <v-btn icon small @click="removeNotification(notification.id)">
                  <v-icon>mdi-close</v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </v-list>
          <!-- <v-divider></v-divider> -->
          <v-card-actions >
            <v-spacer></v-spacer>
            <v-btn text small @click="clearNotifications()">Clear All</v-btn>
          </v-card-actions>
        </v-card>
      </v-menu>





        </div>

      <div :title="userName">
        <RouterLink to="/PageProfil" style="text-decoration: none; color: black"
          >
<v-list-item-avatar style="margin-right:-5px;" >
  <img v-if="dataUrl" :src="dataUrl" alt="">
  <img v-else src="../assets/gg.jpg" alt="">
</v-list-item-avatar> 
</RouterLink>
        </div>


        
    </v-app-bar>
  </nav>
</template>

<script>
import NotificationServices from '@/services/NotificationServices';
import UserServices from '@/services/UserServices';
export default {
  name: "NavBar",
  data: () => ({
    drawer: true,
    group: null,
    notifications: [],
    countNotification: 0,
    dataUrl: ''

  }),
  computed: {
    role() {
      return this.$store.state.auth.status.role
    },
    userName() {
      return this.$store.state.auth.status.userName
    },
    email() {
      return this.$store.state.auth.status.email
    },
    timeAgo() {
      return function(dateString) {
        const date = new Date(dateString);
        const now = new Date();
        const diff = now.getTime() - date.getTime();
        const seconds = diff / 1000;
        const minutes = seconds / 60;
        const hours = minutes / 60;
        const days = hours / 24;
        if (days >= 1) {
          return `${Math.floor(days)} jours`;
        } else if (hours >= 1) {
          return `${Math.floor(hours)} heures`;
        } else if (minutes >= 1) {
          return `${Math.floor(minutes)} minutes`;
        } else {
          return `il y a moins d'une minute`;
        }
      }
    }
  }
,
  methods: {
    async start() {

      UserServices.getProfilePicture()
            .then(response => {
            console.log(response.data);
            const blob = new Blob([response]);
            const objectUrl = URL.createObjectURL(blob);
            this.dataUrl = objectUrl;
            objectUrl.revokeObjectURL
            })
            .catch(error => {
            console.log(error);
            });

      this.notifications = await NotificationServices.fetchAllByUser();
       
      NotificationServices.countByUser()
        .then(response => {
          console.log("countNotification" + JSON.stringify(response));
          this.countNotification = response
        })
        .catch(error => {
          console.log(error);
        });
      },
     async removeNotification(id) {
      if(await NotificationServices.delete(id)) {
          this.start();

      }

    },
     async clearNotifications() {
      if(await NotificationServices.deleteAll()) {
          this.start();
      }
    }
  },
  created() {
    this.start();
  }
};
</script>

<style>

.logo {
  font-size: 24px;
  font-weight: bold;
  margin-right: 20px;
}

.menu-item {
  font-size: 16px;
  margin-right: 20px;
  text-transform: uppercase;
}

.notification-card {
  max-height: 300px;
  /* overflow-y: auto; */
  z-index: 9999
}


.notification-card {
  max-width: 300px;
  max-height: 300px;
}

.notification-card v-list-item {
  cursor: pointer;
}

.notification-card v-list-item:hover {
  background-color: #f5f5f5;
}

.notification-card v-divider {
  margin-top: 10px;
  margin-bottom: 10px;
}

.notification-card v-btn {
  color: #777777;
}

.notification-card v-btn:hover {
  color: #333333;
}

.notification-card v-icon {
  font-size: 20px;
}

.notification-card v-list-item-avatar {
  background-color: #d8f3dc;
  border-radius: 50%;
  height: 36px;
  width: 36px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.notification-card v-list-item-content {
  padding-left: 10px;
}

</style>























