const functions = require('firebase-functions');
const admin = require('firebase-admin');
const Emitet = require('events');
admin.initializeApp();


exports.StartGame = functions.https.onRequest(async (req, res) => {
    var fieldRoom = req.body['Room'];
    var firebase = admin.firestore().doc('/Rooms/' + fieldRoom).get().then(file => {
        if(file.data().isBusy != true) {
            file.ref.update("isBusy",true);
            file.ref.update("rounds",file.data().rounds - 1);
            res.send('{"Result":"Start game"')
        } else {
            res.send('{"Result":"Game is started"')
        }
        
    });
})