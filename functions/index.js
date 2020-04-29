const functions = require('firebase-functions');
const admin = require('firebase-admin');
const Emitet = require('events');
admin.initializeApp();


exports.FreedomVote = functions.https.onRequest(async (req, res) => {
    var fieldRoom = req.body['Room'];
    const rooms = await admin.firestore().collection('/Rooms/' + fieldRoom + "/Roles").get();
    var countVote = 0;
    var id = 0;
    rooms.docs.forEach(target => {
        if(countVote < target.data().VoicesAgainst) {
            countVote = target.data().VoicesAgainst;
            id = target.data().Id;
            console.log("id: " + id);
        }
    });

    let myEvent = new Emitet();
    let myResponse = "result";
    myEvent.on(myResponse,function(data) {
      res.send(data);
    });

    rooms.forEach(kill => {
        kill.ref.update("Voted",false);
        kill.ref.update("VoicesAgainst",0);
        if(kill.data().Id == id){
         kill.ref.update("isDead",true);
        }
        if(countVote == 0) {
          myEvent.emit(myResponse,'{"Result":"Voited nobody"}')
        }
    })
    
    rooms.query.where('Id','==',id).onSnapshot(personage => {
      personage.docChanges().forEach(dead => {
        if(dead.doc.data().isDead == true) {
          console.log("log for dead " + JSON.stringify(dead.doc.data()));
          myEvent.emit(myResponse,dead.doc.data());
        }
      })
    })
})