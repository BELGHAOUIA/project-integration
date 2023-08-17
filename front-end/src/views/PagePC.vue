<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card>
          <v-card-title>
            Les Actifs
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
            :items="actifs"
            item-key="id"
            :search="search1"
          >
            <template v-slot:item="{ item }">
              <tr>
                <th>{{ item.type }}</th>

                <th>{{ item.modele }}</th>
                <th>{{ item.reference }}</th>
                <th  v-if="role=='TECHNICIEN'">
                  <v-btn
                    @click="confirmDelete(item)"
                    outlined
                    color="red"
                    :style="{ borderColor: 'red' }"
                  >
                    <v-icon>mdi-close</v-icon>
                  </v-btn>
                </th>
              </tr>
            </template>
          </v-data-table>
          <v-dialog v-model="confirmSupp" max-width="400">
            <v-card>
              <v-card-title>Confirmation de suppression</v-card-title>
              <v-card-text>
                Voulez-vous vraiment supprimer cet actif ?
              </v-card-text>
              <v-card-actions>
                <v-btn
                  outlined
                  color="red"
                  :style="{ borderColor: 'red' }"
                  @click="deleteItem"
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
        </v-card>
        <br />
        <div>
          <button
          v-if="role=='TECHNICIEN'"
            outlined
            style="
              display: flex;
              align-items: center;
              justify-content: center;
              margin-left: 20px;
            "
            class="button-23"
            id="pdfbutton"
            type=""
            role="button"
            @click="dialogActif = true"
          >
            <span style="margin-right: 5px">Ajouter un Actif</span>
          </button>
          <br>
          <button
        
            outlined
            style="
              display: flex;
              align-items: center;
              justify-content: center;
              margin-left: 20px;
            "
            class="button-23"
            type=""
            role="button"
            @click="generatePDF"
          >
            <span style="margin-right: 5px">Telecharger le pdf</span>
          </button>

          <v-dialog v-model="dialogActif" max-width="400">
            <v-card>
              <v-card-title>Ajouter un Actif </v-card-title>
              <v-card-text>
                <v-form>
                  <v-select
                    v-model="type"
                    label="Type"
                    :items="['Pc', 'Unite', 'Ecran', 'Clavier', 'Souris']"
                    :rules="typeRules"
                  ></v-select>
                  <v-text-field
                    v-model="modele"
                    label="Modele"
                    :rules="modeleRules"
                  ></v-text-field>
                  <v-text-field
                    v-model="reference"
                    label="Reference"
                    :rules="referenceRules"
                  ></v-text-field>
                  <v-text-field
                    v-model="numeroActif"
                    label="Numero Poste"
                    type="number"
                    :rules="numberRules"
                  ></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn
                  outlined
                  color="#008080"
                  :style="{ borderColor: 'black' }"
                  @click.prevent="AddActif"
                  >Ajouter</v-btn
                >
                <v-btn
                  outlined
                  color="gray"
                  :style="{ borderColor: 'black' }"
                  @click="dialogActif = false"
                  >Annuler</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </div>
      </v-col>
      <v-col>
        <v-card>
          <v-card-title>
            Les Logiciels
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
            :items="logiciels"
            item-key="id"
            :search="search2"
          >
            <template v-slot:item="{ item }">
              <tr>
                <th>{{ item.nom }}</th>
                <th>
                  <v-img
                    :src="item.photo"
                    alt="item photo"
                    width="60"
                    height="60"
                  />
                </th>
                <th>
                  <!-- @click="DeleteButtonLogiciel(item.id)" -->
                  <v-btn
                  v-if="role=='TECHNICIEN'"
                  @click="confirmDeleteLogiciel(item)"
                    
                    outlined
                    color="red"
                    :style="{ borderColor: 'red' }"
                  >
                    <v-icon>mdi-close</v-icon>
                  </v-btn>
                </th>
              </tr>
            </template>
          </v-data-table>
          <v-dialog v-model="confirmSuppLogiciel" max-width="400">
            <v-card>
              <v-card-title>Confirmation de suppression</v-card-title>
              <v-card-text>
                Voulez-vous vraiment supprimer ce logiciel ?
              </v-card-text>
              <v-card-actions>
                <v-btn
                  outlined
                  color="red"
                  :style="{ borderColor: 'red' }"
                  @click="deleteItemLogiciel"
                  >Supprimer</v-btn
                >
                <v-btn
                  outlined
                  color="gray"
                  :style="{ borderColor: 'black' }"
                  @click="confirmSuppLogiciel = false"
                  >Annuler</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-card>
        <br />
        <div>
          <button
          v-if="role=='TECHNICIEN'"
            style="
              display: flex;
              align-items: center;
              justify-content: center;
              margin-left: 10px;
            "
            class="button-23"
            type=""
            role="button"
            @click="dialogLogiciel = true"
          >
            <span style="margin-right: 5px">Ajouter un Logiciel</span>
          </button>
          <v-dialog v-model="dialogLogiciel" max-width="400">
            <v-card>
              <v-card-title>Ajouter un Logiciel </v-card-title>
              <v-card-text>
                <v-form>
                  <v-text-field
                    v-model="nom"
                    label="Nom"
                    :rules="nomRules"
                  ></v-text-field>
                  <v-file-input
                    v-model="photo"
                    label="photo"
                    accept="image/*"
                    :rules="photoRules"
                  ></v-file-input>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn
                  outlined
                  color="#008080"
                  :style="{ borderColor: 'black' }"
                  @click="AddLogiciel"
                  >Ajouter</v-btn
                >
                <v-btn
                  outlined
                  color="gray"
                  :style="{ borderColor: 'black' }"
                  @click="dialogLogiciel = false"
                  >Annuler</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </div>
      </v-col>
    </v-row>
    <br />
    <div style="display: flex; flex-wrap: wrap">
      <div style="width: 50%">
        <v-row align-items="center">
          <v-col>
            <v-img
              src="../assets/datashow.png"
              width="300"
              height="300"
              style="margin-left: 100px"
            ></v-img>
          </v-col>
          <v-col>
            <button
            v-if="role=='TECHNICIEN'"
              @click="updateProjecteur(!projecteur)"
              style="
                display: flex;
                align-items: center;
                justify-content: center;
                margin-left: 10px;
                margin-top: 50px;
              "
              class="button-23"
              type=""
              role="button"
            >
              <span style="margin-right: 5px">
                {{ projecteur ? "Supprimer" : "Ajouter" }}</span
              >
            </button>
          </v-col>
        </v-row>
      </div>
      <div style="width: 50%">
        <v-row
          align-items="center"
          class="flex-column"
          style="margin-left: 150px"
        >
          <v-col>
            <v-img src="../assets/table.jpg" width="300" height="300"></v-img>
          </v-col>
          <v-col style="margin-top: 25px; margin-left: 40px">
            <div>
              Nombre de tables : <span>{{ numTables }}</span>
            </div>
            <v-btn
            v-if="role=='TECHNICIEN'"
              outlined
              color="gray"
              :style="{ borderColor: 'black' }"
              style="width: 100px; height: 45px"
              @click="incrementTables"
              class="d-inline-block"
            >
              <i class="bi bi-plus-lg"></i>
            </v-btn>
            <v-btn
            v-if="role=='TECHNICIEN'"
              outlined
              color="red"
              :style="{ borderColor: 'black' }"
              style="margin-left: 20px; width: 100px; height: 45px"
              @click="decrementTables"
              class="d-inline-block"
            >
              <i class="bi bi-dash-lg"></i>
            </v-btn>
          </v-col>
        </v-row>
      </div>
      <template>
        <div class="emploi">
  <template>
    <v-data-table
      :headers="headers"
      :items="emploi"
      :items-per-page="6"
      hide-default-footer
      class="elevation-1 emploiN"
    >
      <template v-slot:body="{ items }">
        <tbody>
          <tr v-for="(item, index) in items" :key="index">
            <td class="text-center">{{ item.jour }}</td>
            <td class="text-center">{{ item.prof1 }}</td>
            <td class="text-center">{{ item.prof2 }}</td>
            <td class="text-center">{{ item.prof3 }}</td>
            <td class="text-center">{{ item.prof4 }}</td>
            <td class="text-center">{{ item.prof5 }}</td>
            <td class="text-center">{{ item.prof6 }}</td>
          </tr>
        </tbody>
      </template>
    </v-data-table>
  </template>
