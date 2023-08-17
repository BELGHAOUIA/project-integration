<template>
    <div>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div id="content" class="content content-full-width">

                        <div class="profile">
                            <div class="profile-header d-flex " >

                                <div class="profile-header-cover"></div>
                                
                                <div class="profile-header-content">
                                  
                                    <div class="profile-header-img">
                                        <label for="profile-image-upload" title="choisir un fichier">
                                        <img v-if="dataUrl != null" :src="dataUrl" alt="photo">
                                         <img v-else src="../assets/gg.jpg" alt=""> 
                                        <v-file-input type="file" id="profile-image-upload" ref="fileInput" style="display: none" accept="image/*" @change="onFileSelected" v-model="file"></v-file-input>
                                        
                                    </label>
                                    </div>
                                 
                                    <div class="profile-header-info">
                                        <h4 style="color:black" class="m-t-10 m-b-5">{{ userName }}</h4>
                                        <p style="color:black" class="m-b-10">{{ email }}</p>
                                        <p style="color:black" class="m-t-10 m-b-5">{{ role }}</p>
                                        <div   @click="logout" style="padding-right: 7px;width: 20px;" >
                                         <v-icon >mdi-logout</v-icon> 
                                       
                                </div>
                                    </div>
                              
                                </div>
                                <v-spacer></v-spacer>
<div style="display:grid;justify-content: center;">


                          

  

   
     
                                <br>
                                
                                <RouterLink v-if="role=='TECHNICIEN'" to="../ListProf" style="text-decoration: none;margin-right: 7px;">
        <v-btn text href="#" class="button-16"
          ><i class="bi bi-people-fill"></i>Comptes</v-btn
        ></RouterLink
      ></div>
                            </div>
                            
                        </div>
                    
                        <div class="profile-content">
                           
                            <div class="tab-content p-0">
                              
                                <div class="tab-pane fade active show" id="profile-post" v-if="role!='TECHNICIEN'">
                                    <div class="timeline-container" style="height: 900px; overflow: auto;">

                                    <ul class="timeline" v-for="ticket in tickets" :key="ticket.id">
                                        <li>
                                       
                                            <div class="timeline-time">
                                                <span class="date">mise a jour depuis {{timeAgo(ticket.lastUpdated)}}</span>
                                            </div>
                                          
                                            <div class="timeline-icon">
                                                <a href="javascript:;">&nbsp;</a>
                                            </div>
                                        
                                            <div class="timeline-body" style="border-bottom: 1px black solid;">
                                                <div class="timeline-header">
                                                    <span class="userimage"><img :src="dataUrl" alt=""></span>
                                                    <span class="username">{{ ticket.userName }}
                                                        <small></small></span>
                                                    <span class="pull-right text-muted">{{ ticket.status }}</span>
                                                </div>
                                                <div class="timeline-content">
                                                    <p>{{ ticket.details }}</p>
                                                    <div class="d-flex justify-space-between" v-if="ticket.numeroSalle">
                                                    <v-chip>Numero Salle: {{ticket.numeroSalle}} </v-chip>
                                                    <v-chip v-if="ticket.numeroActif">Numero Poste: {{ ticket.numeroActif }}</v-chip>
                                                </div>
                                                </div>

                                                
                                            </div>
                                            
                                        </li>
                                        
                                        
                                       
                                    </ul>
                                    </div>
                                    <!-- end timeline -->
                                </div>



                                <div class="tab-pane fade active show" id="profile-post" v-if="role=='TECHNICIEN'">
