<template>
    <div class="three" style="display:grid;grid-template-columns: 500px 800px;justify-content: center;padding-top: 130px ">
        <div data-aos="fade-right" data-aos-delay="400"
            style="box-shadow: rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px;">




            <div id="carouselExampleSlidesOnly" class="carousel slide" style="height: 400PX;" data-bs-ride="carousel"
                data-aos="fade-right" data-aos-delay="400">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="../assets/Web search.gif" class="d-block w-100" alt="...">
                    </div>
                    <div class="carousel-item">
                        <img src="../assets/Computer troubleshooting-pana.png" class="d-block w-100" alt="...">
                    </div>

                </div>
            </div>

        </div>
        <div class="cont">
            <div style="display:grid;justify-content: center;" data-aos="fade-left" data-aos-delay="400">
                <h1 style="text-align:center;   color: #008080; ">Signaler un problème ou une perte</h1>
            </div>
           
            <form data-aos="fade-left" data-aos-delay="400">

  <div class="form-group">
    <label for="type"><i class="bi bi-usb-drive"></i><span></span>Type de problème:</label>
    <select id="type" v-model="selectedType" @change="showSubType">
      <option value="" style="box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;">Sélectionner un type de problème</option>
      <option value="perte">Perte de périphérique</option>
      <option value="probleme">Problème technique ou mécanique</option>
    </select>
  </div>
  <div class="form-group" v-if="showSubTypeOption">
    <label for="subType"><i class="bi bi-tools"></i><span></span>Type de problème spécifique:</label>
    <select id="subType" v-model="selectedSubType">
      <option value="">Sélectionner un type de problème</option>
      <option value="probelm_technique">Problème de logiciel</option>
      <option value="probelm_mecanique">Problème de matériel</option>
    </select>
  </div>
  <div class="form-group" > 
    <!-- v-if="selectedSubType === 'probelm_mecanique'" -->
    <label for="salle"><i class="bi bi-building"></i><span></span>Numéro de Salle :</label>
    <div class="d-flex">
    <select id="salle" v-model="numeroSalle"  @change="chooseNumeroSalle(numeroSalle), ok = true">
      <option value="">Sélectionner une salle</option>
      <option v-for="salle in salles" :key="salle.id" :value="salle.numeroSalle">{{ salle.numeroSalle }}</option>
    </select>
</div>
    <div v-if="ok">
        <label for="num_pc"><i class="bi bi-laptop"></i><span></span>Numéro de PC :</label>
        <select id="num_pc" v-model="actif_id">
            <option value="">Sélectionner un PC</option>
            <option v-for="pc in pcs" :key="pc.id" :value="pc.id">{{ pc.numeroActif }}</option>
        </select>
    </div>
  </div>
  <div class="form-group">
    <label for="details"><i class="bi bi-info-circle"></i><span></span>Détails:</label>
    <textarea id="details" v-model="details"></textarea>
  </div>
  <div class="form-group">
<label for="priorite"><i class=""><v-icon>mdi-clock-outline</v-icon></i><span></span>Priorité de ticket:</label>
<select id="priorite" v-model="priority">
<option value="">Sélectionner la Priorité</option>
<option value="low">Bas</option>
<option value="medium">Moyen</option>
<option value="high">Haute</option>
<option value="critical">Critique</option>
</select>
</div>
  <button style="display: flex; align-items: center; justify-content: center;" class="button-23" type="" role="button" @click.prevent="submitForm">
    <span style="margin-right: 5px;">Send</span>
    <i class="bi bi-send"></i>
  </button>
</form>

        </div>
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
    </div>
</template>
  
<script>
import ServiceBenefecieServices from '@/services/ServiceBenefecieServices';
import SalleServices from '@/services/SalleServices';
export default {
    name: "ServicePage",
    data() {
        return {
            selectedType: '',
            selectedSubType: '',
            details: '',
            priority: '',
            numeroSalle: '',
            actif_id: '',
            email: '',
            showSubTypeOption: false,
            salles: [],
            actifs: [],
            ok: false,
            message: "",
            snackbar: false,
            color: "red"

        }
    },
    computed: {
        pcs() {
      return this.actifs.filter(actif => {
        return actif.type.toLowerCase().startsWith("pc");
        });
    },
    },
    methods: {
        showSubType() {
            if (this.selectedType === "probleme") {
                this.showSubTypeOption = true;
            } else {
                this.showSubTypeOption = false;
            }
        },
        async submitForm() {

            console.log('Formulaire envoyé avec succès');

            let typeServiceGeneral;

            if (this.selectedType == 'perte') {
                typeServiceGeneral = this.selectedType;
            }
            else {
                typeServiceGeneral = this.selectedSubType;
            }
            if(this.actif_id == '') this.actif_id = -1
                this.message = "Echec d'ajout."
            if (await ServiceBenefecieServices.add({
                typeTicket: typeServiceGeneral,
                details: this.details,
                priority: this.priority,
                actifId: this.actif_id,
                numeroSalle: this.numeroSalle

            })) {
                this.selectedType = '';
                this.selectedSubType = '';
                this.details = '';
                this.email = '';
                this.showSubTypeOption = false;
                this.priority = '';
                this.numeroSalle = '',
                this.actif_id = '',
                this.ok = false;
                this.color = "green"
                this.message = "Ticket ajouter avec succes."
            }
            this.snackbar = true

        },
        async start() {
            this.salles =await SalleServices.fetchAll();
        },
        async chooseNumeroSalle(numeroSalle) {
            this.actifs = await SalleServices.fetchAllActifs(numeroSalle);
            console.log(this.actifs)
        }
    },
    created() {
        this.start()
    }
}
</script>
  
<style scoped>
.cont {
    max-width: 700px;
    padding-left: 260px;
    display: grid;
    justify-content: center;

}

h1 {
    font-size: 32px;
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 20px;
}

label {
    display: block;
    font-size: 18px;
    margin-bottom: 10px;
}

select,
textarea,
input[type="email"] {
    width: 100%;
    font-size: 18px;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
    box-sizing: border-box;
    margin-top: 5px;
}

input :active {
    border: 2px black solid;
    box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;
}

.button-23 {
    background-color: #FFFFFF;
    border: 1px solid #222222;
    border-radius: 8px;
    box-sizing: border-box;
    color: #222222;
    cursor: pointer;
    display: inline-block;
    font-family: Circular, -apple-system, BlinkMacSystemFont, Roboto, "Helvetica Neue", sans-serif;
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
    transition: box-shadow .2s, -ms-transform .1s, -webkit-transform .1s, transform .1s;
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




.form-group:hover label {
    color: #008080;
}

.form-group:hover input,
.form-group:hover select,
.form-group:hover textarea {
    border-color: #008080;
}

.form-group:focus-within label {
    color: #587177;
}</style>  