</div>
        <div class="emploi">
          <div>
            <div v-if="afficherEmploi" class="emploi">
              <v-data-table
              class="emploiN"
                :headers="headers"
                :items="emploi"
                item-key="id"
                :items-per-page="6"
                hide-default-footer
              >
                <template v-slot:item="{ item }">
                  <tr>
                    <td>{{ item.jour }}</td>
                    <td>
                      <v-select :items="selectOptions" v-model="item.prof1" />
                    </td>
                    <td>
                      <v-select :items="selectOptions" v-model="item.prof2" />
                    </td>
                    <td>
                      <v-select :items="selectOptions" v-model="item.prof3" />
                    </td>
                    <td>
                      <v-select :items="selectOptions" v-model="item.prof4" />
                    </td>
                    <td>
                      <v-select :items="selectOptions" v-model="item.prof5" />
                    </td>
                    <td>
                      <v-select :items="selectOptions" v-model="item.prof6" />
                    </td>
                  </tr>
                </template>
              </v-data-table>
            </div>
          </div>
          <div class="button">
            <button
            v-if="role=='TECHNICIEN'"
              style="
                display: flex;
                align-items: center;
                justify-content: center;
              "
              class="button-23"
              type=""
              role="button"
              @click.prevent="submitForm"
            >
              {{ afficherEmploi ? "Enregistrer" : "Modifier l'emploi" }}
            </button>
          </div>
        </div>
      </template>
    </div>
  </v-container>
