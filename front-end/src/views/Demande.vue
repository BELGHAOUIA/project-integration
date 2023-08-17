<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card>
          <v-card-title>
            La liste des pertes <i class="fas fa-icone-ma-classe"></i>
            <v-spacer />
            <v-text-field
              v-model="search1"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
          </v-card-title>
          <v-data-table
            :headers="actifHeaders"
            :items="Perte"
            item-key="id"
            :search="search1"
          >
            <template v-slot:item="{ item }">
              <tr>
                <th>{{ item.userName }}</th>

                <th>{{ item.details }}</th>
                <th style="align-items: center">{{ item.numeroSalle }}</th>
            <th>{{ timeAgo(item.dateOpened) }}</th> 
                <th>
                  <button
                    style="
                      display: flex;
                      align-items: center;
                      justify-content: center;
                      width: 20px;
                    "
                    class=""
                    type=""
                    role="button"
                    @click="
                      (dialogPerte = true), (dialog = true), (id = item.id)
                    "
                  >
                    <span style="height: 50px; padding-top: 15px"
                      ><i class="bi bi-envelope-at"></i
                    ></span>
                  </button>
                </th>
              </tr>
            </template>
          </v-data-table>
        </v-card>
        <br />
        <div>
          <v-dialog v-model="dialog" max-width="400">
            <v-card>
              <v-card-text>
                <v-form>
                  <v-text-field v-model="objet" label="Objet"></v-text-field>
                  ce message est prive
                  <v-text-field
                    v-model="response"
                    label="Reponse"
                  ></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn
                  outlined
                  color="#008080"
                  :style="{ borderColor: 'black' }"
                  @click="envoyerEmail"
                  >Envoyer</v-btn
                >
                <v-btn
                  outlined
                  color="gray"
                  :style="{ borderColor: 'black' }"
                  @click="dialog = false"
                  >Annuler</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </div>
      </v-col>
      <v-col v-if="role=='TECHNICIEN'">
        <v-card>
          <v-card-title>
            La liste des problemes
            <v-spacer />
            <v-text-field
              v-model="search2"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
          </v-card-title>
          <v-data-table
            :headers="logicielHeaders"
            :items="Problemes"
            item-key="id"
            :search="search1"
          >
            <template v-slot:item="{ item }">
              <tr>
                <th>{{ item.userName }}</th>

                <th>{{ item.details }}</th>
                <th>{{ item.priority }}</th>

                <th>{{ item.status }}</th>
                <th>{{ timeAgo(item.dateOpened) }}</th>
                <th>
                  <button
                    style="
                      display: flex;
                      align-items: center;
                      justify-content: center;
                    "
                    class="button-23"
                    role="button"
                    @click="updateStatus(item.id)"
                  >
                    Accepter
                  </button>
                </th>
                <th>
                  <button
                    style="
                      display: flex;
                      align-items: center;
                      justify-content: center;
                      width: 20px;
                    "
                    class=""
                    type=""
                    role="button"
                    @click="
                      (dialogProbleme = true), (dialog = true), (id = item.id)
                    "
                  >
                    <span style="height: 50px; padding-top: 15px"
                      ><i class="bi bi-envelope-at"></i
                    ></span>
                  </button>
                </th>
              </tr>
            </template>
          </v-data-table>
        </v-card>
        <br />
      </v-col>
    </v-row>
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
    <br />


  </v-container>
</template>

<script>
import ServiceBenefecieServices from "./../services/ServiceBenefecieServices";
import EmailServices from "@/services/EmailServices";
export default {
  name: "DemandeVue",
  data() {
    return {
      dialog: false,
      dialogProbleme: false,
      dialogPerte: false,
      search1: "",
      search2: "",
      actifHeaders: [
        { text: "Utilisateur", value: "userId" },
        { text: "Perte", value: "details", sortable: false },
        {
          text: "Numero Salle",
          value: "details",
          sortable: true,
          align: "center",
          style: "text-align:center",
        },
        {
          text: "Depuis",
          value: "action",
          sortable: true,
          align: "center",
          style: "text-align:center",
        },
        { text: "Action", value: "action", sortable: false },
      ],
      logicielHeaders: [
        { text: "Utilisateur", value: "userId" },
        { text: "Probleme", value: "details", sortable: false },
        { text: "Priorité", value: "priority", sortable: false },
        { text: "Etat", value: "action", sortable: false },
        { text: "Depuis", value: "action", sortable: false },
        { text: "Action", value: "action", sortable: false },
      ],
      Problemes: [],
      Perte: [],
      id: null,
      object: "",
      response: "",
      message: "",
      snackbar: "",
      color: "red"
    };
  },

  methods: {
    async updateStatus(id) {
      if (await ServiceBenefecieServices.updateStatus(id, "OPEN")) {
        this.start();
      }
    },
    async envoyerEmail() {
      this.message = "Echec d'envoi d'e-mail."

      await EmailServices.sendEmail(this.id, this.objet, this.response)
        .then((response) => {
          console.log(response);
          this.dialog = false;
          if(response == "email-sent") {
            this.message = "E-mail envoyee avec succees."
            this.color = "green"
          }
        })
        .catch((error) => {
          console.log(error);
          this.dialog = false;
        });

        this.snackbar = true

        this.id = ""
        this.objet = ""
        this.response = ""

    },
    async start() {
      await ServiceBenefecieServices.fetchPerte()
        .then((response) => {
          console.log(response);
          this.Perte = response;
        })
        .catch((error) => {
          console.log(error);
        });
      await ServiceBenefecieServices.fetchProbleme()
        .then((response) => {
          console.log(response);
          this.Problemes = response;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  computed: {
    role() {
      return this.$store.state.auth.status.role
    },
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
    timeAgo() {
      return function (dateString) {
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
      };
    },
  },
  created() {
      this.start();
  },
};
</script>
<style scoped>
.button-23:hover {
  color: #008080;
}

.d-inline-block:hover {
  color: #008080;
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
  /* font-size: 16px; */
  font-weight: 20px;
  line-height: 2px;

  outline: none;
  padding-left: 50px;
  padding: 13px 50px;
  position: relative;
  text-align: center;
  text-decoration: none;
  touch-action: manipulation;
  transition: box-shadow 0.2s, -ms-transform 0.1s, -webkit-transform 0.1s,
    transform 0.1s;
  user-select: none;
  -webkit-user-select: none;

  width: 20px;
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
</style>