<div class="timeline-container" style="height: 900px; overflow: auto;">
<ul class="timeline" v-for="ticket in acceptedTckets" :key="ticket.id">
    <li>
   
        <div class="timeline-time">
            <span class="date">mise a jour depuis {{timeAgo(ticket.lastUpdated)}}</span>
            <span class="date">ajouter {{timeAgo(ticket.dateOpened)}}</span>       
         </div>
      
        <div class="timeline-icon">
            <a href="javascript:;">&nbsp;</a>
        </div>
    
        <div class="timeline-body" style="border-bottom: 1px black solid;">
            <div class="timeline-header">
                <span v-if="ticket.email != null" class="userimage"><img :src="ticket.email" alt="none"></span>
                <span v-else class="userimage"><img src="" alt="none"></span>
                <span class="username">{{ ticket.userName }}
                    <small></small></span>
                </div>
                <small>

                    <span class="pull-right text-muted">
                        <v-select :items="selectOptions" v-model="ticket.status" @change="updateStatus(ticket.id,ticket.status)"/>
                        <span>{{ ticket.status }}</span>
                    </span>
                </small>
            <div class="timeline-content">
                <p>{{ ticket.details }}</p>

                <div class="d-flex justify-space-between" v-if="ticket.numeroSalle">
                    <v-chip>Numero Salle: {{ticket.numeroSalle}} </v-chip>
                    <v-chip v-if="ticket.numeroActif">Numero Poste: {{ ticket.numeroActif }}</v-chip>
                </div>
            </div>

            
        </div>
        
    </li>
    
    
   
</ul>
</div>
<!-- end timeline -->
</div>



















                                <!-- end #profile-post tab -->
                            </div>
                            <!-- end tab-content -->
                        </div>
                        <!-- end profile-content -->
                    </div>
                </div>
            </div>
        </div>
    <div style="width:1350px;display:grid;padding-left:350px">
        <v-row class="fill-height">
    <v-col>
      <v-sheet height="600">
        <v-calendar
          ref="calendar"
          v-model="value"
          color="primary"
          type="4day"
          :events="events"
          :event-color="getEventColor"
          :event-ripple="false"
          @change="getEvents"
          @mousedown:event="startDrag"
          @mousedown:time="startTime"
          @mousemove:time="mouseMove"
          @mouseup:time="endDrag"
          @mouseleave.native="cancelDrag"
        >
          <template v-slot:event="{ event, timed, eventSummary }">
            <div class="v-event-draggable">
              <component :is="{ render: eventSummary }"></component>
            </div>
            <div
              v-if="timed"
              class="v-event-drag-bottom"
              @mousedown.stop="extendBottom(event)"
            ></div>
          </template>
        </v-calendar>
      </v-sheet>
    </v-col>
  </v-row></div>
    </div>
</template>
<script>
import ServiceBenefecieServices from '@/services/ServiceBenefecieServices' 
import Logout from '@/services/auth.service.js'
import UserServices from '@/services/UserServices.js'