</template>

<script>
import SalleServices from "./../services/SalleServices";
import LogicielServices from "./../services/LogicielServices";
import ActifServices from "./../services/ActifServices";
import EmploiServices from "@/services/EmploiServices";
import UserServices from "@/services/UserServices";
export default {
  name: "PagPc",
  data() {
    return {
      confirmSupp: false,
      confirmSuppLogiciel: false,
      actifSupp: null,
      logicielSupp: null,
      afficherEmploi: false,
      headers: [
        { text: "" },
        {
          text: "8:30-10:00",
          value: "prof1",
          align: "center",
          style: "text-align:center",
        },
        {
          text: "10:10-11:40",
          value: "prof2",
          align: "center",
          style: "text-align:center",
        },
        {
          text: "11:50-13:20",
          value: "prof3",
          align: "center",
          style: "text-align:center",
        },
        {
          text: "12:40-14:10",
          value: "prof4",
          align: "center",
          style: "text-align:center",
        },
        {
          text: "14:20-15:50",
          value: "prof5",
          align: "center",
          style: "text-align:center",
        },
        {
          text: "16:00-17:30",
          value: "prof6",
          align: "center",
          style: "text-align:center",
        },
      ],
      emploi: [],
      selectOptions: [
        "Bejaoui Hana",
        "Chaouch Imène",
        "Ben Salem Wiem",
        "Ben Tekaya Yosra",
        "Elyes Snoussi",
        "Hachani Nadia",
        "Bilel Zemzem",
        "Hmida Hmida",
        "Bel Hadj Jrad Imène",
        "Bra Ines",
      ],
      dialogActif: false,
      dialogLogiciel: false,
      search1: "",
      search2: "",
      actifHeaders: [
        { text: "Type", value: "type" },
        { text: "Modèle", value: "modele" },
        { text: "Reference", value: "reference" },
        { text: "Action", value: "action", sortable: false },
      ],
      actifs: [
        { type: "Portable", modele: "iPhone 12", reference: "A2404", id: 1 },
        {
          type: "Portable",
          modele: "Samsung Galaxy S21",
          reference: "SM-G991U",
          id: 2,
        },
      ],
      logicielHeaders: [
        { text: "Nom", value: "nom" },
        { text: "Photo", value: "photo", sortable: false },
        { text: "Action", value: "action", sortable: false },
      ],
      logiciels: [],
      projecteur: null,
      nom: "",
      photo: "",
      modele: "",
      type: "",
      reference: "",
      numeroActif: null,
      numTables: 0,
      nomRules: [
        (value) => {
          if (value) return true;

          return "Le nom est requis.";
        },
      ],
      photoRules: [
        (value) => {
          if (value) return true;

          return "La photo est requis.";
        },
      ],
      typeRules: [
        (value) => {
          if (value) return true;

          return "vous devez sélectionner un type.";
        },
      ],
      modeleRules: [
        (value) => {
          if (value) return true;

          return "Le modele est requis.";
        },
      ],
      referenceRules: [
        (value) => {
          if (value) return true;

          return "La reference est requis.";
        },
      ],
      numberRules: [
        (value) => {
          if (value) return true;

          return "La numero est requis.";
        },
      ],
    };
  },
  computed: {
    role() {
      return this.$store.state.auth.status.role
    },
    numeroSalle() {
      return this.$store.getters.getNumeroSalle;
    },
  },
  methods: {
    generatePDF() {
      SalleServices.generatePDF(this.numeroSalle)
      .then((response) => {
              console.log(response);
              const blob = new Blob([response], { type: "application/pdf" });
                    const link = document.createElement("a");
                    link.href = window.URL.createObjectURL(blob);
                    link.download = this.numeroSalle + ".pdf";
                    link.click();
            })
            .catch((error) => {
              console.log(error);
            })
    },
    async submitForm() {
      this.afficherEmploi = !this.afficherEmploi;
     // console.log(this.emploi)
      if (this.afficherEmploi == false) {
       // console.log(this.emploi);
        if (
          await EmploiServices.update(this.emploi, this.numeroSalle)
            .then((response) => {
              console.log(response);
            })
            .catch((error) => {
              console.log(error);
            })
        ){
      console.log("submitForm");
        await  EmploiServices.fetchAll(this.numeroSalle)
            .then((response) => {
              console.log(response);
              this.emploi = response;
            })
            .catch((error) => {
              console.log(error);
            });
        }
      }
    },
    async incrementTables() {
      if (await SalleServices.putTablesNombres(this.numeroSalle, 1)) {
        await SalleServices.fetchTablesNombres(this.numeroSalle)
          .then((response) => {
            //console.log(response);
            this.numTables = response;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    async decrementTables() {
      if (await SalleServices.putTablesNombres(this.numeroSalle, -1)) {
        await SalleServices.fetchTablesNombres(this.numeroSalle)
          .then((response) => {
            //console.log(response);
            this.numTables = response;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    async updateProjecteur(b) {
      if (await SalleServices.putProjecteur(this.numeroSalle, b)) {
        SalleServices.fetchProjecteur(this.numeroSalle)
          .then((response) => {
            console.log(response);
            this.projecteur = response;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    async DeleteButton(id) {
      if (await ActifServices.deleteFromSalle(this.numeroSalle, id)) {
        SalleServices.fetchAllActifs(this.numeroSalle)
          .then((response) => {
            // console.log(response);
            this.actifs = response;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    confirmDelete(item) {
      this.actifSupp = item;
      this.confirmSupp = true;
    },
    deleteItem(id) {
      this.DeleteButton(id);
      this.DeleteButton(this.actifSupp.id);
      this.confirmSupp = false;
    },
    async DeleteButtonLogiciel(id) {
      if (await LogicielServices.deleteFromSalle(this.numeroSalle, id)) {
      this.start()
      }
    },
    confirmDeleteLogiciel(item) {
      this.logicielSupp = item;
      this.confirmSuppLogiciel = true;
    },
    deleteItemLogiciel(id) {
      this.DeleteButtonLogiciel(id);
      this.DeleteButtonLogiciel(this.logicielSupp.id);
      this.confirmSuppLogiciel = false;
    },
    async AddActif() {
      if (
        await ActifServices.add(this.numeroSalle, {
          modele: this.modele,
          type: this.type,
          reference: this.reference,
          etat: "NORMALE",
         // numeroActif: 0
          numeroActif:  Number.parseInt(this.numeroActif)
        })
        ) {
        this.start()
        this.modele = "";
        this.type = "";
        this.reference = "";
        this.numeroActif= "";
        this.dialogActif = false;
      }
    },
    async AddLogiciel() {
      let fileString = this.photo;
      let formData = new FormData();
      formData.append("file", fileString);
      if (
        await LogicielServices.add(this.numeroSalle, {
          nom: this.nom,
          photo: null,
        })
      ) {
        if (await LogicielServices.putLogo(this.nom, formData)) {
          this.nom = "";
          this.photo = "";
          this.dialogLogiciel = false;
          this.start();
        }
      }
    },
    async start() {
      await UserServices.fetchUserNames()
        .then((response) => {
          console.log(response);
          this.selectOptions = response;
        })
        .catch((error) => {
          console.log(error);
        });
      await EmploiServices.fetchAll(this.numeroSalle)
        .then((response) => {
          console.log(response);
          this.emploi = response;
        })
        .catch((error) => {
          console.log(error);
        });

      await SalleServices.fetchAllActifs(this.numeroSalle)
        .then((response) => {
          // console.log(response);
          this.actifs = response;
        })
        .catch((error) => {
          console.log(error);
        });

      await SalleServices.fetchTablesNombres(this.numeroSalle)
        .then((response) => {
          // console.log(response);
          this.numTables = response;
        })
        .catch((error) => {
          console.log(error);
        });

      await SalleServices.fetchAllLogiciels(this.numeroSalle)
        .then((response) => {
          //console.log(response);
          this.logiciels = response;
        })
        .catch((error) => {
          console.log(error);
        });

      this.logiciels.forEach((logiciel) => {
        let objectUrl;
        LogicielServices.fetchImage(logiciel.id)
          .then((response) => {
            const blob = new Blob([response]);
            objectUrl = URL.createObjectURL(blob);
            logiciel.photo = objectUrl;
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {
            //objectUrl.revokeObjectURL
          });
      });

      await SalleServices.fetchProjecteur(this.numeroSalle)
        .then((response) => {
          //console.log(response);
          this.projecteur = response;
        })
        .catch((error) => {
          console.log(error);
        });
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
.emploi {
  margin: auto;
  margin-top: 100px;
}
.button {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}
td {
  border: 1px solid #ccc;
  padding: 8px;
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
#pdfbutton {
  width: 540px;
}

.emploiN {
    /* max-height: 400px; */
    overflow-y: auto;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.24), 0 0 6px rgba(0, 0, 0, 0.12);
  }

  .v-data-table__wrapper {
    border-radius: 8px;
  }

  th {
    font-weight: bold;
    text-align: center;
  }

  td {
    text-align: center;
  }
</style>