export default {
    name: "PageProfil",
    data: () => ({
      value: '',
      events: [],
      colors: ['#2196F3', '#3F51B5', '#673AB7', '#00BCD4', '#4CAF50', '#FF9800', '#757575'],
      names: ['Meeting', 'Holiday', 'PTO', 'Travel', 'Event', 'Birthday', 'Conference', 'Party'],
      dragEvent: null,
      dragStart: null,
      createEvent: null,
      createStart: null,
      extendOriginal: null,
      tickets:[],
      acceptedTckets: [],
      selectOptions: [
      "Ouvert","En Cours","En Pause","Fermer"
      ],
      status:"",
      user: [],
      file: '',
      dataUrl:''
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
  },
    methods: {
        async onFileSelected(){
            let fileString = this.file
            let formData = new FormData()
            formData.append('file', fileString)
            if(await 
            UserServices.setProfilePicture(formData)
            )
            {
              UserServices.getProfilePicture()
            .then(response => {
            // console.log(response);
            const blob = new Blob([response]);
            const objectUrl = URL.createObjectURL(blob);
            this.dataUrl = objectUrl;
            objectUrl.revokeObjectURL
            })
            .catch(error => {
            console.log(error);
            });
            }
            

        } ,
        logout() {
            Logout.logout()
            this.$router.go()
        },
        async updateStatus(id,status) {
            let newStatus;
            if(status == "Ouvert") newStatus = "OPEN"
            if(status == "En Cours") newStatus = "WORKING"
            if(status == "En Pause") newStatus = "HOLD"
            if(status == "Fermer") newStatus = "CLOSED"
            await ServiceBenefecieServices.updateStatus(id,newStatus)
            .then(response => {
          console.log(response);
        })
        .catch(error => {
          console.log(error);
        });
        this.start()
        },
      startDrag ({ event, timed }) {
        if (event && timed) {
          this.dragEvent = event
          this.dragTime = null
          this.extendOriginal = null
        }
      },
      startTime (tms) {
        const mouse = this.toTime(tms)

        if (this.dragEvent && this.dragTime === null) {
          const start = this.dragEvent.start

          this.dragTime = mouse - start
        } else {
          this.createStart = this.roundTime(mouse)
          this.createEvent = {
            name: `Event #${this.events.length}`,
            color: this.rndElement(this.colors),
            start: this.createStart,
            end: this.createStart,
            timed: true,
          }

          this.events.push(this.createEvent)
        }
      },
      extendBottom (event) {
        this.createEvent = event
        this.createStart = event.start
        this.extendOriginal = event.end
      },
      mouseMove (tms) {
        const mouse = this.toTime(tms)

        if (this.dragEvent && this.dragTime !== null) {
          const start = this.dragEvent.start
          const end = this.dragEvent.end
          const duration = end - start
          const newStartTime = mouse - this.dragTime
          const newStart = this.roundTime(newStartTime)
          const newEnd = newStart + duration

          this.dragEvent.start = newStart
          this.dragEvent.end = newEnd
        } else if (this.createEvent && this.createStart !== null) {
          const mouseRounded = this.roundTime(mouse, false)
          const min = Math.min(mouseRounded, this.createStart)
          const max = Math.max(mouseRounded, this.createStart)

          this.createEvent.start = min
          this.createEvent.end = max
        }
      },
      endDrag () {
        this.dragTime = null
        this.dragEvent = null
        this.createEvent = null
        this.createStart = null
        this.extendOriginal = null
      },
      cancelDrag () {
        if (this.createEvent) {
          if (this.extendOriginal) {
            this.createEvent.end = this.extendOriginal
          } else {
            const i = this.events.indexOf(this.createEvent)
            if (i !== -1) {
              this.events.splice(i, 1)
            }
          }
        }

        this.createEvent = null
        this.createStart = null
        this.dragTime = null
        this.dragEvent = null
      },
      roundTime (time, down = true) {
        const roundTo = 15 // minutes
        const roundDownTime = roundTo * 60 * 1000

        return down
          ? time - time % roundDownTime
          : time + (roundDownTime - (time % roundDownTime))
      },
      toTime (tms) {
        return new Date(tms.year, tms.month - 1, tms.day, tms.hour, tms.minute).getTime()
      },
      getEventColor (event) {
        const rgb = parseInt(event.color.substring(1), 16)
        const r = (rgb >> 16) & 0xFF
        const g = (rgb >> 8) & 0xFF
        const b = (rgb >> 0) & 0xFF

        return event === this.dragEvent
          ? `rgba(${r}, ${g}, ${b}, 0.7)`
          : event === this.createEvent
            ? `rgba(${r}, ${g}, ${b}, 0.7)`
            : event.color
      },
      getEvents ({ start, end }) {
        const events = []

        const min = new Date(`${start.date}T00:00:00`).getTime()
        const max = new Date(`${end.date}T23:59:59`).getTime()
        const days = (max - min) / 86400000
        const eventCount = this.rnd(days, days + 20)

        for (let i = 0; i < eventCount; i++) {
          const timed = this.rnd(0, 3) !== 0
          const firstTimestamp = this.rnd(min, max)
          const secondTimestamp = this.rnd(2, timed ? 8 : 288) * 900000
          const start = firstTimestamp - (firstTimestamp % 900000)
          const end = start + secondTimestamp

          events.push({
            name: this.rndElement(this.names),
            color: this.rndElement(this.colors),
            start,
            end,
            timed,
          })
        }

        this.events = events
      },
      rnd (a, b) {
        return Math.floor((b - a + 1) * Math.random()) + a
      },
      rndElement (arr) {
        return arr[this.rnd(0, arr.length - 1)]
      },
      async start() {

        UserServices.getProfilePicture()
            .then(response => {
            // console.log(response);
            const blob = new Blob([response]);
            const objectUrl = URL.createObjectURL(blob);
            this.dataUrl = objectUrl;
            objectUrl.revokeObjectURL
            })
            .catch(error => {
            console.log(error);
            });

        if(this.role!= 'TECHNICIEN') {
            await ServiceBenefecieServices.fetchAllByUser()
            .then(response => {
              this.tickets = response
            })
            .catch(error => {
              console.log(error);
            });
    }

        await ServiceBenefecieServices.fetchAllAcceptedTickets()
        .then(response => {
          console.log(response.data);
          this.acceptedTckets = response
        })
        .catch(error => {
          console.log(error);
        });

        this.acceptedTckets.forEach((ticket) => {
            let objectUrl;
        UserServices.getTargetUserProfilePicture(ticket.email)
          .then((response) => {
            //console.log("photo de user : " + response)
            const blob = new Blob([response]);
            objectUrl = URL.createObjectURL(blob);
            ticket.email = objectUrl;
            // console.log("photo de user : " + ticket.email)
          })
          .catch((error) => {
            console.log(error);
          });
        });

        UserServices.fetchUserDetails()
        .then(response => {
          console.log(response.data);
          this.user = response
        })
        .catch(error => {
          console.log(error);
        });
      },
    },
    created() {
        this.start()
    }
  }
</script>
<style scoped>

.profile-header-img img:hover {
  opacity: 0.5;
  cursor: grab;
}
.logout-button {
  background-color: #fff;
  color: #333;
  /* border: 2px solid #333; */
  /* border-radius: 25px; */
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
  text-transform: uppercase;
  cursor: pointer;
  transition: all 0.3s ease;
  max-height: 60px;
}

.logout-button:hover {
  background-color: grey;
  color: #fff;
}

.logout-button i {
  margin-right: 10px;
  font-size: 20px;
}

body {
    margin-top: 20px;
    background: #eee;
}

.profile-header {
    position: relative;
    overflow: hidden
}

.profile-header .profile-header-cover {
    background-image: url(https://bootdey.com/img/Content/bg1.jp);
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0
}

.profile-header .profile-header-cover:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(to bottom, rgba(194, 194, 194, 0.75) 00% ,rgba(0, 0, 0, 0))
}

.profile-header .profile-header-content {
    color: #fff;
    padding: 25px
}

.profile-header-img {
    float: left;
    width: 120px;
    height: 120px;
    overflow: hidden;
    position: relative;
    z-index: 10;
    margin: 0 0 -20px;
    padding: 3px;
    border-radius: 4px;
    background: #fff
}

.profile-header-img img {
    max-width: 100%
}

.profile-header-info h4 {
    font-weight: 500;
    color: #fff
}

.profile-header-img+.profile-header-info {
    margin-left: 140px
}

.profile-header .profile-header-content,
.profile-header .profile-header-tab {
    position: relative
}

.b-minus-1,
.b-minus-10,
.b-minus-2,
.b-minus-3,
.b-minus-4,
.b-minus-5,
.b-minus-6,
.b-minus-7,
.b-minus-8,
.b-minus-9,
.b-plus-1,
.b-plus-10,
.b-plus-2,
.b-plus-3,
.b-plus-4,
.b-plus-5,
.b-plus-6,
.b-plus-7,
.b-plus-8,
.b-plus-9,
.l-minus-1,
.l-minus-2,
.l-minus-3,
.l-minus-4,
.l-minus-5,
.l-minus-6,
.l-minus-7,
.l-minus-8,
.l-minus-9,
.l-plus-1,
.l-plus-10,
.l-plus-2,
.l-plus-3,
.l-plus-4,
.l-plus-5,
.l-plus-6,
.l-plus-7,
.l-plus-8,
.l-plus-9,
.r-minus-1,
.r-minus-10,
.r-minus-2,
.r-minus-3,
.r-minus-4,
.r-minus-5,
.r-minus-6,
.r-minus-7,
.r-minus-8,
.r-minus-9,
.r-plus-1,
.r-plus-10,
.r-plus-2,
.r-plus-3,
.r-plus-4,
.r-plus-5,
.r-plus-6,
.r-plus-7,
.r-plus-8,
.r-plus-9,
.t-minus-1,
.t-minus-10,
.t-minus-2,
.t-minus-3,
.t-minus-4,
.t-minus-5,
.t-minus-6,
.t-minus-7,
.t-minus-8,
.t-minus-9,
.t-plus-1,
.t-plus-10,
.t-plus-2,
.t-plus-3,
.t-plus-4,
.t-plus-5,
.t-plus-6,
.t-plus-7,
.t-plus-8,
.t-plus-9 {
    position: relative !important
}

.profile-header .profile-header-tab {
    background: #fff;
    list-style-type: none;
    margin: -10px 0 0;
    padding: 0 0 0 140px;
    white-space: nowrap;
    border-radius: 0
}

.text-ellipsis,
.text-nowrap {
    white-space: nowrap !important
}

.profile-header .profile-header-tab>li {
    display: inline-block;
    margin: 0
}

.profile-header .profile-header-tab>li>a {
    display: block;
    color: #929ba1;
    line-height: 20px;
    padding: 10px 20px;
    text-decoration: none;
    font-weight: 700;
    font-size: 12px;
    border: none
}

.profile-header .profile-header-tab>li.active>a,
.profile-header .profile-header-tab>li>a.active {
    color: #242a30
}

.profile-content {
    padding: 25px;
    border-radius: 4px
}

.profile-content:after,
.profile-content:before {
    content: '';
    display: table;
    clear: both
}

.profile-content .tab-content,
.profile-content .tab-pane {
    background: 0 0
}

.profile-left {
    width: 200px;
    float: left
}

.profile-right {
    margin-left: 240px;
    padding-right: 20px
}

.profile-image {
    height: 175px;
    line-height: 175px;
    text-align: center;
    font-size: 72px;
    margin-bottom: 10px;
    border: 2px solid #E2E7EB;
    overflow: hidden;
    border-radius: 4px
}

.profile-image img {
    display: block;
    max-width: 100%
}

.profile-highlight {
    padding: 12px 15px;
    background: #FEFDE1;
    border-radius: 4px
}

.profile-highlight h4 {
    margin: 0 0 7px;
    font-size: 12px;
    font-weight: 700
}

.table.table-profile>thead>tr>th {
    border-bottom: none !important
}

.table.table-profile>thead>tr>th h4 {
    font-size: 20px;
    margin-top: 0
}

.table.table-profile>thead>tr>th h4 small {
    display: block;
    font-size: 12px;
    font-weight: 400;
    margin-top: 5px
}

.table.table-profile>tbody>tr>td,
.table.table-profile>thead>tr>th {
    border: none;
    padding-top: 7px;
    padding-bottom: 7px;
    color: #242a30;
    background: 0 0
}

.table.table-profile>tbody>tr>td.field {
    width: 20%;
    text-align: right;
    font-weight: 600;
    color: #2d353c
}

.table.table-profile>tbody>tr.highlight>td {
    border-top: 1px solid #b9c3ca;
    border-bottom: 1px solid #b9c3ca
}

.table.table-profile>tbody>tr.divider>td {
    padding: 0 !important;
    height: 10px
}

.profile-section+.profile-section {
    margin-top: 20px;
    padding-top: 20px;
    border-top: 1px solid #b9c3ca
}

.profile-section:after,
.profile-section:before {
    content: '';
    display: table;
    clear: both
}

.profile-section .title {
    font-size: 20px;
    margin: 0 0 15px
}

.profile-section .title small {
    font-weight: 400
}

body.flat-black {
    background: #E7E7E7
}

.flat-black .navbar.navbar-inverse {
    background: #212121
}

.flat-black .navbar.navbar-inverse .navbar-form .form-control {
    background: #4a4a4a;
    border-color: #4a4a4a
}

.flat-black .sidebar,
.flat-black .sidebar-bg {
    background: #3A3A3A
}

.flat-black .page-with-light-sidebar .sidebar,
.flat-black .page-with-light-sidebar .sidebar-bg {
    background: #fff
}

.flat-black .sidebar .nav>li>a {
    color: #b2b2b2
}

.flat-black .sidebar.sidebar-grid .nav>li>a {
    border-bottom: 1px solid #474747;
    border-top: 1px solid #474747
}

.flat-black .sidebar .active .sub-menu>li.active>a,
.flat-black .sidebar .nav>li.active>a,
.flat-black .sidebar .nav>li>a:focus,
.flat-black .sidebar .nav>li>a:hover,
.flat-black .sidebar .sub-menu>li>a:focus,
.flat-black .sidebar .sub-menu>li>a:hover,
.sidebar .nav>li.nav-profile>a {
    color: #fff
}

.flat-black .sidebar .sub-menu>li>a,
.flat-black .sidebar .sub-menu>li>a:before {
    color: #999
}

.flat-black .page-with-light-sidebar .sidebar .active .sub-menu>li.active>a,
.flat-black .page-with-light-sidebar .sidebar .active .sub-menu>li.active>a:focus,
.flat-black .page-with-light-sidebar .sidebar .active .sub-menu>li.active>a:hover,
.flat-black .page-with-light-sidebar .sidebar .nav>li.active>a,
.flat-black .page-with-light-sidebar .sidebar .nav>li.active>a:focus,
.flat-black .page-with-light-sidebar .sidebar .nav>li.active>a:hover {
    color: #000
}

.flat-black .page-sidebar-minified .sidebar .nav>li.has-sub:focus>a,
.flat-black .page-sidebar-minified .sidebar .nav>li.has-sub:hover>a {
    background: #323232
}

.flat-black .page-sidebar-minified .sidebar .nav li.has-sub>.sub-menu,
.flat-black .sidebar .nav>li.active>a,
.flat-black .sidebar .nav>li.active>a:focus,
.flat-black .sidebar .nav>li.active>a:hover,
.flat-black .sidebar .nav>li.nav-profile,
.flat-black .sidebar .sub-menu>li.has-sub>a:before,
.flat-black .sidebar .sub-menu>li:before,
.flat-black .sidebar .sub-menu>li>a:after {
    background: #2A2A2A
}

.flat-black .page-sidebar-minified .sidebar .sub-menu>li:before,
.flat-black .page-sidebar-minified .sidebar .sub-menu>li>a:after {
    background: #3e3e3e
}

.flat-black .sidebar .nav>li.nav-profile .cover.with-shadow:before {
    background: rgba(42, 42, 42, .75)
}

.bg-white {
    background-color: #fff !important;
}

.p-10 {
    padding: 10px !important;
}

.media.media-xs .media-object {
    width: 32px;
}

.m-b-2 {
    margin-bottom: 2px !important;
}

.media>.media-left,
.media>.pull-left {
    padding-right: 15px;
}

.media-body,
.media-left,
.media-right {
    display: table-cell;
    vertical-align: top;
}

select.form-control:not([size]):not([multiple]) {
    height: 34px;
}

.form-control.input-inline {
    display: inline;
    width: auto;
    padding: 0 7px;
}


.timeline {
    list-style-type: none;
    margin: 0;
    padding: 0;
    position: relative
}

.timeline:before {
    content: '';
    position: absolute;
    top: 5px;
    bottom: 5px;
    width: 5px;
    background: #2d353c;
    left: 20%;
    margin-left: -2.5px
}

.timeline>li {
    position: relative;
    min-height: 50px;
    padding: 20px 0
}

.timeline .timeline-time {
    position: absolute;
    left: 0;
    width: 18%;
    text-align: right;
    top: 30px
}

.timeline .timeline-time .date,
.timeline .timeline-time .time {
    display: block;
    font-weight: 600
}

.timeline .timeline-time .date {
    line-height: 16px;
    font-size: 12px
}

.timeline .timeline-time .time {
    line-height: 24px;
    font-size: 20px;
    color: #242a30
}

.timeline .timeline-icon {
    left: 15%;
    position: absolute;
    width: 10%;
    text-align: center;
    top: 40px
}

.timeline .timeline-icon a {
    text-decoration: none;
    width: 20px;
    height: 20px;
    display: inline-block;
    border-radius: 20px;
    background: #d9e0e7;
    line-height: 10px;
    color: #fff;
    font-size: 14px;
    border: 5px solid #2d353c;
    transition: border-color .2s linear
}

.timeline .timeline-body {
    margin-left: 23%;
    margin-right: 17%;
    background: #fff;
    position: relative;
    padding: 20px 25px;
    border-radius: 6px
}

.timeline .timeline-body:before {
    content: '';
    display: block;
    position: absolute;
    border: 10px solid transparent;
    border-right-color: #fff;
    left: -20px;
    top: 20px
}

.timeline .timeline-body>div+div {
    margin-top: 15px
}

.timeline .timeline-body>div+div:last-child {
    margin-bottom: -20px;
    padding-bottom: 20px;
    border-radius: 0 0 6px 6px
}

.timeline-header {
    padding-bottom: 10px;
    border-bottom: 1px solid #e2e7eb;
    line-height: 30px
}

.timeline-header .userimage {
    float: left;
    width: 34px;
    height: 34px;
    border-radius: 40px;
    overflow: hidden;
    margin: -2px 10px -2px 0
}

.timeline-header .username {
    font-size: 16px;
    font-weight: 600
}

.timeline-header .username,
.timeline-header .username a {
    color: #2d353c
}

.timeline img {
    max-width: 100%;
    display: block
}

.timeline-content {
    letter-spacing: .25px;
    line-height: 18px;
    font-size: 13px
}

.timeline-content:after,
.timeline-content:before {
    content: '';
    display: table;
    clear: both
}

.timeline-title {
    margin-top: 0
}

.timeline-footer {
    background: #fff;
    border-top: 1px solid #e2e7ec;
    padding-top: 15px
}

.timeline-footer a:not(.btn) {
    color: #575d63
}

.timeline-footer a:not(.btn):focus,
.timeline-footer a:not(.btn):hover {
    color: #2d353c
}

.timeline-likes {
    color: #6d767f;
    font-weight: 600;
    font-size: 12px
}

.timeline-likes .stats-right {
    float: right
}

.timeline-likes .stats-total {
    display: inline-block;
    line-height: 20px
}

.timeline-likes .stats-icon {
    float: left;
    margin-right: 5px;
    font-size: 9px
}

.timeline-likes .stats-icon+.stats-icon {
    margin-left: -2px
}

.timeline-likes .stats-text {
    line-height: 20px
}

.timeline-likes .stats-text+.stats-text {
    margin-left: 15px
}

.timeline-comment-box {
    background: #f2f3f4;
    margin-left: -25px;
    margin-right: -25px;
    padding: 20px 25px
}

.timeline-comment-box .user {
    float: left;
    width: 34px;
    height: 34px;
    overflow: hidden;
    border-radius: 30px
}

.timeline-comment-box .user img {
    max-width: 100%;
    max-height: 100%
}

.timeline-comment-box .user+.input {
    margin-left: 44px
}

.lead {
    margin-bottom: 20px;
    font-size: 21px;
    font-weight: 300;
    line-height: 1.4;
}

.text-danger,
.text-red {
    color: #ff5b57 !important;
}




.v-event-draggable {
  padding-left: 6px;
}

.v-event-timed {
  user-select: none;
  -webkit-user-select: none;
}

.v-event-drag-bottom {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 4px;
  height: 4px;
  cursor: ns-resize;

  &::after {
    display: none;
    position: absolute;
    left: 50%;
    height: 4px;
    border-top: 1px solid white;
    border-bottom: 1px solid white;
    width: 16px;
    margin-left: -8px;
    opacity: 0.8;
    content: '';
  }

  &:hover::after {
    display: block;
  }
}





/* CSS */
.button-16 {
  background-color: #f8f9fa;
  border: 1px solid #f8f9fa;
  border-radius: 4px;
  color: #3c4043;
  cursor: pointer;
  font-family: arial,sans-serif;
  font-size: 14px;
  height: 36px;
  line-height: 27px;
  min-width: 54px;
  padding: 0 16px;
  text-align: center;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  white-space: pre;
}

.button-16:hover {
  border-color: #dadce0;
  box-shadow: rgba(0, 0, 0, .1) 0 1px 1px;
  color: #202124;
}

.button-16:focus {
  border-color: #4285f4;
  outline: none;
}
</style>